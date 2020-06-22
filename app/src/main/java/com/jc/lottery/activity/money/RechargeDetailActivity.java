package com.jc.lottery.activity.money;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.view.SmoothCheckBox;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值详情页面
 * lr
 */
public class RechargeDetailActivity extends BaseActivity {




    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_new;
    }

    @Override
    public void initData() {
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

}
