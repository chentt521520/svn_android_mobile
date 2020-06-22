package com.jc.lottery.activity.victory;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.VictoryBettingDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.VictoryBetsList;
import com.jc.lottery.bean.req.VictoryTicketDetailBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
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
public class VictoryBettingDetailActivity extends BaseActivity {

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
    private VictoryBettingDetailAdapter victoryAdapter = null;
    private List<VictoryBetsList> victoryBetsList;
    private String orderCode = "";
    private String type = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_victory_betting_detail;
    }

    @Override
    public void getPreIntent() {
//        victoryBean = (VictoryBean) getIntent().getSerializableExtra("victoryBean");
        orderCode = getIntent().getStringExtra("orderCode");
        type = getIntent().getStringExtra("type");
//        price = Integer.parseInt(victoryBean.getIssueInfo().getUnit_price());
    }

    @Override
    public void initView() {
        relVictory.setLayoutManager(new LinearLayoutManager(this));
        if (type.equals("1")) {
            tvTitle.setText(getString(R.string.touzhudetail));
        }else {
            tvTitle.setText(getString(R.string.bonus_details));
        }
    }

    @Override
    public void initData() {
//        getHttpInfo();
//        showView();
        getHttpInfo(orderCode);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getHttpInfo(String order) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        VictoryTicketDetailBean.BettingInfo bettingInfo = new VictoryTicketDetailBean.BettingInfo(order);
        VictoryTicketDetailBean.DataBean dataBean = new VictoryTicketDetailBean.DataBean(bettingInfo);
        VictoryTicketDetailBean victoryTicketDetailBean = new VictoryTicketDetailBean(account_name, account_password, dataBean);
        String s1 = new Gson().toJson(victoryTicketDetailBean);
        OkGo.<String>post(MyUrl.pos_GetTicketDetail)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
//                                victoryBean = new Gson().fromJson(response.body(), VictoryBean.class);
                                JSONObject orderInfo = jsonObject.getJSONObject("orderInfo");
                                tvVictoryDate.setText(orderInfo.getString("issue_no"));
                                tvVictoryNo.setText(getString(R.string.victory_ticket_number) + ":" + orderInfo.getString("order_code"));
                                tvVictoryTime.setText(getString(R.string.order_time) + ":" + timeStamp2Date(orderInfo.getLong("order_time")));
                                JSONArray betsList = jsonObject.getJSONArray("betsList");
                                victoryBetsList = new ArrayList<>();
                                for (int i = 0; i < betsList.length(); i++) {
                                    JSONObject json = betsList.getJSONObject(i);
                                    VictoryBetsList victoryBets = new VictoryBetsList();
                                    victoryBets.setBet_content(json.getString("bet_content"));
                                    victoryBets.setGame_name(json.getString("game_name"));
                                    victoryBets.setHost_name(json.getString("host_name"));
                                    victoryBets.setAway_name(json.getString("away_name"));
                                    victoryBets.setResult(json.getString("result"));
//                                    victoryBets.setBet_money(json.getString("bet_money"));
                                    victoryBets.setPlay_type(json.getString("play_type"));
                                    victoryBets.setWin_status(json.getString("win_status"));
                                    victoryBets.setInfoBeans(showBool(victoryBets.getPlay_type(), victoryBets.getBet_content()));
                                    victoryBetsList.add(victoryBets);
                                }
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


    @OnClick({R.id.lly_activation_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_activation_back:
                finish();
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
