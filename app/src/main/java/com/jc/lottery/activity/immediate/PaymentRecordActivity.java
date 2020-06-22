package com.jc.lottery.activity.immediate;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.PaymentRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetPaymentRecordQuery;
import com.jc.lottery.bean.resp.PaymentBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.ViewAnimationUtil;
import com.jc.lottery.view.widget.CustomDatePicker;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付列表页
 */
public class PaymentRecordActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.rel_payment_record)
    RecyclerView relPaymentRecord;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    @BindView(R.id.lly_payment_nodata)
    PercentLinearLayout llyPaymentNodata;

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
    private PaymentRecordAdapter paymentRecordAdapter;
    private PaymentBean paymentBean;
    private List<PaymentBean.PayListBean> payListBeans = new ArrayList<PaymentBean.PayListBean>();
    private int pageNo = 1;
    private int refreshType = 1;
    private int pageCount = 1;
    private int pageNum = 1;
    private int lastOffset = 0;
    private int lastPosition = 0;
    private boolean codeType = true;
    private AlphaAnimation mAnimation;
    private CustomDatePicker customDatePicker;
    private String timeType = "1"; // 1：开始 2：结束
    private String start = "";
    private String end = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_record;
    }

    @Override
    public void initView() {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.pull_up_load_more);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.release_load_now);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.xlistview_header_hint_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.load_complete);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.failed_to_load);
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.no_more);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relPaymentRecord.setLayoutManager(new LinearLayoutManager(this));
        initDatePicker();
    }

    @Override
    public void initData() {
        paymentRecordAdapter = new PaymentRecordAdapter(this);
        getHttpInfo();
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
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                codeType = false;
//                getRecordHttp();
                refreshType = 1;
                pageNo = 1;
                srlReceiving.resetNoMoreData();
                getHttpInfo();
            }
        });
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                codeType = false;
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    getHttpInfo();
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
        relPaymentRecord.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
    }

    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) relPaymentRecord.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(relPaymentRecord.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) relPaymentRecord.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
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

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));

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
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        final pos_GetPaymentRecordQuery.PayRecordBean payRecordBean = new pos_GetPaymentRecordQuery.PayRecordBean(pageNo + "",starts,ends, TimeUtils.get10IntTimeStamp() + "");
        pos_GetPaymentRecordQuery.DataBean dataBean = new pos_GetPaymentRecordQuery.DataBean(payRecordBean);
        pos_GetPaymentRecordQuery pos_getPaymentRecordQuery = new pos_GetPaymentRecordQuery(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getPaymentRecordQuery);
        OkGo.<String>post(MyUrl.pos_GetPaymentRecordQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        if (refreshType == 1) {
                            if (payListBeans != null) {
                                payListBeans.clear();
                            }
                        }
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                pageCount = jsonObject.getInt("pageCount");
                                if (jsonObject.has("pageNum")) {
                                    pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                                }
                                int num = 1;
                                if (pageNo == 1) {
                                    num = 1;
                                }else {
                                    num = (pageNo - 1) * pageNum + 1;
                                }
                                paymentBean = new Gson().fromJson(response.body(), PaymentBean.class);
                                for (int i = 0; i < paymentBean.getPayList().size(); i++) {
                                    paymentBean.getPayList().get(i).setIndex(num);
                                }
                                payListBeans.addAll(paymentBean.getPayList());
                                paymentRecordAdapter.setList(payListBeans);
                                relPaymentRecord.setAdapter(paymentRecordAdapter);
                                if (refreshType != 1) {
                                    scrollToPosition();
//                                    relPaymentRecord.scrollToPosition(payListBeans.size() - 1);
                                }
                            }else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (payListBeans.size() < 1){
                            llyPaymentNodata.setVisibility(View.VISIBLE);
                        }else {
                            llyPaymentNodata.setVisibility(View.GONE);
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        if (codeType){
                            finish();
                        }
                    }
                });
    }

    /**
     * 加载完成
     */
    private void onLoad() {
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
                refreshType = 1;
                pageNo = 1;
                getHttpInfo();
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
        }
    }

}
