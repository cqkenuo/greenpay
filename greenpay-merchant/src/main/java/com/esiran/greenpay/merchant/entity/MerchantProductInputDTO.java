package com.esiran.greenpay.merchant.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 商户产品
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Data
public class MerchantProductInputDTO {
    @ApiModelProperty(value = "支付类型编码",required = true)
    @NotEmpty(message = "支付类型编码不能为空")
    private String payTypeCode;

    @ApiModelProperty(value = "支付产品ID",required = true)
    @NotEmpty(message = "支付产品ID不能为空")
    private Integer productId;

    @ApiModelProperty(value = "产品费率",required = true)
    @NotNull(message = "产品费率不能为空")
    @Min(value = 0,message = "产品费率必须大于或等于0")
    private BigDecimal rate;

    @ApiModelProperty(value = "状态",required = true)
    @NotNull(message = "状态不能为空")
    private Boolean status;
}
