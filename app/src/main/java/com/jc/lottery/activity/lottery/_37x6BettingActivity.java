package com.jc.lottery.activity.lottery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jc.lottery.R;
import com.jc.lottery.adapter._37x6BetLotteryListAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.TicketListBean;
import com.jc.lottery.bean.req.pos_36x7order;
import com.jc.lottery.bean.req.pos_GetDrawNotOpenQuery;
import com.jc.lottery.bean.req.pos_findRule;
import com.jc.lottery.bean.req.pos_print_Notice;
import com.jc.lottery.bean.resp.Resp_3_7_1_drawNotOpenQuery;
import com.jc.lottery.bean.resp.Resp_Order_Success;
import com.jc.lottery.bean.resp.Resp_kuai3_Notice;
import com.jc.lottery.bean.resp.Resp_ruleInfo;
import com.jc.lottery.bean.type.BetMessage;
import com.jc.lottery.bean.type.Betting;
import com.jc.lottery.bean.type.Group;
import com.jc.lottery.bean.type.GroupPayment;
import com.jc.lottery.bean.type.ListSelectionNumerical;
import com.jc.lottery.bean.type.Payment;
import com.jc.lottery.content.Constant;
import com.jc.lottery.content.PrinterCommand;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.DialogInterface;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.DeviceConnFactoryManager;
import com.jc.lottery.util.DialogUtils;
import com.jc.lottery.util.FormatUtil;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.PrintDeviceUtil;
import com.jc.lottery.util.PrintUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.RuleUtils;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ThreadPool;
import com.jc.lottery.util.TimeUtils;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 37选6 确定页面
 */
public class _37x6BettingActivity extends BaseActivity {

    public static _37x6BettingActivity instances;
    private final String BROADCAST1 = "com.caiso.lottery.bet";

