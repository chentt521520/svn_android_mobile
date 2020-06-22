package com.jc.lottery.activity.lottery;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.SettlementDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.LotterySettlementDetailBean;
import com.jc.lottery.bean.SettlementGetDetailBean;
import com.jc.lottery.bean.req.pos_GetLotterySettlementDetails;
import com.jc.lottery.bean.resp.ReceivingActivationBean;
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
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 乐透结算详情页
 */
public class LotterySettlementDetailActivity extends BaseActivity {

    @BindView(R.id.bt_settlement_detail_examine)
    Button btSettlementDetailExamine;
    @BindView(R.id.lly_settlement_detail_examine)
    PercentLinearLayout llySettlementDetailExamine;
    @BindView(R.id.tv_preview_game)
    TextView tvPreviewGame;
    @BindView(R.id.tv_preview_exchange_commission)
    TextView tvPreviewExchangeCommission;
    @BindView(R.id.tv_preview_commission_ratio)
    TextView tvPreviewCommissionRatio;
    @BindView(R.id.tv_preview_sales_commission)
    TextView tvPreviewSalesCommission;
    @BindView(R.id.tv_preview_sales_commission_ratio)
    TextView tvPreviewSalesCommissionRatio;
    @BindView(R.id.tv_preview_total_commission)
    TextView tvPreviewTotalCommission;
    @BindView(R.id.tv_preview_payable)
    TextView tvPreviewPayable;
    @BindView(R.id.tv_preview_total_settlement_amount)
    TextView tvPreviewTotalSettlementAmount;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.tv_preview_total_rewards)
    TextView tvPreviewTotalRewards;
    @BindView(R.id.tv_preview_total_settlement_status)
    TextView tvPreviewTotalSettlementStatus;
    @BindView(R.id.tv_preview_total_settlement_time)
    TextView tvPreviewTotalSettlementTime;
    @BindView(R.id.tv_preview_book)
    TextView tvPreviewTotalSettlementBook;
    @BindView(R.id.tv_preview_cash)
    TextView tvPreviewCash;
    @BindView(R.id.tv_preview_sale)
    TextView tvPreviewSale;
    @BindView(R.id.tv_preview_total_settlement_fixed_quota)
    TextView tvPreviewTotalSettlementFixedQuota;
    @BindView(R.id.tv_preview_total_settlement_surplus_quota)
    TextView tvPreviewTotalSettlementSurplusQuota;
    @BindView(R.id.tv_preview_total_settlement_used_quota)
    TextView tvPreviewTotalSettlementUsedQuota;
    @BindView(R.id.tv_preview_total_settle_quota)
    TextView tvPreviewTotalSettlementSettleQuota;
    private List<ReceivingActivationBean> receivingActivationBeanList = new ArrayList<ReceivingActivationBean>();
    private SettlementDetailAdapter receivingActivationAdapter;
    private String orderCode = "";
    private String settleStatus = "00";
    private LotterySettlementDetailBean settlementGetDetailBean;
    private List<SettlementGetDetailBean.GetList> bookList = new ArrayList<SettlementGetDetailBean.GetList>();
    private int pageNo = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lottery_settlement_detail;
    }

    @Override
    public void getPreIntent() {
        settleStatus = getIntent().getStringExtra("settleStatus");
        orderCode = getIntent().getStringExtra("orderCode");
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
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
    }

    @Override
    public void initData() {
        getHttpInfo();
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                refreshType = 1;
                pageNo = 1;
                srlReceiving.resetNoMoreData();
                getHttpInfo();
            }
        });
        srlReceiving.setEnableLoadmore(false);
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetLotterySettlementDetails.DataBean.SettlementInfo settlementInfo = new pos_GetLotterySettlementDetails.DataBean.SettlementInfo(orderCode);
        pos_GetLotterySettlementDetails pos_getSettlementRecord = new pos_GetLotterySettlementDetails(account_name, account_password, "3", settlementInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.settleSettleDetails)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
