package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/11/06 on 14:49.
 * @ description：兑奖查询
 * @ author: lr
 */
public class TerminalCashPrizeBean {
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

    public TerminalCashPrizeBean(String accountName, DataBean data) {
        this.interfaceCode = "terminalCashPrize";
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
        private CashPrizeInfo cashPrizeInfo;

        public DataBean(CashPrizeInfo cashPrizeInfo) {
            this.cashPrizeInfo = cashPrizeInfo;
        }

        public CashPrizeInfo getCashPrizeInfo() {
            return cashPrizeInfo;
        }

        public void setCashPrizeInfo(CashPrizeInfo cashPrizeInfo) {
            this.cashPrizeInfo = cashPrizeInfo;
        }
    }

    public static class CashPrizeInfo{
        private String gameAlias;
        private String orderCode;
        private String drawNumber;

        public CashPrizeInfo(String gameAlias, String orderCode, String drawNumber) {
            this.gameAlias = gameAlias;
            this.orderCode = orderCode;
            this.drawNumber = drawNumber;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getDrawNumber() {
            return drawNumber;
        }

        public void setDrawNumber(String drawNumber) {
            this.drawNumber = drawNumber;
        }
    }

}
