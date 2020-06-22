package com.jc.lottery.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.jc.lottery.R;
import com.jc.lottery.activity.BluetoothNewActivity;
import com.jc.lottery.content.Constant;
import com.jc.lottery.inter.DialogInterface;


/**
 * @ Create_time: 2018/9/29 on 20:53.
 * @ description：
 * @ author: vchao
 */
public class PrintDeviceUtil {

    private static AlertDialog builder;

    public static boolean connDevice(final Activity activity) {
//        连接打印机
        return connDevice(activity, null);
    }

    public static void cancelDialog() {
        try {
            if (builder != null) {
                builder.cancel();
                builder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean connDevice(final Activity activity, @Nullable final DialogInterface mdialogInterface) {
//        连接打印机
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getConnState()) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.dialog_no_bluetooth, null);

            builder = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT)
                    .setView(view)
                    .create();
            final WindowManager.LayoutParams params = builder.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = 200;
            params.gravity = Gravity.CENTER;
            builder.getWindow().setAttributes(params);
            builder.setCancelable(false);
            builder.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(android.content.DialogInterface dialogInterface) {
                    builder = null;
                }
            });
            builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            builder.show();
            final Button bt_connect = view.findViewById(R.id.bt_dialog_bluetooth_tip_connect);
            final Button bt_cancel = view.findViewById(R.id.bt_dialog_bluetooth_tip_cancel);
            bt_connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ProgressUtil.showProgressDialog(activity,activity.getString(R.string.waitting));
                    LogUtils.e("connect");
                    activity.startActivityForResult(new Intent(activity, BluetoothNewActivity.class), Constant.BLUETOOTH_REQUEST_CODE);
                }
            });
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.e("cancel");
                    if (!Config.MUST_NEED_PRINTER) {
                        if (mdialogInterface != null)
                            sureOrderNoDevice(activity, mdialogInterface);
                    }
                    builder.cancel();
                    ProgressUtil.dismissProgressDialog();
                }
            });
            return false;
        }

//        再检查一下
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getConnState()) {
            ToastUtils.showShort(activity.getString(R.string.str_cann_printer));
            return false;
        }
        return true;
    }

    public static boolean sureOrderNoDevice(final Activity activity, final DialogInterface mdialogInterface) {
//        连接打印机
        if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getConnState()) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.dialog_sure_order, null);

            final AlertDialog builder = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT)
                    .setView(view)
                    .setCancelable(false)
                    .setPositiveButton(R.string.sure, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialogInterface, int i) {
                            if (mdialogInterface != null)
                                mdialogInterface.postive();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialogInterface, int i) {
                            if (mdialogInterface != null)
                                mdialogInterface.negative();
                        }
                    })
                    .create();
            final WindowManager.LayoutParams params = builder.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = 200;
            params.gravity = Gravity.CENTER;
            builder.getWindow().setAttributes(params);
            builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            builder.show();
            return false;
        }
        return true;
    }

}
