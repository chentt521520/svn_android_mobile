package com.jc.lottery.activity.scanner;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.CashPrizeQueryBean;
import com.jc.lottery.bean.req.RedeemQueryBean;
import com.jc.lottery.bean.req.pos_InstantCashInfo;
import com.jc.lottery.bean.req.pos_WinQueryInfo;
import com.jc.lottery.bean.resp.GetCashPrizeBean;
import com.jc.lottery.bean.resp.WinQueryInfo;
import com.jc.lottery.content.HttpContext;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.CommonDialog;
import com.jc.lottery.view.SmoothCheckBox;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑奖页面
 */
public class ManualScannerActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.img_manual_scanner_qr)
    ImageView imgManualScannerQr;
    @BindView(R.id.btn_manual_scanner_submit)
    Button btnManualScannerSubmit;
    @BindView(R.id.et_manual_scanner_one)
    EditText etManualScannerOne;
    @BindView(R.id.et_manual_scanner_two)
    EditText etManualScannerTwo;
    @BindView(R.id.lly_manual_scanner_two)
    PercentLinearLayout llyManualScannerTwo;
    @BindView(R.id.lly_manual_scanner_three)
    PercentLinearLayout llyManualScannerThree;
    @BindView(R.id.scb_scanner_type_one)
    SmoothCheckBox scbScannerTypeOne;
    @BindView(R.id.lly_scanner_type_one)
    LinearLayout llyScannerTypeOne;
    @BindView(R.id.scb_scanner_type_two)
    SmoothCheckBox scbScannerTypeTwo;
    @BindView(R.id.lly_scanner_type_two)
    LinearLayout llyScannerTypeTwo;
    @BindView(R.id.scb_scanner_type_three)
    SmoothCheckBox scbScannerTypeThree;
    @BindView(R.id.lly_scanner_type_three)
    LinearLayout llyScannerTypeThree;
    @BindView(R.id.scb_scanner_type_four)
    SmoothCheckBox scbScannerTypeFour;
    @BindView(R.id.lly_scanner_type_four)
    LinearLayout llyScannerTypeFour;
    private pos_WinQueryInfo.DataBean.OrderInfo orderInfoOne;
    private int cashType = 1; // 1:乐透 2:即开
    private int gameType = 1; // 1:90x5 2:37x6
    private CommonDialog customerDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_manual_scanner;
    }

    @Override
    public void initView() {
        scbScannerTypeOne.setClickable(false);
        scbScannerTypeTwo.setClickable(false);
        scbScannerTypeFour.setClickable(false);
        scbScannerTypeThree.setClickable(false);
        scbScannerTypeOne.setChecked(true);
    }

    @Override
    public void initListener() {
        etManualScannerOne.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        etManualScannerTwo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        etManualScannerOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 20 || s.toString().length() == 30) {
                    llyManualScannerThree.setVisibility(View.GONE);
                    if (s.toString().length() == 20) {
                        llyManualScannerTwo.setVisibility(View.VISIBLE);
//                    showText(s.toString().trim());
                    } else {
                        llyManualScannerTwo.setVisibility(View.GONE);
//                    llyActivationContentNo.setVisibility(View.VISIBLE);
//                    llyActivationContentData.setVisibility(View.GONE);
                    }
                    cashType = 2;
                } else {
                    if (s.toString().length() != 0) {
                        llyManualScannerTwo.setVisibility(View.GONE);
                        cashType = 1;
                        llyManualScannerThree.setVisibility(View.VISIBLE);
                    } else {
                        llyManualScannerTwo.setVisibility(View.GONE);
                        llyManualScannerThree.setVisibility(View.GONE);
                    }

                }
            }
        });

        etManualScannerTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 20) {
//                    showText(s.toString().trim());
                } else {
//                    llyActivationContentNo.setVisibility(View.VISIBLE);
//                    llyActivationContentData.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!HttpContext.getCodeQr().equals("")) {
            String codeQr = HttpContext.getCodeQr();
            if (codeQr.length() > 30) {
                ToastUtils.showShort(getString(R.string.qr_code_format_incorrect));
            } else {
                etManualScannerOne.setText(codeQr);
                if (codeQr.length() == 30) {
                    String terminalNum = SPUtils.look(this, SPkey.userActivationCode);
                    orderInfoOne = new pos_WinQueryInfo.DataBean.OrderInfo(codeQr, "", "", terminalNum);
                    getWinQueryHttpInfo(orderInfoOne);
                }
            }
            HttpContext.setCodeQr("");
        }
        etManualScannerOne.clearFocus();
        etManualScannerTwo.clearFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.img_manual_scanner_qr, R.id.btn_manual_scanner_submit, R.id.lly_scanner_type_one, R.id.lly_scanner_type_two, R.id.lly_scanner_type_three, R.id.lly_scanner_type_four})
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
                String codeQr = etManualScannerOne.getText().toString().trim();
