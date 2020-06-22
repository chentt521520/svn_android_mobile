package com.jc.lottery.bean.resp;

import com.jc.lottery.bean.req.pos_GetSettlement;
import com.jc.lottery.bean.req.pos_settlementQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 14:03
 */

public class SettlementQueryBean implements Serializable{

    private String cashMoney;
    private String code;
    private String commission;
    private String commissionPrize;
    private String commissionSales;
    private String commissionSwitch;
    private String instantPrize;
    private String instantSales;
    private String message;
    private String moneyStatus;
    private String state;
    private String ticketMoney;
    private String totalMoney;
    private ArrayList<CashMoneyBean> cashMoneyMap = new ArrayList<>();
    private Data data;

    public String getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(String cashMoney) {
        this.cashMoney = cashMoney;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCommissionSwitch() {
        return commissionSwitch;
    }

    public void setCommissionSwitch(String commissionSwitch) {
        this.commissionSwitch = commissionSwitch;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoneyStatus() {
        return moneyStatus;
    }

    public void setMoneyStatus(String moneyStatus) {
        this.moneyStatus = moneyStatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTicketMoney() {
        return ticketMoney;
    }

    public void setTicketMoney(String ticketMoney) {
        this.ticketMoney = ticketMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public ArrayList<CashMoneyBean> getCashMoneyMap() {
        return cashMoneyMap;
    }

    public void setCashMoneyMap(ArrayList<CashMoneyBean> cashMoneyMap) {
        this.cashMoneyMap = cashMoneyMap;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class CashMoneyBean implements Serializable{
        private String money;
        private String number;
        private String settleStatus;
        private boolean type = true;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(String settleStatus) {
            this.settleStatus = settleStatus;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }
    }

    public class Data implements Serializable{
        private SettlementInfo settlementInfo;

        public SettlementInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }
    }

    public class SettlementInfo implements Serializable{
        private ArrayList<pos_GetSettlement.BookList> bookList = new ArrayList<>();

        public ArrayList<pos_GetSettlement.BookList> getBookList() {
            return bookList;
        }

        public void setBookList(ArrayList<pos_GetSettlement.BookList> bookList) {
            this.bookList = bookList;
        }
    }
}
