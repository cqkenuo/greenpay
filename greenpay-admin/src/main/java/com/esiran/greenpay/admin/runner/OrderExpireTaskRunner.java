package com.esiran.greenpay.admin.runner;

import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderExpireTaskRunner implements DelayQueueTaskRunner {
    private final Logger logger = LoggerFactory.getLogger(OrderExpireTaskRunner.class);
    private int i = 0;
    @Override
    public void exec(String content){
        System.out.println("执行订单过期逻辑："+ content);
    }
}
