package com.esiran.greenpay.merchant.entity;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@ApiModel(value = "Merchant",description = "商户")
public class MerchantDTO {
    @ApiModelProperty("商户ID")
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("商户名称")
    private String name;
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("联系手机")
    private String phone;
    @ApiModelProperty("状态（0：关闭，1：开启）")
    private Boolean status;
    private PayAccountDTO payAccount;
    private PrepaidAccountDTO prepaidAccount;
    private SettleAccountDTO settleAccountDTO;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;
}