//                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
//                            if (pageNo == pageCount){
//                                srlReceiving.finishLoadmoreWithNoMoreData();
//                            }
                            if (jsonObject.getString("code").equals("00000")) {
                                JSONObject settlInfo = jsonObject.getJSONObject("settlInfo");
                                settlementGetDetailBean = new Gson().fromJson(settlInfo.toString(), LotterySettlementDetailBean.class);
                                showData();
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        if (codeType) {
                            finish();
                        }
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

    @OnClick({R.id.lly_settlement_back, R.id.bt_settlement_detail_examine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_settlement_back:
                finish();
                break;
            case R.id.bt_settlement_detail_examine:
                break;
            default:
                break;
        }
    }

    private void showData() {
        tvPreviewGame.setText(settlementGetDetailBean.getGameName());
        tvPreviewPayable.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getSaleMoney()) + getString(R.string.price_unit));
        tvPreviewExchangeCommission.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getPrizeCommission()));
        tvPreviewCommissionRatio.setText(settlementGetDetailBean.getInstantPrize());
        tvPreviewSalesCommission.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getSalesCommission()));
        tvPreviewSalesCommissionRatio.setText(settlementGetDetailBean.getInstantSales());
        tvPreviewTotalCommission.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getCommission()));
//        tvPreviewPayable.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTicketMoney()));
        tvPreviewTotalRewards.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getCashMoney()) + getString(R.string.price_unit));
        if (settlementGetDetailBean.getCashBets().equals("")) {
            tvPreviewCash.setText("0");
        } else {
            tvPreviewCash.setText(settlementGetDetailBean.getCashBets());
        }
        if (settlementGetDetailBean.getSaleBets().equals("")) {
            tvPreviewSale.setText("0");
        } else {
            tvPreviewSale.setText(settlementGetDetailBean.getSaleBets());
        }

        if (settlementGetDetailBean.getSettleStatus().equals("00")) {
            tvPreviewTotalSettlementStatus.setText(getString(R.string.waiting_for_settlement));
//            tvPreviewTotalSettlementStatus.setText(getString(R.string.wait_shenhe));
            tvPreviewTotalSettlementStatus.setTextColor(Color.rgb(0, 165, 83));
        } else {
            tvPreviewTotalSettlementStatus.setText(getString(R.string.settled));
            tvPreviewTotalSettlementStatus.setTextColor(Color.rgb(48, 178, 102));
        }
        if (!settlementGetDetailBean.getAuditTime().equals("")) {
            tvPreviewTotalSettlementTime.setText(settlementGetDetailBean.getAuditTime());
        } else {
            tvPreviewTotalSettlementTime.setText("--");
        }
        if (settlementGetDetailBean.getTotalMoney().equals("0")){
            tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(255, 122, 0));
            tvPreviewTotalSettlementAmount.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTotalMoney()) + getString(R.string.price_unit));
        }else {
            if (settleStatus.equals("00")) {
                if (settlementGetDetailBean.getMoneyStatus().equals("00")) {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(0,75,255));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTotalMoney()) + getString(R.string.price_unit));
                } else {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(253, 8, 8));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTotalMoney()) + getString(R.string.price_unit));
                }
            } else {
                if (settlementGetDetailBean.getMoneyStatus().equals("00")) {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(253, 8, 8));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTotalMoney()) + getString(R.string.price_unit));
                } else {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(0,75,255));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTotalMoney()) + getString(R.string.price_unit));
                }
            }
        }

        tvPreviewTotalSettlementFixedQuota.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getTotalLimit()) + getString(R.string.price_unit));
        tvPreviewTotalSettlementSurplusQuota.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getSurplusAmount()) + getString(R.string.price_unit));
        tvPreviewTotalSettlementUsedQuota.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getUsedAmount()) + getString(R.string.price_unit));
        tvPreviewTotalSettlementSettleQuota.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getSettleQuota()) + getString(R.string.price_unit));

        if (settleStatus.equals("00") && settlementGetDetailBean.getSettleStatus().equals("00")) {
            llySettlementDetailExamine.setVisibility(View.GONE);
        } else {
            llySettlementDetailExamine.setVisibility(View.GONE);
        }
        if (refreshType == 1) {
            bookList.clear();
        }
//        bookList.addAll(settlementGetDetailBean.getGetList());
//        if (receivingActivationAdapter != null){
//            receivingActivationAdapter.notifyDataSetChanged();
//        }else {
//            receivingActivationAdapter = new SettlementDetailAdapter(LotterySettlementDetailActivity.this, settlementGetDetailBean.getGetList());
//            receivingActivationAdapter.setAllGroup(bookList);
//            lvReceivingDetail.setAdapter(receivingActivationAdapter);
//        }
    }

}
