package com.jc.lottery.fragment.lottery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.betting.LotteryRecordAdapter;
import com.jc.lottery.adapter.betting.VictoryRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.req.pos_Screen37x6History;
import com.jc.lottery.bean.req.pos_listQuery;
import com.jc.lottery.bean.resp.Resp_36x7_history;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class VictoryLotteryRecordFragment extends BaseFragment {

    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    private List<Resp_36x7_history.DrawListBean> datas;
    private VictoryRecordAdapter recordAdapter = null;
    private int refreshType = 1;
    private int pageCount = 1;
    private int pageNo = 1;
    private int pageNum = 1;
    private int lastOffset = 0;
    private int lastPosition = 0;
    private boolean codeType = true;
    private String game = "";

    public VictoryLotteryRecordFragment(String game) {
        this.game = game;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_betting_record;
    }

    @Override
    protected void initView(View view) {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.pull_up_load_more);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.release_load_now);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.xlistview_header_hint_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.load_complete);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.failed_to_load);
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.no_more);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relBetting.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        getVictoryWinQueryHttpInfo();
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                refreshType = 1;
                pageNo = 1;
//                getRecordHttp();
                getVictoryWinQueryHttpInfo();
                srlReceiving.resetNoMoreData();
            }
        });
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                codeType = false;
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    getVictoryWinQueryHttpInfo();
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
        relBetting.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
    }

    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) relBetting.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(relBetting.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) relBetting.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    private void getVictoryWinQueryHttpInfo() {
        datas = new ArrayList<>();
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        String account_pass = SPUtils.look(getActivity(), SPkey.password);
        pos_listQuery.NoticeInfo orderInfo = new pos_listQuery.NoticeInfo("");
        pos_listQuery.DataBean dataBean = new pos_listQuery.DataBean(orderInfo);
        pos_listQuery pos_listQuery = new pos_listQuery(account_name,account_pass, dataBean);
        String s1 = new Gson().toJson(pos_listQuery);
        OkGo.<String>post(MyUrl.pos_listQuery)
                .upJson(s1)
                .execute(new vStringCallback(getActivity()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        if (refreshType == 1) {
                            if (datas != null) {
                                datas.clear();
                            }
                        }
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                pageCount = 1;
//                                ToastUtils.showShort(jsonObject.getString("message"));
                                JSONArray drawNoticeList = jsonObject.getJSONArray("drawNoticeList");
                                for (int i = 0; i < drawNoticeList.length(); i++) {
                                    JSONObject drawJson = drawNoticeList.getJSONObject(i);
                                    Resp_36x7_history.DrawListBean drawListBean = new Resp_36x7_history.DrawListBean();
                                    drawListBean.setDraw(drawJson.getString("issue_no"));
                                    drawListBean.setPrizeNum("");
                                    drawListBean.setPrizeTime(timeStamp2Date(drawJson.getLong("issue_end_time")));
                                    drawListBean.setId(1);
                                    datas.add(drawListBean);
                                }
                                recordAdapter = new VictoryRecordAdapter(getActivity(),game);
                                recordAdapter.setList(datas);
                                relBetting.setAdapter(recordAdapter);
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                            ProgressUtil.dismissProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        if(codeType){
                            if (null != getActivity()){
                                getActivity().finish();
                            }
                        }
                    }
                });
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
}
