package com.jc.lottery.util;

import java.text.DecimalFormat;

/**
 * @ Create_time: 2019/3/30 on 12:19.
 * @ description: 格式化工具类
 * @ author: vchao
 */
public class FormatUtil {
    /**
     * 将每三个数字加上逗号处理
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串
     */
    public static String addComma(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        try {
            str = decimalFormat.format(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String addComma(int num) {
        String str = "" + num;
        return addComma(str);
    }

    /**
     * 去掉逗号，获取数值
     *
     * @param str
     * @return
     */
    public static int removeComma(String str, boolean isInt) {
        String replace = str.replace(",", "");
        return Integer.parseInt(replace);
    }

    public static String removeComma(String str) {
        return str.replace(",", "");
    }
}
