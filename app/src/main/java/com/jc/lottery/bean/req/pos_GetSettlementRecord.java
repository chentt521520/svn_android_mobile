package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：结算列表
 * @ author: lr
 */
public class pos_GetSettlementRecord {
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

    public pos_GetSettlementRecord(String accountName, String password , String channel, DataBean.SettlementInfo settlementInfo) {
        this.interfaceCode = "settlementRecord";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(settlementInfo);
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

        private SettlementInfo settlementInfo;

        public DataBean(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }

        public SettlementInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }

        public static class SettlementInfo {

            private String orderCode;
            private String settleStatus;
            private String pageNo;
            private String operateType;
            private String startTime;
            private String endTime;
            private String date;

            public SettlementInfo(String orderCode, String settleStatus, String pageNo, String operateType) {
                this.orderCode = orderCode;
                this.settleStatus = settleStatus;
                this.pageNo = pageNo;
                this.operateType = operateType;
            }

            public SettlementInfo(String orderCode, String settleStatus, String pageNo, String operateType, String startTime, String endTime, String date) {
                this.orderCode = orderCode;
                this.settleStatus = settleStatus;
                this.pageNo = pageNo;
                this.operateType = operateType;
                this.startTime = startTime;
                this.endTime = endTime;
                this.date = date;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getSettleStatus() {
                return settleStatus;
            }

            public void setSettleStatus(String settleStatus) {
                this.settleStatus = settleStatus;
            }

            public String getPageNo() {
                return pageNo;
            }

            public void setPageNo(String pageNo) {
                this.pageNo = pageNo;
            }

            public String getOperateType() {
                return operateType;
            }

            public void setOperateType(String operateType) {
                this.operateType = operateType;
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
