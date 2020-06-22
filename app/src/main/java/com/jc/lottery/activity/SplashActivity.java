package com.jc.lottery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.jc.lottery.MainActivity;
import com.jc.lottery.R;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.PermissionsChecker;
import com.jc.lottery.util.Preferences;

//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.MsgConstant;
//import com.umeng.message.PushAgent;

public class SplashActivity extends FragmentActivity {
    //危险权限（运行时权限）
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
//			Manifest.permission.CALL_PHONE, // 拨打电话
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_CODE = 0; // 请求码
    private static final int PERMISSION_REQUEST_CODE = 0;        // 系统权限返回码
    private static final String PACKAGE_URL_SCHEME = "package:";
    public Handler handler = new Handler();
    //此处是注册的回调处理
    //参考集成文档的1.7.10
    //http://dev.umeng.com/push/android/integration#1_7_10
//    public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {
//
//        @Override
//        public void onRegistered(String registrationId) {
//            // TODO Auto-generated method stub
//            Log.i("友盟", "registrationId=" + registrationId);
//        }
//    };
    private Boolean is_welcome;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor prefLogin;
    Handler hand = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Intent intents  = new Intent(SplashActivity.this, RechargeNewActivity.class);
//            startActivity(intents);
//            finish();
            if (is_welcome) {
                if (Config.MUST_LOGIN) {
//                    if (!SPUtils.isHave(SplashActivity.this, SPkey.username)) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
//                    }
                }
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SplashActivity.this.finish();
            } else { // 跳转新手引导页
                prefLogin.putBoolean("IS_WELCOME", true);
                prefLogin.commit();
                if (Config.MUST_LOGIN) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                }
//                Intent i = new Intent(SplashActivity.this, Whatsnew.class);
//                startActivity(i);
//                finish();
            }
        }
    };
//    private PushAgent mPushAgent;
    private boolean isRequireCheck; // 是否需要系统权限检测
    private PermissionsChecker mPermissionsChecker;//检查权限

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        //PaySecurityMonitor.getInstance().monitor(getApplicationContext(), PartnerConfig.PARTNER, PartnerConfig.ALIPAY_APP_ID);

        mPreferences = this.getSharedPreferences(Preferences.PREFS_TIME, this.MODE_PRIVATE);
        prefLogin = this.getSharedPreferences(Preferences.PREFS_TIME, this.MODE_PRIVATE).edit();

//        mPushAgent = PushAgent.getInstance(this);

        // mPushAgent.setPushCheck(true);    //默认不检查集成配置文件
//		mPushAgent.setLocalNotificationIntervalLimit(false);  //默认本地通知间隔最少是10分钟

        //sdk开启通知声音
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);

        //应用程序启动统计
        //参考集成文档的1.5.1.2
        //http://dev.umeng.com/push/android/integration#1_5_1
//        mPushAgent.onAppStart();
//
//        //开启推送并设置注册的回调处理
//        mPushAgent.enable(mRegisterCallback);

        is_welcome = (Boolean) mPreferences.getBoolean("IS_WELCOME", false);

        mPermissionsChecker = new PermissionsChecker(this);
        isRequireCheck = true;

        /**
         * 应用权限判断
         */
        if (mPermissionsChecker.judgePermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        } else {
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = hand.obtainMessage();
                    hand.sendMessage(msg);
                }

            }.start();
        }

//		ensureUi();

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    public void ensureUi() {
        LinearLayout ll_splash = (LinearLayout) findViewById(R.id.ll_splash);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeIn.setDuration(3000);
        fadeIn.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.decelerate_interpolator));

		/*Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        fadeOut.setDuration(2000);
		fadeOut.setStartOffset(fadeIn.getDuration());
		fadeOut.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.accelerate_interpolator));
		 */

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {

                if (Config.MUST_LOGIN) {
//                    if (!SPUtils.isHave(SplashActivity.this, SPkey.username)) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
//                    }
                }
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();

            }

            public void onAnimationRepeat(Animation animation) {
            }
        });

        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(fadeIn);
        //animSet.addAnimation(fadeOut);

        ll_splash.startAnimation(animSet);
    }

    @Override
    protected void onResume() {
        super.onResume();
//		if (isRequireCheck) {
//			//权限没有授权，进入授权界面
//			if(mPermissionsChecker.judgePermissions(PERMISSIONS)){
//				ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
//			}
//		}else{
//			isRequireCheck = true;
//		}
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;

            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = hand.obtainMessage();
                    hand.sendMessage(msg);
                }

            }.start();
        } else {
            isRequireCheck = false;
////			showPermissionDialog();
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = hand.obtainMessage();
                    hand.sendMessage(msg);
                }

            }.start();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 提示对话框
     */
    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.ani_top_get_into,R.anim.ani_bottom_sign_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_top_get_into,R.anim.ani_bottom_sign_out);
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}