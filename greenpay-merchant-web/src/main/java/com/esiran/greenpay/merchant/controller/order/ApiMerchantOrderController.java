package com.esiran.greenpay.merchant.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.order.entity.PayExtract;
import com.esiran.greenpay.order.entity.PayOrder;
import com.esiran.greenpay.order.service.IPayExtractService;
import com.esiran.greenpay.order.service.IPayOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant/api/v1")
public class ApiMerchantOrderController {


    private final IPayOrderService payOrderService;
    private final IPayExtractService payExtractService;

    public ApiMerchantOrderController(IPayOrderService payOrderService, IPayExtractService payExtractService) {
        this.payOrderService = payOrderService;
        this.payExtractService = payExtractService;
    }

    @GetMapping("/orders")
    public IPage<PayOrder> orderList(Page<PayOrder> page){
        return payOrderService.page(page);
    }
    @GetMapping("/extracts")
    public IPage<PayExtract> extractList(Page<PayExtract> page){
        return payExtractService.page(page);
    }
}
