package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Package test
 * Project lionxxw-utils
 * Company www.baofoo.com
 * Author wangjian@baofoo.com
 * Created on 2016/11/8 9:18
 * version 1.0.0
 */
public class TestDate {
    private static String DATE_FORMAT = "yyyy年MM月dd日";

    public static void main(String[] args) {
        showDate();
        weekInYear("2015年4月6日");
    }

    /**
     * 展示当前时间
     */
    public static void showDate(){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        System.out.println(format.format(new Date()));
    }

    /**
     * 计算指定日期是当年的第几周
     * @param date
     */
    public static void weekInYear(String date){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //取当前日期的年份里面的周数
        int currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

        System.out.println(currentWeekOfYear);
    }
}
