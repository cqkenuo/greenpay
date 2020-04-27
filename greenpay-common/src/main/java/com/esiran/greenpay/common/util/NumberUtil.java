package com.esiran.greenpay.common.util;

import java.math.BigDecimal;

public class NumberUtil {
    public static String amountFen2Yuan(Integer fen){
        if (fen == null) return null;
        BigDecimal amount = new BigDecimal(fen);
        BigDecimal amountUnit = new BigDecimal(100);
        BigDecimal amountDisplay = amount.divide(amountUnit,2,BigDecimal.ROUND_HALF_UP);
        return String.format("%.2f",amountDisplay.floatValue());
    }
    public static String tenThousandPercent(BigDecimal percent){
        if (percent == null) return null;
        BigDecimal unit = new BigDecimal(100);
        BigDecimal amountDisplay = percent.multiply(unit);
        return String.format("%.2f",amountDisplay.floatValue());
    }
}
