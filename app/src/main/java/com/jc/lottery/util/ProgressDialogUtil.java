package com.jc.lottery.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.jc.lottery.R;
import com.jc.lottery.view.CustomDialog;

public class ProgressDialogUtil {
	public ProgressDialog progressDialog;
	public Context context;
	
	/**
	 * 构造方法
	 */

	public ProgressDialogUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	// Protected methods
    //***************************************
        public void showProgressDialog(String message) {
            try {
                progressDialog = new CustomDialog(context, R.style.CustomDialog, message);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

//                progressDialog = ProgressDialog.show(context, "",  message, true);
//                progressDialog.setIndeterminate(true);
//                progressDialog.setCancelable(true);
        }
        
        public void dismissProgressDialog() {
            try {
                if (progressDialog != null) {
                        progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
