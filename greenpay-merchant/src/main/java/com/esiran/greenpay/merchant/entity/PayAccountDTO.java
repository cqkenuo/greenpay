package com.esiran.greenpay.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户支付账户
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Data
public class PayAccountDTO {
    /**
     * 可用余额（分）
     */
    private Integer availBalance;

    /**
     * 冻结余额（分）
     */
    private Integer freezeBalance;
}
