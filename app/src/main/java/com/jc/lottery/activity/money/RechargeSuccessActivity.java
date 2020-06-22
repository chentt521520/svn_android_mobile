package com.jc.lottery.activity.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.PaymentRecordActivity;
import com.jc.lottery.activity.lottery.LotteryPaymentRecordActivity;
import com.jc.lottery.activity.lottery.RechargeLotteryActivity;
import com.jc.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值详情页面
 * lr
 */
public class RechargeSuccessActivity extends BaseActivity {


    @BindView(R.id.lly_recharge_back)
    LinearLayout llyRechargeBack;
    @BindView(R.id.btn_success_close)
    Button btnSuccessClose;
    @BindView(R.id.btn_btn_success_details)
    Button btnBtnSuccessDetails;
    private String type = "1"; //1:即开 2：乐透

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_success;
    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
//        account_name = SPUtils.look(this, SPkey.username, Config.Test_accountName);
//        tvRechargeName.setText(account_name);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.lly_recharge_back, R.id.btn_success_close, R.id.btn_btn_success_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_recharge_back:
                finish();
                break;
            case R.id.btn_success_close:
                if (type.equals("1")){
                    if (null != RechargeNewActivity.ins) {
                        RechargeNewActivity.ins.showInit();
                    }
                }else {
                    if (null != RechargeLotteryActivity.ins) {
                        RechargeLotteryActivity.ins.showInit();
                    }
                }
                finish();
                break;
            case R.id.btn_btn_success_details:
                Intent intent = new Intent();
                if (type.equals("1")) {
                    intent.setClass(this, PaymentRecordActivity.class);
                }else {
                    intent.setClass(this, LotteryPaymentRecordActivity.class);
                }
                startActivity(intent);
                break;
        }
    }
}
