package com.jc.lottery.activity.immediate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.DeliveryRecordAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.RecordInfoBean;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.req.pos_GetManagement;
import com.jc.lottery.bean.req.pos_GetRecordInfo;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ScaleUtils;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.ViewAnimationUtil;
import com.jc.lottery.view.FlowLayout;
import com.jc.lottery.view.XListView;
import com.jc.lottery.view.widget.CustomDatePicker;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 派送页面
 */
public class DeliveryRecordsActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.lly_receiving_nodata)
    LinearLayout llyReceivingNodata;
    @BindView(R.id.xlv_delivery_record)
    XListView xlvRewardRecord;
    @BindView(R.id.tv_receiving_select_retrieval)
    EditText tvReceivingSelectOrder;
    @BindView(R.id.img_receiving_select_retrieval)
    ImageView imgReceivingSelectOrder;
    @BindView(R.id.sp_delivery_one)
    Spinner spDeliveryOne;
    @BindView(R.id.sp_delivery_two)
    Spinner spDeliveryTwo;
    @BindView(R.id.img_delivery_pop_no)
    ImageView imgDeliveryPopNo;
    @BindView(R.id.tv_delivery_pop_one)
    TextView tvDeliveryPopOne;
    @BindView(R.id.tv_delivery_pop_two)
    TextView tvDeliveryPopTwo;
    @BindView(R.id.tv_delivery_pop_three)
    TextView tvDeliveryPopThree;
    @BindView(R.id.tv_delivery_pop_four)
    TextView tvDeliveryPopFour;
    @BindView(R.id.tv_delivery_pop_five)
    TextView tvDeliveryPopFive;
