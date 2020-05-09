package com.esiran.greenpay.merchant.controller.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.exception.ResourceNotFoundException;
import com.esiran.greenpay.pay.entity.*;
import com.esiran.greenpay.pay.service.*;
import com.esiran.greenpay.settle.entity.SettleOrderInputDTO;
import com.esiran.greenpay.settle.service.ISettleOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/merchant/api/v1")
public class ApiMerchantOrderController {


    private final IOrderService payOrderService;
    private final IExtractService extractService;
    private final ISettleOrderService settleOrderService;


    public ApiMerchantOrderController(IOrderService payOrderService, IExtractService extractService, ISettleOrderService settleOrderService) {
        this.payOrderService = payOrderService;
        this.extractService = extractService;
        this.settleOrderService = settleOrderService;
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
    public Map payExtract(@Validated  SettleOrderInputDTO settleOrderInputDTO) throws ResourceNotFoundException, PostResourceException {
        settleOrderService.postOrder(settleOrderInputDTO);
        Map m = new HashMap<String,String>();
        m.put("code","1");
        m.put("msg","提交成功");
        return m;
    }

}
