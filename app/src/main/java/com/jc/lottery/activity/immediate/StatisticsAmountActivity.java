package com.jc.lottery.activity.immediate;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.StatisticsAmountAdapter;
import com.jc.lottery.adapter.WalletRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.StatisticsBean;
import com.jc.lottery.bean.WalletListBean;
import com.jc.lottery.bean.req.pos_GetStatisticsAmount;
import com.jc.lottery.bean.req.pos_GetWalleQueryInfo;
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
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 销量统计页
 */
public class StatisticsAmountActivity extends BaseActivity {

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
    private StatisticsAmountAdapter statisticsAmountAdapter;
    private List<StatisticsBean> statisticsBeanList = new ArrayList<StatisticsBean>();
    private int pageNo = 1;
    private String roleAlias = "";
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistics_amount;
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
        relPaymentRecord.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    public void initData() {
        roleAlias = SPUtils.look(this, SPkey.roleAlias);
        statisticsAmountAdapter = new StatisticsAmountAdapter(this);
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
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetStatisticsAmount.DataBean dataBean = new pos_GetStatisticsAmount.DataBean();
        pos_GetStatisticsAmount pos_getWalleQueryInfo = new pos_GetStatisticsAmount(account_name,account_password,"3",dataBean);
        String s1 = new Gson().toJson(pos_getWalleQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetStatisticsAmount)
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
                        srlReceiving.finishRefresh();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                if (roleAlias.equals("dls")) {
                                    if (!jsonObject.getString("monthSales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(11);
                                        statisticsBean.setName(getString(R.string.current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("monthSalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(12);
                                        statisticsBean.setName(getString(R.string.subordinate_current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }

                                    if (!jsonObject.getString("todayCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCash"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todaySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(3);
                                        statisticsBean.setName(getString(R.string.total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todayCashFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(6);
                                        statisticsBean.setName(getString(R.string.distributor_total_payment_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCashFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todaySalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(7);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashTotalMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashSalesMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(8);
                                        statisticsBean.setName(getString(R.string.total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashTotalMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(1);
                                        statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashSalesMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(4);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                }else if (roleAlias.equals("gly")){
                                    if (!jsonObject.getString("monthSalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(12);
                                        statisticsBean.setName(getString(R.string.subordinate_current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todayCashFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(6);
                                        statisticsBean.setName(getString(R.string.agent_total_payment_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCashFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todaySalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(7);
                                        statisticsBean.setName(getString(R.string.agent_total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashTotalMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(1);
                                        statisticsBean.setName(getString(R.string.agent_total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashSalesMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(4);
                                        statisticsBean.setName(getString(R.string.agent_total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                }else if (roleAlias.equals("fxs")){
                                    if (!jsonObject.getString("monthSales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(12);
                                        statisticsBean.setName(getString(R.string.current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todayCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCash"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("todaySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(3);
                                        statisticsBean.setName(getString(R.string.total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashTotalMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!jsonObject.getString("cashSalesMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(8);
                                        statisticsBean.setName(getString(R.string.total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                }
                                statisticsAmountAdapter.setList(statisticsBeanList);
                                relPaymentRecord.setAdapter(statisticsAmountAdapter);
                            }else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statisticsBeanList.size() < 1){
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
