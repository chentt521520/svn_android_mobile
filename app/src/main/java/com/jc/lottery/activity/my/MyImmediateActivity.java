package com.jc.lottery.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ActivationListActivity;
import com.jc.lottery.activity.immediate.DeliveryRecordsActivity;
import com.jc.lottery.activity.immediate.ImmediateCashRecordActivity;
import com.jc.lottery.activity.immediate.ImmediatelActivationActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.activity.immediate.LotteryPurchaseActivity;
import com.jc.lottery.activity.immediate.OrderTrackActivity;
import com.jc.lottery.activity.immediate.PaymentRecordActivity;
import com.jc.lottery.activity.immediate.ReceivingRecordsActivity;
import com.jc.lottery.activity.immediate.SettlementActivity;
import com.jc.lottery.activity.immediate.StatisticsAmountActivity;
import com.jc.lottery.activity.immediate.StatisticsAmountsActivity;
import com.jc.lottery.activity.immediate.WalletRecordActivity;
import com.jc.lottery.activity.money.RechargeNewActivity;
import com.jc.lottery.activity.scanner.ManualScannerActivity;
import com.jc.lottery.adapter.MyImmediateAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.MyImmediateBean;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.req.pos_GetAmountQuery;
import com.jc.lottery.bean.req.pos_Validation;
import com.jc.lottery.bean.type.EncryptedStateBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.GetPermissionsUtil;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jc.lottery.util.AesOrMd5.md5OrAes;

/**
 * 我的即开彩页面
 */
