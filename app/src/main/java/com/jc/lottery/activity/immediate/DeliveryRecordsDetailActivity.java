package com.jc.lottery.activity.immediate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.money.OrderPaymentActivity;
import com.jc.lottery.adapter.ReceivingActivationAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.base.application.LotteryApplication;
import com.jc.lottery.bean.req.pos_GetLogisticsCancel;
import com.jc.lottery.bean.req.pos_GetRecordDetails;
import com.jc.lottery.bean.resp.ReceivingActivationBean;
import com.jc.lottery.bean.resp.ReceivingOrderBean;
import com.jc.lottery.content.Constant;
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
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 派送记录详情页面
 */
public class DeliveryRecordsDetailActivity extends BaseActivity {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.tv_recharge_detail_title)
    TextView tvRechargeDetailTitle;
    @BindView(R.id.tv_receiving_one)
    TextView tvReceivingOne;
    @BindView(R.id.tv_receiving_two)
    TextView tvReceivingTwo;
    @BindView(R.id.tv_receiving_three)
    TextView tvReceivingThree;
    @BindView(R.id.tv_receiving_four)
    TextView tvReceivingFour;
    @BindView(R.id.tv_receiving_five)
    TextView tvReceivingFive;
    @BindView(R.id.tv_receiving_six)
    TextView tvReceivingSix;
    @BindView(R.id.img_lottery_pop_one)
    ImageView imgLotteryPopOne;
    @BindView(R.id.lly_lottery_pop_zoom)
    LinearLayout llyLotteryPopZoom;
    @BindView(R.id.lly_receiving_pop)
    LinearLayout llyReceivingPop;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.tv_receiving_money)
    TextView tvReceivingMoney;
    @BindView(R.id.lv_receiving_detail)
    ListView lvReceivingDetail;
    @BindView(R.id.lly_receiving_activation)
    LinearLayout llyReceivingActivation;
    @BindView(R.id.tv_receiving_seven)
    TextView tvReceivingSeven;
    @BindView(R.id.tv_receiving_real_money)
    TextView tvReceivingRealMoney;
    @BindView(R.id.bt_receiving_record_order)
    Button btReceivingRecordOrder;
    @BindView(R.id.lly_receiving_record_order)
    PercentLinearLayout llyReceivingRecordOrder;
    @BindView(R.id.tv_receiving_state)
    TextView tvReceivingState;
    @BindView(R.id.tv_receiving_time)
    TextView tvReceivingTime;
    @BindView(R.id.tv_receiving_payment_method)
    TextView tvReceivingPaymentMethod;
    @BindView(R.id.tv_receiving_real_moneys)
    TextView tvReceivingMoneys;
    @BindView(R.id.bt_receiving_record_cancel)
    Button btReceivingRecordCancel;
    @BindView(R.id.lly_receiving_record_cancel)
    PercentLinearLayout llyReceivingRecordCancel;
    @BindView(R.id.sv_detail)
    ScrollView svDetail;
    private String recordDetailsId = "";
    private ReceivingOrderBean receivingOrderBean = null;
    private Bitmap bitmapQRCode = null;
    private List<ReceivingActivationBean> receivingActivationBeanList = new ArrayList<ReceivingActivationBean>();
    private ReceivingActivationAdapter receivingActivationAdapter;
    private String activityType = "00"; // 00：领取记录 01：派送记录
    private String roleAlias = "";
    public static DeliveryRecordsDetailActivity ins;
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载

    @Override
    public int getLayoutId() {
        return R.layout.activity_receiving_record_detail;
    }

    @Override
    public void getPreIntent() {
        roleAlias = SPUtils.look(LotteryApplication.getInstance(), SPkey.roleAlias);
        recordDetailsId = getIntent().getStringExtra("recordDetailsId");
        activityType = getIntent().getStringExtra("activityType");
    }

    @Override
    public void initData() {
        getRecordHttp();
    }

    @Override
    public void initListener() {
        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                refreshType = 1;
                getRecordHttp();
            }
        });
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshType = 2;
                if (pageNo < pageCount) {
                    pageNo++;
                    getRecordHttp();
                } else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
        tvReceivingOne.setTextIsSelectable(true);
    }

    @Override
    public void initView() {
        ins = this;
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
//        headerReceiving.setTimeFormat(new SimpleDateFormat(getString(R.string.xlistview_header_last_time) +" %s"));
        if (activityType.equals("00")) {
            tvRechargeDetailTitle.setText(R.string.receiving_details);
            tvReceivingState.setText(R.string.receiving_status);
            tvReceivingTime.setText(R.string.receive_time);
        } else {
            tvRechargeDetailTitle.setText(R.string.delivery_details);
            tvReceivingState.setText(R.string.delivery_status);
            tvReceivingTime.setText(R.string.delivery_time);
        }
    }

    @OnClick({R.id.lly_reward_record_back, R.id.img_lottery_pop_one, R.id.lly_lottery_pop_zoom, R.id.bt_receiving_record_order, R.id.bt_receiving_record_cancel})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.img_lottery_pop_one:
                if (bitmapQRCode != null) {
//                    KapBitmapHalper.SaveImageView(imgLotteryPopOne);
//                    intent.setClass(this, PreViewImageActivity.class);
//                    startActivity(intent);
                }
                break;
            case R.id.lly_lottery_pop_zoom:
                if (bitmapQRCode != null) {
//                    KapBitmapHalper.SaveImageView(imgLotteryPopOne);
//                    intent.setClass(this, PreViewImageActivity.class);
//                    startActivity(intent);
                }
                break;
            case R.id.bt_receiving_record_order:
                intent.putExtra("openType", "2");
                intent.putExtra("money", receivingOrderBean.getPayMoney());
                intent.putExtra("order", receivingOrderBean.getOrderCode());
                intent.putExtra("gameName", receivingOrderBean.getGameName());
                intent.putExtra("book", receivingOrderBean.getTicketNum() + "");
                intent.setClass(this, OrderPaymentActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_receiving_record_cancel:
                getOrderCancelHttp();
                break;
            default:
                break;
        }
    }

    public void showOrder() {
        getRecordHttp();
        ReceivingRecordsActivity.ins.onRefresh();
    }

    private void getRecordHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetRecordDetails.DataBean.RecordDetailsInfo recordDetailsInfo = new pos_GetRecordDetails.DataBean.RecordDetailsInfo(Integer.parseInt(recordDetailsId), pageNo);
        pos_GetRecordDetails pos_gameQueryInfo = new pos_GetRecordDetails(account_name, account_password, "3", recordDetailsInfo);
        String json = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetRecordDetails)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        if (pageNo == 1) {
                            receivingActivationBeanList.clear();
                        }
                        try {
                            srlReceiving.finishRefresh();
                            srlReceiving.finishLoadmore();
                            JSONObject json = new JSONObject(response.body());
                            pageCount = Integer.parseInt(json.getString("pageCount"));
                            if (json.getString("code").equals("00000")) {
                                JSONObject getRecordDetails = json.getJSONArray("getList").getJSONObject(0);
                                receivingOrderBean = new ReceivingOrderBean();
                                receivingOrderBean.setGameName(getRecordDetails.getString("gameName"));
                                receivingOrderBean.setGetTime(getRecordDetails.getString("getTime"));
                                receivingOrderBean.setCreateTime(getRecordDetails.getString("createTime"));
                                receivingOrderBean.setOrderCode(getRecordDetails.getString("orderCode"));
                                receivingOrderBean.setPayMoney(getRecordDetails.getString("payMoney"));
                                receivingOrderBean.setPayMoneyReal(getRecordDetails.getString("payMoneyReal"));
                                receivingOrderBean.setPayState(getRecordDetails.getString("payState"));
                                receivingOrderBean.setRecipient(getRecordDetails.getString("recipient"));
//                                receivingOrderBean.setSendDevice(getRecordDetails.getString("sendDevice"));
//                                receivingOrderBean.setSendPerson(getRecordDetails.getString("sendPerson"));
                                receivingOrderBean.setStatus(getRecordDetails.getString("status"));
                                receivingOrderBean.setTicketNum(getRecordDetails.getString("ticketNum"));
                                if (getRecordDetails.has("payMethod")) {
                                    receivingOrderBean.setPayMethod(getRecordDetails.getString("payMethod"));
                                } else {
                                    receivingOrderBean.setPayMethod("");
                                }
//                                if (receivingOrderBean.getStatus().equals("00")) {
                                    pageNum = Integer.parseInt(json.getString("pageNum"));
                                    int num = 1;
                                    if (pageNo == 1) {
                                        num = 1;
                                    }else {
                                        num = (pageNo - 1) * pageNum + 1;
                                    }
                                    JSONArray jsonArray = json.getJSONArray("getList");
                                    if (jsonArray.getJSONObject(0).has("activeState")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            ReceivingActivationBean receivingActivationBean = new ReceivingActivationBean();
                                            receivingActivationBean.setActiveState(jsonObject.getString("activeState"));
                                            receivingActivationBean.setBookNum(jsonObject.getString("bookNum"));
                                            receivingActivationBean.setEndTicketNum("");
                                            receivingActivationBean.setSchemeNum(jsonObject.getString("schemeNum"));
                                            receivingActivationBean.setStartTicketNum("");
                                            receivingActivationBean.setIndex(num);
                                            if (jsonObject.has("activeTime")) {
                                                if (jsonObject.getString("activeTime").equals("")) {
                                                    receivingActivationBean.setActiveTime("--");
                                                } else {
                                                    receivingActivationBean.setActiveTime(jsonObject.getString("activeTime"));
                                                }
                                            } else {
                                                receivingActivationBean.setActiveTime("--");
                                            }
                                            receivingActivationBeanList.add(receivingActivationBean);
                                            num++;
                                        }
                                    }
                                    receivingActivationAdapter = new ReceivingActivationAdapter(DeliveryRecordsDetailActivity.this, receivingActivationBeanList);
                                    receivingActivationAdapter.setAllGroup(receivingActivationBeanList);
                                    lvReceivingDetail.setAdapter(receivingActivationAdapter);
                                    if (pageNo > 1){
                                        svDetail.fullScroll(ScrollView.FOCUS_DOWN);
                                    }
//                                }
                                showInitView();
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
                        srlReceiving.finishLoadmore();
                    }
                });
    }

    private void getOrderCancelHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetLogisticsCancel.DeliveryInfo deliveryInfo = new pos_GetLogisticsCancel.DeliveryInfo(receivingOrderBean.getOrderCode());
        pos_GetLogisticsCancel.DataBean dataBean = new pos_GetLogisticsCancel.DataBean(deliveryInfo);
        pos_GetLogisticsCancel pos_getLogisticsCancel = new pos_GetLogisticsCancel(account_name, account_password, "3", dataBean);
        String json = new Gson().toJson(pos_getLogisticsCancel);
        OkGo.<String>post(MyUrl.pos_GetLogisticsCancel)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            srlReceiving.finishRefresh();
                            JSONObject json = new JSONObject(response.body());
                            ToastUtils.showShort(json.getString("message"));
                            if (json.getString("code").equals("00000")) {
                                ReceivingRecordsActivity.ins.onRefresh();
                                finish();
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

    private void showInitView() {
        if (receivingOrderBean.getPayMethod().equals("")) {
            tvReceivingPaymentMethod.setText("--");
        } else if (receivingOrderBean.getPayMethod().equals("00")) {
            tvReceivingPaymentMethod.setText(getString(R.string.wallet));
        } else if (receivingOrderBean.getPayMethod().equals("01")) {
            tvReceivingPaymentMethod.setText(getString(R.string.offline_payment));
        } else if (receivingOrderBean.getPayMethod().equals("02")) {
            tvReceivingPaymentMethod.setText("Mtn");
        } else if (receivingOrderBean.getPayMethod().equals("03")) {
            tvReceivingPaymentMethod.setText("Airtel");
        } else if (receivingOrderBean.getPayMethod().equals("04")) {
            tvReceivingPaymentMethod.setText("Zamtel");
        }

        tvReceivingOne.setText(receivingOrderBean.getOrderCode());
        tvReceivingTwo.setText(receivingOrderBean.getGameName());
        if (receivingOrderBean.getPayMoney().equals("0")) {
            tvReceivingMoney.setText("--");
            tvReceivingMoneys.setText("--");
        } else {
            tvReceivingMoney.setText(MoneyUtil.getIns().GetMoney(receivingOrderBean.getPayMoney()) + getString(R.string.price_unit));
            tvReceivingMoneys.setText(MoneyUtil.getIns().GetMoney(receivingOrderBean.getPayMoney()) + getString(R.string.price_unit));
        }
        if (receivingOrderBean.getPayMoneyReal().equals("0")) {
            tvReceivingRealMoney.setText("--");
        } else {
            tvReceivingRealMoney.setText(MoneyUtil.getIns().GetMoney(receivingOrderBean.getPayMoneyReal()) + getString(R.string.price_unit));
        }
        if (activityType.equals("00")) {
            if (receivingOrderBean.getStatus().equals("00")) {
                tvReceivingSix.setText(getString(R.string.has_been_received));
                tvReceivingSix.setTextColor(Color.rgb(0,75,255));
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.VISIBLE);
                setListViewHeight(lvReceivingDetail);
                tvReceivingFive.setText(timeStamp2Date(Long.parseLong(receivingOrderBean.getGetTime())));
            } else if (receivingOrderBean.getStatus().equals("01")) {
                tvReceivingSix.setText(getString(R.string.chupiao_fail));
                tvReceivingSix.setTextColor(Color.rgb(253, 8, 8));
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
            } else if (receivingOrderBean.getStatus().equals("02")) {
//                tvReceivingSix.setText(getString(R.string.to_be_dispatched));
                tvReceivingSix.setText(getString(R.string.unclaimed));
                tvReceivingSix.setTextColor(Color.rgb(255, 102, 0));
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
//                generateData();
            } else if (receivingOrderBean.getStatus().equals("03")) {
                tvReceivingSix.setText(getString(R.string.order_invalidation));
                tvReceivingSix.setTextColor(Constant.invalidation);
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
            } else if (receivingOrderBean.getStatus().equals("04")) {
                tvReceivingSix.setText(getString(R.string.cancellation_of_order));
                tvReceivingSix.setTextColor(Constant.invalidation);
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
            } else if (receivingOrderBean.getStatus().equals("05")) {
                tvReceivingSix.setText(getString(R.string.in_delivery));
                tvReceivingSix.setTextColor(Color.rgb(146, 204, 170));
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
            }
        } else {
            llyReceivingActivation.setVisibility(View.VISIBLE);
            if (receivingOrderBean.getStatus().equals("00")) {
                tvReceivingSix.setText(getString(R.string.delivered));
                tvReceivingSix.setTextColor(Color.rgb(0,75,255));
                llyReceivingPop.setVisibility(View.GONE);
                setListViewHeight(lvReceivingDetail);
                tvReceivingFive.setText(timeStamp2Date(Long.parseLong(receivingOrderBean.getGetTime())));
            } else if (receivingOrderBean.getStatus().equals("02")) {
//                tvReceivingSix.setText(getString(R.string.to_be_dispatched));
                tvReceivingSix.setText(getString(R.string.unclaimed));
                tvReceivingSix.setTextColor(Color.rgb(255, 102, 0));
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
            } else if (receivingOrderBean.getStatus().equals("05")) {
                tvReceivingSix.setText(getString(R.string.in_delivery));
                tvReceivingSix.setTextColor(Color.rgb(146, 204, 170));
                llyReceivingPop.setVisibility(View.GONE);
                llyReceivingActivation.setVisibility(View.GONE);
                tvReceivingFive.setText("--");
            }
        }
        if (receivingOrderBean.getPayState().equals("00")) {
            tvReceivingThree.setText(getString(R.string.successful_payment));
            tvReceivingThree.setTextColor(Color.rgb(0,75,255));
        } else if (receivingOrderBean.getPayState().equals("01")) {
            tvReceivingThree.setText(getString(R.string.failure_to_pay));
            tvReceivingThree.setTextColor(Color.rgb(253, 8, 8));
            tvReceivingSix.setText("--");
            tvReceivingSix.setTextColor(Color.rgb(253, 8, 8));
        } else if (receivingOrderBean.getPayState().equals("02")) {
            tvReceivingThree.setText(getString(R.string.to_be_paid));
            tvReceivingThree.setTextColor(Color.rgb(255, 102, 0));
        } else if (receivingOrderBean.getPayState().equals("03")) {
            tvReceivingThree.setText(getString(R.string.to_be_confirmed));
            tvReceivingThree.setTextColor(Color.rgb(255, 102, 0));
        }
        tvReceivingFour.setText(receivingOrderBean.getTicketNum());
        tvReceivingSeven.setText(timeStamp2Date(Long.parseLong(receivingOrderBean.getCreateTime())));
//        if (receivingOrderBean.getPayState().equals("02") && receivingOrderBean.getStatus().equals("02") && !roleAlias.equals("fxs")) {
        if (receivingOrderBean.getPayState().equals("02") && receivingOrderBean.getStatus().equals("02")) {
            llyReceivingRecordOrder.setVisibility(View.VISIBLE);
            tvReceivingSix.setText("--");
        } else {
            llyReceivingRecordOrder.setVisibility(View.GONE);
        }
        if (receivingOrderBean.getPayState().equals("02") && !receivingOrderBean.getStatus().equals("03") && !receivingOrderBean.getStatus().equals("04")) {
            llyReceivingRecordCancel.setVisibility(View.VISIBLE);
        } else {
            llyReceivingRecordCancel.setVisibility(View.GONE);
        }
        if (receivingOrderBean.getStatus().equals("04") || receivingOrderBean.getStatus().equals("03")) {
            tvReceivingThree.setText("--");
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
