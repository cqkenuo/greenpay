package com.esiran.greenpay.merchant.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/merchant/api/v1")
public class ApiMerchantOrderController {


    private final IOrderService payOrderService;
    private final IExtractService extractService;


    public ApiMerchantOrderController(IOrderService payOrderService, IExtractService extractService) {
        this.payOrderService = payOrderService;
        this.extractService = extractService;
    }

    @GetMapping("/orders")
    public IPage<Order> orderList(Page<Order> page){
        return payOrderService.page(page);
    }
    @GetMapping("/extracts")
    public IPage<Extract> extractList(Page<Extract> page){
        return extractService.page(page);
    }

    @PostMapping("/payextract")
    public Map payExtract(){
        Map m = new HashMap<String,String>();
        m.put("code","1");
        m.put("msg","提交成功");
        return m;
    }

}
