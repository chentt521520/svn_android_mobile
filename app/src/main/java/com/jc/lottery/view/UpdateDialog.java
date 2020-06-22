package com.jc.lottery.view;

import android.app.Dialog;
import android.content.Context;

import com.jc.lottery.R;

/**
 * @className： BindingPhoneDialog
 * @classDescription： 版本更新
 * @author： 万
 * @createTime： 2017/12/4 19:48
 */
public class UpdateDialog extends Dialog {
    private Context context;

    public UpdateDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.setContentView(R.layout.update_dialog);
    }
    public UpdateDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
        this.setContentView(R.layout.update_dialog);

    }
}
