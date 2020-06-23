package com.jc.lottery.activity.my;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.activity.MainFragmentTabActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.activity.immediate.SettlementActivity;
import com.jc.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettlementResultActivity extends BaseActivity {

    @BindView(R.id.pay_amount)
    TextView pay_amount;
    @BindView(R.id.pay_type)
    TextView pay_type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement_result;
    }


    @Override
    public void initView() {
        super.initView();
        String amount = getIntent().getStringExtra("amount");
        pay_amount.setText(amount);
    }

    @OnClick({R.id.btn_lottery_pop_to_home, R.id.btn_lottery_pop_to_detail,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_lottery_pop_to_home://返回首页
                Intent intent1 = new Intent(SettlementResultActivity.this, MainFragmentTabActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_lottery_pop_to_detail://订单详情
                startActivity(new Intent(SettlementResultActivity.this, SettlementActivity.class));
                break;
        }
    }
}
