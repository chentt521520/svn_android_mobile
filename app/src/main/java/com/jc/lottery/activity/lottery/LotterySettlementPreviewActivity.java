package com.jc.lottery.activity.lottery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.SettleQueryBean;
import com.jc.lottery.bean.req.pos_LottoSettlement;
import com.jc.lottery.bean.resp.SettleQueryInfo;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 即开彩结算预览页面
 */
public class LotterySettlementPreviewActivity extends BaseActivity {

    @BindView(R.id.tv_preview_total_reward)
    TextView tvPreviewTotalReward;
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
    @BindView(R.id.bt_preview_submit)
    Button btPreviewSubmit;
    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.srl_preview)
    SmartRefreshLayout srlPreview;
    @BindView(R.id.tv_preview_total_settlement_start)
    TextView tvPreviewTotalSettlementStart;
    @BindView(R.id.tv_preview_total_settlement_end)
    TextView tvPreviewTotalSettlementEnd;
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
    private Long time;
    private Long endTime;
    private String gameAlias = "";
    private String gameName = "";
    //    private ArrayList<pos_settlementQuery.BookList> list = new ArrayList<pos_settlementQuery.BookList>();
    private SettleQueryInfo settlementQueryBean = null;
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lottery_settlement_preview;
    }

    @Override
    public void getPreIntent() {
    }

    @Override
    public void initData() {
//        getRecordHttp();
//        String bookList = getIntent().getStringExtra("bookList");
//        Type listType = new TypeToken<List<pos_settlementQuery.BookList>>() {
//        }.getType();
        gameAlias = getIntent().getStringExtra("gameAlias");
        gameName = getIntent().getStringExtra("gameName");
        time = getIntent().getLongExtra("time", 0);
        endTime = getIntent().getLongExtra("endTime", 0);
        previewSettlementQueryHttp(gameAlias, endTime);
//        showData();
    }

    private void showData() {
        tvPreviewGame.setText(gameName);
        if (time != 0) {
            String s = time + "";
            if (s.length() < 13) {
                tvPreviewTotalSettlementStart.setText(timeStamp2Date(time * 1000));
            } else {
                tvPreviewTotalSettlementStart.setText(timeStamp2Date(time));
            }
        } else {
            tvPreviewTotalSettlementStart.setText("--");
        }
        tvPreviewTotalSettlementEnd.setText(timeStamp2Date(endTime * 1000));
        tvPreviewPayable.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getSaleMoney()) + getString(R.string.price_unit));
        tvPreviewTotalReward.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCashMoney()) + getString(R.string.price_unit));
        tvPreviewExchangeCommission.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCommissionPrize()));
        tvPreviewCommissionRatio.setText(settlementQueryBean.getLottoPrize());
        tvPreviewSalesCommission.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCommissionSales()));
        tvPreviewSalesCommissionRatio.setText(settlementQueryBean.getLottoSales());
        tvPreviewTotalCommission.setText(settlementQueryBean.getCommission());
//        tvPreviewPayable.setText(settlementQueryBean.getTicketMoney());
        if (settlementQueryBean.getMoneyStatus().equals("00")) {
            tvPreviewTotalSettlementAmount.setText(getString(R.string.accounts_receivable) + MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()) + getString(R.string.price_unit));
        } else {
            tvPreviewTotalSettlementAmount.setText(getString(R.string.payables) + MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()) + getString(R.string.price_unit));
        }
        tvPreviewCash.setText(settlementQueryBean.getCashBets());
        tvPreviewSale.setText(settlementQueryBean.getSaleBets());
        tvPreviewTotalSettlementFixedQuota.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalLimit()) + getString(R.string.price_unit));
        tvPreviewTotalSettlementSurplusQuota.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getSurplusAmount()) + getString(R.string.price_unit));
        tvPreviewTotalSettlementUsedQuota.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getUsedAmount()) + getString(R.string.price_unit));
        tvPreviewTotalSettlementSettleQuota.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getSettleQuota()) + getString(R.string.price_unit));
    }

    @Override
    public void initListener() {
        srlPreview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                previewSettlementQueryHttp(gameAlias, endTime);
            }
        });
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
    }

    //预览查询结算信息
    private void previewSettlementQueryHttp(String gameAlias, Long time) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        SettleQueryBean.SettlementInfo settlementInfo = new SettleQueryBean.SettlementInfo(gameAlias, time);
        SettleQueryBean.DataBean data = new SettleQueryBean.DataBean(settlementInfo);
        SettleQueryBean pos_getBookNumQuery = new SettleQueryBean(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getBookNumQuery);
        OkGo.<String>post(MyUrl.settleQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        srlPreview.finishRefresh();
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                settlementQueryBean = new Gson().fromJson(response.body(), SettleQueryInfo.class);
                                showData();
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        if (codeType) {
                            finish();
                        }
                    }
                });
    }

    //提交结算
    private void settlementSubmitHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
//        String deliveryCode = etSettlementOrder.getText().toString();
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_LottoSettlement.SettlementInfo settlementInfo = new pos_LottoSettlement.SettlementInfo(gameAlias, time + "", endTime + "", settlementQueryBean.getSaleMoney(), settlementQueryBean.getCashMoney(), settlementQueryBean.getCommission(), settlementQueryBean.getCommissionPrize(), settlementQueryBean.getCommissionSales(), settlementQueryBean.getLottoPrize(), settlementQueryBean.getLottoSales(), settlementQueryBean.getCommissionSwitch(), settlementQueryBean.getTotalMoney(), settlementQueryBean.getMoneyStatus(), settlementQueryBean.getCashBets(), settlementQueryBean.getSaleBets(), settlementQueryBean.getTotalLimit(), settlementQueryBean.getSurplusAmount(), settlementQueryBean.getUsedAmount(), settlementQueryBean.getSettleQuota());
        pos_LottoSettlement.Data data = new pos_LottoSettlement.Data(settlementInfo);
        pos_LottoSettlement pos_getSettlement = new pos_LottoSettlement(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getSettlement);
        OkGo.<String>post(MyUrl.settleSettlement)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            ToastUtils.showShort(jsonObject.getString("message"));
                            if (jsonObject.getString("code").equals("00000")) {
                                LotterySettlementsActivity.instance.getBookInfoHttp();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.lly_reward_record_back, R.id.bt_preview_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.bt_preview_submit:
                if (!settlementQueryBean.getSaleMoney().equals("0") || !settlementQueryBean.getTotalMoney().equals("0") || !settlementQueryBean.getCashMoney().equals("0")) {
                    settlementSubmitHttp();
                } else {
                    ToastUtils.showShort(getString(R.string.no_settlement_time));
                }
                break;
        }
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(this, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
//            format = "dd-MM-yyyy HH:mm:ss";
            format = "dd-MM-yyyy";
        } else {
//            format = "yyyy-MM-dd HH:mm:ss";
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}