//                if (codeQr.length() == 18){
//                    getVictoryWinQueryHttpInfo(codeQr);
//                    return;
//                }
                if (cashType != 1) {
                    String codeQrTwo = etManualScannerTwo.getText().toString().trim();
                    String terminalNum = SPUtils.look(this, SPkey.userActivationCode);
                    if (codeQr.length() != 0) {
                        if (codeQr.length() != 30) {
                            if (codeQr.length() != 20) {
                                ToastUtils.showShort(getString(R.string.the_security_number_is_incorrect));
                            } else {
                                if (codeQrTwo.length() > 0) {
                                    orderInfoOne = new pos_WinQueryInfo.DataBean.OrderInfo("", codeQrTwo, codeQr, terminalNum);
                                    getWinQueryHttpInfo(orderInfoOne);
                                } else {
                                    ToastUtils.showShort(getString(R.string.please_enter_manual_validation_codes));
                                }
                            }
                        } else {
                            orderInfoOne = new pos_WinQueryInfo.DataBean.OrderInfo(codeQr, "", "", terminalNum);
                            getWinQueryHttpInfo(orderInfoOne);
                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.please_scan_or_enter_the_security_code));
                    }
                } else {
                    if (!codeQr.equals("")) {
                        if (gameType == 1) {
                            getLottoWinQueryHttpInfo("90x5", codeQr);
                        } else if (gameType == 2) {
                            getLottoWinQueryHttpInfo("37x6", codeQr);
                        } else if (gameType == 3) {
                            getVictoryWinQueryHttpInfo(codeQr);
                        } else {
                            getLottoWinQueryHttpInfo("49x6", codeQr);
                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.please_scan_or_enter_the_security_code));
                    }
                }
                break;
            case R.id.lly_scanner_type_one:
                gameType = 1;
                showInitView(gameType);
                break;
            case R.id.lly_scanner_type_two:
                gameType = 2;
                showInitView(gameType);
                break;
            case R.id.lly_scanner_type_three:
                gameType = 3;
                showInitView(gameType);
                break;
            case R.id.lly_scanner_type_four:
                gameType = 4;
                showInitView(gameType);
                break;
        }
    }

    private void showInitView(int type) {
        scbScannerTypeOne.setChecked(false);
        scbScannerTypeTwo.setChecked(false);
        scbScannerTypeThree.setChecked(false);
        scbScannerTypeFour.setChecked(false);
        if (type == 1) {
            scbScannerTypeOne.setChecked(true, true);
        } else if (type == 2) {
            scbScannerTypeTwo.setChecked(true, true);
        } else if (type == 3) {
            scbScannerTypeThree.setChecked(true, true);
        } else {
            scbScannerTypeFour.setChecked(true, true);
        }
    }

    private void getWinQueryHttpInfo(pos_WinQueryInfo.DataBean.OrderInfo orderInfo) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_WinQueryInfo pos_instantCashInfo = new pos_WinQueryInfo(account_name, account_password, "3", orderInfo);
        String s1 = new Gson().toJson(pos_instantCashInfo);
        OkGo.<String>post(MyUrl.pos_GetWinQueryInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                WinQueryInfo winQueryInfo = new Gson().fromJson(response.body(), WinQueryInfo.class);
//                                Intent intent = new Intent();
//                                intent.setClass(ManualScannerActivity.this, ScannerDetailsActivity.class);
//                                intent.putExtra("winQueryInfo", winQueryInfo);
//                                startActivity(intent);
                                showDialog(winQueryInfo);

                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                            ProgressUtil.dismissProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

    private void getLottoWinQueryHttpInfo(final String game, final String orderCode) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        CashPrizeQueryBean.OrderInfo orderInfo = new CashPrizeQueryBean.OrderInfo(game, orderCode);
        CashPrizeQueryBean.DataBean dataBean = new CashPrizeQueryBean.DataBean(orderInfo);
        CashPrizeQueryBean cashPrizeQueryBean = new CashPrizeQueryBean(account_name, dataBean);
        String s1 = new Gson().toJson(cashPrizeQueryBean);
        OkGo.<String>post(MyUrl.cashPrizeQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                GetCashPrizeBean getCashPrizeBean = new Gson().fromJson(response.body(), GetCashPrizeBean.class);
                                ToastUtils.showShort(jsonObject.getString("message"));
                                Intent intent = new Intent();
                                intent.putExtra("gameAlias", game);
                                intent.putExtra("orderCode", orderCode);
                                intent.putExtra("getCashPrizeBean", getCashPrizeBean);
                                intent.setClass(ManualScannerActivity.this, LottoScannerDetailsActivity.class);
                                startActivity(intent);
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                            ProgressUtil.dismissProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

    private void getVictoryWinQueryHttpInfo(final String orderCode) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_pass = SPUtils.look(this, SPkey.password);
        RedeemQueryBean.OrderInfo orderInfo = new RedeemQueryBean.OrderInfo(orderCode);
        RedeemQueryBean.DataBean dataBean = new RedeemQueryBean.DataBean(orderInfo);
        RedeemQueryBean cashPrizeQueryBean = new RedeemQueryBean(account_name, account_pass, dataBean);
        String s1 = new Gson().toJson(cashPrizeQueryBean);
        OkGo.<String>post(MyUrl.pos_victoryRedeemQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }


    private void showDialog(final WinQueryInfo winQueryInfo) {
        if (customerDialog == null) {
            View view = LayoutInflater.from(ManualScannerActivity.this).inflate(R.layout.dialog_redeem, null);
            customerDialog = new CommonDialog.Builder(ManualScannerActivity.this)
                    .setView(view)
                    .size(-1, -2)
                    .create();

            ImageView dialog_close = view.findViewById(R.id.dialog_close);
            TextView dialog_btn = view.findViewById(R.id.dialog_btn);
            TextView dialog_title = view.findViewById(R.id.dialog_title);
            TextView dialog_amount = view.findViewById(R.id.dialog_amount);
            if (winQueryInfo != null)
                dialog_amount.setText(winQueryInfo.getWinMoney());

            dialog_close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    customerDialog.dismiss();
                }
            });

            dialog_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent();
//                                intent.setClass(ManualScannerActivity.this, ScannerDetailsActivity.class);
//                                intent.putExtra("winQueryInfo", winQueryInfo);
//                                startActivity(intent);

                    String terminalNum = SPUtils.look(ManualScannerActivity.this, SPkey.userActivationCode);
                    String accountId = SPUtils.look(ManualScannerActivity.this, SPkey.accountId);
                    pos_InstantCashInfo.DataBean.OrderInfo orderInfo = new pos_InstantCashInfo.DataBean.OrderInfo(Integer.parseInt(accountId), winQueryInfo.getData().getOrderInfo().getSecurityCode(), winQueryInfo.getData().getOrderInfo().getManualCode(), winQueryInfo.getData().getOrderInfo().getLogisticsCode(), terminalNum);
                    getHttpInfo(orderInfo);
                }
            });
        }
        customerDialog.show();
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
//                            if (jsonObject.getString("code").equals("00000")) {
//                                tvScannerDetailsCashState.setText(getString(R.string.yiduijiang));
//                                btnScannerDetailsSubmit.setOnClickListener(null);
//                                btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
//                            }
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


