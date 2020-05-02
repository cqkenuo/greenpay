package com.esiran.greenpay.pay.entity;

import lombok.Data;

@Data
public class PayOrder {
    public String notifyReceiveUrl;
    private Order order;
    private OrderDetail orderDetail;
}
