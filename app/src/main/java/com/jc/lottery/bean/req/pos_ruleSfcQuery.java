package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2018/10/11 on 17:35.
 * @ description：规则查询 请求
 * @ author: vchao
 */
public class pos_ruleSfcQuery {

    /**
     * interfaceCode : findRule
     * requestTime : 1534917840
     * accountName : ceshi
     * data : {"ruleInfo":{"gameAlias":"k3"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;
    private DataBean data;

    public pos_ruleSfcQuery(String accountName, String password) {
        this.interfaceCode = "ruleSfcQuery";
        this.channel = "3";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
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
        /**
         * ruleInfo : {"gameAlias":"k3"}
         */

    }
}
