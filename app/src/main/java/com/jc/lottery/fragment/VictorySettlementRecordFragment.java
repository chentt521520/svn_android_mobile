package com.jc.lottery.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.lottery.LotterySettlementDetailActivity;
import com.jc.lottery.activity.victory.VictorySettlementDetailActivity;
import com.jc.lottery.adapter.SettleRecordAdapter;
import com.jc.lottery.adapter.SettlementRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.bean.VictorySettlementRecordBean;
import com.jc.lottery.bean.req.pos_GetSettleRecord;
import com.jc.lottery.bean.req.pos_GetSettlementRecord;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class VictorySettlementRecordFragment extends BaseFragment implements XListView.IXListViewListener {

    @BindView(R.id.lly_settlement_nodata)
    PercentLinearLayout llySettlementNodata;
    @BindView(R.id.xlv_settlement)
    XListView xlvSettlement;
    private String settleStatus = "00";
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private List<VictorySettlementRecordBean> settlementRecordBeanList = new ArrayList<VictorySettlementRecordBean>();
    private SettleRecordAdapter settlementRecordAdapter;
    private boolean codeType = true;
    private int numType = 0;

    public VictorySettlementRecordFragment(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settlement_list;
    }

    @Override
    protected void initView(View view) {
        xlvSettlement.setPullLoadEnable(true);
        xlvSettlement.setXListViewListener(this);
    }

    @Override
    public void initListener() {
        xlvSettlement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("settleStatus",settleStatus);
                intent.putExtra("orderCode", settlementRecordBeanList.get(position - 1).getOrder_code());
                intent.setClass(getActivity(), VictorySettlementDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
//        getHttpInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (null != getActivity()) {
                refreshType = 1;
                pageNo = 1;
                getHttpInfo();
            }
        } else {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            if (null != getActivity()) {
                refreshType = 1;
                pageNo = 1;
                getHttpInfo();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != getActivity()) {
            refreshType = 1;
            pageNo = 1;
            getHttpInfo();
        }
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        String account_password = SPUtils.look(getActivity(), SPkey.password);
        pos_GetSettleRecord.DataBean.SettlementInfo settlementInfo = new pos_GetSettleRecord.DataBean.SettlementInfo("", settleStatus, pageNo + "","","","","","");
        pos_GetSettleRecord pos_getSettlementRecord = new pos_GetSettleRecord(account_name, account_password, "3", settlementInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.pos_GetSettleRecord)
                .upJson(s1)
                .execute(new vStringCallback(getContext())  {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        numType++;
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
                                JSONArray jsonArray = jsonObject.getJSONArray("settleList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject settlementJson = jsonArray.getJSONObject(i);
                                    VictorySettlementRecordBean settlementRecordBean = new VictorySettlementRecordBean();
                                    if (settlementJson.getString("channels").equals("0")) {
                                        settlementRecordBean.setChannels(getString(R.string.terminal));
                                    } else if (settlementJson.getString("channels").equals("1")) {
                                        settlementRecordBean.setChannels(getString(R.string.third_party_app));
                                    } else if (settlementJson.getString("channels").equals("2")) {
                                        settlementRecordBean.setChannels(getString(R.string.block_chain));
                                    } else if (settlementJson.getString("channels").equals("3")) {
                                        settlementRecordBean.setChannels(getString(R.string.mobile_end));
                                    }
                                    settlementRecordBean.setOrder_code(settlementJson.getString("order_code"));
                                    settlementRecordBean.setCreate_time(timeStamp2Date(Long.parseLong(settlementJson.getString("create_time"))));
//                                    settlementRecordBean.setAudit_time(timeStamp2Date(Long.parseLong(settlementJson.getString("audit_time"))));
//                                    settlementRecordBean.setId(settlementJson.getInt("id"));
                                    settlementRecordBean.setSettle_condition(settlementJson.getString("settle_condition"));
                                    settlementRecordBean.setTotal_money(settlementJson.getString("total_money"));
                                    settlementRecordBean.setMoney_status(settlementJson.getString("money_status"));
                                    settlementRecordBean.setSettle_status(settlementJson.getString("settle_status"));
//                                    settlementRecordBean.setChannels(settlementJson.getString("channels"));
                                    settlementRecordBean.setUser_name(settlementJson.getString("user_name"));
                                    settlementRecordBean.setUser_role(settlementJson.getString("user_role"));
                                    settlementRecordBean.setAuditor(settlementJson.getString("auditor"));
                                    settlementRecordBeanList.add(settlementRecordBean);
                                }
                                settlementRecordAdapter = new SettleRecordAdapter(getActivity(),settleStatus);
                                settlementRecordAdapter.setAllGroup(settlementRecordBeanList);
                                xlvSettlement.setAdapter(settlementRecordAdapter);

                                if (settlementRecordBeanList.size() < 1) {
                                    xlvSettlement.setVisibility(View.GONE);
                                    llySettlementNodata.setVisibility(View.VISIBLE);
                                } else {
                                    xlvSettlement.setVisibility(View.VISIBLE);
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
                            xlvSettlement.setSelection(pageNo * pageNum);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
                        onLoad();
                        if (numType == 0){
                            if (null != getActivity()){
                                getActivity().finish();
                            }
                        }
                    }
                });
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(getActivity(), SPkey.Language);
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
        getHttpInfo();
    }

    @Override
    public void onLoadMore() {
        codeType = false;
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

