package com.esiran.greenpay.pay.plugin;

import com.esiran.greenpay.actuator.Plugin;
import com.esiran.greenpay.actuator.entity.Flow;
import com.esiran.greenpay.actuator.entity.Task;
import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.pay.entity.Order;
import com.esiran.greenpay.pay.entity.OrderDetail;
import com.esiran.greenpay.pay.entity.PayOrder;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class UpacpQrPlugin implements Plugin<PayOrder> {
    private static final class CreateOrderTask implements Task<PayOrder> {
        @Autowired
        private PoolingHttpClientConnectionManager pool;
        @Override
        public String taskName() {
            return "createOrderTask";
        }

        @Override
        public String dependent() {
            return "create";
        }

        public String map2sign(Map<String,String> map,String Md5key){
            if (map == null) {
                map = new HashMap<>();
            }
            List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            list.sort(new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : list) {
                String k = entry.getKey();
                Object v = entry.getValue();
                if (null != v && !"".equals(v)
                        && !"sign".equals(k)) {
                    sb.append("&").append(k).append("=").append(v);
                }
            }
            String parms = sb.toString().substring(1).concat("&").concat(Md5key);
            return EncryptUtil.md5(parms);
        }

        public String orderTime(){
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            return dtf.format(time);
        }

        public CloseableHttpResponse post(Map<String ,String> paramsMap, String url) throws IOException {
            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool).build();
            HttpPost httpPost = new HttpPost(url);
            //创建参数List集合,并封装参数
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (String key : paramsMap.keySet()) {
                if (StringUtils.isNotEmpty(paramsMap.get(key))){
                    nameValuePairs.add(new BasicNameValuePair(key,paramsMap.get(key)));
                }
            }
            //创建表单的Entity对象,第一个参数是封装号的表单数据,第二个参数是编码
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs, "utf8");
            //设置表单的Entity对象到Post请求中
            httpPost.setEntity(formEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println("返回参数："+ response);
            return null;
        }

        @Override
        public void action(Flow<PayOrder> flow) throws Exception {
            System.out.println("银联扫码");
            PayOrder payOrder = flow.getData();
            Order order = payOrder.getOrder();
            OrderDetail orderDetail = payOrder.getOrderDetail();
            String attr = orderDetail.getPayInterfaceAttr();
            Map<String,String> map = new HashMap<>();
            map.put("service","bankPay");//接口名称

            map.put("merchantNo","");//商户编号
            map.put("bgUrl",payOrder.getNotifyReceiveUrl());//服务器接收支付结果的 后台地址
            map.put("version","V2.0");//网关版本

            map.put("payChannelCode","");//支付通道编码
            map.put("payChannelType","1");//支付通道类型
            map.put("orderNo",order.getOutOrderNo());//商户订单号
            map.put("orderAmount",String.valueOf(order.getAmount()));//商户订单金额
            map.put("curCode","CNY");//交易币种
            map.put("orderTime",orderTime());//订单时间

            map.put("orderSource","");//订单来源
            map.put("signType","MD5 ");//签名类型
            String sign = map2sign(map,"");
            map.put("sign",sign);
            post(map,"http://cashier.exilenttextile.com/bankPay");
//            flow.returns(map);
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
