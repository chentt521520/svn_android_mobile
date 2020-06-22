package com.jc.lottery.activity.immediate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.adapter.ActivationAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.bean.req.pos_GetActivation;
import com.jc.lottery.bean.req.pos_GetActivationQuery;
import com.jc.lottery.bean.resp.ActivationQueryBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.SmoothCheckBox;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 激活页面
 */
public class ImmediatelActivationActivity extends BaseActivity {

    @BindView(R.id.lly_activation_back)
    LinearLayout llyActivationBack;
    @BindView(R.id.sp_activation_game)
    Spinner spActivationGame;
    @BindView(R.id.btn_activation_query)
    Button btnActivationQuery;
    @BindView(R.id.lly_activation_top)
    LinearLayout llyActivationTop;
    @BindView(R.id.lly_activation_content)
    LinearLayout llyActivationContent;
    @BindView(R.id.rel_activation)
    RecyclerView relActivation;
    @BindView(R.id.img_activation_all)
    ImageView imgscbActivationAll;
    @BindView(R.id.lly_activation_all)
    LinearLayout llyActivationAll;
    @BindView(R.id.lly_activation_select_top)
    LinearLayout llyActivationSelectTop;
    @BindView(R.id.btn_activation_submit)
    Button btnActivationSubmit;
    @BindView(R.id.lly_activation_bottom)
    PercentLinearLayout llyActivationBottom;
    @BindView(R.id.lly_receiving_nodata)
    PercentLinearLayout llyReceivingNodata;
    @BindView(R.id.tv_activation_num)
    TextView tvActivationNum;
    private List<String> nameList = new ArrayList<String>();
    private List<GameListBean> gameListBeans = new ArrayList<GameListBean>();
    private String gameAlias = "";
    private ArrayAdapter adapter;
    private List<ActivationQueryBean> activationQueryBeanList = new ArrayList<ActivationQueryBean>();
    private ActivationAdapter activationAdapter;
    private boolean allSelect = true;
    private int lastOffset = 0;
    private int lastPosition = 0;
    private boolean codeType = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_activation;
    }

    @Override
    public void getPreIntent() {
    }

    @Override
    public void initData() {
//        getRecordHttp();
    }

    @Override
    public void initListener() {
        relActivation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
    }

    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) relActivation.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(relActivation.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) relActivation.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    @Override
    public void initView() {
        relActivation.setLayoutManager(new LinearLayoutManager(this));
        activationAdapter = new ActivationAdapter(this);
//        activationAdapter.setHasStableIds(true);
        getGameHttpInfo();
        imgscbActivationAll.setImageResource(R.drawable.settle_yes);
//        activationAdapter.setOnItemClickListener(new ActivationAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                boolean openType = !activationQueryBeanList.get(position).isOpenType();
//                activationQueryBeanList.get(position).setOpenType(openType);
////                    list.get(position).setOpenType(openType);
//                activationAdapter.notifyItemChanged(position);
//                String tos = "";
//                for (int i = 0; i < activationQueryBeanList.size(); i++) {
//                    tos = tos + "," + activationQueryBeanList.get(i).isOpenType();
//                }
//                ToastUtils.showShort(tos);
//            }
//        });
    }

    @OnClick({R.id.lly_activation_back, R.id.btn_activation_query, R.id.lly_activation_all, R.id.btn_activation_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_activation_back:
                finish();
                break;
            case R.id.btn_activation_query:
                activationSelectHttp(gameAlias);
                break;
            case R.id.lly_activation_all:
                for (ActivationQueryBean activationQueryBean : activationQueryBeanList) {
                    activationQueryBean.setType(!allSelect);
                }
                if (!allSelect){
                    imgscbActivationAll.setImageResource(R.drawable.settle_yes);
                }else {
                    imgscbActivationAll.setImageResource(R.drawable.settle_no);
                }
//                scbActivationAll.setChecked(!allSelect, true);
                activationAdapter.notifyDataSetChanged();
                allSelect = !allSelect;
                showAllBtn();
                break;
            case R.id.btn_activation_submit:
                List<pos_GetActivation.ActiveList> activeList = new ArrayList<pos_GetActivation.ActiveList>();
                for (int i = 0; i < activationQueryBeanList.size(); i++) {
                    if (activationQueryBeanList.get(i).isType()) {
                        pos_GetActivation.ActiveList active = new pos_GetActivation.ActiveList();
                        active.setDeliveryId(activationQueryBeanList.get(i).getDeliveryId());
                        activeList.add(active);
                    }
                }
                if (activeList.size() > 0) {
                    activationHttp(activeList);
                } else {
                    ToastUtils.showShort(getString(R.string.please_select_least_one_order));
                }
                break;
        }
    }

    private void getGameHttpInfo() {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
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
//                                GameListBean gameBean = new GameListBean();
//                                gameBean.setGameName(getString(R.string.all));
//                                gameBean.setGameAlias("");
//                                gameBean.setTicketPrice("");
//                                gameBean.setEnabled("00");
//                                nameList.add(gameBean.getGameName());
//                                gameListBeans.add(gameBean);
                                for (int i = 0; i < gameList.length(); i++) {
                                    JSONObject json = gameList.getJSONObject(i);
                                    GameListBean gameListBean = new GameListBean();
                                    gameListBean.setGameName(json.getString("gameName"));
                                    gameListBean.setGameAlias(json.getString("gameAlias"));
                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
                                    gameListBean.setEnabled(json.getString("enabled"));
                                    if (gameListBean.getEnabled().equals("00")) {
                                        nameList.add(gameListBean.getGameName());
                                        gameListBeans.add(gameListBean);
                                    }
                                }
                                changeSpinner();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
//                        ToastUtils.showShort("response:" + response);
                        ProgressUtil.dismissProgressDialog();
                        if (response == null || response.message() == null) {
                            ToastUtils.showShort(getString(R.string.checknet));
                        }
                        finish();
                    }
                });
    }

    @SuppressLint("ResourceType")
    public void changeSpinner() {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, nameList);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        spActivationGame.setAdapter(adapter);
        gameAlias = nameList.get(0);
        spActivationGame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameAlias = nameList.get(position);
                showInView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void activationSelectHttp(String gameAlias) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetActivationQuery.DataBean.ActiveInfo activeInfo = new pos_GetActivationQuery.DataBean.ActiveInfo(gameAlias);
        pos_GetActivationQuery pos_getActivationQuery = new pos_GetActivationQuery(account_name, account_password, "3", activeInfo);
        String s1 = new Gson().toJson(pos_getActivationQuery);
        OkGo.<String>post(MyUrl.pos_GetActivationQuery)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("code").equals("00000")) {
                                Type listType = new TypeToken<List<ActivationQueryBean>>() {
                                }.getType();
                                activationQueryBeanList = new Gson().fromJson(jsonObject.getJSONArray("activeList").toString(), listType);
                                for (int i = 0; i < activationQueryBeanList.size(); i++) {
                                    activationQueryBeanList.get(i).setCreateTime(timeStamp2Date(Long.parseLong(activationQueryBeanList.get(i).getCreateTime())));
                                    activationQueryBeanList.get(i).setType(true);
                                    activationQueryBeanList.get(i).setOpenType(false);
                                }
//                                scbActivationAll.setChecked(true, false);
                                imgscbActivationAll.setImageResource(R.drawable.settle_yes);
                                allSelect = true;
                                activationAdapter.setList(activationQueryBeanList);
                                relActivation.setAdapter(activationAdapter);

                                if (activationQueryBeanList.size() > 0) {
                                    llyActivationBottom.setVisibility(View.VISIBLE);
                                    llyReceivingNodata.setVisibility(View.GONE);
                                    llyActivationSelectTop.setVisibility(View.VISIBLE);
                                } else {
                                    llyActivationBottom.setVisibility(View.GONE);
                                    llyReceivingNodata.setVisibility(View.VISIBLE);
                                    llyActivationSelectTop.setVisibility(View.GONE);
                                }
                                showAllBtn();
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

    private void activationHttp(List<pos_GetActivation.ActiveList> activeList) {
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        String account_name = SPUtils.look(this, SPkey.username);
        String account_password = SPUtils.look(this, SPkey.password);
        pos_GetActivation pos_getActivation = new pos_GetActivation(account_name, account_password, "3", activeList);
        String s1 = new Gson().toJson(pos_getActivation);
        OkGo.<String>post(MyUrl.pos_GetActivation)
                .upJson(s1)
                .execute(new vStringCallback(this) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            ToastUtils.showShort(jsonObject.getString("message"));
                            if (jsonObject.getString("code").equals("00000")) {
//                                activationInit();
//                                activationSelectHttp(logisticsCode);
                                activationSelectHttp(gameAlias);
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

    public void showAllBtn() {
        int select = 0;
        int selectNum = 0;
        for (int i = 0; i < activationQueryBeanList.size(); i++) {
            if (!activationQueryBeanList.get(i).isType()) {
                select++;
            } else {
                selectNum++;
            }
        }
        if (select != 0) {
            imgscbActivationAll.setImageResource(R.drawable.settle_no);
//            scbActivationAll.setChecked(false, false);
            allSelect = false;
        } else {
            imgscbActivationAll.setImageResource(R.drawable.settle_yes);
//            scbActivationAll.setChecked(true, false);
            allSelect = true;
        }
        tvActivationNum.setText(selectNum + " / " + activationQueryBeanList.size());
    }

    private void showInView(){
        llyActivationBottom.setVisibility(View.GONE);
        llyReceivingNodata.setVisibility(View.VISIBLE);
        llyActivationSelectTop.setVisibility(View.GONE);
    }

}
