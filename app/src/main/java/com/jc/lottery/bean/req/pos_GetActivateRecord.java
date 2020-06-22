package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：结算列表
 * @ author: lr
 */
public class pos_GetActivateRecord {
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

    public pos_GetActivateRecord(String accountName, String password , String channel, DataBean.RecordInfo recordInfo) {
        this.interfaceCode = "activateRecord";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(recordInfo);
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

        private RecordInfo recordInfo;

        public DataBean(RecordInfo recordInfo) {
            this.recordInfo = recordInfo;
        }

        public RecordInfo getRecordInfo() {
            return recordInfo;
        }

        public void setRecordInfo(RecordInfo recordInfo) {
            this.recordInfo = recordInfo;
        }

        public static class RecordInfo {

            private String pageNo;
            private String recipient;
            private String activeState;
            private String startTime;
            private String endTime;
            private String date;

            public RecordInfo(String pageNo, String recipient, String activeState, String startTime, String endTime, String date) {
                this.pageNo = pageNo;
                this.recipient = recipient;
                this.activeState = activeState;
                this.startTime = startTime;
                this.endTime = endTime;
                this.date = date;
            }

            public String getPageNo() {
                return pageNo;
            }

            public void setPageNo(String pageNo) {
                this.pageNo = pageNo;
            }

            public String getRecipient() {
                return recipient;
            }

            public void setRecipient(String recipient) {
                this.recipient = recipient;
            }

            public String getActiveState() {
                return activeState;
            }

            public void setActiveState(String activeState) {
                this.activeState = activeState;
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
