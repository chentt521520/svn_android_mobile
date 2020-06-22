package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：领取记录
 * @ author: lr
 */
public class pos_GetRecordInfo {
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

    public pos_GetRecordInfo(String accountName, String password , String channel, DataBean.GetInfo getInfo) {
        this.interfaceCode = "getRecord";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(getInfo);
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

        private GetInfo getInfo;

        public DataBean(GetInfo getInfo) {
            this.getInfo = getInfo;
        }

        public GetInfo getGetInfo() {
            return getInfo;
        }

        public void setGetInfo(GetInfo getInfo) {
            this.getInfo = getInfo;
        }

        public static class GetInfo {

            private String gameAlias;
            private String pageNo;
            private String status;
            private String orderCode;
            private String receiveStatus;
            private String recipient;
            private String startTime;
            private String endTime;
            private String date;

            public GetInfo(String gameAlias, String pageNo, String status, String orderCode, String receiveStatus, String recipient, String startTime, String endTime, String date) {
                this.gameAlias = gameAlias;
                this.pageNo = pageNo;
                this.status = status;
                this.orderCode = orderCode;
                this.receiveStatus = receiveStatus;
                this.recipient = recipient;
                this.startTime = startTime;
                this.endTime = endTime;
                this.date = date;
            }

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }

            public String getPageNo() {
                return pageNo;
            }

            public void setPageNo(String pageNo) {
                this.pageNo = pageNo;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getReceiveStatus() {
                return receiveStatus;
            }

            public void setReceiveStatus(String receiveStatus) {
                this.receiveStatus = receiveStatus;
            }

            public String getRecipient() {
                return recipient;
            }

            public void setRecipient(String recipient) {
                this.recipient = recipient;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }

}
