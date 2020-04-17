package com.esiran.greenpay.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MerchantOrderDTO implements Serializable {

    private String payOrderId;

    private String mchOrderNo;

    private Integer status;

    private LocalDate createTimeStart;

    private LocalDate createTimeEnd;
}
