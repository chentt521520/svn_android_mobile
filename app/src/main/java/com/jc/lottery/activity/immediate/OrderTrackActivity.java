package com.jc.lottery.activity.immediate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.RewardScannerActivity;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetOrderTracking;
import com.jc.lottery.bean.req.pos_GetWalleQueryInfo;
import com.jc.lottery.bean.req.pos_WinQueryInfo;
import com.jc.lottery.content.HttpContext;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单追踪页
 */
public class OrderTrackActivity extends BaseActivity {

    @BindView(R.id.lly_track_back)
    LinearLayout llyTrackBack;
    @BindView(R.id.lly_receiving_nodata)
    LinearLayout llyTrackNodata;
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    @BindView(R.id.header_type_one_panel)
    RelativeLayout headerTypeOnePanel;
    @BindView(R.id.img_receiving_select_order)
    ImageView imgReceivingSelectOrder;
    @BindView(R.id.et_track_select)
    EditText etTrackSelect;
    @BindView(R.id.et_track_select_two)
    EditText etTrackSelectTwo;
    @BindView(R.id.tv_track_select)
    ImageView tvTrackSelect;
    @BindView(R.id.tv_tracking_box_book)
    TextView tvTrackingBoxBook;
    @BindView(R.id.tv_tracking_one)
    TextView tvTrackingOne;
    @BindView(R.id.tv_tracking_two)
    TextView tvTrackingTwo;
    @BindView(R.id.tv_tracking_three)
    TextView tvTrackingThree;
    @BindView(R.id.tv_tracking_four)
    TextView tvTrackingFour;
    @BindView(R.id.tv_tracking_five)
    TextView tvTrackingFive;
    @BindView(R.id.tv_tracking_six)
    TextView tvTrackingSix;
    @BindView(R.id.lly_track_select)
    LinearLayout llyTrackSelect;
    @BindView(R.id.tv_tracking_seven)
    TextView tvTrackingSeven;
    @BindView(R.id.tv_tracking_eight)
    TextView tvTrackingEight;
    @BindView(R.id.tv_tracking_night)
    TextView tvTrackingNight;
    @BindView(R.id.tv_tracking_ten)
    TextView tvTrackingTen;
    @BindView(R.id.tv_tracking_eleven)
    TextView tvTrackingEleven;
    @BindView(R.id.tv_tracking_twelve)
    TextView tvTrackingTwelve;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_tracking;
    }

    @Override
    public void getPreIntent() {
    }

    @Override
    public void initView() {
    }


    @Override
    public void initData() {
//        getHttpInfo();
    }

    @Override
    public void initListener() {
    }


    private void getHttpInfo(final String scheme, final String carton, final String book) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));

        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetOrderTracking.OrderInfo orderInfo = new pos_GetOrderTracking.OrderInfo(scheme, carton, book);
        pos_GetOrderTracking.DataBean dataBean = new pos_GetOrderTracking.DataBean(orderInfo);
        pos_GetOrderTracking pos_getWalleQueryInfo = new pos_GetOrderTracking(account_name,account_password,"3", dataBean);
        String s1 = new Gson().toJson(pos_getWalleQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetOrderTracking)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                tvTrackingBoxBook.setText(scheme + " - " + book);
                                showText(jsonObject);
                            }else {
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
//                        if (codeType){
//                            finish();
//                        }
                    }
                });
    }

    private void showText(JSONObject jsonObject) throws JSONException {
        llyTrackNodata.setVisibility(View.GONE);
        llyTrackSelect.setVisibility(View.VISIBLE);
        JSONObject queryInfo = jsonObject.getJSONObject("queryInfo");

        tvTrackingOne.setText(queryInfo.getString("recipient"));
//        tvTrackingTwo.setText(queryInfo.getString("parentName"));
        if (!queryInfo.has("parentName") || queryInfo.getString("parentName").equals("")){
            tvTrackingTwo.setText("--");
        }else {
            tvTrackingTwo.setText(queryInfo.getString("parentName"));
        }
        if (queryInfo.getString("settleStatus").equals("00")){
            tvTrackingThree.setText(getString(R.string.waiting_for_settlement));
            tvTrackingThree.setTextColor(Color.rgb(255, 102, 0));
        }else if (queryInfo.getString("settleStatus").equals("01")){
            tvTrackingThree.setText(getString(R.string.settled));
            tvTrackingThree.setTextColor(Color.rgb(0,75,255));
        }else {
            tvTrackingThree.setText(getString(R.string.unsettled));
            tvTrackingThree.setTextColor(Color.rgb(253, 8, 8));
        }
        if (queryInfo.getString("settleStatusSj").equals("00")){
            tvTrackingFour.setText(getString(R.string.waiting_for_settlement));
            tvTrackingFour.setTextColor(Color.rgb(255, 102, 0));
        }else if (queryInfo.getString("settleStatusSj").equals("")){
            tvTrackingFour.setText("--");
        }else if (queryInfo.getString("settleStatusSj").equals("01")){
            tvTrackingFour.setText(getString(R.string.settled));
            tvTrackingFour.setTextColor(Color.rgb(0,75,255));
        }else {
            tvTrackingFour.setText(getString(R.string.unsettled));
            tvTrackingFour.setTextColor(Color.rgb(253, 8, 8));
        }
        if (!queryInfo.has("settleTimeZzLqr") || queryInfo.getString("settleTimeZzLqr").equals("") || queryInfo.getString("settleTimeZzLqr").equals("0")){
            tvTrackingFive.setText("--");
        }else {
            tvTrackingFive.setText(timeStamp2Date(Long.parseLong(queryInfo.getString("settleTimeZzLqr"))));
        }
        if (!queryInfo.has("settleTimeSj") || queryInfo.getString("settleTimeSj").equals("") || queryInfo.getString("settleTimeSj").equals("0")){
            tvTrackingSix.setText("--");
        }else {
            tvTrackingSix.setText(timeStamp2Date(Long.parseLong(queryInfo.getString("settleTimeSj"))));
        }
        if (!queryInfo.has("sendPerson") || queryInfo.getString("sendPerson").equals("")){
            tvTrackingSeven.setText("--");
        }else {
            tvTrackingSeven.setText(queryInfo.getString("sendPerson"));
        }
        if (!queryInfo.has("sendTime") || queryInfo.getString("sendTime").equals("") || queryInfo.getString("sendTime").equals("0")){
            tvTrackingEight.setText("--");
        }else {
            tvTrackingEight.setText(timeStamp2Date(Long.parseLong(queryInfo.getString("sendTime"))));
        }
        tvTrackingNight.setText(MoneyUtil.getIns().GetMoney(queryInfo.getString("cashMoneyGly")) + getString(R.string.price_unit));
        tvTrackingTen.setText(MoneyUtil.getIns().GetMoney(queryInfo.getString("cashMoneySj")) + getString(R.string.price_unit));
        tvTrackingEleven.setText(MoneyUtil.getIns().GetMoney(queryInfo.getString("cashMoneyZzLqr")) + getString(R.string.price_unit));
        tvTrackingTwelve.setText(MoneyUtil.getIns().GetMoney(queryInfo.getString("cashMoney")) + getString(R.string.price_unit));

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

    public void showNum(String num){
        String scheme = etTrackSelect.getText().toString().trim();
        if (scheme.length() != 5){
            ToastUtils.showLong(getString(R.string.incorrect_again));
        }else {
            if (num.trim().length() == 7){
                getHttpInfo(scheme,"",num);
            }else {
                ToastUtils.showLong(getString(R.string.incorrect_again));
            }
        }

    }

    @OnClick({R.id.lly_track_back, R.id.tv_track_select,R.id.img_receiving_select_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_track_back:
                finish();
                break;
            case R.id.tv_track_select:
                hideKeyboard(etTrackSelectTwo);
                showNum(etTrackSelectTwo.getText().toString().trim());
                break;
            case R.id.img_receiving_select_order:
                Intent intent = new Intent();
                intent.setClass(this, RewardScannerActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!HttpContext.getCodeQr().equals("")) {
            String codeQr = HttpContext.getCodeQr();
            etTrackSelectTwo.setText(codeQr);
            if (codeQr.length() == 20) {
                showNum(codeQr.substring(10, 17));
            }
            HttpContext.setCodeQr("");
        }
    }

    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
