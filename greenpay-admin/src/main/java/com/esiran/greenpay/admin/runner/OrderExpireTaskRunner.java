package com.esiran.greenpay.admin.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderExpireTaskRunner implements DelayQueueTaskRunner {
    private final Logger logger = LoggerFactory.getLogger(OrderExpireTaskRunner.class);
    private final IOrderService orderService;

    public OrderExpireTaskRunner(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void exec(String content){
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.set(Order::getStatus, -1)
                .set(Order::getExpiredAt, LocalDateTime.now())
                .eq(Order::getOrderNo,content)
                .eq(Order::getStatus,1);
        orderService.update(orderUpdateWrapper);
    }
}
