package com.jc.lottery.activity.my;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.activity.immediate.DeliveryAgentInputActivity;
import com.jc.lottery.base.BaseActivity;
import com.jc.lottery.bean.req.PasswordUpdateBean;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.Config;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jc.lottery.util.AesOrMd5.md5OrAes;

public class UpdatePasswordActivity extends BaseActivity {

    @BindView(R.id.img_update_password_back)
    ImageView imgForgetPasswordBack;
    @BindView(R.id.et_password_old)
    EditText etPasswordOld;
    @BindView(R.id.et_password_new)
    EditText etPasswordNew;
    @BindView(R.id.et_password_confirm)
    EditText etPasswordConfirm;
    @BindView(R.id.btn_password_submit)
    Button btnPasswordSubmit;

    private CountDownTimer mTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    public void initListener() {
        etPasswordOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length()>30){
                    etPasswordOld.setText(str.substring(0,30)); //截取前x位
                    etPasswordOld.requestFocus();
                    etPasswordOld.setSelection(etPasswordOld.getText().length()); //光标移动到最后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etPasswordNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length()>30){
                    etPasswordNew.setText(str.substring(0,30)); //截取前x位
                    etPasswordNew.requestFocus();
                    etPasswordNew.setSelection(etPasswordNew.getText().length()); //光标移动到最后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.img_update_password_back, R.id.btn_password_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_update_password_back:
                finish();
                break;
            case R.id.btn_password_submit:
                String oldPassword = etPasswordOld.getText().toString().trim();
                String newPassword = etPasswordNew.getText().toString().trim();
                String confirmPassword = etPasswordConfirm.getText().toString().trim();
                if (oldPassword.equals("")){
                    ToastUtils.showShort(getString(R.string.please_enter_the_old_password));
                }else if (newPassword.equals("")){
                    ToastUtils.showShort(getString(R.string.please_enter_the_new_password));
                }else if (confirmPassword.equals("")){
                    ToastUtils.showShort(getString(R.string.please_enter_the_new_password));
                }else {
                    if (newPassword.equals(confirmPassword)) {
                        passwordUpdateHttp(oldPassword, newPassword);
                    }else {
                        ToastUtils.showShort(getString(R.string.please_enter_password_is_consistent));
                    }
                }
                break;
        }
    }

    private void notHide(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(UpdatePasswordActivity.this
                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void passwordUpdateHttp(final String oldPassword, String newPassword) {
        String account_name = SPUtils.look(this, SPkey.username, Config.Test_accountName);
        String terminalNum = SPUtils.look(this, SPkey.userActivationCode);
        ProgressUtil.showProgressDialog(this, getString(R.string.waitting));
        PasswordUpdateBean passwordUpdateBean = new PasswordUpdateBean(account_name,md5OrAes(oldPassword),md5OrAes(newPassword));
        String s = new Gson().toJson(passwordUpdateBean);
        OkGo.<String>post(MyUrl.password_update)
                .upJson(s)
                .execute(new vStringCallback(this) {
                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        LogUtils.e(" 返回内容 " + response.body());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if (jsonObject.has("code")) {
                                String message = jsonObject.getString("message");
                                String code = jsonObject.getString("code");
                                ToastUtils.showShort(message);
                                if (code.equals("00000")){
                                    finish();
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
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        etPasswordOld.clearFocus();
        etPasswordNew.clearFocus();
    }
}
