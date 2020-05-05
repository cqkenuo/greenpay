package com.esiran.greenpay.test;

import com.esiran.greenpay.common.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleTest {

    private static final Gson gson = new GsonBuilder().create();
    private long taskRetry(String message){
        Map<String,String> msgObj = MapUtil.jsonString2stringMap(message);
        if (msgObj == null) return -1;
        int count = Integer.parseInt(msgObj.get("count"));
        msgObj.put("count",String.valueOf(count+1));
        return (30*(int) Math.pow(2,count-1))*1000;
    }
    @Test
    public void test(){
//        for (int i=0;i<100;i++){
//            Random random = new Random();
//            int r = random.nextInt(9)+1;
//            System.out.println(String.format("随机数: %s",r));
//        }
//        System.out.println(System.currentTimeMillis());
        long sum = 0;
        for (int i = 0; i<10; i++){
            Map<String,String> messagePayload = new HashMap<>();
            messagePayload.put("orderNo", "123456789");
            messagePayload.put("mchId", "124567890");
            messagePayload.put("count", String.valueOf(i+1));
            long t = taskRetry(gson.toJson(messagePayload));
            System.out.println(String.format("second: %s, minutes: %s, hour: %s",
                    (t/1000.0f), (t/1000.00f/60.00f),(t/1000.00f/60.00f/60.00f) ));
            sum += t;
        }
        float f = sum/1000.00f/60.00f/60.00f;
        System.out.println(f);
    }

}
