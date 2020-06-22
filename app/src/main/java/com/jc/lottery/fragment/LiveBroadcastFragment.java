package com.jc.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.live.PLVideoViewActivity;
import com.jc.lottery.activity.live.VideoListActivity;
import com.jc.lottery.adapter.LiveAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.LiveBean;
import com.jc.lottery.bean.req.pos_GetElectronicPrizeRecord;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LiveBroadcastFragment extends BaseFragment {


    @BindView(R.id.tv_live_one_num)
    TextView tvLiveOneNum;
    @BindView(R.id.tv_live_one_name)
    TextView tvLiveOneName;
    @BindView(R.id.lly_live_one_go)
    LinearLayout llyLiveOneGo;
    @BindView(R.id.lly_more)
    LinearLayout llyMore;
    @BindView(R.id.tv_live_two_num)
    TextView tvLiveTwoNum;
    @BindView(R.id.tv_live_two_name)
    TextView tvLiveTwoName;
    @BindView(R.id.tv_live_three_num)
    TextView tvLiveThreeNum;
    @BindView(R.id.tv_live_three_name)
    TextView tvLiveThreeName;
    @BindView(R.id.tv_live_right_one)
    TextView tvLiveRightOne;
    @BindView(R.id.tv_live_right_two)
    TextView tvLiveRightTwo;
    @BindView(R.id.lly_live_two_go)
    LinearLayout llyLiveTwoGo;
    @BindView(R.id.tv_live_right_three)
    TextView tvLiveRightThree;
    @BindView(R.id.lly_live_three_go)
    LinearLayout llyLiveThreeGo;
    @BindView(R.id.rel_live)
    RecyclerView relLive;
    @BindView(R.id.img_one_left)
    ImageView imgOneLeft;
    @BindView(R.id.img_one_right)
    ImageView imgOneRight;
    @BindView(R.id.img_two_left)
    ImageView imgTwoLeft;
    @BindView(R.id.img_two_right)
    ImageView imgTwoRight;
    @BindView(R.id.img_three_left)
    ImageView imgThreeLeft;
    @BindView(R.id.img_three_right)
    ImageView imgThreeRight;
    @BindView(R.id.header_receiving_one)
    ClassicsHeader headerReceiving;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.rel_live_one)
    RelativeLayout relLiveOne;
    @BindView(R.id.rel_live_two)
    RelativeLayout relLiveTwo;
    @BindView(R.id.rel_live_three)
    RelativeLayout relLiveThree;
    private LiveAdapter liveAdapter;
    private List<LiveBean> liveOneList = new ArrayList<>();
    private List<LiveBean> liveTwoList = new ArrayList<>();
    private List<LiveBean> liveThreeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_broadcast;
    }

    @Override
    protected void initView(View view) {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relLive.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rl_guide = (RelativeLayout) view.findViewById(R.id.rl_guide);
//        rl_guide.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getHttpInfo();
            }
        });
    }

    @Override
    public void initData() {
//        GetGameList();
        getHttpInfo();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getHttpInfo();
        }
    }

    @OnClick({R.id.lly_live_one_go, R.id.lly_live_two_go, R.id.lly_live_three_go,R.id.lly_more})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_live_one_go:
                if (liveOneList.size() > 0) {
                    intent.setClass(getActivity(), PLVideoViewActivity.class);
                    intent.putExtra("liveBean", liveOneList.get(0));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                }
                break;
            case R.id.lly_live_two_go:
                if (liveOneList.size() > 1) {
                    intent.setClass(getActivity(), PLVideoViewActivity.class);
                    intent.putExtra("liveBean", liveOneList.get(1));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                }
                break;
            case R.id.lly_live_three_go:
                if (liveOneList.size() > 1) {
                    intent.setClass(getActivity(), PLVideoViewActivity.class);
                    intent.putExtra("liveBean", liveOneList.get(2));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                }
                break;
            case R.id.lly_more:
                intent.setClass(getActivity(), VideoListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ani_top_get_into, R.anim.ani_bottom_sign_out);
                break;
        }
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(getActivity(), SPkey.Language);
        String format = "";
        if (language.equals("English")) {
            format = "dd-MM-yyyy HH:mm:ss";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
//        String accountId = SPUtils.look(getActivity(), SPkey.accountId);
        String account_name = SPUtils.look(getActivity(), SPkey.username);
//        long timeStampSec = TimeUtils.get13ServiceTimeStamp() / 1000;
        pos_GetElectronicPrizeRecord pos_gameQueryInfo = new pos_GetElectronicPrizeRecord(account_name);
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetElectronicPrizeRecord)
                .upJson(s1)
                .execute(new vStringCallback(getActivity()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                List<LiveBean> liveList = new ArrayList<>();
                                Type listType = new TypeToken<List<LiveBean>>() {
                                }.getType();
                                liveList = new Gson().fromJson(jsonObject.getJSONArray("drawlist").getJSONArray(0).toString(), listType);
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


    private void showList(List<LiveBean> liveList) {
        liveOneList.clear();
        liveTwoList.clear();
        for (int i = 0; i < liveList.size(); i++) {
            if (liveList.get(i).getElectronicStatus().equals("00")) {
                liveOneList.add(liveList.get(i));
            } else {
                liveTwoList.add(liveList.get(i));
            }
        }
        liveAdapter = new LiveAdapter(getActivity(), this);
        liveAdapter.setList(liveTwoList);
        relLive.setAdapter(liveAdapter);
        if (liveOneList.size() > 0){
            if (liveList.size() == 1){
                tvLiveOneNum.setText(liveOneList.get(0).getDrawNumber());
                tvLiveOneName.setText(liveOneList.get(0).getGameName() + getString(R.string.live_broadcast_lottery));
                relLiveOne.setVisibility(View.VISIBLE);
                relLiveTwo.setVisibility(View.GONE);
            }else if (liveList.size() == 2){
                tvLiveOneNum.setText(liveOneList.get(0).getDrawNumber());
                tvLiveTwoNum.setText(liveOneList.get(1).getDrawNumber());
                tvLiveOneName.setText(liveOneList.get(0).getGameName() + getString(R.string.live_broadcast_lottery));
                tvLiveTwoName.setText(liveOneList.get(1).getGameName() + getString(R.string.live_broadcast_lottery));
                relLiveOne.setVisibility(View.VISIBLE);
                relLiveTwo.setVisibility(View.VISIBLE);
            }else {
                tvLiveOneNum.setText(liveOneList.get(0).getDrawNumber());
                tvLiveTwoNum.setText(liveOneList.get(1).getDrawNumber());
                tvLiveThreeNum.setText(liveOneList.get(2).getDrawNumber());
                tvLiveOneName.setText(liveOneList.get(0).getGameName() + getString(R.string.live_broadcast_lottery));
                tvLiveTwoName.setText(liveOneList.get(1).getGameName() + getString(R.string.live_broadcast_lottery));
                tvLiveThreeName.setText(liveOneList.get(2).getGameName() + getString(R.string.live_broadcast_lottery));
                relLiveOne.setVisibility(View.VISIBLE);
                relLiveTwo.setVisibility(View.VISIBLE);
                relLiveThree.setVisibility(View.VISIBLE);
            }
        }else {
            relLiveOne.setVisibility(View.GONE);
            relLiveTwo.setVisibility(View.GONE);
//            relLiveTwo.setVisibility(View.GONE);
        }
        if (liveOneList.get(0).getLiveStatus().equals("01")) {
            tvLiveRightOne.setText(getString(R.string.playing));
            tvLiveRightOne.setBackgroundResource(R.drawable.live_top_bg_yes);
            imgOneLeft.setImageResource(R.drawable.live_bc_en);
            imgOneRight.setImageResource(R.drawable.live_bc_bt);
        } else {
            tvLiveRightOne.setText(getString(R.string.not_started));
            tvLiveRightOne.setBackgroundResource(R.drawable.live_top_bg_no);
            imgOneLeft.setImageResource(R.drawable.live_bc_ens);
            imgOneRight.setImageResource(R.drawable.live_bc_bts);
        }
        if (liveOneList.get(1).getLiveStatus().equals("01")) {
            tvLiveRightTwo.setText(getString(R.string.playing));
            tvLiveRightTwo.setBackgroundResource(R.drawable.live_top_bg_yes);
            imgTwoLeft.setImageResource(R.drawable.live_bc_en);
            imgTwoRight.setImageResource(R.drawable.live_bc_bt);
        } else {
            tvLiveRightTwo.setText(getString(R.string.not_started));
            tvLiveRightTwo.setBackgroundResource(R.drawable.live_top_bg_no);
            imgTwoLeft.setImageResource(R.drawable.live_bc_ens);
            imgTwoRight.setImageResource(R.drawable.live_bc_bts);
        }
        if (liveOneList.get(2).getLiveStatus().equals("01")) {
            tvLiveRightThree.setText(getString(R.string.playing));
            tvLiveRightThree.setBackgroundResource(R.drawable.live_top_bg_yes);
            imgThreeLeft.setImageResource(R.drawable.live_bc_en);
            imgThreeRight.setImageResource(R.drawable.live_bc_bt);
        } else {
            tvLiveRightThree.setText(getString(R.string.not_started));
            tvLiveRightThree.setBackgroundResource(R.drawable.live_top_bg_no);
            imgThreeLeft.setImageResource(R.drawable.live_bc_ens);
            imgThreeRight.setImageResource(R.drawable.live_bc_bts);
        }
    }

}
