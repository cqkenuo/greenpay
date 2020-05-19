package com.esiran.greenpay.merchant.entity;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import com.esiran.greenpay.common.util.PatternUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel("MerchantInput")
public class MerchantInputDTO {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = PatternUtil.REGEXP_USERNAME, message = "用户名格式校验失败")
    private String username;
    @ApiModelProperty("商户名称")
    @NotBlank(message = "商户名称不能为空")
    private String name;
    @ApiModelProperty("电子邮箱")
    @NotBlank(message = "电子邮箱不能为空")
    @Pattern(regexp = PatternUtil.REGEXP_EMAIL, message = "邮箱格式校验失败")
    private String email;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("状态")
    private Boolean status;
    @ApiModelProperty("密码")
    @Size(min = 32,max = 32,message = "密码格式错误")
    @NotBlank(message = "密码不能为空")
    private String password;

}
