package com.jc.lottery.base.application;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.jc.lottery.BuildConfig;
import com.jc.lottery.R;
import com.jc.lottery.util.AppLanguageUtils;
import com.jc.lottery.util.Density;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.UtilCrashHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.mob.tools.proguard.ProtectedMemberKeeper;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;


public class LotteryApplication extends MultiDexApplication implements ProtectedMemberKeeper {

    private static final boolean DEBUG = LotterySettings.DEBUG;
    private static final String TAG = "信息";
    private static Context mContext;
    private static LotteryApplication instance;
//    public LocationClient mLocationClient = null;
//    public LocationService locationService;
    public Vibrator mVibrator;
//    private RemoteResourceManager mRemoteResourceManager;
//    private CaiSoLottery mCaiSoLottery;
//    private List<Activity> mList = new LinkedList<Activity>();
//    private SharedPreferences.Editor prefLogin;
    private int num = 0;
    private boolean isDebugMode = false;     //正式版要改关闭
//    private String device_token;
//    private PushAgent mPushAgent;

    public static LotteryApplication getInstance() {
        return instance;
    }

//    public static DisplayImageOptions getDefaultDisplayImageOption() {
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0"))).resetViewBeforeLoading(true)
//                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565)
////				.displayer(new FadeInBitmapDisplayer(500)) // 设置图片渐显的时间
//                .displayer(new SimpleBitmapDisplayer()) // 设置图片渐显的时间
//                // .delayBeforeLoading(300) // 下载前的延迟时间
//                .build();
//        return options;
//    }

    public static Context getContext() {
        return mContext;
    }

    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//        MobSDK.init(this, this.getAppkey(), this.getAppSecret());
        instance = this;
        mContext = getApplicationContext();
//        LeakCanary.install(this);

        SPUtils.save(mContext, SPkey.Language, "English");
        Density.setDensity(this);
//        String language = SPUtils.look(mContext, SPkey.Language);
//        if (TextUtils.equals(language, "Chinese")) {
//            AppLanguageUtils.changeAppLanguage(this, Locale.CHINA);
//        } else {
        AppLanguageUtils.changeAppLanguage(this, Locale.ENGLISH);
//        }
        initOkgo();

//        prefLogin = getSharedPreferences(Preferences.PREFS_DEVICE_TOKEN, MODE_PRIVATE).edit();

//        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.xlistview_header_hint_normal);
//        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.its_refreshing);
//        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.xlistview_header_hint_ready);
//        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_completed);
//        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_failed);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.xlistview_header_last_time);
//        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.pull_up_load_more);
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.release_load_now);
//        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.xlistview_header_hint_loading);
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.its_refreshing);
//        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.load_complete);
//        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.failed_to_load);
//        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.no_more);
        /***
         * 初始化定位sdk，建议在Application中创建
         */
//        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());LeakCanary

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
//        initUmengPush();

        initUniversalImageLoader();
//        DatabaseHelper.init(this);
        // 初始化xutils
//        x.Ext.init(this);
        // 设置是否输出debug
//        x.Ext.setDebug(true);
//        mLocationClient = new LocationClient(this);
        // stid = STIDUtil.toSTID(this);
