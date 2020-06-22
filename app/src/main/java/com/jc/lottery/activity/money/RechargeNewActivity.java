package com.jc.lottery.activity.money;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.BuildConfig;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetRatioBean;
import com.jc.lottery.bean.req.pos_PayRecharge;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.SmoothCheckBox;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值页面
 * lr
 */
//A deposit request has been initiated on your phone!Please enter your pin to confirm the payment.If you already entered it,tap on the "Completed" button to Finish
public class RechargeNewActivity extends BaseActivity {

    @BindView(R.id.lly_recharge_back)
    LinearLayout llyRechargeBack;
    @BindView(R.id.tv_recharge_title)
    TextView tvRechargeTitle;
    @BindView(R.id.tv_recharge_name)
    TextView tvRechargeName;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.btn_recharge_submit)
    Button btnRechargeSubmit;
    @BindView(R.id.tv_recharge_one)
    TextView tvRechargeOne;
    @BindView(R.id.tv_recharge_two)
    TextView tvRechargeTwo;
    @BindView(R.id.tv_recharge_three)
    TextView tvRechargeThree;
    @BindView(R.id.et_recharge_four)
    EditText etRechargeFour;
    @BindView(R.id.lly_recharge_one)
    LinearLayout llyRechargeOne;
    @BindView(R.id.lly_recharge_two)
    LinearLayout llyRechargeTwo;
    @BindView(R.id.lly_recharge_three)
    LinearLayout llyRechargeThree;
    @BindView(R.id.view_recharge_phone)
    View viewRechargePhone;
    //    @BindView(R.id.et_recharge_phone)

//    EditText etRechargePhone;
//    @BindView(R.id.lly_recharge_phone)
//    LinearLayout llyRechargePhone;
    @BindView(R.id.lly_recharge_initialization)
    LinearLayout llyRechargeInitialization;
    @BindView(R.id.et_recharge_card_name)
    EditText etRechargeCardName;
    @BindView(R.id.lly_recharge_card_name)
    LinearLayout llyRechargeCardName;
    @BindView(R.id.et_recharge_card_password)
    EditText etRechargeCardPassword;
    @BindView(R.id.lly_recharge_card_password)
    LinearLayout llyRechargeCardPassword;
    @BindView(R.id.tv_recharge_card_information)
    TextView tvRechargeCardInformation;
    @BindView(R.id.lly_recharge_card_information)
    LinearLayout llyRechargeCardInformation;
    @BindView(R.id.lly_recharge_sms)
    LinearLayout llyRechargeSms;
    @BindView(R.id.lly_recharge_money)
    LinearLayout llyRechargeMoney;
    @BindView(R.id.scb_recharge_one)
    SmoothCheckBox scbRechargeOne;
    @BindView(R.id.scb_recharge_two)
    SmoothCheckBox scbRechargeTwo;
    @BindView(R.id.scb_recharge_three)
    SmoothCheckBox scbRechargeThree;
    @BindView(R.id.tv_payment_one)
    TextView tvPaymentOne;
    @BindView(R.id.tv_payment_two)
    TextView tvPaymentTwo;
    @BindView(R.id.tv_payment_three)
    TextView tvPaymentThree;
