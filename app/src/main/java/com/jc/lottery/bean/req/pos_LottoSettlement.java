package com.jc.lottery.bean.req;

import com.jc.lottery.bean.resp.SettlementQueryBean;
import com.jc.lottery.util.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description: 结算
 * @date:${DATA} 18:05
 */

public class pos_LottoSettlement implements Serializable{

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private Data data;

    public pos_LottoSettlement(String accountName, String password, String channel, Data data) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable{
        private SettlementInfo settlementInfo;

        public Data(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }

        public SettlementInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }
    }

    public static class SettlementInfo implements Serializable{

        private String gameAlias;
        private String startTime;
        private String endTime;
        private String saleMoney;
        private String cashMoney;
        private String commission;
        private String commissionPrize;
        private String commissionSales;
        private String lottoPrize;
        private String lottoSales;
        private String commissionSwitch;
        private String totalMoney;
        private String moneyStatus;
        private String cashBets;
        private String saleBets;
        private String totalLimit;
        private String surplusAmount;
        private String usedAmount;
        private String settleQuota;

        public SettlementInfo(String gameAlias, String startTime, String endTime, String saleMoney, String cashMoney, String commission, String commissionPrize, String commissionSales, String lottoPrize, String lottoSales, String commissionSwitch, String totalMoney, String moneyStatus, String cashBets, String saleBets, String totalLimit, String surplusAmount, String usedAmount, String settleQuota) {
            this.gameAlias = gameAlias;
            this.startTime = startTime;
            this.endTime = endTime;
            this.saleMoney = saleMoney;
            this.cashMoney = cashMoney;
            this.commission = commission;
            this.commissionPrize = commissionPrize;
            this.commissionSales = commissionSales;
            this.lottoPrize = lottoPrize;
            this.lottoSales = lottoSales;
            this.commissionSwitch = commissionSwitch;
            this.totalMoney = totalMoney;
            this.moneyStatus = moneyStatus;
            this.cashBets = cashBets;
            this.saleBets = saleBets;
            this.totalLimit = totalLimit;
            this.surplusAmount = surplusAmount;
            this.usedAmount = usedAmount;
            this.settleQuota = settleQuota;
        }
    }
}