public class MyImmediateActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.tv_my_immediate_money)
    TextView tvMyImmediateMoney;
    @BindView(R.id.tv_my_immediate_settlement)
    TextView tvMyImmediateSettlement;
    @BindView(R.id.tv_my_immediate_balance)
    TextView tvMyImmediateBalance;
    @BindView(R.id.tv_my_immediate_settled_amount)
    TextView tvMyImmediateSettledAmount;
    @BindView(R.id.header_receiving_one)
    ClassicsHeader headerReceiving;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.rel_my_immediate)
    RecyclerView relMyImmediate;
    @BindView(R.id.rel_my_immediate_one)
    RecyclerView relMyImmediateOne;
    @BindView(R.id.lly_my_immediate_bottom)
    PercentLinearLayout llyMyImmediateBottom;
    @BindView(R.id.lly_my_immediate_bottom_one)
    PercentRelativeLayout llyMyImmediateBottomOne;
    @BindView(R.id.lly_my_immediate_pop)
    LinearLayout llyMyImmediatePop;
    @BindView(R.id.img_my_immediate_no)
    ImageView imgMyImmediateNo;
    @BindView(R.id.bt_my_immediate_pop_yes)
    Button btMyImmediatePopYes;
    @BindView(R.id.bt_my_immediate_pop_no)
    Button btMyImmediatePopNo;
    @BindView(R.id.tv_lock_title)
    TextView tvLockTitle;
    @BindView(R.id.bt_lock_pop_no)
    Button btLockPopNo;
    @BindView(R.id.bt_lock_pop_yes)
    Button btLockPopYes;
    @BindView(R.id.lly_lock_pop)
    LinearLayout llyLockPop;
    @BindView(R.id.img_lock_icon)
    ImageView imgLockIcon;
    @BindView(R.id.et_my_immediate_password)
    EditText etMyImmediatePassword;
    @BindView(R.id.img_my_immediate_lock_two_one)
    ImageView imgMyImmediateLockTwoOne;
    @BindView(R.id.rel_my_immediate_bottom_two_one)
    PercentRelativeLayout relMyImmediateBottomTwoOne;
    @BindView(R.id.img_my_immediate_lock_two_two)
    ImageView imgMyImmediateLockTwoTwo;
    @BindView(R.id.rel_my_immediate_bottom_two_two)
    PercentRelativeLayout relMyImmediateBottomTwoTwo;
    private List<String> nameList = new ArrayList<String>();
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private List<MyImmediateBean> immediateBeanList = new ArrayList<MyImmediateBean>();
    private List<MyImmediateBean> immediateBeanOneList = new ArrayList<MyImmediateBean>();
    private MyImmediateAdapter myImmediateAdapter;
    private MyImmediateAdapter myImmediateAdapterOne;
    private MyImmediateBean myImmediateBeanOne;
    private MyImmediateBean myImmediateBeanTwo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_immediate;
    }

    @Override
    public void initView() {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getAmountQueryInfo();
            }
        });
        relMyImmediateBottomTwoOne.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (myImmediateBeanOne.isState()){
                    showLockView(-2,false,myImmediateBeanOne.getAlias(),getString(R.string.unlock_or_not),"2");
                }else {
                    showLockView(-2,true,myImmediateBeanOne.getAlias(),getString(R.string.lock_or_not),"2");
                }
                return true;
            }
        });
        relMyImmediateBottomTwoTwo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (myImmediateBeanTwo.isState()){
                    showLockView(-3,false,myImmediateBeanTwo.getAlias(),getString(R.string.unlock_or_not),"2");
                }else {
                    showLockView(-3,true,myImmediateBeanTwo.getAlias(),getString(R.string.lock_or_not),"2");
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {
        relMyImmediate.setLayoutManager(new GridLayoutManager(this, 4));
        relMyImmediateOne.setLayoutManager(new GridLayoutManager(this, 5));
        showInfoView();
//        getGameHttpInfo();
        getAmountQueryInfo();
//        getWalleQueryHttpInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.tv_my_immediate_money, R.id.tv_my_immediate_settlement, R.id.bt_my_immediate_pop_yes, R.id.bt_my_immediate_pop_no, R.id.lly_my_immediate_pop, R.id.bt_lock_pop_no, R.id.lly_lock_pop,R.id.rel_my_immediate_bottom_two_one,R.id.rel_my_immediate_bottom_two_two})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.tv_my_immediate_money:
                intent.putExtra("walleType", "00");
                intent.setClass(this, WalletRecordActivity.class);
                startActivity(intent);
//                intent.setClass(this, RechargeNewActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_my_immediate_settlement:
                intent.setClass(this, SettlementActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_my_immediate_pop_no:
                llyMyImmediatePop.setVisibility(View.GONE);
                etMyImmediatePassword.setText("");
                notHide();
                break;
            case R.id.lly_my_immediate_pop:
                llyMyImmediatePop.setVisibility(View.GONE);
                etMyImmediatePassword.setText("");
                notHide();
                break;
            case R.id.lly_lock_pop:
                llyLockPop.setVisibility(View.GONE);
                notHide();
                break;
            case R.id.bt_lock_pop_no:
                llyLockPop.setVisibility(View.GONE);
                notHide();
                break;
            case R.id.rel_my_immediate_bottom_two_one:
                if (myImmediateBeanOne.isState()){
                    showPopView(-2,1,false,"","2");
                }else {
                    showIntent(-2,"2");
                }
                break;
            case R.id.rel_my_immediate_bottom_two_two:
                if (myImmediateBeanTwo.isState()){
                    showPopView(-3,1,false,"","2");
                }else {
                    showIntent(-3,"2");
                }
                break;
        }
    }

    private void notHide() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MyImmediateActivity.this
                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void getGameHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GameQueryInfo pos_gameQueryInfo = new pos_GameQueryInfo(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetGameQueryInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
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
                                        nameList.add(gameListBean.getGameName());
                                        gameListBeans.add(gameListBean);
                                    }
                                }
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

    private void getAmountQueryInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetAmountQuery pos_gameQueryInfo = new pos_GetAmountQuery(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetAmountQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                tvMyImmediateBalance.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("instantBalance")) + getString(R.string.price_unit));
                                tvMyImmediateSettledAmount.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("settleAmount")) + getString(R.string.price_unit));
                                SPUtils.save(MyImmediateActivity.this, SPkey.instantBalance,MoneyUtil.getIns().GetMoney(jsonObject.getString("instantBalance")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                    }
                });
    }

    private void showInfoView() {
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        if (getPermissionsUtil.getPermissions("yddjkcdp")) {
            immediateBeanOneList.add(new MyImmediateBean(4, getString(R.string.ticket_collection), R.drawable.my_jkc_one, isState("yddjkcdp"), "yddjkcdp"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcdj")) {
            immediateBeanOneList.add(new MyImmediateBean(1, getString(R.string.reward), R.drawable.my_jkc_four, isState("yddjkcdj"), "yddjkcdj"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcjh")) {
            immediateBeanOneList.add(new MyImmediateBean(2, getString(R.string.activation), R.drawable.my_jkc_two, isState("yddjkcjh"), "yddjkcjh"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcjs")) {
            immediateBeanOneList.add(new MyImmediateBean(3, getString(R.string.settlement), R.drawable.my_jkc_three, isState("yddjkcjs"), "yddjkcjs"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcdpjl")) {
            immediateBeanList.add(new MyImmediateBean(5, getString(R.string.receiving_records), R.drawable.my_jkc_five, isState("yddjkcdpjl"), "yddjkcdpjl"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcdjjl")) {
            immediateBeanList.add(new MyImmediateBean(6, getString(R.string.reward_record), R.drawable.my_jkc_six, isState("yddjkcdjjl"), "yddjkcdjjl"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcpsjl")) {
            immediateBeanList.add(new MyImmediateBean(7, getString(R.string.delivery_records), R.drawable.delivery_record_icon, isState("yddjkcpsjl"), "yddjkcpsjl"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcczjl")) {
            immediateBeanList.add(new MyImmediateBean(8, getString(R.string.rechargehistory), R.drawable.qbmx, isState("yddjkcczjl"), "yddjkcczjl"));
        }
        if (getPermissionsUtil.getPermissions("yddjkccz")) {
            immediateBeanOneList.add(new MyImmediateBean(9, getString(R.string.recharge), R.drawable.my_jkc_eight, isState("yddjkccz"), "yddjkccz"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcjhjl")) {
            immediateBeanList.add(new MyImmediateBean(12, getString(R.string.activation_record), R.drawable.activation_list_icon, isState("yddjkcjhjl"), "yddjkcjhjl"));
        }
//        immediateBeanList.add(new MyImmediateBean(9, getString(R.string.wallet_details), R.drawable.qbmx));

        if (getPermissionsUtil.getPermissions("yddjkcjsjl")) {
            tvMyImmediateSettlement.setVisibility(View.VISIBLE);
        }
        if (getPermissionsUtil.getPermissions("yddjkcqb")) {
            tvMyImmediateMoney.setVisibility(View.VISIBLE);
        }
        if (immediateBeanList.size() > 0) {
            myImmediateAdapter = new MyImmediateAdapter(this, "0");
            myImmediateAdapter.setList(immediateBeanList);
            relMyImmediate.setAdapter(myImmediateAdapter);
            llyMyImmediateBottom.setVisibility(View.VISIBLE);
        } else {
            llyMyImmediateBottom.setVisibility(View.GONE);
        }
        if (immediateBeanOneList.size() > 0) {
            myImmediateAdapterOne = new MyImmediateAdapter(this, "1");
            myImmediateAdapterOne.setList(immediateBeanOneList);
            relMyImmediateOne.setAdapter(myImmediateAdapterOne);
            llyMyImmediateBottomOne.setVisibility(View.VISIBLE);
        } else {
            llyMyImmediateBottomOne.setVisibility(View.GONE);
        }

        if (getPermissionsUtil.getPermissions("yddjkcxl")) {
//            immediateBeanList.add(new MyImmediateBean(11, getString(R.string.order_tracking), R.drawable.order_track_icon, isState("yddjkcddzz"), "yddjkcddzz"));
            myImmediateBeanOne = new MyImmediateBean(10, getString(R.string.sales_volume_statistics), R.drawable.sjtj, isState("yddjkcxl"), "yddjkcxl");
            relMyImmediateBottomTwoOne.setVisibility(View.VISIBLE);
        }else {
            relMyImmediateBottomTwoOne.setVisibility(View.GONE);
        }

        if (getPermissionsUtil.getPermissions("yddjkcddzz")) {
            relMyImmediateBottomTwoTwo.setVisibility(View.VISIBLE);
            myImmediateBeanTwo = new MyImmediateBean(11, getString(R.string.order_tracking), R.drawable.order_track_icon, isState("yddjkcddzz"), "yddjkcddzz");
//            immediateBeanList.add(new MyImmediateBean(12, getString(R.string.activation_record), R.drawable.activation_list_icon, isState("yddjkcjhjl"), "yddjkcjhjl"));
        }else {
            relMyImmediateBottomTwoTwo.setVisibility(View.GONE);
        }
    }

    public void cashRecordIntent() {
        Intent intent = new Intent();
//        if (nameList.size() > 0) {
//            intent.putStringArrayListExtra("nameList", (ArrayList<String>) nameList);
        intent.setClass(this, ImmediateCashRecordActivity.class);
        startActivity(intent);
//        } else {
//            ToastUtils.showShort(getString(R.string.please_choose_the_game));
//        }
    }

    public void showPopView(final int pos, final int popType, final boolean type, final String name, final String stateType) {
        llyMyImmediatePop.setVisibility(View.VISIBLE);
        btMyImmediatePopYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etMyImmediatePassword.getText().toString().trim();
                if (!password.equals("")) {
                    getValidation(popType, password, pos, type, name, stateType);
                } else {
                    ToastUtils.showShort(getString(R.string.please_enter_secondary_password));
                }
            }
        });
    }

    private boolean isState(String name) {
        String json = SPUtils.look(this, SPkey.encryptedState);
        Type listType = new TypeToken<List<EncryptedStateBean>>() {
        }.getType();
        List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i).isState();
            }
        }
        return false;
    }

    public void showLockView(final int pos, final boolean type, final String name, final String title, final String stateType) {
        llyLockPop.setVisibility(View.VISIBLE);
        tvLockTitle.setText(title);
        if (type) {
            imgLockIcon.setImageResource(R.drawable.suo);
            btLockPopYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stateType.equals("0")) {
                        immediateBeanList.get(pos).setState(type);
                    } else if (stateType.equals("1")){
                        immediateBeanOneList.get(pos).setState(type);
                    }else {
                        if (pos == -2){
                            myImmediateBeanOne.setState(type);
                            if (type){
                                imgMyImmediateLockTwoOne.setVisibility(View.VISIBLE);
                            }else {
                                imgMyImmediateLockTwoOne.setVisibility(View.GONE);
                            }
                        }else {
                            myImmediateBeanTwo.setState(type);
                            if (type){
                                imgMyImmediateLockTwoTwo.setVisibility(View.VISIBLE);
                            }else {
                                imgMyImmediateLockTwoTwo.setVisibility(View.GONE);
                            }
                        }
                    }
                    String json = SPUtils.look(MyImmediateActivity.this, SPkey.encryptedState);
                    Type listType = new TypeToken<List<EncryptedStateBean>>() {
                    }.getType();
                    List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals(name)) {
                            list.get(i).setState(type);
                        }
                    }
                    String s = new Gson().toJson(list);
                    SPUtils.save(MyImmediateActivity.this, SPkey.encryptedState, s);
                    if (stateType.equals("0")) {
                        myImmediateAdapter.notifyItemChanged(pos);
                    } else if(stateType.equals("1")){
                        myImmediateAdapterOne.notifyItemChanged(pos);
                    }
//                    showPopView(pos,2,type,name);
                    llyLockPop.setVisibility(View.GONE);
                }
            });
        } else {
            imgLockIcon.setImageResource(R.drawable.jsuo);
            btLockPopYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopView(pos, 2, type, name, stateType);
                    llyLockPop.setVisibility(View.GONE);
                }
            });
        }

    }

    private void showIntent(int pos, String type) {
        Intent intent = new Intent();
        int id = 0;
        if (type.equals("0")) {
            id = immediateBeanList.get(pos).getId();
        } else if (type.equals("1")){
            id = immediateBeanOneList.get(pos).getId();
        } else {
            if (pos == -2){
                id = myImmediateBeanOne.getId();
            }else {
                id = myImmediateBeanTwo.getId();
            }
        }
        switch (id) {
            case 1:
                intent.setClass(this, ManualScannerActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, ImmediatelActivationActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this, ImmediatelSettlementActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(this, LotteryPurchaseActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.setClass(this, ReceivingRecordsActivity.class);
                startActivity(intent);
                break;
            case 6:
                cashRecordIntent();
                break;
            case 7:
                intent.setClass(this, DeliveryRecordsActivity.class);
                startActivity(intent);
                break;
            case 8:
                intent.setClass(this, PaymentRecordActivity.class);
                startActivity(intent);
                break;
            case 9:
                intent.setClass(this, RechargeNewActivity.class);
                startActivity(intent);
                break;
            case 10:
                intent.setClass(this, StatisticsAmountsActivity.class);
                startActivity(intent);
                break;
            case 11:
                intent.setClass(this, OrderTrackActivity.class);
                startActivity(intent);
                break;
            case 12:
                intent.setClass(this, ActivationListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getValidation(final int popType, String password, final int pos, final boolean type, final String name, final String stateType) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        pos_Validation.UserInfo userInfo = new pos_Validation.UserInfo(md5OrAes(password));
        pos_Validation.DataBean dataBean = new pos_Validation.DataBean(userInfo);
        pos_Validation pos_validation = new pos_Validation(account_name, dataBean);
        String s1 = new Gson().toJson(pos_validation);
        OkGo.<String>post(MyUrl.pos_Validation)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                llyMyImmediatePop.setVisibility(View.GONE);
                                etMyImmediatePassword.setText("");
                                if (popType == 1) {
                                    showIntent(pos, stateType);
                                } else {
                                    if (stateType.equals("0")) {
                                        immediateBeanList.get(pos).setState(type);
                                    } else if (stateType.equals("1")){
                                        immediateBeanOneList.get(pos).setState(type);
                                    }else {
                                        if (pos == -2){
                                            myImmediateBeanOne.setState(type);
                                            if (type){
                                                imgMyImmediateLockTwoOne.setVisibility(View.VISIBLE);
                                            }else {
                                                imgMyImmediateLockTwoOne.setVisibility(View.GONE);
                                            }
                                        }else {
                                            myImmediateBeanTwo.setState(type);
                                            if (type){
                                                imgMyImmediateLockTwoTwo.setVisibility(View.VISIBLE);
                                            }else {
                                                imgMyImmediateLockTwoTwo.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                    String json = SPUtils.look(MyImmediateActivity.this, SPkey.encryptedState);
                                    Type listType = new TypeToken<List<EncryptedStateBean>>() {
                                    }.getType();
                                    List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).getName().equals(name)) {
                                            list.get(i).setState(type);
                                        }
                                    }
                                    String s = new Gson().toJson(list);
                                    SPUtils.save(MyImmediateActivity.this, SPkey.encryptedState, s);
                                    if (stateType.equals("0")) {
                                        myImmediateAdapter.notifyItemChanged(pos);
                                    } else if (stateType.equals("1")){
                                        myImmediateAdapterOne.notifyItemChanged(pos);
                                    }
//                                    myImmediateAdapter.notifyItemChanged(pos);
                                }
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
        notHide();
    }

}


