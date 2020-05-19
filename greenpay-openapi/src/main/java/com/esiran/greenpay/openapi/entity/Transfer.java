package com.esiran.greenpay.openapi.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transfer {
    private String orderNo;
    private String orderSn;
    private Integer mchId;
    private Integer amount;
    private Integer fee;
    private Integer accountType;
    private String accountName;
    private String accountNumber;
    private String bankName;
    private String bankNumber;
    private String notifyUrl;
    private String extra;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
