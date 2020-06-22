package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class pos_GetRechargeRecord {
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

    public pos_GetRechargeRecord(String accountName, String password , String channel, DataBean data) {
        this.interfaceCode = "paymentRecord";
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
        private RechargeInfo rechargeInfo;

        public DataBean(RechargeInfo rechargeInfo) {
            this.rechargeInfo = rechargeInfo;
        }

        public RechargeInfo getRechargeInfo() {
            return rechargeInfo;
        }

        public void setRechargeInfo(RechargeInfo rechargeInfo) {
            this.rechargeInfo = rechargeInfo;
        }
    }

    public static class RechargeInfo{
        private String pageNo;
        private String orderCode;
        private String phone;
        private String payMethod;
        private String payState;
        private String userName;
        private String payType;

        public RechargeInfo(String pageNo, String orderCode, String phone, String payMethod, String payState, String userName, String payType) {
            this.pageNo = pageNo;
            this.orderCode = orderCode;
            this.phone = phone;
            this.payMethod = payMethod;
            this.payState = payState;
            this.userName = userName;
            this.payType = payType;
        }

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

}
