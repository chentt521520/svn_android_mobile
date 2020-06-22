package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/10/09 on 10:09.
 * @ description：权限查询
 * @ author: lr
 */
public class TerminalPerQueryInfo {
    /**
     * interfaceCode : winQuery
     * requestTime : 1455606858
     * accountName : ceshi
     * password : ceshi
     * channel : ceshi
     * data : {"rechargeCardInfo":{"safetyCode": "13CE59C2-2CDC19A1-EA5EFD4D-22303756"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public TerminalPerQueryInfo(String accountName, DataBean data) {
        this.interfaceCode = "terminalPerQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = data;
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

        private PermissionsInfo permissionsInfo;

        public DataBean(PermissionsInfo permissionsInfo) {
            this.permissionsInfo = permissionsInfo;
        }

        public PermissionsInfo getPermissionsInfo() {
            return permissionsInfo;
        }

        public void setPermissionsInfo(PermissionsInfo permissionsInfo) {
            this.permissionsInfo = permissionsInfo;
        }
    }

    public static class PermissionsInfo{
        private String type;

        public PermissionsInfo(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
