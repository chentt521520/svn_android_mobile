package com.jc.lottery.activity.live;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.VideoListAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.DrawListInfo;
import com.jc.lottery.bean.req.pos_GetHistoryLottery;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ViewAnimationUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开奖直播记录页面
 * lr
 */
//A deposit request has been initiated on your phone!Please enter your pin to confirm the payment.If you already entered it,tap on the "Completed" button to Finish
public class VideoListActivity extends BaseActivity {

    @BindView(R.id.header_receiving_one)
    ClassicsHeader headerReceiving;
    @BindView(R.id.rel_live)
    RecyclerView relLive;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.lly_reward_record_select)
    LinearLayout llyRewardRecordSelect;
    @BindView(R.id.tv_receiving_select_all)
    TextView tvReceivingSelectAll;
    @BindView(R.id.tv_receiving_select_90x5)
    TextView tvReceivingSelect90x5;
    @BindView(R.id.tv_receiving_select_37x6)
    TextView tvReceivingSelect37x6;
    @BindView(R.id.lly_receiving_select)
    LinearLayout llyReceivingSelect;
    private VideoListAdapter videoListAdapter;
    private AlphaAnimation mAnimation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
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
        relLive.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {

        getHttp("");
//        ToastUtils.showShort(instantRecharge + "比例");
//        instantRecharge = (instant.replace("%",""));
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getHttp("");
            }
        });
    }

    @OnClick({R.id.lly_recharge_back, R.id.lly_reward_record_select,R.id.lly_receiving_select,R.id.tv_receiving_select_all,R.id.tv_receiving_select_90x5,R.id.tv_receiving_select_37x6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_recharge_back:
                finish();
                break;
            case R.id.lly_reward_record_select:
                llyReceivingSelect.setVisibility(View.VISIBLE);
                new ViewAnimationUtil().setShowAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.lly_receiving_select:
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
//                    llyReceivingSelect.setVisibility(View.GONE);
                break;
            case R.id.tv_receiving_select_all:
                tvReceivingSelectAll.setBackgroundResource(R.drawable.reward_et_red_bg);
                tvReceivingSelect90x5.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect37x6.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelectAll.setTextColor(Color.WHITE);
                tvReceivingSelect90x5.setTextColor(Color.GRAY);
                tvReceivingSelect37x6.setTextColor(Color.GRAY);
                getHttp("");
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.tv_receiving_select_90x5:
                tvReceivingSelectAll.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect90x5.setBackgroundResource(R.drawable.reward_et_red_bg);
                tvReceivingSelect37x6.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelectAll.setTextColor(Color.GRAY);
                tvReceivingSelect90x5.setTextColor(Color.WHITE);
                tvReceivingSelect37x6.setTextColor(Color.GRAY);
                getHttp("90x5");
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.tv_receiving_select_37x6:
                tvReceivingSelectAll.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect90x5.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect37x6.setBackgroundResource(R.drawable.reward_et_red_bg);
                tvReceivingSelectAll.setTextColor(Color.GRAY);
                tvReceivingSelect90x5.setTextColor(Color.GRAY);
                tvReceivingSelect37x6.setTextColor(Color.WHITE);
                getHttp("37x6");
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getHttp(String game) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        String device = SPUtils.look(this, SPkey.terminalname);
        pos_GetHistoryLottery.DataBean.DrawInfo drawInfo = new pos_GetHistoryLottery.DataBean.DrawInfo("", game, "1", "20");
        pos_GetHistoryLottery pos_payRecharge = new pos_GetHistoryLottery(account_name, drawInfo);
        String s1 = new Gson().toJson(pos_payRecharge);
        OkGo.<String>post(MyUrl.pos_GetHistoryLottery)
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
                                List<DrawListInfo> liveList = new ArrayList<>();
                                Type listType = new TypeToken<List<DrawListInfo>>() {
                                }.getType();
                                liveList = new Gson().fromJson(jsonObject.getJSONArray("drawList").toString(), listType);
                                showList(liveList);
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

    private void showList(List<DrawListInfo> liveList) {
        videoListAdapter = new VideoListAdapter(this, this);
        videoListAdapter.setList(liveList);
        relLive.setAdapter(videoListAdapter);
    }

}
