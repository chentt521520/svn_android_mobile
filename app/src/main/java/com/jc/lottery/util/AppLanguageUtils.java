package com.jc.lottery.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * @ Create_time: 2018/12/5 on 16:23.
 * @ description：更换语言工具类
 * @ author: vchao
 */
public class AppLanguageUtils {

    /**
     * 更改应用语言
     *
     * @param context
     * @param locale  语言地区
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void changeAppLanguage(Context context, Locale locale) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conf.setLocale(locale);
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            conf.setLocales(localeList);
        } else {
            conf.locale = locale;
            try {
                conf.setLayoutDirection(locale);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        res.updateConfiguration(conf, dm);
    }

    public static void getAppLanguage(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList locales = configuration.getLocales();
            for (int i = 0; i < locales.size(); i++) {
                LogUtils.i("locales  == " + i + "  " + locales.get(i).getDisplayName());
            }
        } else {
            Locale locale = configuration.locale;
            LogUtils.i("locales  == " + locale.getDisplayName());
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Context wrapContext(Context context) {
        Resources resources = context.getResources();
        Locale locale = Locale.ENGLISH;
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);
        return context.createConfigurationContext(configuration);
    }
}