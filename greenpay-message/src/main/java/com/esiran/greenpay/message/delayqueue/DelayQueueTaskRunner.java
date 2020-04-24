package com.esiran.greenpay.message.delayqueue;

public interface DelayQueueTaskRunner {
    void exec(String content);
}
