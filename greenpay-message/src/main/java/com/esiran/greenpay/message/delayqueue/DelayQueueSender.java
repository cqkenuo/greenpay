package com.esiran.greenpay.message.delayqueue;

public interface DelayQueueSender {
    boolean sendDelayMessage(String key, String message, long delayTime);
}
