package com.esiran.greenpay.merchant.controller;


import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantInputDTO;
import com.esiran.greenpay.merchant.entity.MerchantUpdateDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
}
