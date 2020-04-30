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

    private Integer calculateOrderFee(Integer amount, BigDecimal rate){
        if (amount == null || rate == null)
            return null;
        BigDecimal amountReal = new BigDecimal(amount);
        BigDecimal rateUnit = new BigDecimal(100);
        BigDecimal feeRateReal = rate.divide(rateUnit, 4, RoundingMode.HALF_UP);
        BigDecimal fee = amountReal.multiply(feeRateReal);
        return fee.intValue();
    }
    @Test
    public void test(){
        int fee = calculateOrderFee(100,new BigDecimal("10"));
        System.out.println(fee);
    }

}
