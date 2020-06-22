package com.jc.lottery.activity.immediate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.RewardScannerActivity;
import com.jc.lottery.adapter.SettlementNewsAdapter;
import com.jc.lottery.adapter.SettlementSelectAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.SettleBookBean;
import com.jc.lottery.bean.req.pos_BookQueryInfo;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.req.pos_settlementQuery;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.content.HttpContext;
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
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 即开彩结算页面
 */
public class ImmediatelSettlementActivity extends BaseActivity {

    @BindView(R.id.sp_sp_settlement_type)
    Spinner spSpSettlementType;
    @BindView(R.id.sp_sp_settlement_types)
    Spinner spSpSettlementTypes;
    //    @BindView(R.id.pull_refresh_scrollview)
//    ScrollView pullRefreshScrollview;
    @BindView(R.id.btn_settlement_reset)
    Button btnSettlementReset;
    @BindView(R.id.btn_settlement_submit)
    Button btnSettlementSubmit;
    @BindView(R.id.lv_settlement)
    RecyclerView relSettlement;
    @BindView(R.id.lv_settlements)
    RecyclerView relSettlements;
    @BindView(R.id.rel_settlement_pop)
    RecyclerView relSettlementPop;
    @BindView(R.id.lly_settlement_select)
    LinearLayout llySettlementSelect;
    @BindView(R.id.lly_settlement_all)
    LinearLayout llySettlementAll;
    @BindView(R.id.btn_settlement_select)
    Button btnSettlementSelect;
    @BindView(R.id.bt_settlement_no)
    Button btnSettlementNo;
    @BindView(R.id.bt_settlement_yes)
    Button btnSettlementYes;
    //    @BindView(R.id.scb_settlement_all)
//    SmoothCheckBox scbSettlementAll;
//    @BindView(R.id.lly_settlement_all)
//    LinearLayout llySettlementAll;
    @BindView(R.id.lly_settlement_back)
    LinearLayout llySettlementBack;
    @BindView(R.id.lly_settlement_pop)
    LinearLayout llySettlementPop;
    @BindView(R.id.lly_settlement_one)
    LinearLayout llySettlementOne;
    @BindView(R.id.lly_settlement_all_pop)
    LinearLayout llySettlementAllPop;
    //    @BindView(R.id.tv_receiving_item_num)
//    TextView tvSettlementNum;
    @BindView(R.id.tv_settlement_open)
    TextView tvSettlementOpen;
    //    @BindView(R.id.lly_settlement_select_top)
//    LinearLayout llySettlementSelectTop;
    @BindView(R.id.lly_settlement_nodata)
    LinearLayout llySettlementNodata;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.et_settlement_select)
    EditText etSelect;
    @BindView(R.id.img_manual_scanner_qr)
    ImageView imgQr;
    @BindView(R.id.tv_settlement_all_pop_del)
    TextView tvPopDel;
    @BindView(R.id.img_settlement_select)
    ImageView imgSettlementSelect;
    @BindView(R.id.lly_settlement_type)
    PercentLinearLayout llySettlementType;
    @BindView(R.id.view_settlement_type)
    View viewSettlementType;
    private int startYear = 0;
    private int startMonth = 0;
    private int startDay = 0;
    private ArrayAdapter gameAdapter;
    private ArrayAdapter typeAdapter;
    private ArrayAdapter bookAdapter;
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private List<String> gameStringList = new ArrayList<String>();
    private List<String> typeStringList = new ArrayList<String>();
    private List<String> bookList = new ArrayList<String>();
    private String account_name = "";
    private String account_password = "";
    private List<SettleBookBean> settlementInfoBeanList = new ArrayList<SettleBookBean>();
    private List<SettleBookBean> settlementQueryBeanList = new ArrayList<SettleBookBean>();
    private List<SettleBookBean> settlementSelectList = new ArrayList<SettleBookBean>();
    private int totalMoney = 0;
    //    private SettlementAdapter settlementAdapter;
    private int selectEndType = 0; // 0 未查询信息 1 已查询
    private SettlementNewsAdapter settlementAdapter;
    private SettlementSelectAdapter settlementSelectAdapter;
    //    private SettlementNewestAdapter popSettlementAdapter;
    private SettlementQueryBean settlementQueryBean;
    private SettlementQueryBean previewSettlementQueryBean;
    public static ImmediatelSettlementActivity instance = null;
    private int num = 0;
    private int pageNo = 1;
    private int pageCount = 1;
    private int selectType = 1; //1：全部 2：检索
    private int pageNum = 1;
    private String totalNum = "1";
    private boolean pageType = false;
    private String qrType = "1"; //1 : 输入 2 ：扫码
    private String settleType = "01";
    private boolean allType = true;
    private int lastOffset = 0;
    private int lastPosition = 0;
    private boolean codeType = true;
    private String role = ""; //角色
    private String selectInType = "0";
    private int typePos = 0;
    private int typePosTop = 0;
    private int typesPos = 0;
    private int typesPosTop = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement;
    }

    @Override
    public void getPreIntent() {
    }

    @Override
    public void initData() {
//        getRecordHttp();
        showTypeList();
        showInfo();
        getGameInfoHttp();
    }

    private void showTypeList() {
        typeStringList.add(getString(R.string.agent_not_send));
        typeStringList.add(getString(R.string.distributor_settled));
        changeSpinner(spSpSettlementTypes, typeAdapter, typeStringList);
    }

    @Override
    public void initListener() {
        spSpSettlementType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                llySettlementSelect.setVisibility(View.GONE);
//                llySettlementSelectTop.setVisibility(View.GONE);
                typePosTop = typePos;
                typePos = position;
                selectEndType = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spSpSettlementTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                llySettlementSelect.setVisibility(View.GONE);
//                llySettlementSelectTop.setVisibility(View.GONE);
                typesPosTop = typesPos;
                typesPos = position;
                selectEndType = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        srlReceiving.setEnableRefresh(false);//是否启用下拉刷新功能
//        srlReceiving.setEnableAutoLoadmore(true);//是否启用上拉加载功能
//        srlReceiving.setEnableNestedScroll(true);//是否启用嵌套滚动

        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pageNo < pageCount) {
                    pageNo++;
                    getBookInfoHttp("");
                } else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });
