package com.jc.lottery.activity.scanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.jc.lottery.R;
import com.jc.lottery.zxing.DeCodeActivity;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;

//import com.alipay.sdk.app.PayTask;

/**
 * 扫描
 */
public class ScannerActivity extends DeCodeActivity implements OnScannerCompletionListener {

//    public static final String EXTRA_LASER_LINE_MODE = "extra_laser_line_mode";
//    public static final String EXTRA_SCAN_MODE = "extra_scan_mode";
//    public static final String EXTRA_SHOW_THUMBNAIL = "EXTRA_SHOW_THUMBNAIL";
//    public static final String EXTRA_SCAN_FULL_SCREEN = "EXTRA_SCAN_FULL_SCREEN";
//    public static final String EXTRA_HIDE_LASER_FRAME = "EXTRA_HIDE_LASER_FRAME";
//
//    public static final int EXTRA_LASER_LINE_MODE_0 = 0;
//    public static final int EXTRA_LASER_LINE_MODE_1 = 1;
//    public static final int EXTRA_LASER_LINE_MODE_2 = 2;
//
//    public static final int EXTRA_SCAN_MODE_0 = 0;
//    public static final int EXTRA_SCAN_MODE_1 = 1;
//    public static final int EXTRA_SCAN_MODE_2 = 2;
//
//    public static final int APPLY_READ_EXTERNAL_STORAGE = 0x111;
//
//    private ScannerView mScannerView;
//    private Result mLastResult;
//    boolean showThumbnail = false;
//
//    public static ScannerActivity instance;
//    private String order_no;
//    private String type;
//    private String moneyStr;
////    private String payState;
//
//    private SharedPreferences.Editor prefLogin;
//    private SharedPreferences mPreferences;
//    private String userId;
//    private long uId;
//    private LinearLayout ui_back;
//    private TextView ui_title_content;
//    private ProgressDialogUtil prog_dlg_;
//    private String myLongitude;
//    private String myLatitude;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanner);
//
//        instance = this;
//
//        prefLogin = this.getSharedPreferences(Preferences.PREFS_NAME, this.MODE_PRIVATE).edit();
//        mPreferences = this.getSharedPreferences(Preferences.PREFS_NAME, this.MODE_PRIVATE);
//        userId = mPreferences.getString("userId", "0");
//        uId = Long.parseLong(userId);
//
//        prefLogin.putString("is_pay_type", "支付");
//        prefLogin.commit();
//
//        ui_back = (LinearLayout) findViewById(R.id.header_type_one_back);
//        ui_title_content = (TextView) findViewById(R.id.header_type_one_title);
//
//        ui_title_content.setText("扫描二维码");
//
//        ui_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        mScannerView = (ScannerView) findViewById(R.id.scanner_view);
//        mScannerView.setOnScannerCompletionListener(this);
//
//        Intent intent = getIntent();
//        if(null != intent) {
//            Bundle extras = intent.getExtras();
//            order_no = extras.getString("order_no"); // 订单号
//            moneyStr = extras.getString("money"); // 金额
//            type = extras.getString("type"); // 彩票类型
//            myLongitude = extras.getString("myLongitude"); // 经度
//            myLatitude = extras.getString("myLatitude"); // 纬度
//
//            int laserMode = extras.getInt(EXTRA_LASER_LINE_MODE);
//            int scanMode = extras.getInt(EXTRA_SCAN_MODE);
//            showThumbnail = extras.getBoolean(EXTRA_SHOW_THUMBNAIL);
//            mScannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
////        mScannerView.setDrawText("将二维码放入框内", true);
//            mScannerView.setDrawText(getResources().getString(R.string.scan_text), true);
//            mScannerView.setDrawTextColor(Color.WHITE);
//
//            if (scanMode == 1) {
//                //二维码
//                mScannerView.setScanMode(Scanner.ScanMode.QR_CODE_MODE);
//            } else if (scanMode == 2) {
//                //一维码
//                mScannerView.setScanMode(Scanner.ScanMode.PRODUCT_MODE);
//            }
//
//            //显示扫描成功后的缩略图
//            mScannerView.isShowResThumbnail(showThumbnail);
//            //全屏识别
//            mScannerView.isScanFullScreen(extras.getBoolean(EXTRA_SCAN_FULL_SCREEN));
//            //隐藏扫描框
//            mScannerView.isHideLaserFrame(extras.getBoolean(EXTRA_HIDE_LASER_FRAME));
////        mScannerView.isScanInvert(true);//扫描反色二维码
////        mScannerView.setCameraFacing(CameraFacing.FRONT);
////        mScannerView.setLaserMoveSpeed(1);//速度
//
////        mScannerView.setLaserFrameTopMargin(100);//扫描框与屏幕上方距离
////        mScannerView.setLaserFrameSize(400, 400);//扫描框大小
////        mScannerView.setLaserFrameCornerLength(25);//设置4角长度
////        mScannerView.setLaserLineHeight(5);//设置扫描线高度
////        mScannerView.setLaserFrameCornerWidth(5);
//
//            switch (laserMode) {
//                case EXTRA_LASER_LINE_MODE_0:
////                mScannerView.setLaserLineResId(R.mipmap.wx_scan_line);//线图
//                    mScannerView.setLaserLineResId(R.drawable.scan_light);//线图
//                    break;
//                case EXTRA_LASER_LINE_MODE_1:
////                mScannerView.setLaserGridLineResId(R.drawable.zfb_grid_scan_line);//网格图
////                mScannerView.setLaserFrameBoundColor(0xFF26CEFF);//支付宝颜色
//                    mScannerView.setLaserFrameBoundColor(R.color.saoma_jiao);
//                    break;
//                case EXTRA_LASER_LINE_MODE_2:
//                    mScannerView.setLaserColor(Color.RED);
//                    break;
//            }
//        }
//    }
//
//
//    @Override
//    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
//        if (rawResult == null) {
//            Toast.makeText(this, "未发现二维码", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//        Log.i("扫码", "结果----parsedResult--------》" + parsedResult);
//        Log.i("扫码", "结果----rawResult--------》" + rawResult);
//
//        if(!TextUtils.isEmpty(rawResult.toString())) {
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("deviceNo",rawResult.toString());
//                jsonObject.put("customerId",userId);
//                jsonObject.put("realtime_longitude",myLongitude); // 经度
//                jsonObject.put("realtime_latitude",myLatitude); // 纬度
//                scan(jsonObject.toString(), rawResult.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 扫码结果请求
//     */
//    private void scan(String json, final String rawResult) {
//        String url = Preferences.scan;
//        Log.i("json", "扫码 json-->"+json);
//        prog_dlg_ = new ProgressDialogUtil(ScannerActivity.this);
//        prog_dlg_.showProgressDialog("请稍候...");
//
//        OkHttpUtils.postString()
//                .url(url)
//                .content(json)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int i) {
//                        Log.i("json", "扫码--Exception->"+e);
//                        Toast.makeText(ScannerActivity.this, "网络异常", Toast.LENGTH_LONG).show();
//                        prog_dlg_.dismissProgressDialog();
//                        ScannerActivity.this.finish();
//                    }
//
//                    @Override
//                    public void onResponse(String str, int a) {
//                        prog_dlg_.dismissProgressDialog();
//                        Log.i("json", "扫码---->"+str);
//                        /**
//                         * {"code":"_0000","message":"支付成功"}
//                         */
//                        if("".equals(str) || "null".equals(str) || null == str) {
//                            Toast.makeText(ScannerActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//                        } else {
//                            try {
//                                JSONObject jsonObject = new JSONObject(str);
//                                String code = "";
//                                String message = "";
//                                String deviceNo = "";
//
//                                if(jsonObject.has("code")) {
//                                    code = jsonObject.getString("code");
//                                }
//                                if(jsonObject.has("message")) {
//                                    message = jsonObject.getString("message");
//                                }
//                                if(jsonObject.has("deviceNo")) {
//                                    deviceNo = jsonObject.getString("deviceNo");
//                                }
//                                if("40000".equals(code)) {
//                                    Intent intent = new Intent(ScannerActivity.this, PaymentsActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("order_no", order_no);
//                                    bundle.putString("money", moneyStr);
//                                    bundle.putString("type", type);
//                                    bundle.putString("deviceNo", deviceNo);
//                                    bundle.putString("rawResult", rawResult);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                    ScannerActivity.this.finish();
//                                } else {
//                                    Toast.makeText(ScannerActivity.this, message, Toast.LENGTH_LONG).show();
//                                    ScannerActivity.this.finish();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected void onResume() {
//        mScannerView.onResume();
//        resetStatusView();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        mScannerView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                if (mLastResult != null) {
//                    restartPreviewAfterDelay(0L);
//                    return true;
//                }
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void restartPreviewAfterDelay(long delayMS) {
//        mScannerView.restartPreviewAfterDelay(delayMS);
//        resetStatusView();
//    }
//
//    private void resetStatusView() {
//        mLastResult = null;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != Activity.RESULT_CANCELED && resultCode == Activity.RESULT_OK) {
////            if (requestCode == PickPictureTotalActivity.REQUEST_CODE_SELECT_PICTURE) {
////                String picturePath = data.getStringExtra(PickPictureTotalActivity.EXTRA_PICTURE_PATH);
////
////                QRDecode.decodeQR(picturePath, this);
////            }
//
//            Log.i("扫码", "data--->"+data);
//        }
//    }
}
