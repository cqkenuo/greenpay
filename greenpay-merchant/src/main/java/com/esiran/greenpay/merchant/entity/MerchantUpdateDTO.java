package com.esiran.greenpay.merchant.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel(value = "MerchantUpdate",description = "商户")
public class MerchantUpdateDTO {
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
}
