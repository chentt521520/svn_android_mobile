package com.jc.lottery.fragment.lottery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.betting.BettingRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.req.HistoryBettingQueryBean;
import com.jc.lottery.bean.resp.BettingListInfo;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class G90x5CashRecordFragment extends BaseFragment {

    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    @BindView(R.id.lly_settlement_nodata)
    PercentLinearLayout llySettlementNodata;
    private List<BettingListInfo> list = new ArrayList<BettingListInfo>();
    private BettingRecordAdapter recordAdapter = null;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
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
//        showList();
//        relBetting.setAdapter(recordAdapter);
        getHttpInfo();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    private void showList() {
//        for (int i = 0; i < 10; i++) {
//            list.add("No." + (7894521 + i));
//        }
        recordAdapter = new BettingRecordAdapter(getActivity(),"90x5","2");
        recordAdapter.setList(list);
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
//        String terminalNum = SPUtils.look(getActivity(), SPkey.terminalId);
        HistoryBettingQueryBean.BettingInfo bettingInfo = new HistoryBettingQueryBean.BettingInfo("90x5", "03","1");
        HistoryBettingQueryBean.DataBean dataBean = new HistoryBettingQueryBean.DataBean(bettingInfo);
        HistoryBettingQueryBean historyBettingQueryBean = new HistoryBettingQueryBean(account_name, dataBean);
        String s1 = new Gson().toJson(historyBettingQueryBean);
        OkGo.<String>post(MyUrl.historyBettingQuery)
                .upJson(s1)
                .execute(new vStringCallback(getContext()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            if (refreshType == 1) {
                                if (list != null) {
                                    list.clear();
                                }
                            }
                            srlReceiving.finishRefresh();
                            srlReceiving.finishLoadmore();
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                pageCount = jsonObject.getInt("pageCount");
                                pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                                List<BettingListInfo> listInfos = new ArrayList<BettingListInfo>();
                                Type listType = new TypeToken<List<BettingListInfo>>() {
                                }.getType();
//                                list = new Gson().fromJson(jsonObject.getJSONArray("bettingList").toString(), listType);
                                listInfos = new Gson().fromJson(jsonObject.getJSONArray("bettingList").toString(), listType);
                                list.addAll(listInfos);
                                if (list.size() > 0) {
                                    recordAdapter = new BettingRecordAdapter(getActivity(),"90x5","2");
                                    recordAdapter.setList(list);
                                    relBetting.setAdapter(recordAdapter);
                                    llySettlementNodata.setVisibility(View.GONE);
                                } else {
                                    llySettlementNodata.setVisibility(View.VISIBLE);
                                }
                                if (refreshType != 1) {
//                                    relPaymentRecord.scrollToPosition(payListBeanList.size() - 1);
                                    scrollToPosition();
                                }
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        if (codeType){
                            if (null != getActivity()) {
                                getActivity().finish();
                            }
                        }
                    }
                });
    }

}
