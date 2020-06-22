package com.jc.lottery.activity.lottery;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jc.lottery.util.Config._90x5_Code_Num_max;
import static com.jc.lottery.util.Config._90x5_Code_Num_min;

public class s90x5Activity extends BaseActivity implements AdapterView.OnItemClickListener, SensorEventListener {
    private final String BROADCAST1 = "com.caiso.lottery.bet";
    @BindView(R.id.header_type_one_title)
    TextView headerTypeOneTitle;
    @BindView(R.id.lin_kl8_zoushitu)
    LinearLayout linKl8Zoushitu;

    @BindView(R.id.kl8_tv_all)
    TextView kl8TvAll;
    @BindView(R.id.sna_gridView1)
    MyGridView sna_gridView1;
    @BindView(R.id.kl8_button_jixuan)
    Button kl8ButtonJixuan;
    @BindView(R.id.tv_sna)
    TextView balls;
    @BindView(R.id.kl8_tv_money)
    TextView kl8_tv_money;
    @BindView(R.id.button_sure)
    Button buttonAdd;
    @BindView(R.id.tv_qihao_37x6)
    TextView tvQihao90x5;
    @BindView(R.id.tv_timer_37x6)
    CountdownTextView tvTimer90x5;

    int[] ball = {90};
    @BindView(R.id.sp_90x5_type)
    Spinner sp90x5Type;
    @BindView(R.id.lly_90x5_type_one)
    LinearLayout lly90x5TypeOne;
    @BindView(R.id.tv_90x5_type)
    TextView tv90x5Type;
    @BindView(R.id.lly_90x5_type_two)
    LinearLayout lly90x5TypeTwo;
    @BindView(R.id.header_type_one_back)
    LinearLayout headerTypeOneBack;
    private _36$7NumberAdapter adapter1;
    private String isRandomSelection = "0"; // 自选 0  机选 1
    private int mZhuShu;
    private int num;
    //     每个彩种的已选择的号码的LIST
    private List<String> selectList1 = new ArrayList<String>();
    //    可选择的号码
    private List<String> numsList1 = new ArrayList<String>();
    private String strBallHaoMa;
    private String number;
    private SensorManager mSensorManager;
    private long time_daojishi;
    private String termNo;
    private ArrayAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<GameAddListBean> gameAddList = new ArrayList<GameAddListBean>();
    private String gamePlayNum = "";
    private int pos = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_s90x5;
    }

    @Override
    public void getPreIntent() {
        strBallHaoMa = getIntent().getStringExtra("ball");
        number = getIntent().getStringExtra("number");
        gamePlayNum = getIntent().getStringExtra("gamePlayNum");
        if (null != gamePlayNum){
            showText(gamePlayNum);
        }
    }

    private void showText(String num){
        String s = getString(R.string.pick_5_numbers);
        if (num.equals("90x5001")) {
            kl8TvAll.setText(String.format(s,"1"));
        } else if (num.equals("90x5002")) {
            kl8TvAll.setText(String.format(s,"2"));
        } else if (num.equals("90x5003")) {
            kl8TvAll.setText(String.format(s,"3"));
        } else if (num.equals("90x5004")) {
            kl8TvAll.setText(String.format(s,"4"));
        } else if (num.equals("90x5005")) {
            kl8TvAll.setText(String.format(s,"5"));
        }else if (num.equals("90x5006")) {
            kl8TvAll.setText(String.format(s,"1"));
        }
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
                VibratorUtils.Vibrate(s90x5Activity.this, 400);
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
            try {
                mSensorManager.unregisterListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initView() {
//    初始化数据
        CountBetsUtils.getnums(90, numsList1);

        sna_gridView1.setSelector(getResources().getDrawable(R.drawable.transparent));
        adapter1 = new _36$7NumberAdapter(this, numsList1, selectList1);
        sna_gridView1.setAdapter(adapter1);
        sna_gridView1.setOnItemClickListener(this);
        if (null != number) {
            lly90x5TypeOne.setVisibility(View.GONE);
            lly90x5TypeTwo.setVisibility(View.VISIBLE);
        } else {
            lly90x5TypeOne.setVisibility(View.VISIBLE);
            lly90x5TypeTwo.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {

        tvTimer90x5.init(CountdownTextView.what_count_down_90x5, new CountdownTextView.TimeListener() {
            @Override
            public void timedown() {
                LogUtils.e("时间到了啊~~~别忘了联网请求一下~~~");
                tvQihao90x5.setText(String.format(getString(R.string.ju_x_qi_endtime), tvTimer90x5.getTerm_no() + ""));
                tvTimer90x5.start(Integer.parseInt(termNo), time_daojishi);
                GetOrUpTermNo();
            }

            @Override
            public void buyDown() {
                buttonAdd.setEnabled(false);
            }

            @Override
            public void timeStart() {
                buttonAdd.setEnabled(true);
            }
        });
        GetOrUpTermNo();
        GetSelectGameAdd();
    }

    @Override
    public void initListener() {
        sp90x5Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = getString(R.string.pick_5_numbers);
                if (gameAddList.get(position).getGamePlayNum().equals("90x5001")) {
                    _90x5_Code_Num_max = 1;
                    _90x5_Code_Num_min = 1;
                    kl8TvAll.setText(String.format(s,"1"));
                } else if (gameAddList.get(position).getGamePlayNum().equals("90x5002")) {
                    _90x5_Code_Num_max = 2;
                    _90x5_Code_Num_min = 2;
                    kl8TvAll.setText(String.format(s,"2"));
                } else if (gameAddList.get(position).getGamePlayNum().equals("90x5003")) {
                    _90x5_Code_Num_max = 3;
                    _90x5_Code_Num_min = 3;
                    kl8TvAll.setText(String.format(s,"3"));
                } else if (gameAddList.get(position).getGamePlayNum().equals("90x5004")) {
                    _90x5_Code_Num_max = 4;
                    _90x5_Code_Num_min = 4;
                    kl8TvAll.setText(String.format(s,"4"));
                } else if (gameAddList.get(position).getGamePlayNum().equals("90x5005")) {
                    _90x5_Code_Num_max = 5;
                    _90x5_Code_Num_min = 5;
                    kl8TvAll.setText(String.format(s,"5"));
                }else if (gameAddList.get(position).getGamePlayNum().equals("90x5006")) {
                    _90x5_Code_Num_max = 1;
                    _90x5_Code_Num_min = 1;
                    kl8TvAll.setText(String.format(s,"1"));
                }
                setClear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tvTimer90x5 != null) {
            tvTimer90x5.stopAll();
            tvTimer90x5 = null;
        }
    }

    /**
     * 获取或更新奖期
     */
    private void GetOrUpTermNo() {
        String account_name = SPUtils.look(this, SPkey.username);
        pos_GetDrawNotOpenQuery.DrawListInfo drawListInfo = new pos_GetDrawNotOpenQuery.DrawListInfo("90x5");
        pos_GetDrawNotOpenQuery.DataBean dataBean = new pos_GetDrawNotOpenQuery.DataBean(drawListInfo);
        pos_GetDrawNotOpenQuery pos_getDrawNotOpenQuery = new pos_GetDrawNotOpenQuery(account_name, dataBean);
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取90选5 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_drawNotOpenQuery)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(s90x5Activity.this, getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  90选5   " + response.body());
                        try {
                            Resp_3_7_1_drawNotOpenQuery resp_3_7_1_drawNotOpenQuery = new Gson().fromJson(response.body(), Resp_3_7_1_drawNotOpenQuery.class);
                            termNo = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getDrawNumber();
                            time_daojishi = resp_3_7_1_drawNotOpenQuery.getDrawList().get(0).getEndTime();
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
        tvQihao90x5.setText(String.format(getString(R.string.ju_x_qi_endtime), termNo));
        tvTimer90x5.start(Integer.parseInt(termNo), time_daojishi);
    }

    public void countBets(int arg2, List<String> numsList, List<String> selectList, _36$7NumberAdapter adapter, int n) {
        isRandomSelection = "0";// 不是机选
        whichGrid(arg2, numsList, selectList, adapter, n);
        num = CountBetsUtils.math2(selectList1.size(), Config._90x5_Code_Num_min);
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
        if (gameAddList.get(sp90x5Type.getSelectedItemPosition()).getGamePlayNum().equals("90x5006")){
            s = s * 89;
        }else if (null != gamePlayNum){
            if (gamePlayNum.equals("90x5006")){
                s = s * 89;
            }
        }
        mZhuShu = s;
        balls.setText(s + "");
//        kl8_tv_money.setText(MoneyUtil.getIns().GetMoney(FormatUtil.addComma(Config.OneBetPrice * s)));
        kl8_tv_money.setText(MoneyUtil.getIns().GetMoney(FormatUtil.addComma(Config.s90x5_R007_NoteMoney_min * s)));
    }

    @OnClick({R.id.kl8_button_jixuan, R.id.button_sure, R.id.lin_kl8_zoushitu,R.id.header_type_one_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_type_one_back:
                finish();
                break;
            case R.id.kl8_button_jixuan:
                randomBall();
                break;
            case R.id.button_sure:
                if (!checkOk()) {
                    return;
                }

                String strBall = null;
                int n = Config._90x5_Code_Num_min;
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

                    ListSelectionNumerical suoShui = new ListSelectionNumerical();
                    strBall = stringBuffer.toString();
                    suoShui.setStrHasoMa(strBall);
                    suoShui.setZhuShu(mZhuShu);
                    suoShui.setIsRandomSelection(isRandomSelection);
                    suoShui.setType("90选5");
                    suoShui.setZuheType("90选5");

                    arrayList.add(suoShui);
                    if ("ballHaoMa".equals(strBallHaoMa)) {
                        Intent intent = new Intent(BROADCAST1);
                        intent.putParcelableArrayListExtra("haoma", arrayList);
                        intent.putExtra("type", getString(R.string.s90x5name));
                        intent.putExtra("zuhe", getString(R.string.s90x5name));
                        sendBroadcast(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(s90x5Activity.this, s90x5BettingActivity.class);
                        intent.putParcelableArrayListExtra("haoma", arrayList);
                        intent.putExtra("gamePlayNum", gameAddList.get(sp90x5Type.getSelectedItemPosition()).getGamePlayNum());
                        intent.putExtra("type", getString(R.string.s90x5name));
                        intent.putExtra("zuhe", getString(R.string.s90x5name));
                        startActivity(intent);
                        setClear();
                    }
                } else {
//                    ToastUtils.showShort("每位数字至少选择" + n + "个");
                }
                break;
            case R.id.lin_kl8_zoushitu:
                Intent intent = new Intent(s90x5Activity.this, s90x5HistoryQueryActivity.class);
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
            if (numZhu > Config.s90x5_R001_NoteNum_max) {
                ToastUtils.showShort(String.format(getString(R.string.max_can_x_zhu), Config.s90x5_R001_NoteNum_max));
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
        int n = Config._90x5_Code_Num_min;
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
        isRandomSelection = "1";
        adapter1.notifyDataSetChanged();
        num = CountBetsUtils.math2(selectList1.size(), n);
        setByText(num);
    }

    public void setClear() {
        setByText(0);
        selectList1.clear();
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        countBets(position, numsList1, selectList1, adapter1, _90x5_Code_Num_max);
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

    /**
     * 获取玩法
     */
    private void GetSelectGameAdd() {
        String account_name = SPUtils.look(this, SPkey.username);
        pos_SelectGameAdd.GameInfo gameInfo = new pos_SelectGameAdd.GameInfo("90x5");
        pos_SelectGameAdd.DataBean dataBean = new pos_SelectGameAdd.DataBean(gameInfo);
        pos_SelectGameAdd pos_getDrawNotOpenQuery = new pos_SelectGameAdd(account_name, dataBean);
        String s = new Gson().toJson(pos_getDrawNotOpenQuery);
        LogUtils.e("  获取90选5 奖期 参数  ===" + s);
        OkGo.<String>post(MyUrl.pos_selectGameAdd)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(s90x5Activity.this, getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e("  90选5   " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONArray jsonArray = jsonObject.getJSONArray("gameAddList");
                            Type listType = new TypeToken<List<GameAddListBean>>() {
                            }.getType();
                            gameAddList = new Gson().fromJson(jsonArray.toString(), listType);
                            for (int i = 0; i < gameAddList.size(); i++) {
                                list.add(gameAddList.get(i).getGamePlayName());
                            }
                            changeSpinner();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void changeSpinner() {
        adapter = new ArrayAdapter<String>(this,
                R.layout.lottery_item_select, list);
        adapter.setDropDownViewResource(R.layout.lottery_item_drop);
        sp90x5Type.setAdapter(adapter);
        if (null != gamePlayNum) {
            for (int i = 0; i < gameAddList.size(); i++) {
                if (gamePlayNum.equals(gameAddList.get(i).getGamePlayNum())) {
                    tv90x5Type.setText(gameAddList.get(i).getGamePlayName());
                    pos = i;
                }
            }
        }
    }

}
