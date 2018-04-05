package com.dce.business.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
    public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 时间转换为yyyy-MM-dd格式的字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return YYYY_MM_DD_MM_HH_SS.format(date);
    }
    
    public static Date getDate(Date date, int minute, Integer hour, Integer day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        if (hour != null && hour == 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        } else if (hour != null && hour != 0) {
            calendar.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (day != null) {
            calendar.add(Calendar.DATE, day);
        }

        return calendar.getTime();
    }
    
    public static Date getDate(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        if (day != null) {
            calendar.add(Calendar.DATE, day);
        }

        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date a = getDate(new Date(), 0, 0, -1);
        System.out.println(a);
        a = getDate(a, 0, 0, -1);
        System.out.println(a);

        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例  
        ca.setTime(new Date()); // 设置时间为当前时间  
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //ca.set(2011, 11, 17);// 月份是从0开始的，所以11表示12月  
        //ca.add(Calendar.YEAR, -1); // 年份减1  
        //ca.add(Calendar.MONTH, -1);// 月份减1  
        ca.add(Calendar.DATE, -1);// 日期减1  
        Date resultDate = ca.getTime(); // 结果  
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(resultDate);

        ca = Calendar.getInstance();
        ca.setTime(resultDate);
        ca.add(Calendar.DATE, -1);// 日期减1  
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        resultDate = ca.getTime(); // 结果  
        System.out.println(resultDate);
    }
}
