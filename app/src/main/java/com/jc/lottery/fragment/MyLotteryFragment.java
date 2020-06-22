package com.jc.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.ImmediatelActivationActivity;
import com.jc.lottery.activity.immediate.ImmediatelSettlementActivity;
import com.jc.lottery.activity.immediate.LotteryPurchaseActivity;
import com.jc.lottery.activity.lottery._37x6SelectNumActivity;
import com.jc.lottery.adapter.MyImmediatesAdapter;
import com.jc.lottery.adapter.MyLotteryAdapter;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.MyImmediateBean;
import com.jc.lottery.bean.req.TerminalPerQueryInfo;
import com.jc.lottery.bean.req.pos_GetDrawNotOpenQuery;
import com.jc.lottery.bean.req.pos_GetQueryGameList;
import com.jc.lottery.bean.req.pos_Validation;
import com.jc.lottery.bean.resp.Resp_3_7_1_drawNotOpenQuery;
import com.jc.lottery.bean.resp.Resp_queryGameList;
import com.jc.lottery.bean.type.EncryptedStateBean;
import com.jc.lottery.bean.type.Group;
import com.jc.lottery.bean.type.MumericalSelection;
import com.jc.lottery.content.Constant;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.GetPermissionsUtil;
import com.jc.lottery.util.GlideImageLoader;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.TimeManager;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jc.lottery.util.AesOrMd5.md5OrAes;


