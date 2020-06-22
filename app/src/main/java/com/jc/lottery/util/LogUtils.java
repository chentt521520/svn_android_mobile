package com.jc.lottery.util;

import android.util.Log;

/**
 * @ Create_time: 2018/6/26 on 14:34.
 * @ description： log工具类
 * @ author: vchao
 */
public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (Config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(Config.LogTag, createLog(message));
        }
    }

    public static void i(String message) {
        if (Config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(Config.LogTag, createLog(message));
        }
    }
// 不重要的log ，单独区分，免除干扰
    public static void ii(String message) {
        if (Config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(Config.IgnoreTag, createLog(message));
        }
    }
    public static void ee(String message) {
        if (Config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(Config.IgnoreTag, createLog(message));
        }
    }

    public static void d(String message) {
        if (Config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.d(Config.LogTag, createLog(message));
        }
    }
}
