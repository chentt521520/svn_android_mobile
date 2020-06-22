package com.jc.lottery.fragment.lottery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.lottery.s90x5HistoryQueryActivity;
import com.jc.lottery.adapter._36x7HistoryAdapter;
import com.jc.lottery.adapter.betting.BettingRecordAdapter;
import com.jc.lottery.adapter.betting.LotteryRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.req.pos_Screen37x6History;
import com.jc.lottery.bean.resp.Resp_36x7_history;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class G90x5LotteryRecordFragment extends BaseFragment {

    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    private LotteryRecordAdapter recordAdapter = null;
    private List<Resp_36x7_history.DrawListBean> datas;
    private int refreshType = 1;
    private int pageCount = 1;
    private int pageNo = 1;
    private int pageNum = 1;
    private int lastOffset = 0;
    private int lastPosition = 0;
    private boolean codeType = true;

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
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relBetting.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                getHttpInfo();
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
                    getHttpInfo();
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
    public void initData() {
        getHttpInfo();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    public void getHttpInfo() {
        datas = new ArrayList<>();

        String account_name = SPUtils.look(getActivity(), SPkey.username);
        pos_Screen37x6History.ColorPeriodInfo colorPeriodInfo = new pos_Screen37x6History.ColorPeriodInfo("90x5");
        pos_Screen37x6History.DataBean dataBean = new pos_Screen37x6History.DataBean(colorPeriodInfo);
        pos_Screen37x6History screen_k3_history = new pos_Screen37x6History(account_name, dataBean);
        String s = new Gson().toJson(screen_k3_history);
        LogUtils.e("  请求参数  " + s);
        OkGo.<String>post(MyUrl.screen_90x5_History)
                .upJson(s)
                .execute(new vStringCallback(getActivity()) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        if (refreshType == 1) {
                            if (datas != null) {
                                datas.clear();
                            }
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            pageCount = jsonObject.getInt("pageCount");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        LogUtils.e(" 返回内容 " + response.body());
                        Resp_36x7_history resp_kuai3_history = new Gson().fromJson(response.body(), Resp_36x7_history.class);
                        for (Resp_36x7_history.DrawListBean drawListBean : resp_kuai3_history.getDrawList()) {
                            drawListBean.setPrizeTime(timeStamp2Date(Long.parseLong(drawListBean.getPrizeTime())));
                            datas.add(drawListBean);
                        }
                        recordAdapter = new LotteryRecordAdapter(getActivity(), "90x5");
                        recordAdapter.setList(datas);
                        relBetting.setAdapter(recordAdapter);
                        if (refreshType != 1) {
//                                    relPaymentRecord.scrollToPosition(payListBeanList.size() - 1);
                            scrollToPosition();
                        }
//                        _36x7HistoryAdapter adapter = new _36x7HistoryAdapter(getActivity(), datas);
//                        lv90x5History.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
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
