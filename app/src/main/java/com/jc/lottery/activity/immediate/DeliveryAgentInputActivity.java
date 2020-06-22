package com.jc.lottery.activity.immediate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.my.MyImmediateActivity;
import com.jc.lottery.activity.scanner.RewardScannerActivity;
import com.jc.lottery.adapter.DeliveryAgentInputAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetCartonNoQuery;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.bean.resp.DeliveryBoxBean;
import com.jc.lottery.content.HttpContext;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.RecyclerViewAnimUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.MyRecycleview;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

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
 * 派送详情页面
 */
public class DeliveryAgentInputActivity extends BaseActivity {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.lly_delivery_agent_pop)
    LinearLayout llyRewardRecordPop;
    @BindView(R.id.rel_delivery_input)
    RecyclerView relDeliveryInput;
    @BindView(R.id.rel_delivery_input_all)
    RecyclerView relDeliveryInputAll;
    @BindView(R.id.rel_delivery_input_all_pop)
    RecyclerView relDeliveryInputAllPop;
    @BindView(R.id.tv_delivery_agent_number)
    TextView tvDeliveryAgentNumber;
    @BindView(R.id.tv_delivery_agent_sure)
    TextView tvDeliveryAgentSure;
    @BindView(R.id.tv_delivery_open)
    TextView tvDeliveryAgentOpen;
    @BindView(R.id.btn_delivery_agent_query)
    Button btnDeliveryAgentQuery;
    @BindView(R.id.et_delivery_agent_book)
    EditText etDeliveryAgentBook;
    @BindView(R.id.img_delivery_agent_qr)
    ImageView imgDeliveryAgentQr;
    @BindView(R.id.img_delivery_agent_pop_del)
    ImageView imgDeliveryAgentPopDel;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    //    @BindView(R.id.header_receiving)
    //    private List<String> list = new ArrayList<String>();
    private ArrayList<DeliveryBookBean> deliveryAllList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliveryAllSaveList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliverySelectList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliverySelectSaveList = new ArrayList<DeliveryBookBean>();
    private List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<DeliveryBookBean>();
    //    private ArrayList<DeliveryBookBean> deliveryBookBeanList = new ArrayList<DeliveryBookBean>();
    private DeliveryAgentInputAdapter deliveryInputAllAdapter;
    private DeliveryAgentInputAdapter deliveryInputSelectAdapter;
    private String bookJson = "";
    private String ticketNum = "";
    private String gameAlias = "";
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private String orderCode;
    private String qrType = "1"; //1 : 输入 2 ：扫码
    private int selectType = 1; //1：全部 2：检索

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery_agent_inputs;
    }

    @Override
    public void getPreIntent() {
        gameAlias = getIntent().getStringExtra("gameAlias");
        orderCode = getIntent().getStringExtra("orderCode");
        ticketNum = getIntent().getStringExtra("ticketNum");
//        String bookJson = SPUtils.look(this,SPkey.bookJson);
        String selectJson = SPUtils.look(this, SPkey.deliverySelectList);
//        String allJson = SPUtils.look(this,SPkey.deliveryAllList);
//        String saveJson = SPUtils.look(this,SPkey.deliveryAllSaveList);
        Type listType = new TypeToken<List<DeliveryBookBean>>() {
        }.getType();
        deliverySelectList = new Gson().fromJson(selectJson, listType);
//        deliveryAllSaveList = new Gson().fromJson(saveJson, listType);
//        deliveryAllList = new Gson().fromJson(allJson, listType);

//        deliveryBookBeanList = new Gson().fromJson(bookJson, listType);
//        if (deliverySelectList.size() < 1) {
//            deliveryAllList = new Gson().fromJson(bookJson, listType);
//            deliveryAllSaveList = new Gson().fromJson(bookJson, listType);
//        }
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
        MyRecycleview myRecycleview = new MyRecycleview(this);  //自定义布局管理器
        myRecycleview.setOrientation(OrientationHelper.VERTICAL);  //垂直
        myRecycleview.setScrollEnabled(true);  //禁止滑动
        relDeliveryInput.setLayoutManager(new LinearLayoutManager(this));
        relDeliveryInputAll.setLayoutManager(myRecycleview);
        relDeliveryInputAllPop.setLayoutManager(new LinearLayoutManager(this));
//        if (deliveryBookBeanList.size() < 1) {
//            llyDeliveryNodata.setVisibility(View.VISIBLE);
//            relDeliveryInput.setVisibility(View.GONE);
//        } else {
//            llyDeliveryNodata.setVisibility(View.GONE);
//            relDeliveryInput.setVisibility(View.VISIBLE);
//        }
        etDeliveryAgentBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                qrType = "1";
                if (s.toString().trim().equals("")) {
                    selectType = 1;
                    srlReceiving.setEnableLoadmore(true);
                    selectBook("");
                }
            }
        });

        srlReceiving.setEnableRefresh(false);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pageNo < pageCount) {
                    pageNo++;
                    getHttpInfo(gameAlias,orderCode,"");
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });

