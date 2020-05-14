package com.esiran.greenpay.admin.controller.agentpay;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.agentpay.entity.AgentPayOrder;
import com.esiran.greenpay.agentpay.entity.AgentPayOrderDTO;
import com.esiran.greenpay.agentpay.service.IAgentPayOrderService;
import com.esiran.greenpay.pay.entity.OrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agentpay/orders")
public class APIAdminAgentPayOrderController {

    private final IAgentPayOrderService agentPayOrderService;

    public APIAdminAgentPayOrderController(IAgentPayOrderService agentPayOrderService) {
        this.agentPayOrderService = agentPayOrderService;
    }

    @GetMapping
    public IPage<AgentPayOrderDTO> list(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size){
        return agentPayOrderService.selectPage(new Page<>(current,size),null);
    }
}
