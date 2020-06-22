package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jc.lottery.R;
import com.jc.lottery.adapter._36$7NumberAdapter;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.GameAddListBean;
import com.jc.lottery.bean.req.pos_GetDrawNotOpenQuery;
import com.jc.lottery.bean.req.pos_SelectGameAdd;
import com.jc.lottery.bean.resp.Resp_3_7_1_drawNotOpenQuery;
import com.jc.lottery.bean.type.ListSelectionNumerical;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.CountBetsUtils;
import com.jc.lottery.util.FormatUtil;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.MoneyUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.util.VibratorUtils;
import com.jc.lottery.view.CountdownTextView;
import com.jc.lottery.view.MyGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class _49x6SelectNumActivity extends BaseActivity implements AdapterView.OnItemClickListener, SensorEventListener {

    private final String BROADCAST1 = "com.caiso.lottery.bet";
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    @BindView(R.id.header_type_one_back)
    LinearLayout headerTypeOneback;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id._37x6_select_listview)
    ListView _37x6SelectListview;
    @BindView(R.id._37x6_select_lin)
    LinearLayout _37x6SelectLin;
    @BindView(R.id.s37x6_tv_all)
    TextView s37x6TvAll;
    @BindView(R.id.sna_gridView1)
    MyGridView sna_gridView1;
    @BindView(R.id.sna_gridView_top)
    MyGridView sna_gridView_top;
    @BindView(R.id.tv_sna)
    TextView balls;
    @BindView(R.id.s37x6_tv_money)
    TextView s37x6_tv_money;
    @BindView(R.id.button_sure)
    Button buttonAdd;
    @BindView(R.id.tv_qihao_37x6)
    TextView tvQihao37x6;
    @BindView(R.id.tv_timer_37x6)
    CountdownTextView tvTimer37x6;

    int[] ball = {49};
    int[] ballTop = {10};
    private _36$7NumberAdapter adapter1;
    private _36$7NumberAdapter adapterTop;
    private String isRandomSelection = "0"; // 自选 0  机选 1
    private int mZhuShu;
    private int num;
    //     每个彩种的已选择的号码的LIST
    private List<String> selectList1 = new ArrayList<String>();
    private List<String> selectListTop = new ArrayList<String>();
    //    可选择的号码
    private List<String> numsList1 = new ArrayList<String>();
    private List<String> numsListTop = new ArrayList<String>();
    private String strBallHaoMa;
    private String number = "";
    private int bet_position;
    private SensorManager mSensorManager;
    private long time_daojishi;
    private String termNo;
    private List<GameAddListBean> gameAddList = new ArrayList<GameAddListBean>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_49x6_select_num;
    }

    @Override
    public void getPreIntent() {
        strBallHaoMa = getIntent().getStringExtra("ball");
        bet_position = getIntent().getIntExtra("bet_position", 0);
        number = getIntent().getStringExtra("number");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            Sensor mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        // values[0]:X轴，values[0]：Y轴，values[0]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(values[0]) > Config.ShakeAccuracy || Math.abs(values[1]) > Config.ShakeAccuracy || Math.abs(values[2]) > Config.ShakeAccuracy)) {
                LogUtils.i("传感器震动  摇一摇选号");
                // 摇动手机后，再伴随震动提示~~
                VibratorUtils.Vibrate(_49x6SelectNumActivity.this, 400);
                randomBall();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tvTimer37x6 != null) {
            tvTimer37x6.stopAll();
            tvTimer37x6 = null;
        }
    }

    @Override
    public void initView() {
//    初始化数据
        CountBetsUtils.getnums(49, numsList1);
        CountBetsUtils.getnums(10, numsListTop);

        sna_gridView1.setSelector(getResources().getDrawable(R.drawable.transparent));
        adapter1 = new _36$7NumberAdapter(this, numsList1, selectList1);
        sna_gridView1.setAdapter(adapter1);
        sna_gridView1.setOnItemClickListener(this);

        sna_gridView_top.setSelector(getResources().getDrawable(R.drawable.transparent));
        adapterTop = new _36$7NumberAdapter(this, numsListTop, selectListTop);
        sna_gridView_top.setAdapter(adapterTop);
        sna_gridView_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                countBets(position, numsListTop, selectListTop, adapterTop, 1);
            }
        });
    }

    @Override
    public void initData() {

        tvTimer37x6.init(CountdownTextView.what_count_down_37x6, new CountdownTextView.TimeListener() {
            @Override
            public void timedown() {
                LogUtils.e("时间到了啊~~~别忘了联网请求一下~~~");
                tvQihao37x6.setText(String.format(getString(R.string.ju_x_qi_endtime), tvTimer37x6.getTerm_no() + ""));
                tvTimer37x6.start(Integer.parseInt(termNo), time_daojishi);
                GetOrUpTermNo();
            }

            @Override
            public void buyDown() {
                if (null != buttonAdd) {
                    buttonAdd.setEnabled(false);
                }
            }

            @Override
            public void timeStart() {
                if (null != buttonAdd) {
                    buttonAdd.setEnabled(true);
                }
            }
        });
        GetOrUpTermNo();
    }

    /**
     * 获取或更新奖期
     */
    private void GetOrUpTermNo() {
        ProgressUtil.showProgressDialog(this, this.getString(R.string.get_jiangqi_info));
        String account_name = SPUtils.look(this, SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo("49x6");
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name,dataBean );
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取37选6 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(_49x6SelectNumActivity.this, getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  37选6   " + response.body());
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            termNo = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getDrawNumber();
                            time_daojishi = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
//                            String data = timeStamp2Date(time_daojishi);
//                            String datas = timeStamp2Date(time_daojishi);
                            startOrUpTimer(termNo, time_daojishi);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void startOrUpTimer(String termNo, long time_daojishi) {
        tvQihao37x6.setText(String.format(getString(R.string.ju_x_qi_endtime), termNo));
        tvTimer37x6.start(Integer.parseInt(termNo), time_daojishi);
    }

    public void countBets(int arg2, List<String> numsList, List<String> selectList, _36$7NumberAdapter adapter, int n) {
        isRandomSelection = "0";// 不是机选
        whichGrid(arg2, numsList, selectList, adapter, n);
        num = CountBetsUtils.math2(selectList1.size(), Config._37x6_Code_Num_min);
        setByText(num);
    }

    /**
     * 添加/删除 选择 刷新adapter
     */
    public void whichGrid(int position, List<String> nums, List<String> select, BaseAdapter adapter, int n) {
        String p = nums.get(position);
        if (select.contains(p)) {
            select.remove(p);
        } else {
            if (select.size() == n) {
                ToastUtils.showShort(String.format(getString(R.string.max_select_x_num), n));
            } else {
                select.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setByText(int s) {
        mZhuShu = s;
        balls.setText(s + "");

//        s37x6_tv_money.setText(MoneyUtil.getIns().GetMoney(FormatUtil.addComma(Config.OneBetPrice * s)));
        s37x6_tv_money.setText(MoneyUtil.getIns().GetMoney(FormatUtil.addComma(Config.s49x6_R007_NoteMoney_min * s)));
    }

    @OnClick({R.id.s37x6_button_jixuan, R.id.button_sure, R.id.lin_s37x6_zoushitu,R.id.header_type_one_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_type_one_back:
                finish();
                break;
            case R.id.s37x6_button_jixuan:
                randomBall();
                break;
            case R.id.button_sure:
                if (!checkOk()) {
                    return;
                }
                String strBall = null;
                int n = Config._37x6_Code_Num_min;
                StringBuffer stringBuffer = new StringBuffer();
                ArrayList<ListSelectionNumerical> arrayList = new ArrayList<ListSelectionNumerical>();

                if ("ballHaoMa".equals(strBallHaoMa) && !"".equals(number)) {
                    String[] nums = number.split(",");
                    if (selectList1.size() < nums.length) {
                        ToastUtils.showShort(getString(R.string.duozhu_tip));
                        return;
                    }
                } else if (selectList1.size() < n) {
                    ToastUtils.showShort(String.format(getString(R.string.min_select_x_num), n));
                    return;
                }

                if (selectList1.size() >= n) {
                    Collections.sort(selectList1);
                    for (int i = 0; i < selectList1.size(); i++) {
                        if (i == selectList1.size() - 1) {
                            stringBuffer.append(selectList1.get(i));
                            stringBuffer.append("");
                        } else {
                            stringBuffer.append(selectList1.get(i));
                            stringBuffer.append(",");
                        }
                    }
                    stringBuffer.append("|");
                    stringBuffer.append(selectListTop.get(0));
                    ListSelectionNumerical suoShui = new ListSelectionNumerical();
                    strBall = stringBuffer.toString();
                    suoShui.setStrHasoMa(strBall);
                    suoShui.setZhuShu(mZhuShu);
                    suoShui.setIsRandomSelection(isRandomSelection);
                    suoShui.setType(getString(R.string.s49x6name));
                    suoShui.setZuheType(getString(R.string.s49x6name));

                    arrayList.add(suoShui);
                    if ("ballHaoMa".equals(strBallHaoMa)) {
                        Intent intent = new Intent(BROADCAST1);
                        intent.putParcelableArrayListExtra("haoma", arrayList);
                        intent.putExtra("type", getString(R.string.s49x6name));
                        intent.putExtra("zuhe", getString(R.string.s49x6name));
                        sendBroadcast(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(_49x6SelectNumActivity.this, _49x6BettingActivity.class);
                        intent.putParcelableArrayListExtra("haoma", arrayList);
                        intent.putExtra("type", getString(R.string.s49x6name));
                        intent.putExtra("zuhe", getString(R.string.s49x6name));
                        startActivity(intent);
                        setClear();
                    }
                } else {
//                    ToastUtils.showShort("每位数字至少选择" + n + "个");
                }
                break;
            case R.id.lin_s37x6_zoushitu:
                Intent intent = new Intent(_49x6SelectNumActivity.this, _49x6HistoryQueryActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 规则检查
     *
     * @return
     */
    private boolean checkOk() {
        try {
            int numZhu = Integer.parseInt(balls.getText().toString());
            if (numZhu > Config.s49x6_R001_NoteNum_max) {
                ToastUtils.showShort(String.format(getString(R.string.max_can_x_zhu), Config.s49x6_R001_NoteNum_max));
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 机选一注
     */
    private void randomBall() {
        setClear();
        int n = Config._37x6_Code_Num_min;
        Random random = new Random();

        if ("ballHaoMa".equals(strBallHaoMa) && !"".equals(number)) {
            String[] nums = number.split(",");
            while (selectList1.size() < nums.length) {
                int ballId_01 = random.nextInt(ball[0]) + 1;
                String id1 = changString(ballId_01);
                if (!selectList1.contains(id1)) {
                    selectList1.add(id1);
                }
            }
        } else {
            while (selectList1.size() < n) {
                int ballId_01 = random.nextInt(ball[0]) + 1;
                String id1 = changString(ballId_01);
                if (!selectList1.contains(id1)) {
                    selectList1.add(id1);
                }
            }
        }
        int ballTops = random.nextInt(ballTop[0]) + 1;
        selectListTop.add(changString(ballTops));
        isRandomSelection = "1";
        adapter1.notifyDataSetChanged();
        adapterTop.notifyDataSetChanged();
        num = CountBetsUtils.math2(selectList1.size(), n);
        setByText(num);
    }

    public void setClear() {
        setByText(0);
        selectList1.clear();
        adapter1.notifyDataSetChanged();
        selectListTop.clear();
        adapterTop.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        countBets(position, numsList1, selectList1, adapter1, Config._37x6_Code_Num_max);
    }

    private String changString(int id) {
        String id1 = "";
        if (id < 10) {
            id1 = "0" + id;
        } else {
            id1 = String.valueOf(id);
        }
        return id1;
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

}
