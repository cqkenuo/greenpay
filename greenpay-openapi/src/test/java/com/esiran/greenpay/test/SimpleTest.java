package com.esiran.greenpay.test;

import com.esiran.greenpay.common.util.*;
import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleTest {
    public final List<Map<String,Object>> availableList = new ArrayList<>();
    private final Random random = new Random();
    private int[] sumArr;
    private static Map<String,Object> createItem(String name, int weight){
        Map<String,Object> item = new HashMap<>();
        item.put("weight",weight);
        item.put("name",name);
        return item;
    }
    @Before
    public void before(){
        availableList.add(createItem("wx_jsapi_1",11));
        availableList.add(createItem("wx_jsapi_2",0));
        availableList.add(createItem("wx_jsapi_3",0));
        availableList.add(createItem("wx_jsapi_4",0));
        availableList.add(createItem("wx_jsapi_5",0));
        availableList.add(createItem("wx_jsapi_6",0));
        availableList.add(createItem("wx_jsapi_7",0));
        availableList.add(createItem("wx_jsapi_8",0));
        availableList.add(createItem("wx_jsapi_9",0));
        availableList.add(createItem("wx_jsapi_10",0));
    }


    public void init(){
        sumArr = new int[availableList.size()];
        int sum = 0;
        for (int i=0;i<availableList.size();i++){
            Map<String,Object> item = availableList.get(i);
            int weight = (int) item.get("weight");
            sum += weight+1;
            sumArr[i] = sum;
        }
    }

    public void sum(){
        sumArr = new int[availableList.size()];
        int sum = 0;
        for (int i=0;i<availableList.size();i++){
            Map<String,Object> item = availableList.get(i);
            int weight = (int) item.get("weight");
            sum += weight;
        }
        System.out.println(sum);
    }

    public void sort(){
        int len = availableList.size();
        for (int i=1; i<len; i++){
            for (int j=0; j<len - i; j++) {
                Map<String,Object> itemJ = availableList.get(j);
                Map<String,Object> itemJ1 = availableList.get(j+1);
                int weightJ = (int) itemJ.get("weight");
                int weightJ1 = (int) itemJ1.get("weight");
                if (weightJ > weightJ1){
                    availableList.set(j,itemJ1);
                    availableList.set(j+1,itemJ);
                }
            }
        }
    }

    public int pick(){
        int len = sumArr.length;
        int bound = sumArr[len-1];
        int val = random.nextInt(bound)+1;
        System.out.println("随机数: bound：" + bound);
        System.out.println("随机数：" + val);
        int left = 0, right = len-1, mid;
        while (left < right){
            mid = (right - left) / 2 + left;
            if (sumArr[mid] == val){
                return mid;
            }else if(sumArr[mid] > val){
                right = mid;
            }else {
                left = mid + 1;
            }
        }
        return left;
    }
    @Test
    public void test() throws Exception {
        sort();
        sum();
        init();
        int index = pick();
        System.out.println("索引：" + index);
        Map<String,Object> item = availableList.get(index);
        int weight = (int) item.get("weight");
        String name = (String) item.get("name");
        System.out.println("名称：" + name);
        System.out.println("权重：" + weight);
//        assert (1 == 0);
    }

}
