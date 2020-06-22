package com.jc.lottery.activity.immediate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.InventoryAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.RecordInfoBean;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.req.pos_GetCloseOrder;
import com.jc.lottery.bean.req.pos_GetRecordInfo;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ScaleUtils;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.ViewAnimationUtil;
import com.jc.lottery.view.FlowLayout;
import com.jc.lottery.view.LinearItemDecoration;
import com.jc.lottery.view.SlideRecyclerView;
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
 * 领取页面
 */
public class ReceivingRecordsActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.lly_receiving_nodata)
    LinearLayout llyReceivingNodata;
    @BindView(R.id.mRecycler)
    SlideRecyclerView mRecycler;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.footer_receiving)
    ClassicsFooter footerReceiving;
    @BindView(R.id.lly_reward_record_select)
    LinearLayout llyRewardRecordSelect;
    @BindView(R.id.btn_receiving_select_reset)
    Button btnReceivingSelectReset;
    @BindView(R.id.btn_receiving_select_submit)
    Button btnReceivingSelectSubmit;
    @BindView(R.id.lly_receiving_select)
    LinearLayout llyReceivingSelect;
    @BindView(R.id.lly_receiving_select_content)
    LinearLayout llyReceivingSelectContent;
    @BindView(R.id.lly_receiving_select_container)
    LinearLayout llyReceivingSelectContainer;
    @BindView(R.id.lly_receiving_select_container_two)
    LinearLayout llyReceivingSelectContainerTwo;
    @BindView(R.id.flow)
    FlowLayout flow;
    @BindView(R.id.flow_one)
    FlowLayout flowOne;
    @BindView(R.id.lly_receiving_click)
    LinearLayout llyReceivingClick;
    @BindView(R.id.tv_receiving_select_order)
    EditText tvReceivingSelectOrder;
    @BindView(R.id.img_receiving_select_order)
    ImageView imgReceivingSelectOrder;
    @BindView(R.id.sp_receiving_select_type)
    Spinner spReceivingSelectType;
    @BindView(R.id.lly_receiving_select_type)
    LinearLayout llyReceivingSelectType;
    @BindView(R.id.tv_receiving_select_start)
    TextView tvReceivingSelectStart;
    @BindView(R.id.tv_receiving_select_end)
    TextView tvReceivingSelectEnd;
    @BindView(R.id.tv_receiving_num)
    TextView tvReceivingNum;
    @BindView(R.id.bt_receiving_pop_no)
    Button btReceivingPopNo;
    @BindView(R.id.bt_receiving_pop_yes)
    Button btReceivingPopYes;
    @BindView(R.id.lly_receiving_pop)
    LinearLayout llyReceivingPop;
    @BindView(R.id.tv_receiving_tip)
    TextView tvReceivingTip;
    //    private RecordAdapter rewardRecordAdapter;
    private InventoryAdapter rewardRecordAdapter;
    private List<RecordInfoBean> dataList = new ArrayList<RecordInfoBean>();
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private List<String> nameList = new ArrayList<String>();
    private List<String> typeList = new ArrayList<String>();
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private String gameAlias = "";
    private String status = "";
    private String gameAliasSelect = "";
    private String statusSelect = "";
    private AlphaAnimation mAnimation;
    private int selectType = 0;
    public static ReceivingRecordsActivity ins;
    private int mListFocus = 0;
    private int firstVisiblePositionTop = 0;
    private boolean codeType = true;
    private CustomDatePicker customDatePicker;
    private String timeType = "1"; // 1：开始 2：结束
    private String start = "";
    private String end = "";
    private String orderId = "";

    private int lastOffset = 0;
    private int lastPosition = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receiving_record;
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
//        xlvRewardRecord.setOnItemClickListener(this);
        LinearItemDecoration divider = new LinearItemDecoration.Builder(this)
                .setSpan(2f)
//                              .setPadding(R.dimen.line_width)
//                              .setLeftPadding(R.dimen.common_title_height)
//                              .setRightPadding(R.dimen.common_title_height)
                .setColorResource(R.color.bg_space)
                .setShowLastLine(true)
                .build();
        mRecycler.addItemDecoration(divider);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        initDatePicker();
        getHttpInfo("", "", "");
    }

    @Override
    public void initListener() {

        srlReceiving.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                codeType = false;
                refreshType = 1;
                pageNo = 1;
                gameAlias = "";
                status = "";
                getHttpInfo(gameAlias, status, "");
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
                    getHttpInfo(gameAlias, status, "");
                } else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
