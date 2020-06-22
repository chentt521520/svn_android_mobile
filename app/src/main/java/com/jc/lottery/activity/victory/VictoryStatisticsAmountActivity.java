package com.jc.lottery.activity.victory;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.StatisticsAmountAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.StatisticsBean;
import com.jc.lottery.bean.req.pos_GetAdminSale;
import com.jc.lottery.bean.req.pos_GetStatisticsAmount;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 销量统计页
 */
public class VictoryStatisticsAmountActivity extends BaseActivity {

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
        pos_GetAdminSale.DataBean dataBean = new pos_GetAdminSale.DataBean();
        pos_GetAdminSale pos_getWalleQueryInfo = new pos_GetAdminSale(account_name,account_password,"3",dataBean);
        String s1 = new Gson().toJson(pos_getWalleQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetBackAdminSale)
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
                                JSONObject statisticsDlsMap = jsonObject.getJSONObject("statisticsDlsMap");
                                JSONObject statisticsDlsXjMap = jsonObject.getJSONObject("statisticsDlsXjMap");
                                JSONObject statisticsGlyMap = jsonObject.getJSONObject("statisticsGlyMap");
                                JSONObject statisticsFxsMap = jsonObject.getJSONObject("statisticsFxsMap");
                                if (roleAlias.equals("dls")) {
//                                    if (!jsonObject.getString("monthSales").equals("")) {
//                                        StatisticsBean statisticsBean = new StatisticsBean();
//                                        statisticsBean.setId(11);
//                                        statisticsBean.setName(getString(R.string.current_month_sales));
//                                        statisticsBean.setContent(jsonObject.getString("monthSales"));
//                                        statisticsBeanList.add(statisticsBean);
//                                    }
//                                    if (!jsonObject.getString("monthSalesFxs").equals("")) {
//                                        StatisticsBean statisticsBean = new StatisticsBean();
//                                        statisticsBean.setId(12);
//                                        statisticsBean.setName(getString(R.string.subordinate_current_month_sales));
//                                        statisticsBean.setContent(jsonObject.getString("monthSalesFxs"));
//                                        statisticsBeanList.add(statisticsBean);
//                                    }
                                    if (!statisticsDlsMap.getString("todayCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_todays));
                                        statisticsBean.setContent(statisticsDlsMap.getString("todayCash"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsMap.getString("todaySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(3);
                                        statisticsBean.setName(getString(R.string.total_sales_today));
                                        statisticsBean.setContent(statisticsDlsMap.getString("todaySales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsXjMap.getString("todayCashXj").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(6);
                                        statisticsBean.setName(getString(R.string.distributor_total_payment_todayl));
                                        statisticsBean.setContent(statisticsDlsXjMap.getString("todayCashXj"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsXjMap.getString("todaySalesXj").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(7);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_settled_todayl));
                                        statisticsBean.setContent(statisticsDlsXjMap.getString("todaySalesXj"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsMap.getString("historyCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                                        statisticsBean.setContent(statisticsDlsMap.getString("historyCash"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsMap.getString("historySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(8);
                                        statisticsBean.setName(getString(R.string.total_historical_sales));
                                        statisticsBean.setContent(statisticsDlsMap.getString("historySales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsXjMap.getString("historyCashXj").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(1);
                                        statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awardsl));
                                        statisticsBean.setContent(statisticsDlsXjMap.getString("historyCashXj"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsXjMap.getString("historySalesXj").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(4);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlement));
                                        statisticsBean.setContent(statisticsDlsXjMap.getString("historySalesXj"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                }else if (roleAlias.equals("gly")){
                                    if (!statisticsGlyMap.getString("todayCashGly").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_todays));
                                        statisticsBean.setContent(statisticsGlyMap.getString("todayCashGly"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsGlyMap.getString("historyCashGly").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                                        statisticsBean.setContent(statisticsGlyMap.getString("historyCashGly"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsFxsMap.getString("todayCashFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(6);
                                        statisticsBean.setName(getString(R.string.distributor_total_payment_todayl));
                                        statisticsBean.setContent(statisticsFxsMap.getString("todayCashFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsFxsMap.getString("todaySalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(7);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_settled_todayl));
                                        statisticsBean.setContent(statisticsFxsMap.getString("todaySalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsFxsMap.getString("historyCashFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(1);
                                        statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awardsl));
                                        statisticsBean.setContent(statisticsFxsMap.getString("historyCashFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsFxsMap.getString("historySalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(4);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlement));
                                        statisticsBean.setContent(statisticsFxsMap.getString("historySalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                } else if (roleAlias.equals("fxs")){
                                    if (!statisticsDlsMap.getString("todayCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_todays));
                                        statisticsBean.setContent(statisticsDlsMap.getString("todayCash"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsMap.getString("todaySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(3);
                                        statisticsBean.setName(getString(R.string.total_sales_today));
                                        statisticsBean.setContent(statisticsDlsMap.getString("todaySales"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsMap.getString("historyCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                                        statisticsBean.setContent(statisticsDlsMap.getString("historyCash"));
                                        statisticsBeanList.add(statisticsBean);
                                    }
                                    if (!statisticsDlsMap.getString("historySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(8);
                                        statisticsBean.setName(getString(R.string.total_historical_sales));
                                        statisticsBean.setContent(statisticsDlsMap.getString("historySales"));
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
                            showListView();
//                            llyPaymentNodata.setVisibility(View.VISIBLE);
                        }
//                        else {
//                            llyPaymentNodata.setVisibility(View.GONE);
//                        }
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

    private void showListView(){
        if (roleAlias.equals("dls")) {
//                                    if (!jsonObject.getString("monthSales").equals("")) {
//                                        StatisticsBean statisticsBean = new StatisticsBean();
//                                        statisticsBean.setId(11);
//                                        statisticsBean.setName(getString(R.string.current_month_sales));
//                                        statisticsBean.setContent(jsonObject.getString("monthSales"));
//                                        statisticsBeanList.add(statisticsBean);
//                                    }
//                                    if (!jsonObject.getString("monthSalesFxs").equals("")) {
//                                        StatisticsBean statisticsBean = new StatisticsBean();
//                                        statisticsBean.setId(12);
//                                        statisticsBean.setName(getString(R.string.subordinate_current_month_sales));
//                                        statisticsBean.setContent(jsonObject.getString("monthSalesFxs"));
//                                        statisticsBeanList.add(statisticsBean);
//                                    }

            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(2);
                statisticsBean.setName(getString(R.string.total_awards_todays));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(3);
                statisticsBean.setName(getString(R.string.total_sales_today));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(6);
                statisticsBean.setName(getString(R.string.distributor_total_payment_todayl));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(7);
                statisticsBean.setName(getString(R.string.distributor_total_sales_settled_todayl));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(5);
                statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(8);
                statisticsBean.setName(getString(R.string.total_historical_sales));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(1);
                statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awardsl));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("dls")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(4);
                statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlement));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
        }else if (roleAlias.equals("gly")){
            if (roleAlias.equals("gly")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(2);
                statisticsBean.setName(getString(R.string.total_awards_todays));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("gly")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(5);
                statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("gly")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(6);
                statisticsBean.setName(getString(R.string.distributor_total_payment_todayl));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("gly")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(7);
                statisticsBean.setName(getString(R.string.distributor_total_sales_settled_todayl));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("gly")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(1);
                statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awardsl));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("gly")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(4);
                statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlement));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
        } else if (roleAlias.equals("fxs")){
            if (roleAlias.equals("fxs")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(2);
                statisticsBean.setName(getString(R.string.total_awards_todays));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("fxs")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(3);
                statisticsBean.setName(getString(R.string.total_sales_today));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("fxs")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(5);
                statisticsBean.setName(getString(R.string.total_amount_historical_awards));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
            if (roleAlias.equals("fxs")) {
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setId(8);
                statisticsBean.setName(getString(R.string.total_historical_sales));
                statisticsBean.setContent("0");
                statisticsBeanList.add(statisticsBean);
            }
        }
        statisticsAmountAdapter.setList(statisticsBeanList);
        relPaymentRecord.setAdapter(statisticsAmountAdapter);
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
