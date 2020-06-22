package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.TongAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.NumberBean;
import com.jc.lottery.bean.OpenWinDetailBean;
import com.jc.lottery.bean.Resp_open_result_detail;
import com.jc.lottery.bean.pos_open_result_detail;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开奖结果详情 页面
 */
public class OpenResultDetailActivity extends BaseActivity {

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
    @BindView(R.id.awarddetail_tv_current_sales)
    TextView awarddetailTvCurrentSales;
    @BindView(R.id.awarddetail_tv_bonus)
    TextView awarddetailTvBonus;
    @BindView(R.id.awarddetail_tv_bonus_two)
    TextView awarddetailTvBonusTwo;
    @BindView(R.id.rv_open_detail)
    RecyclerView rvOpenDetail;
    @BindView(R.id.lin_kl8_zoushitu)
    LinearLayout linKl8Zoushitu;
    @BindView(R.id.tv_prize)
    TextView tvPrize;
    @BindView(R.id.lly_open_result_pool)
    LinearLayout llyOpenResultPool;
    @BindView(R.id.lly_open_result_pool_two)
    LinearLayout llyOpenResultPoolTwo;
    private TongAdapter tongAdapter;
    private ArrayList<OpenWinDetailBean> openWinDetailBeans;
    private String gamealias;
    private String draw;

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_result_detail;
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
        if (gamealias.equals("90x5")){
            llyOpenResultPool.setVisibility(View.GONE);
            llyOpenResultPoolTwo.setVisibility(View.GONE);
        }else {
            llyOpenResultPool.setVisibility(View.VISIBLE);
            llyOpenResultPoolTwo.setVisibility(View.VISIBLE);
        }
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
        rvOpenDetail.setLayoutManager(new LinearLayoutManager(OpenResultDetailActivity.this));
        tongAdapter = new TongAdapter();
        tongAdapter.setData(openWinDetailBeans);
        rvOpenDetail.setAdapter(tongAdapter);

    }

    @Override
    public void initData() {
        String account_name = SPUtils.look(this, SPkey.username, Config.Test_accountName);
        pos_open_result_detail screen_drawNoticeQueryList = new pos_open_result_detail(account_name, gamealias, draw);
        String s = new Gson().toJson(screen_drawNoticeQueryList);
        LogUtils.e(" 获取开奖信息 请求参数  " + s);
        OkGo.<String>post(MyUrl.screen_drawNoticeQueryDetail)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressUtil.showProgressDialog(OpenResultDetailActivity.this, getString(R.string.getInfoing));
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 正在获取开奖详情信息 返回内容 " + response.body());
                        Resp_open_result_detail resp_open_result_detail = new Gson().fromJson(response.body(), Resp_open_result_detail.class);
                        String prizeNum = resp_open_result_detail.getDrawDetails().getPrizeNum();
//                        初始化 开奖号码
                        initOpenNumber(prizeNum);
//                        初始化 奖期信息
                        initTicketInfo(resp_open_result_detail);
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
        awarddetailTvBonus.setText(MoneyUtil.getIns().GetMoney(poolMoney));
        awarddetailTvBonusTwo.setText(MoneyUtil.getIns().GetMoney(secondPool));
        awarddetailTvCurrentSales.setText(MoneyUtil.getIns().GetMoney(income));
    }

    /**
     * 初始化中奖信息
     *
     * @param resp_open_result_detail
     */
    private void initOpenWinInfo(Resp_open_result_detail resp_open_result_detail) {
        if (openWinDetailBeans == null) {
            openWinDetailBeans = new ArrayList<>();
        } else {
            openWinDetailBeans.clear();
        }
        for (Resp_open_result_detail.DrawDetailsBean.LevelListBean levelListBean : resp_open_result_detail.getDrawDetails().getLevelList()) {
            OpenWinDetailBean openWinDetailBean = new OpenWinDetailBean(resp_open_result_detail.getData().getColorPeriodInfo().getGameAlias(), levelListBean);
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
            LinearLayout layout = new LinearLayout(OpenResultDetailActivity.this);
            layout.setGravity(Gravity.CENTER);
            linearLayoutBalldetail.addView(layout);
            TextView view = new TextView(OpenResultDetailActivity.this);
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

}
