package com.jc.lottery.activity.immediate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jc.lottery.R;
import com.jc.lottery.activity.money.OrderPaymentActivity;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.req.pos_CreateOrder;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购彩页面
 */
public class LotteryPurchaseActivity extends BaseActivity {

    @BindView(R.id.lly_lottery_purchase_back)
    LinearLayout llyLotteryPurchaseBack;
    @BindView(R.id.tv_lottery_purchase_title)
    TextView tvLotteryPurchaseTitle;
    @BindView(R.id.sp_lottery_purchase_type)
    Spinner spLotteryPurchaseType;
    @BindView(R.id.lly_lottery_purchase_type)
    LinearLayout llyLotteryPurchaseType;
    @BindView(R.id.tv_lottery_purchase_one)
    TextView tvLotteryPurchaseOne;
    @BindView(R.id.tv_lottery_purchase_two)
    TextView tvLotteryPurchaseTwo;
    @BindView(R.id.tv_lottery_purchase_three)
    TextView tvLotteryPurchaseThree;
    @BindView(R.id.btn_lottery_purchase_submit)
    Button btnLotteryPurchaseSubmit;
    @BindView(R.id.lly_lottery_pop)
    LinearLayout llyLotteryPop;
    @BindView(R.id.btn_lottery_pop_one_submit)
    Button btnLotteryPopOneSubmit;
    @BindView(R.id.btn_lottery_pop_two_submit)
    Button btnLotteryPopTwoSubmit;
    @BindView(R.id.lly_lottery_pop_one)
    LinearLayout llyLotteryPopOne;
    @BindView(R.id.lly_lottery_pop_two)
    LinearLayout llyLotteryPopTwo;
    @BindView(R.id.img_lottery_pop_one)
    ImageView imgLotteryPopOne;
    @BindView(R.id.et_lottery_purchase_six)
    TextView tvLotteryPurchaseSix;
    @BindView(R.id.btn_lottery_pop_three_dismiss)
    Button btnLotteryPopThreeDismiss;
    @BindView(R.id.btn_lottery_pop_three_submit)
    Button btnLotteryPopThreeSubmit;
    @BindView(R.id.lly_lottery_pop_three)
    LinearLayout llyLotteryPopThree;
    @BindView(R.id.tv_lottery_pop_three_type)
    TextView tvLotteryPopThreeType;
    @BindView(R.id.tv_lottery_pop_three_number)
    TextView tvLotteryPopThreeNumber;
    @BindView(R.id.lly_lottery_pop_zoom)
    LinearLayout llyLotteryPopZoom;
    @BindView(R.id.btn_lottery_pop_two_dismiss)
    Button btnLotteryPopTwoDismiss;
    @BindView(R.id.lly_immediate_cash_right)
    LinearLayout llyImmediateCashRight;
    @BindView(R.id.et_lottery_purchase_seven)
    EditText etLotteryPurchaseSeven;
    private ArrayAdapter adapter;
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private List<String> list = new ArrayList<String>();
    private int num = 5;
    private String orderCode = "";
    private String accountId = "";
    private String terminalId = "";
    private String terminalName = "";
    private String account_name = "";
    private String account_password = "";
    private int handleType = 0;
    private Bitmap bitmapQRCode = null;
    private String recordDetailsId = "";
    private String tvType = "1";
    public static LotteryPurchaseActivity ins;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_lottery_purchase;
    }

    @Override
    public void initData() {
        ins = this;
        getHttpInfo();
    }

    @Override
    public void initListener() {
        etLotteryPurchaseSeven.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equals("")) {
                    num = Integer.parseInt(s.toString().trim());
                    if (num > 1000) {
                        etLotteryPurchaseSeven.setText("1000");
                    }
                } else {
                    num = 0;
                }
            }
        });
    }

    public void changeSpinner() {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, list);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        spLotteryPurchaseType.setAdapter(adapter);
    }

    public List<String> getDataSource() {

//        list.add("A");
//        list.add("B");
//        list.add("C");
//        list.add("D");
        return list;
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
////                    mTimer.cancel();
//                    if (handleType == 0) {
////                        recLen = 3;
//                        getStatusHttpInfo();
//                    }
//                    break;
//                case 1:
//                    handleType = 1;
//                    ProgressUtil.dismissProgressDialog();
//                    llyLotteryPopOne.setVisibility(View.GONE);
//                    llyLotteryPopTwo.setVisibility(View.VISIBLE);
//                    break;
//                case 2:
//                    handleType = 1;
//                    break;
//                default:
//                    break;
//            }
//        }
//    };


    @OnClick({R.id.lly_lottery_purchase_back, R.id.tv_lottery_purchase_one, R.id.tv_lottery_purchase_two,
            R.id.tv_lottery_purchase_three, R.id.et_lottery_purchase_six, R.id.btn_lottery_purchase_submit, R.id.btn_lottery_pop_one_submit,
            R.id.btn_lottery_pop_two_submit, R.id.btn_lottery_pop_three_dismiss, R.id.btn_lottery_pop_three_submit,
            R.id.img_lottery_pop_one, R.id.lly_lottery_pop_zoom, R.id.btn_lottery_pop_two_dismiss, R.id.lly_lottery_pop,
            R.id.lly_lottery_pop_three, R.id.lly_lottery_pop_two, R.id.lly_immediate_cash_right})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_lottery_purchase_back:
                finish();
                break;
            case R.id.sp_lottery_purchase_type:
                break;
            case R.id.tv_lottery_purchase_one:
                etLotteryPurchaseSeven.setText("");
                num = 5;
                tvType = "1";
                initTextView(1, tvLotteryPurchaseOne);
                break;
            case R.id.tv_lottery_purchase_two:
                etLotteryPurchaseSeven.setText("");
                num = 10;
                tvType = "1";
                initTextView(1, tvLotteryPurchaseTwo);
                break;
            case R.id.tv_lottery_purchase_three:
                etLotteryPurchaseSeven.setText("");
                num = 15;
                tvType = "1";
                initTextView(1, tvLotteryPurchaseThree);
                break;
            case R.id.et_lottery_purchase_six:
                if (etLotteryPurchaseSeven.getText().toString().equals("")) {
                    num = 0;
                }
                tvType = "2";
                initTextView(1, tvLotteryPurchaseSix);
                etLotteryPurchaseSeven.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_lottery_purchase_submit:
                hideKeyboard(etLotteryPurchaseSeven);
                if (num != 0 && gameListBeans.size() != 0) {
//                    generateData();
                    tvLotteryPopThreeType.setText(gameListBeans.get(spLotteryPurchaseType.getSelectedItemPosition()).getGameName());
                    tvLotteryPopThreeNumber.setText(num + "");
                    llyLotteryPop.setVisibility(View.VISIBLE);
//                    llyLotteryPopOne.setVisibility(View.VISIBLE);
                    llyLotteryPopThree.setVisibility(View.VISIBLE);
                } else if (num == 0) {
                    ToastUtils.showShort(getString(R.string.please_select_the_correct_number));
                } else {
                    ToastUtils.showShort(getString(R.string.please_choose_the_game));
                }
                break;
            case R.id.btn_lottery_pop_one_submit:
                llyLotteryPopOne.setVisibility(View.GONE);
                llyLotteryPopTwo.setVisibility(View.GONE);
                llyLotteryPop.setVisibility(View.GONE);
//                handler.sendEmptyMessage(2);
                ProgressUtil.dismissProgressDialog();
                break;
            case R.id.btn_lottery_pop_two_submit:
                llyLotteryPopTwo.setVisibility(View.GONE);
                llyLotteryPop.setVisibility(View.GONE);
                intent.setClass(this, ReceivingRecordsDetailActivity.class);
                intent.putExtra("recordDetailsId", recordDetailsId);
                startActivity(intent);
                break;
            case R.id.btn_lottery_pop_three_dismiss:
                llyLotteryPop.setVisibility(View.GONE);
                llyLotteryPopThree.setVisibility(View.GONE);
                break;
            case R.id.btn_lottery_pop_three_submit:
                handleType = 0;
//                generateData();
//                llyLotteryPopThree.setVisibility(View.GONE);
//                llyLotteryPopOne.setVisibility(View.VISIBLE);
//                getStatusHttpInfo();
                orderHttp();
                break;
            case R.id.btn_lottery_pop_two_dismiss:
//                intent.setClass(this,ReceivingRecordsActivity.class);
//                startActivity(intent);
                llyLotteryPop.setVisibility(View.GONE);
                llyLotteryPopOne.setVisibility(View.GONE);
                llyLotteryPopTwo.setVisibility(View.GONE);
                break;
            case R.id.lly_lottery_pop:
                llyLotteryPop.setVisibility(View.GONE);
                llyLotteryPopOne.setVisibility(View.GONE);
                llyLotteryPopTwo.setVisibility(View.GONE);
                break;
            case R.id.lly_lottery_pop_three:
                break;
            case R.id.lly_lottery_pop_two:
                break;
            case R.id.lly_immediate_cash_right:
                intent.setClass(this, ReceivingRecordsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        etLotteryPurchaseSeven.setText("");
        num = 5;
        tvType = "1";
        initTextView(1, tvLotteryPurchaseOne);
    }

    private void initTextView(int type, View view) {
        tvLotteryPurchaseOne.setBackgroundResource(R.drawable.shape_bjk_001);
        tvLotteryPurchaseTwo.setBackgroundResource(R.drawable.shape_bjk_001);
        tvLotteryPurchaseThree.setBackgroundResource(R.drawable.shape_bjk_001);
        tvLotteryPurchaseSix.setBackgroundResource(R.drawable.shape_bjk_001);
        tvLotteryPurchaseOne.setTextColor(Color.rgb(122, 122, 122));
        tvLotteryPurchaseTwo.setTextColor(Color.rgb(122, 122, 122));
        tvLotteryPurchaseThree.setTextColor(Color.rgb(122, 122, 122));
        tvLotteryPurchaseSix.setTextColor(Color.rgb(122, 122, 122));
        etLotteryPurchaseSeven.setVisibility(View.INVISIBLE);
        if (type == 1) {
            TextView textView = (TextView) view;
            textView.setBackgroundResource(R.drawable.recharge_shape_bgs);
            textView.setTextColor(Color.rgb(255, 255, 255));
        } else {
            EditText editText = (EditText) view;
            editText.setBackgroundResource(R.drawable.recharge_shape_bgs);
            editText.setTextColor(Color.rgb(255, 255, 255));
        }
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        account_name = SPUtils.look(this, SPkey.username);
        account_password = SPUtils.look(this, SPkey.password);
        pos_GameQueryInfo pos_gameQueryInfo = new pos_GameQueryInfo(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetGameQueryInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
//                        ToastUtils.showShort("response:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                JSONArray gameList = jsonObject.getJSONArray("gameList");
                                for (int i = 0; i < gameList.length(); i++) {
                                    JSONObject json = gameList.getJSONObject(i);
                                    GameListBean gameListBean = new GameListBean();
                                    gameListBean.setGameName(json.getString("gameName"));
                                    gameListBean.setGameAlias(json.getString("gameAlias"));
                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
                                    gameListBean.setEnabled(json.getString("enabled"));
                                    if (gameListBean.getEnabled().equals("00")) {
                                        list.add(gameListBean.getGameName());
                                        gameListBeans.add(gameListBean);
                                    }
                                }
                                changeSpinner();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        if (response == null || response.message() == null) {
                            ToastUtils.showShort(getString(R.string.checknet));
                        }
                        finish();
                    }
                });
    }

    private void orderHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        generateData();
        account_name = SPUtils.look(this, SPkey.username);
        account_password = SPUtils.look(this, SPkey.password);
        String device = SPUtils.look(this, SPkey.terminalname);
        pos_CreateOrder.DataBean.DeliveryInfo deliveryInfo = new pos_CreateOrder.DataBean.DeliveryInfo();
        deliveryInfo.setGameAlias(gameListBeans.get(spLotteryPurchaseType.getSelectedItemPosition()).getGameAlias());
        deliveryInfo.setTicketNum(num + "");
