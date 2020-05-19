package com.esiran.greenpay.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.esiran.greenpay.common.entity.BaseMapperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "PayAccount",description = "支付账户")
public class PayAccountDTO {
    @ApiModelProperty("可用余额")
    private Integer availBalance;

    @ApiModelProperty("可用余额，格式化")
    private String availBalanceDisplay;

    @ApiModelProperty("冻结金额")
    private Integer freezeBalance;

    @ApiModelProperty("冻结金额，格式化")
    private String freezeBalanceDisplay;
}