//    @BindView(R.id.tv_delivery_pop_six)
//    TextView tvDeliveryPopSix;
    @BindView(R.id.tv_delivery_pop_seven)
    TextView tvDeliveryPopSeven;
    @BindView(R.id.lly_delivery_pop)
    LinearLayout llyDeliveryPop;
    @BindView(R.id.btn_receiving_select_submit)
    Button btnReceivingSelectSubmit;
    @BindView(R.id.lly_receiving_select)
    LinearLayout llyReceivingSelect;
    @BindView(R.id.tv_receiving_select_start)
    TextView tvReceivingSelectStart;
    @BindView(R.id.tv_receiving_select_end)
    TextView tvReceivingSelectEnd;
    @BindView(R.id.lly_reward_record_select)
    LinearLayout llyRewardRecordSelect;
    @BindView(R.id.flow)
    FlowLayout flow;
    @BindView(R.id.flow_one)
    FlowLayout flowOne;

    private DeliveryRecordAdapter rewardRecordAdapter;
    private List<RecordInfoBean> dataList = new ArrayList<RecordInfoBean>();
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private String recipient = ""; //领取人
    private List<String> nameList = new ArrayList<String>();
    private List<String> typeList = new ArrayList<String>();
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private String gameAlias = "";
    private String status = "02";
    private String gameAliasSelect = "";
    private String statusSelect = "02";
    private AlphaAnimation mAnimation;
    private int selectType = 0;
    private ArrayAdapter adapter;
    private ArrayAdapter adapterTwo;
    public static DeliveryRecordsActivity instance = null;
    private int mListFocus = 0;
    private int firstVisiblePositionTop = 0;
    private boolean httpType = true;
    private boolean codeType = true;
    private CustomDatePicker customDatePicker;
    private String timeType = "1"; // 1：开始 2：结束
    private String start = "";
    private String end = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery_record;
    }

    @Override
    public void initView() {
        xlvRewardRecord.setPullLoadEnable(true);
        xlvRewardRecord.setXListViewListener(this);
        instance = this;
        initDatePicker();
    }

    @Override
    public void initData() {
        showTypeInfo();
        getGameHttpInfo();
        getHttpInfo(gameAlias, status, recipient);
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
                if (timeType.equals("1")){
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
    public void initListener() {
        xlvRewardRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("recordDetailsId", dataList.get(position - 1).getId() + "");
                intent.setClass(DeliveryRecordsActivity.this, DeliveryRecordsDetailActivity.class);
                startActivity(intent);
            }
        });
        xlvRewardRecord.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mListFocus = xlvRewardRecord.getFirstVisiblePosition();
                    View item = xlvRewardRecord.getChildAt(0);
                    firstVisiblePositionTop = (item == null) ? 0 : item.getTop();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        tvReceivingSelectOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    refreshType = 1;
                    pageNo = 1;
                    recipient = "";
                    getHttpInfo(gameAlias, status, recipient);
                }
            }
        });
    }

    private void getHttpInfo(String gameAlias, final String status, String recipient) {
        if (!httpType){
            onLoad();
            return;
        }
        String starts = tvReceivingSelectStart.getText().toString().trim();
        String ends = tvReceivingSelectEnd.getText().toString().trim();
        if (!starts.equals("")){
            if (!ends.equals("")){
                starts = showArTime(starts,1) + " 00:00:01";
                ends = showArTime(ends,1) + " 23:59:59";
                starts = parseServerTime(starts,"");
                ends = parseServerTime(ends,"");
            }else {
                starts = "";
                ends = "";
            }
        }else {
            starts = "";
            ends = "";
        }
        httpType = false;
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
//        String account_name = "lrgly";
//        String account_password = "123456";
        pos_GetRecordInfo.DataBean.GetInfo getInfo = new pos_GetRecordInfo.DataBean.GetInfo(gameAlias, pageNo + "", status, "", "", recipient,starts,ends, TimeUtils.get10IntTimeStamp() + "");
        pos_GetRecordInfo pos_getRecordInfo = new pos_GetRecordInfo(account_name, account_password, "3", getInfo);
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetRecordInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        httpType = true;
//                        ToastUtils.showShort("response:" + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray = jsonObject.getJSONArray("getList");
                            if (jsonArray.length() < 1 && selectType == 1) {
                                selectType = 0;
                                ToastUtils.showShort(getString(R.string.incorrect_receiver_please_input_again));
                                ProgressUtil.dismissProgressDialog();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectType = 0;
                        onLoad();
                        if (refreshType == 1) {
                            if (dataList != null) {
                                dataList.clear();
                                pageNo = 1;
                            }
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                                pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                int num = 1;
                                if (pageNo == 1) {
                                    num = 1;
                                }else {
                                    num = (pageNo - 1) * pageNum + 1;
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject getList = jsonArray.getJSONObject(i);
                                    RecordInfoBean recordInfoBean = new RecordInfoBean();
                                    recordInfoBean.setGameName(getList.getString("gameName"));
                                    recordInfoBean.setGetDevice(getList.getString("getDevice"));
                                    recordInfoBean.setGetTime(timeStamp2Date(Long.parseLong(getList.getString("getTime"))));
                                    recordInfoBean.setOrderCode(getList.getString("orderCode"));
                                    recordInfoBean.setPayState(getList.getString("payState"));
                                    recordInfoBean.setRecipient(getList.getString("recipient"));
                                    recordInfoBean.setGetChannels(getList.getString("getChannels"));
                                    recordInfoBean.setStatus(getList.getString("status"));
                                    recordInfoBean.setTicketNum(getList.getString("ticketNum"));
                                    recordInfoBean.setId(getList.getInt("id"));
                                    recordInfoBean.setIndex(num);
                                    recordInfoBean.setCreateTime(timeStamp2Date(Long.parseLong(getList.getString("createTime"))));
                                    dataList.add(recordInfoBean);
                                    num++;
                                }
                                rewardRecordAdapter = new DeliveryRecordAdapter(DeliveryRecordsActivity.this, status);
                                rewardRecordAdapter.setAllGroup(dataList);
                                xlvRewardRecord.setAdapter(rewardRecordAdapter);
                                //                            lvRewardRecord.setAdapter(rewardRecordAdapter);
                                if (dataList.size() < 1) {
//                                    xlvRewardRecord.setVisibility(View.GONE);
                                    llyReceivingNodata.setVisibility(View.VISIBLE);
                                } else {
//                                    xlvRewardRecord.setVisibility(View.VISIBLE);
                                    llyReceivingNodata.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                        if (refreshType == 2) {
                            xlvRewardRecord.setSelectionFromTop(mListFocus, firstVisiblePositionTop);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        onLoad();
                        httpType = true;
                    }
                });
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
        codeType = false;
        refreshType = 1;
        pageNo = 1;
        recipient = "";
        getHttpInfo(gameAlias, status, recipient);
    }

    @Override
    public void onLoadMore() {
        codeType = false;
        refreshType = 2;
        if (pageNo != pageCount) {
            pageNo++;
            getHttpInfo(gameAlias, status, recipient);
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
            xlvRewardRecord.stopRefresh();
            xlvRewardRecord.stopLoadMore();
            xlvRewardRecord.setRefreshTime(getString(R.string.just_now));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTypeInfo() {
//        typeList.add(getString(R.string.all));
//        typeList.add(getString(R.string.chupiao_fail));
        typeList.add(getString(R.string.to_be_dispatched));
        typeList.add(getString(R.string.in_delivery));
        typeList.add(getString(R.string.delivered));
//        typeList.add(getString(R.string.order_invalidation));
        showTypeFlowLayout(typeList);
    }

    @SuppressLint("ResourceType")
    public void changeSpinner() {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, nameList);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        spDeliveryTwo.setAdapter(adapter);
        adapterTwo = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, typeList);
        adapterTwo.setDropDownViewResource(R.layout.lottery_item_drop);
        spDeliveryOne.setAdapter(adapterTwo);
        spDeliveryOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshType = 1;
                if (position == 0) {
                    status = "02";
                    getHttpInfo(gameAlias, status, recipient);
                } else if (position == 1) {
                    status = "05";
                    getHttpInfo(gameAlias, status, recipient);
                } else if (position == 2) {
                    status = "00";
                    getHttpInfo(gameAlias, status, recipient);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDeliveryTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshType = 1;
                if (position == 0) {
                    gameAlias = "";
                    getHttpInfo(gameAlias, status, recipient);
                } else {
                    gameAlias = nameList.get(position);
                    getHttpInfo(gameAlias, status, recipient);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getGameHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GameQueryInfo pos_gameQueryInfo = new pos_GameQueryInfo(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetGameQueryInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
//                        ToastUtils.showShort("response:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                JSONArray gameList = jsonObject.getJSONArray("gameList");
                                GameListBean gameBean = new GameListBean();
                                gameBean.setGameName(getString(R.string.all));
                                gameBean.setGameAlias("");
                                gameBean.setTicketPrice("");
                                gameBean.setEnabled("00");
                                nameList.add(gameBean.getGameName());
                                gameListBeans.add(gameBean);
                                for (int i = 0; i < gameList.length(); i++) {
                                    JSONObject json = gameList.getJSONObject(i);
                                    GameListBean gameListBean = new GameListBean();
                                    gameListBean.setGameName(json.getString("gameName"));
                                    gameListBean.setGameAlias(json.getString("gameAlias"));
                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
                                    gameListBean.setEnabled(json.getString("enabled"));
                                    if (gameListBean.getEnabled().equals("00")) {
                                        nameList.add(gameListBean.getGameName());
                                        gameListBeans.add(gameListBean);
                                    }
                                }
//                                changeSpinner();
                                showGameFlowLayout(nameList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        finish();
                    }
                });
    }

    @OnClick({R.id.lly_reward_record_back, R.id.img_receiving_select_retrieval,R.id.img_delivery_pop_no,R.id.lly_delivery_pop,R.id.lly_reward_record_select,R.id.lly_receiving_select,R.id.tv_receiving_select_start,R.id.tv_receiving_select_end, R.id.btn_receiving_select_submit,R.id.btn_receiving_select_reset,R.id.lly_receiving_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.btn_receiving_select_reset:
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                break;
            case R.id.lly_receiving_click:

                break;
            case R.id.img_receiving_select_retrieval:
                if (tvReceivingSelectOrder.getText().toString().trim().length() == 0) {
                    ToastUtils.showShort(getString(R.string.receiver_cannot_be_empty_please_enter));
                } else {
                    recipient = tvReceivingSelectOrder.getText().toString().trim();
                    refreshType = 1;
                    pageNo = 1;
                    getHttpInfo(gameAlias, status, recipient);
                }
                break;
            case R.id.img_delivery_pop_no:
                llyDeliveryPop.setVisibility(View.GONE);
                break;
            case R.id.lly_delivery_pop:
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
                if (tvReceivingSelectStart.getText().toString().equals("")){
                    customDatePicker.show(start);
                }else {
                    customDatePicker.show(showArTime(tvReceivingSelectStart.getText().toString(),1) + " 00:00:00");
                }
                break;
            case R.id.tv_receiving_select_end:
                timeType = "2";
                if (tvReceivingSelectEnd.getText().toString().equals("")){
                    customDatePicker.show(end);
                }else {
                    customDatePicker.show(showArTime(tvReceivingSelectEnd.getText().toString(),1) + " 00:00:00");
                }
                break;
            case R.id.btn_receiving_select_submit:
                refreshType = 1;
                pageNo = 1;
//                recipient = "";
                getHttpInfo(gameAliasSelect, status, recipient);
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvReceivingSelectOrder.clearFocus();
//        getHttpInfo(gameAlias, status, "");
    }

    public void showData() {
        refreshType = 1;
        pageNo = 1;
        recipient = "";
        getHttpInfo(gameAlias, status, recipient);
    }

    public void showManagementInfo(String deliveryId,String deliveryOrder){
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetManagement.MangeData mangeData = new pos_GetManagement.MangeData(deliveryId,deliveryOrder);
        pos_GetManagement.DataBean dataBean = new pos_GetManagement.DataBean(mangeData);
        pos_GetManagement pos_getManagement = new pos_GetManagement(account_name, account_password, dataBean);
        String s1 = new Gson().toJson(pos_getManagement);
        OkGo.<String>post(MyUrl.pos_GetManagement)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
//                        ToastUtils.showShort("response:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                if (jsonObject.has("managementEnt")) {
                                    JSONObject managementEnt = jsonObject.getJSONObject("managementEnt");
                                    tvDeliveryPopOne.setText(getString(R.string.sender_phone) + ": " + managementEnt.getString("senderPhone"));
                                    tvDeliveryPopTwo.setText(getString(R.string.sender_name) + ": " + managementEnt.getString("senderName"));
                                    tvDeliveryPopThree.setText(getString(R.string.driver_name) + ": " + managementEnt.getString("driverName"));
                                    tvDeliveryPopFour.setText(getString(R.string.driver_telephone) + ": " + managementEnt.getString("driverPhone"));
                                    tvDeliveryPopFive.setText(getString(R.string.license_plate_number) + ": " + managementEnt.getString("carNumber"));
//                                    tvDeliveryPopSix.setText("物流ID" + managementEnt.getString("Id"));
                                    if (!managementEnt.getString("mailingDate").equals("")) {
                                        tvDeliveryPopSeven.setText(getString(R.string.sending_time) + ": " + timeStamp2Date(Long.parseLong(managementEnt.getString("mailingDate"))));
                                    }
                                    llyDeliveryPop.setVisibility(View.VISIBLE);
                                }else {
                                    ToastUtils.showShort(getString(R.string.no_logistics_information_present));
                                }
//                                tvDeliveryPopOne.setText();
                            }else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

    private void showTypeFlowLayout(List<String> tvList) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5));
