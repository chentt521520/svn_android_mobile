package com.jc.lottery.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;

/**
 * @ Create_time: 2018/9/3 on 14:23.
 * @ description：SharedPreferences 保存工具类
 * @ author: vchao
 */
public class SPUtils {
    //  保存值
    public static void save(Context context, String key, String value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SPkey.SpName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        LogUtils.ii(" 已经保存   sp    " + key + "   =====   " + value);
    }

    //删除
    public static void delete(Context context, String key) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SPkey.SpName,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, SPkey.DEFAULT);
        editor.commit();
        LogUtils.ee("已经删除  sp  ===   " + key);
    }

    //查看
    public static String look(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPkey.SpName,
                Activity.MODE_PRIVATE);
        String data = sharedPreferences.getString(key, SPkey.DEFAULT);
//        LogUtils.ii("从sp中查看了  " + key + "  的值为  " + data);
        return data;
    }

    //查看
    public static String look(Context context, String key, String default_key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPkey.SpName,
                Activity.MODE_PRIVATE);
        String data = sharedPreferences.getString(key, default_key);
//        LogUtils.ii("从sp中查看了  " + key + "  的值为  " + data);
        return data;
    }

    //删除全部
    public static void deleteAll(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SPkey.SpName, Activity.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    /**
     * 判断本地是否存储该值。
     * 有值 ====   true
     * 无值  ====  false
     *
     * @param value
     * @return
     */
    public static boolean isHave(Context context, String value) {
        if (!TextUtils.isEmpty(value)) {
            String look = SPUtils.look(context, value);
            if (!TextUtils.isEmpty(look) && (!SPkey.DEFAULT.equals(look))) {
                LogUtils.ii("本地有这个值" + value);
                return true;
            }
        }
        LogUtils.ii("本地 没有这个值" + value);
        return false;
    }

    /**
     * 根据值获取存储的键名
     */
    public static String getKeyByValue(Context context, String value, String default_value) {
        SharedPreferences spf = context.getSharedPreferences(SPkey.SpName, Activity.MODE_PRIVATE);
        Map<String, ?> key_Value = (Map<String, ?>) spf.getAll(); //获取所有保存在对应标识下的数据，并以Map形式返回

        /* 只需遍历即可得到存储的key和value值*/
        for (Map.Entry<String, ?> entry : key_Value.entrySet()) {
            try {
                LogUtils.ii("获取的key：" + entry.getKey() + "      ======      获取的value:" + spf.getString(entry.getKey(), ""));
                if (TextUtils.equals(look(context, entry.getKey()), value)) {
                    return entry.getKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return default_value;
    }

}
