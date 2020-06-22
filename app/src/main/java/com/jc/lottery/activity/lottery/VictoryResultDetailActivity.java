package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.TongAdapter;
import com.jc.lottery.adapter.VictoryDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.MatchListBean;
import com.jc.lottery.bean.NumberBean;
import com.jc.lottery.bean.OpenWinDetailBean;
import com.jc.lottery.bean.Resp_open_result_detail;
import com.jc.lottery.bean.pos_open_result_detail;
import com.jc.lottery.bean.req.pos_victory_result_detail;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 开奖结果详情 页面
 */
public class VictoryResultDetailActivity extends BaseActivity {

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_termNo)
    TextView tvTermNo;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.lly_type_one_back)
    LinearLayout linearLayoutBalldetailBack;
    @BindView(R.id.linearLayout_balldetail)
    LinearLayout linearLayoutBalldetail;
    @BindView(R.id.rv_open_detail)
    RecyclerView rvOpenDetail;
    @BindView(R.id.rel_victory)
    RecyclerView relVictory;
    @BindView(R.id.lin_kl8_zoushitu)
    LinearLayout linKl8Zoushitu;
    @BindView(R.id.tv_prize)
    TextView tvPrize;
    private TongAdapter tongAdapter;
    private ArrayList<OpenWinDetailBean> openWinDetailBeans;
    private String gamealias;
    private String draw;
    private List<MatchListBean> matchListBeans = new ArrayList<MatchListBean>();
    private VictoryDetailAdapter victoryDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_victory_result_detail;
    }

    @Override
    public void getPreIntent() {
        gamealias = getIntent().getStringExtra("gameAlias");
        draw = getIntent().getStringExtra("draw");
        LogUtils.e("接收到的游戏别名   ====  " + gamealias);
        if (gamealias.equals("k3")) {
            tvPrize.setText(getString(R.string.jiangdeng));
        } else {
            tvPrize.setText(getString(R.string.prize));
        }
        tvTermNo.setText(String.format(getString(R.string.qici_no), draw));
    }

    @Override
    public void initListener() {
        linearLayoutBalldetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        openWinDetailBeans = new ArrayList<>();
        relVictory.setLayoutManager(new LinearLayoutManager(this));
        rvOpenDetail.setLayoutManager(new LinearLayoutManager(VictoryResultDetailActivity.this));
        tongAdapter = new TongAdapter();
        victoryDetailAdapter = new VictoryDetailAdapter(this);
        tongAdapter.setData(openWinDetailBeans);
        victoryDetailAdapter.setList(matchListBeans);
        rvOpenDetail.setAdapter(tongAdapter);
        relVictory.setAdapter(victoryDetailAdapter);

    }

    @Override
    public void initData() {
        String account_name = SPUtils.look(this, SPkey.username, Config.Test_accountName);
        String password = SPUtils.look(this, SPkey.password, Config.Test_accountName);
        pos_victory_result_detail screen_drawNoticeQueryList = new pos_victory_result_detail(account_name,password,draw);
        String s = new Gson().toJson(screen_drawNoticeQueryList);
        LogUtils.e(" 获取开奖信息 请求参数  " + s);
        OkGo.<String>post(MyUrl.pos_drawNoticeDetail)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressUtil.showProgressDialog(VictoryResultDetailActivity.this, getString(R.string.getInfoing));
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 正在获取开奖详情信息 返回内容 " + response.body());
//                        Resp_open_result_detail resp_open_result_detail = new Gson().fromJson(response.body(), Resp_open_result_detail.class);
//                        String prizeNum = resp_open_result_detail.getDrawDetails().getPrizeNum();
////                        初始化 开奖号码
//                        initOpenNumber(prizeNum);
////                        初始化 奖期信息
//                        initTicketInfo(resp_open_result_detail);
                        List<Resp_open_result_detail.DrawDetailsBean.LevelListBean> resp_open_result_detail = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONArray matchList = jsonObject.getJSONArray("matchList");
                            for (int i = 0; i < matchList.length(); i++) {
                                JSONObject match = matchList.getJSONObject(i);
                                MatchListBean matchBean = new MatchListBean();
                                matchBean.setAway_name(match.getString("away_name"));
                                matchBean.setAway_score(match.getString("away_score"));
                                matchBean.setGame_name(match.getString("game_name"));
                                matchBean.setGame_no(match.getString("game_no"));
                                matchBean.setGame_time(match.getString("game_time"));
                                matchBean.setHost_name(match.getString("host_name"));
                                matchBean.setHost_score(match.getString("host_score"));
                                matchBean.setIs_special(match.getString("is_special"));
                                matchBean.setIssue_no(match.getString("issue_no"));
                                matchBean.setPlay_type(match.getString("play_type"));
                                matchBean.setResult(match.getString("result"));
                                matchListBeans.add(matchBean);
                            }

                            JSONArray prizeLevelList = jsonObject.getJSONArray("prizeLevel");
                            for (int i = 0; i < prizeLevelList.length(); i++) {
                                JSONObject prize = prizeLevelList.getJSONObject(i);
                                Resp_open_result_detail.DrawDetailsBean.LevelListBean levelListBean = new Resp_open_result_detail.DrawDetailsBean.LevelListBean();
                                levelListBean.setWinLevel(prize.getString("award_name"));
                                levelListBean.setWinMoney(prize.getString("amount"));
                                levelListBean.setBetNum(prize.getInt("award_num"));
                                resp_open_result_detail.add(levelListBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tvDate.setText(timeStamp2Date(Long.parseLong(matchListBeans.get(0).getGame_time())));
                         victoryDetailAdapter.notifyDataSetChanged();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                victoryDetailAdapter = new VictoryDetailAdapter(VictoryResultDetailActivity.this);
//                                victoryDetailAdapter.setList(matchListBeans);
//                                relVictory.setAdapter(victoryDetailAdapter);
//                            }
//                        });
//                        初始化中奖信息
                        initOpenWinInfo(resp_open_result_detail);
                    }
                });
    }

    /**
     * 初始化奖期信息
     *
     * @param resp_open_result_detail
     */
    private void initTicketInfo(Resp_open_result_detail resp_open_result_detail) {
        String draw = resp_open_result_detail.getDrawDetails().getDraw();// 期号
        String poolMoney = resp_open_result_detail.getDrawDetails().getPoolMoney();// 奖池金额
        String secondPool = resp_open_result_detail.getDrawDetails().getSecondPool();// 奖池金额
        String prizeTime = TimeUtils.getTime(this, resp_open_result_detail.getDrawDetails().getPrizeTime());// 开奖时间
        String income = resp_open_result_detail.getDrawDetails().getIncome();// 本期销售金额

        tvTermNo.setText(String.format(getString(R.string.qici_no), draw));
        tvDate.setText(prizeTime);
    }

    /**
     * 初始化中奖信息
     *
     * @param resp_open_result_detail
     */
    private void initOpenWinInfo(List<Resp_open_result_detail.DrawDetailsBean.LevelListBean> resp_open_result_detail) {
        if (openWinDetailBeans == null) {
            openWinDetailBeans = new ArrayList<>();
        } else {
            openWinDetailBeans.clear();
        }
        for (Resp_open_result_detail.DrawDetailsBean.LevelListBean levelListBean : resp_open_result_detail) {
            OpenWinDetailBean openWinDetailBean = new OpenWinDetailBean("Jackpot", levelListBean);
//            return String.format("%.2f", moneyDouble);
            openWinDetailBean.setWinMoney(MoneyUtil.getIns().GetMoney(openWinDetailBean.getWinMoney()));
            openWinDetailBeans.add(openWinDetailBean);
        }
        tongAdapter.setData(openWinDetailBeans);
        rvOpenDetail.setAdapter(tongAdapter);
    }

    /**
     * 初始化 中奖号码
     *
     * @param prizeNum
     */
    private void initOpenNumber(String prizeNum) {
        String[] splits = prizeNum.split(" ");
        List<NumberBean> list = showList(splits);
        linearLayoutBalldetail.removeAllViews();
        for (NumberBean numberBean : list) {
            LinearLayout layout = new LinearLayout(VictoryResultDetailActivity.this);
            layout.setGravity(Gravity.CENTER);
            linearLayoutBalldetail.addView(layout);
            TextView view = new TextView(VictoryResultDetailActivity.this);
            LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lp4.rightMargin = 2;
            view.setWidth(18);
            view.setHeight(18);
            view.setHeight(18);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            layout.setPadding(0, 0, 4, 0);
            if (numberBean.getType().equals("2")) {
                view.setBackgroundResource(R.drawable.shape_round_red_textviews);
            } else {
                view.setBackgroundResource(R.drawable.shape_round_red_textview);
            }
            view.setText("" + numberBean.getNumber());
            view.setLayoutParams(lp4);
            layout.addView(view);
        }
    }

    private List<NumberBean> showList(String[] s) {
        List<NumberBean> list = new ArrayList<NumberBean>();
        String[] endString = s[s.length - 1].split("-");
        if (endString.length > 1) {
            for (int i = 0; i < s.length - 1; i++) {
                NumberBean numberBean = new NumberBean();
                numberBean.setNumber(s[i]);
                numberBean.setType("1");
                list.add(numberBean);
            }
            for (int i = 0; i < endString.length; i++) {
                NumberBean numberBean = new NumberBean();
                numberBean.setNumber(endString[i]);
                if (i == endString.length - 1) {
                    numberBean.setType("2");
                } else {
                    numberBean.setType("1");
                }
                list.add(numberBean);
            }
        } else {
            for (int i = 0; i < s.length; i++) {
                NumberBean numberBean = new NumberBean();
                numberBean.setNumber(s[i]);
                numberBean.setType("1");
                list.add(numberBean);
            }
//            list = s.
        }
        return list;
    }

    @OnClick(R.id.lin_kl8_zoushitu)
    public void onViewClicked() {
//        if (gamealias.equals("k3")) {
//            Intent intent = new Intent(this, Kuai3HistoryQueryActivity.class);
//            startActivity(intent);com/jc/lottery/activity/lottery/OpenResultDetailActivity.java:245
        if (gamealias.equals("37x6")) {
            Intent intent = new Intent(this, _37x6HistoryQueryActivity.class);
            startActivity(intent);
        } else if (gamealias.equals("90x5")) {
            Intent intent = new Intent(this, s90x5HistoryQueryActivity.class);
            startActivity(intent);
        }
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(this, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}
