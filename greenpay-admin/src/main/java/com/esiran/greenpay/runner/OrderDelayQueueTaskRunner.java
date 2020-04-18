package com.esiran.greenpay.runner;

import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import com.esiran.greenpay.message.delayqueue.annotation.DelayQueueExecuteTask;
import org.springframework.stereotype.Component;

@Component
public class OrderDelayQueueTaskRunner implements DelayQueueTaskRunner {
    @Override
    public void exec(String content) {
        System.out.println("==================");
        System.out.println(content);
        System.out.println("==================");
    }
}
