package com.jc.lottery.activity.immediate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.RewardRecordDetailActivity;
import com.jc.lottery.adapter.betting.MyImmediateRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.req.pos_CashRecordInfo;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.resp.RewardInfoBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ScaleUtils;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.ViewAnimationUtil;
import com.jc.lottery.view.FlowLayout;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑奖记录页面
 */
public class ImmediateCashRecordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    @BindView(R.id.lly_settlement_nodata)
    PercentLinearLayout llySettlementNodata;
    @BindView(R.id.flow_one)
    FlowLayout flowOne;
    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.btn_receiving_select_submit)
    Button btnReceivingSelectSubmit;
    @BindView(R.id.lly_reward_record_select)
    LinearLayout llyRewardRecordSelect;
    @BindView(R.id.lly_receiving_select)
    LinearLayout llyReceivingSelect;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    @BindView(R.id.tv_receiving_select_start)
    TextView tvReceivingSelectStart;
    @BindView(R.id.tv_receiving_select_end)
    TextView tvReceivingSelectEnd;
    private List<RewardInfoBean> list = new ArrayList<RewardInfoBean>();
    private MyImmediateRecordAdapter recordAdapter = null;
    private String game;
    private int pageNo = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private List<String> nameList = new ArrayList<String>();
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private String gameAliasSelect = "";
    private String gameAlias = "";
    private AlphaAnimation mAnimation;
    private int pageNum = 1;
    private int lastOffset = 0;
    private int lastPosition = 0;
    private CustomDatePicker customDatePicker;
    private String timeType = "1"; // 1：开始 2：结束
    private String start = "";
    private String end = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_immediate_cash_record;
    }

    @Override
    public void initView() {
        headerTypeOneTitle.setText(getString(R.string.reward_record));
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
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    @Override
    public void initListener() {
        relBetting.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new MyImmediateRecordAdapter(this);
//        recordAdapter.setOnItemClickListener(new MyImmediateRecordAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent();
//                intent.putExtra("data", list.get(position));
//                intent.setClass(ImmediateCashRecordActivity.this, RewardRecordDetailActivity.class);
//                startActivity(intent);
//            }
//        });
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                refreshType = 1;
                pageNo = 1;
                gameAliasSelect = "";
                gameAlias = gameAliasSelect;
                srlReceiving.resetNoMoreData();
                getHttpInfo(gameAlias);
            }
        });

        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    getHttpInfo(gameAlias);
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });

        relBetting.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        LinearLayoutManager layoutManager = (LinearLayoutManager) relBetting.getLayoutManager();
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
        if(relBetting.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) relBetting.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    @Override
    public void initData() {
        getGameHttpInfo();
        getHttpInfo("");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.btn_receiving_select_submit,R.id.lly_reward_record_select,R.id.lly_receiving_select,R.id.tv_receiving_select_start,R.id.tv_receiving_select_end,R.id.btn_receiving_select_reset,R.id.lly_receiving_click})
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
            case R.id.btn_receiving_select_submit:
                refreshType = 1;
                pageNo = 1;
                gameAlias = gameAliasSelect;
                getHttpInfo(gameAlias);
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.lly_reward_record_select:
                if (nameList.size() < 1) {
                    getGameHttpInfo();
                }
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
        }
    }

    private void getGameHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GameQueryInfo pos_gameQueryInfo = new pos_GameQueryInfo(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetGameQueryInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
//                        ToastUtils.showShort("response:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                JSONArray gameList = jsonObject.getJSONArray("gameList");
                                GameListBean gameBean = new GameListBean();
                                gameBean.setGameName(getString(R.string.all));
                                gameBean.setGameAlias("");
                                gameBean.setTicketPrice("");
                                gameBean.setEnabled("00");
                                nameList.add(gameBean.getGameName());
                                gameListBeans.add(gameBean);
                                for (int i = 0; i < gameList.length(); i++) {
                                    JSONObject json = gameList.getJSONObject(i);
                                    GameListBean gameListBean = new GameListBean();
                                    gameListBean.setGameName(json.getString("gameName"));
                                    gameListBean.setGameAlias(json.getString("gameAlias"));
                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
                                    gameListBean.setEnabled(json.getString("enabled"));
                                    if (gameListBean.getEnabled().equals("00")) {
                                        nameList.add(gameListBean.getGameName());
                                        gameListBeans.add(gameListBean);
                                    }
                                }
//                                changeSpinner(spReceivingSelectName, nameList);
                                showGameFlowLayout(nameList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        finish();
                    }
                });
    }

    private void getHttpInfo(String gameAlias) {
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
        pos_CashRecordInfo.DataBean.CashInfo cashInfo = new pos_CashRecordInfo.DataBean.CashInfo(gameAlias, pageNo + "",starts,ends, TimeUtils.get10IntTimeStamp() + "");
        pos_CashRecordInfo pos_cashRecordInfo = new pos_CashRecordInfo(account_name, account_password, "3", cashInfo);
        String s1 = new Gson().toJson(pos_cashRecordInfo);
        OkGo.<String>post(MyUrl.pos_CashRecordInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
//                        onLoad();
                        if (refreshType == 1) {
                            if (list != null) {
                                list.clear();
                                pageNo = 1;
                            }
                        }
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            if (jsonObject.has("pageNum")) {
                                pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                            }
                            int num = 1;
                            if (pageNo == 1) {
                                num = 1;
                            }else {
                                num = (pageNo - 1) * pageNum + 1;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("cashList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject cashList = jsonArray.getJSONObject(i);
                                RewardInfoBean recordInfoBean = new RewardInfoBean();
                                recordInfoBean.setGameName(cashList.getString("gameName"));
                                recordInfoBean.setCashState(cashList.getString("cashState"));
                                recordInfoBean.setCashMoney(cashList.getString("cashMoney"));
                                recordInfoBean.setCashTime(timeStamp2Date(Long.parseLong(cashList.getString("cashTime"))));
                                recordInfoBean.setOrderCode(cashList.getString("orderCode"));
                                recordInfoBean.setSafetyCode(cashList.getString("safetyCode"));
                                recordInfoBean.setChannel(cashList.getString("channel"));
                                recordInfoBean.setBookNum(cashList.getString("bookNum"));
                                recordInfoBean.setTicketNum(cashList.getString("ticketNum"));
                                recordInfoBean.setIndex(num);
                                list.add(recordInfoBean);
                                num++;
                            }
//                            recordAdapter = new MyImmediateRecordAdapter(getActivity());
                            recordAdapter.setList(list);
                            relBetting.setAdapter(recordAdapter);
                            if (list.size() < 1) {
                                relBetting.setVisibility(View.GONE);
                                llySettlementNodata.setVisibility(View.VISIBLE);
                            } else {
                                relBetting.setVisibility(View.VISIBLE);
                                llySettlementNodata.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                        if (refreshType != 1) {
                            scrollToPosition();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                    }
                });
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(this, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    private void showGameFlowLayout(List<String> list) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5));
        if (flowOne != null) {
            flowOne.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(this);
            tv.setPadding(ScaleUtils.dip2px(this, 25), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 25), ScaleUtils.dip2px(this, 5));
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            if (i == 0) {
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundResource(R.drawable.reward_et_red_bg);
                gameAliasSelect = gameListBeans.get(0).getGameAlias();
            } else {
                tv.setBackgroundResource(R.drawable.reward_et_bg);
                tv.setTextColor(Color.GRAY);
            }
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(this);
            flowOne.addView(tv, layoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == flowOne.getChildAt(0).getParent()) {
            for (int i = 0; i < flowOne.getChildCount(); i++) {
                TextView tv = (TextView) flowOne.getChildAt(i);
                if (v == tv) {
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.reward_et_red_bg);
                    gameAliasSelect = gameListBeans.get(i).getGameAlias();
                } else {
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.reward_et_bg);
                }
            }
        }
    }

}
