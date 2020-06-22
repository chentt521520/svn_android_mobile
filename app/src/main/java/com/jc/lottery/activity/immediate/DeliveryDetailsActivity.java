package com.jc.lottery.activity.immediate;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
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
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 派送详情页面
 */
public class DeliveryDetailsActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.btn_delivery_details_submit)
    Button btnDeliveryDetailsSubmit;
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
    private RecordInfoBean recordInfoBean = new RecordInfoBean();
    private String gameAlias = "";
    private String orderCode = "";
    private String role = ""; //角色
    private List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<DeliveryBookBean>();
    private List<DeliveryBoxBean> deliveryBoxBeanList = new ArrayList<DeliveryBoxBean>();
    private ArrayAdapter adapter;
    private List<String> nameList = new ArrayList<String>();
    private String bookJson = "";
    private SettlementNewestAdapter settlementNewestAdapter;
    public static DeliveryDetailsActivity instance = null;
    private String ticketNum = "";
    private ArrayList<DeliveryBookBean> selectList = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliveryAllLists = new ArrayList<DeliveryBookBean>();
    private ArrayList<DeliveryBookBean> deliveryAllSaveLists = new ArrayList<DeliveryBookBean>();
    private int pageNo = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery_details;
    }

    @Override
    public void getPreIntent() {
        recordInfoBean = (RecordInfoBean) getIntent().getSerializableExtra("recordInfoBean");
        gameAlias = getIntent().getStringExtra("gameAlias");
        orderCode = getIntent().getStringExtra("orderCode");
    }

    @Override
    public void initView() {
        instance = this;
        relDeliveryDetails.setLayoutManager(new GridLayoutManager(this, 3));
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

    @OnClick({R.id.lly_manual_scanner_back, R.id.btn_delivery_details_submit, R.id.lly_delivery_this_number,R.id.btn_delivery_details_next})
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
                    if (selectList.size() > 0){
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
        }
    }

    public void changeSpinner(int tick) {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, nameList);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        if (role.equals("dls")) {
//            spDeliveryThisNumber.setAdapter(adapter);
            settlementNewestAdapter = new SettlementNewestAdapter(this);
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
            for (int i = 0; i < selectList.size(); i++) {
                pos_GetLogisticsDelivery.BookNoListInfo bookNoListInfo = new pos_GetLogisticsDelivery.BookNoListInfo();
                bookNoListInfo.setBookNo(selectList.get(i).getBookNum());
                bookNoListInfo.setLogisticsCode(selectList.get(i).getLogisticsCode());
                bookNoListInfo.setSchemeNum(selectList.get(i).getSchemeNum());
                bookNoListInfo.setTicketNo(selectList.get(i).getTicketNo());
                bookNoListInfo.setBookId(selectList.get(i).getBookId());
                bookNoListInfo.setCartonNo(selectList.get(i).getCartonNo());
                bookNoListInfo.setCartonNoId(selectList.get(i).getCartonNoId());
                bookNoListInfo.setSheetsNum(selectList.get(i).getSheetsNum());
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
            llyDeliveryThisNumber.setVisibility(View.VISIBLE);
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

    public void showAdapter(List<DeliveryBookBean> deliverySelectList,List<DeliveryBookBean> deliveryAllSaveList,List<DeliveryBookBean> deliveryAllList){
        selectList.clear();
        deliveryAllSaveLists.clear();
        deliveryAllLists.clear();
        deliveryAllSaveLists.addAll(deliveryAllSaveList);
        deliveryAllLists.addAll(deliveryAllList);
        selectList.addAll(deliverySelectList);
        settlementNewestAdapter.setList(selectList);
        relDeliveryDetails.setAdapter(settlementNewestAdapter);
        tvDeliveryThisNumber.setText(selectList.size() + "");
        imgDeliveryThisNumber.setVisibility(View.INVISIBLE);
    }

}