    @BindView(R.id.rel_title)
    RelativeLayout relTitle;
    @BindView(R.id.tv_k3order_term)
    TextView tvK3orderTerm;
    @BindView(R.id.tv_k3order_minute)
    TextView tvK3orderMinute;
    @BindView(R.id.tv_k3order_second)
    TextView tvK3orderSecond;
    @BindView(R.id.id_order_daojishi)
    LinearLayout idOrderDaojishi;
    @BindView(R.id.addlottery_tv_qihao)
    TextView addlottery_tv_qihao;
    @BindView(R.id.addlottery_tv_time)
    TextView addlottery_tv_time;
    @BindView(R.id.bt_winninglottery)
    Button bt_winninglottery;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.addlottery_edittext_bei)
    EditText addlottery_edittext_bei;
    @BindView(R.id.addlottery_edittext_qi)
    EditText edittext_qi_num;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tV_zhushu001)
    TextView tv_zhushu001;
    @BindView(R.id.tv_beishu)
    TextView tv_beishu;
    @BindView(R.id.tv_qishu)
    TextView tv_qishu;
    @BindView(R.id.button_add)
    Button button_add;
    @BindView(R.id.addlottery_tv_protocol)
    TextView addlotteryTvProtocol;

    private LinearLayout ll_order_daojishi;

    private String term_no;
    private String termNo;
    private String strNum = null;
    private String type;
    private int numBei = 1;
    private int numZhuiHao = 1;
    private int id = 0;
    private int drawId;
    private long end_time;
    private byte[] esc = {0x10, 0x04, 0x02};
    private byte[] tsc = {0x1b, '!', '?'};
    private byte[] cpcl = {0x1b, 0x68};
    private String[] strBeiShu;
    private String[] strBeiShuV;
    private String[] strZuiHao = new String[50];

    private Group<Payment> group;
    private ThreadPool threadPool;
    private List<ListSelectionNumerical> haoma;
    private Resp_Order_Success resp_orderSuccess;
    private GroupPayment instance;
    private _37x6BetLotteryListAdapter adapter;

    private BroadcastReceiver mReceiverss = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            @SuppressWarnings("unchecked")
            List<ListSelectionNumerical> haoma = (List<ListSelectionNumerical>) intent.getSerializableExtra("haoma");

            if (haoma != null && haoma.size() > 0) {
                for (int i = 0; i < haoma.size(); i++) {
                    ListSelectionNumerical suoShui = haoma.get(i);
                    Payment payment = new Payment();
                    payment.setMumber(suoShui.getStrHasoMa());
                    payment.setBetting(suoShui.getZhuShu());
                    payment.setZuheType(suoShui.getZuheType());
                    payment.setIsRandomSelection(suoShui.getIsRandomSelection());
                    payment.setType(type);
                    group.add(payment);
                    adapter.setGroup(group);
                }
                tv_zhushu001.setText(String.valueOf(getNum()));
                numBei = Integer.parseInt(tv_beishu.getText().toString());
                calcPrice();
            }
            listView.setSelection(group.size());
            unregisterReceiver(mReceiverss);

            if (instance.getGroup().size() < 5) {
                bt_winninglottery.setAlpha(1);
            } else {
                bt_winninglottery.setAlpha(0.6f);
            }
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("收到广播……");
            btnPrinterState();
        }
    };
    private boolean isShowConnectDialog = false;// 这次订单是否展示过连接打印机弹窗了

    @Override
    public void getPreIntent() {
//        ((LotteryApplication) getApplication()).addActivity(this);
        instances = this;
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        haoma = intent.getParcelableArrayListExtra("haoma");
    }

    @Override
    public int getLayoutId() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_37x6_betting;
    }

    @Override
    public void initView() {
        View bottomView = LayoutInflater.from(this).inflate(R.layout.addlottery_listview_bottom, null);
        listView.addFooterView(bottomView);
        ll_order_daojishi = (LinearLayout) findViewById(R.id.id_order_daojishi);
        tv_beishu.setText(String.valueOf(numBei));
        ll_order_daojishi.setVisibility(View.GONE);

        instance = GroupPayment.instance();
        group = instance.getGroup();
        if (haoma != null && haoma.size() > 0) {
            for (int i = 0; i < haoma.size(); i++) {
                ListSelectionNumerical suoShui = haoma.get(i);
                Payment payment = new Payment();
                payment.setMumber(suoShui.getStrHasoMa());
                payment.setBetting(suoShui.getZhuShu());
                payment.setZuheType(suoShui.getZuheType());
                payment.setIsRandomSelection(suoShui.getIsRandomSelection());
                payment.setType(type);
                group.add(payment);
            }
        }
        adapter = new _37x6BetLotteryListAdapter(this, tv_money, tv_zhushu001, tv_beishu, tv_qishu);
        adapter.setGroup(group);
        listView.setAdapter(adapter);
        listView.setSelection(group.size());

        if (instance.getGroup().size() < 5) {
            bt_winninglottery.setAlpha(1);
        } else {
            bt_winninglottery.setAlpha(0.6f);
        }

        calcPrice();
        tv_zhushu001.setText(String.valueOf(getNum()));
        strBeiShu = getResources().getStringArray(R.array.beishu);
        strBeiShuV = new String[strBeiShu.length];

        for (int i = 0; i < strBeiShu.length; i++) {
            strBeiShuV[i] = strBeiShu[i] + getString(R.string.bei);
        }

        for (int i = 0; i < 50; i++) {
            strZuiHao[i] = String.valueOf(i + 1) + getString(R.string.qi);
        }
    }

    @Override
    public void initData() {
//        获取或者更新奖期
        GetOrUpTermNo();
//        获取规则
        GetRules(Constant.GAME_ALIAS_37X6);
    }

    @Override
    public void initListener() {
        edittext_qi_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int qi_num = Integer.parseInt(charSequence.toString());
                    tv_qishu.setText(qi_num + "");
                    calcPrice();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int qi_num = Integer.parseInt(editable.toString());
                    if (qi_num < 1) {
                        ToastUtils.showShort(getString(R.string.min_one));
                        edittext_qi_num.setText("1");
                        tv_qishu.setText("1");
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        addlottery_edittext_bei.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int bei_num = Integer.parseInt(charSequence.toString());
                    tv_beishu.setText(bei_num + "");
                    calcPrice();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    LogUtils.e("" + editable + "  " + editable.toString());
                    int qi_num = Integer.parseInt(editable.toString());
                    if (qi_num < 1) {
                        ToastUtils.showShort(getString(R.string.minyiBei));
                        addlottery_edittext_bei.setText("1");
                        tv_beishu.setText("1");
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void GetRules(final String gameAlias) {
        String account_name = SPUtils.look(this, SPkey.username, Config.Test_accountName);
        pos_findRule pos_findRule = new pos_findRule(account_name, gameAlias);
        String s = new Gson().toJson(pos_findRule);
        LogUtils.e("  请求参数  " + s);
        OkGo.<String>post(MyUrl.pos_findRule)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 返回内容 " + response.body());
                        Resp_ruleInfo resp_ruleInfo = new Gson().fromJson(response.body(), Resp_ruleInfo.class);
                        List<Resp_ruleInfo.RuleListBean> ruleList = resp_ruleInfo.getRuleList();
                        if (TextUtils.equals(gameAlias, Constant.GAME_ALIAS_37X6)) {
                            RuleUtils.init37x6Rule(ruleList);
                        }
                        addlotteryTvProtocol.setText(String.format(getString(R.string.max_can_x_bei), Config.s37x6_R002_NoteMultiple_max) + "," + String.format(getString(R.string.max_can_x_qi), Config.s37x6_R003_NotePeriod_max));
                    }
                });
    }

    private void GetOrUpTermNo() {
        ProgressUtil.showProgressDialog(this, this.getString(R.string.get_jiangqi_info));
        String account_name = SPUtils.look(this, SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo("37x6");
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name, dataBean);
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    protected void vOnSuccess(Response<String> response) {
                        LogUtils.e("37xuan 6   投注页面  获取奖期结果 " + response.body());
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            termNo = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getDrawNumber();
                            end_time = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
                            drawId = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getDrawId();
                            startOrUpTimer(termNo, end_time);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void startOrUpTimer(String termNo, long time_daojishi) {
        LogUtils.e("aaaa   " + termNo + "   " + time_daojishi);
        term_no = termNo;
        addlottery_tv_qihao.setText(String.format(getString(R.string.qici_no), term_no));
        addlottery_tv_time.setText(getString(R.string.end_time) + TimeUtils.getTime(this, time_daojishi));
        LogUtils.e("    截止时间 ：   ==   " + TimeUtils.getTime(this, time_daojishi));
    }

    public int getNum() {
        int num = 0;
        for (int i = 0; i < group.size(); i++) {
            Payment p = (Payment) group.get(i);
            num += p.getBetting();
        }
        return num;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.getGroup().clear();
    }

    /**
     * 规则检查
     *
     * @return
     */
    private boolean checkOk() {
        try {
            if (end_time != 0) {
                // 获取现在的时间，如果已过结束时间，不予投注
                long serviceTimeStamp = TimeUtils.get13ServiceTimeStamp();
                if (serviceTimeStamp > end_time) {
                    ToastUtils.showShort(getString(R.string.isstop));
                    return false;
                }
            }
            int money = Integer.parseInt(FormatUtil.removeComma(tv_money.getText().toString()));
            int numZhu = Integer.parseInt(tv_zhushu001.getText().toString());
            int numBei = Integer.parseInt(tv_beishu.getText().toString());
            int numQi = Integer.parseInt(tv_qishu.getText().toString());
            if (numZhu > Config.s37x6_R001_NoteNum_max) {
                ToastUtils.showShort(String.format(getString(R.string.max_can_x_zhu), Config.s37x6_R001_NoteNum_max));
                return false;
            }
            if (numBei > Config.s37x6_R002_NoteMultiple_max) {
                ToastUtils.showShort(String.format(getString(R.string.max_can_x_bei), Config.s37x6_R002_NoteMultiple_max));
                return false;
            }
            if (numQi > Config.s37x6_R003_NotePeriod_max) {
                ToastUtils.showShort(String.format(getString(R.string.max_can_x_qi), Config.s37x6_R003_NotePeriod_max));
                return false;
            }
            if (money > Config.s37x6_R005_NoteMoney_max / 100) {
                ToastUtils.showShort(getString(R.string.max_money) + FormatUtil.addComma(Config.s37x6_R005_NoteMoney_max) + getString(R.string.price_unit));
                return false;
            }
            if (money < Config.s37x6_R007_NoteMoney_min / 100) {
//                ToastUtils.showShort(getString(R.string.min_money) + FormatUtil.addComma(Config.s37x6_R007_NoteMoney_min) + getString(R.string.price_unit));
                ToastUtils.showShort(getString(R.string.min_select));
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 计算价钱
     */
    private void calcPrice() {
        try {
            numZhuiHao = Integer.parseInt(tv_qishu.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            numZhuiHao = 1;
        }
        try {
            numBei = Integer.parseInt(tv_beishu.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            numBei = 1;
        }
//        String addComma_price = FormatUtil.addComma(getNum() * Config.OneBetPrice * numBei * numZhuiHao);
        String addComma_price = FormatUtil.addComma(getNum() * Config.s37x6_R007_NoteMoney_min * numBei * numZhuiHao);
        tv_money.setText(MoneyUtil.getIns().GetMoney(addComma_price));
    }

    private void gotoPay(Resp_Order_Success resp_OrderSuccess) {
        if (Config.OpenPaypage) {
            try {
                ToastUtils.showShort("下单成功，请支付……");
//                Intent intent = new Intent(_37x6BettingActivity.this, Kuai3PaymentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("order_no", "1111");
//                bundle.putString("money", "2");
//                bundle.putString("type", type);
//                bundle.putString("deviceNo", "11");
//                bundle.putString("rawResult", "1");
//                bundle.putSerializable("paybean", resp_OrderSuccess);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                finish();//暂无
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            if (!BuildConfig.needPrint) {
//                printTicket(resp_OrderSuccess);
//                notice_37x6_ticket();
//            } else {
            printTicket(resp_OrderSuccess);
//            }
        }
    }

    private void printTicket(final Resp_Order_Success resp_orderSuccess) {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
            if (Config.MUST_NEED_PRINTER) {
                PrintDeviceUtil.connDevice(this);
                return;
            } else {
                //            如果不需要打印
                notice_37x6_ticket();
                return;
            }
        }
//        确定连接上了
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(" commond == " + DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand());
                if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.ESC) {
                    boolean b = PrintUtils.sendTicket(_37x6BettingActivity.this, resp_orderSuccess, end_time, Constant.action_print_notice_37x6);
//                    boolean b = PrintUtils.sendNewestTicket(_37x6BettingActivity.this, resp_orderSuccess, end_time, Constant.action_print_notice_37x6);
//                    boolean b = PrintUtils.sendTest;
                    if (!b) {
                        ToastUtils.showShort(getString(R.string.print_error));
                        return;
                    }
                } else {
                    ToastUtils.showShort(getString(R.string.print_error));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            IntentFilter filter = new IntentFilter(Constant.action_print_notice_37x6);
            registerReceiver(receiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnPrinterState() {
        LogUtils.e("打印机状态查询 &………");
        //打印机状态查询
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
            ToastUtils.showShort(getString(R.string.str_cann_printer));
            return;
        }

        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                Vector<Byte> data = new Vector<>(esc.length);
                if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.ESC) {

                    for (int i = 0; i < esc.length; i++) {
                        data.add(esc[i]);
                    }
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(data);
                } else if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.TSC) {
                    for (int i = 0; i < tsc.length; i++) {
                        data.add(tsc[i]);
                    }
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(data);
                } else if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.CPCL) {
                    for (int i = 0; i < cpcl.length; i++) {
                        data.add(cpcl[i]);
                    }
                    DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(data);
                }
            }
        });

        notice_37x6_ticket();
    }

    private void notice_37x6_ticket() {
        LogUtils.e("打印通知……");
        ProgressUtil.showProgressDialog(_37x6BettingActivity.this, getString(R.string.printing_wait), false);
        String account_name = SPUtils.look(_37x6BettingActivity.this, SPkey.username, Config.Test_accountName);
        String kind_37x6_game_id = SPUtils.look(this, SPkey.kind_37x6, "194");

        pos_print_Notice pos_print_Notice = new pos_print_Notice(Constant.ifc_print_notice_37x6, account_name, resp_orderSuccess.getOrderCode(), Integer.parseInt(kind_37x6_game_id),
                resp_orderSuccess.getData().getOrderInfo().getDrawNumber(), resp_orderSuccess.getData().getOrderInfo().getGameAlias());
        String s = new Gson().toJson(pos_print_Notice);
        LogUtils.e("  打印通知  请求参数  " + s);
        OkGo.<String>post(MyUrl.pos_37x6_notice)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {

                        LogUtils.e(" 返回内容 " + response.body());
                        final Resp_kuai3_Notice resp_kuai3_notice = new Gson().fromJson(response.body(), Resp_kuai3_Notice.class);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShort("" + resp_kuai3_notice.getMessage());
                                Betting bet = new Betting("bjks", "119513", "codes", "money", "5", "2", "出票成功", "XXX.00", "android", "10.00", "num");
                                Intent intent = new Intent(_37x6BettingActivity.this, BettingSuccessActivity.class);
                                intent.putExtra("yincang", false);// 不隐藏了
                                intent.putExtra("betting", bet);
                                intent.putExtra("info", resp_orderSuccess);
                                intent.putExtra("strType", Constant.Game_Name_37x6);
                                startActivity(intent); //暂无
                                button_add.setEnabled(true);
                                ProgressUtil.dismissProgressDialog();
                                finish();
                            }
                        }, 0);
                    }
                });
    }

    private void CreateOrder() {
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
            if (Config.MUST_NEED_PRINTER) {
                PrintDeviceUtil.connDevice(this, null);
                return;
            } else {
                if (!isShowConnectDialog) {
                    PrintDeviceUtil.connDevice(this, new DialogInterface() {
                        @Override
                        public void postive() {
                            CreateOrder();
                            isShowConnectDialog = false;
                        }

                        @Override
                        public void negative() {
                            isShowConnectDialog = false;
                        }
                    });
                    isShowConnectDialog = true;// 这次订单是否展示过连接打印机弹窗了
                    return;
                }
            }
        }

        ProgressUtil.showProgressDialog(_37x6BettingActivity.this, getString(R.string.waiting), false);
        button_add.setEnabled(false);
        pos_36x7order.DataBean.OrderInfoBean infoBean = new pos_36x7order.DataBean.OrderInfoBean();

        infoBean.setGameAlias(Constant.GAME_ALIAS_37X6);
        List<TicketListBean> ticketList = new ArrayList<>();
        for (Payment payment : group) {
            String[] member = payment.getMumber().split(",");
            String eachBetMode = Config.eachBetMode_dan;
            if (member.length > Config._37x6_Code_Num_min) {
                eachBetMode = Config.eachBetMode_fu;
            } else {
                eachBetMode = Config.eachBetMode_dan;
            }

//            把逗号替换为空格
            String s = payment.getMumber().replace(",", " ");
//            ticketList.add(new TicketListBean(s, Config.OneBetPrice * payment.getBetting() + "", eachBetMode));
            ticketList.add(new TicketListBean(s, Config.s37x6_R007_NoteMoney_min * payment.getBetting() + "", eachBetMode));
        }
        infoBean.setTicketList(ticketList);
        String temp_default;
        if (Config.MUST_LOGIN) {
            temp_default = "";
        } else {
            temp_default = Config.Test_Terminal;
        }
        String terminal = SPUtils.look(_37x6BettingActivity.this, SPkey.userActivationCode, temp_default);
        if (TextUtils.isEmpty(terminal)) {
            ToastUtils.showShort(getString(R.string.needreactivte));
            return;
        }
        infoBean.setTerminal(terminal);
        infoBean.setMultiDraw(tv_qishu.getText().toString());
        infoBean.setBetDouble(tv_beishu.getText().toString());
        infoBean.setNoteNumber(getNum() + "");

