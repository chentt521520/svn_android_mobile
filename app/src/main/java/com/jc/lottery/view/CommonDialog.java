package com.jc.lottery.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jc.lottery.R;


public class CommonDialog extends Dialog {

    private Context context;
    private onDismissListener dialogOnClick;    //对话框操作监听
    private View mContentView;
    private int mWidth = -2;
    private int mHeight = -2;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.MyDialog2);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    public void setDialogOnClick(onDismissListener dialogOnClick) {
        this.dialogOnClick = dialogOnClick;
    }

    public static class Builder {

        private CommonDialog mDialog;

        public Builder(Context context) {
            mDialog = new CommonDialog(context);
        }

        public Builder setContext(Context context) {
            mDialog.context = context;
            return this;
        }

        public Builder setDialogOnClick(onDismissListener dialogOnClick) {
            mDialog.setDialogOnClick(dialogOnClick);
            return this;
        }

        public Builder setView(View view) {
            this.mDialog.mContentView = view;
            return this;
        }

        public Builder size(int width, int height) {
            this.mDialog.mWidth = width;
            this.mDialog.mHeight = height;
            return this;
        }

        /**
         * ͨ 通过Builder类设置完属性后构造对话框的方法
         */
        public CommonDialog create() {
            this.mDialog.build();
            return mDialog;
        }
    }

    private CommonDialog build() {
        setContentView(mContentView);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = mWidth; // 高度设置为屏幕的0.6
        lp.height = mHeight; // 高度设置为屏幕的0.6
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        dialogWindow.setAttributes(lp);
//        setCanceledOnTouchOutside(true);
//        setCancelable(true);
        return this;
    }

    @Override
    public void show() {
        super.show();
    }


    public interface onDismissListener {
        void onDismissListener(View view);

        void onConfirmListener(View view);
    }
}
