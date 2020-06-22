package com.jc.lottery.activity.immediate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.RewardScannerActivity;
import com.jc.lottery.adapter.DeliveryAgentInputAdapter;
import com.jc.lottery.adapter.DeliveryAgentInputsAdapter;
import com.jc.lottery.adapter.SettlementAgentNewestAdapter;
import com.jc.lottery.adapter.SettlementNewestAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.RecordInfoBean;
import com.jc.lottery.bean.req.pos_GetCartonNoQuery;
import com.jc.lottery.bean.req.pos_GetLogisticsDelivery;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.bean.resp.DeliveryBoxBean;
import com.jc.lottery.content.Constant;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 派送详情页面
 */
public class DeliveryAgentDetailsActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.btn_delivery_details_submit)
    Button btnDeliveryDetailsSubmit;
    @BindView(R.id.btn_delivery_details_select)
    Button btnDeliveryDetailsSelect;
    @BindView(R.id.bt_agent_no)
    Button btnAgentNo;
    @BindView(R.id.bt_agent_yes)
    Button btnAgentYes;
    @BindView(R.id.btn_delivery_details_next)
    Button btnDeliveryDetailsNext;
    @BindView(R.id.lly_delivery_box_number)
    PercentLinearLayout llyDeliveryBoxNumber;
    @BindView(R.id.lly_delivery_this_number)
    PercentLinearLayout llyDeliveryThisNumber;
    @BindView(R.id.lly_delivery_stock)
    PercentLinearLayout llyDeliveryStock;
    @BindView(R.id.lly_delivery_unit_fare)
    PercentLinearLayout llyDeliveryUnitFare;
    @BindView(R.id.lly_delivery_tickets)
    PercentLinearLayout llyDeliveryTickets;
    @BindView(R.id.sp_delivery_box_number)
    Spinner spDeliveryBoxNumber;
    //    @BindView(R.id.sp_delivery_this_number)
//    Spinner spDeliveryThisNumber;
    @BindView(R.id.tv_delivery_name)
    TextView tvDeliveryName;
    @BindView(R.id.tv_delivery_ticket_number)
    TextView tvDeliveryTicketNumber;
    @BindView(R.id.tv_delivery_number)
    TextView tvDeliveryNumber;
    @BindView(R.id.tv_delivery_channel)
    TextView tvDeliveryChannel;
    @BindView(R.id.tv_delivery_pay_status)
    TextView tvDeliveryPayStatus;
    @BindView(R.id.tv_delivery_this_number)
    TextView tvDeliveryThisNumber;
    @BindView(R.id.rel_delivery_details)
    RecyclerView relDeliveryDetails;
    @BindView(R.id.rel_delivery_select)
    RecyclerView relDeliverySelect;
    @BindView(R.id.rel_agent_pop)
    RecyclerView relDeliveryPop;
    @BindView(R.id.img_delivery_this_number)
    ImageView imgDeliveryThisNumber;
    @BindView(R.id.tv_delivery_stock)
    TextView tvDeliveryStock;
    @BindView(R.id.tv_delivery_unit_fare)
    TextView tvDeliveryUnitFare;
    @BindView(R.id.tv_delivery_tickets)
    TextView tvDeliveryTickets;
    @BindView(R.id.tv_delivery_pay_mode)
    TextView tvDeliveryPayMode;
    @BindView(R.id.srl_receiving)
    SmartRefreshLayout srlReceiving;
    @BindView(R.id.lly_agent_all_pop)
    LinearLayout llyAgentPop;
    @BindView(R.id.img_delivery_agent_qr)
    ImageView imgDeliveryAgentQr;
    @BindView(R.id.img_delivery_agent_select)
    ImageView imgDeliveryAgentSelect;
    @BindView(R.id.et_delivery_agent_book)
    EditText etDeliveryAgentBook;
    private RecordInfoBean recordInfoBean = new RecordInfoBean();
    private String gameAlias = "";
    private String orderCode = "";
    private String role = ""; //角色
    private List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<DeliveryBookBean>();
    private final static List<DeliveryBookBean> deliverySaveList = new ArrayList<DeliveryBookBean>();
    private List<DeliveryBoxBean> deliveryBoxBeanList = new ArrayList<DeliveryBoxBean>();
    private ArrayAdapter adapter;
    private List<String> nameList = new ArrayList<String>();
    private String bookJson = "";
//    private SettlementAgentNewestAdapter settlementNewestAdapter;
    private DeliveryAgentInputsAdapter deliveryInputAllAdapter;
    private DeliveryAgentInputsAdapter deliveryInputPopAdapter;
    private DeliveryAgentInputsAdapter deliveryInputSelectAdapter;
    public static DeliveryAgentDetailsActivity instance = null;
    private String ticketNum = "";
    private ArrayList<DeliveryBookBean> selectList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliveryAllLists = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliveryAllSaveLists = new ArrayList<DeliveryBookBean>();

