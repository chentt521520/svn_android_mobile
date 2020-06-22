package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：结算
 * @ author: lr
 */
public class SettleSettlementBean {
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

    public SettleSettlementBean(String accountName, String password , String channel, DataBean data) {
        this.interfaceCode = "settlement";
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
    }

    public static class SettlementInfo{
        private String gameAlias;
        private Long startTime;
        private Long endTime;
        private String saleMoney;
        private String cashMoney;
        private String commission;
        private String commissionPrize;
        private String commissionSales;
        private String instantPrize;
        private String instantSales;
        private String commissionSwitch;
        private String totalMoney;
        private String moneyStatus;

        public SettlementInfo(String gameAlias, Long startTime, Long endTime, String saleMoney, String cashMoney, String commission, String commissionPrize, String commissionSales, String instantPrize, String instantSales, String commissionSwitch, String totalMoney, String moneyStatus) {
            this.gameAlias = gameAlias;
            this.startTime = startTime;
            this.endTime = endTime;
            this.saleMoney = saleMoney;
            this.cashMoney = cashMoney;
            this.commission = commission;
            this.commissionPrize = commissionPrize;
            this.commissionSales = commissionSales;
            this.instantPrize = instantPrize;
            this.instantSales = instantSales;
            this.commissionSwitch = commissionSwitch;
            this.totalMoney = totalMoney;
            this.moneyStatus = moneyStatus;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public String getSaleMoney() {
            return saleMoney;
        }

        public void setSaleMoney(String saleMoney) {
            this.saleMoney = saleMoney;
        }

        public String getCashMoney() {
            return cashMoney;
        }

        public void setCashMoney(String cashMoney) {
            this.cashMoney = cashMoney;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getCommissionPrize() {
            return commissionPrize;
        }

        public void setCommissionPrize(String commissionPrize) {
            this.commissionPrize = commissionPrize;
        }

        public String getCommissionSales() {
            return commissionSales;
        }

        public void setCommissionSales(String commissionSales) {
            this.commissionSales = commissionSales;
        }

        public String getInstantPrize() {
            return instantPrize;
        }

        public void setInstantPrize(String instantPrize) {
            this.instantPrize = instantPrize;
        }

        public String getInstantSales() {
            return instantSales;
        }

        public void setInstantSales(String instantSales) {
            this.instantSales = instantSales;
        }

        public String getCommissionSwitch() {
            return commissionSwitch;
        }

        public void setCommissionSwitch(String commissionSwitch) {
            this.commissionSwitch = commissionSwitch;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getMoneyStatus() {
            return moneyStatus;
        }

        public void setMoneyStatus(String moneyStatus) {
            this.moneyStatus = moneyStatus;
        }
    }

}
