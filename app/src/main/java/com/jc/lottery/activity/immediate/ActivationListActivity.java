package com.jc.lottery.activity.immediate;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.ActivationRecordAdapter;
import com.jc.lottery.adapter.ActivationTwoAdapter;
import com.jc.lottery.adapter.SettlementRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.ActivationRecordBean;
import com.jc.lottery.bean.DeliveryDetailsBean;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.bean.req.DeliveryDetailsQueryBean;
import com.jc.lottery.bean.req.pos_GetActivateRecord;
import com.jc.lottery.bean.req.pos_GetSettlementRecord;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.ViewAnimationUtil;
import com.jc.lottery.view.XListView;
import com.jc.lottery.view.widget.CustomDatePicker;
import com.lzy.okgo.OkGo;
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

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 激活记录页面
 */
public class ActivationListActivity extends BaseActivity implements XListView.IXListViewListener{

    @BindView(R.id.lly_settlement_nodata)
    PercentLinearLayout llySettlementNodata;
    @BindView(R.id.lly_reward_record_select)
    LinearLayout llyActivationSelect;
    @BindView(R.id.lly_activation_list)
    LinearLayout llyActivationList;
    @BindView(R.id.xlv_settlement)
    XListView xlvSettlement;

    @BindView(R.id.btn_receiving_select_submit)
    Button btnReceivingSelectSubmit;
    @BindView(R.id.lly_receiving_select)
    LinearLayout llyReceivingSelect;
    @BindView(R.id.tv_receiving_select_start)
    TextView tvReceivingSelectStart;
    @BindView(R.id.tv_receiving_select_end)
    TextView tvReceivingSelectEnd;
    private String settleStatus = "00";
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private List<ActivationRecordBean> settlementRecordBeanList = new ArrayList<ActivationRecordBean>();
    private ActivationRecordAdapter settlementRecordAdapter;
    private int mListFocus = 0;
    private int firstVisiblePositionTop = 0;
    private String startTime = "";
    private String endTime = "";
    private AlphaAnimation mAnimation;
    private CustomDatePicker customDatePicker;
    private String timeType = "1"; // 1：开始 2：结束
    private String start = "";
    private String end = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_activation_list;
    }

    @Override
    public void getPreIntent() {

    }

    @Override
    public void initView() {
        xlvSettlement.setPullLoadEnable(true);
        xlvSettlement.setXListViewListener(this);
        initDatePicker();
    }

    @Override
    public void initListener() {

        xlvSettlement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("deliveryId",settlementRecordBeanList.get(position - 1).getDeliveryId());
                intent.putExtra("order",settlementRecordBeanList.get(position - 1).getOrderCode());
                intent.putExtra("game",settlementRecordBeanList.get(position - 1).getGameName());
                intent.putExtra("state", settlementRecordBeanList.get(position - 1).getActiveState());
                intent.putExtra("person", settlementRecordBeanList.get(position - 1).getSendPerson());
                intent.putExtra("personl", settlementRecordBeanList.get(position - 1).getRecipient());
                intent.putExtra("time", settlementRecordBeanList.get(position - 1).getActiveTime());
                intent.setClass(ActivationListActivity.this, ActivationListDetailsActivity.class);
                startActivity(intent);
            }
        });
        xlvSettlement.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mListFocus = xlvSettlement.getFirstVisiblePosition();
                    View item = xlvSettlement.getChildAt(0);
                    firstVisiblePositionTop = (item == null) ? 0 : item.getTop();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //向前走一天
        Date date = calendar.getTime();
        start = formatter.format(date);
        end = now;
//        tvReceivingSelectStart.setText(formatter.format(date));
//        tvReceivingSelectEnd.setText(now);
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                if (timeType.equals("1")) {
                    tvReceivingSelectStart.setText(showArTime(time,0));
                } else {
                    tvReceivingSelectEnd.setText(showArTime(time,0));
                }
            }
        }, "1999-01-01 00:00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 显示时和分
        customDatePicker.setIsLoop(false); // 允许循环滚动
