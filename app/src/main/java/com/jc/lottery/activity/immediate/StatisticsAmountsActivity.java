package com.jc.lottery.activity.immediate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.StatisticsBean;
import com.jc.lottery.bean.req.pos_GetStatisticsAmount;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 销量统计页
 */
public class StatisticsAmountsActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    //    @BindView(R.id.rel_payment_record)
//    RecyclerView relPaymentRecord;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.lly_amounts_dls_one)
    TextView llyAmountsDlsOne;
    @BindView(R.id.lly_amounts_dls_two)
    TextView llyAmountsDlsTwo;
    @BindView(R.id.lly_amounts_dls_three)
    TextView llyAmountsDlsThree;
    @BindView(R.id.lly_amounts_dls_four)
    TextView llyAmountsDlsFour;
    @BindView(R.id.lly_amounts_dls_five)
    TextView llyAmountsDlsFive;
    @BindView(R.id.lly_amounts_dls)
    LinearLayout llyAmountsDls;
    @BindView(R.id.lly_amounts_fxs_one)
    TextView llyAmountsFxsOne;
    @BindView(R.id.lly_amounts_fxs_two)
    TextView llyAmountsFxsTwo;
    @BindView(R.id.lly_amounts_fxs_three)
    TextView llyAmountsFxsThree;
    @BindView(R.id.lly_amounts_fxs_four)
    TextView llyAmountsFxsFour;
    @BindView(R.id.lly_amounts_fxs_five)
    TextView llyAmountsFxsFive;
    @BindView(R.id.lly_amounts_fxs)
    LinearLayout llyAmountsFxs;
    @BindView(R.id.lly_amounts_gly_one)
    TextView llyAmountsGlyOne;
    @BindView(R.id.lly_amounts_gly_two)
    TextView llyAmountsGlyTwo;
    @BindView(R.id.lly_amounts_gly_three)
    TextView llyAmountsGlyThree;
    @BindView(R.id.lly_amounts_gly_four)
    TextView llyAmountsGlyFour;
    @BindView(R.id.lly_amounts_gly_five)
    TextView llyAmountsGlyFive;
    @BindView(R.id.lly_amounts_gly)
    LinearLayout llyAmountsGly;
    @BindView(R.id.tv_statistics_one)
    TextView tvStatisticsOne;
    @BindView(R.id.view_statistics_one)
    View viewStatisticsOne;
    @BindView(R.id.lly_statistics_one)
    LinearLayout llyStatisticsOne;
    @BindView(R.id.tv_statistics_two)
    TextView tvStatisticsTwo;
    @BindView(R.id.view_statistics_two)
    View viewStatisticsTwo;
    @BindView(R.id.lly_statistics_two)
    LinearLayout llyStatisticsTwo;
    //    @BindView(R.id.footer_receiving)
//    ClassicsFooter footerReceiving;
    private List<StatisticsBean> statisticsBeanList = new ArrayList<StatisticsBean>();
    private int pageNo = 1;
    private String roleAlias = "";
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistics_amounts;
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
//        relPaymentRecord.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    public void initData() {
        roleAlias = SPUtils.look(this, SPkey.roleAlias);
