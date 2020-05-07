package com.esiran.greenpay.openapi.entity;

import lombok.Data;

@Data
public class Transfer {
    private String orderNo;
    private String orderSn;
    private Integer mchId;
    private Integer amount;
    private Integer fee;
    private String accountName;
    private String accountNumber;

    private String bankName;
    private String bankNumber;
    private String notifyUrl;
    private String extra;
    /**
     * 订单状态（1：待处理，2：处理中，3：处理成功，4：处理失败）
     */
    private Boolean status;
}
