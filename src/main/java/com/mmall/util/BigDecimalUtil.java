package com.mmall.util;

import java.math.BigDecimal;

/**
 * 使用BigDecimal的String构造器
 * Created by Administrator on 2018/3/11 0011.
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {}

    /**
     * 加法
     * @param d1
     * @param d2
     */
    public static BigDecimal add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2);
    }

    /**
     * 减法
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2);
    }

    /**
     * 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2);
    }

    public static BigDecimal div(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        // 四舍五入，保留两位小数点
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

}
