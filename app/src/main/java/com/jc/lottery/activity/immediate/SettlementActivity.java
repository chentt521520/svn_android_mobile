package com.jc.lottery.activity.immediate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.SettlementRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.bean.req.pos_GetSettlementRecord;
import com.jc.lottery.fragment.MyImmediateRecordFragment;
import com.jc.lottery.fragment.SettlementRecordFragment;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.util.ImagePagerIndicator;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.ViewAnimationUtil;
import com.jc.lottery.view.XListView;
import com.jc.lottery.view.widget.CustomDatePicker;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 结算列表页
 */
public class SettlementActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;

    @BindView(R.id.btn_receiving_select_submit)
    Button btnReceivingSelectSubmit;
    @BindView(R.id.lly_receiving_select)
    LinearLayout llyReceivingSelect;
    @BindView(R.id.tv_receiving_select_start)
    TextView tvReceivingSelectStart;
    @BindView(R.id.tv_receiving_select_end)
    TextView tvReceivingSelectEnd;
    @BindView(R.id.lly_reward_record_select)
    LinearLayout llyRewardRecordSelect;
    private String[] mTitle;
    // fragment的集合
    private ArrayList<SettlementRecordFragment> list;
    private MySettlementViewPageAdapter adapter;

    FragmentManager fm = getSupportFragmentManager();

    private AlphaAnimation mAnimation;
    private CustomDatePicker customDatePicker;
    private String timeType = "1"; // 1：开始 2：结束
    private String start = "";
    private String end = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement_viewpage;
    }

    @Override
    public void initView() {
        ImagePagerIndicator imagePagerIndicator = (ImagePagerIndicator) findViewById(R.id.image_indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (SPUtils.look(this,SPkey.roleAlias).equals("fxs")){
            mTitle = new String[]{getString(R.string.settlement_records)};
            imagePagerIndicator.setVisibility(View.GONE);
        }else {
            mTitle = new String[]{getString(R.string.settlement_records), getString(R.string.audit_record)};
        }
        headerTypeOneTitle.setText(getString(R.string.settlement_records));
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        nameList = getIntent().getStringArrayListExtra("nameList");
        // 将fragment放进集合，并初始化适配器
        list = new ArrayList<SettlementRecordFragment>();
        for (int i = 0; i < mTitle.length; i++) {
            if (i == 0) {
                list.add(new SettlementRecordFragment("00"));
            }else {
                list.add(new SettlementRecordFragment("01"));
            }
        }
        adapter = new MySettlementViewPageAdapter(fm,
                list);
        viewPager.setAdapter(adapter);
        imagePagerIndicator.setDEFAULT_TAB_VISABLE_COUNT(mTitle.length);
        imagePagerIndicator.setTabTitles(mTitle, 16);
        imagePagerIndicator.setViewPager(viewPager, 0);
        imagePagerIndicator.setOnPageChangeListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("","viewpager onPageSelected"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initDatePicker();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //向前走一天
        Date date = calendar.getTime();
        start = formatter.format(date);
        end = now;
//        tvReceivingSelectStart.setText(formatter.format(date));
//        tvReceivingSelectEnd.setText(now);
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                if (timeType.equals("1")){
                    tvReceivingSelectStart.setText(showArTime(time,0));
                } else {
                    tvReceivingSelectEnd.setText(showArTime(time,0));
                }
            }
        }, "1999-01-01 00:00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 显示时和分
        customDatePicker.setIsLoop(false); // 允许循环滚动
//        tvReceivingSelectStart.setText("");
//        tvReceivingSelectEnd.setText("");
    }

    private String showArTime(String time,int type){
        String newTime = "";
        if (type == 0) {
            if (time.length() == 19) {
                newTime = time.substring(0, 10);
                newTime = newTime.substring(0, 4) + getString(R.string.y) + newTime.substring(5, 7) + getString(R.string.m) + newTime.substring(8, 10) + getString(R.string.d);
            } else {
                newTime = time;
            }
        }else {
            newTime = time;
            newTime = newTime.replace(getString(R.string.y),"-");
            newTime = newTime.replace(getString(R.string.m),"-");
            newTime = newTime.replace(getString(R.string.d),"");
        }
        return newTime;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back,R.id.lly_reward_record_select,R.id.lly_receiving_select,R.id.tv_receiving_select_start,R.id.tv_receiving_select_end, R.id.btn_receiving_select_submit,R.id.btn_receiving_select_reset,R.id.lly_receiving_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.btn_receiving_select_reset:
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                break;
            case R.id.lly_receiving_click:

                break;
            case R.id.lly_reward_record_select:
                llyReceivingSelect.setVisibility(View.VISIBLE);
                new ViewAnimationUtil().setShowAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.lly_receiving_select:
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
//                    llyReceivingSelect.setVisibility(View.GONE);
                break;
            case R.id.tv_receiving_select_start:
                timeType = "1";
                if (tvReceivingSelectStart.getText().toString().equals("")){
                    customDatePicker.show(start);
                }else {
                    customDatePicker.show(showArTime(tvReceivingSelectStart.getText().toString(),1) + " 00:00:00");
                }
                break;
            case R.id.tv_receiving_select_end:
                timeType = "2";
                if (tvReceivingSelectEnd.getText().toString().equals("")){
                    customDatePicker.show(end);
                }else {
                    customDatePicker.show(showArTime(tvReceivingSelectEnd.getText().toString(),1) + " 00:00:00");
                }
                break;
            case R.id.btn_receiving_select_submit:
//                refreshType = 1;
//                pageNo = 1;
////                getRecordHttp();
//                getHttpInfo();
                String starts = tvReceivingSelectStart.getText().toString().trim();
                String ends = tvReceivingSelectEnd.getText().toString().trim();
                if (!starts.equals("")){
                    if (!ends.equals("")){
                        starts = showArTime(starts,1) + " 00:00:01";
                        ends = showArTime(ends,1) + " 23:59:59";
                        starts = parseServerTime(starts,"");
                        ends = parseServerTime(ends,"");
                    }else {
                        starts = "";
                        ends = "";
                    }
                }else {
                    starts = "";
                    ends = "";
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).showHttpTime(starts,ends);
                }
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
        }
    }

    public static String parseServerTime(String date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
//            format = "yyyy-MM-dd";
        }
        if (date.equals("")) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
//            return String.valueOf(sdf.parse(date).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public class MySettlementViewPageAdapter extends FragmentPagerAdapter {

        private List<SettlementRecordFragment> list;

        public MySettlementViewPageAdapter(FragmentManager fm, ArrayList<SettlementRecordFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("", "容器 onPageSelected" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
