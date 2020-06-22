package com.jc.lottery.activity.immediate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.SettlementDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.SettlementGetDetailBean;
import com.jc.lottery.bean.req.pos_GetSettlementCheck;
import com.jc.lottery.bean.req.pos_GetSettlementDetails;
import com.jc.lottery.bean.resp.ReceivingActivationBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 结算详情页
 */
public class SettlementDetailNewestActivity extends BaseActivity {

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
    @BindView(R.id.lv_receiving_detail)
    ListView lvReceivingDetail;
    @BindView(R.id.lly_receiving_activation)
    LinearLayout llyReceivingActivation;
    @BindView(R.id.tv_preview_total_settlement_status)
    TextView tvPreviewTotalSettlementStatus;
    @BindView(R.id.tv_preview_total_settlement_time)
    TextView tvPreviewTotalSettlementTime;
    @BindView(R.id.tv_preview_book)
    TextView tvPreviewTotalSettlementBook;
    @BindView(R.id.tv_receiving_real_moneys)
    TextView tvPreviewTotalSettlementMoneys;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    private List<ReceivingActivationBean> receivingActivationBeanList = new ArrayList<ReceivingActivationBean>();
    private SettlementDetailAdapter receivingActivationAdapter;
    private int settlementId = 0;
    private String settleStatus = "00";
    private SettlementGetDetailBean settlementGetDetailBean;
    private List<SettlementGetDetailBean.GetList> bookList = new ArrayList<SettlementGetDetailBean.GetList>();
    private int pageNo = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement_detail_newest;
    }

    @Override
    public void getPreIntent() {
        settleStatus = getIntent().getStringExtra("settleStatus");
        settlementId = getIntent().getIntExtra("id", 0);
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
                refreshType = 1;
                pageNo = 1;
                srlReceiving.resetNoMoreData();
                getHttpInfo();
            }
        });
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    getHttpInfo();
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetSettlementDetails.DataBean.SettlementInfo settlementInfo = new pos_GetSettlementDetails.DataBean.SettlementInfo(settlementId + "", pageNo + "");
        pos_GetSettlementDetails pos_getSettlementRecord = new pos_GetSettlementDetails(account_name, account_password, "3", settlementInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.pos_GetSettlementDetails)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            if (pageNo == pageCount){
                                srlReceiving.finishLoadmoreWithNoMoreData();
                            }
                            if (jsonObject.getString("code").equals("00000")) {
                                settlementGetDetailBean = new Gson().fromJson(response.body(), SettlementGetDetailBean.class);
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
                    }
                });
    }

    private void getSettlementCheck() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetSettlementCheck.SettlementInfo settlementInfo = new pos_GetSettlementCheck.SettlementInfo(settlementGetDetailBean.getGetList().get(0).getOrderCode());
        pos_GetSettlementCheck.DataBean dataBean = new pos_GetSettlementCheck.DataBean(settlementInfo);
        pos_GetSettlementCheck pos_getSettlementCheck = new pos_GetSettlementCheck(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getSettlementCheck);
        OkGo.<String>post(MyUrl.pos_GetSettlementCheck)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            ToastUtils.showShort(jsonObject.getString("message"));
                            if (jsonObject.getString("code").equals("00000")) {
                                btSettlementDetailExamine.setBackgroundResource(R.drawable.shape_bg_false);
                                btSettlementDetailExamine.setOnClickListener(null);
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
                getSettlementCheck();
                break;
            default:
                break;
        }
    }

    private void showData() {
        tvPreviewGame.setText(settlementGetDetailBean.getGetList().get(0).getGameName());
        tvPreviewTotalSettlementBook.setText(settlementGetDetailBean.getGetList().get(0).getTicketNum());
        tvPreviewExchangeCommission.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getPrizeCommission()));
        tvPreviewCommissionRatio.setText(settlementGetDetailBean.getGetList().get(0).getInstantPrize());
        tvPreviewSalesCommission.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getSalesCommission()));
        tvPreviewSalesCommissionRatio.setText(settlementGetDetailBean.getGetList().get(0).getInstantSales());
        tvPreviewTotalCommission.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getCommission()));
        tvPreviewPayable.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTicketMoney()) + getString(R.string.price_unit));
        tvPreviewTotalRewards.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getCashMoney()) + getString(R.string.price_unit));
        if (settlementGetDetailBean.getGetList().get(0).getSettleStatus().equals("00")) {
            tvPreviewTotalSettlementStatus.setText(getString(R.string.wait_shenhe));
            tvPreviewTotalSettlementStatus.setTextColor(Color.rgb(0,165,83));
        }else if (settlementGetDetailBean.getGetList().get(0).getSettleStatus().equals("03")){
            tvPreviewTotalSettlementStatus.setText(getString(R.string.rejected));
            tvPreviewTotalSettlementStatus.setTextColor(Color.rgb(0,165,83));
        }else {
            tvPreviewTotalSettlementStatus.setText(getString(R.string.settled));
            tvPreviewTotalSettlementStatus.setTextColor(Color.rgb(48,178,102));
        }
        if (null != settlementGetDetailBean.getGetList().get(0).getAuditTime()) {
            if (!settlementGetDetailBean.getGetList().get(0).getAuditTime().equals("")) {
                tvPreviewTotalSettlementTime.setText(timeStamp2Date(Long.parseLong(settlementGetDetailBean.getGetList().get(0).getAuditTime())));
            } else {
                tvPreviewTotalSettlementTime.setText("--");
            }
        }else {
            tvPreviewTotalSettlementTime.setText("--");
        }
        if (settlementGetDetailBean.getGetList().get(0).getTotalMoney().equals("0")){
            tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(255, 122, 0));
            tvPreviewTotalSettlementAmount.setText(MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
        }else {
            if (settleStatus.equals("00")){
                if (settlementGetDetailBean.getGetList().get(0).getMoneyStatus().equals("00")) {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(0,75,255));
                    tvPreviewTotalSettlementMoneys.setTextColor(Color.rgb(0,75,255));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                    tvPreviewTotalSettlementMoneys.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                } else {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(253, 8, 8));
                    tvPreviewTotalSettlementMoneys.setTextColor(Color.rgb(253, 8, 8));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                    tvPreviewTotalSettlementMoneys.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                }
            }else {
                if (settlementGetDetailBean.getGetList().get(0).getMoneyStatus().equals("00")) {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(253, 8, 8));
                    tvPreviewTotalSettlementMoneys.setTextColor(Color.rgb(253, 8, 8));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                    tvPreviewTotalSettlementMoneys.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                } else {
                    tvPreviewTotalSettlementAmount.setTextColor(Color.rgb(0,75,255));
                    tvPreviewTotalSettlementMoneys.setTextColor(Color.rgb(0,75,255));
                    tvPreviewTotalSettlementAmount.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                    tvPreviewTotalSettlementMoneys.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementGetDetailBean.getGetList().get(0).getTotalMoney()) + getString(R.string.price_unit));
                }
            }
        }
        if (settleStatus.equals("00") && settlementGetDetailBean.getGetList().get(0).getSettleStatus().equals("00")) {
            llySettlementDetailExamine.setVisibility(View.GONE);
        } else {
            llySettlementDetailExamine.setVisibility(View.GONE);
        }
        if (refreshType == 1){
            bookList.clear();
        }
        bookList.addAll(settlementGetDetailBean.getGetList());
        if (receivingActivationAdapter != null){
            receivingActivationAdapter.notifyDataSetChanged();
        }else {
            receivingActivationAdapter = new SettlementDetailAdapter(SettlementDetailNewestActivity.this, settlementGetDetailBean.getGetList());
            receivingActivationAdapter.setAllGroup(bookList);
            lvReceivingDetail.setAdapter(receivingActivationAdapter);
        }
        setListViewHeight(lvReceivingDetail);
    }

    //为listview动态设置高度
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