//    private ArrayList<DeliveryBookBean> deliveryAllList = new ArrayList<DeliveryBookBean>();
//    private ArrayList<DeliveryBookBean> deliveryAllSaveList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliverySelectList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliveryInputSelectList = new ArrayList<DeliveryBookBean>();
//    private ArrayList<DeliveryBookBean> deliverySelectSaveList = new ArrayList<DeliveryBookBean>();
    private int pageNo = 1;
    private int pageCount = 1;
    private String qrType = "1"; //1 : 输入 2 ：扫码
    private int selectType = 1; //1：全部 2：检索

    @Override
    public int getLayoutId() {
        return R.layout.activity_agent_delivery_details;
    }

    @Override
    public void getPreIntent() {
        recordInfoBean = (RecordInfoBean) getIntent().getSerializableExtra("recordInfoBean");
        gameAlias = getIntent().getStringExtra("gameAlias");
        orderCode = getIntent().getStringExtra("orderCode");
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
        relDeliveryDetails.setLayoutManager(new GridLayoutManager(this, 1));
        relDeliveryPop.setLayoutManager(new GridLayoutManager(this, 1));
        relDeliverySelect.setLayoutManager(new GridLayoutManager(this, 1));
        srlReceiving.setEnableRefresh(false);
    }

    @Override
    public void initListener() {
        srlReceiving.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pageNo < pageCount) {
                    pageNo++;
                    getAgentHttpInfo(gameAlias,orderCode,"");
                }else {
                    srlReceiving.finishLoadmoreWithNoMoreData();
                }
            }
        });

        etDeliveryAgentBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                qrType = "1";
//                if (s.toString().trim().equals("")) {
//                    selectType = 1;
//                    srlReceiving.setEnableLoadmore(true);
//                    selectBook("");
//                }
            }
        });

        srlReceiving.setEnableRefresh(false);
//    }
    }

    @Override
    public void initData() {
//        showView();
        role = SPUtils.look(this, SPkey.roleAlias);
//        role = "gly";
        showRoleView();
        if (role.equals("dls")) {
            btnDeliveryDetailsNext.setVisibility(View.VISIBLE);
            getHttpInfo(gameAlias, orderCode);
        }else {
            btnDeliveryDetailsNext.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.btn_delivery_details_submit, R.id.lly_delivery_this_number,R.id.btn_delivery_details_next,R.id.btn_delivery_details_select,R.id.bt_agent_yes,R.id.bt_agent_no,R.id.lly_agent_all_pop,R.id.img_delivery_agent_qr,R.id.img_delivery_agent_select})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.lly_delivery_this_number:
                if (role.equals("gly")) {
                    bookJson = new Gson().toJson(deliveryBookBeanList);
                    intent.putExtra("gameAlias", gameAlias);
                    intent.putExtra("ticketNum", ticketNum);
                    intent.putExtra("bookJson", bookJson);
                    intent.setClass(this, DeliveryInputActivity.class);
                    startActivity(intent);
                }else {
                    if (deliveryBookBeanList.size() > 0) {
                        String select = new Gson().toJson(selectList);
                        String deliveryAll = new Gson().toJson(deliveryAllLists);
                        String deliveryAllSave = new Gson().toJson(deliveryAllSaveLists);
                        SPUtils.save(this,SPkey.deliverySelectList,select);
                        SPUtils.save(this,SPkey.deliveryAllList,deliveryAll);
                        SPUtils.save(this,SPkey.deliveryAllSaveList,deliveryAllSave);
                        SPUtils.save(this,SPkey.bookJson,bookJson);
                        intent.putExtra("ticketNum", ticketNum);
                        intent.putExtra("gameAlias", gameAlias);
                        intent.putExtra("orderCode", orderCode);
//                    intent.putExtra("bookJson", bookJson);
//                    intent.putExtra("deliverySelectList", select);
//                    intent.putExtra("deliveryAllList", deliveryAll);
//                    intent.putExtra("deliveryAllSaveList", deliveryAllSave);
                        intent.setClass(this, DeliveryAgentInputActivity.class);
                        startActivity(intent);
                    }else {
                        ToastUtils.showShort(getString(R.string.no_distributable_book_number_found));
                    }
                }
                break;
            case R.id.btn_delivery_details_submit:
