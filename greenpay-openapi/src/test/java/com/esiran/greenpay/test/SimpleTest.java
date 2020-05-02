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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleTest {

    @Test
    public void test(){
        for (int i=0;i<100;i++){
            Random random = new Random();
            int r = random.nextInt(9)+1;
            System.out.println(String.format("随机数: %s",r));
        }
    }

}
