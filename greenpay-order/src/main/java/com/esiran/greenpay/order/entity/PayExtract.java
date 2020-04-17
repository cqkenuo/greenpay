package com.esiran.greenpay.order.entity;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户提现记录表
 * </p>
 *
 * @author Militch
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PayExtract extends BaseMapperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String extractNo;

    /**
     * 商户id
     */
    private Integer mchId;

    /**
     * 提现金额
     */
    private Integer amount;

    /**
     * 提现状态 0 待审核 ，1提现成功，-1提现失败
     */
    private Integer status;


    /**
     * 结束时间
     */
    private LocalDateTime endAt;


}
