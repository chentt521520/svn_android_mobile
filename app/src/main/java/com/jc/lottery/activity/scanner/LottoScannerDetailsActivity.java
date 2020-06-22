package com.jc.lottery.activity.scanner;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.LottoScannerDetailAdapter;
import com.jc.lottery.adapter.LottoScannerNumberAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.WinEachListBean;
import com.jc.lottery.bean.req.TerminalCashPrizeBean;
import com.jc.lottery.bean.resp.GetCashPrizeBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 兑奖详情页面
 */
public class LottoScannerDetailsActivity extends BaseActivity {

    @BindView(R.id.lly_manual_scanner_back)
    LinearLayout llyManualScannerBack;
    @BindView(R.id.lly_scanner_details_money)
    LinearLayout llyScannerDetailsMoney;
    @BindView(R.id.lly_scanner_details_state)
    LinearLayout llyScannerDetailsState;
    @BindView(R.id.btn_scanner_details_submit)
    Button btnScannerDetailsSubmit;
    @BindView(R.id.tv_scanner_details_game)
    TextView tvScannerDetailsGame;
    @BindView(R.id.tv_scanner_details_active_money)
    TextView tvScannerDetailsActiveMoney;
    @BindView(R.id.tv_scanner_details_win_states)
    TextView tvScannerDetailsWinStates;
    @BindView(R.id.tv_scanner_details_money)
    TextView tvScannerDetailsMoney;
    @BindView(R.id.lly_scanner_details_one)
    PercentLinearLayout llyScannerDetailsOne;
    @BindView(R.id.lly_scanner_details_two)
    PercentLinearLayout llyScannerDetailsTwo;
    @BindView(R.id.tv_scanner_details_issue)
    TextView tvScannerDetailsIssue;
    @BindView(R.id.tv_scanner_details_open_time)
    TextView tvScannerDetailsOpenTime;
    @BindView(R.id.tv_scanner_details_recipient_buy_time)
    TextView tvScannerDetailsRecipientBuyTime;
    @BindView(R.id.tv_scanner_details_terminal)
    TextView tvScannerDetailsTerminal;
    @BindView(R.id.tv_scanner_details_multidraw)
    TextView tvScannerDetailsMultidraw;
    @BindView(R.id.tv_scanner_details_hejis)
    TextView tvScannerDetailsHejis;
    @BindView(R.id.rel_lotto_detail)
    RecyclerView relLottoDetail;
    @BindView(R.id.rel_scanner_details_num)
    RecyclerView relLottoDetailNum;
    @BindView(R.id.lly_lotto_win)
    LinearLayout llyLottoWin;
    private GetCashPrizeBean getCashPrizeBean;
    private String gameAlias = "";
    private String orderCode = "";
    private LottoScannerDetailAdapter lottoScannerDetailAdapter;
    private LottoScannerNumberAdapter lottoScannerNumberAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lotto_scanner_details;
    }

    @Override
    public void getPreIntent() {
        gameAlias = getIntent().getStringExtra("gameAlias");
        orderCode = getIntent().getStringExtra("orderCode");
        getCashPrizeBean = (GetCashPrizeBean) getIntent().getSerializableExtra("getCashPrizeBean");
    }

    @Override
    public void initView() {
        relLottoDetail.setLayoutManager(new LinearLayoutManager(this));
        relLottoDetailNum.setLayoutManager(new LinearLayoutManager(this));
        lottoScannerDetailAdapter = new LottoScannerDetailAdapter(this);
        lottoScannerNumberAdapter = new LottoScannerNumberAdapter(this);
    }

    @Override
    public void initData() {
        showView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.lly_manual_scanner_back, R.id.btn_scanner_details_submit})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_manual_scanner_back:
                finish();
                break;
            case R.id.btn_scanner_details_submit:
                getHttpInfo(gameAlias, orderCode);
