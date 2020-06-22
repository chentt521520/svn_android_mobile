package com.jc.lottery.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.SettlementDetailActivity;
import com.jc.lottery.activity.immediate.SettlementDetailNewestActivity;
import com.jc.lottery.adapter.SettlementRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.SettlementRecordBean;
import com.jc.lottery.bean.req.pos_GetSettlementRecord;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class SettlementRecordFragment extends BaseFragment implements XListView.IXListViewListener {


    @BindView(R.id.lly_settlement_nodata)
    PercentLinearLayout llySettlementNodata;
    @BindView(R.id.xlv_settlement)
    XListView xlvSettlement;
    private String settleStatus = "00";
    private int pageNo = 1;
    private int pageNum = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载
    private List<SettlementRecordBean> settlementRecordBeanList = new ArrayList<SettlementRecordBean>();
    private SettlementRecordAdapter settlementRecordAdapter;
    private int mListFocus = 0;
    private int firstVisiblePositionTop = 0;
    private String startTime = "";
    private String endTime = "";

    public SettlementRecordFragment(String settleStatus) {
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
                intent.putExtra("id", settlementRecordBeanList.get(position - 1).getId());
                intent.setClass(getActivity(), SettlementDetailNewestActivity.class);
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

    @Override
    public void initData() {
        getHttpInfo();
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

    public void showHttpTime(String start,String end){
        refreshType = 1;
        pageNo = 1;
        startTime = start;
        endTime = end;
        getHttpInfo();
    }

    private void getHttpInfo() {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        String account_password = SPUtils.look(getActivity(), SPkey.password);
        pos_GetSettlementRecord.DataBean.SettlementInfo settlementInfo = new pos_GetSettlementRecord.DataBean.SettlementInfo("", "", pageNo + "", settleStatus,startTime,endTime, TimeUtils.get10IntTimeStamp() + "");
        pos_GetSettlementRecord pos_getSettlementRecord = new pos_GetSettlementRecord(account_name, account_password, "3", settlementInfo);
        String s1 = new Gson().toJson(pos_getSettlementRecord);
        OkGo.<String>post(MyUrl.pos_GetSettlementRecord)
                .upJson(s1)
                .execute(new vStringCallback(getContext()) {

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
                                JSONArray jsonArray = jsonObject.getJSONArray("settlementList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject settlementJson = jsonArray.getJSONObject(i);
                                    SettlementRecordBean settlementRecordBean = new SettlementRecordBean();
                                    if (settlementJson.getString("channels").equals("0")) {
                                        settlementRecordBean.setChannels(getString(R.string.terminal));
                                    } else if (settlementJson.getString("channels").equals("1")) {
                                        settlementRecordBean.setChannels(getString(R.string.third_party_app));
                                    } else if (settlementJson.getString("channels").equals("2")) {
                                        settlementRecordBean.setChannels(getString(R.string.block_chain));
                                    } else if (settlementJson.getString("channels").equals("3")) {
                                        settlementRecordBean.setChannels(getString(R.string.mobile_end));
                                    }
                                    settlementRecordBean.setCreateName(settlementJson.getString("createName"));
                                    settlementRecordBean.setCreateTime(timeStamp2Date(Long.parseLong(settlementJson.getString("createTime"))));
                                    settlementRecordBean.setId(settlementJson.getInt("id"));
                                    settlementRecordBean.setOrderCode(settlementJson.getString("orderCode"));
                                    settlementRecordBean.setSettleStatus(settlementJson.getString("settleStatus"));
                                    settlementRecordBean.setTotalMoney(settlementJson.getString("totalMoney"));
                                    settlementRecordBean.setGameName(settlementJson.getString("gameName"));
                                    settlementRecordBean.setMoneyStatus(settlementJson.getString("moneyStatus"));
                                    settlementRecordBean.setIndex(num);
                                    settlementRecordBeanList.add(settlementRecordBean);
                                    num++;
                                }
                                settlementRecordAdapter = new SettlementRecordAdapter(getActivity(),settleStatus);
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

