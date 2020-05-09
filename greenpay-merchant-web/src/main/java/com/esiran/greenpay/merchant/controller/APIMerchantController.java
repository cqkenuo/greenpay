package com.esiran.greenpay.merchant.controller;


import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantInputDTO;
import com.esiran.greenpay.merchant.entity.MerchantUpdateDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/merchant/api/v1")
public class APIMerchantController extends CURDBaseController {


    private final IMerchantService merchantService;

    public APIMerchantController(IMerchantService merchantService) {
        this.merchantService = merchantService;

    }

    @PostMapping("/update")
    public void updateMerchant(@Validated MerchantUpdateDTO merchantUpdateDTO){
        Merchant merchant = theUser();
        BeanUtils.copyProperties(merchantUpdateDTO,merchant);
        merchant.setUpdatedAt(LocalDateTime.now());
        merchantService.updateById(merchant);
    }
    @PostMapping("/updatepassword")
    @PageViewHandleError
    public void updatePassword(@RequestBody
                                    @NotBlank(message = "旧密码不能为空") String oldPassword,
                                    @NotBlank(message = "新密码不能为空") String password,
                               Map<String,String> map ) throws Exception {
        Merchant merchant = theUser();
        if (!merchant.getPassword().equals(oldPassword)){
            throw new PostResourceException("旧密码错误为空");
        }
    }
}
