package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2018/9/30 on 17:37.
 * @ description：无参数的请求参数
 * @ author: vchao
 */
public class Req_NoneParm {

    /**
     * interfaceCode : timeCalibration
     * requestTime : 1455606858
     * accountName : admin
     * data : {}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public Req_NoneParm(String interfaceCode, String accountName) {
        this.interfaceCode = interfaceCode;
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = new DataBean();
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
