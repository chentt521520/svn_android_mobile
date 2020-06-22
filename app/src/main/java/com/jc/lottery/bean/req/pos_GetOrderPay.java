package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class pos_GetOrderPay {
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
    private String channel;
    private DataBean data;

    public pos_GetOrderPay(String accountName, String password,String channel, DataBean data) {
        this.interfaceCode = "orderData";
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
        private OrderData orderData;

        public DataBean(OrderData orderData) {
            this.orderData = orderData;
        }

        public OrderData getOrderData() {
            return orderData;
        }

        public void setOrderData(OrderData oderData) {
            this.orderData = orderData;
        }
    }

    public static class OrderData{
        private String orderCode;
        private String payChannl;

        public OrderData(String orderCode, String payChannl) {
            this.orderCode = orderCode;
            this.payChannl = payChannl;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPayChannl() {
            return payChannl;
        }

        public void setPayChannl(String payChannl) {
            this.payChannl = payChannl;
        }
    }

}
