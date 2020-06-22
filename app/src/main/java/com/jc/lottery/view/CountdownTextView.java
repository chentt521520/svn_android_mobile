package com.jc.lottery.view;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;


import com.jc.lottery.R;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.TimeManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @ Create_time: 2018/9/12 on 21:47.
 * @ description：倒计时控件
 * @ author: vchao
 */
public class CountdownTextView extends android.support.v7.widget.AppCompatTextView {
    public static final int what_count_down_k3 = 1;
    public static final int what_count_down_kl8 = 2;
    public static final int what_count_down_pl5 = 3;
    public static final int what_count_down_36x7 = 4;
    public static final int what_count_down_37x6 = 5;
    public static final int what_count_down_90x5 = 6;
//    public static final int what_count_down_36x7 = 4;

    Map<Integer, Term_view_bean> mTimerMap;
    private int term_no;
    private int ticket_mode;
    private TimeListener timeListener;
    private long end_time = 0;
    private boolean contine_tag = true;
    private boolean is_over = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
// 暂不合并，方便区别处理
            switch (msg.what) {
                case what_count_down_k3:// 10 分钟一期
//                    LogUtils.i("=== " + (end_time - TimeManager.getInstance().getServiceTime()));
                    if (end_time - TimeManager.getInstance().getServiceTime() <= Config.K3_R006_Time_End_sale * 1000) {
                        LogUtils.ee("快3 已经停止售彩了");
                        timeListener.buyDown();
                    }
                    if (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                        timeListener.timedown();
                        while (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                            term_no++;
                            end_time += 10 * 60 * 1000;
                        }
                        setTimeText();
                        try {
                            stopSaveValue(term_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        start(term_no, end_time);
                    } else {
                        setTimeText();
                    }
                    break;
                case what_count_down_kl8:
                    if (end_time - TimeManager.getInstance().getServiceTime() <= Config.KL8_R006_Time_End_sale * 1000) {
                        LogUtils.ee("快乐8 已经停止售彩了");
                        timeListener.buyDown();
                    }
//                    LogUtils.i("=== " + (end_time - TimeManager.getInstance().getServiceTime()));
                    if (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                        while (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                            term_no++;
                            end_time += 5 * 60 * 1000;
                        }
                        setTimeText();
                        try {
                            stopSaveValue(term_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        start(term_no, end_time);
                        timeListener.timedown();
                    } else {
                        setTimeText();
                    }
                    break;
                case what_count_down_pl5:
                    if (end_time - TimeManager.getInstance().getServiceTime() <= Config.PL5_R006_Time_End_sale * 1000) {
                        LogUtils.ee("排列5 已经停止售彩了");
                        timeListener.buyDown();
                    }
//                    LogUtils.i("=== " + (end_time - TimeManager.getInstance().getServiceTime()));
                    if (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                        while (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                            term_no++;
                            end_time += 24 * 60 * 60 * 1000;
                        }
                        setTimeText();
                        try {
                            stopSaveValue(term_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        start(term_no, end_time);
                        timeListener.timedown();
                    } else {
                        setTimeText();
                    }
                    break;
                case what_count_down_36x7:
                    if (end_time - TimeManager.getInstance().getServiceTime() <= Config.s36x7_R006_Time_End_sale * 1000) {
                        LogUtils.ee("36x7 已经停止售彩了");
                        timeListener.buyDown();
                    }
//                    LogUtils.i("=== " + (end_time - TimeManager.getInstance().getServiceTime()));
                    if (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                        while (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                            term_no++;
                            end_time += 24 * 60 * 60 * 1000;
                        }
                        setTimeText();
                        try {
                            stopSaveValue(term_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        start(term_no, end_time);
                        timeListener.timedown();
                    } else {
                        setTimeText();
                    }
                    break;
                case what_count_down_37x6:
                    if (end_time - TimeManager.getInstance().getServiceTime() <= Config.s37x6_R006_Time_End_sale * 1000) {
                        LogUtils.ee("37x6 已经停止售彩了");
                        timeListener.buyDown();
                    }
//                    LogUtils.i("=== " + (end_time - TimeManager.getInstance().getServiceTime()));
                    if (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                        while (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                            term_no++;
                            end_time += 24 * 60 * 60 * 1000;
                        }
                        setTimeText();
                        try {
                            stopSaveValue(term_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        start(term_no, end_time);
                        timeListener.timedown();
                    } else {
                        setTimeText();
                    }
                    break;
                case what_count_down_90x5:
                    if (end_time - TimeManager.getInstance().getServiceTime() <= Config.s90x5_R006_Time_End_sale * 1000) {
                        LogUtils.ee("90x5 已经停止售彩了");
                        timeListener.buyDown();
                    }
//                    LogUtils.i("=== " + (end_time - TimeManager.getInstance().getServiceTime()));
                    if (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                        while (end_time - TimeManager.getInstance().getServiceTime() <= 0) {
                            term_no++;
                            end_time += 24 * 60 * 60 * 1000;
                        }
                        setTimeText();
                        try {
                            stopSaveValue(term_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        start(term_no, end_time);
                        timeListener.timedown();
                    } else {
                        setTimeText();
                    }
                    break;
            }
        }
    };

    public CountdownTextView(Context context) {
        super(context);
        initTextType(context);
    }

    public CountdownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTextType(context);
    }

    public CountdownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTextType(context);
    }

    private void setTimeText() {
        setText(second2TimeSecond((end_time - TimeManager.getInstance().getServiceTime()) / 1000));
    }

    private void initTextType(Context context) {
        // 加载自定义字体
        Typeface TEXT_TYPE;
        try {
            TEXT_TYPE = Typeface.createFromAsset(context.getAssets(), "fonts/Quartz_Regular.ttf");
        } catch (Exception e) {
            LogUtils.e("加载第三方字体失败。");
            TEXT_TYPE = null;
        }

        if (TEXT_TYPE != null) {
            setTypeface(TEXT_TYPE);
        }
    }

    public void init(int ticket_mode, TimeListener timeListener) {
        mTimerMap = new HashMap<>();
        this.timeListener = timeListener;
        this.ticket_mode = ticket_mode;
    }

    public void start(final int term_no, final long end_time) {
        LogUtils.e("开始了" + term_no + "  " + end_time);
        timeListener.timeStart();

        contine_tag = true;
        this.end_time = end_time;
        this.term_no = term_no;
        if (mTimerMap.get(term_no) == null) {
            Timer timer = new Timer();
            Term_view_bean term_view_bean = new Term_view_bean(timer, term_no, end_time);
            mTimerMap.put(term_no, term_view_bean);
            mTimerMap.get(term_no).getTimer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (contine_tag) {
                        if (is_over) {
                            this.cancel();
                            return;
                        }
//                        LogUtils.i("当前奖期  == " + term_no + "\n结束时间 == " + end_time +
//                                "\n现在时间 == " + TimeManager.getInstance().getServiceTime() + "\n时间差 == " + (end_time - TimeManager.getInstance().getServiceTime()));
                        mHandler.sendEmptyMessage(ticket_mode);
                    }
                }
            }, 0, 1000);
        }
    }

    public void stop(int position_term_no) {
        if (mTimerMap.get(position_term_no) != null) {
            mTimerMap.get(position_term_no).getTimer().cancel();
        }
    }

    public void stopAll() {
        try {
            Iterator it = mTimerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                int key = (int) entry.getKey();
                Term_view_bean bean = (Term_view_bean) entry.getValue();
                bean.getTimer().cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contine_tag = false;
        is_over = true;
    }

    public void stopSaveValue(int value) {
        Iterator it = mTimerMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            int key = (int) entry.getKey();
            if (key != value) {
                Term_view_bean bean = (Term_view_bean) entry.getValue();
                bean.getTimer().cancel();
            }
        }
    }

    /**
     * 格式化
     *
     * @param second
     * @return
     */
    private String second2TimeSecond(long second) {

        long days = second / (3600 * 24);
        long hours = (second / 3600) % 24;
        long minutes = (second % 3600) / 60;
        long seconds = second % 60;

        String showTime = "";
        if (days > 0) {
            showTime = days + getContext().getString(R.string.day);
        }
        if (hours > 0) {
            if (hours < 10) {
                showTime += "  0" + hours + " : ";
            } else {
                showTime += "  " + hours + " : ";
            }
        }
        if (minutes < 10) {
            showTime += "0" + minutes + " : ";
        } else {
            showTime += "" + minutes + " : ";
        }
        if (seconds < 10) {
            showTime += "0" + seconds;
        } else {
            showTime += "" + seconds;
        }
        return showTime;
    }

    /**
     * 获取当前奖期
     *
     * @return
     */
    public int getTerm_no() {
        return term_no;
    }

    /**
     * 修正当前倒计时信息
     */
    public void updateTimer(final int term_no, final long end_time) {
        this.end_time = end_time;
        this.term_no = term_no;
        if (mTimerMap.get(term_no) == null) {
            start(term_no, end_time);
        } else {
            mTimerMap.get(term_no).setEnd_time(end_time);
        }
    }

    public interface TimeListener {
        void timedown();

        void buyDown();

        void timeStart();
    }

    private class Term_view_bean {
        private Timer timer;// 定时器
        private int integer;// 奖期
        private long end_time;// 结束时间

        public Term_view_bean() {
        }

        public Term_view_bean(Timer timer, int integer, long end_time) {
            this.timer = timer;
            this.integer = integer;
            this.end_time = end_time;
        }

        public Timer getTimer() {
            return timer;
        }

        public void setTimer(Timer timer) {
            this.timer = timer;
        }

        public int getInteger() {
            return integer;
        }

        public void setInteger(int integer) {
            this.integer = integer;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }
    }
}