//        lvSettlement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                boolean selectType = !settlementInfoBeanList.get(position).isType();
//                if (selectType){
//                    showNum("1");
//                }else {
//                    showNum("2");
//                }
//                settlementInfoBeanList.get(position).setType(selectType);
//                settlementAdapter.notifyDataSetChanged();
//            }
//        });
        etSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                qrType = "1";
                if (s.toString().length() == 0) {
                    if (selectType == 2) {
                        imgSettlementSelect.setVisibility(View.VISIBLE);
                        pageType = false;
                        settlementAdapter.setList(settlementInfoBeanList);
                        settlementAdapter.notifyDataSetChanged();
                        selectType = 1;
//                        srlReceiving.resetNoMoreData();
                        srlReceiving.setEnableLoadmore(true);
//                        pageCount = 2;
//                        pageNo = pageSaveNo;
//                        pageCount = pageSaveCount;
                    }
                }
            }
        });

        relSettlement.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        llySettlementAllPop.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

        });

        etSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        relSettlements.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
    }

    public void showInType() {
        if (typePos != typePosTop || typesPos != typesPosTop) {
            num = 0;
            pageNo = 1;
            pageCount = 1;
            selectType = 1;
            settlementInfoBeanList.clear();
            settlementQueryBeanList.clear();
            settlementSelectList.clear();
//            settlementAdapter.notifyDataSetChanged();
//            settlementSelectAdapter.notifyDataSetChanged();
        }
    }

    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) relSettlements.getLayoutManager();
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
        if (relSettlements.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) relSettlements.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void initView() {
        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.pull_up_load_more);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.release_load_now);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.xlistview_header_hint_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.load_complete);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.failed_to_load);
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.no_more);
        instance = this;
        relSettlement.setLayoutManager(new LinearLayoutManager(this));
        relSettlements.setLayoutManager(new LinearLayoutManager(this));
        relSettlementPop.setLayoutManager(new LinearLayoutManager(this));
        role = SPUtils.look(this, SPkey.roleAlias);
        if (role.equals("fxs")) {
            llySettlementType.setVisibility(View.GONE);
            viewSettlementType.setVisibility(View.GONE);
        } else {
            llySettlementType.setVisibility(View.VISIBLE);
            viewSettlementType.setVisibility(View.VISIBLE);
        }
