package com.esiran.greenpay.merchant.controller.replace;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.agentpay.entity.AgentPayBatchInputDTO;
import com.esiran.greenpay.pay.entity.ReplacepayOrder;
import com.esiran.greenpay.pay.entity.ReplacepayRecharge;
import com.esiran.greenpay.pay.service.IExtractService;
import com.esiran.greenpay.pay.service.IOrderService;
import com.esiran.greenpay.pay.service.IReplacepayOrderService;
import com.esiran.greenpay.pay.service.IReplacepayRechargeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/merchant/api/v1")
public class ApiMerchantReplaceController {
    private final IReplacepayOrderService replacepayOrderService;
    private final IReplacepayRechargeService replacepayRechargeService;


    public ApiMerchantReplaceController(IReplacepayOrderService replacepayOrderService, IReplacepayRechargeService replacepayRechargeService) {
        this.replacepayOrderService = replacepayOrderService;
        this.replacepayRechargeService = replacepayRechargeService;
    }

    @GetMapping("/replaceLists")
    public IPage<ReplacepayOrder> replaceLists(Page<ReplacepayOrder> page){
        return replacepayOrderService.page(page);
    }

    @GetMapping("/recharge")
    public IPage<ReplacepayRecharge> recharges(Page<ReplacepayRecharge> page){
        return replacepayRechargeService.page(page);
    }

    @PostMapping("/replace/add")
    public Map replaceAdd(@Validated ReplacepayOrder replacepayOrder){
        Map m = new HashMap();
        replacepayOrder.setMchId("11111");
        replacepayOrder.setReplaceMoney(replacepayOrder.getReplaceMoney() * 100L);
        replacepayOrder.setCreatedAt(LocalDateTime.now());
        replacepayOrder.setReplaceId(IdWorker.getTimeId());
        replacepayOrder.setUpdatedAt(LocalDateTime.now());
        replacepayOrder.setStatus(0);
        try {
            replacepayOrderService.save(replacepayOrder);
        } catch (Exception e) {
            e.printStackTrace();
            m.put("code",0);
            m.put("msg","提交失败");
            return m;
        }
        m.put("code",1);
        m.put("msg","提交成功");
        return m;
    }
    @PostMapping("/replace/bath")
    public Map bath(@Validated AgentPayBatchInputDTO batchInputDTO){
        return null;
    }

    @PostMapping("/recharge/add")
    public Map rechargeAdd(@Validated ReplacepayRecharge replacepayRecharge){
        Map m = new HashMap();
        replacepayRecharge.setCreatedAt(LocalDateTime.now());
        replacepayRecharge.setUpdatedAt(LocalDateTime.now());
        replacepayRecharge.setStatus(0);
        replacepayRecharge.setRechargeId(IdWorker.getTimeId());
        replacepayRecharge.setMchId("1");
        replacepayRecharge.setRechargeMoney(replacepayRecharge.getRechargeMoney() * 100L);
        try {
            replacepayRechargeService.save(replacepayRecharge);
        } catch (Exception e) {
            e.printStackTrace();
            m.put("code",0);
            m.put("msg","提交失败");
            return m;
        }
        m.put("code",1);
        m.put("msg","提交成功");
        return m;
    }
}
