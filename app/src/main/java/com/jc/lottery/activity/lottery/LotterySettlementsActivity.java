package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.adapter.SettlementAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.req.SettleTimeQuery;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.req.pos_settlementQuery;
import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.SmoothCheckBox;
import com.jc.lottery.view.datepicker.time.DatePickerDialogFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 即开彩结算页面
 */
public class LotterySettlementsActivity extends BaseActivity {

    @BindView(R.id.pull_refresh_scrollview)
    ScrollView pullRefreshScrollview;
    @BindView(R.id.btn_settlement_reset)
    Button btnSettlementReset;
    @BindView(R.id.btn_settlement_submit)
    Button btnSettlementSubmit;
    @BindView(R.id.lv_settlement)
    ListView lvSettlement;
    @BindView(R.id.lly_settlement_select)
    LinearLayout llySettlementSelect;
    @BindView(R.id.btn_settlement_preview)
    Button btnSettlementPreview;
    @BindView(R.id.scb_settlement_all)
    SmoothCheckBox scbSettlementAll;
    @BindView(R.id.lly_settlement_all)
    LinearLayout llySettlementAll;
    @BindView(R.id.lly_settlement_back)
    LinearLayout llySettlementBack;
    @BindView(R.id.lv_settlement_pop)
    RecyclerView lvSettlementPop;
    @BindView(R.id.lly_settlement_pop)
    LinearLayout llySettlementPop;
    @BindView(R.id.tv_settlement_pop_total)
    TextView tvSettlementPopTotal;
    @BindView(R.id.btn_settlement_pop_submit)
    Button btnSettlementPopSubmit;
    @BindView(R.id.lly_settlement_pop_content)
    LinearLayout llySettlementPopContent;
    @BindView(R.id.lly_settlement_nodata)
    LinearLayout llySettlementNodata;
    @BindView(R.id.lly_lottery_settlement_start)
    LinearLayout llyLotterySettlementStart;
    @BindView(R.id.lly_lottery_settlement_end)
    LinearLayout llyLotterySettlementEnd;
    @BindView(R.id.tv_lottery_settlement_start)
    TextView tvLotterySettlementStart;
    @BindView(R.id.tv_lottery_settlement_end)
    TextView tvLotterySettlementEnd;
    @BindView(R.id.tv_lottery_settlement_ends)
    TextView tvLotterySettlementEnds;
    @BindView(R.id.tv_lottery_settlement_time)
    TextView tvLotterySettlementTime;
    @BindView(R.id.lly_lottery_settlement_time)
    LinearLayout llyLotterySettlementTime;
    @BindView(R.id.lly_lottery_settlement_starts)
    LinearLayout llyLotterySettlementStarts;
    @BindView(R.id.tv_lottery_settlement_starts)
    TextView tvLotterySettlementStarts;
    @BindView(R.id.tv_receiving_select_90)
    TextView tvReceivingSelect90;
    @BindView(R.id.tv_receiving_select_37)
    TextView tvReceivingSelect37;
    @BindView(R.id.tv_receiving_select_49)
    TextView tvReceivingSelect49;
    private int startYear = 0;
    private int startMonth = 0;
    private int startDay = 0;
    private ArrayAdapter gameAdapter;
    private ArrayAdapter bookAdapter;
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private List<String> gameStringList = new ArrayList<String>();
    private List<String> bookList = new ArrayList<String>();
    private String account_name = "";
    private String account_password = "";
    private List<SettlementQueryBean.CashMoneyBean> settlementInfoBeanList = new ArrayList<SettlementQueryBean.CashMoneyBean>();
    private int totalMoney = 0;
    //    private SettlementAdapter settlementAdapter;
    private int selectEndType = 0; // 0 未查询信息 1 已查询
    private SettlementAdapter settlementAdapter;
    //    private SettlementNewestAdapter popSettlementAdapter;
    private SettlementQueryBean settlementQueryBean;
    private SettlementQueryBean previewSettlementQueryBean;
    public static LotterySettlementsActivity instance = null;
    private Long time;
    private boolean codeType = true;
    private String gameAlias = "90x5";

    @Override
    public int getLayoutId() {
        return R.layout.activity_lottery_settlements;
    }

    @Override
    public void getPreIntent() {
    }

    @Override
    public void initData() {
//        getRecordHttp();
//        showInfo();
//        getGameInfoHttp();
        getBookInfoHttp();
    }

