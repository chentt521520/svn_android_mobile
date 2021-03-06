package com.jc.lottery.view.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.application.LotteryApplication;
import com.jc.lottery.bean.resp.Resp_App_update;
import com.jc.lottery.bean.type.SoftwareUpdate;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressDialogUtil;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.UpdateDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @className： UpdateAppManager
 * @classDescription： 版本更新管理类
 * @author： 万
 * @createTime： 2017/12/13 11:57
 */
public class UpdateAppManager {
    // 外存sdcard存放路径
    private static final String savePath = Environment.getExternalStorageDirectory() + "/" + "lotteryUpdate" + "/";
    // 下载应用存放全路径
    private static final String saveFileName = savePath + "cce_lottery.apk";
    /* 进度条与通知ui刷新的handler和msg常量 */
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private final String state;
    /**
     * 版本更新
     */
    private Context mContext;
    private String apkUrl;
    private Thread downLoadThread;
    private boolean interceptFlag = false;
    private TextView popup_tv;
    private int progress = 0;
    private DownloadDialog dialog;
    private MySeekBar seekbar;
    private InputStream is;
    private FileOutputStream fos;
    private int fileSize = 0;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    popup_tv.setText(progress + "%");
                    popup_tv.setBackgroundResource(R.drawable.dialog_download_tv_bg);
                    seekbar.setProgress(progress);