//        relSettlement.setNestedScrollingEnabled(false);
    }

    //查询游戏本号
    public void getBookInfoHttp(String bookNum) {
        String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
        role = SPUtils.look(this, SPkey.roleAlias);
        if (spSpSettlementTypes.getSelectedItemPosition() == 0) {
            settleType = "01";
        } else {
            settleType = "02";
        }
        if (role.equals("fxs")) {
            settleType = "03";
        }
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        account_name = SPUtils.look(this, SPkey.username);
        account_password = SPUtils.look(this, SPkey.password);
        pos_BookQueryInfo pos_bookQueryInfo = new pos_BookQueryInfo(account_name, account_password, "3", new pos_BookQueryInfo.DataBean(new pos_BookQueryInfo.SettlementInfo(gameAlias, pageNo + "", bookNum, settleType)));
        String s1 = new Gson().toJson(pos_bookQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetSettlementBookQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishLoadmore();
                        if (pageNo == 1 && selectType != 2) {
                            settlementInfoBeanList.clear();
                        }
//                        ToastUtils.showShort("response:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (selectType == 1) {
                                totalNum = jsonObject.getString("totalNum");
                                pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            } else {
                                srlReceiving.setEnableLoadmore(false);
                            }
                            if (jsonObject.has("pageNum")) {
                                pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                            }
                            srlReceiving.resetNoMoreData();
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("bookList");
                                Type type = new TypeToken<List<SettleBookBean>>() {
                                }.getType();
                                ArrayList<SettleBookBean> list = new Gson().fromJson(String.valueOf(jsonArray), type);
                                if (selectType == 2) {
                                    imgSettlementSelect.setVisibility(View.INVISIBLE);
                                    settlementQueryBeanList.clear();
                                    settlementQueryBeanList.addAll(list);
                                    if (settlementInfoBeanList.size() > 0) {
                                        if (list.size() > 0) {
                                            if (qrType.equals("2") && settlementQueryBeanList.size() < 2) {
                                                for (int i = 0; i < settlementQueryBeanList.size(); i++) {
                                                    settlementQueryBeanList.get(i).setType(true);
                                                }
                                                settlementSelectList.addAll(settlementQueryBeanList);
                                                if (settlementSelectAdapter != null) {
                                                    settlementSelectAdapter.notifyDataSetChanged();
                                                } else {
                                                    settlementSelectAdapter = new SettlementSelectAdapter(ImmediatelSettlementActivity.this);
                                                    settlementSelectAdapter.setList(settlementSelectList);
                                                    relSettlementPop.setAdapter(settlementSelectAdapter);
//                                                    llySettlementPop.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            if (querySelect(list.get(0))) {
                                                settlementAdapter.setList(settlementQueryBeanList);
                                                settlementAdapter.notifyDataSetChanged();
                                            } else {
                                                ToastUtils.showShort(jsonObject.getString("message"));
                                            }
                                        } else {
                                            ToastUtils.showShort(getString(R.string.does_not_exist));
                                            etSelect.setText("");
                                        }
                                    } else {
                                        settlementInfoBeanList.addAll(list);
//                                        tvSettlementNum.setText(num + "/" + "1");
                                        btnSettlementSelect.setText(getString(R.string.settlement) + "(" + num + "/" + "1" + ")");
                                        settlementAdapter = new SettlementNewsAdapter(ImmediatelSettlementActivity.this);
                                        settlementAdapter.setList(settlementQueryBeanList);
                                        relSettlement.setAdapter(settlementAdapter);
                                        relSettlements.setAdapter(settlementAdapter);
                                        llySettlementNodata.setVisibility(View.GONE);
//                                        llySettlementSelectTop.setVisibility(View.GONE);
                                        llySettlementSelect.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    imgSettlementSelect.setVisibility(View.VISIBLE);
                                    if (list.size() > 0) {
                                        llySettlementNodata.setVisibility(View.GONE);
                                        selectEndType = 1;
                                        Iterator<SettleBookBean> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            SettleBookBean value = iterator.next();
                                            for (int j = 0; j < settlementInfoBeanList.size(); j++) {
                                                if (settlementInfoBeanList.get(j).getSchemeNum().equals(value.getSchemeNum()) && settlementInfoBeanList.get(j).getBookNum().equals(value.getBookNum())) {
                                                    iterator.remove();
                                                }
                                            }
                                        }
                                        if (pageNo == 1) {
                                            if (settlementSelectList.size() < 1) {
                                                for (int i = 0; i < list.size(); i++) {
                                                    list.get(i).setType(true);
                                                }
                                                settlementSelectList.addAll(list);
                                            } else {
                                                for (int i = 0; i < list.size(); i++) {
                                                    for (int j = 0; j < settlementSelectList.size(); j++) {
                                                        if (list.get(i).getSchemeNum().equals(settlementSelectList.get(j).getSchemeNum()) && list.get(i).getBookNum().equals(settlementSelectList.get(j).getBookNum())) {
                                                            list.get(i).setType(true);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            for (int i = 0; i < list.size(); i++) {
                                                for (int j = 0; j < settlementSelectList.size(); j++) {
                                                    if (list.get(i).getSchemeNum().equals(settlementSelectList.get(j).getSchemeNum()) && list.get(i).getBookNum().equals(settlementSelectList.get(j).getBookNum())) {
                                                        list.get(i).setType(true);
                                                    }
                                                }
                                            }
                                        }
                                        settlementInfoBeanList.addAll(list);
//                                        if (settlementInfoBeanList.size() < 100){
//                                            num = settlementInfoBeanList.size();
//                                        }else {
//                                            num = 100;
//                                        }
//                                        for (int i = 0; i < num; i++) {
//                                            settlementInfoBeanList.get(i).setType(true);
//                                        }
//                                        tvSettlementNum.setText(num + "/" + totalNum);
                                        btnSettlementSelect.setText(getString(R.string.settlement) + "(" + num + "/" + totalNum + ")");
                                        settlementAdapter = new SettlementNewsAdapter(ImmediatelSettlementActivity.this);
                                        settlementAdapter.setList(settlementInfoBeanList);
                                        relSettlement.setAdapter(settlementAdapter);
                                        relSettlements.setAdapter(settlementAdapter);
                                        if (pageNo > 1) {
//                                            relSettlements.scrollToPosition(settlementAdapter.getItemCount());
                                            scrollToPosition();
                                        }
//                                    setListViewHeight(lvSettlement);
//                                        llySettlementSelectTop.setVisibility(View.GONE);
                                        llySettlementSelect.setVisibility(View.VISIBLE);
//                                    settlementQueryHttp(list);
                                    } else {
                                        ToastUtils.showShort(getString(R.string.no_settlement_information));
                                        llySettlementNodata.setVisibility(View.VISIBLE);
//                                        llySettlementSelectTop.setVisibility(View.GONE);
                                        llySettlementSelect.setVisibility(View.GONE);
                                        selectEndType = 0;
                                    }
                                }
                                num = showNum();
//                                if (num == 0) {
//                                    btnSettlementSelect.setText(getString(R.string.settlement) + "(0)");
//                                } else {
                                    btnSettlementSelect.setText(getString(R.string.settlement) + "(" + num + "/" + totalNum + ")");
//                                }
//                                tvSettlementNum.setText(num + "/" + totalNum);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        srlReceiving.finishLoadmore();
                    }
                });
    }

    //查询游戏类型
    private void getGameInfoHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        account_name = SPUtils.look(this, SPkey.username);
        account_password = SPUtils.look(this, SPkey.password);
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
                                for (int i = 0; i < gameList.length(); i++) {
                                    JSONObject json = gameList.getJSONObject(i);
                                    GameListBean gameListBean = new GameListBean();
                                    gameListBean.setGameName(json.getString("gameName"));
                                    gameListBean.setGameAlias(json.getString("gameAlias"));
                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
                                    gameListBean.setEnabled(json.getString("enabled"));
                                    gameStringList.add(gameListBean.getGameName());
                                    gameListBeans.add(gameListBean);
                                }
                                changeSpinner(spSpSettlementType, gameAdapter, gameStringList);
                                getBookInfoHttp("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        if (response == null || response.message() == null) {
                            ToastUtils.showShort(getString(R.string.checknet));
                        }
                        finish();
                    }
                });
    }

    //预览查询结算信息
    private void previewSettlementQueryHttp(final ArrayList<pos_settlementQuery.BookList> list) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        final String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();

        pos_settlementQuery.Data data = new pos_settlementQuery.Data(new pos_settlementQuery.SettlementInfo(gameAlias, list));
        pos_settlementQuery pos_getBookNumQuery = new pos_settlementQuery(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getBookNumQuery);
        OkGo.<String>post(MyUrl.pos_GetSettlementQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                previewSettlementQueryBean = new Gson().fromJson(response.body(), SettlementQueryBean.class);
                                String bookList = new Gson().toJson(list);
                                Intent intent = new Intent();
                                intent.putExtra("bookList", bookList);
                                intent.putExtra("gameAlias", gameAlias);
                                intent.putExtra("settlementQueryBean", previewSettlementQueryBean);
                                intent.setClass(ImmediatelSettlementActivity.this, SettlementPreviewActivity.class);
                                startActivity(intent);
                                llySettlementPop.setVisibility(View.GONE);
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
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!HttpContext.getCodeQr().equals("") && HttpContext.getCodeQr().length() == 20) {
            String codeQr = HttpContext.getCodeQr();
//            etManualScannerOne.setText(codeQr);
            DeliveryBookBean deliveryBookBean = new DeliveryBookBean();
            deliveryBookBean.setBookNum(codeQr.substring(10, 17));
            deliveryBookBean.setTicketNo(codeQr.substring(17, 20));
            deliveryBookBean.setSchemeNum(codeQr.substring(5, 10));
            deliveryBookBean.setLogisticsCode(codeQr);
            etSelect.setText(deliveryBookBean.getBookNum());

            qrType = "2";
            selectBook();
//            deliveryBookBeanList.add(deliveryBookBean);
//            deliveryInputAdapter.notifyDataSetChanged();
        } else if (!HttpContext.getCodeQr().equals("") && HttpContext.getCodeQr().length() != 20) {
            ToastUtils.showShort(getString(R.string.please_scan_the_correct_logistics_code));
        }
        HttpContext.setCodeQr("");
        etSelect.clearFocus();
    }

    private void selectBook() {
        String bookNum = etSelect.getText().toString().trim();
        if (bookNum.length() == 0) {
            selectType = 1;
            pageNo = 1;
            String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
            if (!gameAlias.equals("")) {
                num = 0;
                getBookInfoHttp("");
//                    scbSettlementAll.setClickable(false);
//                    scbSettlementAll.setChecked(true, true);
            } else {
                ToastUtils.showShort(getString(R.string.getting_game_information));
            }
        } else if (bookNum.length() < 8) {
            pageType = true;
            selectType = 2;
            pageNo = 1;
            String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
            if (!gameAlias.equals("")) {
                getBookInfoHttp(bookNum);
//                    scbSettlementAll.setClickable(false);
//                    scbSettlementAll.setChecked(true, true);
            } else {
                ToastUtils.showShort(getString(R.string.getting_game_information));
            }
        } else {
            ToastUtils.showShort(getString(R.string.does_not_exist));
        }
        notHide();
    }

    @OnClick({R.id.btn_settlement_reset, R.id.btn_settlement_submit, R.id.lly_settlement_back, R.id.btn_settlement_select, R.id.lly_settlement_pop, R.id.lly_settlement_one, R.id.bt_settlement_yes, R.id.bt_settlement_no, R.id.img_manual_scanner_qr, R.id.tv_settlement_all_pop_del, R.id.lly_settlement_all_pop, R.id.tv_settlement_open, R.id.lly_settlement_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_settlement_reset:
                resetAll();
                break;
            case R.id.btn_settlement_submit:
                String bookNum = etSelect.getText().toString().trim();
                if (bookNum.length() == 0) {
                    selectType = 1;
                    pageNo = 1;
                    String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
                    if (!gameAlias.equals("")) {
                        num = 0;
                        showInType();
                        getBookInfoHttp("");
//                    scbSettlementAll.setClickable(false);
//                    scbSettlementAll.setChecked(true, true);
                    } else {
                        ToastUtils.showShort(getString(R.string.getting_game_information));
                    }
                } else if (bookNum.length() < 8) {
                    pageType = true;
                    selectType = 2;
                    pageNo = 1;
                    String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
                    if (!gameAlias.equals("")) {
                        getBookInfoHttp(bookNum);
//                    scbSettlementAll.setClickable(false);
//                    scbSettlementAll.setChecked(true, true);
                    } else {
                        ToastUtils.showShort(getString(R.string.getting_game_information));
                    }
                } else {
                    ToastUtils.showShort(getString(R.string.does_not_exist));
                }
                notHide();
//                settlementQueryHttp();
//                testHttp();
                break;
            case R.id.bt_settlement_yes:
//                settlementSubmitHttp();
                if (selectEndType != 0) {
                    ArrayList<pos_settlementQuery.BookList> bookLists = new ArrayList<pos_settlementQuery.BookList>();
                    for (int i = 0; i < settlementSelectList.size(); i++) {
                        if (settlementSelectList.get(i).isType()) {
                            bookLists.add(new pos_settlementQuery.BookList(settlementSelectList.get(i).getBookNum(), settlementSelectList.get(i).getSchemeNum()));
                        }
                    }
                    if (bookLists.size() > 0) {
                        if (bookLists.size() < 101) {
                            previewSettlementQueryHttp(bookLists);
                        } else {
                            ToastUtils.showShort(getString(R.string.you_please_re_select));
                        }
                    } else {
                        ToastUtils.showShort(R.string.please_choose_number);
                    }
//                    showPopWindow();
                } else {
                    ToastUtils.showShort(getString(R.string.please_query_first));
                }
                break;
//            case R.id.lly_settlement_all:
//                if (scbSettlementAll.isChecked()) {
//                    for (int i = 0; i < settlementInfoBeanList.size(); i++) {
//                        settlementInfoBeanList.get(i).setType(false);
//                    }
//                    showNum("4");
//                } else {
//                    for (int i = 0; i < settlementInfoBeanList.size(); i++) {
//                        settlementInfoBeanList.get(i).setType(true);
//                    }
//                    showNum("3");
//                }
//                scbSettlementAll.setChecked(!scbSettlementAll.isChecked(), true);
//                settlementAdapter.notifyDataSetChanged();
//                break;
            case R.id.lly_settlement_back:
                finish();
                break;
            case R.id.btn_settlement_select:
//                settlementSelectList.clear();
//                for (int i = 0; i < settlementInfoBeanList.size(); i++) {
//                    if (settlementInfoBeanList.get(i).isType()) {
//                        settlementSelectList.add(settlementInfoBeanList.get(i));
//                    }
//                }
                if (null == settlementSelectAdapter) {
                    settlementSelectAdapter = new SettlementSelectAdapter(ImmediatelSettlementActivity.this);
                }
                settlementSelectAdapter.setList(settlementSelectList);
                relSettlementPop.setAdapter(settlementSelectAdapter);
                relSettlementPop.scrollToPosition(settlementSelectList.size() - 1);
                if (settlementSelectList != null && settlementSelectList.size() > 0) {
                    llySettlementPop.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.lly_settlement_pop:
                llySettlementPop.setVisibility(View.GONE);
                break;
            case R.id.lly_settlement_one:

                break;
            case R.id.bt_settlement_no:
                llySettlementPop.setVisibility(View.GONE);
                break;
            case R.id.img_manual_scanner_qr:
                Intent intent = new Intent();
                intent.setClass(this, RewardScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_settlement_all_pop_del:
                llySettlementAllPop.setVisibility(View.GONE);
                break;
            case R.id.tv_settlement_open:
                llySettlementAllPop.setVisibility(View.VISIBLE);
                break;
            case R.id.lly_settlement_all:
                if (allType) {
                    settlementSelectList.clear();
                    for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                        settlementInfoBeanList.get(i).setType(false);
                    }
                    imgSettlementSelect.setImageResource(R.drawable.settle_no);
                    num = 0;
                    btnSettlementSelect.setText(getString(R.string.settlement) + "(" + num + "/" + totalNum + ")");
                } else {
                    List<SettleBookBean> list = new ArrayList<SettleBookBean>();
                    if (settlementInfoBeanList.size() > 100) {
                        for (int i = 0; i < 100; i++) {
                            if (num < 100) {
                                if (!settlementInfoBeanList.get(i).isType()) {
                                    list.add(settlementInfoBeanList.get(i));
                                    num++;
                                }
                                settlementInfoBeanList.get(i).setType(true);
                            }
                        }
                    } else {
                        for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                            if (num < 100) {
                                if (!settlementInfoBeanList.get(i).isType()) {
                                    list.add(settlementInfoBeanList.get(i));
                                    num++;
                                }
                                settlementInfoBeanList.get(i).setType(true);
                            }
                        }
                    }
                    settlementSelectList.addAll(list);
                    imgSettlementSelect.setImageResource(R.drawable.settle_yes);
                    btnSettlementSelect.setText(getString(R.string.settlement) + "(" + num + "/" + totalNum + ")");
                }
                settlementAdapter.notifyDataSetChanged();
                allType = !allType;
                break;
        }
    }

    private void notHide() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(ImmediatelSettlementActivity.this
                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showDelList(SettleBookBean settleBookBean) {
        int pos = -1;
        for (int i = 0; i < settlementInfoBeanList.size(); i++) {
            if (settlementInfoBeanList.get(i).getBookNum().equals(settleBookBean.getBookNum()) && settlementInfoBeanList.get(i).getSchemeNum().equals(settleBookBean.getSchemeNum())) {
                settlementInfoBeanList.get(i).setType(false);
                pos = i;
            }
        }
        if (qrType.equals("2") && settlementQueryBeanList.size() < 2) {
            for (int i = 0; i < settlementQueryBeanList.size(); i++) {
                if (settlementQueryBeanList.get(i).getBookNum().equals(settleBookBean.getBookNum()) && settlementQueryBeanList.get(i).getSchemeNum().equals(settleBookBean.getSchemeNum())) {
                    settlementQueryBeanList.get(i).setType(false);
                    pos = i;
                }
            }
        }
        if (pos != -1) {
            settlementAdapter.notifyItemChanged(pos);
        }
        showNum("2", null);
    }

    public void showInfo() {
        changeSpinner(spSpSettlementType, gameAdapter, gameStringList);
    }

    public void resetBook() {
        if (bookList.size() > 0) {
            bookList.clear();
        }
        bookList.add(getString(R.string.all));
//        changeSpinner(spSettlementBook, bookAdapter, bookList);
    }

    public void resetAll() {
        settlementSelectList.clear();
        srlReceiving.setEnableLoadmore(true);
        changeSpinner(spSpSettlementType, gameAdapter, gameStringList);
        resetBook();
        startYear = 0;
        startMonth = 0;
        startDay = 0;
        pageNo = 1;
        llySettlementSelect.setVisibility(View.GONE);
//        llySettlementSelectTop.setVisibility(View.GONE);
        selectEndType = 0;
        llySettlementNodata.setVisibility(View.VISIBLE);
    }

    public void changeSpinner(Spinner spinner, ArrayAdapter adapter, List<String> list) {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, list);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        spinner.setAdapter(adapter);
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

    public void showNum(String type, SettleBookBean settleBookBean) {
        if (settleBookBean != null) {
            querySelects(settleBookBean);
        }
        if (type.equals("1")) {
            int selectNum = 0;
            for (int i = 0; i < settlementSelectList.size(); i++) {
                if (settlementSelectList.get(i).getSchemeNum().equals(settleBookBean.getSchemeNum()) && settlementSelectList.get(i).getBookNum().equals(settleBookBean.getBookNum())) {
                    selectNum++;
                }
            }
            if (selectNum == 0) {
                settlementSelectList.add(settleBookBean);
            }
            num++;
        } else if (type.equals("2")) {
            if (null != settleBookBean) {
                int selectNum = -1;
                for (int i = 0; i < settlementSelectList.size(); i++) {
                    if (settlementSelectList.get(i).getSchemeNum().equals(settleBookBean.getSchemeNum()) && settlementSelectList.get(i).getBookNum().equals(settleBookBean.getBookNum())) {
                        selectNum = i;
                    }
                }
                if (selectNum != -1) {
                    settlementSelectList.remove(selectNum);
                }
            }
            num--;
        } else if (type.equals("3")) {
            num = settlementInfoBeanList.size();
        } else {
            num = 0;
        }
        if (null == settleBookBean) {
            num = settlementSelectList.size() - 1;
        } else {
            num = settlementSelectList.size();
        }
        btnSettlementSelect.setText(getString(R.string.settlement) + "(" + num + "/" + totalNum + ")");
        if (num == 0) {
            imgSettlementSelect.setImageResource(R.drawable.settle_no);
            allType = false;
        } else {
            imgSettlementSelect.setImageResource(R.drawable.settle_yes);
            allType = true;
        }
    }

    public int getNum() {
        return num;
    }

    private boolean querySelect(SettleBookBean settleBookBean) {
        if (settlementQueryBeanList.size() > 0) {
            for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                if (settleBookBean == settlementInfoBeanList.get(i)) {
                    if (settlementInfoBeanList.get(i).isType()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void querySelects(SettleBookBean settleBookBean) {
        if (selectType != 2) {
            return;
        }
        for (int i = 0; i < settlementInfoBeanList.size(); i++) {
            if (settleBookBean.getBookNum().equals(settlementInfoBeanList.get(i).getBookNum()) && settleBookBean.getSchemeNum().equals(settlementInfoBeanList.get(i).getSchemeNum())) {
                settlementInfoBeanList.set(i, settleBookBean);
                return;
            }
        }
        settlementInfoBeanList.add(settleBookBean);
    }

    private int showNum() {
        return settlementSelectList.size();
    }

}
