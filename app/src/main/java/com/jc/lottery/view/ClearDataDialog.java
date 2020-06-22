package com.jc.lottery.view;

import android.app.Dialog;
import android.content.Context;

import com.jc.lottery.R;

/**
 * @className： BindingPhoneDialog
 * @classDescription： 订单确认页面，清空数据
 * @author： 万
 * @createTime： 2017/12/4 19:48
 */
public class ClearDataDialog extends Dialog {
    private Context context;

    public ClearDataDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.setContentView(R.layout.clear_data_dialog);
    }
    public ClearDataDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
        this.setContentView(R.layout.clear_data_dialog);

    }
}
