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

public class pos_GetSettlement implements Serializable{

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private Data data;

    public pos_GetSettlement(String accountName, String password, String channel, Data data) {
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
        private List<BookList> bookList = new ArrayList<BookList>();
        private List<SettlementQueryBean.CashMoneyBean> cashMoneyMap = new ArrayList<>();
        private String ticketMoney;
        private String cashMoney;
        private String commission;
        private String commissionPrize;
        private String commissionSales;
        private String instantPrize;
        private String instantSales;
        private String commissionSwitch;
        private String totalMoney;
        private String moneyStatus;

        public SettlementInfo(String gameAlias, List<BookList> bookList, List<SettlementQueryBean.CashMoneyBean> cashMoneyMap, String ticketMoney, String cashMoney, String commission, String commissionPrize, String commissionSales, String instantPrize, String instantSales, String commissionSwitch, String totalMoney, String moneyStatus) {
            this.gameAlias = gameAlias;
            this.bookList = bookList;
            this.cashMoneyMap = cashMoneyMap;
            this.ticketMoney = ticketMoney;
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

        public List<BookList> getBookList() {
            return bookList;
        }

        public void setBookList(List<BookList> bookList) {
            this.bookList = bookList;
        }

        public List<SettlementQueryBean.CashMoneyBean> getCashMoneyMap() {
            return cashMoneyMap;
        }

        public void setCashMoneyMap(List<SettlementQueryBean.CashMoneyBean> cashMoneyMap) {
            this.cashMoneyMap = cashMoneyMap;
        }

        public String getTicketMoney() {
            return ticketMoney;
        }

        public void setTicketMoney(String ticketMoney) {
            this.ticketMoney = ticketMoney;
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

    public class BookList implements Serializable {
        private String bookNum;
        private String schemeNum;

        public BookList(String bookNum, String schemeNum) {
            this.bookNum = bookNum;
            this.schemeNum = schemeNum;
        }

        public String getBookNum() {
            return bookNum;
        }

        public void setBookNum(String bookNum) {
            this.bookNum = bookNum;
        }

        public String getSchemeNum() {
            return schemeNum;
        }

        public void setSchemeNum(String schemeNum) {
            this.schemeNum = schemeNum;
        }
    }
}
