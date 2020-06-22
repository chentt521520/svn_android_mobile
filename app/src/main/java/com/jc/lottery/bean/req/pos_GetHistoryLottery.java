package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：获取历史直播开奖记录
 * @ author: lr
 */
public class pos_GetHistoryLottery {
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
    private DataBean data;

    public pos_GetHistoryLottery(String accountName, DataBean.DrawInfo drawInfo) {
        this.interfaceCode = "historyLottery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = new DataBean(drawInfo);
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

        private DrawInfo drawInfo;

        public DataBean(DrawInfo drawInfo) {
            this.drawInfo = drawInfo;
        }

        public static class DrawInfo {

            private String drawNumber;
            private String gameAlias;
            private String pageNo;
            private String pageSize;

            public DrawInfo(String drawNumber, String gameAlias, String pageNo, String pageSize) {
                this.drawNumber = drawNumber;
                this.gameAlias = gameAlias;
                this.pageNo = pageNo;
                this.pageSize = pageSize;
            }
        }
    }

}
