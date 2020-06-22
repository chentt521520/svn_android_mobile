package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：结算列表
 * @ author: lr
 */
public class pos_GetSettleRecord {
    /**
     * interfaceCode : rechargeCardQuery
     * requestTime : 1455606858
     * accountName : ceshi
     * password : ceshi
     * channel : ceshi
     * data : {"rechargeCardInfo":{"cardNumber":"10013300716553096874"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private DataBean data;

    public pos_GetSettleRecord(String accountName, String password , String channel, DataBean.SettlementInfo settleInfo) {
        this.interfaceCode = "settleRecord";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(settleInfo);
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
         * rechargeCardInfo : {"gameAlias": "jkc"}
         */

        private SettlementInfo settleInfo;

        public DataBean(SettlementInfo settleInfo) {
            this.settleInfo = settleInfo;
        }

        public static class SettlementInfo {

            private String order_code;
            private String type;
            private String pageNo;
            private String settle_status;
            private String user_name;
            private String user_role;
            private String startTime;
            private String endTime;

            public SettlementInfo(String order_code, String type, String pageNo, String settle_status, String user_name, String user_role, String startTime, String endTime) {
                this.order_code = order_code;
                this.type = type;
                this.pageNo = pageNo;
                this.settle_status = settle_status;
                this.user_name = user_name;
                this.user_role = user_role;
                this.startTime = startTime;
                this.endTime = endTime;
            }
        }
    }


}
