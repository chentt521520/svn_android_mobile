package com.jc.lottery.activity.scanner;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的乐透页面
 */
public class MyLotteryActivity extends BaseActivity {


    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.img_manual_scanner_qr)
    ImageView imgManualScannerQr;
    @BindView(R.id.btn_manual_scanner_submit)
    Button btnManualScannerSubmit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_manual_scanner;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.img_manual_scanner_qr,R.id.btn_manual_scanner_submit})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.img_manual_scanner_qr:
                intent.setClass(this, RewardScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_manual_scanner_submit:
                intent.setClass(this, ScannerDetailsActivity.class);
                startActivity(intent);
                break;
        }
    }

}


