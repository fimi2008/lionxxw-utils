package com.lionxxw.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * BigDecimal 计算工具类
 */
public class MoneyCalculateUtils {

    /**
     * 实现浮点数的加法运算功能
     *
     * @param v1      加数1
     * @param v2      加数2
     * @param decimal 保留小数位数
     * @return v1+v2的和
     */
    public static String add(String v1, String v2, int decimal) {
        DecimalFormat df = new DecimalFormat(getFormat(decimal)); //格式化，保留两位小数
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return df.format(b1.add(b2)).toString();
    }

    /**
     * 实现浮点数的减法运算功能
     *
     * @param v1 被减数
     * @param v2 减数
     * @return v1-v2的差
     */
    public static String sub(String v1, String v2, int decimal) {
        DecimalFormat df = new DecimalFormat(getFormat(decimal)); //格式化，保留两位小数
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return df.format(b1.subtract(b2)).toString();
    }

    /**
     * 实现浮点数的乘法运算功能
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return v1×v2的积
     */
    public static String multi(String v1, String v2, int decimal) {
        DecimalFormat df = new DecimalFormat(getFormat(decimal)); //格式化，保留两位小数
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return df.format(b1.multiply(b2)).toString();
    }

    public static String multiAmount(String v1, String v2) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return formater.format(b1.multiply(b2)).toString();
    }

    /**
     * 实现浮点数的除法运算功能
     * 当发生除不尽的情况时，精确到小数点以后scale位，后面的位数进行四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return v1/v2的商
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的小数位四舍五入功能
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 获取格式化样式
     *
     * @param decimal 保留小数位数
     * @return
     */
    private static String getFormat(int decimal) {
        StringBuffer returnStr = new StringBuffer("0");

        if (decimal > 0) {
            returnStr.append(".");
        }

        for (int i = 0; i < decimal; i++) {
            returnStr.append("0");
        }

        return returnStr.toString();
    }
}