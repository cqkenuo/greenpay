package com.esiran.greenpay.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.merchant.entity.PayAccount;
import com.esiran.greenpay.merchant.entity.PrepaidAccount;
import com.esiran.greenpay.merchant.service.IPayAccountService;
import com.esiran.greenpay.merchant.service.IPrepaidAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

    private final IPayAccountService payAccountService;
    private final IPrepaidAccountService prepaidAccountService;

    public MerchantController(IPayAccountService payAccountService, IPrepaidAccountService prepaidAccountService) {
        this.payAccountService = payAccountService;
        this.prepaidAccountService = prepaidAccountService;
    }

    @GetMapping("/home")
    public String home(Model model){
        PayAccount payAccount = payAccountService.getOne(new LambdaQueryWrapper<PayAccount>().eq(PayAccount::getMerchantId, 4));
        PrepaidAccount prepaidAccount = prepaidAccountService.getOne(new LambdaQueryWrapper<PrepaidAccount>().eq(PrepaidAccount::getMerchantId, 4));
        model.addAttribute("payAccount",payAccount);
        model.addAttribute("prepaidAccount",prepaidAccount);
        return "merchant/index";
    }
    @GetMapping("/user/profile")
    public String user(){
        return "merchant/user";
    }


}
