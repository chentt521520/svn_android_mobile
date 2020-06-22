package com.jc.lottery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jc.lottery.BuildConfig;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.base.application.LotteryApplication;
import com.jc.lottery.bean.MyImmediateBean;
import com.jc.lottery.bean.req.pos_terminalActivation;
import com.jc.lottery.bean.req.pos_terminalLogin;
import com.jc.lottery.bean.req.pos_user_type;
import com.jc.lottery.bean.resp.Resp_login;
import com.jc.lottery.bean.resp.Resp_user_type;
import com.jc.lottery.bean.type.EncryptedStateBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.AppLanguageUtils;
import com.jc.lottery.util.CommonUtils;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.GetJsonUtils;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.PhoneInfoUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.jc.lottery.view.update.UpdateAppManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jc.lottery.util.AesOrMd5.md5OrAes;

/**
 * 登录页面
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.tv_login_terminalname)
    TextView tvTerminalName;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.switch_change_language)
    Switch switchChangeLanguage;
    @BindView(R.id.lly_login_forgot_password)
    LinearLayout llyLoginForgotPassword;
    private boolean isShowDialog = false;
    private long firstTime = 0;//记录用户首次点击返回键的时间
    private AlertDialog builder;
    private UpdateAppManager manager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_logins;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                switchChangeLanguage.setThumbResource(R.drawable.switch_custom_thumb_selector);
                switchChangeLanguage.setTrackResource(R.drawable.switch_custom_track_selector);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String terminalname = SPUtils.look(LoginActivity.this, SPkey.terminalname, "");
        if (!TextUtils.isEmpty(terminalname)) {
            tvTerminalName.setText(terminalname);
        } else {
            tvTerminalName.setText(R.string.no_jihuo);
        }
        loadPermission();
        try {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                String androidId = getAndroidId(LoginActivity.this);
                LogUtils.e("   android   ==" + androidId);
                if (TextUtils.equals(androidId, "a6a93e260975036")) {
                    if (Config.EditTianchong) {
                    }
                    etLoginUsername.setText("lr");
                    etLoginPassword.setText("123456");
                } else if (TextUtils.equals(androidId, "9964e655376a77fa")) {
//                    etLoginUsername.setText("guojian");
//                    etLoginPassword.setText("123456");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setEditTextInhibitInputSpace(etLoginUsername);

        etLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length()>20){
                    etLoginUsername.setText(str.substring(0,20)); //截取前x位
                    etLoginUsername.requestFocus();
                    etLoginUsername.setSelection(etLoginUsername.getText().length()); //光标移动到最后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length()>30){
                    etLoginPassword.setText(str.substring(0,30)); //截取前x位
                    etLoginPassword.requestFocus();
                    etLoginPassword.setSelection(etLoginPassword.getText().length()); //光标移动到最后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 禁止EditText输入空格
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" "))return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    @Override
    public void initData() {
//        检查版本更新
        manager = new UpdateAppManager(LoginActivity.this, "main");
        manager.checkUpdate(LoginActivity.this);
        String name = SPUtils.look(LoginActivity.this, SPkey.temporaryUserName);
        String password = SPUtils.look(LoginActivity.this, SPkey.temporaryPassword);
        if (!name.equals("")){
            etLoginUsername.setText(name);
        }
        if (!password.equals("")){
            etLoginPassword.setText(password);
        }
//        manager.showUpDateDialog(this, "1.1.0", "aa", "http://47.104.173.4:8889/wxpos/version/wxpos_124_v1.1.0-2019-04-02-10.37.apk");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("收到-----  反馈  requestCode----" + requestCode + "    resultCode ---" + resultCode);
        switch (requestCode) {
            case 20001:
                manager.installApk();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    boolean hasInstallPermission = isHasInstallPermissionWithO(this);
//                    if (!hasInstallPermission) {
//                        ToastUtils.showShort(getString(R.string.req_permission));
//                        return;
//                    }
//                }
//
//                String saveFileName = Environment.getExternalStorageDirectory() + "/lotteryUpdate/cce_lottery.apk";
//                File apkfile = new File(saveFileName);
//                if (!apkfile.exists()) {
//                    return;
//                }
//
//                Intent i = new Intent(Intent.ACTION_VIEW);// 显示用户数据
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Uri apkuri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    apkuri = FileProvider.getUriForFile(this, "com.tianyi.immediatel.provider", apkfile);
//                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                } else {
//                    apkuri = Uri.fromFile(apkfile);
//                }
//                i.setDataAndType(apkuri, "application/vnd.android.package-archive");
//                startActivity(i);
                break;
            default:
                break;
        }
    }

    /**
     * 动态加载权限
     */
    private void loadPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] needPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
            List<String> permissionList = new ArrayList<>();
            for (String value : needPermissions) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, value) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(value);
                }
            }
            if (!permissionList.isEmpty()) {  //申请的集合不为空时，表示有需要申请的权限
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 0);
            } else { //所有的权限都已经授权过了
//                LogUtil.e("所有的权限都已经赋予了");
            }
        }
    }
    /**
     * 获取序列号
     * @param context
     * @return
     */
    private String getAndroidId(Context context) {
        String androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    @Override
    public void initListener() {
        String language = SPUtils.look(LoginActivity.this, SPkey.Language);
        if (TextUtils.equals(language, "Chinese")) {
            switchChangeLanguage.setText("中文");
            switchChangeLanguage.setChecked(false);
        } else {
            switchChangeLanguage.setText("English");
            switchChangeLanguage.setChecked(true);
        }
        // 添加监听
        switchChangeLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EventBus.getDefault().post("change");
                if (isChecked) {
                    switchChangeLanguage.setText("English");
                    AppLanguageUtils.changeAppLanguage(LoginActivity.this, Locale.ENGLISH);
                    SPUtils.save(LoginActivity.this, SPkey.Language, "English");
                } else {
                    switchChangeLanguage.setText("中文");
                    AppLanguageUtils.changeAppLanguage(LoginActivity.this, Locale.CHINA);
                    SPUtils.save(LoginActivity.this, SPkey.Language, "Chinese");
                }
                LotteryApplication.getInstance().initOkgo();
                temporaryUserInfo(etLoginUsername.getText().toString().trim(),etLoginPassword.getText().toString().trim());
                //重新启动Activity
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.lly_login_forgot_password, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_login_forgot_password:
//                Intent intent = new Intent();
//                intent.setClass(this,ForgetPasswordActivity.class);
//                startActivity(intent);
//                ProgressUtil.showProgressDialog(LoginActivity.this, getString(R.string.logining));
                break;
            case R.id.bt_login:
//                Intent intent = new Intent();
//                intent.setClass(this,MainFragmentTabActivity.class);
//                startActivity(intent);
//                finish();
                if (CommonUtils.isEmpty(etLoginUsername)) {
                    ToastUtils.showShort(getString(R.string.username_tip));
                    return;
                } else if (CommonUtils.isEmpty(etLoginPassword)) {
                    ToastUtils.showShort(getString(R.string.password_tip));
                    return;
                }
//                if (manager == null) {
//                    manager = new UpdateAppManager(LoginActivity.this, "main");
//                }
////                doLoginClick();
//                manager.checkLoginUpdate(LoginActivity.this, new UpdateAppManager.LoginActionInterface() {
//                    @Override
//                    public void login() {
//                        doLoginClick();
//                    }
//                });
                final String username = CommonUtils.getText(etLoginUsername);
                final String password = CommonUtils.getText(etLoginPassword);
                if (username.equals("lrdls") || username.equals("lrgly") || username.equals("lrfxs")){
                    login(username, password, false);
                }else {
                    if (!SPUtils.isHave(LoginActivity.this, SPkey.userActivationCode)) {
                        // 设备激活弹框
                        showActivationDialog(username, password);
                    } else {
                        login(username, password, false);
                    }
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quit();// 自己实现的弹出提示框,这个大家都会就不详细写了.
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void quit() {
        if (System.currentTimeMillis() - firstTime > 2000) {
            ToastUtils.showShort(getString(R.string.will_exit));
            firstTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void login(final String username, final String password, final boolean isAdmin) {
        ProgressUtil.showProgressDialog(LoginActivity.this, getString(R.string.logining));
        String userActivationCode = "";
        String uniqueMark = "";
        if (username.equals("lrdls")){
            userActivationCode = "837523513843";
            uniqueMark = "866774041150023";
        }else if(username.equals("lrgly")){
            userActivationCode = "256668034671";
            uniqueMark = "355145108183025";
        }else if (username.equals("lrfxs")){
            userActivationCode = "092500320433";
            uniqueMark = "355645107502760";
        }else{
            userActivationCode = SPUtils.look(LoginActivity.this, SPkey.userActivationCode, "");
            uniqueMark = getMEID();
        }
        pos_terminalLogin.DataBean.UserInfoBean userInfoBean = new pos_terminalLogin.DataBean.UserInfoBean(md5OrAes(password),uniqueMark,"3",userActivationCode);
        pos_terminalLogin pos_login = new pos_terminalLogin(username, userInfoBean);
        String s = new Gson().toJson(pos_login);
        LogUtils.e("请求登陆  参数" + s);
        OkGo.<String>post(MyUrl.pos_userLogin)
                .upJson(s)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        LogUtils.e(" 返回内容 " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.has("code")) {
                                String code = jsonObject.getString("code");
                                if (TextUtils.equals(code, "00000")) {
                                    if (isAdmin) {
                                        ToastUtils.showShort(getString(R.string.device_jihuo_scuess));
                                        return;
                                    }
//                                    ToastUtils.showShort(getString(R.string.login_scuess));
                                    Resp_login resp_login = new Gson().fromJson(response.body(), Resp_login.class);
                                    SPUtils.save(LoginActivity.this, SPkey.username, username);
                                    SPUtils.save(LoginActivity.this, SPkey.terminalname, resp_login.getTerminalName());
                                    if (username.equals("lrdls")){
                                        SPUtils.save(LoginActivity.this, SPkey.terminalname, "lrdls");
                                    }
                                    if (username.equals("lrgly")){
                                        SPUtils.save(LoginActivity.this, SPkey.terminalname, "lrgly");
                                    }
                                    if (username.equals("lrfxs")){
                                        SPUtils.save(LoginActivity.this, SPkey.terminalname, "lrfxs");
                                    }
                                    SPUtils.save(LoginActivity.this, SPkey.terminalId, resp_login.getTerminalId() + "");
                                    SPUtils.save(LoginActivity.this, SPkey.roleAlias, resp_login.getRoleAlias() + "");
                                    SPUtils.save(LoginActivity.this, SPkey.registrationTime, resp_login.getRegistrationTime() + "");
                                    SPUtils.save(LoginActivity.this, SPkey.instantRecharge, resp_login.getInstantRecharge() + "");
//                                    SPUtils.save(LoginActivity.this, SPkey.roleName, resp_login.getRoleName() + "");
                                    // TODO: 2018/10/18 account_id 后续待更改
                                    if (Config.UseRealAccountId) {
                                        SPUtils.save(LoginActivity.this, SPkey.accountId, "" + resp_login.getUserId());
                                    } else {
                                        SPUtils.delete(LoginActivity.this, SPkey.accountId);
                                    }
                                    SPUtils.save(LoginActivity.this, SPkey.userActivationCode, resp_login.getTerminalNum());
                                    addHttpHeaders(resp_login.getToken());
                                    isEncryptedState();
                                    startActivity(new Intent(LoginActivity.this, MainFragmentTabActivity.class));
//                                    overridePendingTransition(R.anim.ani_bottom_sign_out,R.anim.ani_top_get_into);
                                    if (!isFinishing()) {
                                        temporaryUserInfo(etLoginUsername.getText().toString().trim(), "");
                                    }else {
                                        temporaryUserInfo("", "");
                                    }
                                    SPUtils.save(LoginActivity.this, SPkey.password, password);
                                    finish();
                                } else if (TextUtils.equals(code, "40005")) {
//                                    设备编号不正确!
                                    if (isAdmin) {
                                        try {
                                            String message = jsonObject.getString("message");
//                                            ToastUtils.showShort(message);
                                            LogUtils.e("  " + message);
                                            if (!isShowDialog) {
                                                showActivationDialog(username, password);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return;
                                    }
                                    try {
                                        String message = jsonObject.getString("message");
                                        ToastUtils.showShort(message);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ToastUtils.showShort(getString(R.string.login_fail));
                                    }
                                } else if(TextUtils.equals(code, "40008")||TextUtils.equals(code, "40003")) {
                                    showActivationDialog(username,password);
                                }else {
                                        try {
                                            String message = jsonObject.getString("message");
                                            ToastUtils.showShort(message);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            ToastUtils.showShort(getString(R.string.login_fail));
                                        }
                                    }
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ProgressUtil.dismissProgressDialog();
                        ToastUtils.showShort(getString(R.string.checknet));
                    }
                });
    }

    /**
     * 展示设备激活对话框
     *
     * @param username
     * @param password
     */
    private void showActivationDialog(final String username, final String password) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_activation, null);
        final EditText et_dialog_activation_code = view.findViewById(R.id.et_dialog_activation_code);
        final TextView tv_activation_device_name = view.findViewById(R.id.tv_activation_device_name);
        final TextView tv_old_code = view.findViewById(R.id.tv_activation_device_tip);
        String activationCode = SPUtils.look(LoginActivity.this, SPkey.userActivationCode, "");
        if (!TextUtils.isEmpty(activationCode)) {
            tv_old_code.setText(String.format(getString(R.string.old_activation_code_unuse), activationCode));
        }
        if (tv_activation_device_name != null) {
            String terminalname = SPUtils.look(LoginActivity.this, SPkey.terminalname, "");
            if (!TextUtils.isEmpty(terminalname)) {
                tv_activation_device_name.setText(getString(R.string.device_name) + terminalname);
            } else {
                tv_activation_device_name.setVisibility(View.GONE);
            }
        }
        String code = SPUtils.look(LoginActivity.this, SPkey.userActivationCode, "");
        et_dialog_activation_code.setText(code);
        builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setView(view)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (CommonUtils.isEmpty(et_dialog_activation_code)) {
                            ToastUtils.showShort(getString(R.string.device_no_tip));
                        } else {
                            if (et_dialog_activation_code.getText().toString().trim().length() != 12){
                                ToastUtils.showShort(getString(R.string.please_input_correctly));
                            }else {
                                addTerminalActivation(username, password, CommonUtils.getText(et_dialog_activation_code),dialog);
                            }
                        }
                    }
                }).create();
        builder.setCancelable(false);
        builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        builder.show();
        isShowDialog = true;
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShowDialog = false;
            }
        });
    }

    private void addTerminalActivation(final String name, final String password, final String activation_code, final DialogInterface dialog){
//        password = md5OrAes(password);
//        本地的激活码
//        String userActivationCode = SPUtils.look(LoginActivity.this, SPkey.userActivationCode, "");
        String uniqueMark = getMEID();
        pos_terminalActivation.DataBean.UserInfoBean userInfoBean = new pos_terminalActivation.DataBean.UserInfoBean(md5OrAes(password),uniqueMark,"3",activation_code);
        pos_terminalActivation pos_terminalActivation = new pos_terminalActivation(name, userInfoBean);
        String s = new Gson().toJson(pos_terminalActivation);
        LogUtils.e("请求登陆  参数" + s);

        OkGo.<String>post(MyUrl.pos_terminalActivation)
                .upJson(s)
                .execute(new vStringCallback(this) {

                    @Override
                    protected void vOnSuccess(Response<String> response) {
                        LogUtils.e(" 返回内容 " + response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.has("code")) {
                                String code = jsonObject.getString("code");
                                if (TextUtils.equals(code, "00000")) {
                                    SPUtils.save(LoginActivity.this, SPkey.userActivationCode, activation_code);
                                    ToastUtils.showShort(getString(R.string.device_jihuo_scuess));
                                    dialog.dismiss();
                                    try {
//                                        Resp_active_device resp_active_device = new Gson().fromJson(response.body(), Resp_active_device.class);
//                                        tvTerminalName.setText(activation_code);
//                                        JSONObject data = jsonObject.getJSONObject("data");
//                                        JSONObject userInfo = data.getJSONObject("userInfo");
                                        String terminalName = jsonObject.getString("terminalName");
                                        tvTerminalName.setText(terminalName);
                                        SPUtils.save(LoginActivity.this, SPkey.terminalname, terminalName);
                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (builder != null) {
                                            Field field = builder.getClass().getSuperclass().getDeclaredField("mShowing");
                                            field.setAccessible(true);
                                            field.set(builder, true);
                                            builder.dismiss();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (TextUtils.equals(code, "40005")) {
//                                    设备编号不正确!
                                    try {
                                        String message = jsonObject.getString("message");
                                        ToastUtils.showShort(message);
                                        if (!Config.MUST_LOGIN) {
                                            SPUtils.save(LoginActivity.this, SPkey.userActivationCode, activation_code);
                                            SPUtils.save(LoginActivity.this, SPkey.accountId, Config.Test_accountId + "");
                                        }
                                        showActivationDialog(name, password);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ToastUtils.showShort(getString(R.string.device_no_error));
                                    }
                                } else {
                                    try {
                                        String message = jsonObject.getString("message");
                                        ToastUtils.showShort(message);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ToastUtils.showShort(getString(R.string.login_fail));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

    }

    private void addHttpHeaders(String token){
        HttpHeaders headers = new HttpHeaders();
        if (TextUtils.equals(SPUtils.look(this, SPkey.Language), "Chinese")) {
            headers.put("Accept-Language", "zh-CN");
        } else {
            headers.put("Accept-Language", BuildConfig.LanguageTag);
        }
        if(!token.equals("")){
            headers.put("token", token);
        }
        OkGo.getInstance().addCommonHeaders(headers);
    }

    private void temporaryUserInfo(String name,String password){
        SPUtils.save(LoginActivity.this, SPkey.temporaryUserName, name);
        SPUtils.save(LoginActivity.this, SPkey.temporaryPassword, password);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getMEID() {
//        int dpi = getResources().getDisplayMetrics().densityDpi;
//        ToastUtils.showShort(dpi + "");
        if (etLoginUsername.getText().toString().trim().equals("lrdls")){
            return "866774041150023";
        }else if(etLoginUsername.getText().toString().trim().equals("lrgly")){
            return "355145108183025";
        }else if (etLoginUsername.getText().toString().trim().equals("lrfxs")) {
            return "355645107502760";
        }
        String meid = PhoneInfoUtils.getIMEI(this);
        //截取#之前的字符串
        if (meid.indexOf(";") != -1){
            meid = meid.substring(0, meid.indexOf(";"));
        }
        if (TextUtils.isEmpty(meid)) {
            meid = getAndroidId(this);
        }
        return meid;
    }

    private void isEncryptedState(){
        String encryptedState = SPUtils.look(this, SPkey.encryptedState);
        if (encryptedState != null && !encryptedState.equals("")){
//            String json = new GetJsonUtils().readTextFromSDcard(this);
//            SPUtils.save(this,SPkey.encryptedState,json);
        }else {
            String json = new GetJsonUtils().readTextFromSDcard(this);
            SPUtils.save(this,SPkey.encryptedState,json);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        etLoginUsername.clearFocus();
        etLoginPassword.clearFocus();
    }
}
