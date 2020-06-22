package com.jc.lottery.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Create_time: 2018/9/3 on 11:09.
 * @ description： 时间工具类
 * @ author: vchao
 */
public class TimeUtils {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DEFAULT_remaining_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss:SSS");

    /**
     * 获取当前10位整数时间戳
     *
     * @return
     */
    public static int get10IntTimeStamp() {
        return ((int) (TimeManager.getInstance().getServiceTime() / 1000));
    }

    public static long get13ServiceTimeStamp() {
        return TimeManager.getInstance().getServiceTime();
    }

    public static String GetToday() {
        String s = DEFAULT_DATE_FORMAT.format(new Date());
        return s;
    }

    public static String GetNow() {
        String s = DEFAULT_TIME_FORMAT.format(new Date());
        return s;
    }

    public static short GetYear(long currentTimeMillis) {
        return Short.parseShort(getTime(currentTimeMillis, new SimpleDateFormat("yyyy")));
    }

    public static short GetMonth(long currentTimeMillis) {
        return Short.parseShort(getTime(currentTimeMillis, new SimpleDateFormat("MM")));
    }

    public static short GetDay(long currentTimeMillis) {
        return Short.parseShort(getTime(currentTimeMillis, new SimpleDateFormat("dd")));
    }

    public static short GetYear() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("yyyy")));
    }

    public static short GetMonth() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("MM")));
    }

    public static short GetDay() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("dd")));
    }

    public static short GetHour() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("HH")));
    }

    public static short GetMinutes() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("mm")));
    }

    public static short GetSecond() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("ss")));
    }

    public static int GetMS() {
        return Short.parseShort(getTime(0, new SimpleDateFormat("SSS")));
    }

    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        if (timeInMillis == 0) {
            return dateFormat.format(new Date());
        } else {
            return dateFormat.format(new Date(timeInMillis));
        }
    }

    public static String timeStamp2Date(Context context, long time) {
        String language = SPUtils.look(context, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        }else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static String getTime(Context context, long time) {
        String language = SPUtils.look(context, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        }else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }
//    public static String getTime(long timeInMillis) {
////        if (timeInMillis == 0) {
////            return DEFAULT_TIME_FORMAT.format(new Date());
////        } else {
//        if (timeInMillis < 100000000000l) {
//            timeInMillis = timeInMillis * 1000;
//        }
//        return DEFAULT_TIME_FORMAT.format(new Date(timeInMillis));
////        }
//    }
}
