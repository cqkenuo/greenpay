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
        long startTime = System.currentTimeMillis();
        long id = Thread.currentThread().getId();
        String name = Thread.currentThread().getName();
        final int a = i;
        i += 1;
        logger.info("Exec running [{}]: id: {}, name: {}, content: {}: ",a, id, name, content);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("start time: {}, end time: {}, diff: {}", startTime, end, end-startTime);
        logger.info("exec running [{}]: id: {}, name: {}, content: {}",a, id, name, content);
    }
}
