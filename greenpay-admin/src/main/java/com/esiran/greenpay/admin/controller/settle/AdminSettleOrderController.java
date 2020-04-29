package com.esiran.greenpay.admin.controller.settle;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.admin.controller.CURDBaseController;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.settle.entity.SettleConfig;
import com.esiran.greenpay.settle.entity.SettleConfigInputDTO;
import com.esiran.greenpay.settle.entity.SettleOrderDTO;
import com.esiran.greenpay.settle.service.ISettleConfigService;
import com.esiran.greenpay.settle.service.ISettleOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/settle")
public class AdminSettleOrderController extends CURDBaseController {
    private final ISettleOrderService orderService;
    private final ISettleConfigService settleConfigService;
    public AdminSettleOrderController(
            ISettleOrderService orderService, ISettleConfigService settleConfigService) {
        this.orderService = orderService;
        this.settleConfigService = settleConfigService;
    }

    @GetMapping("/payable")
    public String list(){
        return "admin/settle/payable/list";
    }
    @GetMapping("/audit")
    public String audit(){
        return "admin/settle/audit/list";
    }
    @PostMapping("/audit/{orderNo}/flow/pass")
    public String auditPost(@PathVariable String orderNo){
        orderService.updateOrderStatus(orderNo,2);
        return "redirect:/admin/settle/audit";
    }
    @GetMapping("/settings")
    @PageViewHandleError
    public String settings(ModelMap modelMap, HttpSession httpSession){
        LambdaQueryWrapper<SettleConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.last("LIMIT 1");
        SettleConfig settleConfig = settleConfigService.getOne(queryWrapper);
        modelMap.addAttribute("data", settleConfig);
        return "admin/settle/settings/index";
    }


    @PostMapping("/settings")
    public String settingsPost(@Valid SettleConfigInputDTO dto){
        settleConfigService.update(dto);
        return redirect("/admin/settle/settings");
    }
    @GetMapping("/audit/{orderNo}/detail")
    public String auditDetail(@PathVariable String orderNo, ModelMap modelMap){
        SettleOrderDTO order = orderService.getByOrderNo(orderNo);
        modelMap.addAttribute("order", order);
        return "admin/settle/audit/detail";
    }

    @GetMapping("/payable/{orderNo}/detail")
    public String detail(@PathVariable String orderNo, ModelMap modelMap){
        SettleOrderDTO order = orderService.getByOrderNo(orderNo);
        modelMap.addAttribute("order", order);
        return "admin/settle/payable/detail";
    }
}