//        deliveryInfo.setRecipientId(accountId);
//        deliveryInfo.setRecipient(account_name);
        deliveryInfo.setGetdeviceId(terminalId);
        deliveryInfo.setGetdevice(device);
//        deliveryInfo.setPayState("02");
//        deliveryInfo.setOrderCode(orderCode);
        pos_CreateOrder pos_createOrder = new pos_CreateOrder(account_name, account_password, "3", deliveryInfo);
        String s1 = new Gson().toJson(pos_createOrder);
        OkGo.<String>post(MyUrl.pos_CreateOrderInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
//                                recordDetailsId = jsonObject.getString("receiveId");
                                llyLotteryPopThree.setVisibility(View.GONE);
                                llyLotteryPop.setVisibility(View.GONE);
//                                llyLotteryPopTwo.setVisibility(View.VISIBLE);
                                Intent intent = new Intent();
                                intent.putExtra("openType","1");
                                intent.putExtra("money",jsonObject.getString("totalMoney"));
                                intent.putExtra("order", jsonObject.getString("orderCode"));
                                intent.putExtra("gameName", gameListBeans.get(spLotteryPurchaseType.getSelectedItemPosition()).getGameName());
                                intent.putExtra("book", num + "");
                                intent.setClass(LotteryPurchaseActivity.this, OrderPaymentActivity.class);
                                startActivity(intent);
//                                if (tvType.equals("2")){
//                                    etLotteryPurchaseSeven.setText("");
//                                }
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

    private void generateData() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssss");
        String temp = sf.format(new Date());
        int random = (int) (Math.random() * 100000000);
        orderCode = temp + random;
        accountId = SPUtils.look(this, SPkey.accountId);
        terminalId = SPUtils.look(this, SPkey.terminalId);
//        DeliveryBean deliveryBean = new DeliveryBean();
//        deliveryBean.setGameAlias(gameListBeans.get(spLotteryPurchaseType.getSelectedItemPosition()).getGameAlias());
//        deliveryBean.setTicketNum(num + "");
//        deliveryBean.setRecipientId(accountId);
//        deliveryBean.setGetDeviceId(terminalId);
//        deliveryBean.setPayState("00");
//        deliveryBean.setGetChannels("3");
//        deliveryBean.setOrderCode(orderCode);
//        deliveryBean.setType("get");
//        String deliveryJson = new Gson().toJson(deliveryBean);
//        try {
//            // 根据字符串生成二维码图片并显示在界面上，
//            bitmapQRCode = createQRCode(deliveryJson, DensityUtils.dip2px(this, 140));
//            imgLotteryPopOne.setImageBitmap(bitmapQRCode);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
    }

    private static final int BLACK = 0xff000000; //二维码颜色

    public static Bitmap createQRCode(String str, int widthAndHeight)
            throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