//        tvReceivingSelectStart.setText("");
//        tvReceivingSelectEnd.setText("");
    }

    private String showArTime(String time,int type){
        String newTime = "";
        if (type == 0) {
            if (time.length() == 19) {
                newTime = time.substring(0, 10);
                newTime = newTime.substring(0, 4) + getString(R.string.y) + newTime.substring(5, 7) + getString(R.string.m) + newTime.substring(8, 10) + getString(R.string.d);
            } else {
                newTime = time;
            }
        }else {
            newTime = time;
            newTime = newTime.replace(getString(R.string.y),"-");
            newTime = newTime.replace(getString(R.string.m),"-");
            newTime = newTime.replace(getString(R.string.d),"");
        }
        return newTime;
    }

    @Override
    public void initData() {
        getHttpInfo();
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String starts = tvReceivingSelectStart.getText().toString().trim();
        String ends = tvReceivingSelectEnd.getText().toString().trim();
        if (!starts.equals("")) {
            if (!ends.equals("")) {
                starts = showArTime(starts,1) + " 00:00:01";
                ends = showArTime(ends,1) + " 23:59:59";
                starts = parseServerTime(starts, "");
                ends = parseServerTime(ends, "");
            } else {
                starts = "";
                ends = "";
            }
        } else {
            starts = "";
            ends = "";
        }
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetActivateRecord.DataBean.RecordInfo recordInfo = new pos_GetActivateRecord.DataBean.RecordInfo(pageNo + "", "", "", starts, ends, TimeUtils.get10IntTimeStamp() + "");
        pos_GetActivateRecord pos_getSettlementRecord = new pos_GetActivateRecord(account_name, account_password, "3", recordInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.pos_GetActivateRecord)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        if (refreshType == 1) {
                            if (settlementRecordBeanList != null) {
                                settlementRecordBeanList.clear();
                                pageNo = 1;
                            }
                        }
                        onLoad();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                                pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                                int num = 1;
                                if (pageNo == 1) {
                                    num = 1;
                                }else {
                                    num = (pageNo - 1) * pageNum + 1;
                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("activateList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject settlementJson = jsonArray.getJSONObject(i);
                                    ActivationRecordBean activationRecordBean = new ActivationRecordBean();
                                    activationRecordBean.setGameName(" " + settlementJson.getString("gameName"));
                                    activationRecordBean.setOrderCode(settlementJson.getString("orderCode"));
                                    activationRecordBean.setRecipient(" " + settlementJson.getString("recipient"));
                                    activationRecordBean.setSendPerson(" " + settlementJson.getString("sendPerson"));
                                    activationRecordBean.setDeliveryId(settlementJson.getString("deliveryId"));
//                                    activationRecordBean.setSchemeNum(" " + settlementJson.getString("schemeNum"));
//                                    activationRecordBean.setCartonNo(" " + settlementJson.getString("cartonNo"));
//                                    activationRecordBean.setBookNum(" " + settlementJson.getString("bookNum"));
                                    activationRecordBean.setActiveState(settlementJson.getString("activeState"));
                                    if (!settlementJson.has("activeTime")||settlementJson.getString("activeTime").equals("")){
                                        activationRecordBean.setActiveTime(" " + "--");
                                    }else {
                                        activationRecordBean.setActiveTime(" " + timeStamp2Date(settlementJson.getLong("activeTime")));
                                    }

                                    activationRecordBean.setIndex(num);
                                    settlementRecordBeanList.add(activationRecordBean);
                                    num++;
                                }
                                settlementRecordAdapter = new ActivationRecordAdapter(ActivationListActivity.this);
                                settlementRecordAdapter.setAllGroup(settlementRecordBeanList);
                                xlvSettlement.setAdapter(settlementRecordAdapter);

                                if (settlementRecordBeanList.size() < 1) {
                                    llyActivationList.setVisibility(View.GONE);
                                    llySettlementNodata.setVisibility(View.VISIBLE);
                                } else {
                                    llyActivationList.setVisibility(View.VISIBLE);
                                    llySettlementNodata.setVisibility(View.GONE);
                                }
                            }else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                        if (refreshType == 2) {
//                            xlvSettlement.setSelection(pageNo * pageNum);
                            xlvSettlement.setSelectionFromTop(mListFocus, firstVisiblePositionTop);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        onLoad();
                    }
                });
    }

    public static String parseServerTime(String date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
//            format = "yyyy-MM-dd";
        }
        if (date.equals("")) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
//            return String.valueOf(sdf.parse(date).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back,R.id.btn_receiving_select_reset,R.id.lly_reward_record_select, R.id.lly_receiving_select, R.id.tv_receiving_select_start, R.id.tv_receiving_select_end, R.id.btn_receiving_select_submit,R.id.lly_receiving_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.btn_receiving_select_reset:
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                break;
            case R.id.lly_receiving_click:

                break;
            case R.id.lly_reward_record_select:
                llyReceivingSelect.setVisibility(View.VISIBLE);
                new ViewAnimationUtil().setShowAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.lly_receiving_select:
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
//                    llyReceivingSelect.setVisibility(View.GONE);
                break;
            case R.id.tv_receiving_select_start:
                timeType = "1";
                if (tvReceivingSelectStart.getText().toString().equals("")) {
                    customDatePicker.show(start);
                } else {
                    customDatePicker.show(showArTime(tvReceivingSelectStart.getText().toString(),1) + " 00:00:00");
                }
                break;
            case R.id.tv_receiving_select_end:
                timeType = "2";
                if (tvReceivingSelectEnd.getText().toString().equals("")) {
                    customDatePicker.show(end);
                } else {
                    customDatePicker.show(showArTime(tvReceivingSelectEnd.getText().toString(),1) + " 00:00:00");
                }
                break;
            case R.id.btn_receiving_select_submit:
                refreshType = 1;
                pageNo = 1;
//                getRecordHttp();
                getHttpInfo();
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
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

    @Override
    public void onRefresh() {
        tvReceivingSelectStart.setText("");
        tvReceivingSelectEnd.setText("");
        startTime = "";
        endTime = "";
        refreshType = 1;
        pageNo = 1;
        getHttpInfo();
    }

    @Override
    public void onLoadMore() {
        refreshType = 2;
        if (pageNo != pageCount) {
            pageNo++;
            getHttpInfo();
        } else {
            onLoad();
            ToastUtils.showShort(getString(R.string.no_more));
        }
    }

    /**
     * 加载完成
     */
    private void onLoad() {
        try {
            xlvSettlement.stopRefresh();
            xlvSettlement.stopLoadMore();
            xlvSettlement.setRefreshTime(getString(R.string.just_now));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
