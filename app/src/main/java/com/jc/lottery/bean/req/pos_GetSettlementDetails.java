package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：结算列表
 * @ author: lr
 */
public class pos_GetSettlementDetails {
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

    public pos_GetSettlementDetails(String accountName, String password , String channel, DataBean.SettlementInfo settlementInfo) {
        this.interfaceCode = "settlementDetails";
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

            private String settlementId;
            private String pageNo;

            public SettlementInfo(String settlementId, String pageNo) {
                this.settlementId = settlementId;
                this.pageNo = pageNo;
            }

            public String getSettlementId() {
                return settlementId;
            }

            public void setSettlementId(String settlementId) {
                this.settlementId = settlementId;
            }

            public String getPageNo() {
                return pageNo;
            }

            public void setPageNo(String pageNo) {
                this.pageNo = pageNo;
            }
        }
    }
}
