package com.jc.lottery.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jc.lottery.R;
import com.jc.lottery.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ Create_time: 2018/9/3 on 11:07.
 * @ description：公用基础工具类
 * @ author: vchao
 */
public class CommonUtils {
    /**
     * 检查 EditText 是否为空
     *
     * @return 空 true   非空 false
     */
    public static boolean isEmpty(EditText et) {
        if (et != null) {
            String s = et.getText().toString();
            if (!TextUtils.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取文本框内容
     *
     * @return 有数据  true   空false
     */
    public static String getText(EditText et) {
        if (!isEmpty(et)) {
            String s = et.getText().toString().trim();
            return s;
        }
        return "";
    }

    /**
     * 检查 接口返回数据 是否正确
     * 错误固定格式
     * {
     * "code": "40008",
     * "data": {},
     * "message": "非法参数！",
     * "state":"00"
     * }
     *
     * @return 正确 true      false错误
     */
    public static boolean isOK(String s,Context context) {
        LogUtils.ee("  验证的数据 ====   " + s);
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String code;
                if (jsonObject.has("code")) {
                    code = jsonObject.getString("code");

                    if (TextUtils.equals(code, "40001")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.action_fail);
                        }
                        return false;
                    } else if (TextUtils.equals(code, "40002")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.yanqian_fail);
                        }
                        return false;
                    } else if (TextUtils.equals(code, "40003")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.param_is_null);
                        }
                        return false;
                    } else if (TextUtils.equals(code, "40005")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.param_no_error);
                        }
                        return false;
                    } else if (TextUtils.equals(code, "40006")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.system_error);
                        }
                        return false;
                    } else if (TextUtils.equals(code, "40007")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.data_is_reday);
                        }
                        return false;
                    } else if (TextUtils.equals(code, "50000")) {
                        try {
                            String message = jsonObject.getString("message");
//                            ToastUtils.showShort(message + "," + s);
                            ToastUtils.showShort(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.action_fail);
                        }
                        return false;
                    }else if (TextUtils.equals(code, "50001")) {
                        try {
                            String message = jsonObject.getString("message");
                            ToastUtils.showShort(message);
                            if (null != context) {
                                Intent intent = new Intent();
                                intent.setClass(context, LoginActivity.class);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShort(R.string.action_fail);
                        }
                        return false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort(R.string.net_not_ok);
            return false;
        }
        return true;
    }

    /**
     * 设置ListView根据item数量设置高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }
}