//        infoBean.setTotalMoney("" + Config.OneBetPrice * getNum() * Integer.parseInt(tv_qishu.getText().toString()) * Integer.parseInt(tv_beishu.getText().toString()));
        infoBean.setTotalMoney("" + Config.s37x6_R007_NoteMoney_min * getNum() * Integer.parseInt(tv_qishu.getText().toString()) * Integer.parseInt(tv_beishu.getText().toString()));

        boolean hasdan = false;
        boolean hasfu = false;
        for (TicketListBean ticketListBean : ticketList) {
            if (TextUtils.equals(ticketListBean.getEachBetMode(), Constant.BET_MODE_SINGLE)) {
                hasdan = true;
            } else if (TextUtils.equals(ticketListBean.getEachBetMode(), Constant.BET_MODE_DOUBLE)) {
                hasfu = true;
            }
        }
        if (hasdan && hasfu) {
            infoBean.setBetMode(Constant.BET_MODE_COMPOUND);
        } else if (hasdan) {
            infoBean.setBetMode(Constant.BET_MODE_SINGLE);
        } else if (hasfu) {
            infoBean.setBetMode(Constant.BET_MODE_DOUBLE);
        } else {
            infoBean.setBetMode(Constant.BET_MODE_COMPOUND);
        }
        infoBean.setDrawNumber(term_no + "");
        infoBean.setDataSource(Constant.DATA_SOURCE_ANDROID_APP_END);

        String accountName = SPUtils.look(_37x6BettingActivity.this, SPkey.username, Config.Test_accountName);
        pos_36x7order s36x7order = new pos_36x7order(accountName, infoBean);
        String s = new Gson().toJson(s36x7order);
