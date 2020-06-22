package com.jc.lottery.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jc.lottery.R;
import com.jc.lottery.bean.type.GroupPayment;
import com.jc.lottery.view.ClearDataDialog;

/**
 * @ Create_time: 2018/9/6 on 17:06.
 * @ description：对话框工具类
 * @ author: vchao
 */
public class DialogUtils {
    /**
     * 清空数据弹窗
     */
    public static void showClearDataDialog(final Activity activity, String content) {
        final ClearDataDialog dialog = new ClearDataDialog(activity, R.style.MyDialog2);

        Button clear_data_dialog_tv_yes = (Button) dialog.findViewById(R.id.clear_data_dialog_tv_yes);
        Button clear_data_dialog_tv_no = (Button) dialog.findViewById(R.id.clear_data_dialog_tv_no);
        TextView clear_data_dialog_tv_content = (TextView) dialog.findViewById(R.id.clear_data_dialog_tv_content);

        clear_data_dialog_tv_content.setText(content);

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

        dialog.show();

        clear_data_dialog_tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    GroupPayment.instance().getGroup().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                activity.finish();
            }
        });
        clear_data_dialog_tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 提示对话框
     */
    public static void showPermissionDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });

        builder.setPositiveButton(activity.getString(R.string.set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 退出 清空数据 对话框
     */
    public static void ExitClearDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.is_clear_sure));
        builder.setTitle("提示");
        builder.setPositiveButton(activity.getString(R.string.shi), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GroupPayment.instance().getGroup().clear();
                activity.finish();
            }
        });

        builder.setNegativeButton(activity.getString(R.string.fou), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
