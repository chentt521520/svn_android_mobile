package com.jc.lottery.activity.scanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_InstantCashInfo;
import com.jc.lottery.bean.resp.WinQueryInfo;
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
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑奖详情页面
 */
public class ScannerDetailsActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.lly_scanner_details_money)
    LinearLayout llyScannerDetailsMoney;
    @BindView(R.id.lly_scanner_details_state)
    LinearLayout llyScannerDetailsState;
    @BindView(R.id.btn_scanner_details_submit)
    Button btnScannerDetailsSubmit;
    @BindView(R.id.tv_scanner_details_game)
    TextView tvScannerDetailsGame;
    @BindView(R.id.tv_scanner_details_recipient)
    TextView tvScannerDetailsRecipient;
    @BindView(R.id.tv_scanner_details_recipient_name)
    TextView tvScannerDetailsRecipientName;
    @BindView(R.id.tv_scanner_details_win_state)
    TextView tvScannerDetailsWinState;
    @BindView(R.id.tv_scanner_details_cash_state)
    TextView tvScannerDetailsCashState;
    @BindView(R.id.tv_scanner_details_active_state)
    TextView tvScannerDetailsActiveState;
    @BindView(R.id.tv_scanner_details_order)
    TextView tvScannerDetailsOrder;
    @BindView(R.id.tv_scanner_details_active_money)
    TextView tvScannerDetailsActiveMoney;
    @BindView(R.id.tv_scanner_details_win_states)
    TextView tvScannerDetailsWinStates;
    @BindView(R.id.tv_scanner_details_money)
    TextView tvScannerDetailsMoney;
    @BindView(R.id.lly_scanner_details_one)
    PercentLinearLayout llyScannerDetailsOne;
    @BindView(R.id.lly_scanner_details_two)
    PercentLinearLayout llyScannerDetailsTwo;
    private WinQueryInfo winQueryInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scanner_details;
    }

    @Override
    public void getPreIntent() {
        winQueryInfo = (WinQueryInfo) getIntent().getSerializableExtra("winQueryInfo");
    }

    @Override
    public void initData() {
        showView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.btn_scanner_details_submit})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.btn_scanner_details_submit:
                String terminalNum = SPUtils.look(this, SPkey.userActivationCode);
                String accountId = SPUtils.look(this, SPkey.accountId);
                pos_InstantCashInfo.DataBean.OrderInfo orderInfo = new pos_InstantCashInfo.DataBean.OrderInfo(Integer.parseInt(accountId), winQueryInfo.getData().getOrderInfo().getSecurityCode(), winQueryInfo.getData().getOrderInfo().getManualCode(), winQueryInfo.getData().getOrderInfo().getLogisticsCode(), terminalNum);
                getHttpInfo(orderInfo);
//                intent.setClass(this, RewardRecordDetailActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private void showView() {
        tvScannerDetailsGame.setText(winQueryInfo.getGameName());
        tvScannerDetailsRecipient.setText(winQueryInfo.getRecipient());
        tvScannerDetailsRecipientName.setText(winQueryInfo.getParentName());
        if (winQueryInfo.getWinState().equals("00")) {
            tvScannerDetailsWinState.setText(getString(R.string.zhongjiang));
            tvScannerDetailsWinStates.setText(getString(R.string.zhongjiang));
            tvScannerDetailsWinState.setTextColor(Color.rgb(0, 165, 83));
            tvScannerDetailsWinStates.setTextColor(Color.rgb(0, 165, 83));
        } else {
            tvScannerDetailsWinState.setText(getString(R.string.weizhongjiang));
            tvScannerDetailsWinStates.setText(getString(R.string.weizhongjiang));
            tvScannerDetailsWinState.setTextColor(Color.rgb(48, 178, 102));
            tvScannerDetailsWinStates.setTextColor(Color.rgb(48, 178, 102));
        }
        if (winQueryInfo.getCashState().equals("00")) {
            tvScannerDetailsCashState.setText(getString(R.string.yiduijiang));
            tvScannerDetailsCashState.setTextColor(Color.rgb(0, 165, 83));
        } else {
            tvScannerDetailsCashState.setText(getString(R.string.no_convertibility));
            tvScannerDetailsCashState.setTextColor(Color.rgb(48, 178, 102));
        }
        if (winQueryInfo.getActiveState().equals("00")) {
            tvScannerDetailsActiveState.setText(getString(R.string.not_active));
            tvScannerDetailsActiveState.setTextColor(Color.rgb(0, 165, 83));
            tvScannerDetailsActiveMoney.setText("--");
            llyScannerDetailsOne.setVisibility(View.VISIBLE);
            llyScannerDetailsTwo.setVisibility(View.VISIBLE);
        } else {
            tvScannerDetailsActiveState.setText(getString(R.string.activated));
            tvScannerDetailsActiveState.setTextColor(Color.rgb(48, 178, 102));
            tvScannerDetailsActiveMoney.setText(MoneyUtil.getIns().GetMoney(winQueryInfo.getWinMoney()) + getString(R.string.price_unit));
            llyScannerDetailsMoney.setVisibility(View.VISIBLE);
            tvScannerDetailsMoney.setText(MoneyUtil.getIns().GetMoney(winQueryInfo.getWinMoney()) + getString(R.string.price_unit));
            llyScannerDetailsState.setVisibility(View.VISIBLE);
        }
        if (!winQueryInfo.getWinState().equals("00") || winQueryInfo.getCashState().equals("00")) {
            btnScannerDetailsSubmit.setVisibility(View.GONE);
        }
    }

    private void getHttpInfo(final pos_InstantCashInfo.DataBean.OrderInfo orderInfo) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_InstantCashInfo pos_instantCashInfo = new pos_InstantCashInfo(account_name, account_password, "3", orderInfo);
        String s1 = new Gson().toJson(pos_instantCashInfo);
        OkGo.<String>post(MyUrl.pos_GetCashInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                tvScannerDetailsCashState.setText(getString(R.string.yiduijiang));
                                btnScannerDetailsSubmit.setOnClickListener(null);
                                btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
                            }
                            ToastUtils.showShort(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        ToastUtils.showShort(getString(R.string.checknet));
                    }
                });
    }

}