                    if (progress == 99) {
                        dialog.dismiss();
                    }
                    break;
                case DOWN_OVER:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = isHasInstallPermissionWithO(mContext);
                        if (!hasInstallPermission) {
                            ToastUtils.showShort(mContext.getString(R.string.req_permission));
                            startInstallPermissionSettingActivity(mContext);
                            return;
                        }
                    }
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
//                int length = fileSize;
                is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public UpdateAppManager(Context mContext, String state) {
        this.mContext = mContext;
        this.state = state;
    }

    /**
     * 启动到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断市场是否存在的方法
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "1.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        if (Config.OpenBeforeNetQuest) {
//            SoftwareUpdateTask task = new SoftwareUpdateTask();
//            task.execute();
        }
    }

    public void checkUpdate(Context context) {
        checkUpdate(context, false);
    }

    public void checkLoginUpdate(final Context context, final LoginActionInterface loginActionInterface) {
        String version = getAppVersionName(mContext);
//        String account_name = SPUtils.look(context, SPkey.username, Config.Test_accountName);
//        Req_App_update req_App_update = new Req_App_update(account_name, getAppVersionName(context));
//        String app_up = new Gson().toJson(req_App_update);
//        LogUtils.e("  检查更新 请求参数  " + app_up);
        OkGo.<String>post(MyUrl.pos_app_update)
                .upString("package=com.jc.lottery&version=" + version +"&type=2")
//                .upJson(app_up)
                .execute(new vStringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(context, context.getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 返回内容 " + response.body());
                        Resp_App_update resp_app_update = new Gson().fromJson(response.body(), Resp_App_update.class);
                        if (resp_app_update.getUpdateInfo() == null) {
                            loginActionInterface.login();
                            return;
                        }else {
//                            fileSize = resp_app_update.getUpdateInfo().getFileSize();
                        }
                        String appVersionName = getAppVersionName(mContext);
                        if (TextUtils.equals("1.0", resp_app_update.getUpdateInfo().getVersion()) || TextUtils.equals("1.0.0", resp_app_update.getUpdateInfo().getVersion())) {
                            LogUtils.e("当前已经是最新版本   本地" + appVersionName + "服务器" + resp_app_update.getUpdateInfo().getVersion());
                            loginActionInterface.login();
                            return;
                        }
                        if (!TextUtils.equals(appVersionName, resp_app_update.getUpdateInfo().getVersion())) {
                            LogUtils.e("有更新的版本     本地" + appVersionName + "服务器" + resp_app_update.getUpdateInfo().getVersion());
//                            showUpDateDialog((Activity) mContext, resp_app_update.getVersionInfo().getVersionNum(), resp_app_update.getVersionInfo().getVersionDes(), MyUrl.Down_pre + resp_app_update.getVersionInfo().getVersionFile());
                            showUpDateDialog((Activity) mContext, resp_app_update.getUpdateInfo().getVersion(), resp_app_update.getUpdateInfo().getVersion(), resp_app_update.getUpdateInfo().getUrl());
                        } else {
                            LogUtils.e("当前已经是最新版本   本地" + appVersionName + "服务器" + resp_app_update.getUpdateInfo().getVersion());
                            loginActionInterface.login();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loginActionInterface.login();
                    }
                });
    }

    public void checkUpdate(final Context context, final boolean isToast) {
        String version = getAppVersionName(mContext);
//        Req_App_update req_App_update = new Req_App_update(account_name, getAppVersionName(context));
//        String app_up = new Gson().toJson(req_App_update);
//        LogUtils.e("  检查更新 请求参数  " + app_up);
        OkGo.<String>post(MyUrl.pos_app_update + "?package=com.jc.lottery&version=" + version + "&type=2")
                .upString("")
                .execute(new vStringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (ProgressUtil.getProgressDialog() == null) {
                            ProgressUtil.showProgressDialog(context, context.getString(R.string.waitting));
                        }
                    }

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 返回内容 " + response.body());
                        Resp_App_update resp_app_update = new Gson().fromJson(response.body(), Resp_App_update.class);
//                        int appVersionCode = getAppVersionCode(mContext);
//                        if (appVersionCode < resp_app_update.getVersionInfo().getId()) {
                        if (resp_app_update.getUpdateInfo() == null) {
                            if (isToast) {
                                ToastUtils.showShort(context.getString(R.string.is_new_version));
                            }
                            return;
                        }else {
//                            fileSize = resp_app_update.getUpdateInfo().getFileSize();
                        }
                        String appVersionName = getAppVersionName(mContext);
//                        if (TextUtils.equals("1.0", resp_app_update.getUpdateInfo().getVersion()) || TextUtils.equals("1.0.0", resp_app_update.getUpdateInfo().getVersion())) {
//                            LogUtils.e("当前已经是最新版本   本地" + appVersionName + "服务器" + resp_app_update.getUpdateInfo().getVersion());
//                            if (isToast) {
//                                ToastUtils.showShort(context.getString(R.string.is_new_version));
//                            }
//                            return;
//                        }
                        if (!TextUtils.equals(appVersionName, resp_app_update.getUpdateInfo().getVersion())) {
                            LogUtils.e("有更新的版本     本地" + appVersionName + "服务器" + resp_app_update.getUpdateInfo().getVersion());
//                            showUpDateDialog((Activity) mContext, resp_app_update.getVersionInfo().getVersionNum(), resp_app_update.getVersionInfo().getVersionDes(), MyUrl.Down_pre + resp_app_update.getVersionInfo().getVersionFile());
                            showUpDateDialog((Activity) mContext, resp_app_update.getUpdateInfo().getVersion(), resp_app_update.getUpdateInfo().getDescription(), resp_app_update.getUpdateInfo().getUrl());
                        } else {
                            if (isToast) {
                                ToastUtils.showShort(context.getString(R.string.is_new_version));
                            }
                            LogUtils.e("当前已经是最新版本   本地" + appVersionName + "服务器" + resp_app_update.getUpdateInfo().getVersion());
                        }

                    }
                });
    }

    /**
     * 检查版本更新，跳转到腾讯应用宝进行下载
     * <p>
     * 可以使用com.tencent.mobileqq 腾讯QQ测试
     */
    private void intit_getClick() {
        //这里开始执行一个应用市场跳转逻辑，默认this为Context上下文对象
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 跳转到应用市场
        intent.setData(Uri.parse("market://details?id=" + mContext.getPackageName()));
        //存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
        if (intent.resolveActivity(mContext.getPackageManager()) != null) { //可以接收
            mContext.startActivity(intent);
        } else { //没有应用市场，我们通过浏览器跳转到应用宝
            intent.setData(Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + mContext.getPackageName()));
            //这里存在一个极端情况就是有些用户浏览器也没有，再判断一次
            if (intent.resolveActivity(mContext.getPackageManager()) != null) { //有浏览器
                mContext.startActivity(intent);
            } else { //天哪，这还是智能手机吗？
                Toast.makeText(mContext, "天啊，您没安装应用市场，连浏览器也没有，您买个手机干啥？", Toast.LENGTH_SHORT).show();
            }
        }


//        if (isAvilible(mContext, "com.tencent.android.qqdownloader")) {
//            // 市场存在
//            Toast.makeText(mContext.getApplicationContext(), "ssss", Toast.LENGTH_SHORT).show();
////            launchAppDetail(mContext.getApplicationContext(), "com.tianyi.lottery", "com.tencent.android.qqdownloader");
//            launchAppDetail(mContext.getApplicationContext(), "com.tencent.mobileqq", "com.tencent.android.qqdownloader");
//        } else {
////            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.tianyi.lottery");
//            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.tencent.mobileqq");
//            Intent it = new Intent(Intent.ACTION_VIEW, uri);
//            mContext.startActivity(it);
//        }
    }

    /**
     * 版本更新弹窗
     */
    public void showUpDateDialog(final Activity activity, String version, String content, final String url) {
        final UpdateDialog dialog = new UpdateDialog(activity, R.style.MyDialog2);

        TextView update_dialog_tv_version = (TextView) dialog.findViewById(R.id.update_dialog_tv_version);
        TextView update_dialog_tv_content = (TextView) dialog.findViewById(R.id.update_dialog_tv_content);

        Button update_dialog_bt_yes = (Button) dialog.findViewById(R.id.update_dialog_bt_yes);
        Button update_dialog_bt_no = (Button) dialog.findViewById(R.id.update_dialog_bt_no);

        update_dialog_tv_version.setText("(v" + version + ")");

        //把服务器端返回的“|”替换成换行符号“\n”分条展示
        if (content.contains("|")) {
            content = content.replace("|", "\n");
        }

        update_dialog_tv_content.setText(content);

        Window dialogWindow = dialog.getWindow();
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        dialog.setCancelable(false);
        dialog.show();

        update_dialog_bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownloadDialog(url);
//                    intit_getClick();

//                dialog.dismiss();
            }
        });
        update_dialog_bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor prefLogin = activity.getSharedPreferences(Preferences.PREFS_TIME, activity.MODE_PRIVATE).edit();
