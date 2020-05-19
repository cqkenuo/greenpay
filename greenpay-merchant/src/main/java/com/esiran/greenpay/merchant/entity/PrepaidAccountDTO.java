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
 * 商户预充值账户
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Data
@ApiModel(value = "PrepaidAccount",description = "预付款账户")
public class PrepaidAccountDTO {
    @ApiModelProperty("可用余额")
    private Integer availBalance;
    @ApiModelProperty("冻结金额")
    private Integer freezeBalance;
    @ApiModelProperty("可用余额，格式化")
    private String availBalanceDisplay;
    @ApiModelProperty("冻结金额，格式化")
    private String freezeBalanceDisplay;
}