//        if (flow != null) {
//            flow.removeAllViews();
//        }
        for (int i = 0; i < tvList.size(); i++) {
            TextView tv = new TextView(this);
            tv.setPadding(ScaleUtils.dip2px(this, 25), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 25), ScaleUtils.dip2px(this, 5));
            tv.setText(tvList.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            if (i == 0) {
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundResource(R.drawable.reward_et_red_bg);
                statusSelect = "";
            } else {
                tv.setBackgroundResource(R.drawable.reward_et_bg);
                tv.setTextColor(Color.GRAY);
            }
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(this);
//            if (i == 2) {
//                tv.setVisibility(View.GONE);
//            }
            flow.addView(tv, layoutParams);
        }
    }

    private void showGameFlowLayout(List<String> list) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 5));
        if (flowOne != null) {
            flowOne.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(this);
            tv.setPadding(ScaleUtils.dip2px(this, 25), ScaleUtils.dip2px(this, 5), ScaleUtils.dip2px(this, 25), ScaleUtils.dip2px(this, 5));
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            if (i == 0) {
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundResource(R.drawable.reward_et_red_bg);
                gameAliasSelect = gameListBeans.get(0).getGameAlias();
            } else {
                tv.setBackgroundResource(R.drawable.reward_et_bg);
                tv.setTextColor(Color.GRAY);
            }
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(this);
            flowOne.addView(tv, layoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == flowOne.getChildAt(0).getParent()) {
            for (int i = 0; i < flowOne.getChildCount(); i++) {
                TextView tv = (TextView) flowOne.getChildAt(i);
                if (v == tv) {
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.reward_et_red_bg);
                    gameAliasSelect = gameListBeans.get(i).getGameAlias();
                } else {
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.reward_et_bg);
                }
            }
        } else {
            for (int i = 0; i < flow.getChildCount(); i++) {
                TextView tv = (TextView) flow.getChildAt(i);
                if (v == tv) {
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.reward_et_red_bg);
                    if (i == 0) {
                        status = "02";
                    } else if (i == 1) {
                        status = "05";
                    } else if (i == 2) {
                        status = "00";
                    }
//                    if (position == 0) {
//                        status = "02";
//                        getHttpInfo(gameAlias, status, recipient);
//                    } else if (position == 1) {
//                        status = "05";
//                        getHttpInfo(gameAlias, status, recipient);
//                    } else if (position == 2) {
//                        status = "00";
//                        getHttpInfo(gameAlias, status, recipient);
//                    }
                } else {
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.reward_et_bg);
                }
            }
        }
    }
}
