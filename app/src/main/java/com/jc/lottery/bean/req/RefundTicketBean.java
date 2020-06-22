package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/12/24 on 10:49.
 * @ description：退票
 * @ author: lr
 */
public class RefundTicketBean {
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
    private DataBean data;

    public RefundTicketBean(String accountName, DataBean data) {
        this.interfaceCode = "refundTicket";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
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
        private OrderInfo orderInfo;

        public DataBean(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }
    }

    public static class OrderInfo{
        private String gameAlias;
        private String drawNumber;
        private String orderCode;

        public OrderInfo(String gameAlias, String drawNumber, String orderCode) {
            this.gameAlias = gameAlias;
            this.drawNumber = drawNumber;
            this.orderCode = orderCode;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getDrawNumber() {
            return drawNumber;
        }

        public void setDrawNumber(String drawNumber) {
            this.drawNumber = drawNumber;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }
    }

}
