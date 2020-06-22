package com.jc.lottery.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.scanner.RewardRecordDetailActivity;
import com.jc.lottery.adapter.betting.MyImmediateRecordAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.req.pos_CashRecordInfo;
import com.jc.lottery.bean.resp.RewardInfoBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
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
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class MyImmediateRecordFragment extends BaseFragment {


    @BindView(R.id.rel_betting)
    RecyclerView relBetting;
    @BindView(R.id.lly_settlement_nodata)
    PercentLinearLayout llySettlementNodata;
    private List<RewardInfoBean> list = new ArrayList<RewardInfoBean>();
    private MyImmediateRecordAdapter recordAdapter = null;
    private String game;
    private int pageNo = 1;
    private int pageCount = 1;
    private int refreshType = 1; //1：下拉刷新 2：上拉加载

    public MyImmediateRecordFragment(String game) {
        this.game = game;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_betting_record;
    }

    @Override
    protected void initView(View view) {
        relBetting.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initListener() {
        recordAdapter = new MyImmediateRecordAdapter(getActivity());
        recordAdapter.setOnItemClickListener(new MyImmediateRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("data", list.get(position));
                intent.setClass(getActivity(), RewardRecordDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        getHttpInfo(game);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    private void getHttpInfo(String gameAlias) {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        String account_password = SPUtils.look(getActivity(), SPkey.password);
        pos_CashRecordInfo.DataBean.CashInfo cashInfo = new pos_CashRecordInfo.DataBean.CashInfo(gameAlias, pageNo + "","","", TimeUtils.get10IntTimeStamp() + "");
        pos_CashRecordInfo pos_cashRecordInfo = new pos_CashRecordInfo(account_name, account_password, "3", cashInfo);
        String s1 = new Gson().toJson(pos_cashRecordInfo);
        OkGo.<String>post(MyUrl.pos_CashRecordInfo)
                .upJson(s1)
                .execute(new vStringCallback(getContext()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
//                        ToastUtils.showShort("response:" + response.body());
//                        onLoad();
//                        if (refreshType == 1) {
//                            if (dataList != null) {
//                                dataList.clear();
//                                pageNo = 1;
//                            }
//                        } else {
//                            pageNo++;
//                        }

                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
                            JSONArray jsonArray = jsonObject.getJSONArray("cashList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject cashList = jsonArray.getJSONObject(i);
                                RewardInfoBean recordInfoBean = new RewardInfoBean();
                                recordInfoBean.setGameName(cashList.getString("gameName"));
                                recordInfoBean.setCashState(cashList.getString("cashState"));
                                recordInfoBean.setCashMoney(cashList.getString("cashMoney"));
                                recordInfoBean.setCashTime(timeStamp2Date(Long.parseLong(cashList.getString("cashTime"))));
                                recordInfoBean.setOrderCode(cashList.getString("orderCode"));
                                recordInfoBean.setSafetyCode(cashList.getString("safetyCode"));
                                recordInfoBean.setChannel(cashList.getString("channel"));
                                list.add(recordInfoBean);
                            }
//                            recordAdapter = new MyImmediateRecordAdapter(getActivity());
                            recordAdapter.setList(list);
                            relBetting.setAdapter(recordAdapter);
                            if (list.size() < 1) {
                                relBetting.setVisibility(View.GONE);
                                llySettlementNodata.setVisibility(View.VISIBLE);
                            } else {
                                relBetting.setVisibility(View.VISIBLE);
                                llySettlementNodata.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.showShort("response:" + response.body());
                        ProgressUtil.dismissProgressDialog();
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

}

