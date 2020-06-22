package com.jc.lottery.activity.immediate;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.ActivationTwoAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.DeliveryDetailsBean;
import com.jc.lottery.bean.req.DeliveryDetailsQueryBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 激活详情页面
 */
public class ActivationDetailsActivity extends BaseActivity {

    @BindView(R.id.lly_activation_details_back)
    LinearLayout llyActivationDetailsBack;
    @BindView(R.id.rel_activation_details)
    RecyclerView relActivationDetails;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    private ActivationTwoAdapter activationTwoAdapter;
    private List<DeliveryDetailsBean> list = new ArrayList<DeliveryDetailsBean>();
    private String deliveryId;
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_activation_details;
    }

    @Override
    public void getPreIntent() {
        deliveryId = getIntent().getStringExtra("deliveryId");

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (height * 0.6);
        params.width = (int) (width * 0.9);
        getWindow().setAttributes(params);
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
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
        relActivationDetails.setLayoutManager(new GridLayoutManager(this, 1));
        srlReceiving.setEnableRefresh(false);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                refreshType = 1;
                pageNo = 1;
                srlReceiving.resetNoMoreData();
                activationBookSelectHttp();
            }
        });

        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                codeType = false;
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    activationBookSelectHttp();
                } else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
    }

    @Override
    public void initData() {
        activationTwoAdapter = new ActivationTwoAdapter(this);
        activationTwoAdapter.setList(list);
        relActivationDetails.setAdapter(activationTwoAdapter);
        activationBookSelectHttp();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_activation_details_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_activation_details_back:
                finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void activationBookSelectHttp() {
//        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        DeliveryDetailsQueryBean.DeliveryInfoBean activeInfo = new DeliveryDetailsQueryBean.DeliveryInfoBean(deliveryId, pageNo + "");
        DeliveryDetailsQueryBean.DataBean dataBean = new DeliveryDetailsQueryBean.DataBean(activeInfo);
        DeliveryDetailsQueryBean deliveryDetailsQueryBean = new DeliveryDetailsQueryBean(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(deliveryDetailsQueryBean);
        OkGo.<String>post(MyUrl.pos_deliveryDetailsQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ProgressUtil.dismissProgressDialog();
                        if (refreshType == 1) {
                            if (list != null) {
                                list.clear();
                                pageNo = 1;
                            }
                        }
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                            if (jsonObject.getString("code").equals("00000")) {
                                Type listType = new TypeToken<List<DeliveryDetailsBean>>() {
                                }.getType();
                                List<DeliveryDetailsBean> actationList = new ArrayList<DeliveryDetailsBean>();
                                actationList = new Gson().fromJson(jsonObject.getJSONArray("deliveryDetailsList").toString(), listType);
                                list.addAll(actationList);
//                                for (int i = 0; i < list.size(); i++) {
//                                    list.get(i).setCreateTime(timeStamp2Date(Long.parseLong(activationQueryBeanList.get(i).getCreateTime())));
//                                }
                                activationTwoAdapter.setList(list);
                                relActivationDetails.setAdapter(activationTwoAdapter);
                                if (pageNo > 1){
                                    if (activationTwoAdapter.getItemCount() > 28) {
                                        relActivationDetails.scrollToPosition(activationTwoAdapter.getItemCount() - 28);
                                    }
                                }
//                                if (activationQueryBeanList.size() > 0) {
//                                    llyActivationBottom.setVisibility(View.VISIBLE);
//                                    llyReceivingNodata.setVisibility(View.GONE);
//                                } else {
//                                    llyActivationBottom.setVisibility(View.GONE);
//                                    llyReceivingNodata.setVisibility(View.VISIBLE);
//                                }
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        if (codeType){
                            finish();
                        }
                    }
                });
    }
}
