package com.jc.lottery.activity.scanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetRewardDetail;
import com.jc.lottery.bean.resp.RewardInfoBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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
 * 兑奖详情页面
 */
public class RewardRecordDetailActivity extends BaseActivity {


    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.tv_recharge_detail_title)
    TextView tvRechargeDetailTitle;
    @BindView(R.id.tv_reward_one)
    TextView tvRewardOne;
    @BindView(R.id.tv_reward_two)
    TextView tvRewardTwo;
    @BindView(R.id.tv_reward_three)
    TextView tvRewardThree;
    @BindView(R.id.tv_reward_four)
    TextView tvRewardFour;
    @BindView(R.id.tv_reward_five)
    TextView tvRewardFive;
    @BindView(R.id.tv_reward_six)
    TextView tvRewardSix;
    @BindView(R.id.tv_reward_sixs)
    TextView tvRewardSixs;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.tv_reward_book)
    TextView tvRewardBook;
    @BindView(R.id.tv_reward_ticket)
    TextView tvRewardTicket;
    private RewardInfoBean data;
    private String orderCode;
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_record_detail;
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
                codeType = false;
                getRewardHttp();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        data = (RewardInfoBean) intent.getSerializableExtra("data");

        if (data != null) {
            orderCode = data.getOrderCode();
            getRewardHttp();
        } else {
            ToastUtils.showShort("");
        }
    }

    private void getRewardHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetRewardDetail.DataBean.CashInfo cashInfo = new pos_GetRewardDetail.DataBean.CashInfo(orderCode);
        pos_GetRewardDetail pos_getRewardDetail = new pos_GetRewardDetail(account_name, account_password, "3", cashInfo);
        String json = new Gson().toJson(pos_getRewardDetail);
        OkGo.<String>post(MyUrl.pos_GetRewardDetail)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            srlReceiving.finishRefresh();
                            JSONObject json = new JSONObject(response.body());
                            if (json.getString("code").equals("00000")) {
                                JSONObject getRewardDetail = json.getJSONObject("rewardDetail");
                                tvRewardOne.setText(getRewardDetail.getString("orderCode"));
                                tvRewardBook.setText(getRewardDetail.getString("bookNum"));
                                tvRewardTicket.setText(getRewardDetail.getString("ticketNum"));
                                tvRewardTwo.setText(getRewardDetail.getString("gameName"));
                                if (getRewardDetail.getString("cashState").equals("00")) {
                                    tvRewardThree.setText(R.string.yiduijiang);
//                                    tvRewardThree.setTextColor(Color.rgb(48, 178, 102));
                                } else {
                                    tvRewardThree.setText(R.string.no_convertibility);
//                                    tvRewardThree.setTextColor(Color.rgb(0, 165, 83));
                                }
//                                Long time = Long.parseLong(getRewardDetail.getString("cashTime"));
                                if (getRewardDetail.has("cashTime")){
                                    tvRewardFive.setText(timeStamp2Date(getRewardDetail.getLong("cashTime")));
                                }else {
                                    tvRewardFive.setText("--");
                                }
                                tvRewardSix.setText(MoneyUtil.getIns().GetMoney(getRewardDetail.getString("cashMoney")) + getString(R.string.price_unit));
                                tvRewardSixs.setText("+" + MoneyUtil.getIns().GetMoney(getRewardDetail.getString("cashMoney")) + ".00");
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

    @OnClick({R.id.lly_reward_record_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            default:
                break;
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