//        String name = SPUtils.look(this,SPkey.username);
//        String data = new GetJsonUtils().readOrderFromSDcard(this);
//        Resp_Order_Success respOrderSuccess = new Gson().fromJson(data,Resp_Order_Success.class);
//        if(name.equals("lr")){
//            printTicket(respOrderSuccess);
//        }
        LogUtils.e("  下单 =  请求参数  ======  " + s);
        OkGo.<String>post(MyUrl.pos_37x6_appOrder)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    protected void vOnSuccess(Response<String> response) {
                        LogUtils.e("  返回结果：  " + response.body());
                        try {
                            resp_orderSuccess = new Gson().fromJson(response.body(), Resp_Order_Success.class);
                            ToastUtils.showShort(resp_orderSuccess.getMessage());
                            if (TextUtils.equals(Config.BetSuccessCode, resp_orderSuccess.getCode())) {
//                                下单成功
                                gotoPay(resp_orderSuccess);
                            } else {
                                button_add.setEnabled(true);
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            button_add.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            button_add.setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        button_add.setEnabled(true);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        button_add.setEnabled(true);
                    }
                });
    }

    private void random5Ball(int num) {
        for (int i = 0; i < num; i++) {
            BetMessage betMessage = new BetMessage();
            Payment payment = new Payment();
            payment.setType(type);
            payment.setIsRandomSelection("1");

            betMessage.setType(type);
            strNum = betMessage.getBet();
            payment.setZuheType("单式");
            payment.setBetting(1);
            payment.setIsRandomSelection("1");

            payment.setMumber(strNum);
            group.add(payment);
        }
        adapter.setGroup(group);
        listView.setSelection(group.size());
        tv_zhushu001.setText(String.valueOf(getNum()));
        calcPrice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.BLUETOOTH_REQUEST_CODE: {
                    ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
                    /*获取蓝牙mac地址*/
                    String macAddress = data.getStringExtra(Constant.EXTRA_DEVICE_ADDRESS);
                    coo(macAddress, id);
//                    ToastUtils.showShort(getString(R.string.connectedscusses));
//                    PrintDeviceUtil.cancelDialog();
                    break;
                }
            }
        }
    }

    private void coo(final String macAddress, final int sId) {
        final DeviceConnFactoryManager deviceConnFactoryManager = new DeviceConnFactoryManager.Build()
                .setId(sId)
                //设置连接方式
                .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
                //设置连接的蓝牙mac地址
                .setMacAddress(macAddress)
                .build();
        new Thread() {
            @Override
            public void run() {
                deviceConnFactoryManager.getDeviceConnFactoryManagers()[sId].openPort();
                PrintDeviceUtil.cancelDialog();
                ProgressUtil.dismissProgressDialog();
                Looper.prepare();
                ToastUtils.showShort(getString(R.string.connectedscusses));
                Looper.loop();
            }
        }.start();
//        //初始化话DeviceConnFactoryManager
//        new DeviceConnFactoryManager.Build()
//                .setId(id)
//                //设置连接方式
//                .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.BLUETOOTH)
//                //设置连接的蓝牙mac地址
//                .setMacAddress(macAddress)
//                .build();
//        //打开端口
//        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].openPort();
    }

    public void initType() {
        String number = "";
        if (group.size() > 0) {
            Payment bean = group.get(0);
            number = bean.getMumber();
        }
        Intent intent = new Intent(_37x6BettingActivity.this, _37x6SelectNumActivity.class);
        intent.putExtra("ball", "ballHaoMa");
        intent.putExtra("number", number);
        startActivity(intent);
    }

    public void setNum(int i, String s, String num, String isRandomSelection, StringBuffer buffer) {
        if (i == group.size() - 1) {
            buffer.append(s).append(num).append("-" + isRandomSelection);
        } else {
            buffer.append(s).append(num).append("-" + isRandomSelection).append("^");
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (instance.getGroup().size() > 0) {
                DialogUtils.showClearDataDialog(_37x6BettingActivity.this, getString(R.string.is_clear_sure));
            } else {
                _37x6BettingActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setAlpha(Group<Payment> group) {
        if (group.size() < 5) {
            bt_winninglottery.setAlpha(1);
        } else {
            bt_winninglottery.setAlpha(0.6f);
        }
    }

    @OnClick({R.id.header_type_one_back, R.id.bt_winninglottery, R.id.btlottery_delete, R.id.addlottery_tv_protocol, R.id.button_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_type_one_back:
                DialogUtils.showClearDataDialog(_37x6BettingActivity.this, getString(R.string.is_clear_sure));
                break;
            case R.id.addlottery_tv_protocol:
                /*Intent intent = new Intent(this, PlayTypesActivity.class);
                intent.putExtra("type", "platform_protocol");
                intent.putExtra("title", "彩票服务协议");
                startActivity(intent);*/
                break;
            case R.id.btlottery_delete:
                instance.getGroup().clear();

                tv_zhushu001.setText(String.valueOf(getNum()));
                calcPrice();
                adapter.notifyDataSetChanged();

                if (instance.getGroup().size() < 5) {
                    bt_winninglottery.setAlpha(1);
                } else {
                    bt_winninglottery.setAlpha(0.6f);
                }
                break;
            case R.id.button_add:

                if (!checkOk()) {
                    return;
                }
                CreateOrder();
                break;

            case R.id.bt_winninglottery:
                if (group.size() >= 5) {
                    ToastUtils.showShort(getString(R.string.up_groups_purchased_time));
                } else {
                    registerReceiver(mReceiverss, new IntentFilter(BROADCAST1));
                    initType();
                }
                break;
        }
    }

}