package com.jc.lottery.activity.money;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.MainFragmentTabActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetOrderPay;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单支付页面
 * lr
 */
public class OrderPaymentActivity extends BaseActivity {

    @BindView(R.id.lly_recharge_back)
    LinearLayout llyRechargeBack;
    @BindView(R.id.lly_recharge_one)
    LinearLayout llyRechargeOne;
    @BindView(R.id.lly_recharge_two)
    LinearLayout llyRechargeTwo;
    @BindView(R.id.lly_recharge_three)
    LinearLayout llyRechargeThree;
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
    @BindView(R.id.tv_payment_four)
    TextView tvPaymentFour;
    @BindView(R.id.tv_payment_five)
    TextView tvPaymentFive;
    @BindView(R.id.scb_recharge_four)
    SmoothCheckBox scbRechargeFour;
    @BindView(R.id.lly_recharge_four)
    PercentLinearLayout llyRechargeFour;
    @BindView(R.id.scb_recharge_five)
    SmoothCheckBox scbRechargeFive;
    @BindView(R.id.lly_recharge_five)
    PercentLinearLayout llyRechargeFive;
    @BindView(R.id.tv_payment_order)
    TextView tvPaymentOrder;
    @BindView(R.id.tv_payment_money)
    TextView tvPaymentMoney;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.btn_payment_submit)
    Button btnPaymentSubmit;
    //    @BindView(R.id.btn_lottery_pop_two_dismiss)