//        xlvRewardRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("activityType", "00");
//                intent.putExtra("recordDetailsId", dataList.get(position - 1).getId() + "");
//                intent.setClass(ReceivingRecordsActivity.this, ReceivingRecordsDetailActivity.class);
//                startActivity(intent);
//            }
//        });

//        xlvRewardRecord.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    mListFocus = xlvRewardRecord.getFirstVisiblePosition();
//                    View item = xlvRewardRecord.getChildAt(0);
//                    firstVisiblePositionTop = (item == null) ? 0 : item.getTop();
//                }
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
    }

    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
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
        if (mRecycler.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mRecycler.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
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

    private void getHttpInfo(String gameAlias, String status, String orderCode) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
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
        pos_GetRecordInfo.DataBean.GetInfo getInfo = new pos_GetRecordInfo.DataBean.GetInfo(gameAlias, pageNo + "", "01", orderCode, status, "", starts, ends, TimeUtils.get10IntTimeStamp() + "");
        pos_GetRecordInfo pos_getRecordInfo = new pos_GetRecordInfo(account_name, account_password, "3", getInfo);
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetRecordInfo)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray = jsonObject.getJSONArray("getList");
                            if (jsonArray.length() < 1 && selectType == 1) {
                                selectType = 0;
//                                ToastUtils.showShort("订单号不正确 请重新输入");
                                ProgressUtil.dismissProgressDialog();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectType = 0;
//                        onLoad();
                        if (refreshType == 1) {
                            if (dataList != null) {
                                dataList.clear();
                                pageNo = 1;
                            }
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                tvReceivingNum.setText(MoneyUtil.getIns().GetMoney(jsonObject.getString("totalMoney")) + getString(R.string.price_unit));
                                pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                                pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                int num = 1;
                                if (pageNo == 1) {
                                    num = 1;
                                } else {
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
                                    recordInfoBean.setPayMoney(getList.getString("payMoney"));
                                    recordInfoBean.setShutState(getList.getString("shutState"));
                                    //                                recordInfoBean.setSendDevice(getList.getString("sendDevice"));
                                    //                                recordInfoBean.setSendPerson(getList.getString("sendPerson"));
                                    recordInfoBean.setStatus(getList.getString("status"));
                                    recordInfoBean.setTicketNum(getList.getString("ticketNum"));
                                    recordInfoBean.setId(getList.getInt("id"));
                                    recordInfoBean.setCreateTime(timeStamp2Date(Long.parseLong(getList.getString("createTime"))));
                                    recordInfoBean.setIndex(num);
                                    dataList.add(recordInfoBean);
                                    num++;
                                }
                                rewardRecordAdapter = new InventoryAdapter(ReceivingRecordsActivity.this, dataList, ReceivingRecordsActivity.this);
//                                rewardRecordAdapter.setAllGroup(dataList);
                                mRecycler.setAdapter(rewardRecordAdapter);
                                //                            lvRewardRecord.setAdapter(rewardRecordAdapter);
                                if (dataList.size() < 1) {
                                    mRecycler.setVisibility(View.GONE);
                                    llyReceivingNodata.setVisibility(View.VISIBLE);
                                } else {
                                    mRecycler.setVisibility(View.VISIBLE);
                                    llyReceivingNodata.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
//                        int w_cur_pos = xlvRewardRecord.getFirstVisiblePosition();
//                        int w_top = xlvRewardRecord.getTop();

                        if (refreshType == 2) {
                            scrollToPosition();
//                            xlvRewardRecord.setSelection((pageNo - 1) * pageNum);
//                            xlvRewardRecord.setSelectionFromTop(mListFocus, firstVisiblePositionTop);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishRefresh();
                        srlReceiving.finishLoadmore();
                        if (codeType) {
                            finish();
                        }
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
        codeType = false;
        refreshType = 1;
        pageNo = 1;
        gameAlias = "";
        status = "";

        getHttpInfo(gameAlias, status, "");
    }

    @Override
    public void onLoadMore() {
        codeType = false;
        refreshType = 2;
        if (pageNo != pageCount) {
            pageNo++;
            getHttpInfo(gameAlias, status, "");
        } else {
//            onLoad();
            ToastUtils.showShort(getString(R.string.no_more));
        }
    }

    /**
     * 加载完成
     */
//    private void onLoad() {
//        try {
//            xlvRewardRecord.stopRefresh();
//            xlvRewardRecord.stopLoadMore();
//            xlvRewardRecord.setRefreshTime(getString(R.string.just_now));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void showTypeInfo() {
        typeList.add(getString(R.string.all));
        typeList.add(getString(R.string.has_been_received));
        typeList.add(getString(R.string.unclaimed));
//        typeList.add(getString(R.string.chupiao_fail));
        typeList.add(getString(R.string.order_invalidation));
        typeList.add(getString(R.string.cancellation_of_order));
//        typeList.add(getString(R.string.order_invalidation));
        showTypeFlowLayout(typeList);
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
//                                changeSpinner(spReceivingSelectName, nameList);
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
                    }
                });
    }

    @OnClick({R.id.btn_receiving_select_submit, R.id.lly_receiving_select, R.id.lly_reward_record_back, R.id.lly_reward_record_select, R.id.lly_receiving_select_content, R.id.lly_receiving_click, R.id.img_receiving_select_order, R.id.tv_receiving_select_start, R.id.tv_receiving_select_end, R.id.btn_receiving_select_reset, R.id.bt_receiving_pop_no, R.id.bt_receiving_pop_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.btn_receiving_select_reset:
                tvReceivingSelectStart.setText("");
                tvReceivingSelectEnd.setText("");
                break;
            case R.id.btn_receiving_select_submit:
                refreshType = 1;
                pageNo = 1;
                gameAlias = gameAliasSelect;
                status = statusSelect;
                getHttpInfo(gameAliasSelect, statusSelect, "");
//                llyReceivingSelect.setVisibility(View.GONE);
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
                break;
            case R.id.lly_reward_record_select:
                if (nameList.size() < 1) {
                    getGameHttpInfo();
                }
                if (typeList.size() < 1) {
                    showTypeInfo();
                }
                llyReceivingSelect.setVisibility(View.VISIBLE);
                new ViewAnimationUtil().setShowAnimation(llyReceivingSelect, 300, mAnimation);
//                addTextView(5);
                break;
            case R.id.lly_receiving_select:
                new ViewAnimationUtil().setHideAnimation(llyReceivingSelect, 300, mAnimation);
//                    llyReceivingSelect.setVisibility(View.GONE);
                break;
            case R.id.lly_receiving_select_content:
                break;
            case R.id.lly_receiving_click:

                break;
            case R.id.img_receiving_select_order:
                if (tvReceivingSelectOrder.getText().toString().trim().length() != 24) {
//                    ToastUtils.showShort("请正确输入订单号");
                } else {
                    selectType = 1;
                    refreshType = 1;
                    pageNo = 1;
                    gameAlias = "";
                    status = "";
                    getHttpInfo(gameAlias, status, tvReceivingSelectOrder.getText().toString().trim());
                }
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
            case R.id.bt_receiving_pop_no:
                llyReceivingPop.setVisibility(View.GONE);
                break;
            case R.id.bt_receiving_pop_yes:
                if (!orderId.equals("")) {
                    getOrderCancelHttp(orderId);
                }
                break;
        }
    }

    public void showViewPop(String id,String status) {
        String role = SPUtils.look(this, SPkey.roleAlias);
        if (role.equals("fxs")){
            if (status.equals("00")){
                tvReceivingTip.setText(getString(R.string.close_order_notss));
            }else {
                tvReceivingTip.setText(getString(R.string.close_order_nots));
            }
        }else {
            tvReceivingTip.setText(getString(R.string.close_order_nots));
        }
        orderId = id;
        llyReceivingPop.setVisibility(View.VISIBLE);
    }

    private void getOrderCancelHttp(String orderId) {
        ProgressUtil.showProgressDialog(this, this.getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetCloseOrder.OrderInfo orderInfo = new pos_GetCloseOrder.OrderInfo(orderId);
        pos_GetCloseOrder.DataBean dataBean = new pos_GetCloseOrder.DataBean(orderInfo);
        pos_GetCloseOrder pos_getLogisticsCancel = new pos_GetCloseOrder(account_name, account_password, "3", dataBean);
        String json = new Gson().toJson(pos_getLogisticsCancel);
        OkGo.<String>post(MyUrl.pos_GetCloseOrder)
                .upJson(json)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject json = new JSONObject(response.body());
                            ToastUtils.showShort(json.getString("message"));
                            if (json.getString("code").equals("00000")) {
                                llyReceivingPop.setVisibility(View.GONE);
                                onRefresh();
//                                ReceivingRecordsActivity.ins.onRefresh();
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
                        statusSelect = "";
                    } else if (i == 1) {
                        statusSelect = "00";
                    } else if (i == 2) {
                        statusSelect = "02";
                    } else if (i == 3) {
                        statusSelect = "03";
                    } else {
                        statusSelect = "04";
                    }
                } else {
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.reward_et_bg);
                }
            }
        }
    }

}
