package com.esiran.greenpay.runner;

import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import com.esiran.greenpay.message.delayqueue.annotation.DelayQueueExecuteTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@Component
public class OrderDelayQueueTaskRunner implements DelayQueueTaskRunner {
    private final Logger logger = LoggerFactory.getLogger(OrderDelayQueueTaskRunner.class);
    private int i = 0;
    @Override
    public void exec(String content){
        long id = Thread.currentThread().getId();
        String name = Thread.currentThread().getName();
        final int a = i;
        i += 1;
        logger.info("Exec running [{}]: id: {}, name: {}, content: {}: ",a, id, name, content);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("exec running [{}]: id: {}, name: {}, content: {}",a, id, name, content);
    }
}