//                Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//                prefLogin.putInt("year", year);
//                prefLogin.putInt("month", month);
//                prefLogin.putInt("day", day);
//                prefLogin.commit();
                ToastUtils.showShort(activity.getString(R.string.must_update));
//                dialog.dismiss();
            }
        });
    }

    /**
     * 下载apk弹窗
     */
    public void showDownloadDialog(String url) {
        dialog = new DownloadDialog(mContext, R.style.MyDialog2);

        final LinearLayout lin_bg = (LinearLayout) dialog.findViewById(R.id.lin_bg);
        popup_tv = (TextView) dialog.findViewById(R.id.dialog_download_tv_num);
        seekbar = (MySeekBar) dialog.findViewById(R.id.seekbar);

        Window dialogWindow = dialog.getWindow();
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        dialog.setCancelable(false);
        dialog.show();

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                popup_tv.measure(spec, spec);
                int quotaWidth = popup_tv.getMeasuredWidth();

                int spec2 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                popup_tv.measure(spec2, spec2);
                int sbWidth = lin_bg.getMeasuredWidth();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) popup_tv.getLayoutParams();
                params.leftMargin = (int) (((double) progress / seekBar.getMax()) * sbWidth - (double) quotaWidth * progress / seekBar.getMax());
                popup_tv.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    if(null != fos && null != is) {
//                        try {
        // TODO 有Bug NetworkOnMainThreadException
//                            fos.close();
//                            is.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });

        downloadApk(url);
    }

    /**
     * 下载apk
     */
    private void downloadApk(String url) {
        apkUrl = url;

        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    public void installApk() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = isHasInstallPermissionWithO(mContext);
            if (!hasInstallPermission) {
                ToastUtils.showShort(mContext.getString(R.string.req_permission));
                startInstallPermissionSettingActivity(mContext);
                return;
            }
        }
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);// 显示用户数据
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkuri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0以上版本，需要配置权限才能安装未知来源的程序:本代码的处理是使用FileProvider读取Uri资源
                apkuri = FileProvider.getUriForFile(mContext, "com.jc.lottery.provider", apkfile);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                apkuri = Uri.fromFile(apkfile);
            }
            i.setDataAndType(apkuri, "application/vnd.android.package-archive");
            mContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("异常  ……" + e.toString() + "\n异常  ……" + e.getLocalizedMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ((Activity) context).startActivityForResult(intent, 20001);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    public interface LoginActionInterface {
        void login();
    }

//    class SoftwareUpdateTask extends AsyncTask<Void, Void, SoftwareUpdate> {
//        private ProgressDialogUtil prog_dlg_;
//        private Exception mReason = null;
//
//
//        @Override
//        public void onPreExecute() {
//            prog_dlg_ = new ProgressDialogUtil(mContext);
//            prog_dlg_.showProgressDialog("请稍候...");
//        }
//
//        @Override
//        public SoftwareUpdate doInBackground(Void... params) {
//            SoftwareUpdate update = null;
//
//            try {
//                CaiSoLottery caiSoLottery = ((LotteryApplication) ((Activity) mContext).getApplication()).getCaiSoLottery();
//                update = caiSoLottery.addSoftwareUpdate("CaiSoLottery-ANDROID");
//            } catch (Exception e) {
//                mReason = e;
//            }
//            return update;
//        }
//
//        @Override
//        public void onPostExecute(SoftwareUpdate update) {
//            prog_dlg_.dismissProgressDialog();
//
//            if (update != null) {
//                int version_code = getAppVersionCode(mContext);
//                Log.i("信息", "版本更新--->" + version_code);
//                String code = update.getUpload_type();
//                long gx_code = Long.parseLong(code);
//                if (version_code < gx_code) {
////                    showUpDateDialog((Activity) mContext, update.getVersion(), update.getUpdataDescription(), update.getDownloadUrl());
//                    showUpDateDialog((Activity) mContext, update.getVersion(), update.getUpdataDescription(), MyUrl.Down_Update + update.getVersion());
//                } else {
//                    if ("set".equals(state)) {
////                        ToastUtils.showShort(context.getString(R.string.is_new_version));
//                    }
//                }
//            } else {
//                NotificationsUtil.ToastReasonForFailure(mContext, mReason);
//            }
//        }

//    }
}