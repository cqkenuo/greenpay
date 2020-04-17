package com.esiran.greenpay.merchant.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.service.IOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant/api/v1")
public class ApiMerchantOrderController {


    private final IOrderService payOrderService;


    public ApiMerchantOrderController(IOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }


    @GetMapping("/orders")
    public IPage<Order> orderList(Page<Order> page){
        return payOrderService.page(page);
    }
//    @GetMapping("/extracts")
//    public IPage<PayExtract> extractList(Page<PayExtract> page){
//        return payExtractService.page(page);
//    }
}
