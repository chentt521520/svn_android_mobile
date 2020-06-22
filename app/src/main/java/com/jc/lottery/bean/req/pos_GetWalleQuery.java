package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：钱包查询
 * @ author: lr
 */
public class pos_GetWalleQuery {
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
    private int accountId;
    private DataBean data;

    public pos_GetWalleQuery(int accountId, DataBean data) {
        this.interfaceCode = "walleQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountId = accountId;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
        private WalleInfo settlementInfo;

        public DataBean(WalleInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }

        public WalleInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(WalleInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }
    }

    public static class WalleInfo{
        private String roleAlias;
        private String pageNo;
        private String userName;

        public WalleInfo(String roleAlias, String pageNo, String userName) {
            this.roleAlias = roleAlias;
            this.pageNo = pageNo;
            this.userName = userName;
        }

        public String getRoleAlias() {
            return roleAlias;
        }

        public void setRoleAlias(String roleAlias) {
            this.roleAlias = roleAlias;
        }

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

}
