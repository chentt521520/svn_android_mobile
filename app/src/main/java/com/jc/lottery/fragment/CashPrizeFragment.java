package com.jc.lottery.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.lottery.LottoCashRecordActivity;
import com.jc.lottery.activity.lottery.OpenResultDetailActivity;
import com.jc.lottery.activity.lottery.VictoryResultDetailActivity;
import com.jc.lottery.activity.scanner.LotteryRecordActivity;
import com.jc.lottery.activity.scanner.ManualScannerActivity;
import com.jc.lottery.activity.scanner.RewardScannerActivity;
import com.jc.lottery.activity.victory.VictoryScannerDetailActivity;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.VictoryScannerBean;
import com.jc.lottery.bean.req.RedeemQueryBean;
import com.jc.lottery.bean.req.pos_90x5_CurrentQuery;
import com.jc.lottery.bean.req.pos_listQuery;
import com.jc.lottery.bean.resp.DrawListBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.GetPermissionsUtil;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CashPrizeFragment extends BaseFragment {

    @BindView(R.id.tv_cash_prize_game)
    TextView tvCashPrizeGame;
    @BindView(R.id.tv_cash_prize_number)
    TextView tvCashPrizeNumber;
    @BindView(R.id.lly_cash_prize_qr)
    PercentLinearLayout llyCashPrizeQr;
    @BindView(R.id.lly_cash_prize_record)
    PercentLinearLayout llyCashPrizeRecord;
    @BindView(R.id.lly_cash_prize_manual)
    PercentLinearLayout llyCashPrizeManual;
    @BindView(R.id.tv_cash_prize_more)
    TextView tvCashPrizeMore;
    @BindView(R.id.lly_cash_prize_one)
    PercentLinearLayout llyCashPrizeOne;
    @BindView(R.id.tv_cash_prize_number_two)
    TextView tvCashPrizeNumberTwo;
    @BindView(R.id.lly_cash_prize_two)
    PercentLinearLayout llyCashPrizeTwo;
    @BindView(R.id.tv_cash_prize_number_three)
    TextView tvCashPrizeNumberThree;
    @BindView(R.id.tv_cash_prize_number_four)
    TextView tvCashPrizeNumberFour;
    @BindView(R.id.lly_cash_prize_three)
    PercentLinearLayout llyCashPrizeThree;
    @BindView(R.id.lly_cash_prize_four)
    PercentLinearLayout llyCashPrizeFour;
    @BindView(R.id.tv_cash_prize_one_time)
    TextView tvCashPrizeOneTime;
    @BindView(R.id.tv_cash_prize_two_time)
    TextView tvCashPrizeTwoTime;
    @BindView(R.id.tv_cash_prize_three_time)
    TextView tvCashPrizeThreeTime;
    @BindView(R.id.tv_cash_prize_four_time)
    TextView tvCashPrizeFourTime;
    @BindView(R.id.lly_cash_prize_bottom)
    PercentLinearLayout llyCashPrizeBottom;
    @BindView(R.id.lly_one)
    PercentLinearLayout llyOne;
    @BindView(R.id.lly_two)
    PercentLinearLayout llyTwo;
    @BindView(R.id.lly_three)
    PercentLinearLayout llyThree;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cash_prize;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView(View view) {
//        rl_guide = (RelativeLayout) view.findViewById(R.id.rl_guide);
//        rl_guide.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
//        GetGameList();
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        if (getPermissionsUtil.getPermissions("yddltdqkjjl")) {
            getHttpInfo("90x5");
            getHttpInfo("37x6");
            getHttpInfo("49x6");
        }else {
            llyCashPrizeBottom.setVisibility(View.GONE);
            llyOne.setVisibility(View.GONE);
            llyTwo.setVisibility(View.GONE);
            llyThree.setVisibility(View.GONE);
        }
        if (getPermissionsUtil.getPermissions("yddsfckjgg")){
            getVictoryWinQueryHttpInfo();
            llyCashPrizeBottom.setVisibility(View.VISIBLE);
        }

//        showHistoryView();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
            if (getPermissionsUtil.getPermissions("yddltdqkjjl")) {
                getHttpInfo("90x5");
                getHttpInfo("37x6");
                getHttpInfo("49x6");
            }
        }
    }

    @OnClick({R.id.lly_cash_prize_qr, R.id.lly_cash_prize_record, R.id.lly_cash_prize_manual, R.id.tv_cash_prize_more})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        switch (view.getId()) {
            case R.id.lly_cash_prize_manual:
                if (getPermissionsUtil.getPermissions("yddjkcdj")) {
                    intent.setClass(getActivity(), ManualScannerActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.showShort(getString(R.string.no_operation_permission));
                }
                break;
            case R.id.lly_cash_prize_qr:
                if (getPermissionsUtil.getPermissions("yddjkcdj")) {
                    intent.putExtra("entranceType", "1");
                    intent.setClass(getActivity(), RewardScannerActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.showShort(getString(R.string.no_operation_permission));
                }
                break;
            case R.id.lly_cash_prize_record:
                if (getPermissionsUtil.getPermissions("yddltdjjl")) {
                    intent.setClass(getActivity(), LottoCashRecordActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.showShort(getString(R.string.no_operation_permission));
                }
                break;
            case R.id.tv_cash_prize_more:
                if (getPermissionsUtil.getPermissions("yddltlskjjl")) {
                    intent.setClass(getActivity(), LotteryRecordActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.showShort(getString(R.string.no_operation_permission));
                }
                break;
        }
    }

    private void showHistoryView(final DrawListBean drawListBean, final String game) {
        if (game.equals("90x5")) {
            llyCashPrizeOne.removeAllViews();
            String[] num = drawListBean.getPrizeNumOrder().split(" ");
            for (int i = 0; i < num.length; i++) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.cash_prize_item_text, null);
                TextView tv = view.findViewById(R.id.sna_number);
                tv.setText(num[i]);
                llyCashPrizeOne.addView(view);
//                tvCashPrizeOneTime.setText(drawListBean.get);
//                tvCashPrizeOneTime.setText(drawListBean.get());
            }
            tvCashPrizeNumber.setText(drawListBean.getDraw());
            tvCashPrizeOneTime.setText(timeStamp2Date(drawListBean.getPrizeTime()));
            llyCashPrizeOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("gameAlias",game);
                    intent.putExtra("draw",drawListBean.getDraw());
                    intent.setClass(getActivity(), OpenResultDetailActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        } else if(game.equals("37x6")){
            llyCashPrizeTwo.removeAllViews();
            String[] num = drawListBean.getPrizeNumOrder().split(" ");
            List<String> numList = showList(num);
            for (int i = 0; i < numList.size(); i++) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.cash_prize_item_text, null);
                TextView tv = view.findViewById(R.id.sna_number);
                if (i == numList.size() - 1){
                    tv.setBackgroundResource(R.drawable.red_balls);
                }
                tv.setText(numList.get(i));
                llyCashPrizeTwo.addView(view);
            }
            tvCashPrizeNumberTwo.setText(drawListBean.getDraw());
            tvCashPrizeTwoTime.setText(timeStamp2Date(drawListBean.getPrizeTime()));
            llyCashPrizeTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("gameAlias",game);
                    intent.putExtra("draw",drawListBean.getDraw());
                    intent.setClass(getActivity(), OpenResultDetailActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }else {
            llyCashPrizeThree.removeAllViews();
            String[] num = drawListBean.getPrizeNumOrder().split(" ");
            List<String> numList = showList(num);
            for (int i = 0; i < numList.size(); i++) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.cash_prize_item_text, null);
                TextView tv = view.findViewById(R.id.sna_number);
                if (i == numList.size() - 1){
                    tv.setBackgroundResource(R.drawable.red_balls);
                }
                tv.setText(numList.get(i));
                llyCashPrizeThree.addView(view);
            }
            tvCashPrizeNumberThree.setText(drawListBean.getDraw());
            tvCashPrizeThreeTime.setText(timeStamp2Date(drawListBean.getPrizeTime()));
            llyCashPrizeThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("gameAlias",game);
                    intent.putExtra("draw",drawListBean.getDraw());
                    intent.setClass(getActivity(), OpenResultDetailActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    private List<String> showList(String[] s){
        List<String> list = new ArrayList<String>();
        String[] endString = s[s.length - 1].split("-");
        if (endString.length > 1){
            for (int i = 0; i < s.length - 1; i++) {
                list.add(s[i]);
            }
            for (int i = 0; i < endString.length; i++) {
                list.add(endString[i]);
            }
        }else {
            for (int i = 0; i < s.length; i++) {
                list.add(s[i]);
            }
//            list = s.
        }
        return list;
    }

    private void getHttpInfo(final String game) {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        pos_90x5_CurrentQuery.ColorPeriodInfo colorPeriodInfo = new pos_90x5_CurrentQuery.ColorPeriodInfo(game);
        pos_90x5_CurrentQuery.DataBean dataBean = new pos_90x5_CurrentQuery.DataBean(colorPeriodInfo);
        pos_90x5_CurrentQuery pos_90x5_currentQuery = new pos_90x5_CurrentQuery(account_name, dataBean);
        String s1 = new Gson().toJson(pos_90x5_currentQuery);
        String url = "";
        if (game.equals("90x5")) {
            url = MyUrl.screen_90x5_CurrentQuery;
        } else if (game.equals("37x6")){
            url = MyUrl.screen_37x6_CurrentQuery;
        }else {
            url = MyUrl.screen_49x6_CurrentQuery;
        }
        OkGo.<String>post(url)
                .upJson(s1)
                .execute(new vStringCallback(getContext()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("drawList");
                                if (jsonArray.length() > 0) {
                                    DrawListBean drawListBean = new Gson().fromJson(jsonArray.get(0).toString(), DrawListBean.class);
                                    GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
                                    if (getPermissionsUtil.getPermissions("yddltdqkjjl")) {
                                        showHistoryView(drawListBean, game);
                                        llyCashPrizeBottom.setVisibility(View.VISIBLE);
                                    }else {
                                        llyCashPrizeBottom.setVisibility(View.GONE);
                                    }
                                }
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

    private void getVictoryWinQueryHttpInfo() {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        String account_pass = SPUtils.look(getActivity(), SPkey.password);
        pos_listQuery.NoticeInfo orderInfo = new pos_listQuery.NoticeInfo("");
        pos_listQuery.DataBean dataBean = new pos_listQuery.DataBean(orderInfo);
        pos_listQuery pos_listQuery = new pos_listQuery(account_name,account_pass, dataBean);
        String s1 = new Gson().toJson(pos_listQuery);
        OkGo.<String>post(MyUrl.pos_listQuery)
                .upJson(s1)
                .execute(new vStringCallback(getActivity()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
//                                ToastUtils.showShort(jsonObject.getString("message"));
                                JSONArray drawNoticeList = jsonObject.getJSONArray("drawNoticeList");
                                final JSONObject drawJson = drawNoticeList.getJSONObject(0);
                                final String issue = drawJson.getString("issue_no");
                                tvCashPrizeNumberFour.setText(issue);
                                tvCashPrizeFourTime.setText(timeStamp2Date(drawJson.getLong("issue_end_time")));
                                llyCashPrizeFour.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        Intent intent = new Intent();
//                                        intent.putExtra("gameAlias",game);
//                                        intent.putExtra("draw",drawListBean.getDraw());
//                                        intent.setClass(getActivity(), OpenResultDetailActivity.class);
//                                        getActivity().startActivity(intent);
                                        Intent intent = new Intent();
                                        intent.putExtra("gameAlias","jc");
                                        intent.putExtra("draw",issue);
                                        intent.setClass(getActivity(), VictoryResultDetailActivity.class);
                                        getActivity().startActivity(intent);
                                    }
                                });
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                            ProgressUtil.dismissProgressDialog();
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
