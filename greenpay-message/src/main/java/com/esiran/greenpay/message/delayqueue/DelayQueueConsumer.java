package com.esiran.greenpay.message.delayqueue;

import com.esiran.greenpay.message.delayqueue.entity.DelayMessage;

public interface DelayQueueConsumer {
    String poll(String key, long timeout);
    DelayMessage poll(long timeout);
    void close();
}
