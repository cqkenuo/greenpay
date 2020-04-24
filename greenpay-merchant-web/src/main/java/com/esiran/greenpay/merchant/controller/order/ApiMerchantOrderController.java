package com.esiran.greenpay.merchant.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.esiran.greenpay.pay.entity.Extract;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.ReplacepayOrder;
import com.esiran.greenpay.pay.entity.ReplacepayRecharge;
import com.esiran.greenpay.pay.service.IExtractService;
import com.esiran.greenpay.pay.service.IOrderService;
import com.esiran.greenpay.pay.service.IReplacepayOrderService;
import com.esiran.greenpay.pay.service.IReplacepayRechargeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

}
