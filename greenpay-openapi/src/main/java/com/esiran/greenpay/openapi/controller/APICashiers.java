package com.esiran.greenpay.openapi.controller;

import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.PluginLoader;
import com.esiran.greenpay.common.sign.Md5SignType;
import com.esiran.greenpay.common.sign.SignType;
import com.esiran.greenpay.common.sign.SignVerify;
import com.esiran.greenpay.common.util.MapUtil;
import com.esiran.greenpay.common.util.NumberUtil;
import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.message.delayqueue.impl.RedisDelayQueueClient;
import com.esiran.greenpay.openapi.entity.CashierInputDTO;
import com.esiran.greenpay.openapi.security.OpenAPISecurityUtils;
import com.esiran.greenpay.openapi.service.ICashierService;
import com.esiran.greenpay.pay.entity.Interface;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDetail;
import com.esiran.greenpay.pay.entity.PayOrder;
import com.esiran.greenpay.pay.plugin.PayOrderFlow;
import com.esiran.greenpay.pay.service.IInterfaceService;
import com.esiran.greenpay.pay.service.IOrderDetailService;
import com.esiran.greenpay.pay.service.IOrderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/v1/cashiers")
public class APICashiers {
    private static final Logger logger = LoggerFactory.getLogger(APICashiers.class);
    private final ICashierService cashierService;
    private final IApiConfigService apiConfigService;
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    private final IInterfaceService interfaceService;
    private final PluginLoader pluginLoader;
    private final RedisDelayQueueClient redisDelayQueueClient;
    private static final Gson gson = new GsonBuilder().create();
    @Value("${web.hostname:http://localhost}")
    private String webHostname;
    public APICashiers(
            ICashierService cashierService,
            IApiConfigService apiConfigService,
            IOrderService orderService,
            IOrderDetailService orderDetailService,
            IInterfaceService interfaceService,
            PluginLoader pluginLoader,
            RedisDelayQueueClient redisDelayQueueClient) {
        this.cashierService = cashierService;
        this.apiConfigService = apiConfigService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.interfaceService = interfaceService;
        this.pluginLoader = pluginLoader;
        this.redisDelayQueueClient = redisDelayQueueClient;
    }

    @RequestMapping
    public String create(@Valid CashierInputDTO inputDTO, HttpServletRequest request) throws Exception {
        Merchant merchant = OpenAPISecurityUtils.getSubject();
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        Pattern wxBrowserPattern = Pattern.compile("micromessenger");
        Pattern aliBrowserPattern = Pattern.compile("alipayclient");
        Matcher wxBrowserMatcher = wxBrowserPattern.matcher(userAgent);
        Matcher aliBrowserMatcher = aliBrowserPattern.matcher(userAgent);
        String productCode = "wx_jsapi";
        if (wxBrowserMatcher.matches()){
            productCode = "wx_jsapi";
        }else if(aliBrowserMatcher.matches()){
            productCode = "ali_jsapi";
        }
        PayOrder payOrder = cashierService.createCashierByInput(productCode, inputDTO, merchant);
        if (productCode.equals("wx_jsapi")){
            String timestamp = String.valueOf(System.currentTimeMillis());
            String redirectUrl = String.format("%s/api/v1/cashiers/pay/wx/order?orderNo=%s",
                    webHostname,payOrder.getOrder().getOrderNo());
            ApiConfig apiConfig = apiConfigService.getOneByMerchantId(merchant.getId());
            Map<String,String> reqMap = new HashMap<>();
            reqMap.put("timestamp",timestamp);
            reqMap.put("signType","md5");
            reqMap.put("apiKey",apiConfig.getApiKey());
            reqMap.put("redirectUrl",redirectUrl);
            String req = MapUtil.sortAndSerialize(reqMap);
            SignType signType = new Md5SignType(req);
            SignVerify verify = signType.sign(apiConfig.getApiSecurity());
            String sign = verify.getSign();
            return String.format("redirect:/api/v1/helper/wx/openid?%s&sign=%s",req,sign);
        }
        return null;
    }
    @GetMapping("/pay/wx/order")
    public String payOrder(
            @RequestParam String orderNo,
            @RequestParam String openId,
            ModelMap modelMap,
            HttpServletRequest request,
            HttpServletResponse response){
        logger.info("orderNo: {}, openId: {}",orderNo,openId);
        Order order = orderService.getOneByOrderNo(orderNo);
        OrderDetail orderDetail = orderDetailService.getOneByOrderNo(orderNo);
        if (order == null || orderDetail == null || order.getStatus() != 1 ){
            response.setStatus(404);
            return null;
        }
        Map<String,Object> upstreamExtra = new LinkedHashMap<>();
        upstreamExtra.put("openId",openId);
        String upstreamExtraJson = gson.toJson(upstreamExtra);
        orderDetail.setUpstreamExtra(upstreamExtraJson);
        orderDetailService.updateById(orderDetail);
        String notifyReceiveUrl = String.format(
                "%s/api/v1/invoices/%s/callback",
                webHostname,order.getOrderNo());
        PayOrder payOrder = new PayOrder();
        payOrder.setOrder(order);
        payOrder.setOrderDetail(orderDetail);
        payOrder.setNotifyReceiveUrl(notifyReceiveUrl);
        Interface interfaces = interfaceService.getById(orderDetail.getPayInterfaceId());
        Map<String,Object> results;
        try {
            Plugin<PayOrder> payOrderPlugin = pluginLoader.loadForClassPath(interfaces.getInterfaceImpl());
            PayOrderFlow payOrderFlow = new PayOrderFlow(payOrder);
            payOrderPlugin.apply(payOrderFlow);
            payOrderFlow.execDependent("create");
            results = payOrderFlow.getResults();
        } catch (Exception e) {
            response.setStatus(404);
            return null;
        }
        String wxMpAppId = "wx2aeda339f56138bf";
        String wxMpSecret = "731787f51247a33c4ff210cd613dd780";
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(wxMpAppId);
        config.setSecret(wxMpSecret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        String currentUrl = request.getRequestURL().append("?")
                .append(request.getQueryString()).toString();
        WxJsapiSignature wxConfig;
        try {
            wxConfig = wxMpService.createJsapiSignature(currentUrl);
        } catch (WxErrorException e) {
            e.printStackTrace();
            response.setStatus(404);
            return null;
        }
        modelMap.put("order",order);
        modelMap.put("orderAmountDisplay", NumberUtil.amountFen2Yuan(order.getAmount()));
        modelMap.put("payAttr",results);
        modelMap.put("wxConfig",wxConfig);
        return "cashier/wx";
    }
}
