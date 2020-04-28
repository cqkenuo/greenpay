package com.esiran.greenpay.merchant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Map loginInput(){
    return null;
    }
}
