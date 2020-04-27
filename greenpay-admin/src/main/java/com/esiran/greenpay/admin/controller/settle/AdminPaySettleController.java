package com.esiran.greenpay.admin.controller.settle;

import com.esiran.greenpay.pay.entity.OrderDTO;
import com.esiran.greenpay.pay.entity.OrderDetailDTO;
import com.esiran.greenpay.pay.service.IOrderDetailService;
import com.esiran.greenpay.pay.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/settle")
public class AdminPaySettleController {
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    public AdminPaySettleController(
            IOrderService orderService,
            IOrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/list")
    public String list(){
        return "admin/settle/list";
    }
    @GetMapping("/audit")
    public String audit(){
        return "admin/settle/audit";
    }

    @GetMapping("/list/{orderNo}/detail")
    public String detail(@PathVariable String orderNo, ModelMap modelMap){
        OrderDTO order = orderService.getByOrderNo(orderNo);
        OrderDetailDTO orderDetail = orderDetailService.getByOrderNo(orderNo);
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("orderDetail", orderDetail);
        return "admin/order/detail";
    }
}
