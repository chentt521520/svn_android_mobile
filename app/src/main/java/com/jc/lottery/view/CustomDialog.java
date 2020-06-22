package com.jc.lottery.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.lottery.R;

/**
 * @className： CustomDialog
 * @classDescription： 加载提醒对话框
 * @author： 万
 * @createTime： 2017/12/14 12:55
 */
public class CustomDialog extends ProgressDialog {
    private String message = "加载中...";

    public CustomDialog(Context context, String message){
        super(context);
        this.message = message;
    }

    public CustomDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    @SuppressLint("ResourceType")
    private void init(Context context) {
        //设置可取消，点击其他区域能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.load_dialog);
        LinearLayout lly_loading = (LinearLayout) findViewById(R.id.lly_loading);
        TextView tv_load_dialog = (TextView) findViewById(R.id.tv_load_dialog);
        tv_load_dialog.setText(message);
        lly_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismiss();
            }
        });
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(layoutParams);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