//        loadLottery(this);
//        loadResourceManagers();
//        new MediaCardStateBroadcastReceiver().register();
        UtilCrashHandler handler = new UtilCrashHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public void initOkgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(500000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(500000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(500000, TimeUnit.MILLISECONDS);
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        HttpHeaders headers = new HttpHeaders();
        if (TextUtils.equals(SPUtils.look(this, SPkey.Language), "Chinese")) {
            headers.put("Accept-Language", "zh-CN");
        } else {
            headers.put("Accept-Language", BuildConfig.LanguageTag);
        }

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .addCommonHeaders(headers)                      //全局公共头
                .setRetryCount(0);                          //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//				.addCommonParams(params);                       //全局公共参数
    }

    private void initUmengPush() {
//        mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setDebugMode(isDebugMode);       //注意 正式发布应用时，请务必将本开关关闭，避免影响用户正常使用APP。
//        mPushAgent.enable();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                do {
////                    device_token = UmengRegistrar.getRegistrationId(LotteryApplication.this);
////                    System.out.println("-------------------------------------》" + device_token);
////                    prefLogin.putString("device_token", device_token);
//                    prefLogin.commit();
//                    if (num <= 3) {
//                        try {
//                            num += 1;
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        break;
//                    }
//                } while (TextUtils.isEmpty(device_token));
//            }
//        }, 1000);

//        initPushMessage();//处理推送信息
//        initPushNotification();//处理推送消息的点击事件
    }

//    public void initPushMessage() {
//        /**
//         * 该Handler是在IntentService中被调用，故 1.
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK 2.
//         * IntentService里的onHandleIntent方法是并不处于主线程中，因此，如果需调用到主线程，需如下所示;
//         * 或者可以直接启动Service
//         * */
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler(getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e(TAG, "--dealWithCustomMessage--msg---" + msg);
//                        // 对自定义消息的处理方式，点击或者忽略
//                        boolean isClickOrDismissed = true;
//                        if (isClickOrDismissed) {
//                            // 自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            // 自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            // 接收到推送消息
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                switch (msg.builder_id) {
//                    case 1:
//                        return null;
//                    default:
//                        // 默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//
//            }
//
//        };
//        mPushAgent.setMessageHandler(messageHandler);
//    }

//    public void initPushNotification() {
        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            /*
             * @Override public void dealWithCustomAction(Context arg0, UMessage
             * arg1) { // TODO Auto-generated method stub
             * super.dealWithCustomAction(arg0, arg1); }
             *
             * @Override public void handleMessage(Context context, UMessage
             * msg) { try { Intent intent=new Intent(); Log.i(TAG, "custom=" +
             * msg.ticker); // 自定义消息的内容 Log.i(TAG, "title=" + msg.title); //
             * 通知标题 Log.i(TAG, "text=" + msg.text); // 通知内容 Log.i(TAG,
             * "extra:"+msg.extra.get("openmenu").toString());
             * intent.putExtra("open", msg.extra.get("openmenu").toString());
             * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             * intent.setClass(context, MainActivity.class);
             * context.startActivity(intent); } catch (Exception e) {
             * e.printStackTrace();
             *
             *
             * } }
             *
             *
             *
             * @Override public void launchApp(Context context, UMessage msg) {
             * // TODO Auto-generated method stub super.launchApp(context, msg);
             *
             * }
             */

            // 判断是否是广播还是单播(1==单播，2==广播)
//            @Override
//            public void openActivity(Context context, UMessage msg) {
//                Log.e(TAG, "--openActivity--msg---" + msg);
//
//            }
//        };
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
//    }

//    public void loadLottery(Context context) {
//        // String version = getVersionString(context);
//        mCaiSoLottery = new CaiSoLottery(CaiSoLottery.createHttpApi("", false));
//    }

//    public CaiSoLottery getCaiSoLottery() {
//        return mCaiSoLottery;
//    }

//    private void loadResourceManagers() {
//        try {
//            mRemoteResourceManager = new RemoteResourceManager("cache");
//        } catch (IllegalStateException e) {
//            if (DEBUG)
//                Log.d(TAG, "Falling back to NullDiskCache for RemoteResourceManager");
//            mRemoteResourceManager = new RemoteResourceManager(new NullDiskCache());
//        }
//    }

//    public RemoteResourceManager getRemoteResourceManager() {
//        return mRemoteResourceManager;
//    }

    // add Activity
//    public void addActivity(Activity activity) {
//        mList.add(activity);
//    }
//
//    public void exit() {
//        try {
//            for (Activity activity : mList) {
//                if (activity != null)
//                    activity.finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
////            System.exit(0);
//        }
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    private void initUniversalImageLoader() {
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for releaseAllVideos app
//        config.defaultDisplayImageOptions(getDefaultDisplayImageOption());
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * Set up resource managers on the application depending on SD card state.
     *
     * @author Joe LaPenna (joe@joelapenna.com)
     */
//    private class MediaCardStateBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (DEBUG)
//                Log.d(TAG, "Media state changed, reloading resource managers:" + intent.getAction());
//            if (Intent.ACTION_MEDIA_UNMOUNTED.equals(intent.getAction())) {
//                getRemoteResourceManager().shutdown();
//                loadResourceManagers();
//            } else if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
//                loadResourceManagers();
//            }
//        }

//        public void register() {
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
//            intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
//            intentFilter.addDataScheme("file");
//            registerReceiver(this, intentFilter);
//        }
//    }
}
