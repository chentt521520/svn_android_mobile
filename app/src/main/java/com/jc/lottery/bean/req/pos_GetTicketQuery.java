package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2020/04/09 on 15:21.
 * @ description：投注、兑奖记录查询
 * @ author: lr
 */
public class pos_GetTicketQuery {
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
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private DataBean data;

    public pos_GetTicketQuery(String accountName, String password , String channel, DataBean data) {
        this.interfaceCode = "ticketQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
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
        /**
         * rechargeCardInfo : {"gameAlias": "jkc"}
         */
        private BettingInfo bettingInfo;

        public DataBean(BettingInfo bettingInfo) {
            this.bettingInfo = bettingInfo;
        }
    }

    public static class BettingInfo{
        private String type;
        private String pageNo;
        private String startTime;
        private String endTime;

        public BettingInfo(String type, String pageNo, String startTime, String endTime) {
            this.type = type;
            this.pageNo = pageNo;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

}
