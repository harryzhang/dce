package com.dce.business.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    
    /**
     * 时间转换为yyyy-MM-dd格式的字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return YYYY_MM_DD_MM_HH_SS.format(date);
    }
}
