package com.mmall.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间转换工具类
 * Created by Administrator on 2018/3/10 0010.
 */
public class DatetimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串日期转成日期
     * @param dateTimeStr
     * @return
     */
    public static Date str2Date(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * 日期转字符串格式的日期
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        if (date == null) {
            return StringUtils.EMPTY; // 空字符串
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }




    // 测试
    public static void main(String[] args) {
        System.out.println(DatetimeUtil.date2Str(new Date()));
        System.out.println(DatetimeUtil.str2Date("2018-05-12 12:25:36"));
    }

}
