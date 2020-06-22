package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetRechargeDetail;
import com.jc.lottery.bean.req.pos_GetRecordDetails;
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
 * 充值详情页面
 */
public class RechargeRecordsDetailActivity extends BaseActivity {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.tv_recharge_detail_title)
    TextView tvRechargeDetailTitle;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.tv_recharge_order)
    TextView tvRechargeOrder;
    @BindView(R.id.tv_recharge_mode)
    TextView tvRechargeMode;
    @BindView(R.id.tv_recharge_state)
    TextView tvRechargeState;
    @BindView(R.id.tv_recharge_phone)
    TextView tvRechargePhone;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.tv_recharge_real)
    TextView tvRechargeReal;
    @BindView(R.id.tv_recharge_time)
    TextView tvRechargeTime;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    private String order = "";
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_record_detail;
    }

    @Override
    public void getPreIntent() {
        order = getIntent().getStringExtra("order");
    }

    @Override
    public void initData() {
        getRecordHttp(order);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                codeType = false;
                getRecordHttp(order);
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
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
    }

    @OnClick({R.id.lly_reward_record_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;

            default:
                break;
        }
    }

    private void getRecordHttp(String order) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetRechargeDetail.RechargeInfo rechargeInfo = new pos_GetRechargeDetail.RechargeInfo(order);
        pos_GetRechargeDetail.DataBean dataBean = new pos_GetRechargeDetail.DataBean(rechargeInfo);
        pos_GetRechargeDetail pos_gameQueryInfo = new pos_GetRechargeDetail(account_name, account_password, "3", dataBean);
        String json = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.rechargeDetail)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            srlReceiving.finishRefresh();
                            JSONObject json = new JSONObject(response.body());
                            if (json.getString("code").equals("00000")) {
                                JSONObject rechargeDetail = json.getJSONObject("rechargeDetail");
                                showInitView(rechargeDetail);
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

    private void showInitView(JSONObject rechargeDetail) throws JSONException {
        tvRechargeOrder.setText(rechargeDetail.getString("orderCode"));
        if (rechargeDetail.getString("phone").equals("")){
            tvRechargePhone.setText("--");
        }else {
            tvRechargePhone.setText(rechargeDetail.getString("phone"));
        }
        if (rechargeDetail.has("payTime")) {
            tvRechargeTime.setText(timeStamp2Date(rechargeDetail.getLong("payTime")));
        }else {
            tvRechargeTime.setText("--");
        }
        tvRechargeMoney.setText(MoneyUtil.getIns().GetMoney(rechargeDetail.getString("payMoney")) + getString(R.string.price_unit));
        tvRechargeReal.setText(MoneyUtil.getIns().GetMoney(rechargeDetail.getString("payMoneyReal")) + getString(R.string.price_unit));
        if (rechargeDetail.getString("payMethod").equals("")) {
            tvRechargeMode.setText("--");
        } else if (rechargeDetail.getString("payMethod").equals("01")) {
            tvRechargeMode.setText(getString(R.string.offline_payment));
        } else if (rechargeDetail.getString("payMethod").equals("02")) {
            tvRechargeMode.setText("Mtn");
        } else if (rechargeDetail.getString("payMethod").equals("03")) {
            tvRechargeMode.setText("Airtel");
        } else if (rechargeDetail.getString("payMethod").equals("04")) {
            tvRechargeMode.setText("Zamtel");
        }

        if (rechargeDetail.getString("payState").equals("01")) {
            tvRechargeState.setText(getString(R.string.recharge_scussess));
            tvRechargeState.setTextColor(Color.rgb(0,75,255));
        } else if (rechargeDetail.getString("payState").equals("02")) {
            tvRechargeState.setText(getString(R.string.failure_of_recharge));
            tvRechargeState.setTextColor(Color.rgb(253, 8, 8));
        } else if (rechargeDetail.getString("payState").equals("00")) {
            tvRechargeState.setText(getString(R.string.waiting_for_recharge));
            tvRechargeState.setTextColor(Color.rgb(255, 102, 0));
        } else if (rechargeDetail.getString("payState").equals("03")) {
            tvRechargeState.setText(getString(R.string.to_be_confirmed));
            tvRechargeState.setTextColor(Color.rgb(255, 102, 0));
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

    private void generateData() {
//        String orderCode = receivingOrderBean.getOrderCode();
//        String accountId = SPUtils.look(this, SPkey.accountId);
//        String terminalId = SPUtils.look(this, SPkey.terminalId);
//        DeliveryBean deliveryBean = new DeliveryBean();
//        deliveryBean.setGameAlias(receivingOrderBean.getGameName());
//        deliveryBean.setTicketNum(receivingOrderBean.getTicketNum());
//        deliveryBean.setRecipientId(accountId);
//        deliveryBean.setGetDeviceId(terminalId);
//        deliveryBean.setPayState("00");
//        deliveryBean.setGetChannels("3");
//        deliveryBean.setOrderCode(orderCode);
//        deliveryBean.setType("get");
//        String deliveryJson = new Gson().toJson(deliveryBean);
//        try {
//            // 根据字符串生成二维码图片并显示在界面上，
//            bitmapQRCode = createQRCode(deliveryJson, DensityUtils.dip2px(this, 140));
//            imgLotteryPopOne.setImageBitmap(bitmapQRCode);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
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