//        relDeliveryInputAll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                //继承了Activity的onTouchEvent方法，直接监听点击事件
//                if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                    //当手指按下的时候
//                    x1 = event.getX();
//                    y1 = event.getY();
//                }
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                    //当手指离开的时候
//                    x2 = event.getX();
//                    y2 = event.getY();
//                    if(y1 - y2 > 30) {
//                        llyRewardRecordPop.setVisibility(View.VISIBLE);
////                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
//                    } else if(y2 - y1 > 30) {
////                        llyRewardRecordPop.setVisibility(View.VISIBLE);
////                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//                    } else if(x1 - x2 > 30) {
////                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
//                    } else if(x2 - x1 > 30) {
////                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                return true;
//            }
//        });

        relDeliveryInputAll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if(layoutManager instanceof GridLayoutManager){
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof LinearLayoutManager){
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof StaggeredGridLayoutManager){
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1){
                        llyRewardRecordPop.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

        });
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

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if(y1 - y2 > 50) {
//                llyRewardRecordPop.setVisibility(View.VISIBLE);
////                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
//            } else if(y2 - y1 > 50) {
//                llyRewardRecordPop.setVisibility(View.VISIBLE);
////                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//            } else if(x1 - x2 > 50) {
////                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if(x2 - x1 > 50) {
////                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public void initData() {
        deliveryInputAllAdapter = new DeliveryAgentInputAdapter(this, "1");
        deliveryInputAllAdapter.setList(deliveryAllList);
        relDeliveryInputAll.setAdapter(deliveryInputAllAdapter);
        relDeliveryInputAllPop.setAdapter(deliveryInputAllAdapter);
        deliveryInputSelectAdapter = new DeliveryAgentInputAdapter(this, "2");
        deliveryInputSelectAdapter.setList(deliverySelectList);
        relDeliveryInput.setAdapter(deliveryInputSelectAdapter);
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(relDeliveryInput);
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(relDeliveryInputAll);
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(relDeliveryInputAllPop);
        tvDeliveryAgentNumber.setText(deliverySelectList.size() + " / " + ticketNum);
//        getHttpInfo();
        getHttpInfo(gameAlias,orderCode,"");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!HttpContext.getCodeQr().equals("") && HttpContext.getCodeQr().length() == 20) {
            String codeQr = HttpContext.getCodeQr();
//            etManualScannerOne.setText(codeQr);
            String schemeCode = "";
            DeliveryBookBean deliveryBookBean = new DeliveryBookBean();
            deliveryBookBean.setBookNum(codeQr.substring(10, 17));
            deliveryBookBean.setTicketNo(codeQr.substring(17, 20));
            deliveryBookBean.setSchemeNum(codeQr.substring(5, 10));
            schemeCode = codeQr.substring(0, 5);
            deliveryBookBean.setLogisticsCode(codeQr);
            if (getBookNo(deliveryBookBean.getBookNum())) {
                etDeliveryAgentBook.setText(deliveryBookBean.getBookNum());
                qrType = "2";
                selectType = 2;
                boolean select = selectBook(deliveryBookBean.getBookNum());
                if (select){
                    return;
                }
                if (deliveryBookBean.getBookNum().length() < 8) {
                    if (getBookNo(deliveryBookBean.getBookNum())) {
                        if (!selectBooks(deliveryBookBean.getBookNum())){
                            getSelectHttpInfo(gameAlias,orderCode,deliveryBookBean.getBookNum());
                        }else {
//                            ToastUtils.showShort(getString(R.string.does_not_exist));
                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.please_scan_the_same_number_informations));
                    }
                }
//                getQueryBookNo(schemeCode, deliveryBookBean);
            } else {
                ToastUtils.showShort(getString(R.string.please_scan_the_same_number_informations));
            }
