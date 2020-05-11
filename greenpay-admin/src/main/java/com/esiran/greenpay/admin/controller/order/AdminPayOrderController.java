package com.esiran.greenpay.admin.controller.order;

import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.framework.annotation.PageViewHandleError;
import com.esiran.greenpay.merchant.entity.ApiConfigDTO;
import com.esiran.greenpay.merchant.service.IApiConfigService;
import com.esiran.greenpay.pay.entity.OrderDTO;
import com.esiran.greenpay.pay.entity.OrderDetailDTO;
import com.esiran.greenpay.pay.service.IOrderDetailService;
import com.esiran.greenpay.pay.service.IOrderService;
import com.esiran.greenpay.pay.service.impl.OrderNotifyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/order")
public class AdminPayOrderController {
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;


    private final OrderNotifyService orderNotifyService;
    private final IApiConfigService iApiConfigService;
    public AdminPayOrderController(IOrderService orderService, IOrderDetailService orderDetailService, OrderNotifyService orderNotifyService, IApiConfigService iApiConfigService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.orderNotifyService = orderNotifyService;
        this.iApiConfigService = iApiConfigService;
    }


    @GetMapping("/list")
    @PageViewHandleError
    public String list() {
        return "admin/order/list";
    }

    @PostMapping("/list")
    public String notify(@RequestParam String orderNo, @RequestParam Integer mchId) throws PostResourceException {
        if (mchId<=0 || StringUtils.isBlank(orderNo)) {
            throw new PostResourceException("商户ID不正确");
        }

        OrderDTO order = orderService.getByOrderNo(orderNo);
        if (order == null) {
            throw new PostResourceException("订单不存在");
        }

        if (order.getStatus() <= 1) {
            throw new PostResourceException("该订单未支付,无法通知商户");
        }

        //查询商户授权码
        ApiConfigDTO merchant = iApiConfigService.findByMerchantId(mchId);
        if (merchant == null) {
            throw new PostResourceException("未查詢到商戶");
        }
        String signType = "md5";
        String credential = merchant.getApiSecurity();
        String apiPrivKey = merchant.getPrivateKey();
        if (apiPrivKey != null && apiPrivKey.length() > 0){
            signType = "rsa";
            credential = apiPrivKey;
        }
        //发送通知
        boolean b = orderNotifyService.notifyByOrderNo(orderNo, credential,signType);
        if (!b) {
            throw new PostResourceException("通知成功，商户返回值校验失败");
        }
        return "redirect:/admin/order/list";
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
