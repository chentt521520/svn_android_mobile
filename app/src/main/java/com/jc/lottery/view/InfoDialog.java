package com.jc.lottery.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.lottery.R;

/**
 * @author lr
 * @description:
 * @date:${DATA} 14:05
 */

public class InfoDialog extends Dialog {
    private InfoDialog(Context context, int themeResId) {
                 super(context, themeResId);
             }

            public static class Builder {
            private View mLayout;

//                  private ImageView mIcon;
          private TextView mTitle;
          private TextView mMessage;
          private Button mButton;
          private Button mButtonNo;
          private View.OnClickListener mButtonClickListener;
          private View.OnClickListener mButtonClickListenerNo;

                private InfoDialog mDialog;

                  public Builder(Context context) {
                      mDialog = new InfoDialog(context, R.style.Theme_AppCompat_Dialog);
                      LayoutInflater inflater =
                                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                      //加载布局文件
                      mLayout = inflater.inflate(R.layout.alert_lock_password, null, false);
                      //添加布局文件到 Dialog
                      mDialog.addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                      ViewGroup.LayoutParams.MATCH_PARENT));
//                      mIcon = mLayout.findViewById(R.id.dialog_icon);
                      mTitle = mLayout.findViewById(R.id.tv_lock_title);
//                      mMessage = mLayout.findViewById(R.id.dialog_message);
                      mButton = mLayout.findViewById(R.id.btn_lock_yes);
                      mButtonNo = mLayout.findViewById(R.id.btn_lock_no);
                     }


                  /**
            * 设置 Dialog 标题
            */
                  public Builder setTitle(@NonNull String title) {
                         mTitle.setText(title);
                         mTitle.setVisibility(View.VISIBLE);
                         return this;
                     }

                  /**
            * 设置 Message
            */

                  /**
            * 设置按钮文字和监听
            */
                  public Builder setButton(@NonNull String text, View.OnClickListener listener) {
                         mButton.setText(text);
                         mButtonClickListener = listener;
                         return this;
                     }
//                public Builder setButtonNo(View.OnClickListener listener) {
//                    mButtonNo.setText(text);
//                    mButtonClickListenerNo = listener;
//                    return this;
//                }

                  public InfoDialog create() {
                      mButton.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              mDialog.dismiss();
                              mButtonClickListener.onClick(v);
                          }
                      });
                      mButtonNo.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              mDialog.dismiss();
                              mButtonClickListenerNo.onClick(v);
                          }
                      });
                      mDialog.setContentView(mLayout);
                      mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
                      mDialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
                      return mDialog;
                    }
     }
 }

