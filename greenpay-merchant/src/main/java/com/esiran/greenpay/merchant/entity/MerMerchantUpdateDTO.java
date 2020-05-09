package com.esiran.greenpay.merchant.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel(value = "MerchantUpdate",description = "商户")
public class MerMerchantUpdateDTO {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("商户名称")
    @NotBlank(message = "商户名称不能为空")
    private String name;
    @ApiModelProperty("电子邮箱")
    @NotBlank(message = "电子邮箱不能为空")
    private String email;
    @ApiModelProperty("联系手机")
    @NotBlank(message = "联系手机不能为空")
    private String phone;
}
