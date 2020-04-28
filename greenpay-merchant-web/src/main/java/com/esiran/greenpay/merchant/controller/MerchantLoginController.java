package com.esiran.greenpay.merchant.controller;

import com.esiran.greenpay.merchant.entity.MerchantLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Map loginInput(@RequestBody MerchantLogin merchantLogin){
    return null;
    }
}
