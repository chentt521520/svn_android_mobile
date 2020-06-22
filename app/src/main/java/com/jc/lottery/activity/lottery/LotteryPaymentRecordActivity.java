package com.jc.lottery.activity.lottery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.PaymentRecordAdapter;
import com.jc.lottery.adapter.RechargeRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetPaymentRecordQuery;
import com.jc.lottery.bean.req.pos_GetRechargeRecord;
import com.jc.lottery.bean.resp.PaymentBean;
import com.jc.lottery.bean.resp.RechargeBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值记录页
 */
public class LotteryPaymentRecordActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.rel_payment_record)
    RecyclerView relPaymentRecord;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    @BindView(R.id.lly_payment_nodata)
    PercentLinearLayout llyPaymentNodata;
    private RechargeRecordAdapter paymentRecordAdapter;
    private RechargeBean paymentBean;
    private List<RechargeBean.RechargeList> payListBeans = new ArrayList<RechargeBean.RechargeList>();
    private int pageNo = 1;
    private boolean codeType = true;
    private String type = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_record;
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
        relPaymentRecord.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        if (getIntent().hasExtra("type")){
            type = getIntent().getStringExtra("type");
        }
        paymentRecordAdapter = new RechargeRecordAdapter(this);
        getHttpInfo();
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
//                getRecordHttp();
                getHttpInfo();
            }
        });
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                codeType = false;
            }
        });
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        String gameType = "";
        if (type.equals("")){
            gameType = "01";
        }else {
            gameType = "02";
        }
        final pos_GetRechargeRecord.RechargeInfo payRecordBean = new pos_GetRechargeRecord.RechargeInfo(pageNo + "","","","","","",gameType);
        pos_GetRechargeRecord.DataBean dataBean = new pos_GetRechargeRecord.DataBean(payRecordBean);
        pos_GetRechargeRecord pos_getPaymentRecordQuery = new pos_GetRechargeRecord(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getPaymentRecordQuery);
        OkGo.<String>post(MyUrl.rechargeRecord)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        if (refreshType == 1) {
//                            if (settlementRecordBeanList != null) {
//                                settlementRecordBeanList.clear();
//                                pageNo = 1;
//                            }
//                        }
                        srlReceiving.finishRefresh();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                paymentBean = new Gson().fromJson(response.body(), RechargeBean.class);
                                payListBeans = paymentBean.getRechargeList();
                                paymentRecordAdapter.setList(payListBeans);
                                relPaymentRecord.setAdapter(paymentRecordAdapter);
                            }else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (payListBeans.size() < 1){
                            llyPaymentNodata.setVisibility(View.VISIBLE);
                        }else {
                            llyPaymentNodata.setVisibility(View.GONE);
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        if (codeType){
                            finish();
                        }
                    }
                });
    }

    /**
     * 加载完成
     */
    private void onLoad() {
    }


    @OnClick({R.id.lly_manual_scanner_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
        }
    }

}
