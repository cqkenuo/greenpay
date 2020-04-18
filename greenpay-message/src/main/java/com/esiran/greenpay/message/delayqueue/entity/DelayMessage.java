package com.esiran.greenpay.message.delayqueue.entity;

import lombok.Data;

@Data
public class DelayMessage {
    private String messageId;
    private String content;
    private Long delayTime;
}
