package com.jc.lottery.activity.lottery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.BettingDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.HistoryBetDetailBean;
import com.jc.lottery.bean.req.RefundTicketBean;
import com.jc.lottery.bean.resp.BettingDetailInfo;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投注详情页面
 */
public class BettingRecordsDetailActivity extends BaseActivity {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.tv_recharge_detail_title)
    TextView tvRechargeDetailTitle;
    @BindView(R.id.header_betting_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.tv_betting_detail_game)
    TextView tvBettingDetailGame;
    @BindView(R.id.tv_betting_detail_order)
    TextView tvBettingDetailOrder;
    @BindView(R.id.tv_betting_detail_draw_number)
    TextView tvBettingDetailDrawNumber;
    @BindView(R.id.tv_betting_detail_bet_mode)
    TextView tvBettingDetailBetMode;
    @BindView(R.id.tv_betting_detail_bet_double)
    TextView tvBettingDetailBetDouble;
    @BindView(R.id.tv_betting_detail_multi_draw)
    TextView tvBettingDetailMultiDraw;
    @BindView(R.id.tv_betting_detail_note_number)
    TextView tvBettingDetailNoteNumber;
    @BindView(R.id.tv_betting_detail_order_money)
    TextView tvBettingDetailOrderMoney;
    @BindView(R.id.tv_betting_detail_win_amount)
    TextView tvBettingDetailWinAmount;
    @BindView(R.id.tv_betting_detail_bet_status)
    TextView tvBettingDetailBetStatus;
    @BindView(R.id.tv_betting_detail_win_title_state)
    TextView tvBettingDetailWinTitleState;
    @BindView(R.id.tv_betting_detail_win_state)
    TextView tvBettingDetailWinState;
    @BindView(R.id.tv_betting_detail_buy_time)
    TextView tvBettingDetailBuyTime;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.rel_betting_detail)
    RecyclerView relBettingDetail;
    @BindView(R.id.tv_betting_detail_bottom_one)
    TextView tvBettingDetailBottomOne;
    @BindView(R.id.tv_betting_detail_bottom_two)
    TextView tvBettingDetailBottomTwo;
    @BindView(R.id.tv_betting_detail_bottom_title)
    TextView tvBettingDetailBottomTitle;
    @BindView(R.id.bt_betting_detail_refund)
    Button btBettingDetailRefund;

    private String gameAlias = "";
    private String drawNumber = "";
    private String order = "";
    private String type = "";
    private BettingDetailAdapter bettingDetailAdapter;
    private long buyTime;
    private int time = 0;
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_betting_detail_newest;
    }

    @Override
    public void getPreIntent() {
        gameAlias = getIntent().getStringExtra("gameAlias");
        order = getIntent().getStringExtra("order");
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            tvRechargeDetailTitle.setText(getString(R.string.touzhudetail));
            tvBettingDetailGame.setText(getString(R.string.touzhudetail));
            tvBettingDetailWinTitleState.setText(getString(R.string.winstates));
        } else {
            tvRechargeDetailTitle.setText(getString(R.string.bonus_details));
            tvBettingDetailGame.setText(getString(R.string.bonus_details));
            tvBettingDetailWinTitleState.setText(getString(R.string.reward_status));
            btBettingDetailRefund.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        getRecordHttp(gameAlias, order);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                getRecordHttp(gameAlias, order);
            }
        });
    }

    @Override
    public void initView() {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relBettingDetail.setLayoutManager(new LinearLayoutManager(this));
        bettingDetailAdapter = new BettingDetailAdapter(this, type);
//        headerReceiving.setTimeFormat(new SimpleDateFormat(getString(R.string.xlistview_header_last_time) +" %s"));
    }

    @OnClick({R.id.lly_reward_record_back,R.id.bt_betting_detail_refund})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.bt_betting_detail_refund:
//                long timeStampSec = System.currentTimeMillis();
                long timeStampSec = TimeUtils.get13ServiceTimeStamp() / 1000;
                if(gameAlias.equals("90x5")){
                    time = Config.s90x5_R008_NoteRefund_Time_max;
                }else {
                    time = Config.s37x6_R008_NoteRefund_Time_max;
                }
                if (timeStampSec - buyTime < time * 60 * 1000){
                    refundTicketHttp(gameAlias,drawNumber,order);
                }else {
                    ToastUtils.showShort(getString(R.string.now_is_more_time));
                }
                break;
            default:
                break;
        }
    }

    private void getRecordHttp(String gameAlias, String order) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        HistoryBetDetailBean.BettingInfoBean bettingInfoBean = new HistoryBetDetailBean.BettingInfoBean(gameAlias, order);
        HistoryBetDetailBean.DataBean dataBean = new HistoryBetDetailBean.DataBean(bettingInfoBean);
        HistoryBetDetailBean historyBetDetailBean = new HistoryBetDetailBean(account_name, dataBean);
        String json = new Gson().toJson(historyBetDetailBean);
        OkGo.<String>post(MyUrl.historyBetDetail)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            srlReceiving.finishRefresh();
                            JSONObject json = new JSONObject(response.body());
                            if (json.getString("code").equals("00000")) {
                                BettingDetailInfo bettingDetailInfo = new Gson().fromJson(response.body(), BettingDetailInfo.class);
                                showInitView(bettingDetailInfo);
                            } else {
                                ToastUtils.showShort(json.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        if (codeType){
                            finish();
                        }
                    }
                });
    }

    private void showInitView(BettingDetailInfo bettingDetailInfo) throws JSONException {
        BettingDetailInfo.OrderInfo orderInfo = bettingDetailInfo.getOrderInfo();
        drawNumber = orderInfo.getDrawNumber();
        tvBettingDetailOrder.setText(orderInfo.getOrderCode());
        tvBettingDetailDrawNumber.setText(orderInfo.getDrawNumber());
        if (orderInfo.getBetMode().equals("01")) {
            tvBettingDetailBetMode.setText(getString(R.string.danshi));//投注方式
        } else if (orderInfo.getBetMode().equals("02")) {
            tvBettingDetailBetMode.setText(getString(R.string.fushi));//投注方式
        } else if (orderInfo.getBetMode().equals("03")) {
            tvBettingDetailBetMode.setText(getString(R.string.dantuofushi));//投注方式
        } else {
            tvBettingDetailBetMode.setText(getString(R.string.hunhetouzhu));//投注方式
        }
        tvBettingDetailBetDouble.setText(orderInfo.getBetDouble());
        tvBettingDetailOrderMoney.setText(MoneyUtil.getIns().GetMoney(orderInfo.getOrderMoney()) + getString(R.string.price_unit));
        buyTime = Long.parseLong(orderInfo.getBuyTime()) / 1000;
        tvBettingDetailBuyTime.setText(timeStamp2Date(Long.parseLong(orderInfo.getBuyTime())));
        tvBettingDetailMultiDraw.setText(orderInfo.getMultiDraw());
        tvBettingDetailNoteNumber.setText(orderInfo.getNoteNumber());
        if (orderInfo.getBetStatus().equals("00")) {
            tvBettingDetailBetStatus.setText(getString(R.string.chupiao_scuess));
        } else if (orderInfo.getBetStatus().equals("01")) {
            tvBettingDetailBetStatus.setText(getString(R.string.chupiao_fail));
        } else if (orderInfo.getBetStatus().equals("02")) {
            tvBettingDetailBetStatus.setText(getString(R.string.chupiao_daichupiao));
        } else {
            tvBettingDetailBetStatus.setText(getString(R.string.refund));
        }
        if (orderInfo.getWinstate().equals("00")) {
            tvBettingDetailWinState.setText(getString(R.string.weizhongjiang));
        } else if (orderInfo.getWinstate().equals("01")) {
            tvBettingDetailWinState.setText(getString(R.string.zhongjiang));
        } else if (orderInfo.getWinstate().equals("02")) {
            tvBettingDetailWinState.setText(getString(R.string.daikaijiang));
        } else if (orderInfo.getWinstate().equals("03")) {
            tvBettingDetailWinState.setText(getString(R.string.yiduijiang));
        } else {
            tvBettingDetailWinState.setText(getString(R.string.qijiang));
        }
        if (null == orderInfo.getWinAmount() || orderInfo.getWinAmount().equals("")) {
            tvBettingDetailWinAmount.setText("--");
        } else {
            tvBettingDetailWinAmount.setText(MoneyUtil.getIns().GetMoney(orderInfo.getWinAmount()) + getString(R.string.price_unit));
        }
        tvBettingDetailBottomTitle.setText(orderInfo.getGameName() + "(No." + orderInfo.getDrawNumber() + ")");
        tvBettingDetailBottomOne.setText(getString(R.string.touzhuqishu) + orderInfo.getMultiDraw());
        tvBettingDetailBottomTwo.setText(getString(R.string.touzhubeishu) + orderInfo.getBetDouble());
        bettingDetailAdapter.setList(bettingDetailInfo.getBetsList());
        relBettingDetail.setAdapter(bettingDetailAdapter);
        if (type.equals("1")){
//            long timeStampSec = System.currentTimeMillis() / 1000;
            long timeStampSec = TimeUtils.get13ServiceTimeStamp() / 1000;
            if(gameAlias.equals("90x5")){
                time = Config.s90x5_R008_NoteRefund_Time_max;
            }else {
                time = Config.s37x6_R008_NoteRefund_Time_max;
            }
            if (timeStampSec - buyTime < time * 60 * 1000 && !orderInfo.getBetStatus().equals("03")){
                btBettingDetailRefund.setVisibility(View.VISIBLE);
            }else {
                btBettingDetailRefund.setVisibility(View.GONE);
            }
        }
//        if (rechargeDetail.getString("payState").equals("01")) {
//            tvRechargeState.setText(getString(R.string.recharge_scussess));
//            tvRechargeState.setTextColor(Color.rgb(0,75,255));
//        } else if (rechargeDetail.getString("payState").equals("02")) {
//            tvRechargeState.setText(getString(R.string.failure_of_recharge));
//            tvRechargeState.setTextColor(Color.rgb(253, 8, 8));
//        } else if (rechargeDetail.getString("payState").equals("00")) {
//            tvRechargeState.setText(getString(R.string.waiting_for_recharge));
//            tvRechargeState.setTextColor(Color.rgb(255, 102, 0));
//        } else if (rechargeDetail.getString("payState").equals("03")) {
//            tvRechargeState.setText(getString(R.string.to_be_confirmed));
//            tvRechargeState.setTextColor(Color.rgb(255, 102, 0));
//        }
    }

    private void refundTicketHttp(String gameAlias,String drawNumber, String order) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        RefundTicketBean.OrderInfo orderInfo = new RefundTicketBean.OrderInfo(gameAlias,drawNumber, order);
        RefundTicketBean.DataBean dataBean = new RefundTicketBean.DataBean(orderInfo);
        RefundTicketBean historyBetDetailBean = new RefundTicketBean(account_name, dataBean);
        String json = new Gson().toJson(historyBetDetailBean);
        OkGo.<String>post(MyUrl.refundTicket)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            srlReceiving.finishRefresh();
                            JSONObject json = new JSONObject(response.body());
                            if (json.getString("code").equals("00000")){
                                tvBettingDetailBetStatus.setText(getString(R.string.refund));
                                btBettingDetailRefund.setVisibility(View.GONE);
                            }
                            ToastUtils.showShort(json.getString("message"));
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

    //为listview动态设置高度
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