//                intent.setClass(this, RewardRecordDetailActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private void showView() {
        tvScannerDetailsIssue.setText(getCashPrizeBean.getCashPrizeList().get(0).getDrawNumber());
        tvScannerDetailsGame.setText(getCashPrizeBean.getCashPrizeList().get(0).getGameName());
        tvScannerDetailsOpenTime.setText(timeStamp2Date(Long.parseLong(getCashPrizeBean.getCashPrizeList().get(0).getPrizeTime())));
        tvScannerDetailsRecipientBuyTime.setText(timeStamp2Date(Long.parseLong(getCashPrizeBean.getCashPrizeList().get(0).getBuyTime())));
        tvScannerDetailsTerminal.setText(getCashPrizeBean.getCashPrizeList().get(0).getTerminalNum());
        tvScannerDetailsMultidraw.setText(getCashPrizeBean.getCashPrizeList().get(0).getMultidraw());
        tvScannerDetailsHejis.setText(MoneyUtil.getIns().GetMoney(getCashPrizeBean.getCashPrizeList().get(0).getRealpayMoney()));
//        tvScannerDetailsRecipient.setText(winQueryInfo.getRecipient());
//        tvScannerDetailsRecipientName.setText(winQueryInfo.getParentName());
        llyScannerDetailsOne.setVisibility(View.GONE);
        lottoScannerNumberAdapter.setList(getCashPrizeBean.getCashPrizeList());
        relLottoDetailNum.setAdapter(lottoScannerNumberAdapter);
        if (getCashPrizeBean.getWinList().size() > 0) {
            int num = 0;
            for (int i = 0; i < getCashPrizeBean.getWinList().size(); i++) {
                if (getCashPrizeBean.getWinList().get(i).getWinEachList().size() != 0){
                    num++;
                }
            }
            if (num == 0){
                llyScannerDetailsMoney.setVisibility(View.GONE);
                if (getCashPrizeBean.getWinList().get(0).getWinState().equals("00")){
                    tvScannerDetailsWinStates.setText(getString(R.string.weizhongjiang));
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("01")){
                    tvScannerDetailsWinStates.setText(getString(R.string.congratulations_winning_prize));
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("02")){
                    tvScannerDetailsWinStates.setText(getString(R.string.daikaijiang));
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("03")){
                    tvScannerDetailsWinStates.setText(getString(R.string.yiduijiang));
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("04")){
                    tvScannerDetailsWinStates.setText(getString(R.string.qijiang));
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("05")){
                    tvScannerDetailsWinStates.setText(getString(R.string.refunded));
                }
//                    tvScannerDetailsWinStates.setText(getString(R.string.weizhongjiang));
                tvScannerDetailsWinStates.setTextColor(Color.rgb(48, 178, 102));
                llyScannerDetailsTwo.setVisibility(View.VISIBLE);
                btnScannerDetailsSubmit.setVisibility(View.GONE);
                return;
            }else {
                if (getCashPrizeBean.getWinList().get(0).getWinState().equals("03")){
                    btnScannerDetailsSubmit.setText(getString(R.string.yiduijiang));
                    btnScannerDetailsSubmit.setOnClickListener(null);
                    btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("04")){
                    btnScannerDetailsSubmit.setText(getString(R.string.qijiang));
                    btnScannerDetailsSubmit.setOnClickListener(null);
                    btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
                }else if (getCashPrizeBean.getWinList().get(0).getWinState().equals("05")){
                    btnScannerDetailsSubmit.setText(getString(R.string.refunded));
                    btnScannerDetailsSubmit.setOnClickListener(null);
                    btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
                }
            }
            llyScannerDetailsMoney.setVisibility(View.GONE);
            llyScannerDetailsTwo.setVisibility(View.GONE);
            llyLottoWin.setVisibility(View.VISIBLE);
            showList();
//            tvScannerDetailsWinStates.setText(getString(R.string.zhongjiang));
//            tvScannerDetailsWinStates.setTextColor(Color.rgb(0, 165, 83));
//            llyScannerDetailsMoney.setVisibility(View.VISIBLE);
//            tvScannerDetailsMoney.setText(MoneyUtil.getIns().GetMoney(getCashPrizeBean.getCashPrizeList().get(0).getWinAmount()));
        } else {
            llyScannerDetailsMoney.setVisibility(View.GONE);
            tvScannerDetailsWinStates.setText(getString(R.string.weizhongjiang));
            tvScannerDetailsWinStates.setTextColor(Color.rgb(48, 178, 102));
            llyScannerDetailsTwo.setVisibility(View.VISIBLE);
            btnScannerDetailsSubmit.setVisibility(View.GONE);
        }