//            deliveryBookBeanList.add(deliveryBookBean);
//            deliveryInputAdapter.notifyDataSetChanged();
        } else if (!HttpContext.getCodeQr().equals("") && HttpContext.getCodeQr().length() != 20) {
            ToastUtils.showShort(getString(R.string.please_scan_the_correct_logistics_code));
        }
        HttpContext.setCodeQr("");
//        notHide();
        etDeliveryAgentBook.clearFocus();
    }

    private boolean getBookNo(String bookNum) {
        for (int i = 0; i < deliverySelectList.size(); i++) {
            if (deliverySelectList.get(i).getBookNum().equals(bookNum)) {
                return false;
            }
        }
        return true;
    }

    public void addSelectList(String type, int pos) {
        if (type.equals("1")){
            if (deliverySelectList.size() == Integer.parseInt(ticketNum)){
                if (!deliveryAllList.get(pos).isBookType()) {
                    ToastUtils.showShort(getString(R.string.this_number_is_inconsistent));
                    return;
                }
            }
        }
        if (type.equals("1")) {
            boolean bookType = false;
            int dPos = 0;
            for (int i = 0; i < deliveryAllSaveList.size(); i++) {
                if (deliveryAllSaveList.get(i).getBookNum().equals(deliveryAllList.get(pos).getBookNum())) {
//                    deliveryAllSaveList.remove(i);
                    dPos = i;
                    bookType = deliveryAllSaveList.get(i).isBookType();
                    deliveryAllSaveList.get(i).setBookType(!bookType);
                }
            }
            if (!bookType){
                deliverySelectList.add(deliveryAllList.get(pos));
                deliveryInputSelectAdapter.notifyItemChanged(pos);
                relDeliveryInput.scrollToPosition(deliverySelectList.size() - 1);
            }else {
                int selectPos = -1;
                for (int i = 0; i < deliverySelectList.size(); i++) {
                    if (deliverySelectList.get(i).getBookNum().equals(deliveryAllSaveList.get(dPos).getBookNum())&&deliverySelectList.get(i).getSchemeNum().equals(deliveryAllSaveList.get(dPos).getSchemeNum())){
                        selectPos = i;
                    }
                }
                if (selectPos != -1) {
                    deliverySelectList.remove(selectPos);
                    deliveryInputSelectAdapter.notifyDataSetChanged();
                }
//                deliveryInputSelectAdapter.notifyItemRangeChanged(pos, deliverySelectList.size() - pos);
            }
            deliveryAllList.get(pos).setBookType(!bookType);
            deliveryInputAllAdapter.notifyItemChanged(pos);
//            deliveryInputAllAdapter.notifyItemRemoved(pos);
//            deliveryInputAllAdapter.notifyItemRangeChanged(pos, deliveryAllList.size() - pos);

        } else {
            int dPos = 0;
            for (int i = 0; i < deliveryAllSaveList.size(); i++) {
                if (deliveryAllSaveList.get(i).getBookNum().equals(deliverySelectList.get(pos).getBookNum())) {
//                    deliveryAllSaveList.remove(i);
                    dPos = i;
                    if (deliveryAllList.size() < 2){
                        if (deliveryAllList.size() == 1){
                            if (deliveryAllList.get(0).getSchemeNum().equals(deliverySelectList.get(pos).getSchemeNum())&&deliveryAllList.get(0).getBookNum().equals(deliverySelectList.get(pos).getBookNum())) {
                                deliveryAllList.get(0).setBookType(false);
                                deliveryInputAllAdapter.notifyItemChanged(0);
                            }
                        }
                    }else {
                        deliveryAllList.get(i).setBookType(false);
                        deliveryInputAllAdapter.notifyItemChanged(dPos);
                    }
                    deliveryAllSaveList.get(i).setBookType(false);
                }
            }
//            deliveryAllList.add(deliverySelectList.get(pos));
//            deliveryAllSaveList.add(deliverySelectList.get(pos));
            deliverySelectList.remove(pos);
            deliveryInputSelectAdapter.notifyItemRemoved(pos);
            deliveryInputSelectAdapter.notifyItemRangeChanged(pos, deliverySelectList.size() - pos);
        }
        tvDeliveryAgentNumber.setText(deliverySelectList.size() + " / " + ticketNum);
    }

    @OnClick({R.id.lly_reward_record_back, R.id.tv_delivery_agent_sure, R.id.btn_delivery_agent_query,R.id.img_delivery_agent_qr,R.id.img_delivery_agent_pop_del,R.id.lly_delivery_agent_pop,R.id.tv_delivery_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.tv_delivery_agent_sure:
                DeliveryDetailsActivity.instance.showAdapter(deliverySelectList,deliveryAllSaveList,deliveryAllList);
                finish();
                break;
            case R.id.btn_delivery_agent_query:
                String book = etDeliveryAgentBook.getText().toString().trim();
                if (book.length() < 8 && book.length() != 0) {
                    if (getBookNo(book)) {
                        if (!selectBooks(book)){
                            selectType = 2;
                            getSelectHttpInfo(gameAlias,orderCode,book);
                        }else {
//                            ToastUtils.showShort(getString(R.string.does_not_exist));
                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.please_scan_the_same_number_informations));
                    }
                }else if (book.length() == 0){
                    pageNo = 1;
                    selectType = 1;
                    getHttpInfo(gameAlias,orderCode,"");
//                    ToastUtils.showShort(getString(R.string.qr_code_format_incorrect));
                }else {
                    ToastUtils.showShort(getString(R.string.does_not_exist));
                }
                notHide();
                break;
            case R.id.img_delivery_agent_qr:
                Intent intent = new Intent();
                intent.setClass(this, RewardScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.img_delivery_agent_pop_del:
                llyRewardRecordPop.setVisibility(View.GONE);
                break;
            case R.id.lly_delivery_agent_pop:
                break;
            case R.id.tv_delivery_open:
                llyRewardRecordPop.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void notHide(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(DeliveryAgentInputActivity.this
                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean selectBook(String book) {
        int select = 0;
        if (book.equals("")) {
            if (deliveryAllSaveList.size() > 0) {
                deliveryAllList.clear();
                deliveryAllList.addAll(deliveryAllSaveList);
                deliveryInputAllAdapter.notifyDataSetChanged();
            }
            return true;
        }
        for (int i = 0; i < deliveryAllSaveList.size(); i++) {
            if (deliveryAllSaveList.get(i).getBookNum().indexOf(book) != -1) {
                if (select == 0) {
                    deliveryAllList.clear();
                }
                if (qrType.equals("2")){
                    deliveryAllSaveList.get(i).setBookType(true);
                }
                deliveryAllList.add(deliveryAllSaveList.get(i));
                select++;
            }
        }
        if (select != 0) {
            if (qrType.equals("2")){
                deliverySelectList.addAll(deliveryAllList);
                if (null == deliveryInputSelectAdapter) {
                    deliveryInputSelectAdapter = new DeliveryAgentInputAdapter(this, "2");
                    deliveryInputSelectAdapter.setList(deliverySelectList);
                    relDeliveryInput.setAdapter(deliveryInputSelectAdapter);
                }else {
                    deliveryInputSelectAdapter.notifyDataSetChanged();
                    relDeliveryInput.scrollToPosition(deliverySelectList.size() - 1);
                }
                tvDeliveryAgentNumber.setText(deliverySelectList.size() + " / " + ticketNum);
            }
            deliveryInputAllAdapter.notifyDataSetChanged();
            return true;
        } else {
            return false;
//            ToastUtils.showShort(getString(R.string.does_not_exist));
        }
    }

    private void getHttpInfo(String gameAlias, String orderCode, String bookNum) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
//        String account_name = "lrgly";
//        String account_password = "123456";
        pos_GetCartonNoQuery.GetInfo getInfo = new pos_GetCartonNoQuery.GetInfo(gameAlias, orderCode,pageNo + "","");
        pos_GetCartonNoQuery.DataBean dataBean = new pos_GetCartonNoQuery.DataBean(getInfo);
        pos_GetCartonNoQuery pos_getRecordInfo = new pos_GetCartonNoQuery(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetCartonNoQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        try {
                            srlReceiving.finishLoadmore();
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (selectType == 2){
                                srlReceiving.setEnableLoadmore(false);
                            }
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                            if (jsonObject.getString("code").equals("00000")) {
                                if (pageNo == 1){
                                    deliveryAllSaveList.clear();
                                    deliveryAllList.clear();
                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                if (jsonArray.length() > 0) {
                                    bookJson = jsonArray.toString();
                                    Type listType = new TypeToken<List<DeliveryBookBean>>() {
                                    }.getType();
                                    deliveryBookBeanList = new Gson().fromJson(jsonArray.toString(), listType);
                                    Iterator<DeliveryBookBean> iterator = deliveryBookBeanList.iterator();
                                    while (iterator.hasNext()) {
                                        DeliveryBookBean value = iterator.next();
                                        for (int j = 0; j < deliverySelectSaveList.size(); j++) {
                                            if (deliverySelectSaveList.get(j).getSchemeNum().equals(value.getSchemeNum())&&deliverySelectSaveList.get(j).getBookNum().equals(value.getBookNum())){
                                                iterator.remove();
                                            }
                                        }
                                    }
//                                    if (pageNo == 1) {
                                    for (int i = 0; i < deliverySelectList.size(); i++) {
                                        for (int j = 0; j < deliveryBookBeanList.size(); j++) {
                                            if (deliveryBookBeanList.get(j).getSchemeNum().equals(deliverySelectList.get(i).getSchemeNum()) && deliveryBookBeanList.get(j).getBookNum().equals(deliverySelectList.get(i).getBookNum())) {
                                                deliveryBookBeanList.get(j).setBookType(true);
                                            }
                                        }
                                    }
//                                    }
                                    deliveryAllSaveList.addAll(deliveryBookBeanList);
                                    deliveryAllList.addAll(deliveryBookBeanList);
                                    deliveryInputAllAdapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.showShort(jsonObject.getString("message"));
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

    private void getSelectHttpInfo(String gameAlias, String orderCode, String bookNum) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
//        String account_name = "lrgly";
//        String account_password = "123456";
        pos_GetCartonNoQuery.GetInfo getInfo = new pos_GetCartonNoQuery.GetInfo(gameAlias, orderCode,pageNo + "",bookNum);
        pos_GetCartonNoQuery.DataBean dataBean = new pos_GetCartonNoQuery.DataBean(getInfo);
        pos_GetCartonNoQuery pos_getRecordInfo = new pos_GetCartonNoQuery(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetCartonNoQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        try {
//                            if (selectType == 2){
                                srlReceiving.setEnableLoadmore(false);
//                            }
                            srlReceiving.finishLoadmore();
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                if (jsonArray.length() > 0) {
                                    Type listType = new TypeToken<List<DeliveryBookBean>>() {
                                    }.getType();
                                    List<DeliveryBookBean> deliveryBookBeanLists = new ArrayList<DeliveryBookBean>();
                                    deliveryBookBeanLists = new Gson().fromJson(jsonArray.toString(), listType);
                                    if (qrType.equals("2")){
                                        if (deliveryBookBeanLists.size() < 2) {
                                            for (int i = 0; i < deliveryBookBeanLists.size(); i++) {
                                                deliveryBookBeanLists.get(i).setBookType(true);
                                            }
                                        }
                                        deliverySelectList.addAll(deliveryBookBeanLists);
                                        deliveryInputSelectAdapter.notifyDataSetChanged();
                                        relDeliveryInput.scrollToPosition(deliverySelectList.size() - 1);
                                        tvDeliveryAgentNumber.setText(deliverySelectList.size() + " / " + ticketNum);
                                    }
                                    deliveryAllList.clear();
                                    deliverySelectSaveList.addAll(deliveryBookBeanLists);
                                    deliveryAllSaveList.addAll(deliveryBookBeanLists);
                                    deliveryAllList.addAll(deliveryBookBeanLists);
                                    deliveryInputAllAdapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.showShort(getString(R.string.does_not_exist));
                                    etDeliveryAgentBook.setText("");
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

    private boolean selectBooks(String book) {
        int select = 0;
        if (book.equals("")) {
            if (deliveryAllSaveList.size() > 0) {
                deliveryAllList.clear();
                deliveryAllList.addAll(deliveryAllSaveList);
                deliveryInputAllAdapter.notifyDataSetChanged();
            }
            srlReceiving.setEnableLoadmore(false);
            return true;
        }
        for (int i = 0; i < deliveryAllSaveList.size(); i++) {
            if (deliveryAllSaveList.get(i).getBookNum().indexOf(book) != -1) {
                if (select == 0) {
                    deliveryAllList.clear();
                }
                deliveryAllList.add(deliveryAllSaveList.get(i));
                select++;
            }
        }
        if (select != 0) {
            deliveryInputAllAdapter.notifyDataSetChanged();
        } else {
//            ToastUtils.showShort(getString(R.string.does_not_exist));
            return false;
        }
        srlReceiving.setEnableLoadmore(false);
        return true;
    }

}
