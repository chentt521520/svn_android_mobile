package com.jc.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.VictoryRuleBean;
import com.jc.lottery.bean.req.Req_NoneParm;
import com.jc.lottery.bean.req.TerminalPerQueryInfo;
import com.jc.lottery.bean.req.pos_findRule;
import com.jc.lottery.bean.req.pos_ruleSfcQuery;
import com.jc.lottery.bean.resp.PermissionsListBean;
import com.jc.lottery.bean.resp.Resp_ruleInfo;
import com.jc.lottery.bean.resp.Resp_timeCalibration;
import com.jc.lottery.content.Constant;
import com.jc.lottery.fragment.CashPrizeFragment;
import com.jc.lottery.fragment.LiveBroadcastFragment;
import com.jc.lottery.fragment.MyLotteryFragment;
import com.jc.lottery.fragment.PersonalCenterFragment;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.GetPermissionsUtil;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.RuleUtils;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeManager;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.update.UpdateAppManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 程序主页面
 */
public class MainFragmentTabActivity extends BaseActivity {
    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;
    public static final int FRAGMENT_Five = 4;
    public static final int FRAGMENT_SIX = 5;
    public static final String POSITION = "position";
    @BindView(R.id.ll_gd)
    LinearLayout llGc;
    @BindView(R.id.ll_xy)
    LinearLayout llXy;
    @BindView(R.id.ll_kj)
    LinearLayout llKj;
    @BindView(R.id.ll_wd)
    LinearLayout llWd;
    @BindView(R.id.ll_dj)
    LinearLayout llDj;
    private FragmentManager fragmentManager;
    private View mCurrentTab = null;
    private int position;
    private MyLotteryFragment oneFm;
    private CashPrizeFragment twoFm;
    private PersonalCenterFragment threeFm;
    private LiveBroadcastFragment sixFm;
//    private RewardScannerNewFragment fiveFm;
//    private AccountsFragment fourFm;
    private long firstTime = 0;//记录用户首次点击返回键的时间
    private UpdateAppManager manager;

    @Override
    public void getPreIntent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_main;
    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
        showFragment(FRAGMENT_ONE);

        this.mCurrentTab = this.llGc;
        this.llGc.setSelected(true);
    }

    @Override
    public void initData() {
//        terminalPerQueryInfo();
//        时间校准
        timeCalibration();
////        检查更新
//        checkUpdate();
//        获取规则
//        GetRules(Constant.GAME_ALIAS_K3);
//        GetRules(Constant.GAME_ALIAS_KL8);
//        GetRules(Constant.GAME_ALIAS_36X7);
//        GetRules(Constant.GAME_ALIAS_PL5);
        GetRules(Constant.GAME_ALIAS_37X6);
        GetRules(Constant.GAME_ALIAS_90X5);
        GetRules(Constant.GAME_ALIAS_49X6);
//        GetVictoryRules();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20001) {
            //再次执行安装流程，包含权限判等
            manager.installApk();
        }
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        manager = new UpdateAppManager(MainFragmentTabActivity.this, "main");
        manager.checkUpdate(MainFragmentTabActivity.this);
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //屏幕旋转时记录位置
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //屏幕恢复时取出位置
        showFragment(savedInstanceState.getInt(POSITION));
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void showFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragment(ft);
        //注意这里设置位置
        position = index;
        switch (index) {
            case FRAGMENT_ONE:
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (oneFm == null) {
                    oneFm = new MyLotteryFragment();
                    ft.add(R.id.fl_container, oneFm);
                } else {
                    ft.show(oneFm);
                }
                break;
            case FRAGMENT_TWO:
                if (twoFm == null) {
//                    twoFm = new AwardDetailFragment();
                    twoFm = new CashPrizeFragment();
                    ft.add(R.id.fl_container, twoFm);
                } else {
                    ft.show(twoFm);
                }
                break;
            case FRAGMENT_THREE:
                if (threeFm == null) {
                    threeFm = new PersonalCenterFragment();
                    ft.add(R.id.fl_container, threeFm);
                } else {
                    ft.show(threeFm);
                }
                break;
            case FRAGMENT_FOUR:
//                if (fourFm == null) {
//                    fourFm = new AccountsFragment();
//                    ft.add(R.id.fl_container, fourFm);
//                } else {
//                    ft.show(fourFm);
//                }
                break;
            case FRAGMENT_Five:
//                if (fiveFm == null) {
//                    fiveFm = new RewardScannerNewFragment();
//                    ft.add(R.id.fl_container, fiveFm);
//                } else {
//                    ft.show(fiveFm);
//                }
//                Intent intent = new Intent();
//                intent.setClass(this, ScannerActivity.class);
//                startActivity(intent);
                break;
            case FRAGMENT_SIX:
                if (sixFm == null) {
                    sixFm = new LiveBroadcastFragment();
                    ft.add(R.id.fl_container, sixFm);
                } else {
                    ft.show(sixFm);
                }
                break;
        }

        ft.commit();
    }

    public void hideFragment(FragmentTransaction ft) {
        //如果不为空，就先隐藏起来
        if (oneFm != null) {
            ft.hide(oneFm);
        }
        if (twoFm != null) {
            ft.hide(twoFm);
        }
        if (threeFm != null) {
            ft.hide(threeFm);
        }
        if (sixFm != null) {
            ft.hide(sixFm);
        }
//        if (fourFm != null) {
//            ft.hide(fourFm);
//        }
//        if (fiveFm != null) {
//            ft.hide(fiveFm);
//        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void quit() {
        if (System.currentTimeMillis() - firstTime > 2000) {
            ToastUtils.showShort(getString(R.string.will_exit));
            firstTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void XXX(String messageEvent) {
        LogUtils.e("收到语言变化的通知了");
        finish();
    }

    @OnClick({R.id.ll_gd, R.id.ll_xy, R.id.ll_wd, R.id.ll_kj,R.id.ll_dj})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == this.mCurrentTab.getId()) {
            return;
        }
        this.mCurrentTab.setSelected(false);
        this.mCurrentTab = view;
        view.setSelected(true);
        switch (id) {
            case R.id.ll_gd:
                showFragment(FRAGMENT_ONE);
                break;
            case R.id.ll_dj:
                showFragment(FRAGMENT_TWO);
                break;
            case R.id.ll_wd:
                showFragment(FRAGMENT_THREE);
                break;
            case R.id.ll_xy:
                showFragment(FRAGMENT_FOUR);
                break;
            case R.id.ll_kj:
                showFragment(FRAGMENT_SIX);
                break;
        }
    }

    /**
     * 获取规则
     *
     * @param gameAlias
     */
    private void GetRules(final String gameAlias) {
        String account_name = SPUtils.look(MainFragmentTabActivity.this, SPkey.username, Config.Test_accountName);
        pos_findRule pos_findRule = new pos_findRule(account_name, gameAlias);
        String s = new Gson().toJson(pos_findRule);
        LogUtils.e("  请求参数  " + s);
        OkGo.<String>post(MyUrl.pos_findRule)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 返回内容 " + response.body());
                        Resp_ruleInfo resp_ruleInfo = new Gson().fromJson(response.body(), Resp_ruleInfo.class);
                        List<Resp_ruleInfo.RuleListBean> ruleList = resp_ruleInfo.getRuleList();
                        if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_K3)) {
                            RuleUtils.initK3Rule(ruleList);
                        } else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_37X6)) {
                            RuleUtils.init37x6Rule(ruleList);
                        } else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_90X5)) {
                            RuleUtils.init90x5Rule(ruleList);
                        }else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_49X6)) {
                            RuleUtils.init49x6Rule(ruleList);
                        }
