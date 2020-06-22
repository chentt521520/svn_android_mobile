package com.jc.lottery.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.lottery.base.application.LotteryApplication;
import com.jc.lottery.bean.resp.PermissionsListBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 19:19
 */

public class GetPermissionsUtil {

    public boolean getPermissions(String per){
        String data = SPUtils.look(LotteryApplication.getInstance(), SPkey.permissionsList);
        List<PermissionsListBean> permissionsList = new ArrayList<>();
        Type listType = new TypeToken<List<PermissionsListBean>>() {
        }.getType();
        permissionsList = new Gson().fromJson(data, listType);
        for (int i = 0; i < permissionsList.size(); i++) {
            if (permissionsList.get(i).getAlias().equals(per)){
                return true;
            }
        }
        return false;
    }

    public boolean getPermissionsLt(String per){
        String data = SPUtils.look(LotteryApplication.getInstance(), SPkey.permissionsList);
        List<PermissionsListBean> permissionsList = new ArrayList<>();
        Type listType = new TypeToken<List<PermissionsListBean>>() {
        }.getType();
        permissionsList = new Gson().fromJson(data, listType);
        for (int i = 0; i < permissionsList.size(); i++) {
            if (permissionsList.get(i).getAlias().indexOf(per) != -1){
                return true;
            }
        }
        return false;
    }
}
