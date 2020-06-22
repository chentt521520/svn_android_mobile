package com.jc.lottery.activity.immediate;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.pos_GetSettlement;
import com.jc.lottery.bean.req.pos_settlementQuery;
import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 即开彩结算预览页面
 */
public class SettlementPreviewActivity extends BaseActivity {

    @BindView(R.id.tv_preview_total_reward)
    TextView tvPreviewTotalReward;
    @BindView(R.id.tv_preview_exchange_commission)
    TextView tvPreviewExchangeCommission;
    @BindView(R.id.tv_preview_commission_ratio)
    TextView tvPreviewCommissionRatio;
    @BindView(R.id.tv_preview_sales_commission)
    TextView tvPreviewSalesCommission;
    @BindView(R.id.tv_preview_sales_commission_ratio)
    TextView tvPreviewSalesCommissionRatio;
    @BindView(R.id.tv_preview_total_commission)
    TextView tvPreviewTotalCommission;
    @BindView(R.id.tv_preview_payable)
    TextView tvPreviewPayable;
    @BindView(R.id.tv_preview_total_settlement_amount)
    TextView tvPreviewTotalSettlementAmount;
    @BindView(R.id.tv_preview_book)
    TextView tvPreviewBook;
    @BindView(R.id.tv_receiving_real_moneys)
    TextView tvPreviewMoneys;
    @BindView(R.id.tv_game_name)
    TextView tvGameName;
    @BindView(R.id.bt_preview_submit)
    Button btPreviewSubmit;
    @BindView(R.id.lly_reward_record_back)
    LinearLayout llyRewardRecordBack;
    @BindView(R.id.header_receiving)
    ClassicsHeader headerReceiving;
    @BindView(R.id.srl_preview)
    SmartRefreshLayout srlPreview;
    private SettlementQueryBean settlementQueryBean;
    private String gameAlias = "";
    private ArrayList<pos_settlementQuery.BookList> list = new ArrayList<pos_settlementQuery.BookList>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement_preview;
    }

    @Override
    public void getPreIntent() {
    }

    @Override
    public void initData() {
//        getRecordHttp();
        String bookList = getIntent().getStringExtra("bookList");
        Type listType = new TypeToken<List<pos_settlementQuery.BookList>>() {
        }.getType();
        list = new Gson().fromJson(bookList, listType);
        gameAlias = getIntent().getStringExtra("gameAlias");
        settlementQueryBean = (SettlementQueryBean) getIntent().getSerializableExtra("settlementQueryBean");
        showData();
    }

    private void showData() {
        tvGameName.setText(gameAlias);
        tvPreviewBook.setText(list.size() + "");
        tvPreviewTotalReward.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCashMoney()));
        tvPreviewExchangeCommission.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCommissionPrize()));
        tvPreviewCommissionRatio.setText(settlementQueryBean.getInstantPrize());
        tvPreviewSalesCommission.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCommissionSales()));
        tvPreviewSalesCommissionRatio.setText(settlementQueryBean.getInstantSales());
        tvPreviewTotalCommission.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getCommission()));
        tvPreviewPayable.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getTicketMoney()));
        if (settlementQueryBean.getMoneyStatus().equals("00")) {
            tvPreviewTotalSettlementAmount.setText(getString(R.string.accounts_receivable) + " " + MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()));
            tvPreviewMoneys.setText("+" + MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()));
            tvPreviewMoneys.setTextColor(Color.rgb(0, 75, 255));
//            tvPreviewTotalSettlementAmount.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()) + getString(R.string.price_unit));
        } else {
            tvPreviewTotalSettlementAmount.setText(getString(R.string.payables) + " " + MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()));
            tvPreviewMoneys.setText("-" + MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()));
            tvPreviewMoneys.setTextColor(Color.rgb(209, 0, 0));
//            tvPreviewTotalSettlementAmount.setText(MoneyUtil.getIns().GetMoney(settlementQueryBean.getTotalMoney()) + getString(R.string.price_unit));
        }

    }

    @Override
    public void initListener() {
        srlPreview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                getAmountQueryInfo();
                if (list.size() > 0) {
                    previewSettlementQueryHttp(list);
                }
            }
        });
    }

    @Override
    public void initView() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = (int) (widthPixels * 0.9);
        getWindow().setAttributes(params);
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
        headerReceiving.setProgressResource(R.drawable.progressbarmore);
        headerReceiving.setArrowResource(R.drawable.top_refresh);
        headerReceiving.setEnableLastTime(false);
    }

    //预览查询结算信息
    private void previewSettlementQueryHttp(ArrayList<pos_settlementQuery.BookList> list) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_settlementQuery.Data data = new pos_settlementQuery.Data(new pos_settlementQuery.SettlementInfo(gameAlias, list));
        pos_settlementQuery pos_getBookNumQuery = new pos_settlementQuery(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getBookNumQuery);
        OkGo.<String>post(MyUrl.pos_GetSettlementQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        srlPreview.finishRefresh();
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                settlementQueryBean = new Gson().fromJson(response.body(), SettlementQueryBean.class);
                                showData();
                            } else {
                                ToastUtils.showShort(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        srlPreview.finishRefresh();
                    }
                });
    }

    //提交结算
    private void settlementSubmitHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
//        String deliveryCode = etSettlementOrder.getText().toString();
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetSettlement.SettlementInfo settlementInfo = new pos_GetSettlement.SettlementInfo(gameAlias, settlementQueryBean.getData().getSettlementInfo().getBookList(), settlementQueryBean.getCashMoneyMap(), settlementQueryBean.getTicketMoney(), settlementQueryBean.getCashMoney(), settlementQueryBean.getCommission(), settlementQueryBean.getCommissionPrize(), settlementQueryBean.getCommissionSales(), settlementQueryBean.getInstantPrize(), settlementQueryBean.getInstantSales(), settlementQueryBean.getCommissionSwitch(), settlementQueryBean.getTotalMoney(), settlementQueryBean.getMoneyStatus());
        pos_GetSettlement.Data data = new pos_GetSettlement.Data(settlementInfo);
        pos_GetSettlement pos_getSettlement = new pos_GetSettlement(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getSettlement);
        OkGo.<String>post(MyUrl.pos_GetSettlementSettlement)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            ToastUtils.showShort(jsonObject.getString("message"));
                            if (jsonObject.getString("code").equals("00000")) {
                                ImmediatelSettlementActivity.instance.resetAll();
                                ImmediatelSettlementActivity.instance.getBookInfoHttp("");

                                //弹出结算成功界面
                                finish();
                            }
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


    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.lly_reward_record_back, R.id.bt_preview_finish, R.id.bt_preview_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_reward_record_back:
                finish();
                break;
            case R.id.bt_preview_finish:
                finish();
                break;
            case R.id.bt_preview_submit:
                settlementSubmitHttp();
                break;
        }
    }

}
