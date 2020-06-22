package com.jc.lottery.bean;

import com.jc.lottery.activity.immediate.SettlementActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 16:48
 */

public class SettlementGetDetailBean {
    private String code;
    private String message;
    private String pageCount;
    private String pageNum;
    private String state;
    private String totalNum;
    private List<GetList> getList = new ArrayList<GetList>();
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<GetList> getGetList() {
        return getList;
    }

    public void setGetList(List<GetList> getList) {
        this.getList = getList;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private SettlementInfo settlementInfo;

        public SettlementInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }
    }
    public class SettlementInfo{
        private String pageNo;
        private String settlementId;

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getSettlementId() {
            return settlementId;
        }

        public void setSettlementId(String settlementId) {
            this.settlementId = settlementId;
        }
    }
    public class GetList{
        private String auditTime;
        private String auditor;
        private String bookNum;
        private String cashMoney;
        private String channels;
        private String commission;
        private String commissionSwitch;
        private String gameName;
        private String instantPrize;
        private String instantSales;
        private String moneyStatus;
        private String orderCode;
        private String prizeCommission;
        private String salesCommission;
        private String schemeNum;
        private String settleStatus;
        private String ticketMoney;
        private String totalMoney;
        private String winMoney;
        private String ticketNum;
        private boolean type = false;

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getAuditor() {
            return auditor;
        }

        public void setAuditor(String auditor) {
            this.auditor = auditor;
        }

        public String getBookNum() {
            return bookNum;
        }

        public void setBookNum(String bookNum) {
            this.bookNum = bookNum;
        }

        public String getCashMoney() {
            return cashMoney;
        }

        public void setCashMoney(String cashMoney) {
            this.cashMoney = cashMoney;
        }

        public String getChannels() {
            return channels;
        }

        public void setChannels(String channels) {
            this.channels = channels;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getCommissionSwitch() {
            return commissionSwitch;
        }

        public void setCommissionSwitch(String commissionSwitch) {
            this.commissionSwitch = commissionSwitch;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
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

        public String getMoneyStatus() {
            return moneyStatus;
        }

        public void setMoneyStatus(String moneyStatus) {
            this.moneyStatus = moneyStatus;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPrizeCommission() {
            return prizeCommission;
        }

        public void setPrizeCommission(String prizeCommission) {
            this.prizeCommission = prizeCommission;
        }

        public String getSalesCommission() {
            return salesCommission;
        }

        public void setSalesCommission(String salesCommission) {
            this.salesCommission = salesCommission;
        }

        public String getSchemeNum() {
            return schemeNum;
        }

        public void setSchemeNum(String schemeNum) {
            this.schemeNum = schemeNum;
        }

        public String getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(String settleStatus) {
            this.settleStatus = settleStatus;
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

        public String getWinMoney() {
            return winMoney;
        }

        public void setWinMoney(String winMoney) {
            this.winMoney = winMoney;
        }

        public String getTicketNum() {
            return ticketNum;
        }

        public void setTicketNum(String ticketNum) {
            this.ticketNum = ticketNum;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }
    }
}
