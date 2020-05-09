package com.esiran.greenpay.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.merchant.entity.Merchant;
import com.esiran.greenpay.merchant.entity.MerchantDetailDTO;
import com.esiran.greenpay.merchant.entity.PayAccount;
import com.esiran.greenpay.merchant.entity.PrepaidAccount;
import com.esiran.greenpay.merchant.entity.UsernamePasswordInputDTO;
import com.esiran.greenpay.merchant.service.IMerchantService;
import com.esiran.greenpay.merchant.service.IPayAccountService;
import com.esiran.greenpay.merchant.service.IPrepaidAccountService;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.service.IOrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.LongStream;

@Controller
@RequestMapping("/merchant")
public class MerchantController extends CURDBaseController{

    private final IPayAccountService payAccountService;
    private final IPrepaidAccountService prepaidAccountService;
    private final IOrderService orderService;
    private final IMerchantService merchantService;

    public MerchantController(IPayAccountService payAccountService, IPrepaidAccountService prepaidAccountService, IOrderService orderService, IMerchantService merchantService) {
        this.payAccountService = payAccountService;
        this.prepaidAccountService = prepaidAccountService;
        this.orderService = orderService;
        this.merchantService = merchantService;
    }

    @GetMapping("/home")
    public String home(Model model){
        Merchant m = theUser();
        PayAccount payAccount = payAccountService.getOne(new LambdaQueryWrapper<PayAccount>().eq(PayAccount::getMerchantId, m.getId()));
        PrepaidAccount prepaidAccount = prepaidAccountService.getOne(new LambdaQueryWrapper<PrepaidAccount>().eq(PrepaidAccount::getMerchantId, m.getId()));
        List<Order> orders = orderService.getByDay(m.getId());
        long count = orders.size();
        long orderMoenyTotal = orders.stream().mapToLong(Order::getAmount).sum();
        long orderMoeny2Total = orders.stream().filter(order -> 2 == order.getStatus()).mapToLong(Order::getAmount).sum();
        long count2 = orders.stream().filter(order -> 2 == order.getStatus()).count();
        model.addAttribute("payAccount",payAccount);
        model.addAttribute("count",count);
        model.addAttribute("count2",count2);
        model.addAttribute("orderMoenyTotal",orderMoenyTotal);
        model.addAttribute("orderMoeny2Total",orderMoeny2Total);
        model.addAttribute("prepaidAccount",prepaidAccount);
        return "merchant/index";
    }
    @GetMapping("/user/profile")
    public String user(Model model){
        Merchant merchant = theUser();
        model.addAttribute("merchant",merchant);
        return "merchant/user";
    }
    @GetMapping("/user/security")
    public String security(Model model){
        Merchant merchant = theUser();
        model.addAttribute("merchant",merchant);
        return "merchant/security";
    }

    @GetMapping("/user/api")
    public String userapi(Model model) {
        Merchant merchant = theUser();
        MerchantDetailDTO merchantDetailDTO = merchantService.findMerchantById(merchant.getId());
        model.addAttribute("merchant", merchantDetailDTO);
        return "merchant/userapi";
    }

    @GetMapping("/login")
    @PageViewHandleError
    public String login() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return redirect("/merchant/home");
        }
        return "merchant/login2";
    }
    @PostMapping("/login")
    public String loginPost(@Valid UsernamePasswordInputDTO inputDTO){
        UsernamePasswordToken token = new UsernamePasswordToken(
                inputDTO.getUsername(),inputDTO.getPassword());
        SecurityUtils.getSubject().login(token);
        return redirect("/merchant/home");
    }
    @PostMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return redirect("/merchant/login");
    }

}
