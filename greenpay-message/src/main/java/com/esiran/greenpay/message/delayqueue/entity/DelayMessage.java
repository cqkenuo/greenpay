package com.esiran.greenpay.message.delayqueue.entity;

import lombok.Data;

@Data
public class DelayMessage {
    private String id;
    private String key;
    private String content;
    private Long delayTime;
}
