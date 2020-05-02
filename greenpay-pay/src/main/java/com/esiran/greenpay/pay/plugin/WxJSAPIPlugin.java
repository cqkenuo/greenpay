package com.esiran.greenpay.pay.plugin;

import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.actuator.entity.Task;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDetail;
import com.esiran.greenpay.pay.entity.PayOrder;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class WxJSAPIPlugin implements Plugin<PayOrder> {
    private static final class CreateOrderTask implements Task<PayOrder> {
        private static final Gson gson = new Gson();
        @Override
        public String taskName() {
            return "createWxJSAPIOrder";
        }

        @Override
        public String dependent() {
            return "create";
        }

        private Map<String,Object> buildOutObj(Map<String,Object> config,WxPayUnifiedOrderResult orderResult){
            if (config == null) return null;
            String appId = (String) config.get("appId");
            String mchKey = (String) config.get("mchKey");
            String nonceStr = UUID.randomUUID().toString().replace("-","");
            Long timestamp = System.currentTimeMillis();
            String packageStr = "prepay_id=".concat(orderResult.getPrepayId());
            String signType = "MD5";
            String params = "appId=".concat(appId).concat("&")
                    .concat("nonceStr=").concat(nonceStr).concat("&")
                    .concat("package=").concat(packageStr).concat("&")
                    .concat("signType=").concat(signType).concat("&")
                    .concat("timeStamp=").concat(String.valueOf(timestamp)).concat("&")
                    .concat("key=").concat(String.valueOf(mchKey));
            String paramsMd5 = EncryptUtil.md5(params);
            if (paramsMd5 == null)
                paramsMd5 = "";
            String paySign = paramsMd5.toUpperCase();
            Map<String,Object> out = new LinkedHashMap<>();
            out.put("nonceStr",nonceStr);
            out.put("packageStr",packageStr);
            out.put("signType",signType);
            out.put("timestamp",timestamp);
            out.put("paySign",paySign);
            return out;
        }

        private static WxPayConfig parseWxPayConfig(Map<String,Object> objectMap){
            if (objectMap == null) return null;
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId((String) objectMap.get("appId"));
            payConfig.setMchId((String) objectMap.get("mchId"));
            payConfig.setMchKey((String) objectMap.get("mchKey"));
            return payConfig;
        }
        @Override
        public void action(Flow<PayOrder> flow) throws Exception {
            PayOrder payOrder = flow.getData();
            Order order = payOrder.getOrder();
            OrderDetail orderDetail = payOrder.getOrderDetail();
            String configJson = orderDetail.getPayInterfaceAttr();
            String upstreamExtraJson = orderDetail.getUpstreamExtra();
            Map<String,Object> config = gson.fromJson(configJson,
                    new TypeToken<Map<String,Object>>(){}.getType());
            Map<String,Object> upstreamExtra = gson.fromJson(upstreamExtraJson,
                    new TypeToken<Map<String,Object>>(){}.getType());
            if (config == null)
                throw new Exception("支付接口参数不能为空");
            WxPayConfig wxPayConfig = parseWxPayConfig(config);
            if (upstreamExtra == null)
                throw new Exception("支付接口扩展参数不能为空");
            WxPayService service = new WxPayServiceImpl();
            service.setConfig(wxPayConfig);
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody(order.getSubject());
            orderRequest.setOutTradeNo(order.getOrderNo());
            orderRequest.setTotalFee(order.getAmount());
            orderRequest.setOpenid((String) upstreamExtra.get("openId"));
            orderRequest.setSpbillCreateIp("127.0.0.1");
            orderRequest.setNotifyUrl(payOrder.getNotifyReceiveUrl());
            orderRequest.setTradeType("JSAPI");
            WxPayUnifiedOrderResult result = service.unifiedOrder(orderRequest);
            flow.returns(buildOutObj(config,result));
        }
    }
    @Override
    public void apply(Flow<PayOrder> flow) {
        flow.add(new CreateOrderTask());
    }
}