//                        else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_KL8)) {
//                            RuleUtils.initKL8Rule(ruleList);
//                        } else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_PL5)) {
//                            RuleUtils.initPL5Rule(ruleList);
//                        } else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_36X7)) {
//                            RuleUtils.init36x7Rule(ruleList);
//                        }
                    }
                });
    }

    /**
     * 时间校准
     */
    private void timeCalibration() {
        String account_name = SPUtils.look(MainFragmentTabActivity.this, SPkey.username, Config.Test_accountName);
        Req_NoneParm Req_NoneParm = new Req_NoneParm(Constant.ifc_time_calibration, account_name);
        String s = new Gson().toJson(Req_NoneParm);
        LogUtils.e("  时间校准 请求参数  " + s);
        OkGo.<String>post(MyUrl.screen_time_Calibration)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 时间校准  返回内容 " + response.body());
                        try {
                            Resp_timeCalibration resp_timeCalibration = new Gson().fromJson(response.body(), Resp_timeCalibration.class);
                            TimeManager.getInstance().initServerTime(resp_timeCalibration.getTimeStamp());
                            SPUtils.save(MainFragmentTabActivity.this, SPkey.currentTimeMillis, resp_timeCalibration.getTimeStamp() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取权限
     */
    private void terminalPerQueryInfo() {
        String account_name = SPUtils.look(MainFragmentTabActivity.this, SPkey.username, Config.Test_accountName);
        TerminalPerQueryInfo.PermissionsInfo permissionsInfo = new TerminalPerQueryInfo.PermissionsInfo("2");
        TerminalPerQueryInfo.DataBean dataBean = new TerminalPerQueryInfo.DataBean(permissionsInfo);
        TerminalPerQueryInfo terminalPerQueryInfo = new TerminalPerQueryInfo(account_name,dataBean);
        String s = new Gson().toJson(terminalPerQueryInfo);
        LogUtils.e("获取权限  " + s);
        OkGo.<String>post(MyUrl.terminalPerQuery)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 获取权限  返回内容 " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONArray perJson = jsonObject.getJSONArray("permissionsList");
//                            if (perJson.length() > 0) {
                            SPUtils.save(MainFragmentTabActivity.this, SPkey.permissionsList, perJson.toString());
//                            }
                            if(new GetPermissionsUtil().getPermissions("yddjkcjs")||new GetPermissionsUtil().getPermissions("yddjkcdp")||new GetPermissionsUtil().getPermissions("yddjkcjh")||new GetPermissionsUtil().getPermissions("yddlttz")){
                                llGc.setVisibility(View.VISIBLE);
                            }else {
                                llGc.setVisibility(View.GONE);
                                showFragment(FRAGMENT_TWO);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
