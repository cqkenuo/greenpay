package com.esiran.greenpay.merchant.controller;

import com.esiran.greenpay.merchant.entity.MerchantLogin;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Controller
@RequestMapping("/merchant")
public class MerchantLoginController {
    @GetMapping()
    public String login(){
        return "merchant/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public void loginInput(@Validated MerchantLogin merchantLogin){
         String password = merchantLogin.getPassword();
        System.out.println(password);
//        UsernamePasswordToken token = new UsernamePasswordToken(merchantLogin.getUsername(), merchantLogin.getPassword());
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(token);
    }
}