//    @BindView(R.id.tv_recharge_six)
//    TextView tvRechargeSix;
    @BindView(R.id.tv_payment_four)
    TextView tvPaymentFour;
    @BindView(R.id.scb_recharge_four)
    SmoothCheckBox scbRechargeFour;
    @BindView(R.id.lly_recharge_four)
    PercentLinearLayout llyRechargeFour;
    @BindView(R.id.view_recharge_phones)
    View viewRechargePhones;
    @BindView(R.id.et_recharge_phones)
    EditText etRechargePhones;
    @BindView(R.id.lly_recharge_phones)
    PercentLinearLayout llyRechargePhones;
    @BindView(R.id.lly_recharge_recharging)
    PercentLinearLayout llyRechargeRecharging;
    @BindView(R.id.rll_recharge_initialization)
    RelativeLayout rllRechargeInitialization;
    @BindView(R.id.tv_recharge_instant)
    TextView tvRechargeInstant;
    @BindView(R.id.tv_recharge_pre_code)
    TextView tvRechargePreCode;
    @BindView(R.id.img_payment_four)
    ImageView imgPaymentFour;
    @BindView(R.id.img_payment_one)
    ImageView imgPaymentOne;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    private int rechargeType = 4; // 1:YoPayments 2:card 3:APay
    private String moneyApay = "5000.00";
    private String moneyYoPay = "5000";
    private String account_name = "";
    private String instant = "";
    private double instantRecharge = 0;
    private String amount = "5000";
    private int cardType = 0;//0:未查询充值卡信息 1:充值卡已充值 2:充值卡可用
    private int typeApay = 0;
    private double moneySubmit = 0;
    private String moneySubmitString = "";
    public static RechargeNewActivity ins;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_news;
    }

    @Override
    public void initData() {
        ins = this;
        account_name = SPUtils.look(this, SPkey.username);
        instant = SPUtils.look(this, SPkey.instantRecharge);
        getRatioHttp();
        tvRechargePreCode.setText("+" + BuildConfig.rechargePreCode + " - ");
        String instantBalance = SPUtils.look(this, SPkey.instantBalance);
        tvBalance.setText(instantBalance + ".00");
//        ToastUtils.showShort(instantRecharge + "比例");
//        instantRechaarge = (instant.replace("%",""));
    }

    @Override
    public void initListener() {
        etRechargeCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 20) {
//                    rechargeCardQueryHttp();
                } else {
                    initTextCardView();
                    tvRechargeCardInformation.setText("0");
                    cardType = 0;
                    tvRechargeMoney.setText(moneyString("0"));
                }
            }
        });
        etRechargeFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equals("")) {
                    tvRechargeMoney.setText(moneyString(etRechargeFour.getText().toString().trim()));
                } else {
                    tvRechargeMoney.setText(moneyString("0"));
                }
            }
        });
        etRechargeFour.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    initTextView(2, tvPaymentFour);
                    if (etRechargeFour.getText().toString().trim().equals("")) {
                        tvRechargeMoney.setText(moneyString("0"));
                    } else {
                        tvRechargeMoney.setText(moneyString(etRechargeFour.getText().toString().trim()));
                    }
                }
            }
        });
        etRechargePhones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 11) {
                    etRechargePhones.setText(str.substring(0, 11)); //截取前x位
                    etRechargePhones.requestFocus();
                    etRechargePhones.setSelection(etRechargePhones.getText().length()); //光标移动到最后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        scbRechargeOne.setClickable(false);
        scbRechargeTwo.setClickable(false);
        scbRechargeThree.setClickable(false);
        scbRechargeFour.setClickable(false);
        scbRechargeFour.setChecked(true);
    }

    @OnClick({R.id.lly_recharge_back, R.id.btn_recharge_submit, R.id.tv_recharge_one, R.id.tv_recharge_two, R.id.tv_recharge_three, R.id.et_recharge_four, R.id.lly_recharge_one, R.id.lly_recharge_two, R.id.lly_recharge_three, R.id.lly_recharge_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_recharge_back:
                finish();
                break;
            case R.id.btn_recharge_submit:
                String phone = etRechargePhones.getText().toString().trim();
                if (TextUtils.isEmpty(phone) && rechargeType != 4) {
                    ToastUtils.showShort(getString(R.string.please_enter_phone_no));
                    return;
                }//线上充值需放开
                phone = BuildConfig.rechargePreCode + phone;
                if (instant.equals("")) {
                    getRatioHttp();
                    ToastUtils.showShort(getString(R.string.getting_recharge_rate_please_wait));
                    return;
                }
                if (Double.parseDouble(moneySubmitString) == 0) {
                    ToastUtils.showShort(getString(R.string.please_recharge_amount_correctly));
                    return;
                }
                switch (rechargeType) {
                    case 1:
//                        ToastUtils.showShort(getString(R.string.online_recharge_temporarily_unavailable));
                        orderHttp("02", moneySubmitString + "", phone);
                        break;
                    case 2:
                        ToastUtils.showShort(getString(R.string.online_recharge_temporarily_unavailable));
//                        orderHttp("03", moneySubmitString + "", phone);
                        break;
                    case 3:
                        ToastUtils.showShort(getString(R.string.online_recharge_temporarily_unavailable));
//                        orderHttp("04", moneySubmitString + "", phone);
                        break;
                    case 4:
                        orderHttp("01", moneySubmitString + "", "");
                        break;
                }
                break;
            case R.id.tv_recharge_one:
                etRechargeFour.setText("");
                initTextView(1, tvRechargeOne);
                tvRechargeMoney.setText(moneyString("5000"));
                break;
            case R.id.tv_recharge_two:
                etRechargeFour.setText("");
                initTextView(1, tvRechargeTwo);
                tvRechargeMoney.setText(moneyString("50000"));
                break;
            case R.id.tv_recharge_three:
                etRechargeFour.setText("");
                initTextView(1, tvRechargeThree);
                tvRechargeMoney.setText(moneyString("100000"));
                break;
            case R.id.et_recharge_four:
                initTextView(2, tvPaymentFour);
                if (etRechargeFour.getText().toString().trim().equals("")) {
                    tvRechargeMoney.setText(moneyString("0"));
                } else {
                    tvRechargeMoney.setText(moneyString(etRechargeFour.getText().toString().trim()));
                }
                break;
            case R.id.lly_recharge_one:
                rechargeType = 1;
                initSmoothCheckBox(llyRechargeOne, tvPaymentOne, scbRechargeOne);
                imgPaymentOne.setImageResource(R.drawable.payment_02);
//                viewRechargePhone.setVisibility(View.VISIBLE);
//                llyRechargePhone.setVisibility(View.VISIBLE);
                break;
            case R.id.lly_recharge_two:
                rechargeType = 2;
                initSmoothCheckBox(llyRechargeTwo, tvPaymentTwo, scbRechargeTwo);
//                llyRechargeSms.setVisibility(View.VISIBLE);
//                llyRechargeMoney.setVisibility(View.GONE);
                break;
            case R.id.lly_recharge_three:
                rechargeType = 3;
                initSmoothCheckBox(llyRechargeThree, tvPaymentThree, scbRechargeThree);
                break;
            case R.id.lly_recharge_four:
                rechargeType = 4;
                initSmoothCheckBox(llyRechargeFour, tvPaymentFour, scbRechargeFour);
                imgPaymentFour.setImageResource(R.drawable.payment_01);
                break;
            default:
                break;
        }
    }

    private void initTextView(int type, View view) {
        tvRechargeOne.setBackgroundResource(R.drawable.shape_bjk_001);
        tvRechargeTwo.setBackgroundResource(R.drawable.shape_bjk_001);
        tvRechargeThree.setBackgroundResource(R.drawable.shape_bjk_001);
//        tvRechargeSix.setBackgroundResource(R.drawable.shape_bjk_001);
        tvRechargeOne.setTextColor(Color.rgb(122, 122, 122));
        tvRechargeTwo.setTextColor(Color.rgb(122, 122, 122));
        tvRechargeThree.setTextColor(Color.rgb(122, 122, 122));
//        tvRechargeSix.setTextColor(Color.rgb(122, 122, 122));
        TextView textView = (TextView) view;
        textView.setBackgroundResource(R.drawable.recharge_shape_bgs);
        textView.setTextColor(Color.rgb(255, 255, 255));
//        if (type == 1) {
//            etRechargeFour.setVisibility(View.GONE);
//        } else {
//            etRechargeFour.setVisibility(View.VISIBLE);
//        }
    }

    private void initTextCardView() {
        tvRechargeCardInformation.setBackgroundResource(R.drawable.shape_bjk_001);
        tvRechargeCardInformation.setTextColor(Color.rgb(122, 122, 122));
    }

    private void initSmoothCheckBox(LinearLayout llyBg, TextView tvBg, SmoothCheckBox smoothCheckBox) {
//        llyRechargeOne.setBackgroundResource(R.drawable.order_payment_select_no_bg);
//        llyRechargeTwo.setBackgroundResource(R.drawable.order_payment_select_no_bg);
//        llyRechargeThree.setBackgroundResource(R.drawable.order_payment_select_no_bg);
//        llyRechargeFour.setBackgroundResource(R.drawable.order_payment_select_no_bg);
        tvPaymentOne.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentTwo.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentThree.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentFour.setTextColor(Color.rgb(0, 0, 0));
        scbRechargeOne.setChecked(false, false);
        scbRechargeTwo.setChecked(false, false);
        scbRechargeThree.setChecked(false, false);
        scbRechargeFour.setChecked(false, false);
        imgPaymentFour.setImageResource(R.drawable.payment_01_no);
        imgPaymentOne.setImageResource(R.drawable.payment_02_no);
//        llyBg.setBackgroundResource(R.drawable.order_payment_select_bg);
        tvBg.setTextColor(Color.rgb(0, 75, 255));
        smoothCheckBox.setChecked(true, true);
        if (rechargeType != 4) {
            llyRechargePhones.setVisibility(View.VISIBLE);
        } else {
            llyRechargePhones.setVisibility(View.GONE);
        }
    }

    private String moneyString(String money) {
        moneyApay = money + ".00";
        moneyYoPay = money + "";
        amount = money;
        moneySubmit = Integer.parseInt(money) * instantRecharge;
        double mon = Integer.parseInt(money) * instantRecharge;
//        moneySubmitString = String.format("%.2f", mon);
        NumberFormat nf = new DecimalFormat("#.##");
        moneySubmitString = nf.format(mon);
        return moneySubmitString + getString(R.string.price_unit);
    }

    private void orderHttp(String payMethod, String payMoney, String phone) {
//        rllRechargeInitialization.setVisibility(View.GONE);
//        llyRechargeRecharging.setVisibility(View.VISIBLE);
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        String device = SPUtils.look(this, SPkey.terminalname);
        pos_PayRecharge.PayInfo payInfo = new pos_PayRecharge.PayInfo("00", payMethod, payMoney, amount, phone);
        pos_PayRecharge.DataBean dataBean = new pos_PayRecharge.DataBean(payInfo);
        pos_PayRecharge pos_payRecharge = new pos_PayRecharge(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_payRecharge);
        OkGo.<String>post(MyUrl.pos_PayRecharge)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                if (rechargeType != 4) {
                                    Intent intent = new Intent();
                                    intent.putExtra("url", jsonObject.getString("payUrl"));
                                    intent.setClass(RechargeNewActivity.this, RechargeNewDetailActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent();
                                    intent.putExtra("type", "1");
                                    intent.setClass(RechargeNewActivity.this, RechargeSuccessActivity.class);
                                    startActivity(intent);
//                                    ToastUtils.showShort(getString(R.string.submit_successfully));
//                                    finish();
                                }
//                                recordDetailsId = jsonObject.getString("receiveId");
                            } else {
//                                rllRechargeInitialization.setVisibility(View.VISIBLE);
//                                llyRechargeRecharging.setVisibility(View.GONE);
                                ToastUtils.showShort(jsonObject.getString("message"));
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

    private void getRatioHttp() {
//        rllRechargeInitialization.setVisibility(View.GONE);
//        llyRechargeRecharging.setVisibility(View.VISIBLE);
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        tvRechargeName.setText(": " + account_name);
        pos_GetRatioBean pos_payRecharge = new pos_GetRatioBean(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_payRecharge);
        OkGo.<String>post(MyUrl.pos_GetRatio)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                instant = jsonObject.getString("instantRecharge");
                                NumberFormat nf = NumberFormat.getPercentInstance();
                                try {
                                    Number m = nf.parse(instant);
                                    instantRecharge = m.doubleValue();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                tvRechargeMoney.setText(moneyString("5000"));
                                tvRechargeInstant.setText(": " + instant);
//                                recordDetailsId = jsonObject.getString("receiveId");
                            } else {
//                                rllRechargeInitialization.setVisibility(View.VISIBLE);
//                                llyRechargeRecharging.setVisibility(View.GONE);
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etRechargePhones.clearFocus();
        etRechargeFour.clearFocus();
    }

    public void showInit() {
        etRechargeFour.setText("");
        initTextView(1, tvRechargeOne);
        tvRechargeMoney.setText(moneyString("5000"));
    }

}
