package com.jc.lottery.activity.my;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.lottery.LotteryPaymentRecordActivity;
import com.jc.lottery.activity.lottery.LotterySettlementsActivity;
import com.jc.lottery.activity.lottery.LotteryWalletRecordActivity;
import com.jc.lottery.activity.lottery.LottoCashRecordActivity;
import com.jc.lottery.activity.lottery.LottoStatisticsAmountActivity;
import com.jc.lottery.activity.lottery.RechargeLotteryActivity;
import com.jc.lottery.activity.lottery.SettlementListActivity;
import com.jc.lottery.activity.scanner.BettingRecordActivity;
import com.jc.lottery.activity.scanner.CashRecordActivity;
import com.jc.lottery.activity.victory.VictorySettlementListActivity;
import com.jc.lottery.activity.victory.VictoryWalletRecordActivity;
import com.jc.lottery.adapter.MyLotteryListAdapter;
import com.jc.lottery.adapter.MyVictoryListAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.MyImmediateBean;
import com.jc.lottery.bean.req.AmountQueryBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jc.lottery.util.AesOrMd5.md5OrAes;

/**
 * 我的胜负彩页面
 */
public class MyVictoryActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.lly_my_lottery_cash)
    PercentLinearLayout llyMyLotteryCash;
    @BindView(R.id.lly_my_lottery_betting)
    PercentLinearLayout llyMyLotteryBetting;
    @BindView(R.id.tv_my_lottery_settlement_list)
    TextView tvMyLotterySettlement;
    @BindView(R.id.lly_my_lottery_settlement)
    PercentLinearLayout llyMyLotterySettlementList;
    @BindView(R.id.tv_lottery_recharge)
    TextView tvLotteryRecharge;
    @BindView(R.id.lly_my_lottery_recharge_record)
    PercentLinearLayout llyMyLotteryRechargeRecord;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.tv_my_lottery_balance)
    TextView tvMyLotteryBalance;
    @BindView(R.id.tv_my_lottery_settle)
    TextView tvMyLotterySettle;
    @BindView(R.id.tv_my_lottery_money)
    TextView tvMyLotteryMoney;
    @BindView(R.id.rel_my_lottery)
    RecyclerView relMyLottery;
    @BindView(R.id.lly_my_lottery_bottom)
    PercentLinearLayout llyMyLotteryBottom;
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
    @BindView(R.id.tv_my_lottery_surplus_quota)
    TextView tvMyLotterySurplusQuota;
    @BindView(R.id.tv_my_lottery_fixed_quota)
    TextView tvMyLotteryFixedQuota;
    @BindView(R.id.lly_lottory_top)
    PercentLinearLayout llyLottoryTop;
    @BindView(R.id.tv_my_lottery_used_quota)
    TextView tvMyLotteryUsedQuota;
    //    @BindView(R.id.tv_my_lottery_balances)
//    TextView tvMyLotteryBalances;
//    @BindView(R.id.tv_my_lottery_moneys)
//    TextView tvMyLotteryMoneys;
//    @BindView(R.id.lly_lottory_top_dls)
//    PercentLinearLayout llyLottoryTopDls;
//    @BindView(R.id.lly_lottory_top_fxs)
//    PercentLinearLayout llyLottoryTopFxs;
    private List<MyImmediateBean> immediateBeanList = new ArrayList<MyImmediateBean>();
    private MyVictoryListAdapter myImmediateAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_victorys;
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

        String role = SPUtils.look(this, SPkey.roleAlias);
//        role = "gly";
//        if (role.equals("gly")) {
//            llyLottoryTop.setVisibility(View.GONE);
//            llyLottoryTopFxs.setVisibility(View.GONE);
//            llyLottoryTopDls.setVisibility(View.VISIBLE);
//        }else {
//            llyLottoryTop.setVisibility(View.VISIBLE);
//            llyLottoryTopFxs.setVisibility(View.VISIBLE);
//            llyLottoryTopDls.setVisibility(View.GONE);
//        }
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getAmountQueryInfo();
            }
        });
    }

    @Override
    public void initData() {
        relMyLottery.setLayoutManager(new GridLayoutManager(this, 4));
        getAmountQueryInfo();
        showInfoView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.lly_my_lottery_cash, R.id.lly_my_lottery_betting, R.id.tv_my_lottery_settlement_list, R.id.lly_my_lottery_settlement, R.id.tv_lottery_recharge, R.id.lly_my_lottery_recharge_record, R.id.tv_my_lottery_money})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.tv_lottery_recharge:
                intent.setClass(this, RechargeLotteryActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_my_lottery_cash:
                intent.setClass(this, CashRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_my_lottery_betting:
                intent.setClass(this, BettingRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_my_lottery_settlement_list:
                intent.setClass(this, VictorySettlementListActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_my_lottery_settlement:
                intent.setClass(this, LotterySettlementsActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_my_lottery_recharge_record:
                intent.putExtra("type","02");
                intent.setClass(this, LotteryPaymentRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_my_lottery_money:
                intent.putExtra("walleType", "01");
                intent.setClass(this, VictoryWalletRecordActivity.class);
                startActivity(intent);
                break;
//            case R.id.tv_my_lottery_moneys:
//                intent.putExtra("walleType", "01");
//                intent.setClass(this, LotteryWalletRecordActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    private void getAmountQueryInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        AmountQueryBean pos_gameQueryInfo = new AmountQueryBean(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.amountQuery)
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
                                tvMyLotteryBalance.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("lottoBalance")) + getString(R.string.price_unit));
//                                tvMyLotteryBalances.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("lottoBalance")) + getString(R.string.price_unit));
//                                tvMyLotterySettle.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("settleAmount")) + getString(R.string.price_unit));
                                tvMyLotterySettle.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("settleAmountSfc")) + getString(R.string.price_unit));
                                String money = MoneyUtil.getIns().GetMoney(jsonObject.getString("surplusLimit"));
                                if (!money.equals("")){
                                    if (Integer.parseInt(money) < 0){
                                        money = "0";
                                    }
                                }
                                tvMyLotterySurplusQuota.setText(money + getString(R.string.price_unit));
                                tvMyLotteryUsedQuota.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("surplusAmount")) + getString(R.string.price_unit));
                                tvMyLotteryFixedQuota.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("totalLimit")) + getString(R.string.price_unit));
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

    public void showLockView(final int pos, final boolean type, final String name, final String title) {
        llyLockPop.setVisibility(View.VISIBLE);
        tvLockTitle.setText(title);
        if (type) {
            imgLockIcon.setImageResource(R.drawable.suo);
            btLockPopYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    immediateBeanList.get(pos).setState(type);
                    String json = SPUtils.look(MyVictoryActivity.this, SPkey.encryptedState);
                    Type listType = new TypeToken<List<EncryptedStateBean>>() {
                    }.getType();
                    List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals(name)) {
                            list.get(i).setState(type);
                        }
                    }
                    String s = new Gson().toJson(list);
                    SPUtils.save(MyVictoryActivity.this, SPkey.encryptedState, s);
                    myImmediateAdapter.notifyItemChanged(pos);