public class MyLotteryFragment extends BaseFragment {
    Group<MumericalSelection> group;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lly_my_lottery_ticket)
    PercentLinearLayout llyMyLotteryTicket;
    @BindView(R.id.lly_my_lottery_activation)
    PercentLinearLayout llyMyLotteryActivation;
    @BindView(R.id.lly_my_lottery_settlement)
    PercentLinearLayout llyMyLotterySettlement;
    @BindView(R.id.rel_my_lottery_lt)
    RecyclerView relMyLotteryLt;
    @BindView(R.id.rel_my_lottery)
    RecyclerView relMyLottery;
    @BindView(R.id.lly_my_lottery_immediate)
    PercentLinearLayout llyMyLotteryImmediate;
    @BindView(R.id.lly_my_lottery_lt)
    PercentLinearLayout llyMyLotteryLt;
    @BindView(R.id.lly_my_immediate_pop)
    LinearLayout llyMyImmediatePop;
    @BindView(R.id.img_my_immediate_no)
    ImageView imgMyImmediateNo;
    @BindView(R.id.bt_my_immediate_pop_yes)
    Button btMyImmediatePopYes;
    @BindView(R.id.bt_my_immediate_pop_no)
    Button btMyImmediatePopNo;
    @BindView(R.id.tv_lock_title)
    TextView tvLockTitle;
    @BindView(R.id.bt_lock_pop_no)
    Button btLockPopNo;
    @BindView(R.id.bt_lock_pop_yes)
    Button btLockPopYes;
    @BindView(R.id.lly_lock_pop)
    LinearLayout llyLockPop;
    @BindView(R.id.img_lock_icon)
    ImageView imgLockIcon;
    @BindView(R.id.et_my_immediate_password)
    EditText etMyImmediatePassword;
    Unbinder unbinder;
    @BindView(R.id.rel_my_jack)
    RecyclerView relMyJack;
    @BindView(R.id.lly_my_lottery_jack)
    PercentLinearLayout llyMyLotteryJack;
    private List<Integer> images;
    private RelativeLayout rl_guide;
    private ArrayList<String> gameListName = new ArrayList<String>();
    private List<MyImmediateBean> immediateBeanList = new ArrayList<MyImmediateBean>();
    private List<MyImmediateBean> ltBeanList = new ArrayList<MyImmediateBean>();
    private List<MyImmediateBean> jcBeanList = new ArrayList<MyImmediateBean>();
    private MyLotteryAdapter myLotteryLtAdapter;
    private MyLotteryAdapter myLotteryAdapter;
    private MyLotteryAdapter myLotteryJcAdapter;
    private MyImmediatesAdapter myImmediateAdapter;
    private Resp_queryGameList respQueryGameList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        group = new Group<MumericalSelection>();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_lottery;
    }

    @Override
    protected void initView(View view) {
//        rl_guide = (RelativeLayout) view.findViewById(R.id.rl_guide);
//        rl_guide.setVisibility(View.GONE);
        String language = SPUtils.look(getActivity(), SPkey.Language);
        if (images == null) {
            images = new ArrayList<>();
//            if (language.equals("Chinese")) {
//                images.add(new Integer(R.drawable.banner));
//                images.add(new Integer(R.drawable.banner));
//            } else {
//                images.add(new Integer(R.drawable.banner_ens));
//                images.add(new Integer(R.drawable.banner_en02));
//                images.add(new Integer(R.drawable.banner_en03));
//                images.add(new Integer(R.drawable.banner_en04));
//            }
            images.add(new Integer(R.drawable.banners));
            images.add(new Integer(R.drawable.banners));
        }
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.isAutoPlay(true);
        banner.setDelayTime(5000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(images);
        banner.start();
    }

    @Override
    public void initListener() {
        relMyLotteryLt.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        relMyLottery.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        relMyJack.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    public void initData() {
//        GetGameList();
        terminalPerQueryInfo();

//        initGameList(gameListName);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }


    @OnClick({R.id.lly_my_lottery_ticket, R.id.lly_my_lottery_activation, R.id.lly_my_lottery_settlement, R.id.bt_my_immediate_pop_yes, R.id.bt_my_immediate_pop_no, R.id.lly_my_immediate_pop, R.id.bt_lock_pop_no, R.id.lly_lock_pop})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lly_my_lottery_ticket:
                intent.setClass(getActivity(), LotteryPurchaseActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_my_lottery_activation:
                intent.setClass(getActivity(), ImmediatelActivationActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_my_lottery_settlement:
                intent.setClass(getActivity(), ImmediatelSettlementActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_my_immediate_pop_no:
                llyMyImmediatePop.setVisibility(View.GONE);
                etMyImmediatePassword.setText("");
//                notHide();
                break;
            case R.id.lly_my_immediate_pop:
                llyMyImmediatePop.setVisibility(View.GONE);
                etMyImmediatePassword.setText("");
//                notHide();
                break;
            case R.id.lly_lock_pop:
                llyLockPop.setVisibility(View.GONE);
//                notHide();
                break;
            case R.id.bt_lock_pop_no:
                llyLockPop.setVisibility(View.GONE);
//                notHide();
                break;
        }
    }

    private void notHide() {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity()
                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取游戏列表
     */
    private void GetGameList() {
        //游戏列表
//        gameBeans = new ArrayList<>();
//        gameListName = new ArrayList<>();
//        rv_main_gamelist.setAdapter(gameAdapter);
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        pos_GetQueryGameList.InitGameReqInfo initGameReqInfo = new pos_GetQueryGameList.InitGameReqInfo("", "00", "");
        pos_GetQueryGameList.DataBean dataBean = new pos_GetQueryGameList.DataBean(initGameReqInfo);
        pos_GetQueryGameList gameQuery = new pos_GetQueryGameList(account_name, dataBean);
        String s = new Gson().toJson(gameQuery);
        LogUtils.e("  参数  ===" + s);

        OkGo.<String>post(MyUrl.new_queryGameList)
                .upJson(s)
                .execute(new vStringCallback(getContext()) {

                    @Override
                    protected void vOnSuccess(Response<String> response) {
                        LogUtils.e("" + response.body());
                        try {
                            respQueryGameList = new Gson().fromJson(response.body(), Resp_queryGameList.class);
                            gameListName.clear();
                            for (Resp_queryGameList.GameListBean gameListBean : respQueryGameList.getGameList()) {
                                if (TextUtils.equals(gameListBean.getAlias(), SPkey.kind_k3)) {
                                    SPUtils.save(getActivity(), SPkey.kind_k3, gameListBean.getId() + "");
                                    gameListName.add(Constant.Game_Name_k3);
                                } else if (TextUtils.equals(gameListBean.getAlias(), SPkey.kind_37x6)) {
                                    SPUtils.save(getActivity(), SPkey.kind_37x6, gameListBean.getId() + "");
                                    gameListName.add(Constant.Game_Name_37x6);
                                } else if (TextUtils.equals(gameListBean.getAlias(), SPkey.kind_90x5)) {
                                    SPUtils.save(getActivity(), SPkey.kind_90x5, gameListBean.getId() + "");
                                    gameListName.add(Constant.Game_Name_90x5);
                                } else if (TextUtils.equals(gameListBean.getAlias(), SPkey.kind_49x6)) {
                                    SPUtils.save(getActivity(), SPkey.kind_49x6, gameListBean.getId() + "");
                                    gameListName.add(Constant.Game_Name_49x6);
                                }
                            }
                            initGameList(gameListName);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取权限
     */
    private void terminalPerQueryInfo() {
        String account_name = SPUtils.look(getActivity(), SPkey.username, Config.Test_accountName);
        TerminalPerQueryInfo.PermissionsInfo permissionsInfo = new TerminalPerQueryInfo.PermissionsInfo("2");
        TerminalPerQueryInfo.DataBean dataBean = new TerminalPerQueryInfo.DataBean(permissionsInfo);
        TerminalPerQueryInfo terminalPerQueryInfo = new TerminalPerQueryInfo(account_name, dataBean);
        String s = new Gson().toJson(terminalPerQueryInfo);
        LogUtils.e("  时间校准 请求参数  " + s);
        OkGo.<String>post(MyUrl.terminalPerQuery)
                .upJson(s)
                .execute(new vStringCallback(getActivity()) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 获取权限  返回内容 " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONArray perJson = jsonObject.getJSONArray("permissionsList");
//                            if (perJson.length() > 0) {
                            SPUtils.save(getActivity(), SPkey.permissionsList, perJson.toString());
                            showInfoView();
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showInfoView() {
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        if (getPermissionsUtil.getPermissions("yddjkcdp")) {
            immediateBeanList.add(new MyImmediateBean(4, getString(R.string.ticket_collection), R.drawable.my_jkc_one, isState("yddjkcdp"), "yddjkcdp"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcjh")) {
            immediateBeanList.add(new MyImmediateBean(2, getString(R.string.activation), R.drawable.my_jkc_two, isState("yddjkcjh"), "yddjkcjh"));
        }
        if (getPermissionsUtil.getPermissions("yddjkcjs")) {
            immediateBeanList.add(new MyImmediateBean(3, getString(R.string.settlement), R.drawable.my_jkc_three, isState("yddjkcjs"), "yddjkcjs"));
        }
        if (immediateBeanList.size() > 0) {
            myImmediateAdapter = new MyImmediatesAdapter(this, getContext(), "0");
            myImmediateAdapter.setList(immediateBeanList);
            relMyLottery.setAdapter(myImmediateAdapter);
            llyMyLotteryImmediate.setVisibility(View.VISIBLE);
        } else {
            llyMyLotteryImmediate.setVisibility(View.GONE);
        }
        if (getPermissionsUtil.getPermissions("yddlttz")) {
            GetGameList();
        } else {
            llyMyLotteryLt.setVisibility(View.GONE);
        }
        if (getPermissionsUtil.getPermissions("yddsfctz")) {
            jcBeanList.add(new MyImmediateBean(6, getString(R.string.jackpot), R.drawable.jackpot_icon, false, "yddsfc"));
        } else {
            llyMyLotteryJack.setVisibility(View.GONE);
        }
        myLotteryJcAdapter = new MyLotteryAdapter(getActivity(), this);
        myLotteryJcAdapter.setList(jcBeanList);
        relMyJack.setAdapter(myLotteryJcAdapter);
        llyMyLotteryImmediate.setVisibility(View.VISIBLE);
    }

    //乐透
    private void initGameList(ArrayList<String> gameListName) {
        GetPermissionsUtil getPermissionsUtil = new GetPermissionsUtil();
        if (getPermissionsUtil.getPermissions("yddlttz")) {
            for (int i = 0; i < gameListName.size(); i++) {
                if (gameListName.get(i).equals(Constant.Game_Name_37x6)) {
                    ltBeanList.add(new MyImmediateBean(4, getString(R.string.s37x6name), R.drawable.icon_376, isState("yddlt37"), "yddlt37"));
                } else if (gameListName.get(i).equals(Constant.Game_Name_90x5)) {
                    ltBeanList.add(new MyImmediateBean(5, getString(R.string.s90x5name), R.drawable.icon_905, isState("yddlt90"), "yddlt90"));
                } else if (gameListName.get(i).equals(Constant.Game_Name_49x6)) {
                    ltBeanList.add(new MyImmediateBean(7, getString(R.string.s49x6name), R.drawable.icon_496, isState("yddlt496"), "yddlt496"));
                }
            }
        }
//        ltBeanList.add(new MyImmediateBean(4, getString(R.string.s37x6name), R.drawable.icon_376, false, ""));
//        ltBeanList.add(new MyImmediateBean(5, getString(R.string.s90x5name), R.drawable.icon_905, false, ""));
        if (ltBeanList.size() > 0) {
            myLotteryLtAdapter = new MyLotteryAdapter(getActivity(), this);
            myLotteryLtAdapter.setList(ltBeanList);
            relMyLotteryLt.setAdapter(myLotteryLtAdapter);
            llyMyLotteryLt.setVisibility(View.VISIBLE);
        } else {
            llyMyLotteryLt.setVisibility(View.GONE);
        }
    }

    private void getValidation(final int popType, String password, final int pos, final boolean type, final String name, final String stateType) {
        ProgressUtil.showProgressDialog(getContext(), getString(R.string.waitting));
        String account_name = SPUtils.look(getContext(), SPkey.username);
        pos_Validation.UserInfo userInfo = new pos_Validation.UserInfo(md5OrAes(password));
        pos_Validation.DataBean dataBean = new pos_Validation.DataBean(userInfo);
        pos_Validation pos_validation = new pos_Validation(account_name, dataBean);
        String s1 = new Gson().toJson(pos_validation);
        OkGo.<String>post(MyUrl.pos_Validation)
                .upJson(s1)
                .execute(new vStringCallback(getContext()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                llyMyImmediatePop.setVisibility(View.GONE);
                                etMyImmediatePassword.setText("");
                                String json = SPUtils.look(getActivity(), SPkey.encryptedState);
                                Type listType = new TypeToken<List<EncryptedStateBean>>() {
                                }.getType();
                                List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getName().equals(name)) {
                                        list.get(i).setState(type);
                                    }
                                }
                                String s = new Gson().toJson(list);
                                SPUtils.save(getActivity(), SPkey.encryptedState, s);
                                if (popType == 1) {
                                    if (name.indexOf("jkc") != -1) {
                                        showIntent("jkc", pos);
                                    } else {
                                        showIntent("lt", pos);
                                    }
                                } else {
                                    if (name.indexOf("jkc") != -1) {
                                        immediateBeanList.get(pos).setState(type);
                                        myLotteryAdapter.notifyItemChanged(pos);
                                    } else {
                                        ltBeanList.get(pos).setState(type);
                                        myLotteryLtAdapter.notifyItemChanged(pos);
                                    }
                                }
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
        notHide();
    }

    private void showIntent(String type, int pos) {
        Intent intent = new Intent();
        int id = 0;
        if (type.equals("jkc")) {
            id = immediateBeanList.get(pos).getId();
        } else {
            id = ltBeanList.get(pos).getId();
        }
        switch (id) {
            case 1:
                intent.setClass(getActivity(), LotteryPurchaseActivity.class);
                getActivity().startActivity(intent);
                break;
            case 2:
                intent.setClass(getActivity(), ImmediatelActivationActivity.class);
                getActivity().startActivity(intent);
                break;
            case 3:
                intent.setClass(getActivity(), ImmediatelSettlementActivity.class);
                getActivity().startActivity(intent);
                break;
            case 4:
                jump37x6("37x6");
                break;
            case 5:
                break;
        }
    }

    private void jump37x6(String gameAlias) {
        ProgressUtil.showProgressDialog(getActivity(), getActivity().getString(R.string.get_jiangqi_info));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo(gameAlias);
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name, dataBean);
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取37选6 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(getActivity()) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  37选6   " + response.body());
                        if (!response.equals("")) {
                            getActivity().startActivity(new Intent(getActivity(), _37x6SelectNumActivity.class));
                            return;
                        }
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            long start_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getStartTime();
                            if (TimeManager.getInstance().getServiceTime() < start_time) {
                                ToastUtils.showShort(getActivity().getString(R.string.now_is_not_start));
                                return;
                            }
                            long end_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
                            if (TimeManager.getInstance().getServiceTime() < end_time) {
                                getActivity().startActivity(new Intent(getActivity(), _37x6SelectNumActivity.class));
                            } else {
                                ToastUtils.showShort(getActivity().getString(R.string.now_is_end));
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(getActivity().getString(R.string.get_info_fail));
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(getActivity().getString(R.string.temp_stop));
                        }
                    }
                });
    }

    public void showPopView(final int pos, final int popType, final boolean type, final String name, final String stateType) {
        llyMyImmediatePop.setVisibility(View.VISIBLE);
        btMyImmediatePopYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etMyImmediatePassword.getText().toString().trim();
                if (!password.equals("")) {
                    getValidation(popType, password, pos, type, name, stateType);
                } else {
                    ToastUtils.showShort(getString(R.string.please_enter_secondary_password));
                }
            }
        });
    }

    public void showLockView(final int pos, final boolean type, final String name, final String title, final String stateType) {
        llyLockPop.setVisibility(View.VISIBLE);
        tvLockTitle.setText(title);
        if (type) {
            imgLockIcon.setImageResource(R.drawable.suo);
            btLockPopYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String json = SPUtils.look(getActivity(), SPkey.encryptedState);
                    Type listType = new TypeToken<List<EncryptedStateBean>>() {
                    }.getType();
                    List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals(name)) {
                            list.get(i).setState(type);
                        }
                    }
                    String s = new Gson().toJson(list);
                    SPUtils.save(getActivity(), SPkey.encryptedState, s);
                    if (name.indexOf("jkc") != -1) {
                        immediateBeanList.get(pos).setState(type);
                        myLotteryAdapter.notifyItemChanged(pos);
                    } else {
                        ltBeanList.get(pos).setState(type);
                        myLotteryLtAdapter.notifyItemChanged(pos);
                    }
//                    immediateBeanList.get(pos).setState(type);
//                    myLotteryAdapter.notifyItemChanged(pos);
                    llyLockPop.setVisibility(View.GONE);
                }
            });
        } else {
            imgLockIcon.setImageResource(R.drawable.jsuo);
            btLockPopYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopView(pos, 2, type, name, "");
                    llyLockPop.setVisibility(View.GONE);
                }
            });
        }

    }

    private boolean isState(String name) {
        String json = SPUtils.look(getActivity(), SPkey.encryptedState);
        Type listType = new TypeToken<List<EncryptedStateBean>>() {
        }.getType();
        List<EncryptedStateBean> list = new Gson().fromJson(json, listType);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i).isState();
            }
        }
        return false;
    }

}
