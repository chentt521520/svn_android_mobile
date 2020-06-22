package com.jc.lottery.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2019/8/7.
 */

public class ScaleUtils {

    //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //px转dp
    public static int px2dip(Context context, int pxValue) {
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics()));
    }
    //px转换为sp
    public static int px2sp(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / v + 0.5f);
    }

    //dp转换为sp
    public static int dp2sp(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dip2px(context,value) / v + 0.5f);
    }

}