    @Override
    public void initListener() {
//        spSpSettlementType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                llySettlementSelect.setVisibility(View.GONE);
//                selectEndType = 1;
//
//                getBookInfoHttp();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        lvSettlement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean selectType = !settlementInfoBeanList.get(position).isType();
                settlementInfoBeanList.get(position).setType(selectType);
                settlementAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initView() {
        instance = this;
    }

    //查询结算时间
    public void getBookInfoHttp() {
//        String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
//        String gameAlias = "37x6";
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        account_name = SPUtils.look(this, SPkey.username);
        account_password = SPUtils.look(this, SPkey.password);
        SettleTimeQuery.SettlementInfo settlementInfo = new SettleTimeQuery.SettlementInfo(gameAlias);
        SettleTimeQuery.DataBean dataBean = new SettleTimeQuery.DataBean(settlementInfo);
        SettleTimeQuery settleTimeQuery = new SettleTimeQuery(account_name, account_password, "3", dataBean);
        String s1 = new Gson().toJson(settleTimeQuery);
        OkGo.<String>post(MyUrl.settleTimeQuery)
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
                                selectEndType = 1;
                                time = jsonObject.getLong("settlementTime");
                                if (time != 0) {
                                    llyLotterySettlementStarts.setVisibility(View.VISIBLE);
                                    String s = time + "";
                                    if (s.length() < 13) {
                                        tvLotterySettlementStarts.setText(timeStamp2Date(time * 1000));
                                        tvLotterySettlementTime.setText(timeStamp2Date(time * 1000));
                                    } else {
                                        tvLotterySettlementStarts.setText(timeStamp2Date(time));
                                        tvLotterySettlementTime.setText(timeStamp2Date(time));
                                    }
                                } else {
//                                    llyLotterySettlementStarts.setVisibility(View.GONE);
//                                    tvLotterySettlementStarts.setText("--");
                                    tvLotterySettlementStarts.setText(getString(R.string.registration_times));
//                                    tvLotterySettlementTime.setText("--");
                                    tvLotterySettlementTime.setText(getString(R.string.registration_times));
                                }
//                                llyLotterySettlementTime.setVisibility(View.VISIBLE);
//                                llySettlementNodata.setVisibility(View.GONE);
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

    //查询游戏类型
    private void getGameInfoHttp() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        account_name = SPUtils.look(this, SPkey.username);
        account_password = SPUtils.look(this, SPkey.password);
        pos_GameQueryInfo pos_gameQueryInfo = new pos_GameQueryInfo(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetGameQueryInfo)
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
                                JSONArray gameList = jsonObject.getJSONArray("gameList");
//                                for (int i = 0; i < gameList.length(); i++) {
//                                    JSONObject json = gameList.getJSONObject(i);
//                                    GameListBean gameListBean = new GameListBean();
//                                    gameListBean.setGameName(json.getString("gameName"));
//                                    gameListBean.setGameAlias(json.getString("gameAlias"));
//                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
//                                    gameListBean.setEnabled(json.getString("enabled"));
//                                    gameStringList.add(gameListBean.getGameName());
//                                    gameListBeans.add(gameListBean);
//                                }
//                                GameListBean gameListBean = new GameListBean();
//                                gameListBean.setGameName("5/90");
//                                gameListBean.setGameAlias("90x5");
//                                gameListBean.setTicketPrice("");
//                                gameListBean.setEnabled("");
//                                GameListBean gameListBeanTwo = new GameListBean();
//                                gameListBeanTwo.setGameName("6/37");
//                                gameListBeanTwo.setGameAlias("37x6");
//                                gameListBeanTwo.setTicketPrice("");
//                                gameListBeanTwo.setEnabled("");
//                                gameStringList.add(gameListBean.getGameName());
//                                gameStringList.add(gameListBeanTwo.getGameName());
//                                gameListBeans.add(gameListBean);
//                                gameListBeans.add(gameListBeanTwo);
//                                changeSpinner(spSpSettlementType, gameAdapter, gameStringList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        finish();
                    }
                });
    }

    //查询结算信息
    private void settlementQueryHttp(ArrayList<pos_settlementQuery.BookList> list) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