//        statisticsAmountAdapter = new StatisticsAmountAdapter(this);
        if (roleAlias.equals("dls")) {
            llyStatisticsOne.setVisibility(View.VISIBLE);
            llyStatisticsTwo.setVisibility(View.VISIBLE);
            llyAmountsDls.setVisibility(View.VISIBLE);
        } else if (roleAlias.equals("fxs")) {
            llyStatisticsOne.setVisibility(View.GONE);
            llyStatisticsTwo.setVisibility(View.VISIBLE);
            llyAmountsFxs.setVisibility(View.VISIBLE);
            tvStatisticsTwo.setTextColor(Color.rgb(22, 119, 255));
            viewStatisticsTwo.setVisibility(View.VISIBLE);
        } else {
//            tvStatisticsOne.setText(getString(R.string.administrators));
            llyStatisticsOne.setVisibility(View.VISIBLE);
            llyStatisticsTwo.setVisibility(View.GONE);
            llyAmountsGly.setVisibility(View.VISIBLE);
        }
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
        pos_GetStatisticsAmount pos_getWalleQueryInfo = new pos_GetStatisticsAmount(account_name, account_password, "3", dataBean);
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
                                        llyAmountsDlsTwo.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("monthSales")));
                                    }
                                    if (!jsonObject.getString("monthSalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(12);
                                        statisticsBean.setName(getString(R.string.subordinate_current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsTwo.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("monthSalesFxs")));
                                    }

                                    if (!jsonObject.getString("todayCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCash"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsDlsFour.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todayCash")));
                                    }
                                    if (!jsonObject.getString("todaySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(3);
                                        statisticsBean.setName(getString(R.string.total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySales"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsDlsOne.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todaySales")));
                                    }
                                    if (!jsonObject.getString("todayCashFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(6);
                                        statisticsBean.setName(getString(R.string.distributor_total_payment_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCashFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsFour.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todayCashFxs")));
                                    }
                                    if (!jsonObject.getString("todaySalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(7);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsOne.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todaySalesFxs")));
                                    }
                                    if (!jsonObject.getString("cashTotalMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsDlsFive.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashTotalMoney")));
                                    }
                                    if (!jsonObject.getString("cashSalesMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(8);
                                        statisticsBean.setName(getString(R.string.total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsDlsThree.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashSalesMoney")));
                                    }
                                    if (!jsonObject.getString("cashTotalMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(1);
                                        statisticsBean.setName(getString(R.string.distributor_total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsFive.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashTotalMoneyFxs")));
                                    }
                                    if (!jsonObject.getString("cashSalesMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(4);
                                        statisticsBean.setName(getString(R.string.distributor_total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsThree.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashSalesMoneyFxs")));
                                    }
                                } else if (roleAlias.equals("gly")) {
                                    if (!jsonObject.getString("monthSalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(12);
                                        statisticsBean.setName(getString(R.string.subordinate_current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsGlyTwo.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("monthSalesFxs")));
                                    }
                                    if (!jsonObject.getString("todayCashFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(6);
                                        statisticsBean.setName(getString(R.string.agent_total_payment_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCashFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsGlyFour.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todayCashFxs")));
                                    }
                                    if (!jsonObject.getString("todaySalesFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(7);
                                        statisticsBean.setName(getString(R.string.agent_total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySalesFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsGlyOne.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todaySalesFxs")));
                                    }
                                    if (!jsonObject.getString("cashTotalMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(1);
                                        statisticsBean.setName(getString(R.string.agent_total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsGlyFive.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashTotalMoneyFxs")));
                                    }
                                    if (!jsonObject.getString("cashSalesMoneyFxs").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(4);
                                        statisticsBean.setName(getString(R.string.agent_total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoneyFxs"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsGlyThree.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashSalesMoneyFxs")));
                                    }
                                } else if (roleAlias.equals("fxs")) {
                                    if (!jsonObject.getString("monthSales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(12);
                                        statisticsBean.setName(getString(R.string.current_month_sales));
                                        statisticsBean.setContent(jsonObject.getString("monthSales"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsTwo.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("monthSales")));
                                    }
                                    if (!jsonObject.getString("todayCash").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(2);
                                        statisticsBean.setName(getString(R.string.total_awards_today));
                                        statisticsBean.setContent(jsonObject.getString("todayCash"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsFour.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todayCash")));
                                    }
                                    if (!jsonObject.getString("todaySales").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(3);
                                        statisticsBean.setName(getString(R.string.total_sales_settled_today));
                                        statisticsBean.setContent(jsonObject.getString("todaySales"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsOne.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("todaySales")));
                                    }
                                    if (!jsonObject.getString("cashTotalMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(5);
                                        statisticsBean.setName(getString(R.string.total_amount_historical_settlement_awards));
                                        statisticsBean.setContent(jsonObject.getString("cashTotalMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsFive.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashTotalMoney")));
                                    }
                                    if (!jsonObject.getString("cashSalesMoney").equals("")) {
                                        StatisticsBean statisticsBean = new StatisticsBean();
                                        statisticsBean.setId(8);
                                        statisticsBean.setName(getString(R.string.total_sales_historical_settlement));
                                        statisticsBean.setContent(jsonObject.getString("cashSalesMoney"));
                                        statisticsBeanList.add(statisticsBean);
                                        llyAmountsFxsThree.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("cashSalesMoney")));
                                    }
                                }
//                                statisticsAmountAdapter.setList(statisticsBeanList);
//                                relPaymentRecord.setAdapter(statisticsAmountAdapter);
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        if (statisticsBeanList.size() < 1){
//                            llyPaymentNodata.setVisibility(View.VISIBLE);
//                        }else {
//                            llyPaymentNodata.setVisibility(View.GONE);
//                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        if (codeType) {
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


    @OnClick({R.id.lly_manual_scanner_back,R.id.lly_statistics_one,R.id.lly_statistics_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.lly_statistics_one:
                if (roleAlias.equals("dls")){
                    llyAmountsDls.setVisibility(View.VISIBLE);
                    llyAmountsFxs.setVisibility(View.GONE);
                    tvStatisticsOne.setTextColor(Color.rgb(22, 119, 255));
                    viewStatisticsOne.setVisibility(View.VISIBLE);
                    tvStatisticsTwo.setTextColor(Color.rgb(153, 153, 153));
                    viewStatisticsTwo.setVisibility(View.GONE);
                }
                break;
            case R.id.lly_statistics_two:
                if (roleAlias.equals("dls")){
                    llyAmountsDls.setVisibility(View.GONE);
                    llyAmountsFxs.setVisibility(View.VISIBLE);
                    tvStatisticsTwo.setTextColor(Color.rgb(22, 119, 255));
                    viewStatisticsTwo.setVisibility(View.VISIBLE);
                    tvStatisticsOne.setTextColor(Color.rgb(153, 153, 153));
                    viewStatisticsOne.setVisibility(View.GONE);
                }
                break;
        }
    }

}
