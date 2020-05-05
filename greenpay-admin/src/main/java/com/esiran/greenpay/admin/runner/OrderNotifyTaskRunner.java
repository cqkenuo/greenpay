package com.esiran.greenpay.admin.runner;

import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.util.MapUtil;
import com.esiran.greenpay.merchant.entity.ApiConfig;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.merchant.service.IPayAccountService;
import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import com.esiran.greenpay.message.delayqueue.impl.RedisDelayQueueClient;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.service.IOrderNotifyService;
import com.esiran.greenpay.pay.service.IOrderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderNotifyTaskRunner implements DelayQueueTaskRunner {
    private final Logger logger = LoggerFactory.getLogger(OrderNotifyTaskRunner.class);
    private final IOrderNotifyService orderNotifyService;
    private final IApiConfigService apiConfigService;
    private final IOrderService orderService;
    private final RedisDelayQueueClient delayQueueClient;
    private static final Gson gson = new GsonBuilder().create();
    private final IPayAccountService payAccountService;
    public OrderNotifyTaskRunner(
            IOrderNotifyService orderNotifyService,
            IApiConfigService apiConfigService,
            IOrderService orderService,
            RedisDelayQueueClient delayQueueClient,
            IPayAccountService payAccountService) {
        this.orderNotifyService = orderNotifyService;
        this.apiConfigService = apiConfigService;
        this.orderService = orderService;
        this.delayQueueClient = delayQueueClient;
        this.payAccountService = payAccountService;
    }
    private void taskRetry(String message){
        Map<String,String> msgObj = MapUtil.jsonString2stringMap(message);
        if (msgObj == null) return;
        int count = Integer.parseInt(msgObj.get("count"));
        if (count <= 10){
            msgObj.put("count",String.valueOf(count+1));
            long delayTime = (30*(int) Math.pow(2,count-1))*1000;
            delayQueueClient.sendDelayMessage("order:notify",gson.toJson(msgObj),delayTime);
        }
    }
    @Override
    public void exec(String content){
        Map<String,String> msgObj = MapUtil.jsonString2stringMap(content);
        if (msgObj == null) return;
        String orderNo = msgObj.get("orderNo");
        String count = msgObj.get("count");
        logger.info("Handle order notify, orderNo: {}, handle count: {}",orderNo,count);
        Order order = orderService.getOneByOrderNo(orderNo);
        if (order == null) return;
        Integer status = order.getStatus();
        if (status != 2) return;
        int amount = order.getAmount();
        int fee = order.getFee();
        int total = amount - fee;
        payAccountService.updateBalance(order.getMchId(),-total,0);
        orderService.updateOrderStatus(orderNo,3);
        ApiConfig apiConfig = apiConfigService.getOneByMerchantId(order.getMchId());
        if (apiConfig == null) return;
        try {
            boolean ok = orderNotifyService.notifyByOrderNo(order.getOrderNo(),apiConfig.getApiSecurity());
            if (!ok) taskRetry(content);
        } catch (PostResourceException e) {
            e.printStackTrace();
        }
    }
}