//        String gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
        pos_settlementQuery.Data data = new pos_settlementQuery.Data(new pos_settlementQuery.SettlementInfo(gameAlias, list));
        pos_settlementQuery pos_getBookNumQuery = new pos_settlementQuery(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getBookNumQuery);
        OkGo.<String>post(MyUrl.pos_GetSettlementQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                selectEndType = 1;
                                settlementQueryBean = new Gson().fromJson(response.body(), SettlementQueryBean.class);
                                settlementInfoBeanList = settlementQueryBean.getCashMoneyMap();
                                for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                                    settlementInfoBeanList.get(i).setType(true);
                                }
                                settlementAdapter = new SettlementAdapter(LotterySettlementsActivity.this);
                                settlementAdapter.setAllGroup(settlementInfoBeanList);
                                lvSettlement.setAdapter(settlementAdapter);
                                setListViewHeight(lvSettlement);
                                llySettlementSelect.setVisibility(View.VISIBLE);
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
                    }
                });
    }

    //预览查询结算信息
    private void previewSettlementQueryHttp(final ArrayList<pos_settlementQuery.BookList> list) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        pos_settlementQuery.Data data = new pos_settlementQuery.Data(new pos_settlementQuery.SettlementInfo(gameAlias, list));
        pos_settlementQuery pos_getBookNumQuery = new pos_settlementQuery(account_name, account_password, "3", data);
        String s1 = new Gson().toJson(pos_getBookNumQuery);
        OkGo.<String>post(MyUrl.pos_GetSettlementQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                previewSettlementQueryBean = new Gson().fromJson(response.body(), SettlementQueryBean.class);
                                String bookList = new Gson().toJson(list);
                                Intent intent = new Intent();
                                intent.putExtra("bookList", bookList);
                                intent.putExtra("gameAlias", gameAlias);
                                intent.putExtra("settlementQueryBean", previewSettlementQueryBean);
//                                intent.setClass(LotterySettlementActivity.this, SettlementPreviewActivity.class);
                                intent.setClass(LotterySettlementsActivity.this, LotterySettlementPreviewActivity.class);
                                startActivity(intent);
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
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.btn_settlement_reset, R.id.btn_settlement_submit, R.id.btn_settlement_preview, R.id.lly_settlement_all, R.id.lly_settlement_back, R.id.btn_settlement_pop_submit, R.id.lly_settlement_pop, R.id.lly_settlement_pop_content, R.id.lly_lottery_settlement_start, R.id.lly_lottery_settlement_end, R.id.lly_lottery_settlement_ends,R.id.tv_receiving_select_90,R.id.tv_receiving_select_37,R.id.tv_receiving_select_49})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_settlement_reset:
                resetAll();
                break;
            case R.id.tv_receiving_select_90:
                if (!gameAlias.equals("90x5")){
                    gameAlias = "90x5";
                    getBookInfoHttp();
                }
                tvReceivingSelect90.setBackgroundResource(R.drawable.reward_et_red_bg);
                tvReceivingSelect37.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect49.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect90.setTextColor(Color.WHITE);
                tvReceivingSelect37.setTextColor(Color.GRAY);
                tvReceivingSelect49.setTextColor(Color.GRAY);
                break;
            case R.id.tv_receiving_select_37:
                if (!gameAlias.equals("37x6")){
                    gameAlias = "37x6";
                    getBookInfoHttp();
                }
                tvReceivingSelect90.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect49.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect37.setBackgroundResource(R.drawable.reward_et_red_bg);
                tvReceivingSelect90.setTextColor(Color.GRAY);
                tvReceivingSelect49.setTextColor(Color.GRAY);
                tvReceivingSelect37.setTextColor(Color.WHITE);
                break;
            case R.id.tv_receiving_select_49:
                if (!gameAlias.equals("49x6")){
                    gameAlias = "49x6";
                    getBookInfoHttp();
                }
                tvReceivingSelect90.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect37.setBackgroundResource(R.drawable.reward_et_bg);
                tvReceivingSelect49.setBackgroundResource(R.drawable.reward_et_red_bg);
                tvReceivingSelect90.setTextColor(Color.GRAY);
                tvReceivingSelect37.setTextColor(Color.GRAY);
                tvReceivingSelect49.setTextColor(Color.WHITE);
                break;
            case R.id.btn_settlement_submit:
//                gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
                if (!gameAlias.equals("")) {
                    getBookInfoHttp();
                    scbSettlementAll.setClickable(false);
                    scbSettlementAll.setChecked(true, true);
                } else {
                    ToastUtils.showShort(getString(R.string.getting_game_information));
                }
//                settlementQueryHttp();
//                testHttp();
                break;
            case R.id.btn_settlement_preview:
//                settlementSubmitHttp();
                if (selectEndType != 0) {
//                    ArrayList<pos_settlementQuery.BookList> bookLists = new ArrayList<pos_settlementQuery.BookList>();
//                    for (int i = 0; i < settlementInfoBeanList.size(); i++) {
//                        if (settlementInfoBeanList.get(i).isType()) {
//                            String[] arr = settlementInfoBeanList.get(i).getNumber().split("-");
//                            bookLists.add(new pos_settlementQuery.BookList(arr[1], arr[0]));
//                        }
//                    }
//                    if (bookLists.size() > 0) {
//                        previewSettlementQueryHttp(bookLists);
//                    } else {
//                        ToastUtils.showShort(R.string.please_choose_number);
//                    }
                    String end = tvLotterySettlementEnds.getText().toString().trim();
                    Long endTime = null;
                    if (!end.equals("")) {
//                        end = end + " 00:00:01";
                        endTime = Long.parseLong(parseServerTime(end, null));
//                        endTime = getStringToDate(end, null);
                        if (endTime > time) {
//                            gameAlias = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameAlias();
                            String gameName = "";
                            if (gameAlias.equals("90x5")){
                                gameName = "5/90";
                            }else {
                                gameName = "6/37";
                            }
//                            String gameName = gameListBeans.get(spSpSettlementType.getSelectedItemPosition()).getGameName();
                            Intent intent = new Intent();
                            intent.putExtra("gameAlias", gameAlias);
                            intent.putExtra("gameName", gameName);
                            intent.putExtra("time", time);
                            intent.putExtra("endTime", endTime);
//                    intent.putExtra("settlementQueryBean", previewSettlementQueryBean);
//                                intent.setClass(LotterySettlementActivity.this, SettlementPreviewActivity.class);
                            intent.setClass(LotterySettlementsActivity.this, LotterySettlementPreviewActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShort(getString(R.string.should_be_greater));
                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.Please_end_time));
                    }
//                    showPopWindow();
                } else {
                    ToastUtils.showShort(getString(R.string.please_query_first));
                }
                break;
            case R.id.lly_settlement_all:
                if (scbSettlementAll.isChecked()) {
                    for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                        settlementInfoBeanList.get(i).setType(false);
                    }
                } else {
                    for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                        settlementInfoBeanList.get(i).setType(true);
                    }
                }
                scbSettlementAll.setChecked(!scbSettlementAll.isChecked(), true);
                settlementAdapter.notifyDataSetChanged();
                break;
            case R.id.lly_settlement_back:
                finish();
                break;
            case R.id.lly_settlement_pop:
                llySettlementPop.setVisibility(View.GONE);
                break;
            case R.id.btn_settlement_pop_submit:
                String tos = "";
                for (int i = 0; i < settlementInfoBeanList.size(); i++) {
                    tos = tos + settlementInfoBeanList.get(i).isType() + ",";
                }
                ToastUtils.showShort(tos);
