package com.jc.lottery.activity.victory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.VictoryBettingDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.VictoryBetsList;
import com.jc.lottery.bean.VictoryScannerBean;
import com.jc.lottery.bean.req.RedeemBean;
import com.jc.lottery.bean.req.RedeemQueryBean;
import com.jc.lottery.bean.req.VictoryTicketDetailBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 胜负彩页
 */
public class VictoryScannerDetailActivity extends BaseActivity {

    @BindView(R.id.lly_activation_back)
    LinearLayout llyActivationBack;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.tv_victory_date)
    TextView tvVictoryDate;
    @BindView(R.id.tv_victory_no)
    TextView tvVictoryNo;
    @BindView(R.id.tv_victory_time)
    TextView tvVictoryTime;
    @BindView(R.id.rel_victory)
    RecyclerView relVictory;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_scanner_type)
    TextView tvScannerType;
    @BindView(R.id.tv_scanner_money)
    TextView tvScannerMoney;
    @BindView(R.id.btn_scanner_details_submit)
    Button btnScannerDetailsSubmit;
    private VictoryBettingDetailAdapter victoryAdapter = null;
    private List<VictoryBetsList> victoryBetsList;
    private String orderCode = "";
    //    private String type = "";
    private VictoryScannerBean victoryScannerBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_victory_scanner_detail;
    }

    @Override
    public void getPreIntent() {
//        victoryBean = (VictoryBean) getIntent().getSerializableExtra("victoryBean");
        orderCode = getIntent().getStringExtra("orderCode");
        victoryScannerBean = (VictoryScannerBean) getIntent().getSerializableExtra("victoryScannerBean");
//        price = Integer.parseInt(victoryBean.getIssueInfo().getUnit_price());
    }

    @Override
    public void initView() {
        relVictory.setLayoutManager(new LinearLayoutManager(this));
        tvTitle.setText(getString(R.string.bonus_details));
    }

    @Override
    public void initData() {
//        getHttpInfo();
//        showView();
//        getHttpInfo(orderCode);
        showList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void showList() {
        tvVictoryDate.setText(victoryScannerBean.getCashPrizeList().get(0).getIssue_no());
        tvVictoryNo.setText(getString(R.string.victory_ticket_number) + ":" + victoryScannerBean.getCashPrizeList().get(0).getOrder_code());
        tvVictoryTime.setText(getString(R.string.order_time) + ":" + timeStamp2Date(Long.parseLong(victoryScannerBean.getCashPrizeList().get(0).getOrder_time())));
        if (victoryScannerBean.getCashPrizeList().get(0).getCash_status().equals("00")) {
            if (victoryScannerBean.getCashPrizeList().get(0).getWin_status().equals("01")) {
                btnScannerDetailsSubmit.setVisibility(View.VISIBLE);
                tvScannerMoney.setVisibility(View.VISIBLE);
                tvScannerType.setText(getString(R.string.zhongjiang));
                tvScannerType.setTextColor(Color.rgb(48, 178, 102));
                tvScannerMoney.setText(MoneyUtil.getIns().GetMoney(victoryScannerBean.getCashPrizeList().get(0).getWin_money()) + getString(R.string.price_unit));
            } else if (victoryScannerBean.getCashPrizeList().get(0).getWin_status().equals("00")) {
                tvScannerType.setText(getString(R.string.weizhongjiang));
                tvScannerType.setTextColor(Color.rgb(48, 178, 102));
            } else {
                tvScannerType.setText(getString(R.string.daikaijiang));
                tvScannerType.setTextColor(Color.rgb(48, 178, 102));
            }
        } else if (victoryScannerBean.getCashPrizeList().get(0).getCash_status().equals("01")) {
            tvScannerType.setText(getString(R.string.yiduijiang));
            tvScannerType.setTextColor(Color.rgb(48, 178, 102));
        } else {
            tvScannerType.setText(getString(R.string.qijiang));
            tvScannerType.setTextColor(Color.rgb(48, 178, 102));
        }
        victoryBetsList = new ArrayList<>();
        for (int i = 0; i < victoryScannerBean.getCashPrizeList().size(); i++) {
            VictoryScannerBean.CashPrizeList cashPrizeList = victoryScannerBean.getCashPrizeList().get(i);
            VictoryBetsList victoryBets = new VictoryBetsList();
            victoryBets.setBet_content(cashPrizeList.getBet_content());
            victoryBets.setGame_name(cashPrizeList.getGame_name());
            victoryBets.setHost_name(cashPrizeList.getHost_name());
            victoryBets.setAway_name(cashPrizeList.getAway_name());
            victoryBets.setResult(cashPrizeList.getResult());
//                                    victoryBets.setBet_money(json.getString("bet_money"));
            victoryBets.setPlay_type(cashPrizeList.getPlay_type());
            victoryBets.setWin_status(cashPrizeList.getWin_status());
            victoryBets.setInfoBeans(showBool(victoryBets.getPlay_type(), victoryBets.getBet_content()));
            victoryBetsList.add(victoryBets);
        }
        showView();
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_pass = SPUtils.look(this, SPkey.password);
        RedeemBean.OrderInfo orderInfo = new RedeemBean.OrderInfo(victoryScannerBean.getCashPrizeList().get(0).getIssue_no(),orderCode);
        RedeemBean.DataBean dataBean = new RedeemBean.DataBean(orderInfo);
        RedeemBean cashPrizeQueryBean = new RedeemBean(account_name,account_pass, dataBean);
        String s1 = new Gson().toJson(cashPrizeQueryBean);
        OkGo.<String>post(MyUrl.pos_victoryRedeem)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
//                                tvScannerDetailsCashState.setText(getString(R.string.yiduijiang));
                                btnScannerDetailsSubmit.setText(getString(R.string.yiduijiang));
                                btnScannerDetailsSubmit.setOnClickListener(null);
                                btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
                            }
                            ToastUtils.showShort(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {

                    }
                });
    }

    private List<VictoryBetsList.InfoBean> showBool(String type, String content) {
        List<VictoryBetsList.InfoBean> list = new ArrayList<>();
        if (type.equals("0")) {
            for (int i = 0; i < 3; i++) {
                VictoryBetsList.InfoBean infoBean = new VictoryBetsList.InfoBean();
                if (i == 0) {
                    if (content.indexOf("1") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("1");
                        infoBean.setType(true);
                    }
                }
                if (i == 1) {
                    if (content.indexOf("X") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("X");
                        infoBean.setType(true);
                    }
                }
                if (i == 2) {
                    if (content.indexOf("2") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("2");
                        infoBean.setType(true);
                    }
                }
                list.add(infoBean);
            }
        } else {
            for (int i = 0; i < 9; i++) {
                VictoryBetsList.InfoBean infoBean = new VictoryBetsList.InfoBean();
                if (i < 3) {
                    infoBean.setName("");
                    infoBean.setType(false);
                }
                if (i == 3) {
                    if (content.indexOf("0") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("0");
                        infoBean.setType(true);
                    }
                }
                if (i == 4) {
                    if (content.indexOf("1") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("1");
                        infoBean.setType(true);
                    }
                }
                if (i == 5) {
                    if (content.indexOf("2") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("2");
                        infoBean.setType(true);
                    }
                }
                if (i == 6) {
                    if (content.indexOf("3") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("3");
                        infoBean.setType(true);
                    }
                }
                if (i == 7) {
                    if (content.indexOf("4") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("4");
                        infoBean.setType(true);
                    }
                }
                if (i == 8) {
                    if (content.indexOf("5+") == -1) {
                        infoBean.setName("");
                        infoBean.setType(false);
                    } else {
                        infoBean.setName("5+");
                        infoBean.setType(true);
                    }
                }
                list.add(infoBean);
            }
        }
        return list;
    }

    private void showView() {
        victoryAdapter = new VictoryBettingDetailAdapter(this);
        victoryAdapter.setList(victoryBetsList);
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
            format = "dd-MM-yyyy HH:mm";
//            format = "HH:mm";
        } else {
            format = "yyyy-MM-dd HH:mm";
//            format = "HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }


    @OnClick({R.id.lly_activation_back,R.id.btn_scanner_details_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_activation_back:
                finish();
                break;
            case R.id.btn_scanner_details_submit:
                getHttpInfo();
                break;
        }
    }

    public String getContent(List<Boolean> booleanList) {
        String s = "";
        for (int i = 0; i < booleanList.size(); i++) {
            if (booleanList.get(i)) {
                if (i == 0) {
                    s = s + " 1";
                } else if (i == 1) {
                    s = s + " X";
                } else if (i == 2) {
                    s = s + " 2";
                } else if (i == 3) {
                    s = s + " 0";
                } else if (i == 4) {
                    s = s + " 1";
                } else if (i == 5) {
                    s = s + " 2";
                } else if (i == 6) {
                    s = s + " 3";
                } else if (i == 7) {
                    s = s + " 4";
                } else if (i == 8) {
                    s = s + " 5+";
                }
            }
        }
        return s.substring(1, s.length());
    }

}
