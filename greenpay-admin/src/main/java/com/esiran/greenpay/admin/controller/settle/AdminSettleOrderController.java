package com.esiran.greenpay.admin.controller.settle;

import com.esiran.greenpay.settle.entity.SettleOrderDTO;
import com.esiran.greenpay.settle.service.ISettleOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/settle")
public class AdminSettleOrderController {
    private final ISettleOrderService orderService;
    public AdminSettleOrderController(
            ISettleOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/settle/list/list";
    }
    @GetMapping("/audit")
    public String audit(){
        return "admin/settle/audit/list";
    }

    @GetMapping("/audit/{orderNo}/detail")
    public String auditDetail(@PathVariable String orderNo, ModelMap modelMap){
        SettleOrderDTO order = orderService.getByOrderNo(orderNo);
        modelMap.addAttribute("order", order);
        return "admin/settle/audit/detail";
    }

    @GetMapping("/list/{orderNo}/detail")
    public String detail(@PathVariable String orderNo, ModelMap modelMap){
        SettleOrderDTO order = orderService.getByOrderNo(orderNo);
        modelMap.addAttribute("order", order);
        return "admin/settle/list/detail";
    }
}