//                settlementSubmitHttp();
                break;
            case R.id.lly_settlement_pop_content:
                break;
            case R.id.lly_lottery_settlement_start:
                DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
                datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                    @Override
                    public void onDateChoose(int year, int month, int day) {
                        startYear = year;
                        startMonth = month;
                        startDay = day;
                        tvLotterySettlementStart.setText(year + "-" + month + "-" + day);
                        tvLotterySettlementEnd.setText("");
                        llySettlementSelect.setVisibility(View.GONE);
                        selectEndType = 0;
//                        Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                }, getString(R.string.Please_starting_time));
                datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
                break;
            case R.id.lly_lottery_settlement_end:
                DatePickerDialogFragment datePickerDialogFragments = new DatePickerDialogFragment();
                datePickerDialogFragments.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                    @Override
                    public void onDateChoose(int year, int month, int day) {
                        if (timeCompare(startYear, startMonth, startDay, year, month, day)) {
                            tvLotterySettlementEnd.setText(year + "-" + month + "-" + day);
                            llySettlementSelect.setVisibility(View.GONE);
                            selectEndType = 0;
                        } else {
                            ToastUtils.showShort(getString(R.string.should_be_greater));
                        }
//                        Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                }, getString(R.string.Please_end_time));
                if (startYear != 0) {
                    datePickerDialogFragments.show(getSupportFragmentManager(), "DatePickerDialogEndFragment");
                } else {
                    ToastUtils.showShort(getString(R.string.Please_starting_time));
                }
                break;
            case R.id.lly_lottery_settlement_ends:
                DatePickerDialogFragment datePickerDialogFragmentEnd = new DatePickerDialogFragment();
                datePickerDialogFragmentEnd.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                    @Override
                    public void onDateChoose(int year, int month, int day) {
                        if (timeCompare(startYear, startMonth, startDay, year, month, day)) {
                            tvLotterySettlementEnds.setText(year + "-" + month + "-" + day);
                            llySettlementSelect.setVisibility(View.GONE);
//                            selectEndType = 0;
                        } else {
                            ToastUtils.showShort(getString(R.string.should_be_greater));
                        }
//                        Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                }, getString(R.string.Please_end_time));
                datePickerDialogFragmentEnd.show(getSupportFragmentManager(), "DatePickerDialogEndFragment");
                break;
        }
    }


    public void showInfo() {
//        GameListBean gameBean = new GameListBean();
//        gameBean.setGameName(getString(R.string.all));
//        gameBean.setGameAlias("");
//        gameBean.setTicketPrice("");
//        gameBean.setEnabled("00");
//        gameStringList.add(getString(R.string.all));
//        gameListBeans.add(gameBean);
//        bookList.add(getString(R.string.all));
//        changeSpinner(spSpSettlementType, gameAdapter, gameStringList);
//        changeSpinner(spSettlementBook, bookAdapter, bookList);
    }

    public void resetBook() {
        if (bookList.size() > 0) {
            bookList.clear();
        }
        bookList.add(getString(R.string.all));
//        changeSpinner(spSettlementBook, bookAdapter, bookList);
    }

    public void resetAll() {
        gameAlias = "90x5";
        tvReceivingSelect90.setBackgroundResource(R.drawable.reward_et_red_bg);
        tvReceivingSelect37.setBackgroundResource(R.drawable.reward_et_bg);
        tvReceivingSelect90.setTextColor(Color.WHITE);
        tvReceivingSelect37.setTextColor(Color.GRAY);
//        changeSpinner(spSpSettlementType, gameAdapter, gameStringList);
        resetBook();
        startYear = 0;
        startMonth = 0;
        startDay = 0;
        llyLotterySettlementTime.setVisibility(View.GONE);
        llySettlementSelect.setVisibility(View.GONE);
        selectEndType = 0;
        llySettlementNodata.setVisibility(View.VISIBLE);
    }

    private void showPopWindow() {
//        for (int i = 0; i < 6; i++) {
//            SettlementInfoBean settlementInfoBean = new SettlementInfoBean();
//            settlementInfoBean.setBookNum("123121" + i);
//            settlementInfoBean.setSettleStatus("00");
//            settlementInfoBeanList.add(settlementInfoBean);
//        }
//        lvSettlementPop.setLayoutManager(new LinearLayoutManager(this));
//        llySettlementPop.setVisibility(View.VISIBLE);
//        List<SettlementQueryBean.CashMoneyBean> list = settlementInfoBeanList;
//        tvSettlementPopTotal.setText(settlementQueryBean.getTotalMoney());
//        popSettlementAdapter = new SettlementNewestAdapter(this);
//        popSettlementAdapter.setList(list);
//        lvSettlementPop.setAdapter(popSettlementAdapter);
    }

    public void changeSpinner(Spinner spinner, ArrayAdapter adapter, List<String> list) {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, list);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        spinner.setAdapter(adapter);
    }

    private boolean timeCompare(int year, int month, int day, int endYear, int endMonth, int endDay) {
        if (year <= endYear) {
            if (year == endYear) {
                if (month <= endMonth) {
                    if (month == endMonth) {
                        if (day <= endDay) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static long getStringToDate(String dateString, String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            pattern = "yyyy-MM-dd";
        }
        if (pattern.equals("")) {
            return 0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String parseServerTime(String date, String format) {
        if (format == null || format.isEmpty()) {
//            format = "yyyy-MM-dd HH:mm:ss";
            format = "yyyy-MM-dd";
        }
        if (date.equals("")) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
//            return String.valueOf(sdf.parse(date).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //为listview动态设置高度
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public String timeStamp2Date(long time) {
        String language = SPUtils.look(this, SPkey.Language);
        String format = "";
        if (language.equals("English")) {
//            format = "dd-MM-yyyy HH:mm:ss";
            format = "dd-MM-yyyy";
        } else {
//            format = "yyyy-MM-dd HH:mm:ss";
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}
