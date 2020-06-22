package com.jc.lottery.inter;

import android.content.Context;
import android.content.Intent;

import com.jc.lottery.R;
import com.jc.lottery.activity.LoginActivity;
import com.jc.lottery.util.CommonUtils;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.ToastUtils;
import com.lzy.okgo.model.Response;

/**
 * @ Create_time: 2018/9/25 on 15:07.
 * @ description：自定义回调 处理
 * @ author: vchao
 */
public abstract class vStringCallback extends com.lzy.okgo.callback.StringCallback {

    private Context context;

    public vStringCallback() {

    }
    public vStringCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        try {
            LogUtils.e("" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ProgressUtil.dismissProgressDialog();
            if (response == null || response.message() == null) {
                if (context != null){
                    ToastUtils.showShort(context.getString(R.string.checknet));
                }else {
                    ToastUtils.showShort("Please check the network connection!");
                }
            } else {
                if (!response.message().equals("")){
//                    ToastUtils.showShort("" + response.message());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(Response<String> response) {
//        if (response.getRawResponse().code() == 300){
//            if (context != null){
//                Intent intent = new Intent();
//                intent.setClass(context, LoginActivity.class);
//                context.startActivity(intent);
//            }
//        }
        if (CommonUtils.isOK(response.body(),context)) {
            try {
                vOnSuccess(response);
//                ProgressUtil.dismissProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
//            ToastUtils.showShort(context.getString(R.string.checknet));
            onError(response);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        try {
            ProgressUtil.dismissProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void vOnSuccess(Response<String> response);
}
