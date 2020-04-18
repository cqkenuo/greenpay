package com.esiran.greenpay.message.delayqueue;

public interface DelayQueueConsumer {
    String poll(String key, long timeout);
}
