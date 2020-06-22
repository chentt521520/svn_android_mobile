package com.jc.lottery.activity.immediate;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.RewardScannerActivity;
import com.jc.lottery.adapter.DeliveryInputAdapter;
import com.jc.lottery.adapter.DeliveryInputCartAdapter;
import com.jc.lottery.adapter.RecyclerViewAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.CartNoBookList;
import com.jc.lottery.bean.DeliveryBookList;
import com.jc.lottery.bean.req.pos_GetLogisticsDelivery;
import com.jc.lottery.bean.req.pos_GetQueryBookNo;
import com.jc.lottery.bean.resp.DeliveryBookBean;
import com.jc.lottery.content.HttpContext;
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
public class DeliveryInputActivity extends BaseActivity {

    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.btn_delivery_input_one)
    Button btnDeliveryInputOne;
    @BindView(R.id.btn_delivery_input_two)
    Button btnDeliveryInputTwo;
    @BindView(R.id.rel_delivery_input)
    RecyclerView relDeliveryInput;
    @BindView(R.id.lly_delivery_qr)
    LinearLayout llyDeliveryQr;
    @BindView(R.id.tv_delivery_input_number)
    TextView tvDeliveryInputNumber;
    @BindView(R.id.tv_delivery_input_num)
    TextView tvDeliveryInputNum;
    @BindView(R.id.lly_delivery_nodata)
    PercentLinearLayout llyDeliveryNodata;
    @BindView(R.id.rel_delivery_book)
    RecyclerView relDeliveryBook;
    @BindView(R.id.lly_my_immediate_pop)
    LinearLayout llyMyImmediatePop;
    @BindView(R.id.bt_my_immediate_pop_no)
    Button btMyImmediatePopNo;
    //    private List<String> list = new ArrayList<String>();
    private List<DeliveryBookBean> deliveryBookBeanList = new ArrayList<DeliveryBookBean>();
    private List<DeliveryBookBean> deliveryBookBeanSaveList = new ArrayList<DeliveryBookBean>();
    private DeliveryInputAdapter deliveryInputAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DeliveryInputCartAdapter deliveryInputCartAdapter;
    private String ticketNum = "";
    private String gameAlias = "";
    private List<CartNoBookList> cartList = new ArrayList<CartNoBookList>();
    private List<pos_GetLogisticsDelivery.BookNoListInfo> list = new ArrayList<pos_GetLogisticsDelivery.BookNoListInfo>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_delivery_input;
    }

    @Override
    public void getPreIntent() {
        gameAlias = getIntent().getStringExtra("gameAlias");
        ticketNum = getIntent().getStringExtra("ticketNum");
        String bookJson = getIntent().getStringExtra("bookJson");
        Type listType = new TypeToken<List<DeliveryBookBean>>() {
        }.getType();
        deliveryBookBeanSaveList = new Gson().fromJson(bookJson, listType);
//        deliveryBookBeanList = new Gson().fromJson(bookJson, listType);
        if (deliveryBookBeanSaveList.size() > 0) {
            showInitDelivery(deliveryBookBeanSaveList);
            showNestList();
        }
        tvDeliveryInputNumber.setText(showNumBook() + "/" + ticketNum);
    }

    private void showInitDelivery(List<DeliveryBookBean> deliveryBookBean){
        for (int i = 0; i < deliveryBookBean.size(); i++) {
            if (deliveryBookBean.get(i).getCartonType().equals("1")){
                cartList.add(showListCarton(deliveryBookBean.get(i)));
            }
        }
    }

    @Override
    public void initView() {
        relDeliveryBook.setLayoutManager(new GridLayoutManager(this, 3));
        relDeliveryInput.setLayoutManager(new GridLayoutManager(this, 2));
        if (deliveryBookBeanList.size() < 1) {
            llyDeliveryNodata.setVisibility(View.VISIBLE);
            relDeliveryInput.setVisibility(View.GONE);
        } else {
            llyDeliveryNodata.setVisibility(View.GONE);
            relDeliveryInput.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
//        showView();
//        DataSetObserver dataSetObserver = new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                // like the following line
////                someButton.setEnabled(listAdapter.getCount() > 0);
//            }
//        };
        RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                tvDeliveryInputNumber.setText(showNumBook() + "/" + ticketNum);
                if (deliveryBookBeanList.size() < 1) {
                    llyDeliveryNodata.setVisibility(View.VISIBLE);
                    relDeliveryInput.setVisibility(View.GONE);
                } else {
                    llyDeliveryNodata.setVisibility(View.GONE);
                    relDeliveryInput.setVisibility(View.VISIBLE);
                }
            }
        };