//    Button btnLotteryPopTwoDismiss;
    @BindView(R.id.lly_payment_pop_one)
    LinearLayout llyPaymentPopOne;
    @BindView(R.id.lly_payment_pop)
    LinearLayout llyPaymentPop;
    @BindView(R.id.tv_payment_game_name)
    TextView tvPaymentGameName;
    @BindView(R.id.tv_payment_book)
    TextView tvPaymentBook;
    @BindView(R.id.pay_amount)
    TextView pay_amount;
    @BindView(R.id.pay_type)
    TextView pay_type;
    private int rechargeType = 0; // 1:YoPayments 2:card 3:APay
    private String gameName = "";
    private String book = "";
    private String money = "";
    private String order = "";
    private String role = "";
    private String openType = "";
    private String orderCode;
    private String payChannl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_payment;
    }

    @Override
    public void initData() {
        openType = getIntent().getStringExtra("openType");
        gameName = getIntent().getStringExtra("gameName");
        book = getIntent().getStringExtra("book");
        money = getIntent().getStringExtra("money");
        order = getIntent().getStringExtra("order");
        role = SPUtils.look(this, SPkey.roleAlias);
        tvPaymentMoney.setText(MoneyUtil.getIns().GetMoney(money));
        tvPaymentOrder.setText(order);
        tvPaymentGameName.setText(gameName);
        tvPaymentBook.setText(book);
        tvRechargeMoney.setText(MoneyUtil.getIns().GetMoney(money) + getString(R.string.price_unit));
        scbRechargeOne.setClickable(false);
        scbRechargeTwo.setClickable(false);
        scbRechargeThree.setClickable(false);
        scbRechargeFour.setClickable(false);
        scbRechargeFive.setClickable(false);
        scbRechargeOne.setChecked(true, false);
        rechargeType = 0;
        if (role.equals("fxs")) {
            llyRechargeTwo.setVisibility(View.VISIBLE);
            rechargeType = 1;
            initSmoothCheckBox(llyRechargeTwo, tvPaymentTwo, scbRechargeTwo);
        } else if (role.equals("dls")) {
            llyRechargeOne.setVisibility(View.VISIBLE);
            rechargeType = 0;
            initSmoothCheckBox(llyRechargeOne, tvPaymentOne, scbRechargeOne);
        } else {
            llyRechargeOne.setVisibility(View.VISIBLE);
            llyRechargeTwo.setVisibility(View.VISIBLE);
//            llyRechargeThree.setVisibility(View.VISIBLE);
//            llyRechargeFour.setVisibility(View.VISIBLE);
//            llyRechargeFive.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
    }

    @OnClick({R.id.lly_recharge_back, R.id.lly_recharge_one, R.id.lly_recharge_two, R.id.lly_recharge_three, R.id.lly_recharge_four, R.id.lly_recharge_five,
            R.id.btn_payment_submit, R.id.btn_lottery_pop_to_home, R.id.btn_lottery_pop_to_detail, R.id.lly_payment_pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_recharge_back:
                finish();
                break;
            case R.id.lly_recharge_one:
                rechargeType = 0;
                initSmoothCheckBox(llyRechargeOne, tvPaymentOne, scbRechargeOne);
                break;
            case R.id.lly_recharge_two:
                rechargeType = 1;
                initSmoothCheckBox(llyRechargeTwo, tvPaymentTwo, scbRechargeTwo);
                break;
            case R.id.lly_recharge_three:
                rechargeType = 2;
                initSmoothCheckBox(llyRechargeThree, tvPaymentThree, scbRechargeThree);
                break;
            case R.id.lly_recharge_four:
                rechargeType = 3;
                initSmoothCheckBox(llyRechargeFour, tvPaymentFour, scbRechargeFour);
                break;
            case R.id.lly_recharge_five:
                rechargeType = 4;
                initSmoothCheckBox(llyRechargeFive, tvPaymentFive, scbRechargeFive);
                break;
            case R.id.btn_payment_submit:
                orderHttp();
                break;
//            case R.id.btn_lottery_pop_two_dismiss:
//                if (openType.equals("1")) {
//                    LotteryPurchaseActivity.ins.finish();
//                    Intent intent = new Intent();
//                    intent.setClass(this, LotteryPurchaseActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    ReceivingRecordsDetailActivity.ins.showOrder();
//                    finish();
//                }
//                break;
            case R.id.btn_lottery_pop_to_home://返回首页
                Intent intent = new Intent(OrderPaymentActivity.this, MainFragmentTabActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_lottery_pop_to_detail://订单详情
                startActivity(new Intent(OrderPaymentActivity.this,ReceivingRecordsActivity.class));

//                Intent intent2 = new Intent();
//                intent2.putExtra("activityType", payChannl);
//                intent2.putExtra("recordDetailsId", "");
//                intent2.setClass(OrderPaymentActivity.this, ReceivingRecordsDetailActivity.class);
//                startActivity(intent2);
                break;
            case R.id.lly_payment_pop:
                break;
            default:
                break;
        }
    }

    private void orderHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        String device = SPUtils.look(this, SPkey.terminalname);
        final pos_GetOrderPay.OrderData orderData = new pos_GetOrderPay.OrderData(order, "0" + rechargeType);
        pos_GetOrderPay.DataBean dataBean = new pos_GetOrderPay.DataBean(orderData);
        pos_GetOrderPay pos_createOrder = new pos_GetOrderPay(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_createOrder);
        OkGo.<String>post(MyUrl.pos_GetOrderPay)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {

                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                llyPaymentPop.setVisibility(View.VISIBLE);
                                llyPaymentPopOne.setVisibility(View.VISIBLE);
                                pay_amount.setText(MoneyUtil.getIns().GetMoney(money) + "ZWM");

                                // {"code":"00000","data":{"orderData":{"orderCode":"724325486397100032","payChannl":"00"}},"message":"支付成功!","state":"00"}
//                                String data = jsonObject.getString("data");
                                JSONObject orderData = jsonObject.getJSONObject("data").getJSONObject("orderData");
                                orderCode = orderData.getString("orderCode");
                                payChannl = orderData.getString("payChannl");

//   recordDetailsId = jsonObject.getString("receiveId");
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
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initTextView(int type, View view) {
//        tvRechargeOne.setBackgroundResource(R.drawable.shape_bjk_001);
//        tvRechargeTwo.setBackgroundResource(R.drawable.shape_bjk_001);
//        tvRechargeThree.setBackgroundResource(R.drawable.shape_bjk_001);
//        etRechargeFour.setBackgroundResource(R.drawable.shape_bjk_001);
//        tvRechargeOne.setTextColor(Color.rgb(122, 122, 122));
//        tvRechargeTwo.setTextColor(Color.rgb(122, 122, 122));
//        tvRechargeThree.setTextColor(Color.rgb(122, 122, 122));
//        etRechargeFour.setTextColor(Color.rgb(122, 122, 122));
//        if (type == 1) {
//            TextView textView = (TextView) view;
//            textView.setBackgroundResource(R.drawable.recharge_shape_bg);
//            textView.setTextColor(Color.rgb(0,165,83));
//        } else {
//            EditText editText = (EditText) view;
//            editText.setBackgroundResource(R.drawable.recharge_shape_bg);
//            editText.setTextColor(Color.rgb(0,165,83));
//        }
    }

    private void initTextCardView() {
//        tvRechargeCardInformation.setBackgroundResource(R.drawable.shape_bjk_001);
//        tvRechargeCardInformation.setTextColor(Color.rgb(122, 122, 122));
    }

    private void initSmoothCheckBox(LinearLayout llyBg, TextView tvBg, SmoothCheckBox smoothCheckBox) {
        llyRechargeOne.setBackgroundResource(R.drawable.order_payment_select_no_bg);
        llyRechargeTwo.setBackgroundResource(R.drawable.order_payment_select_no_bg);
        llyRechargeThree.setBackgroundResource(R.drawable.order_payment_select_no_bg);
        llyRechargeFour.setBackgroundResource(R.drawable.order_payment_select_no_bg);
        llyRechargeFive.setBackgroundResource(R.drawable.order_payment_select_no_bg);
        tvPaymentOne.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentTwo.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentThree.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentFour.setTextColor(Color.rgb(0, 0, 0));
        tvPaymentFive.setTextColor(Color.rgb(0, 0, 0));
        scbRechargeOne.setChecked(false, false);
        scbRechargeTwo.setChecked(false, false);
        scbRechargeThree.setChecked(false, false);
        scbRechargeFour.setChecked(false, false);
        scbRechargeFive.setChecked(false, false);
        llyBg.setBackgroundResource(R.drawable.order_payment_select_bg);
        tvBg.setTextColor(Color.rgb(0, 75, 255));
        smoothCheckBox.setChecked(true, true);
    }

    private String moneyString(String money) {
//        moneyApay = money + ".00";
//        moneyYoPay = money + "";
        return money + ".00" + getString(R.string.price_unit);
    }

}
