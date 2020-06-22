package com.jc.lottery.activity.victory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jc.lottery.R;
import com.jc.lottery.activity.lottery.BettingSuccessActivity;
import com.jc.lottery.activity.lottery._37x6BettingActivity;
import com.jc.lottery.adapter.VictoryConfirmAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.TicketListBean;
import com.jc.lottery.bean.VictoryBean;
import com.jc.lottery.bean.req.pos_36x7order;
import com.jc.lottery.bean.req.pos_GetMatchQuery;
import com.jc.lottery.bean.req.pos_print_Notice;
import com.jc.lottery.bean.req.pos_victoryBetting;
import com.jc.lottery.bean.req.pos_victory_print_Notice;
import com.jc.lottery.bean.resp.Resp_Order_Success;
import com.jc.lottery.bean.resp.Resp_kuai3_Notice;
import com.jc.lottery.bean.type.Betting;
import com.jc.lottery.bean.type.Payment;
import com.jc.lottery.content.Constant;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.DialogInterface;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.DeviceConnFactoryManager;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.PrintDeviceUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 胜负彩页
 */
public class VictoryConfirmActivity extends BaseActivity {


    @BindView(R.id.lly_activation_back)
    LinearLayout llyActivationBack;
    @BindView(R.id.tv_victory_name)
    TextView tvVictoryName;
    @BindView(R.id.tv_victory_money)
    TextView tvVictoryMoney;
    @BindView(R.id.tv_victory_time)
    TextView tvVictoryTime;
    //    @BindView(R.id.tv_victory_ok)
//    TextView tvVictoryOk;
    @BindView(R.id.rel_victory)
    RecyclerView relVictory;
    @BindView(R.id.tv_sna)
    TextView tvSna;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.button_add)
    Button button_add;
    private VictoryConfirmAdapter victoryAdapter = null;
    private VictoryBean victoryBean = null;
    private int sna = 0;
    private int price = 0;
    private int id = 0;
    private boolean isShowConnectDialog = false;// 这次订单是否展示过连接打印机弹窗了


    @Override
    public int getLayoutId() {
        return R.layout.activity_victory_confirm;
    }

    @Override
    public void getPreIntent() {
        victoryBean = (VictoryBean) getIntent().getSerializableExtra("victoryBean");
        sna = getIntent().getIntExtra("sna", 0);
        price = Integer.parseInt(victoryBean.getIssueInfo().getUnit_price());
    }

    @Override
    public void initView() {
        relVictory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
//        getHttpInfo();
        showText();
        showView();
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
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetMatchQuery pos_getRecordInfo = new pos_GetMatchQuery(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetMatchQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
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

                    }
                });
    }

    private void showView() {

        tvVictoryName.setText(victoryBean.getIssueInfo().getIssue_no() + " " + victoryBean.getIssueInfo().getLottery_name());
        tvVictoryMoney.setText(victoryBean.getIssueInfo().getPool_amount());
        tvVictoryTime.setText(timeStamp2Date(Long.parseLong(victoryBean.getIssueInfo().getIssue_end_time())) + "/" + getWeekDay(Long.parseLong(victoryBean.getIssueInfo().getIssue_end_time())));
//        for (int i = 0; i < victoryBean.getMatchList().size(); i++) {
//            victoryBean.getMatchList().get(i).setType("0");
//            List<Boolean> selectOne = new ArrayList<>();
//            List<Boolean> selectTwo = new ArrayList<>();
//            for (int j = 0; j < 3; j++) {
//                selectOne.add(false);
//            }
//            for (int j = 0; j < 6; j++) {
//                selectTwo.add(false);
//            }
//            if (victoryBean.getMatchList().get(i).getPlay_type().equals("0")){
//                victoryBean.getMatchList().get(i).setTypeSelect(selectOne);
//            }else {
//                victoryBean.getMatchList().get(i).setTypeSelect(selectTwo);
//            }
//        }
        victoryAdapter = new VictoryConfirmAdapter(this);
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

    @OnClick({R.id.lly_activation_back,R.id.button_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_activation_back:
                finish();
                break;
            case R.id.button_add:
                CreateOrder();
                break;
        }
    }

    public void showText() {
        tvSna.setText(getString(R.string.gong) + sna + getString(R.string.zhu_select));
        tvMoney.setText(getString(R.string.gong) + " " + (sna * price / 100) + " " + getString(R.string.price_unit));
    }

    private void CreateOrder() {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
            if (Config.MUST_NEED_PRINTER) {
//            if (false) {
                PrintDeviceUtil.connDevice(this, null);
                return;
            } else {
                if (!isShowConnectDialog) {
                    PrintDeviceUtil.connDevice(this, new DialogInterface() {
                        @Override
                        public void postive() {
                            CreateOrder();
                            isShowConnectDialog = false;
                        }

                        @Override
                        public void negative() {
                            isShowConnectDialog = false;
                        }
                    });
                    isShowConnectDialog = true;// 这次订单是否展示过连接打印机弹窗了
                    return;
                }
            }
        }

        ProgressUtil.showProgressDialog(VictoryConfirmActivity.this, getString(R.string.waiting), false);
        button_add.setEnabled(false);
        pos_victoryBetting.OrderInfo orderInfo = new pos_victoryBetting.OrderInfo();

//        infoBean.setTicketList(ticketList);
        String temp_default;
        if (Config.MUST_LOGIN) {
            temp_default = "";
        } else {
            temp_default = Config.Test_Terminal;
        }
        String terminal = SPUtils.look(VictoryConfirmActivity.this, SPkey.userActivationCode, temp_default);
        if (TextUtils.isEmpty(terminal)) {
            ToastUtils.showShort(getString(R.string.needreactivte));
            return;
        }
        orderInfo.setIssue_no(victoryBean.getIssueInfo().getIssue_no());
        orderInfo.setAmount((sna * price) + "");
        orderInfo.setBet_num(sna + "");
        orderInfo.setBet_multiple("1");
        orderInfo.setDevice_num(terminal);
        orderInfo.setData_source("3");
        orderInfo.setGameList(gameList());
//        infoBean.setTotalMoney("" + Config.OneBetPrice * getNum() * Integer.parseInt(tv_qishu.getText().toString()) * Integer.parseInt(tv_beishu.getText().toString()));

        String accountName = SPUtils.look(VictoryConfirmActivity.this, SPkey.username, Config.Test_accountName);
        String accountPass = SPUtils.look(VictoryConfirmActivity.this, SPkey.password, Config.Test_accountName);
        pos_victoryBetting s36x7order = new pos_victoryBetting(accountName,accountPass, orderInfo);
        String s = new Gson().toJson(s36x7order);
//        String name = SPUtils.look(this,SPkey.username);
//        String data = new GetJsonUtils().readOrderFromSDcard(this);
//        Resp_Order_Success respOrderSuccess = new Gson().fromJson(data,Resp_Order_Success.class);
//        if(name.equals("lr")){
//            printTicket(respOrderSuccess);
//        }
        LogUtils.e("  下单 =  请求参数  ======  " + s);
        OkGo.<String>post(MyUrl.pos_victoryBetting)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    protected void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        LogUtils.e("  返回结果：  " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")){
                                notice_victory_ticket(jsonObject.getString("orderCode"));
                            }
                            ToastUtils.showLong(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        try {
//                            resp_orderSuccess = new Gson().fromJson(response.body(), Resp_Order_Success.class);
//                            ToastUtils.showShort(resp_orderSuccess.getMessage());
//                            if (TextUtils.equals(Config.BetSuccessCode, resp_orderSuccess.getCode())) {
////                                下单成功
//                                gotoPay(resp_orderSuccess);
//                            } else {
//                                button_add.setEnabled(true);
//                            }
//                        } catch (JsonSyntaxException e) {
//                            e.printStackTrace();
//                            button_add.setEnabled(true);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            button_add.setEnabled(true);
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        button_add.setEnabled(true);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        button_add.setEnabled(true);
                    }
                });
    }

    private void notice_victory_ticket(String order) {
        LogUtils.e("打印通知……");
        ProgressUtil.showProgressDialog(VictoryConfirmActivity.this, getString(R.string.printing_wait), false);
        String account_name = SPUtils.look(VictoryConfirmActivity.this, SPkey.username, Config.Test_accountName);
        String account_pass = SPUtils.look(VictoryConfirmActivity.this, SPkey.password, Config.Test_accountName);
        String kind_37x6_game_id = SPUtils.look(this, SPkey.kind_37x6, "194");
        pos_victory_print_Notice.DataBean.OrderInfo orderInfo = new pos_victory_print_Notice.DataBean.OrderInfo(victoryBean.getIssueInfo().getIssue_no(),order);
        pos_victory_print_Notice pos_victory_print_notice = new pos_victory_print_Notice(account_name,account_pass,orderInfo);
        String s = new Gson().toJson(pos_victory_print_notice);
        LogUtils.e("  打印通知  请求参数  " + s);
        OkGo.<String>post(MyUrl.pos_victoryPrintNotice)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        LogUtils.e(" 返回内容 " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")){
                                Intent intent = new Intent();
                                intent.setClass(VictoryConfirmActivity.this,VictoryBettingSuccessActivity.class);
                                startActivity(intent);
                                finish();
                            }
//                            ToastUtils.showLong(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public List<pos_victoryBetting.GameList> gameList(){
        List<pos_victoryBetting.GameList> gameListBean = new ArrayList<>();
        for (int i = 0; i < victoryBean.getMatchList().size(); i++) {
            pos_victoryBetting.GameList gameBean = new pos_victoryBetting.GameList();
            gameBean.setGame_key_id(victoryBean.getMatchList().get(i).getId());
            gameBean.setGame_no(victoryBean.getMatchList().get(i).getGame_no());
            gameBean.setPlay_type(victoryBean.getMatchList().get(i).getPlay_type());
            gameBean.setBet_content(getContent(victoryBean.getMatchList().get(i).getTypeSelect()));
            gameListBean.add(gameBean);
        }
        return gameListBean;
    }

    public String getContent(List<Boolean> booleanList){
        String s = "";
        for (int i = 0; i < booleanList.size(); i++) {
            if (booleanList.get(i)){
                if (i == 0){
                    s = s + " 1";
                }else if (i == 1){
                    s = s + " X";
                }else if (i == 2){
                    s = s + " 2";
                }else if (i == 3){
                    s = s + " 0";
                }else if (i == 4){
                    s = s + " 1";
                }else if (i == 5){
                    s = s + " 2";
                }else if (i == 6){
                    s = s + " 3";
                }else if (i == 7){
                    s = s + " 4";
                }else if (i == 8){
                    s = s + " 5+";
                }
            }
        }
        return s.substring(1,s.length());
    }

}
