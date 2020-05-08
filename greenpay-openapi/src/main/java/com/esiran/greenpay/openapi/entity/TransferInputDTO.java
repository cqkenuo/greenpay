package com.esiran.greenpay.openapi.entity;

import lombok.Data;

@Data
public class TransferInputDTO {
    private Integer mchId;
    private Integer amount;
    private Integer fee;
    private String accountName;
    private String accountNumber;
    private String bankName;
    private String bankNumber;
    private String notifyUrl;
    private String extra;
    private Boolean status;
}
