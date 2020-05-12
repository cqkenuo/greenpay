package com.esiran.greenpay.pay.entity;

import com.esiran.greenpay.common.util.NumberUtil;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 支付订单
 * </p>
 *
 * @author Militch
 * @since 2020-04-14
 */
@Data
public class OrderQueryDTO {
    private String orderNo;
    private String orderSn;
    private Integer mchId;
    private String subject;
    private String outOrderNo;
    private Integer status;
    private String startTime;
    private String endTime;
}
