package com.lionxxw.utils;


import java.util.Date;
import java.util.Random;

/**
 * 随机数生成工具，例如随机生成订单号，流水号等。
 */
public class Generator {

    /**
     * 随机生成指定长度的数字字符串
     * @param length
     * @return
     */
    public static String genRandomNo(int length) throws Exception {
        return genRandomNo("", length);
    }

    /**
     * 根据前缀字符串随机生成指定长度的数字字符串
     * @param length
     * @return
     */
    public static String genRandomNo(String prefixStr, int length) throws Exception {
        if(length <= 0){
            throw new Exception("The value of length is less than 0.");
        }

        if(prefixStr.length() > length) {
            throw new Exception("The length of prefixStr is more than " + length + ".");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(prefixStr);
        builder.append(new Date());

        int curLen = builder.length();

        //当前缀+日期的长度大于或等于所需的长度时，截取字符串。

        if(curLen >= length) {
            return builder.substring(0, length);
        }

        //当前缀+日期的长度小于所需的长度时，使用随机数补全长度

        Random random = new Random();
        for (int i = 0; i < length - curLen; i++) {
            builder.append(random.nextInt(10));
        }

        return builder.toString();
    }
}
