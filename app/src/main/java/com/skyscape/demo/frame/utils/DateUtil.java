package com.skyscape.demo.frame.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author: Administrator
 * @date: 2021/6/10
 * @description
 */
public class DateUtil {
    public static final String NO_SECOND = "yyyy.MM.dd HH:mm";
    public static final String NO_MINUTE = "yyyy.MM.dd HH";
    public static final String NO_HOUR = "yyyy.MM.dd";
    public static final String NO_DAY = "yyyy.MM";
    public static final String NO_MONTH = "yyyy";

    public static final String transformDate(long time, String mode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mode, Locale.CHINA);
        return dateFormat.format(new Date(time));
    }

    //时间转换为天时分秒
    public static String calculate(Long remainTime) {
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = remainTime.intValue() / 1000;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        if (hour > 60) {
            day = hour / 24;
            hour = hour % 24;
        }
        String secondFormat = second < 10 ? "0" + String.valueOf(second) : "" + second;
        String minuteFormat = minute < 10 ? "0" + String.valueOf(minute) : "" + minute;
        String hourFormat = hour < 10 ? "0" + String.valueOf(hour) : "" + hour;
        String dayFormat = hour < 10 ? "0" + String.valueOf(day) : "" + day;

        return day == 0 ? hourFormat + ": " + minuteFormat + ": " + secondFormat
                : dayFormat + ": " + hourFormat + ": " + minuteFormat + ": " + secondFormat;

    }
}