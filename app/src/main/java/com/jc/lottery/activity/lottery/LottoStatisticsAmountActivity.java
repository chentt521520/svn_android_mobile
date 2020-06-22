package com.jc.lottery.activity.lottery;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.StatisticsAmountAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.StatisticsBean;
import com.jc.lottery.bean.req.TerminalStatisticsBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 乐透销量统计页
 */
public class LottoStatisticsAmountActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.rel_payment_record)
    RecyclerView relPaymentRecord;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    //    @BindView(R.id.footer_receiving)
//    ClassicsFooter footerReceiving;
    @BindView(R.id.lly_payment_nodata)
    PercentLinearLayout llyPaymentNodata;
    @BindView(R.id.rel_payment_record_37x6)
    RecyclerView relPaymentRecord37x6;
    private StatisticsAmountAdapter statisticsAmountAdapter;
    private StatisticsAmountAdapter statisticsAmount37x6Adapter;
    private List<StatisticsBean> statisticsBeanList = new ArrayList<StatisticsBean>();
    private List<StatisticsBean> statisticsBean37x6List = new ArrayList<StatisticsBean>();
    private int pageNo = 1;
    private String roleAlias = "";
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lotto_statistics_amount;
    }

    @Override
    public void initView() {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relPaymentRecord.setLayoutManager(new GridLayoutManager(this, 2));
        relPaymentRecord37x6.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void initData() {
        roleAlias = SPUtils.look(this, SPkey.roleAlias);
        statisticsAmountAdapter = new StatisticsAmountAdapter(this);
        statisticsAmount37x6Adapter = new StatisticsAmountAdapter(this);
        getHttpInfo();
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
//                getRecordHttp();
                getHttpInfo();
            }
        });
//        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//
//            }
//        });
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        TerminalStatisticsBean pos_getWalleQueryInfo = new TerminalStatisticsBean(account_name);
        String s1 = new Gson().toJson(pos_getWalleQueryInfo);
        OkGo.<String>post(MyUrl.terminalStatistics)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        if (refreshType == 1) {
//                            if (settlementRecordBeanList != null) {
//                                settlementRecordBeanList.clear();
//                                pageNo = 1;
//                            }
//                        }
                        statisticsBeanList.clear();
                        statisticsBean37x6List.clear();
                        srlReceiving.finishRefresh();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                if (roleAlias.equals("fxs")||roleAlias.equals("gly")||roleAlias.equals("dls")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("statisticsList");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String gameAlias = json.getString("gameAlias");
                                        if (gameAlias.equals("90x5")){
                                            if (!json.getString("todayCash").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(1);
                                                statisticsBean.setName(getString(R.string.total_awards_todays));
                                                statisticsBean.setContent(json.getInt("todayCash") + "");
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                            if (!json.getString("historyCash").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(2);
                                                statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                                                statisticsBean.setContent(json.getInt("historyCash") + "");
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                            if (!json.getString("todaySales").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(3);
                                                statisticsBean.setName(getString(R.string.total_sales_today));
                                                statisticsBean.setContent(json.getInt("todaySales") + "");
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                            if (!json.getString("historySales").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(4);
                                                statisticsBean.setName(getString(R.string.total_historical_sales));
                                                statisticsBean.setContent(json.getInt("historySales") + "");
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                        }else if(gameAlias.equals("37x6")){
                                            if (!json.getString("todayCash").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(1);
                                                statisticsBean.setName(getString(R.string.total_awards_todays));
                                                statisticsBean.setContent(json.getString("todayCash"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                            if (!json.getString("historyCash").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(2);
                                                statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                                                statisticsBean.setContent(json.getString("historyCash"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                            if (!json.getString("todaySales").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(3);
                                                statisticsBean.setName(getString(R.string.total_sales_today));
                                                statisticsBean.setContent(json.getString("todaySales"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                            if (!json.getString("historySales").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(4);
                                                statisticsBean.setName(getString(R.string.total_historical_sales));
                                                statisticsBean.setContent(json.getString("historySales"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                        }
                                    }
                                }
                                if (roleAlias.equals("dls")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("statisticsXjList");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String gameAlias = json.getString("gameAlias");
                                        if (gameAlias.equals("90x5")){
                                            if (!json.getString("todayCashXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(1);
                                                statisticsBean.setName(getString(R.string.distributor_total_payment_todayl));
                                                statisticsBean.setContent(json.getString("todayCashXj"));
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                            if (!json.getString("historyCashXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(2);
                                                statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awardsl));
                                                statisticsBean.setContent(json.getString("historyCashXj"));
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                            if (!json.getString("todaySalesXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(3);
                                                statisticsBean.setName(getString(R.string.distributor_total_sales_settled_todayl));
                                                statisticsBean.setContent(json.getString("todaySalesXj"));
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                            if (!json.getString("historySalesXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(4);
                                                statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlementl));
                                                statisticsBean.setContent(json.getString("historySalesXj"));
                                                statisticsBeanList.add(statisticsBean);
                                            }
                                        }else if(gameAlias.equals("37x6")){
                                            if (!json.getString("todayCashXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(1);
                                                statisticsBean.setName(getString(R.string.distributor_total_payment_todayl));
                                                statisticsBean.setContent(json.getString("todayCashXj"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                            if (!json.getString("historyCashXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(2);
                                                statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awardsl));
                                                statisticsBean.setContent(json.getString("historyCashXj"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                            if (!json.getString("todaySalesXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(3);
                                                statisticsBean.setName(getString(R.string.distributor_total_sales_settled_todayl));
                                                statisticsBean.setContent(json.getString("todaySalesXj"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                            if (!json.getString("historySalesXj").equals("")) {
                                                StatisticsBean statisticsBean = new StatisticsBean();
                                                statisticsBean.setId(4);
                                                statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlementl));
                                                statisticsBean.setContent(json.getString("historySalesXj"));
                                                statisticsBean37x6List.add(statisticsBean);
                                            }
                                        }
                                    }
                                }
                                statisticsAmountAdapter.setList(statisticsBeanList);
                                relPaymentRecord.setAdapter(statisticsAmountAdapter);
                                statisticsAmount37x6Adapter.setList(statisticsBean37x6List);
                                relPaymentRecord37x6.setAdapter(statisticsAmount37x6Adapter);
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statisticsBeanList.size() < 1) {
                            llyPaymentNodata.setVisibility(View.VISIBLE);
                        } else {
                            llyPaymentNodata.setVisibility(View.GONE);
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        if (null != srlReceiving) {
                            if (null != srlReceiving.finishRefresh()) {
                                srlReceiving.finishRefresh();
                            }
                        }
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


    @OnClick({R.id.lly_manual_scanner_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
        }
    }

}
