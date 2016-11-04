package com.lionxxw.utils.date;

import com.lionxxw.utils.constant.DateFormat;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 */
public class DateUtil {

    public static Date getDate(String s) {
        return getDate(s, null);
    }

    public static Date getJustDate(String s) {
        return getDate(s, DateFormat.Y_M_D);
    }

    public static Date getDate(long date) {
        return getDate(date, null);
    }

    public static Date getJustDate(long date) {
        return getDate(date, DateFormat.Y_M_D);
    }

    public static Date getDate(long date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DateFormat.SETTLE_PATTERN;
        }

        return getDate(formatDate(new Date(date), format), format);
    }

    public static Date getDate(String s, String format) {
        Date date;
        try {
            if (StringUtils.isEmpty(format)) {
                format = DateFormat.SETTLE_PATTERN;
            }

            date = new SimpleDateFormat(format).parse(s);
        } catch (Exception e) {
            date = new Date(0L);
        }

        return date;
    }

    public static String formatDate(long date, String format) {
        return formatDate(new Date(date), format);
    }

    public static String formatDate(long date) {
        return formatDate(new Date(date), null);
    }

    public static String formatJustDate(long date) {
        return formatDate(new Date(date), DateFormat.Y_M_D);
    }

    public static String formatDate(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DateFormat.SETTLE_PATTERN;
        }

        return new SimpleDateFormat(format).format(date);
    }
}
