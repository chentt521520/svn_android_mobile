package com.jc.lottery.activity.immediate;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.SettlementDetailsAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.SettlementGetDetailBean;
import com.jc.lottery.bean.req.pos_GetSettlementCheck;
import com.jc.lottery.bean.req.pos_GetSettlementDetails;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 结算详情页
 */
public class SettlementDetailActivity extends BaseActivity implements XListView.IXListViewListener {


    @BindView(R.id.xlv_settlement)
    XListView xlvSettlement;
    @BindView(R.id.bt_settlement_detail_examine)
    Button btSettlementDetailExamine;
    @BindView(R.id.lly_settlement_detail_examine)
    PercentLinearLayout llySettlementDetailExamine;
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private int settlementId = 0;
    private String settleStatus = "00";
    //    private List<SettlementDetailBean> detailBeanList = new ArrayList<SettlementDetailBean>();
    private SettlementDetailsAdapter settlementDetailsAdapter;
    private SettlementGetDetailBean settlementGetDetailBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement_detail_list;
    }

    @Override
    public void getPreIntent() {
        settleStatus = getIntent().getStringExtra("settleStatus");
        settlementId = getIntent().getIntExtra("id", 0);
    }

    @Override
    public void initView() {
        xlvSettlement.setPullLoadEnable(true);
        xlvSettlement.setXListViewListener(this);
    }

    @Override
    public void initData() {
        getHttpInfo();
    }

    @Override
    public void initListener() {
        xlvSettlement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                settlementGetDetailBean.getGetList().get(position - 1).setType(!settlementGetDetailBean.getGetList().get(position - 1).isType());
                settlementDetailsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetSettlementDetails.DataBean.SettlementInfo settlementInfo = new pos_GetSettlementDetails.DataBean.SettlementInfo(settlementId + "", pageNo + "");
        pos_GetSettlementDetails pos_getSettlementRecord = new pos_GetSettlementDetails(account_name, account_password, "3", settlementInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.pos_GetSettlementDetails)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        onLoad();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                settlementGetDetailBean = new Gson().fromJson(response.body(), SettlementGetDetailBean.class);
                                settlementDetailsAdapter = new SettlementDetailsAdapter(SettlementDetailActivity.this);
                                settlementDetailsAdapter.setAllGroup(settlementGetDetailBean.getGetList());
                                xlvSettlement.setAdapter(settlementDetailsAdapter);
                                if (settleStatus.equals("00")&&settlementGetDetailBean.getGetList().get(0).getSettleStatus().equals("00")){
                                    llySettlementDetailExamine.setVisibility(View.VISIBLE);
                                }else {
                                    llySettlementDetailExamine.setVisibility(View.GONE);
                                }
                            }else {
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
                        onLoad();
                    }
                });
    }

    private void getSettlementCheck(){
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetSettlementCheck.SettlementInfo settlementInfo = new pos_GetSettlementCheck.SettlementInfo(settlementGetDetailBean.getGetList().get(0).getOrderCode());
        pos_GetSettlementCheck.DataBean dataBean = new pos_GetSettlementCheck.DataBean(settlementInfo);
        pos_GetSettlementCheck pos_getSettlementCheck = new pos_GetSettlementCheck(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(pos_getSettlementCheck);
        OkGo.<String>post(MyUrl.pos_GetSettlementCheck)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            ToastUtils.showShort(jsonObject.getString("message"));
                            if (jsonObject.getString("code").equals("00000")) {
                                btSettlementDetailExamine.setBackgroundResource(R.drawable.shape_bg_false);
                                btSettlementDetailExamine.setOnClickListener(null);
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

    @OnClick({R.id.lly_settlement_back,R.id.bt_settlement_detail_examine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_settlement_back:
                finish();
                break;
            case R.id.bt_settlement_detail_examine:
                getSettlementCheck();
                break;
            default:
                break;
        }
    }

}