//        if (winQueryInfo.getCashState().equals("00")) {
//            tvScannerDetailsCashState.setText(getString(R.string.yiduijiang));
//            tvScannerDetailsCashState.setTextColor(Color.rgb(0, 165, 83));
//        } else {
//            tvScannerDetailsCashState.setText(getString(R.string.no_convertibility));
//            tvScannerDetailsCashState.setTextColor(Color.rgb(48, 178, 102));
//        }
//        if (!getCashPrizeBean.getCashStatus().equals("01")) {
//            btnScannerDetailsSubmit.setVisibility(View.GONE);
//        }
    }

    private void getHttpInfo(String gameAlias, String orderCode) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        TerminalCashPrizeBean.CashPrizeInfo cashPrizeInfo = new TerminalCashPrizeBean.CashPrizeInfo(gameAlias, orderCode, getCashPrizeBean.getCashPrizeList().get(0).getDrawNumber());
        TerminalCashPrizeBean.DataBean dataBean = new TerminalCashPrizeBean.DataBean(cashPrizeInfo);
        TerminalCashPrizeBean pos_instantCashInfo = new TerminalCashPrizeBean(account_name, dataBean);
        String s1 = new Gson().toJson(pos_instantCashInfo);
        OkGo.<String>post(MyUrl.terminalCashPrize)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
//                                tvScannerDetailsCashState.setText(getString(R.string.yiduijiang));
                                btnScannerDetailsSubmit.setText(getString(R.string.yiduijiang));
                                btnScannerDetailsSubmit.setOnClickListener(null);
                                btnScannerDetailsSubmit.setBackgroundResource(R.drawable.scanner_new_bg);
                            }
                            ToastUtils.showShort(jsonObject.getString("message"));
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

    private void showList(){
        List<WinEachListBean> listBeans = new ArrayList<WinEachListBean>();
        for (int i = 0; i < getCashPrizeBean.getWinList().size(); i++) {
            for (int j = 0; j < getCashPrizeBean.getWinList().get(i).getWinEachList().size(); j++) {
                WinEachListBean winEachListBean = new WinEachListBean();
                winEachListBean.setDrawNumber(getCashPrizeBean.getWinList().get(i).getDrawNumber());
                winEachListBean.setWinLevel(getCashPrizeBean.getWinList().get(i).getWinEachList().get(j).getWinLevel());
                winEachListBean.setWinMoney(getCashPrizeBean.getWinList().get(i).getWinEachList().get(j).getWinMoney());
                winEachListBean.setWinNum(getCashPrizeBean.getWinList().get(i).getWinEachList().get(j).getWinNum());
                listBeans.add(winEachListBean);
            }
//            for (int j = 0; j < 10; j++) {
//                WinEachListBean winEachListBean = new WinEachListBean();
//                winEachListBean.setDrawNumber(getCashPrizeBean.getWinList().get(i).getDrawNumber());
//                winEachListBean.setWinLevel(getCashPrizeBean.getWinList().get(i).getWinEachList().get(0).getWinLevel() + j);
//                winEachListBean.setWinMoney(getCashPrizeBean.getWinList().get(i).getWinEachList().get(0).getWinMoney() + j);
//                winEachListBean.setWinNum(getCashPrizeBean.getWinList().get(i).getWinEachList().get(0).getWinNum() + j);
//                listBeans.add(winEachListBean);
//            }
        }
        lottoScannerDetailAdapter.setList(listBeans);
        relLottoDetail.setAdapter(lottoScannerDetailAdapter);
    }

}
