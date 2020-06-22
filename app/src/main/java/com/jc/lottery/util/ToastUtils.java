package com.jc.lottery.util;

import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.lottery.R;
import com.jc.lottery.base.application.LotteryApplication;

/**
 * @ Create_time: 2018/6/27 on 11:14.
 * @ description：吐司工具类
 * @ author: vchao
 */
public class ToastUtils {
    private static Toast toast;
    private static long mLastClickTime;
    private static long timeInterval = 3000L;
    /**
     * 短时间显示  Toast
     *
     * @param message 吐司内容
     */
    public static void showShort(CharSequence message) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            show(message, Toast.LENGTH_LONG);
            mLastClickTime = nowTime;
        }
    }

    public static void showShort(int res_id) {
        show(res_id, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param message 吐司内容
     */
    public static void showLong(CharSequence message) {
        show(message, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示时间
     *
     * @param sequence 吐司内容
     * @param duration 吐司时长
     */
    public static void show(CharSequence sequence, int duration) {
        try {
            LogUtils.e("  吐司：  " + sequence);
            if (toast == null) {
                toast = new Toast(LotteryApplication.getContext());
            }
//            toast.setGravity(Gravity.CENTER, 0, 120);
            toast.setGravity(Gravity.CENTER, 0, 0);
            View v = LayoutInflater.from(LotteryApplication.getContext()).inflate(R.layout.base_toast_layout, null);
            TextView tv2 = v.findViewById(R.id.tv_toast);
            tv2.setText(sequence);
            toast.setView(v);
            toast.setDuration(duration);
            toast.show();
//            Toast.makeText(LotteryApplication.getContext(),sequence,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Looper.prepare();
                Toast.makeText(LotteryApplication.getContext(), sequence, duration).show();
                Looper.loop();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void show(int res_id, int duration) {
        try {
            LogUtils.e("  吐司：  " + res_id);
            if (res_id == 0) {
                return;
            }
            if (toast == null) {
                toast = new Toast(LotteryApplication.getContext());
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            View v = LayoutInflater.from(LotteryApplication.getContext()).inflate(R.layout.base_toast_layout, null);
            TextView tv2 = v.findViewById(R.id.tv_toast);
            tv2.setText(res_id);
            toast.setView(v);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Looper.prepare();
                Toast.makeText(LotteryApplication.getContext(), res_id, duration).show();
                Looper.loop();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 隐藏toast
     */
    public static void hideToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}