//                    showPopView(pos,2,type,name);
                    llyLockPop.setVisibility(View.GONE);
                }
            });
        } else {
            imgLockIcon.setImageResource(R.drawable.jsuo);
            btLockPopYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopView(pos, 2, type, name);
                    llyLockPop.setVisibility(View.GONE);
                }
            });
        }

    }

    public void showPopView(final int pos, final int popType, final boolean type, final String name) {
        llyMyImmediatePop.setVisibility(View.VISIBLE);
        btMyImmediatePopYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etMyImmediatePassword.getText().toString().trim();
                if (!password.equals("")) {
                    getValidation(popType, password, pos, type, name);
                } else {
                    ToastUtils.showShort(getString(R.string.please_enter_secondary_password));
                }
            }
        });
    }

    private void showInfoView() {
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        if (getPermissionsUtil.getPermissions("yddltczjl")) {
            immediateBeanList.add(new MyImmediateBean(1, getString(R.string.rechargehistory), R.drawable.my_lottory_one, isState("yddltczjl"), "yddltczjl"));
        }
        if (getPermissionsUtil.getPermissions("yddsfcdjjl")) {
            immediateBeanList.add(new MyImmediateBean(2, getString(R.string.reward_record), R.drawable.my_jkc_six, isState("yddsfcdjjl"), "yddsfcdjjl"));
        }
        if (getPermissionsUtil.getPermissions("yddsfctzjl")) {
            immediateBeanList.add(new MyImmediateBean(3, getString(R.string.bethistiory), R.drawable.tzjl, isState("yddsfctzjl"), "yddsfctzjl"));
        }
        if (getPermissionsUtil.getPermissions("yddsfcjs")) {
            immediateBeanList.add(new MyImmediateBean(4, getString(R.string.settlement), R.drawable.my_lottory_three, isState("yddsfcjs"), "yddsfcjs"));
        }
        if (getPermissionsUtil.getPermissions("yddltcz")) {
            immediateBeanList.add(new MyImmediateBean(5, getString(R.string.recharge), R.drawable.my_jkc_eight, isState("yddltcz"), "yddltcz"));
        }
        if (getPermissionsUtil.getPermissions("yddsfcxl")) {
            immediateBeanList.add(new MyImmediateBean(6, getString(R.string.sales_volume_statistics), R.drawable.sjtj, isState("yddsfcxl"), "yddsfcxl"));
        }
        if (getPermissionsUtil.getPermissions("yddsfcjsjl")) {
            tvMyLotterySettlement.setVisibility(View.VISIBLE);
        }
        if (getPermissionsUtil.getPermissions("yddltqb")) {
            tvMyLotteryMoney.setVisibility(View.VISIBLE);
//            tvMyLotteryMoneys.setVisibility(View.VISIBLE);
        }
        if (immediateBeanList.size() > 0) {
            myImmediateAdapter = new MyVictoryListAdapter(this);
            myImmediateAdapter.setList(immediateBeanList);
            relMyLottery.setAdapter(myImmediateAdapter);
            llyMyLotteryBottom.setVisibility(View.VISIBLE);
        } else {
            llyMyLotteryBottom.setVisibility(View.GONE);
        }
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

    private void getValidation(final int popType, String password, final int pos, final boolean type, final String name) {
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
                                    showIntent(pos);
                                } else {
                                    immediateBeanList.get(pos).setState(type);
                                    String json = SPUtils.look(MyVictoryActivity.this, SPkey.encryptedState);
                                    Type listType = new TypeToken<List<EncryptedStateBean>>() {
                                    }.getType();
                                    List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).getName().equals(name)) {
                                            list.get(i).setState(type);
                                        }
                                    }
                                    String s = new Gson().toJson(list);
                                    SPUtils.save(MyVictoryActivity.this, SPkey.encryptedState, s);
                                    myImmediateAdapter.notifyItemChanged(pos);
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
    }

    private void showIntent(int pos) {
        Intent intent = new Intent();
        switch (immediateBeanList.get(pos).getId()) {
            case 1:
                intent.putExtra("type","02");
                intent.setClass(this, LotteryPaymentRecordActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, LottoCashRecordActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this, BettingRecordActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(this, LotterySettlementsActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.setClass(this, RechargeLotteryActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent.setClass(this, LottoStatisticsAmountActivity.class);
                startActivity(intent);
                break;
        }
    }

}