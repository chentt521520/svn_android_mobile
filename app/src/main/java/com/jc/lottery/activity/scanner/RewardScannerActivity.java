package com.jc.lottery.activity.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.content.HttpContext;
import com.jc.lottery.util.ProgressUtil;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑奖页面
 */
public class RewardScannerActivity extends BaseActivity implements OnScannerCompletionListener {

    @BindView(R.id.scanner_view)
    ScannerView scannerView;
    @BindView(R.id.header_type_one_back_arrow)
    ImageView headerTypeOneBackArrow;
    @BindView(R.id.header_type_one_back)
    LinearLayout headerTypeOneBack;
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.rll_scanner_reward)
    RelativeLayout rllScannerReward;
    boolean showThumbnail = false;
    private String entranceType = ""; //1 扫码入口进入

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_scanner;
    }

    @Override
    public void initData() {
        if (getIntent().hasExtra("entranceType")){
            entranceType = getIntent().getStringExtra("entranceType");
        }
        scannerView.setOnScannerCompletionListener(this);

        scannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
//        mScannerView.setDrawText("将二维码放入框内", true);
        scannerView.setDrawText(getResources().getString(R.string.scan_text), true);
        scannerView.setDrawTextColor(Color.WHITE);
        scannerView.setScanMode(BarcodeFormat.values());
//        scannerView.setLaserFrameBoundColor(Color.rgb(0,165,83));
//        scannerView.isScanFullScreen(true);
        //显示扫描成功后的缩略图
        scannerView.isShowResThumbnail(showThumbnail);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.onPause();
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (!rawResult.toString().equals("")) {
            ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
            HttpContext.setCodeQr(rawResult.getText().toString());
            if (entranceType.equals("1")){
                Intent intent = new Intent();
                intent.setClass(this, ManualScannerActivity.class);
                startActivity(intent);
            }
            finish();
//            getWinQueryHttpInfo(rawResult.getText().toString());
        }
    }

    @OnClick({R.id.header_type_one_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_type_one_back:
                finish();
                break;
        }
    }

//    private void getWinQueryHttpInfo(String safetyCode){
//        String account_name = SPUtils.look(this, SPkey.username);
//        String account_password = SPUtils.look(this, SPkey.password);
//        pos_WinQueryInfo pos_instantCashInfo = new pos_WinQueryInfo(account_name, account_password,"3",safetyCode);
//        String s1 = new Gson().toJson(pos_instantCashInfo);
//        OkGo.<String>post(MyUrl.pos_GetWinQueryInfo)
//                .upJson(s1)
//                .execute(new StringCallback() {
//
//                    @Override
//                    public void onSuccess(Response<String> response) {
////                        ToastUtils.showShort("response:" + response.body());
//                        Intent intent = new Intent();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.body());
//                            String winState = jsonObject.getString("winState");
//                            String winMoney = jsonObject.getString("winMoney");
//                            intent.putExtra("winState",winState);
//                            intent.putExtra("winMoney",winMoney);
//                            intent.setClass(RewardScannerActivity.this, RewardScannerDetailActivity.class);
//                            startActivity(intent);
//                            ProgressUtil.dismissProgressDialog();
//                            scannerView.restartPreviewAfterDelay(0);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
////                        ToastUtils.showShort("response:" + response.body());
//                        ProgressUtil.dismissProgressDialog();
//                        scannerView.restartPreviewAfterDelay(0);
//                    }
//                });
//    }

}
