package com.jc.lottery.activity.immediate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.ActivationListDetailAdapter;
import com.jc.lottery.adapter.ActivationRecordAdapter;
import com.jc.lottery.adapter.SettlementDetailAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.ActivationRecordBean;
import com.jc.lottery.bean.DeliveryDetailsListBean;
import com.jc.lottery.bean.req.pos_GetActivateRecord;
import com.jc.lottery.bean.req.pos_GetdeliveryDetailsQuery;
import com.jc.lottery.bean.resp.ReceivingActivationBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 激活记录页面
 */
public class ActivationListDetailsActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.lly_no)
    LinearLayout llyNo;
    @BindView(R.id.tv_details_order)
    TextView tvDetailsOrder;
    @BindView(R.id.tv_details_game)
    TextView tvDetailsGame;
    @BindView(R.id.tv_details_state)
    TextView tvDetailsState;
    @BindView(R.id.tv_details_person)
    TextView tvDetailsPerson;
    @BindView(R.id.tv_details_delivery)
    TextView tvDetailsDelivery;
    @BindView(R.id.tv_details_time)
    TextView tvDetailsTime;
    @BindView(R.id.lv_details)
    ListView lvDetails;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    private String deliveryId;
    private String order,game,state,person,personl,time;
    private List<DeliveryDetailsListBean> detailsList = new ArrayList<DeliveryDetailsListBean>();
    private ActivationListDetailAdapter activationListDetailAdapter;
    private int pageNo = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载

    @Override
    public int getLayoutId() {
        return R.layout.activity_activation_lists_details;
    }

    @Override
    public void getPreIntent() {
        deliveryId = getIntent().getStringExtra("deliveryId");
        order = getIntent().getStringExtra("order");
        game = getIntent().getStringExtra("game");
        state = getIntent().getStringExtra("state");
        person = getIntent().getStringExtra("person");
        personl = getIntent().getStringExtra("personl");
        time = getIntent().getStringExtra("time");
        if (person.trim().equals("")){
            person = " -- ";
        }
        tvDetailsOrder.setText(order);
        tvDetailsGame.setText(game);
        if (state.equals("00")){
            tvDetailsState.setText(getString(R.string.not_active));
            llyNo.setVisibility(View.GONE);
        }else {
            tvDetailsState.setText(getString(R.string.activated));
        }

        tvDetailsPerson.setText(personl);
        tvDetailsDelivery.setText(person);
        tvDetailsTime.setText(time);
    }

    @Override
    public void initView() {
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
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshType = 1;
                pageNo = 1;
                srlReceiving.resetNoMoreData();
                getHttpInfo();
            }
        });
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    getHttpInfo();
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });

    }

    @Override
    public void initData() {
        getHttpInfo();
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetdeliveryDetailsQuery.DataBean.DeliveryInfo recordInfo = new pos_GetdeliveryDetailsQuery.DataBean.DeliveryInfo(deliveryId, pageNo + "", "", "");
        pos_GetdeliveryDetailsQuery pos_getSettlementRecord = new pos_GetdeliveryDetailsQuery(account_name, account_password, "3", recordInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.pos_GetDeliveryDetailsQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
//                        if (refreshType == 1) {
//                            if (settlementRecordBeanList != null) {
//                                settlementRecordBeanList.clear();
//                                pageNo = 1;
//                            }
//                        }
//                        onLoad();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            if (pageNo == pageCount){
                                srlReceiving.finishLoadmoreWithNoMoreData();
                            }
                            if (jsonObject.getString("code").equals("00000")) {
                                JSONArray deliveryDetailsList = jsonObject.getJSONArray("deliveryDetailsList");
                                List<DeliveryDetailsListBean> list = new ArrayList<DeliveryDetailsListBean>();
                                for (int i = 0; i < deliveryDetailsList.length(); i++) {
                                    JSONObject beanJson = deliveryDetailsList.getJSONObject(i);
                                    DeliveryDetailsListBean deliveryDetailsListBean = new DeliveryDetailsListBean();
                                    deliveryDetailsListBean.setActiveState(beanJson.getString("activeState"));
                                    deliveryDetailsListBean.setActiveTime(beanJson.getString("activeTime"));
                                    deliveryDetailsListBean.setBookNum(beanJson.getString("bookNum"));
                                    deliveryDetailsListBean.setCartonNo(beanJson.getString("cartonNo"));
                                    deliveryDetailsListBean.setSchemeNum(beanJson.getString("schemeNum"));

                                    list.add(deliveryDetailsListBean);
                                }
                                if (refreshType == 1){
                                    detailsList.clear();
                                }
                                detailsList.addAll(list);
                                if (activationListDetailAdapter != null){
                                    activationListDetailAdapter.notifyDataSetChanged();
                                }else {
                                    activationListDetailAdapter = new ActivationListDetailAdapter(ActivationListDetailsActivity.this, detailsList);
//                                activationListDetailAdapter.setAllGroup(detailsList);
                                    lvDetails.setAdapter(activationListDetailAdapter);
                                }


                                setListViewHeight(lvDetails);
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
//                        if (refreshType == 2) {
////                            xlvSettlement.setSelection(pageNo * pageNum);
//                            xlvSettlement.setSelectionFromTop(mListFocus, firstVisiblePositionTop);
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
