package com.esiran.greenpay.merchant.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchant/order")
public class MerchantOrderController {

    @GetMapping("/list")
    public String orderList(){
        return "merchant/order/list";
    }
    @GetMapping("/extract")
    public String extracts(){
        return "merchant/order/extract";
    }
}
