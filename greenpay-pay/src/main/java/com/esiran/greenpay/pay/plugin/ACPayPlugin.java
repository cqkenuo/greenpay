package com.esiran.greenpay.pay.plugin;

import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.actuator.entity.Task;
import com.esiran.greenpay.common.entity.APIException;
import com.esiran.greenpay.common.sign.Md5SignType;
import com.esiran.greenpay.common.sign.SignType;
import com.esiran.greenpay.common.util.MapUtil;
import com.esiran.greenpay.common.util.OkHttpUtil;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDetail;
import com.esiran.greenpay.pay.entity.PayOrder;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ACPayPlugin implements Plugin<PayOrder> {
    private static final OkHttpClient okHttpClient;
    private static final Logger logger = LoggerFactory.getLogger(UpacpQrJKPlugin.class);
    static {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Duration.ofSeconds(180))
                .writeTimeout(Duration.ofSeconds(180))
                .connectTimeout(Duration.ofSeconds(180))
                .callTimeout(Duration.ofSeconds(180))
                .build();
    }

    private static final class CreateOrderTask implements Task<PayOrder>{

        @Override
        public String taskName() {
            return "createOrderTask";
        }

        @Override
        public String dependent() {
            return "creat";
        }

        @Override
        public void action(Flow<PayOrder> flow) throws Exception {
            System.out.println("付款码支付");
            PayOrder payOrder = flow.getData();
            Order order = payOrder.getOrder();
            OrderDetail orderDetail = payOrder.getOrderDetail();
            String attrJson = orderDetail.getPayInterfaceAttr();
            Map<String, String> attrMap = MapUtil.jsonString2stringMap(attrJson);
            if (attrMap == null) throw new APIException("请求参数有误","CHANNEL_REQUEST_ERROR");
            String memberId = attrMap.get("memberId");
            String apiClientPrivKey = attrMap.get("apiClientPrivKey");
            Map<String,String> map = new HashMap<>();
            map.put("authCode",orderDetail.getUpstreamExtra());
            map.put("orderAmt",String.valueOf(order.getAmount()));
            map.put("memberId",memberId);
            String principal = MapUtil.sortAndSerialize(map);
            Md5SignType signType = new Md5SignType(principal);
            String sign = signType.sign2(apiClientPrivKey);
            map.put("sign",sign);
            logger.info("sign: {}",sign);
            logger.info("apiPrivKey: {}",apiClientPrivKey);
            logger.info("Request: {}",MapUtil.sortAndSerialize(map));
            FormBody formBody = OkHttpUtil.map2fromBody(map);
            Request request = new Request.Builder()
                    .url("http://localhost:8601/alipayCreditPay/payFromOutside")
                    .post(formBody)
                    .build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new APIException("支付渠道请求失败","CHANNEL_REQUEST_ERROR");
                String body = responseBody.string();
                logger.info("Response body: {}", body);
                if (StringUtils.isEmpty(body)) throw new APIException("支付渠道请求失败","CHANNEL_REQUEST_ERROR");
                Pattern pattern = Pattern.compile("^<form .+$",Pattern.DOTALL);
                Matcher matcher = pattern.matcher(body);
//                body = new String(Base64.getEncoder().encode(body.getBytes(StandardCharsets.UTF_8)));
                if (!matcher.matches()) throw new APIException("支付渠道请求失败","CHANNEL_REQUEST_ERROR");
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("resultUrl",null);
                objectMap.put("resultBody",body);
                flow.returns(objectMap);
            }
        }
    }
    private static final class OrderNotifyTask implements Task<PayOrder> {

        @Override
        public String taskName() {
            return "orderNotifyTask";
        }

        @Override
        public String dependent() {
            return "notify";
        }

        @Override
        public void action(Flow<PayOrder> flow) throws Exception {
        }
    }

    @Override
    public void apply(Flow<PayOrder> flow) {
        flow.add(new CreateOrderTask());
    }
}