//        deliveryInputAdapter = new DeliveryInputAdapter(this);
//        deliveryInputAdapter.registerAdapterDataObserver(dataObserver);
//        deliveryInputAdapter.setList(deliveryBookBeanList);
//        relDeliveryInput.setAdapter(deliveryInputAdapter);recyclerViewAdapter
//        recyclerViewAdapter = new RecyclerViewAdapter(this,deliveryBookBeanList,2);
        recyclerViewAdapter.registerAdapterDataObserver(dataObserver);
//        recyclerViewAdapter.setList(deliveryBookBeanList);
        relDeliveryInput.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_reward_record_back, R.id.btn_delivery_input_one, R.id.btn_delivery_input_two, R.id.lly_delivery_qr, R.id.lly_my_immediate_pop,R.id.bt_my_immediate_pop_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.btn_delivery_input_one:
                finish();
                break;
            case R.id.btn_delivery_input_two:
                if (deliveryBookBeanSaveList.size() > 0) {
                    showSaveList();
                    DeliveryDetailsActivity.instance.showList(deliveryBookBeanSaveList,showNumBook());
                    finish();
                } else {
                    ToastUtils.showShort(getString(R.string.please_scan_if_the_input_is_not_scanned));
                }
                break;
            case R.id.lly_delivery_qr:
                if (showNumBook() < Integer.parseInt(ticketNum)) {
                    Intent intent = new Intent();
                    intent.setClass(this, RewardScannerActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(getString(R.string.up_to) + ticketNum + getString(R.string.you_have_entered_the_upper_limit));
                }
                break;
            case R.id.lly_my_immediate_pop:
                llyMyImmediatePop.setVisibility(View.GONE);
                break;
            case R.id.bt_my_immediate_pop_no:
                llyMyImmediatePop.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!HttpContext.getCodeQr().equals("")) {
            if (HttpContext.getCodeQr().length() == 20) {
                String codeQr = HttpContext.getCodeQr();
//            etManualScannerOne.setText(codeQr);
                String schemeCode = "";
                DeliveryBookBean deliveryBookBean = new DeliveryBookBean();
                deliveryBookBean.setBookNum(codeQr.substring(10, 17));
                deliveryBookBean.setTicketNo(codeQr.substring(17, 20));
                deliveryBookBean.setSchemeNum(codeQr.substring(5, 10));
                deliveryBookBean.setCartonNo("");
                schemeCode = codeQr.substring(0, 5);
                deliveryBookBean.setLogisticsCode(codeQr);
                deliveryBookBean.setCartonType("");
                if (getBookNo(deliveryBookBean.getSchemeNum(),deliveryBookBean.getBookNum())) {
                    getQueryBookNo(schemeCode, deliveryBookBean,"","");
                } else {
                    ToastUtils.showShort(getString(R.string.please_scan_the_same_number_information));
                }
            } else if (HttpContext.getCodeQr().length() == 63) {
                String codeQr = HttpContext.getCodeQr();
//            etManualScannerOne.setText(codeQr);
                String schemeCode = "";
                DeliveryBookBean deliveryBookBean = new DeliveryBookBean();
                int book = Integer.parseInt(codeQr.substring(46, 53));
                int num = Integer.parseInt(codeQr.substring(15, 18));
                if (showNumBook() + Integer.parseInt(codeQr.substring(15, 18)) > Integer.parseInt(ticketNum)){
                    ToastUtils.showShort(getString(R.string.up_to) + ticketNum + getString(R.string.you_have_entered_the_upper_limit));
                    return;
                }
                book = book + num - 1;
                String bookNum = book + "";
                int bookLength = 7 - bookNum.length();
                if (bookNum.length() < 7){
                    for (int j = 0; j < bookLength; j++) {
                        bookNum = "0" + bookNum;
                    }
                }
                deliveryBookBean.setBookNum(codeQr.substring(46, 53) + "-" + bookNum);
                deliveryBookBean.setTicketNo("");
                deliveryBookBean.setSchemeNum(codeQr.substring(5, 10));
                deliveryBookBean.setCartonNo(codeQr.substring(10, 15));
                schemeCode = codeQr.substring(0, 5);
                deliveryBookBean.setLogisticsCode("");
                deliveryBookBean.setCartonType("1");
                String endNum = bookNum + "";
                int bookLengths = 7 - endNum.length();
                if (endNum.length() < 7) {
                    for (int j = 0; j < bookLengths; j++) {
                        endNum = "0" + endNum;
                    }
                }
                if (getCartNo(deliveryBookBean.getCartonNo(),deliveryBookBean.getSchemeNum())) {
                    getQueryBookNo(schemeCode, deliveryBookBean,codeQr.substring(46, 53),endNum);
                } else {
                    ToastUtils.showShort(getString(R.string.please_scan_the_same_number_information));
                }
            } else {
                ToastUtils.showShort(getString(R.string.please_scan_the_correct_logistics_code));
            }
        } else if (!HttpContext.getCodeQr().equals("") && HttpContext.getCodeQr().length() != 20 && HttpContext.getCodeQr().length() != 63) {
            ToastUtils.showShort(getString(R.string.please_scan_the_correct_logistics_code));
        }
        HttpContext.setCodeQr("");
    }

    private boolean getBookNo(String schemeNum,String bookNum) {
        for (int i = 0; i < deliveryBookBeanSaveList.size(); i++) {
            if (deliveryBookBeanSaveList.get(i).getSchemeNum().equals(schemeNum)&&deliveryBookBeanSaveList.get(i).getBookNum().equals(bookNum)) {
                return false;
            }
        }
        for (int i = 0; i < cartList.size(); i++) {
            for (int j = 0; j < cartList.get(i).getList().size(); j++) {
                pos_GetLogisticsDelivery.BookNoListInfo bookNoListInfo = cartList.get(i).getList().get(j);
                if (bookNoListInfo.getSchemeNum().equals(schemeNum)&&bookNoListInfo.getBookNo().equals(bookNum)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean getCartNo(String cart,String schemeNum) {
        for (int i = 0; i < deliveryBookBeanSaveList.size(); i++) {
            if (deliveryBookBeanSaveList.get(i).getSchemeNum().equals(schemeNum)&&deliveryBookBeanSaveList.get(i).getCartonNo().equals(cart)) {
                return false;
            }
        }
        return true;
    }

    private CartNoBookList showListCarton(DeliveryBookBean deliveryBookBean) {
        List<pos_GetLogisticsDelivery.BookNoListInfo> list = new ArrayList<pos_GetLogisticsDelivery.BookNoListInfo>();
        String[] book = deliveryBookBean.getBookNum().split("-");
        int numStart = Integer.parseInt(book[0]);
        int numEnd = Integer.parseInt(book[1]) + 1;
        int pos = 0;
        for (int i = numStart; i < numEnd; i++) {
            pos_GetLogisticsDelivery.BookNoListInfo bookNoListInfo = new pos_GetLogisticsDelivery.BookNoListInfo();
            int bookNo = Integer.parseInt(book[0]) + pos;
            String bookNum = bookNo + "";
            int bookLength = 7 - bookNum.length();
            if (bookNum.length() < 7) {
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
            pos++;
        }
        CartNoBookList cartNoBookList = new CartNoBookList();
        cartNoBookList.setCartNo(deliveryBookBean.getCartonNo());
        cartNoBookList.setList(list);
        return cartNoBookList;
    }

    private void getQueryBookNo(String schemeCode, final DeliveryBookBean deliveryBookBean,String bookStart,String bookEnd) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetQueryBookNo.BookInfo bookInfo = new pos_GetQueryBookNo.BookInfo(gameAlias, schemeCode, deliveryBookBean.getSchemeNum(), deliveryBookBean.getBookNum(), deliveryBookBean.getCartonNo(),bookStart,bookEnd);
        pos_GetQueryBookNo.DataBean dataBean = new pos_GetQueryBookNo.DataBean(bookInfo);
        pos_GetQueryBookNo pos_getQueryBookNo = new pos_GetQueryBookNo(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getQueryBookNo);
        OkGo.<String>post(MyUrl.pos_GetQueryBookNo)
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
//                                DeliveryBookBean deliveryBookTitle = new DeliveryBookBean();
//                                deliveryBookTitle.setTitleNot("true");
//                                deliveryBookTitle.setBookNum(deliveryBookBean.getSchemeNum());
//                                deliveryBookBeanList.add(deliveryBookTitle);
                                deliveryBookBean.setCartonNo(jsonObject.getString("cartonNo"));
                                deliveryBookBean.setCartonNoId(jsonObject.getString("cartonNoId"));
                                deliveryBookBean.setSheetsNum(jsonObject.getString("sheetsNum"));
                                if (deliveryBookBean.getCartonType().equals("1")){
                                    cartList.add(showListCarton(deliveryBookBean));
                                }
                                deliveryBookBeanSaveList.add(deliveryBookBean);
                                showNestList();
//                                deliveryInputAdapter.notifyDataSetChanged();
                                recyclerViewAdapter.notifyDataSetChanged();
                                relDeliveryInput.scrollToPosition(deliveryBookBeanList.size() - 1);
//                                changeSpinner(spReceivingSelectName, nameList);
                            } else {
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

    public void showPopBook(String cart) {
        list.clear();
        for (int i = 0; i < cartList.size(); i++) {
            if (cart.equals(cartList.get(i).getCartNo())) {
                list.addAll(cartList.get(i).getList());
                tvDeliveryInputNum.setText(cart);
                llyMyImmediatePop.setVisibility(View.VISIBLE);
                deliveryInputCartAdapter = new DeliveryInputCartAdapter(this);
                deliveryInputCartAdapter.setList(list);
                relDeliveryBook.setAdapter(deliveryInputCartAdapter);
                return;
            }
        }
    }

    private void showNestList(){
        List<DeliveryBookList> cartNoBookList = new ArrayList<DeliveryBookList>();
        for (int i = 0; i < deliveryBookBeanSaveList.size(); i++) {
            DeliveryBookList cartNoBook = new DeliveryBookList();
            String s = deliveryBookBeanSaveList.get(i).getSchemeNum();
            cartNoBook.setCartNo(s);
            List<DeliveryBookBean> list = new ArrayList<DeliveryBookBean>();
            for (int j = 0; j < deliveryBookBeanSaveList.size(); j++) {
                int num = 0;
                for (int k = 0; k < cartNoBookList.size(); k++) {
                    if (cartNoBookList.get(k).getCartNo().equals(s)){
                        num++;
                    }
                }
                if (num == 0){
                    if (s.equals(deliveryBookBeanSaveList.get(j).getSchemeNum())){
                        list.add(deliveryBookBeanSaveList.get(j));
                    }
                }
            }
            if (list.size() > 0) {
                cartNoBook.setList(list);
                cartNoBookList.add(cartNoBook);
            }
        }
        deliveryBookBeanList.clear();
        deliveryBookBeanSaveList.clear();
        for (int i = 0; i < cartNoBookList.size(); i++) {
            DeliveryBookBean deliveryBookBean = new DeliveryBookBean();
            deliveryBookBean.setTitleNot("true");
            deliveryBookBean.setBookNum(cartNoBookList.get(i).getCartNo());
            deliveryBookBeanList.add(deliveryBookBean);
            deliveryBookBeanList.addAll(cartNoBookList.get(i).getList());
            deliveryBookBeanSaveList.addAll(cartNoBookList.get(i).getList());
        }
    }

    //返回上一层的信息
    public void showSaveList(){
        deliveryBookBeanSaveList.clear();
        for (int i = 0; i < deliveryBookBeanList.size(); i++) {
            if (deliveryBookBeanList.get(i).getTitleNot().equals("")){
                deliveryBookBeanSaveList.add(deliveryBookBeanList.get(i));
            }
        }
    }

    //获取当前本数
    public int showNumBook(){
        int num = 0;
        for (int i = 0; i < deliveryBookBeanList.size(); i++) {
            if (null != deliveryBookBeanList.get(i).getCartonType()) {
                if (deliveryBookBeanList.get(i).getCartonType().equals("")) {
                    num++;
                }else {
                    for (int j = 0; j < cartList.size(); j++) {
                        if (cartList.get(j).getCartNo().equals(deliveryBookBeanList.get(i).getCartonNo())) {
                            num = num + cartList.get(j).getList().size();
                        }
                    }
                }
            }
        }
        return num;
    }

    public void bookDel(int pos){
        List<String> numList = new ArrayList<String>();
        for (int i = 0; i < cartList.size(); i++) {
            if (null !=deliveryBookBeanList.get(pos).getCartonType()){
                if (deliveryBookBeanList.get(pos).getCartonNo().equals(cartList.get(i).getCartNo())){
                    cartList.remove(i);
                }
            }
        }

        for (int i = 0; i < deliveryBookBeanSaveList.size(); i++) {
            if (deliveryBookBeanSaveList.get(i).equals(deliveryBookBeanList.get(pos))){
                deliveryBookBeanSaveList.remove(i);
                deliveryBookBeanList.remove(pos);
                if (boolType(pos)){
                    deliveryBookBeanList.remove(pos - 1);
                }
                recyclerViewAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    //判断是否删除标题
    public boolean boolType(int pos){
        if (pos != 0) {
            if (deliveryBookBeanList.get(pos - 1).getTitleNot().equals("true")){
                if (pos < deliveryBookBeanList.size()){
                    if (deliveryBookBeanList.get(pos).getTitleNot().equals("true")){
                        return true;
                    }
                }else {
                    return true;
                }
            }
        }
        return false;
    }
}
