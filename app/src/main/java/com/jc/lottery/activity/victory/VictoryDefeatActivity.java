package com.jc.lottery.activity.victory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.VictoryAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.VictoryBean;
import com.jc.lottery.bean.VictoryRuleBean;
import com.jc.lottery.bean.req.pos_GetMatchQuery;
import com.jc.lottery.bean.req.pos_ruleSfcQuery;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.FormatUtil;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.RuleUtils;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 胜负彩页
 */
public class VictoryDefeatActivity extends BaseActivity {


    @BindView(R.id.lly_activation_back)
    LinearLayout llyActivationBack;
    @BindView(R.id.tv_victory_name)
    TextView tvVictoryName;
    @BindView(R.id.tv_victory_money)
    TextView tvVictoryMoney;
    @BindView(R.id.tv_victory_time)
    TextView tvVictoryTime;
    @BindView(R.id.tv_victory_ok)
    TextView tvVictoryOk;
    @BindView(R.id.rel_victory)
    RecyclerView relVictory;
    @BindView(R.id.tv_sna)
    TextView tvSna;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.img_del)
    ImageView imgDel;
    @BindView(R.id.btn_select)
    Button btnSelect;
    private VictoryAdapter victoryAdapter = null;
    private VictoryBean victoryBean = null;
    private int sna = 0;
    private int note = 0;
    private int price = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_victory_defeat;
    }

    @Override
    public void initView() {
        relVictory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        showText(0);
        GetVictoryRules();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getHttpInfo() {
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetMatchQuery pos_getRecordInfo = new pos_GetMatchQuery(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetMatchQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                victoryBean = new Gson().fromJson(response.body(), VictoryBean.class);
                                showView();
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

    private void showView() {

        tvVictoryName.setText(victoryBean.getIssueInfo().getIssue_no() + " " + victoryBean.getIssueInfo().getLottery_name());
        tvVictoryMoney.setText(victoryBean.getIssueInfo().getPool_amount());
        tvVictoryTime.setText(timeStamp2Date(Long.parseLong(victoryBean.getIssueInfo().getIssue_end_time())) + "/" + getWeekDay(Long.parseLong(victoryBean.getIssueInfo().getIssue_end_time())));
        for (int i = 0; i < victoryBean.getMatchList().size(); i++) {
            victoryBean.getMatchList().get(i).setType("0");
            List<Boolean> selectOne = new ArrayList<>();
            List<Boolean> selectTwo = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                selectOne.add(false);
            }
            for (int j = 0; j < 9; j++) {
                selectTwo.add(false);
            }
            if (i != 14) {
                victoryBean.getMatchList().get(i).setTypeSelect(selectOne);
            } else {
                victoryBean.getMatchList().get(i).setTypeSelect(selectTwo);
            }

//            if (victoryBean.getMatchList().get(i).getPlay_type().equals("0")){
//                victoryBean.getMatchList().get(i).setTypeSelect(selectOne);
//            }else {
//                victoryBean.getMatchList().get(i).setTypeSelect(selectTwo);
//            }
        }
        victoryAdapter = new VictoryAdapter(this, this);
        victoryAdapter.setList(victoryBean.getMatchList());
        relVictory.setAdapter(victoryAdapter);
    }

    public static String parseServerTime(String date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
//            format = "yyyy-MM-dd";
        }
        if (date.equals("")) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
//            return String.valueOf(sdf.parse(date).getTime() / 1000);
//            return String.valueOf(sdf.parse(date).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(this, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
//            format = "dd-MM-yyyy HH:mm";
            format = "HH:mm";
        } else {
//            format = "yyyy-MM-dd HH:mm";
            format = "HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public String getWeekDay(long seconds) {

        Date date = new Date(seconds);
        String Week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int wek = c.get(Calendar.DAY_OF_WEEK);
        if (wek == 1) {
            Week += getString(R.string.sun);
        }
        if (wek == 2) {
            Week += getString(R.string.mon);
        }
        if (wek == 3) {
            Week += getString(R.string.tue);
        }
        if (wek == 4) {
            Week += getString(R.string.wed);
        }
        if (wek == 5) {
            Week += getString(R.string.thu);
        }
        if (wek == 6) {
            Week += getString(R.string.fri);
        }
        if (wek == 7) {
            Week += getString(R.string.sat);
        }
        return Week;

    }

    @OnClick({R.id.lly_activation_back, R.id.tv_victory_ok, R.id.img_del,R.id.btn_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_activation_back:
                finish();
                break;
            case R.id.tv_victory_ok:
                if (sna > 0) {
                    if (checkOk()) {
                        Intent intent = new Intent();
                        intent.putExtra("sna", note);
                        intent.putExtra("victoryBean", victoryBean);
                        intent.setClass(this, VictoryConfirmActivity.class);
                        startActivity(intent);
                    }
                } else {
                    ToastUtils.showLong(getString(R.string.select_least_game));
                }

                break;
            case R.id.img_del:
                delList();
                break;
            case R.id.btn_select:
                selectList();
                break;
            default:
                break;
        }
    }

    public void showText(int num) {
        if (num != 0) {
            price = Integer.parseInt(victoryBean.getIssueInfo().getUnit_price()) / 100;
            sna = num - 14;
            if (sna == 1) {
                note = 1;
            } else {
                note = 1;
                for (int i = 0; i < sna - 1; i++) {
                    note = note * 2;
                }
//                note = note * 2;
            }
            tvSna.setText(getString(R.string.gong) + note + getString(R.string.zhu_select));
            tvMoney.setText(getString(R.string.gong) + " " + (note * price) + " " + getString(R.string.price_unit));
        } else {
            sna = 0;
            note = 0;
            tvSna.setText(getString(R.string.gong) + 0 + getString(R.string.zhu_select));
            tvMoney.setText(getString(R.string.gong) + " " + 0 + " " + getString(R.string.price_unit));
        }
    }

    public void delList() {
        for (int i = 0; i < victoryBean.getMatchList().size(); i++) {
            for (int j = 0; j < victoryBean.getMatchList().get(i).getTypeSelect().size(); j++) {
                victoryBean.getMatchList().get(i).getTypeSelect().set(j, false);
            }
        }
        victoryAdapter.notifyDataSetChanged();
        showText(0);
    }

    public void selectList(){
        Random random = new Random();
        for (int i = 0; i < victoryBean.getMatchList().size(); i++) {
            for (int j = 0; j < victoryBean.getMatchList().get(i).getTypeSelect().size(); j++) {
                victoryBean.getMatchList().get(i).getTypeSelect().set(j, false);
            }
        }
        for (int i = 0; i < victoryBean.getMatchList().size(); i++) {
            if (i != 14){
                int num = random.nextInt(4 - 1) + 1;
                num = num - 1;
                victoryBean.getMatchList().get(i).getTypeSelect().set(num, true);
            }else {
                int num = 0;
                if (victoryBean.getMatchList().get(i).getPlay_type().equals("1")){
                    num = random.nextInt(7 - 1) + 1;
                    num = num - 1 + 3;
                }else {
                    num = random.nextInt(4 - 1) + 1;
                    num = num - 1;
                }
                victoryBean.getMatchList().get(i).getTypeSelect().set(num, true);
            }
        }
        victoryAdapter.notifyDataSetChanged();
        showText(15);
    }

    private boolean checkOk() {
        if (note > Config.victory_R001_NoteNum_max) {
            ToastUtils.showShort(String.format(getString(R.string.max_can_x_zhu), Config.s37x6_R001_NoteNum_max));
            return false;
        }
        if (note * price > Config.s37x6_R005_NoteMoney_max / 100) {
            ToastUtils.showShort(getString(R.string.max_money) + FormatUtil.addComma(Config.s37x6_R005_NoteMoney_max / 100) + getString(R.string.price_unit));
            return false;
        }
        return true;
    }

    /**
     * 获取胜负彩规则
     */
    private void GetVictoryRules() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username, Config.Test_accountName);
        String account_pass = SPUtils.look(this, SPkey.password, Config.Test_accountName);
        pos_ruleSfcQuery pos_findRule = new pos_ruleSfcQuery(account_name, account_pass);
        String s = new Gson().toJson(pos_findRule);
        LogUtils.e("  请求参数  " + s);
        OkGo.<String>post(MyUrl.pos_ruleSfcQuery)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 返回内容 " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                List<VictoryRuleBean> victoryRuleBeans = new ArrayList<>();
                                Type listType = new TypeToken<List<VictoryRuleBean>>() {
                                }.getType();
                                victoryRuleBeans = new Gson().fromJson(jsonObject.getJSONArray("ruleList").toString(), listType);
                                RuleUtils.victoryRule(victoryRuleBeans);
                                getHttpInfo();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        if (TextUtils.equals( Constant.GAME_ALIAS_K3)) {

//                        }
//                        else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_KL8)) {
//                            RuleUtils.initKL8Rule(ruleList);
//                        } else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_PL5)) {
//                            RuleUtils.initPL5Rule(ruleList);
//                        } else if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_36X7)) {
//                            RuleUtils.init36x7Rule(ruleList);
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

}
