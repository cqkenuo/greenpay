package com.esiran.greenpay.openapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.PluginLoader;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.common.util.IdWorker;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.message.delayqueue.impl.RedisDelayQueueClient;
import com.esiran.greenpay.openapi.entity.Invoice;
import com.esiran.greenpay.openapi.entity.InvoiceInputDTO;
import com.esiran.greenpay.openapi.service.IInvoiceService;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.plugin.PayOrderFlow;
import com.esiran.greenpay.pay.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class InvoiceService implements IInvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    private static final ModelMapper modelMapper = new ModelMapper();
    private final IMerchantService merchantService;
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    private final IdWorker idWorker;
    private final IInterfaceService interfaceService;
    private final IProductService productService;
    private final RedisDelayQueueClient redisDelayQueueClient;
    private final PluginLoader pluginLoader;
    private static final Gson gson = new GsonBuilder().create();
    public InvoiceService(
            IMerchantService merchantService,
            IOrderService orderService,
            IOrderDetailService orderDetailService,
            IdWorker idWorker,
            IInterfaceService interfaceService,
            IProductService productService,
            RedisDelayQueueClient redisDelayQueueClient,
            PluginLoader pluginLoader) {
        this.merchantService = merchantService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.idWorker = idWorker;
        this.interfaceService = interfaceService;
        this.productService = productService;
        this.redisDelayQueueClient = redisDelayQueueClient;
        this.pluginLoader = pluginLoader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Invoice createInvoiceByInput(InvoiceInputDTO invoiceInputDTO, Merchant merchant) throws Exception {
        Invoice invoice = modelMapper.map(invoiceInputDTO,Invoice.class);
        invoice.setOrderNo(String.valueOf(idWorker.nextId()));
        invoice.setOrderSn(EncryptUtil.baseTimelineCode());
        invoice.setCreatedAt(LocalDateTime.now());
        if (!merchant.getStatus()){
            throw new APIException("商户状态已锁定","MERCHANT_STATUS_LOCKED");
        }
        Product product = productService.getByProductCode(invoice.getChannel());
        if (product == null || !product.getStatus()) {
            throw new APIException("支付产品暂不支持创建订单","PRODUCT_NOT_SUPPORT");
        }
        PassageAndSubAccount passageAndSubAccount =
                merchantService.scheduler(merchant.getId(),product.getId());
        if (passageAndSubAccount == null){
            throw new APIException("支付产品暂不支持创建订单","PRODUCT_NOT_SUPPORT");
        }
        Passage passage = passageAndSubAccount.getPassage();
        PassageAccount passageAccount = passageAndSubAccount.getPassageAccount();
        String insCode = passage.getInterfaceCode();
        Interface ins = interfaceService.getByCode(insCode);
        if (ins == null || !ins.getStatus()){
            throw new APIException("支付产品暂不支持创建订单","PRODUCT_NOT_SUPPORT");
        }
        logger.info("Create order by Product Id: {}, Passage Id: {}, Passage Account: {}",
                product.getId(), passage.getId(), passageAccount.getId());
        // 构造订单
        Order order = modelMapper.map(invoice,Order.class);
        order.setStatus(1);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setMchId(merchant.getId());
        order.setPayProductId(product.getId());
        Integer orderFee = NumberUtil.calculateAmountFee(order.getAmount(),
                passageAndSubAccount.getProductRate());
        order.setFee(orderFee);
        order.setPayProductName(product.getProductName());
        LambdaQueryWrapper<Order> orderQueryWrapper = new QueryWrapper<Order>().lambda()
                .eq(Order::getMchId,order.getMchId())
                .eq(Order::getAppId,order.getAppId())
                .eq(Order::getOutOrderNo,order.getOutOrderNo());
        Order oldOrder = orderService.getOne(orderQueryWrapper);
        if (oldOrder != null){
            throw new APIException("商户订单号不能重复","ORDER_NO_DUPLICATE");
        }
        orderService.save(order);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setPayTypeCode(product.getPayTypeCode());
        orderDetail.setOrderNo(invoice.getOrderNo());
        orderDetail.setPayProductId(product.getId());
        orderDetail.setPayPassageId(passage.getId());
        orderDetail.setPayPassageAccId(passageAccount.getId());
        orderDetail.setPayInterfaceAttr(passageAccount.getInterfaceAttr());
        orderDetail.setPayInterfaceId(ins.getId());
        String channelExtraStr = invoiceInputDTO.getChannelExtra();
        orderDetail.setUpstreamExtra(channelExtraStr);
        orderDetail.setCreatedAt(LocalDateTime.now());
        orderDetail.setUpdatedAt(LocalDateTime.now());
        orderDetailService.save(orderDetail);
        Map<String,Object> results = null;
        try {
            Plugin<PayOrder> plugin = pluginLoader.loadForClassPath(ins.getInterfaceImpl());
            PayOrder payOrder = new PayOrder();
            payOrder.setOrder(order);
            payOrder.setOrderDetail(orderDetail);
            String notifyReceiveUrl = String.format("http://localhost/api/v1/invoices/%s/notify",
                    order.getOrderNo());
            payOrder.setNotifyReceiveUrl(notifyReceiveUrl);
            PayOrderFlow payOrderFlow = new PayOrderFlow(payOrder);
            plugin.apply(payOrderFlow);
            payOrderFlow.execDependent("create");
            results = payOrderFlow.getResults();
            orderDetailService.updatePayCredentialByOrderNo(order.getOrderNo(),results);
        }catch (Exception e){
            e.printStackTrace();
            throw new APIException("CHANNEL_REQUEST_ERROR","支付渠道请求失败");
//            logger.debug(e.getMessage());
        }finally {
            redisDelayQueueClient.sendDelayMessage("order:expire",order.getOrderNo(),20*60*1000);
        }
        Invoice out = modelMapper.map(order,Invoice.class);
        out.setChannel(product.getProductCode());
        out.setCredential(results);
        return out;
    }
}