//                intent.setClass(this, DeliveryInputActivity.class);
//                startActivity(intent);
                if (role.equals("dls")) {
                    if (deliverySelectList.size() > 0){
                        submitDelivery();
                    }else {
                        ToastUtils.showShort(getString(R.string.please_choose_number));
                    }
                }else {
                    if (deliveryBookBeanList.size() > 0) {
                        submitDelivery();
                    }else {
                        ToastUtils.showShort(getString(R.string.please_choose_number));
                    }
                }
                break;
            case R.id.btn_delivery_details_next:
                if (deliveryBookBeanList.size() > 0) {
                    String select = new Gson().toJson(selectList);
                    String deliveryAll = new Gson().toJson(deliveryAllLists);
                    String deliveryAllSave = new Gson().toJson(deliveryAllSaveLists);
                    SPUtils.save(this,SPkey.deliverySelectList,select);
                    SPUtils.save(this,SPkey.deliveryAllList,deliveryAll);
                    SPUtils.save(this,SPkey.deliveryAllSaveList,deliveryAllSave);
                    SPUtils.save(this,SPkey.bookJson,bookJson);
                    intent.putExtra("ticketNum", ticketNum);
                    intent.putExtra("gameAlias", gameAlias);
                    intent.putExtra("orderCode", orderCode);
//                    intent.putExtra("bookJson", bookJson);
//                    intent.putExtra("deliverySelectList", select);
//                    intent.putExtra("deliveryAllList", deliveryAll);
//                    intent.putExtra("deliveryAllSaveList", deliveryAllSave);
                    intent.setClass(this, DeliveryAgentInputActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.showShort(getString(R.string.no_distributable_book_number_found));
                }
                break;
            case R.id.btn_delivery_details_select:
//                deliverySaveList.clear();
//                deliverySaveList.addAll(deliveryBookBeanList);
//                deliverySelectList.clear();
//                for (int j = 0; j < deliveryBookBeanList.size(); j++) {
//                    if (deliveryBookBeanList.get(j).isBookType()){
//                        List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<>();
//                        deliveryBookBeanList.add(deliveryBookBeanList.get(j));
//                        deliverySelectList.addAll(deepCopy(deliveryBookBeanList));
////                        deliverySelectList.add(deliveryBookBeanList.get(j));
//                    }
//                }
//                deliveryInputPopAdapter.notifyDataSetChanged();
                llyAgentPop.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_agent_no:
                llyAgentPop.setVisibility(View.GONE);
                deliverySelectList.clear();
                for (int i = 0; i < deliveryBookBeanList.size(); i++) {
                    if (deliveryBookBeanList.get(i).isBookType()){
//                        DeliveryBookBean deliveryBookBean = deliveryBookBeanList.get(i);
                        try {
                            deliverySelectList.add(deliveryBookBeanList.get(i).clone());
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                deliveryInputPopAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_agent_yes:
                for (int i = 0; i < deliverySelectList.size(); i++) {
//                    if (deliverySelectList.get(i).isBookType()){
                        for (int j = 0; j < deliveryBookBeanList.size(); j++) {
                            if (deliveryBookBeanList.get(j).getBookNum().equals(deliverySelectList.get(i).getBookNum())){
                                if (deliverySelectList.get(i).isBookType()){
                                    deliveryBookBeanList.get(j).setBookType(true);
                                }else {
                                    deliveryBookBeanList.get(j).setBookType(false);
                                }
                            }
                        }
//                    }
                }
                deliveryInputAllAdapter.notifyDataSetChanged();
                llyAgentPop.setVisibility(View.GONE);
                deliverySelectList.clear();
                for (int i = 0; i < deliveryBookBeanList.size(); i++) {
                    if (deliveryBookBeanList.get(i).isBookType()){
//                        DeliveryBookBean deliveryBookBean = ;
                        try {
                            deliverySelectList.add(deliveryBookBeanList.get(i).clone());
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                deliveryInputPopAdapter.notifyDataSetChanged();
                btnDeliveryDetailsSelect.setText(getString(R.string.have_chosen) + "(" + deliverySelectList.size() + "/" + ticketNum + ")");
                break;
            case R.id.img_delivery_agent_qr:
                intent.setClass(this, RewardScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.img_delivery_agent_select:
                String book = etDeliveryAgentBook.getText().toString().trim();
                if (book.length() < 8 && book.length() != 0) {
                    if (getBookNo(book)) {
//                        if (!selectBooks(book)){
                            selectType = 2;
                            relDeliverySelect.setVisibility(View.VISIBLE);
                            srlReceiving.setVisibility(View.GONE);
                            getSelectHttpInfo(gameAlias,orderCode,book);
//                        }else {
////                            ToastUtils.showShort(getString(R.string.does_not_exist));
//                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.please_scan_the_same_number_informations));
                    }
                }else if (book.length() == 0){
                    pageNo = 1;
                    relDeliverySelect.setVisibility(View.GONE);
                    srlReceiving.setVisibility(View.VISIBLE);
//                    selectType = 1;
//                    getHttpInfo(gameAlias,orderCode,"");
//                    ToastUtils.showShort(getString(R.string.qr_code_format_incorrect));
                }else {
                    ToastUtils.showShort(getString(R.string.does_not_exist));
                }
                notHide();
                break;
        }
    }

    private boolean getBookNo(String bookNum) {
        for (int i = 0; i < deliverySelectList.size(); i++) {
            if (deliverySelectList.get(i).getBookNum().equals(bookNum)) {
                return false;
            }
        }
        return true;
    }

//    private boolean selectBooks(String book) {
//        int select = 0;
////        if (book.equals("")) {
////            if (deliveryBookBeanList.size() > 0) {
////                deliveryAllList.clear();
////                deliveryAllList.addAll(deliveryAllSaveList);
////                deliveryInputAllAdapter.notifyDataSetChanged();
////            }
////            srlReceiving.setEnableLoadmore(false);
////            return true;
////        }
//        for (int i = 0; i < deliveryBookBeanList.size(); i++) {
//            if (deliveryBookBeanList.get(i).getBookNum().indexOf(book) != -1) {
//                if (select == 0) {
//                    deliveryAllList.clear();
//                }
//                deliveryAllList.add(deliveryAllSaveList.get(i));
//                select++;
//            }
//        }
//        if (select != 0) {
//            deliveryInputAllAdapter.notifyDataSetChanged();
//        } else {
////            ToastUtils.showShort(getString(R.string.does_not_exist));
//            return false;
//        }
//        srlReceiving.setEnableLoadmore(false);
//        return true;
//    }

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
                        deliveryInputSelectList.clear();
//                        ToastUtils.showShort("response:" + response.body());
                        try {
//                            if (selectType == 2){
//                            srlReceiving.setEnableLoadmore(false);
////                            }
//                            srlReceiving.finishLoadmore();
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                if (jsonArray.length() > 0) {
                                    Type listType = new TypeToken<List<DeliveryBookBean>>() {
                                    }.getType();
                                    List<DeliveryBookBean> deliveryBookBeanLists = new ArrayList<DeliveryBookBean>();
                                    deliveryBookBeanLists = new Gson().fromJson(jsonArray.toString(), listType);
//                                    if (qrType.equals("2")){
//                                        if (deliveryBookBeanLists.size() < 2) {
//                                            for (int i = 0; i < deliveryBookBeanLists.size(); i++) {
//                                                deliveryBookBeanLists.get(i).setBookType(true);
//                                            }
//                                        }
                                    deliveryInputSelectList.addAll(deliveryBookBeanLists);
//                                        deliveryInputSelectAdapter.notifyDataSetChanged();
//                                        relDeliveryInput.scrollToPosition(deliverySelectList.size() - 1);
//                                        tvDeliveryAgentNumber.setText(deliverySelectList.size() + " / " + ticketNum);
//                                    }
//                                    deliveryAllList.clear();
//                                    deliverySelectSaveList.addAll(deliveryBookBeanLists);
//                                    deliveryAllSaveList.addAll(deliveryBookBeanLists);
//                                    deliveryAllList.addAll(deliveryBookBeanLists);
                                    deliveryInputSelectAdapter.notifyDataSetChanged();
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

    private void notHide(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(DeliveryAgentDetailsActivity.this
                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void changeSpinner(int tick) {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, nameList);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        if (role.equals("dls")) {
//            spDeliveryThisNumber.setAdapter(adapter);
//            settlementNewestAdapter = new SettlementAgentNewestAdapter(this);
            deliveryInputAllAdapter = new DeliveryAgentInputsAdapter(this, "1");
            deliveryInputSelectAdapter = new DeliveryAgentInputsAdapter(this, "3");
            deliveryInputSelectAdapter.setList(deliveryInputSelectList);
            deliveryInputAllAdapter.setList(deliveryBookBeanList);
            relDeliveryDetails.setAdapter(deliveryInputAllAdapter);
            deliveryInputPopAdapter = new DeliveryAgentInputsAdapter(this, "2");
            deliveryInputPopAdapter.setList(deliverySelectList);
            relDeliveryPop.setAdapter(deliveryInputPopAdapter);
            relDeliverySelect.setAdapter(deliveryInputSelectAdapter);
//            settlementNewestAdapter.setList(deliveryBookBeanList);
//            relDeliveryDetails.setAdapter(settlementNewestAdapter);
//            settlementNewestAdapter.setOnItemClickListener(new SettlementNewestAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//
//                    tvDeliveryThisNumber.setText(showNumber() + "");
//                }
//            });
//            tvDeliveryThisNumber.setText(tick + "");
//            imgDeliveryThisNumber.setVisibility(View.INVISIBLE);
        } else if (role.equals("gly")) {
            spDeliveryBoxNumber.setAdapter(adapter);
            imgDeliveryThisNumber.setVisibility(View.VISIBLE);

            spDeliveryBoxNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tvDeliveryStock.setText(deliveryBoxBeanList.get(position).getSurplus());
                    tvDeliveryTickets.setText(Integer.parseInt(deliveryBoxBeanList.get(position).getTicketPrice()) + getString(R.string.price_unit));
                    tvDeliveryUnitFare.setText(Integer.parseInt(deliveryBoxBeanList.get(position).getSheetsNum()) + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            tvDeliveryThisNumber.setText("0");
        }
    }

    private void getHttpInfo(String gameAlias, String orderCode) {
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
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                                nameList.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                int tick = 0;
                                if (jsonArray.length() > 0) {
                                    if (role.equals("dls")) {
                                        bookJson = jsonArray.toString();
                                        Type listType = new TypeToken<List<DeliveryBookBean>>() {
                                        }.getType();
                                        deliveryBookBeanList = new Gson().fromJson(jsonArray.toString(), listType);
                                        tick = Integer.parseInt(recordInfoBean.getTicketNum());
                                        for (int i = 0; i < deliveryBookBeanList.size(); i++) {
                                            if (i < tick){
                                                deliveryBookBeanList.get(i).setType(true);
                                            }else {
                                                deliveryBookBeanList.get(i).setType(false);
                                            }
                                            nameList.add(deliveryBookBeanList.get(i).getBookNum());
                                        }
                                    } else if (role.equals("gly")) {
                                        tick = 0;
                                        Type listType = new TypeToken<List<DeliveryBoxBean>>() {
                                        }.getType();
                                        deliveryBoxBeanList = new Gson().fromJson(jsonArray.toString(), listType);
                                        for (int i = 0; i < deliveryBoxBeanList.size(); i++) {
                                            nameList.add(deliveryBoxBeanList.get(i).getCartonNo());
                                        }
                                    }
                                }
                                changeSpinner(tick);
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
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

    private void getAgentHttpInfo(String gameAlias, String orderCode, String bookNum) {
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
//                            if (selectType == 2){
//                                srlReceiving.setEnableLoadmore(false);
//                            }
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
//                            pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
                            if (jsonObject.getString("code").equals("00000")) {
//                                if (pageNo == 1){
//                                    deliveryAllSaveList.clear();
//                                    deliveryAllList.clear();
//                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("getList");
                                if (jsonArray.length() > 0) {
                                    bookJson = jsonArray.toString();
                                    Type listType = new TypeToken<List<DeliveryBookBean>>() {
                                    }.getType();
                                    List<DeliveryBookBean> deliverySave = new ArrayList<DeliveryBookBean>();
                                    deliverySave = new Gson().fromJson(jsonArray.toString(), listType);
                                    deliveryBookBeanList.addAll(deliverySave);
//                                    Iterator<DeliveryBookBean> iterator = deliveryBookBeanList.iterator();
//                                    while (iterator.hasNext()) {
//                                        DeliveryBookBean value = iterator.next();
//                                        for (int j = 0; j < deliverySelectSaveList.size(); j++) {
//                                            if (deliverySelectSaveList.get(j).getSchemeNum().equals(value.getSchemeNum())&&deliverySelectSaveList.get(j).getBookNum().equals(value.getBookNum())){
//                                                iterator.remove();
//                                            }
//                                        }
//                                    }
////                                    if (pageNo == 1) {
//                                    for (int i = 0; i < deliverySelectList.size(); i++) {
//                                        for (int j = 0; j < deliveryBookBeanList.size(); j++) {
//                                            if (deliveryBookBeanList.get(j).getSchemeNum().equals(deliverySelectList.get(i).getSchemeNum()) && deliveryBookBeanList.get(j).getBookNum().equals(deliverySelectList.get(i).getBookNum())) {
//                                                deliveryBookBeanList.get(j).setBookType(true);
//                                            }
//                                        }
//                                    }
//                                    }
//                                    deliveryAllSaveList.addAll(deliveryBookBeanList);
//                                    deliveryAllList.addAll(deliveryBookBeanList);
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

    private void submitDelivery() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String accountId = SPUtils.look(this, SPkey.accountId);
        String terminalName = SPUtils.look(this, SPkey.terminalname);
        String terminalId = SPUtils.look(this, SPkey.terminalId);
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetLogisticsDelivery.DeliveryInfo deliveryInfo = new pos_GetLogisticsDelivery.DeliveryInfo();
        if (role.equals("dls")) {
            deliveryInfo.setGameAlias(gameAlias);
            deliveryInfo.setOrderCode(orderCode);
            deliveryInfo.setTicketNum(recordInfoBean.getTicketNum());
            deliveryInfo.setSendDeviceId(terminalId);
            deliveryInfo.setSendDevice(terminalName);
            deliveryInfo.setSendPersonId(accountId);
            deliveryInfo.setSendPerson(account_name);
            List<pos_GetLogisticsDelivery.BookNoListInfo> bookList = new ArrayList<pos_GetLogisticsDelivery.BookNoListInfo>();
            for (int i = 0; i < deliverySelectList.size(); i++) {
                pos_GetLogisticsDelivery.BookNoListInfo bookNoListInfo = new pos_GetLogisticsDelivery.BookNoListInfo();
                bookNoListInfo.setBookNo(deliverySelectList.get(i).getBookNum());
                bookNoListInfo.setLogisticsCode(deliverySelectList.get(i).getLogisticsCode());
                bookNoListInfo.setSchemeNum(deliverySelectList.get(i).getSchemeNum());
                bookNoListInfo.setTicketNo(deliverySelectList.get(i).getTicketNo());
                bookNoListInfo.setBookId(deliverySelectList.get(i).getBookId());
                bookNoListInfo.setCartonNo(deliverySelectList.get(i).getCartonNo());
                bookNoListInfo.setCartonNoId(deliverySelectList.get(i).getCartonNoId());
                bookNoListInfo.setSheetsNum(deliverySelectList.get(i).getSheetsNum());
                bookList.add(bookNoListInfo);
            }
            if (bookList.size() < 1) {
                ProgressUtil.dismissProgressDialog();
                ToastUtils.showShort(getString(R.string.please_choose_number));
                ProgressUtil.dismissProgressDialog();
                return;
            } else if (bookList.size() != Integer.parseInt(ticketNum)){
                ToastUtils.showShort(getString(R.string.this_number_is_inconsistent));
                ProgressUtil.dismissProgressDialog();
                return;
            }
            deliveryInfo.setBookNoList(bookList);
        } else {
            deliveryInfo.setGameAlias(gameAlias);
            deliveryInfo.setOrderCode(orderCode);
            deliveryInfo.setTicketNum(recordInfoBean.getTicketNum());
            deliveryInfo.setSendDeviceId(terminalId);
            deliveryInfo.setSendDevice(terminalName);
            deliveryInfo.setSendPersonId(accountId);
            deliveryInfo.setSendPerson(account_name);
//            deliveryInfo.setCartonNo(deliveryBoxBeanList.get(spDeliveryBoxNumber.getSelectedItemPosition()).getCartonNo());
//            deliveryInfo.setSheetsNum(deliveryBoxBeanList.get(spDeliveryBoxNumber.getSelectedItemPosition()).getSheetsNum());
            List<pos_GetLogisticsDelivery.BookNoListInfo> bookList = new ArrayList<pos_GetLogisticsDelivery.BookNoListInfo>();
            for (int i = 0; i < deliveryBookBeanList.size(); i++) {
                if (deliveryBookBeanList.get(i).isType()) {
                    pos_GetLogisticsDelivery.BookNoListInfo bookNoListInfo = new pos_GetLogisticsDelivery.BookNoListInfo();
                    if (deliveryBookBeanList.get(i).getCartonType().equals("")) {
                        bookNoListInfo.setBookNo(deliveryBookBeanList.get(i).getBookNum());
                        bookNoListInfo.setLogisticsCode(deliveryBookBeanList.get(i).getLogisticsCode());
                        bookNoListInfo.setSchemeNum(deliveryBookBeanList.get(i).getSchemeNum());
                        bookNoListInfo.setTicketNo(deliveryBookBeanList.get(i).getTicketNo());
                        bookNoListInfo.setCartonNo(deliveryBookBeanList.get(i).getCartonNo());
                        bookNoListInfo.setCartonNoId(deliveryBookBeanList.get(i).getCartonNoId());
                        bookNoListInfo.setSheetsNum(deliveryBookBeanList.get(i).getSheetsNum());
                        bookNoListInfo.setBookId("");
                        bookList.add(bookNoListInfo);
                    }else {
                        bookList.addAll(showListCarton(deliveryBookBeanList.get(i)));
//                        bookNoListInfo.setBookNo(deliveryBookBeanList.get(i).getBookNum());
//                        bookNoListInfo.setLogisticsCode(deliveryBookBeanList.get(i).getLogisticsCode());
//                        bookNoListInfo.setSchemeNum(deliveryBookBeanList.get(i).getSchemeNum());
//                        bookNoListInfo.setTicketNo(deliveryBookBeanList.get(i).getTicketNo());
//                        bookNoListInfo.setCartonNo(deliveryBookBeanList.get(i).getCartonNo());
//                        bookNoListInfo.setCartonNoId(deliveryBookBeanList.get(i).getCartonNoId());
//                        bookNoListInfo.setSheetsNum(deliveryBookBeanList.get(i).getSheetsNum());
//                        bookNoListInfo.setBookId("");
                    }
                }
            }
            if (bookList.size() < 1) {
                ProgressUtil.dismissProgressDialog();
                ToastUtils.showShort(getString(R.string.please_choose_number));
                ProgressUtil.dismissProgressDialog();
                return;
            }else if (bookList.size() != Integer.parseInt(ticketNum)){
                ToastUtils.showShort(getString(R.string.this_number_is_inconsistent));
                ProgressUtil.dismissProgressDialog();
                return;
            }
            deliveryInfo.setBookNoList(bookList);
        }
//        deliveryInfo.setBookNoList();
        pos_GetLogisticsDelivery.DataBean dataBean = new pos_GetLogisticsDelivery.DataBean(deliveryInfo);
        pos_GetLogisticsDelivery pos_getRecordInfo = new pos_GetLogisticsDelivery(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getRecordInfo);
        OkGo.<String>post(MyUrl.pos_GetLogisticsDelivery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            ToastUtils.showShort(jsonObject.getString("message"));
                            if (jsonObject.getString("code").equals("00000")) {
                                finish();
                                DeliveryRecordsActivity.instance.showData();
                            }
//                            else {
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }

    private List<pos_GetLogisticsDelivery.BookNoListInfo> showListCarton(DeliveryBookBean deliveryBookBean){
        List<pos_GetLogisticsDelivery.BookNoListInfo> list = new ArrayList<pos_GetLogisticsDelivery.BookNoListInfo>();
        String[] book = deliveryBookBean.getBookNum().split("-");
        int startNum = Integer.parseInt(book[0]);
        int num = Integer.parseInt(book[1]) + 1;
        for (int i = startNum; i < num; i++) {
            pos_GetLogisticsDelivery.BookNoListInfo bookNoListInfo = new pos_GetLogisticsDelivery.BookNoListInfo();
            int bookNo = Integer.parseInt(book[0]) + i;
            String bookNum = bookNo + "";
            int bookLength = 7 - bookNum.length();
            if (bookNum.length() < 7){
                for (int j = 0; j < bookLength; j++) {
                    bookNum = "0" + bookNum;
                }
            }
            bookNoListInfo.setBookNo(bookNum);
            bookNoListInfo.setLogisticsCode("");
            bookNoListInfo.setSchemeNum(deliveryBookBean.getSchemeNum());
            bookNoListInfo.setTicketNo("");
            bookNoListInfo.setCartonNo(deliveryBookBean.getCartonNo());
            bookNoListInfo.setCartonNoId(deliveryBookBean.getCartonNoId());
            bookNoListInfo.setSheetsNum(deliveryBookBean.getSheetsNum());
            bookNoListInfo.setBookId("");
            list.add(bookNoListInfo);
        }
        return list;
    }

    private void showRoleView() {
        if (role.equals("dls")) {
            llyDeliveryThisNumber.setVisibility(View.GONE);
        } else if (role.equals("gly")) {
//            llyDeliveryBoxNumber.setVisibility(View.VISIBLE);
            llyDeliveryThisNumber.setVisibility(View.VISIBLE);
//            llyDeliveryStock.setVisibility(View.VISIBLE);
//            llyDeliveryTickets.setVisibility(View.VISIBLE);
//            llyDeliveryUnitFare.setVisibility(View.VISIBLE);
        }
        tvDeliveryThisNumber.setText("0");
        tvDeliveryName.setText(recordInfoBean.getGameName());
        tvDeliveryTicketNumber.setText(recordInfoBean.getOrderCode());
        tvDeliveryNumber.setText(recordInfoBean.getTicketNum());
//        tvDeliveryPayMode.setText(recordInfoBean.get());
        ticketNum = recordInfoBean.getTicketNum();
        if (recordInfoBean.getGetChannels().equals("0")) {
            tvDeliveryChannel.setText(getString(R.string.terminal));
        } else if (recordInfoBean.getGetChannels().equals("1")) {
            tvDeliveryChannel.setText(getString(R.string.third_party_app));
        } else if (recordInfoBean.getGetChannels().equals("2")) {
            tvDeliveryChannel.setText(getString(R.string.block_chain));
        } else if (recordInfoBean.getGetChannels().equals("3")) {
            tvDeliveryChannel.setText(getString(R.string.mobile_end));
        } else {
            tvDeliveryPayStatus.setText("--");
        }
        if (recordInfoBean.getPayState().equals("00")) {
            tvDeliveryPayStatus.setText(getString(R.string.successful_payment));
            tvDeliveryPayStatus.setTextColor(Constant.success);
        } else if (recordInfoBean.getPayState().equals("01")) {
            tvDeliveryPayStatus.setText(getString(R.string.failure_to_pay));
            tvDeliveryPayStatus.setTextColor(Constant.chupiao_fail);
        } else if (recordInfoBean.getPayState().equals("02")) {
            tvDeliveryPayStatus.setText(getString(R.string.to_be_paid));
            tvDeliveryPayStatus.setTextColor(Constant.dispatched);
        } else {
            tvDeliveryPayStatus.setText("--");
        }
    }

    public void showNumber() {
        int number = 0;
        for (int i = 0; i < deliveryBookBeanList.size(); i++) {
            if (deliveryBookBeanList.get(i).isType()) {
                number++;
            }
        }
        tvDeliveryThisNumber.setText(number + "");
    }

    public void showList(List<DeliveryBookBean> deliveryBookBeanList,int num) {
        this.deliveryBookBeanList = deliveryBookBeanList;
        tvDeliveryThisNumber.setText(num + "");
    }

    public void addSelectList(String type, int pos) {
        if (type.equals("1")){
            if (deliveryBookBeanList.get(pos).isBookType()){
                deliveryBookBeanList.get(pos).setBookType(false);
                for (int i = 0; i < deliverySelectList.size(); i++) {
                    if (deliveryBookBeanList.get(pos).getBookNum().equals(deliverySelectList.get(i).getBookNum())){
                        deliverySelectList.remove(i);
                    }
                }
            }else {
                if (Integer.parseInt(ticketNum) == deliverySelectList.size()){
                    ToastUtils.showShort(getString(R.string.this_number_is_inconsistent));
                    return;
                }
                deliveryBookBeanList.get(pos).setBookType(true);
//                DeliveryBookBean deliveryBookBean = new DeliveryBookBean(deliveryBookBeanList.get(pos));
//                List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<>();
//                deliveryBookBeanList.add(deliveryBookBeanList.get(pos));
                try {
                    deliverySelectList.add(deliveryBookBeanList.get(pos).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            deliveryInputAllAdapter.notifyDataSetChanged();
            deliveryInputPopAdapter.notifyDataSetChanged();
            btnDeliveryDetailsSelect.setText(getString(R.string.have_chosen) + "(" + deliverySelectList.size() + "/" + ticketNum + ")");
        }else if (type.equals("2")){
            if (deliverySelectList.get(pos).isBookType()){
                deliverySelectList.get(pos).setBookType(false);
            }else {
                deliverySelectList.get(pos).setBookType(true);
            }
            deliveryInputPopAdapter.notifyDataSetChanged();
        }else {
            if (deliveryInputSelectList.get(pos).isBookType()){
                deliveryInputSelectList.get(pos).setBookType(false);
                for (int i = 0; i < deliverySelectList.size(); i++) {
                    if (deliveryInputSelectList.get(pos).getBookNum().equals(deliverySelectList.get(i).getBookNum())){
                        deliverySelectList.remove(i);
                    }
                }
                for (int i = 0; i < deliveryBookBeanList.size(); i++) {
                    if (deliveryInputSelectList.get(pos).getBookNum().equals(deliveryBookBeanList.get(i).getBookNum())){
                        deliveryBookBeanList.get(i).setBookType(false);
                    }
                }
            }else {
                int s = 0;
                if (Integer.parseInt(ticketNum) == deliverySelectList.size()){
                    ToastUtils.showShort(getString(R.string.this_number_is_inconsistent));
                    return;
                }
                for (int i = 0; i < deliveryBookBeanList.size(); i++) {
                    if (deliveryInputSelectList.get(pos).getBookNum().equals(deliveryBookBeanList.get(i).getBookNum())){
                        deliveryBookBeanList.get(i).setBookType(true);
                        s++;
                    }
                }
                if (s == 0){
                    try {
                        deliveryBookBeanList.add(deliveryInputSelectList.get(pos).clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                deliveryInputSelectList.get(pos).setBookType(true);
//                DeliveryBookBean deliveryBookBean = new DeliveryBookBean(deliveryBookBeanList.get(pos));
//                List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<>();
//                deliveryBookBeanList.add(deliveryBookBeanList.get(pos));
                try {
                    deliverySelectList.add(deliveryInputSelectList.get(pos).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            deliveryInputSelectAdapter.notifyDataSetChanged();
            deliveryInputPopAdapter.notifyDataSetChanged();
            deliveryInputAllAdapter.notifyDataSetChanged();
            btnDeliveryDetailsSelect.setText(getString(R.string.have_chosen) + "(" + deliverySelectList.size() + "/" + ticketNum + ")");
        }
    }
//    public void addSelectList(String type, int pos) {
//        if (type.equals("1")){
//            if (deliverySelectList.size() == Integer.parseInt(ticketNum)){
//                if (!deliveryBookBeanList.get(pos).isBookType()) {
//                    ToastUtils.showShort(getString(R.string.this_number_is_inconsistent));
//                    return;
//                }
//            }
//        }
//        if (type.equals("1")) {
//            boolean bookType = false;
//            int dPos = 0;
//            for (int i = 0; i < deliveryBookBeanList.size(); i++) {
//                if (deliveryAllSaveList.get(i).getBookNum().equals(deliveryBookBeanList.get(pos).getBookNum())) {
////                    deliveryAllSaveList.remove(i);
//                    dPos = i;
//                    bookType = deliveryAllSaveList.get(i).isBookType();
//                    deliveryAllSaveList.get(i).setBookType(!bookType);
//                }
//            }
//            if (!bookType){
//                deliverySelectList.add(deliveryAllList.get(pos));
////                deliveryInputSelectAdapter.notifyItemChanged(pos);
////                relDeliveryInput.scrollToPosition(deliverySelectList.size() - 1);
//            }else {
//                int selectPos = -1;
//                for (int i = 0; i < deliverySelectList.size(); i++) {
//                    if (deliverySelectList.get(i).getBookNum().equals(deliveryAllSaveList.get(dPos).getBookNum())&&deliverySelectList.get(i).getSchemeNum().equals(deliveryAllSaveList.get(dPos).getSchemeNum())){
//                        selectPos = i;
//                    }
//                }
//                if (selectPos != -1) {
//                    deliverySelectList.remove(selectPos);
////                    deliveryInputSelectAdapter.notifyDataSetChanged();
//                }
////                deliveryInputSelectAdapter.notifyItemRangeChanged(pos, deliverySelectList.size() - pos);
//            }
//            deliveryAllList.get(pos).setBookType(!bookType);
//            deliveryInputAllAdapter.notifyItemChanged(pos);
////            deliveryInputAllAdapter.notifyItemRemoved(pos);
////            deliveryInputAllAdapter.notifyItemRangeChanged(pos, deliveryAllList.size() - pos);
//
//        } else {
//            int dPos = 0;
//            for (int i = 0; i < deliveryAllSaveList.size(); i++) {
//                if (deliveryAllSaveList.get(i).getBookNum().equals(deliverySelectList.get(pos).getBookNum())) {
////                    deliveryAllSaveList.remove(i);
//                    dPos = i;
//                    if (deliveryAllList.size() < 2){
//                        if (deliveryAllList.size() == 1){
//                            if (deliveryAllList.get(0).getSchemeNum().equals(deliverySelectList.get(pos).getSchemeNum())&&deliveryAllList.get(0).getBookNum().equals(deliverySelectList.get(pos).getBookNum())) {
//                                deliveryAllList.get(0).setBookType(false);
//                                deliveryInputAllAdapter.notifyItemChanged(0);
//                            }
//                        }
//                    }else {
//                        deliveryAllList.get(i).setBookType(false);
//                        deliveryInputAllAdapter.notifyItemChanged(dPos);
//                    }
//                    deliveryAllSaveList.get(i).setBookType(false);
//                }
//            }
////            deliveryAllList.add(deliverySelectList.get(pos));
////            deliveryAllSaveList.add(deliverySelectList.get(pos));
//            deliverySelectList.remove(pos);
////            deliveryInputSelectAdapter.notifyItemRemoved(pos);
////            deliveryInputSelectAdapter.notifyItemRangeChanged(pos, deliverySelectList.size() - pos);
//        }
////        tvDeliveryAgentNumber.setText(deliverySelectList.size() + " / " + ticketNum);
//    }

    public static <E> List<E> deepCopy(List<E> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<E> dest = (List<E>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<E>();
        }
    }

    public void showAdapter(List<DeliveryBookBean> deliverySelectList,List<DeliveryBookBean> deliveryAllSaveList,List<DeliveryBookBean> deliveryAllList){
        selectList.clear();
        deliveryAllSaveLists.clear();
        deliveryAllLists.clear();
        deliveryAllSaveLists.addAll(deliveryAllSaveList);
        deliveryAllLists.addAll(deliveryAllList);
        selectList.addAll(deliverySelectList);
        deliveryInputAllAdapter.setList(selectList);
        relDeliveryDetails.setAdapter(deliveryInputAllAdapter);
        tvDeliveryThisNumber.setText(selectList.size() + "");
        imgDeliveryThisNumber.setVisibility(View.INVISIBLE);
    }

}
