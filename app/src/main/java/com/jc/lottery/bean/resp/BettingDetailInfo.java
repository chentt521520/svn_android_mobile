package com.jc.lottery.bean.resp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 14:59
 */

public class BettingDetailInfo {

    private OrderInfo orderInfo;
    private List<BetsListInfo> betsList = new ArrayList<BetsListInfo>();

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<BetsListInfo> getBetsList() {
        return betsList;
    }

    public void setBetsList(List<BetsListInfo> betsList) {
        this.betsList = betsList;
    }

    public class OrderInfo{
        private String betDouble;
        private String betMode;
        private String winAmount;
        private String buyTime;
        private String drawNumber;
        private String frisbeeStatus;
        private String gameName;
        private String multiDraw;
        private String noteNumber;
        private String orderCode;
        private String orderMoney;
        private String betStatus;
        private String winstate;
        private String cashUserName;
        private String cashingTime;

        public String getBetDouble() {
            return betDouble;
        }

        public void setBetDouble(String betDouble) {
            this.betDouble = betDouble;
        }

        public String getBetMode() {
            return betMode;
        }

        public void setBetMode(String betMode) {
            this.betMode = betMode;
        }

        public String getWinAmount() {
            return winAmount;
        }

        public void setWinAmount(String winAmount) {
            this.winAmount = winAmount;
        }

        public String getBuyTime() {
            return buyTime;
        }

        public void setBuyTime(String buyTime) {
            this.buyTime = buyTime;
        }

        public String getDrawNumber() {
            return drawNumber;
        }

        public void setDrawNumber(String drawNumber) {
            this.drawNumber = drawNumber;
        }

        public String getFrisbeeStatus() {
            return frisbeeStatus;
        }

        public void setFrisbeeStatus(String frisbeeStatus) {
            this.frisbeeStatus = frisbeeStatus;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getMultiDraw() {
            return multiDraw;
        }

        public void setMultiDraw(String multiDraw) {
            this.multiDraw = multiDraw;
        }

        public String getNoteNumber() {
            return noteNumber;
        }

        public void setNoteNumber(String noteNumber) {
            this.noteNumber = noteNumber;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderMoney() {
            return orderMoney;
        }

        public void setOrderMoney(String orderMoney) {
            this.orderMoney = orderMoney;
        }

        public String getBetStatus() {
            return betStatus;
        }

        public void setBetStatus(String betStatus) {
            this.betStatus = betStatus;
        }

        public String getWinstate() {
            return winstate;
        }

        public void setWinstate(String winstate) {
            this.winstate = winstate;
        }

        public String getCashUserName() {
            return cashUserName;
        }

        public void setCashUserName(String cashUserName) {
            this.cashUserName = cashUserName;
        }

        public String getCashingTime() {
            return cashingTime;
        }

        public void setCashingTime(String cashingTime) {
            this.cashingTime = cashingTime;
        }
    }

    public class BetsListInfo{

        private String betMode;
        private String betMoney;
        private String betNum;
        private String betStatus;
        private String winMoney;
        private String winState;

        public String getBetMode() {
            return betMode;
        }

        public void setBetMode(String betMode) {
            this.betMode = betMode;
        }

        public String getBetMoney() {
            return betMoney;
        }

        public void setBetMoney(String betMoney) {
            this.betMoney = betMoney;
        }

        public String getBetNum() {
            return betNum;
        }

        public void setBetNum(String betNum) {
            this.betNum = betNum;
        }

        public String getBetStatus() {
            return betStatus;
        }

        public void setBetStatus(String betStatus) {
            this.betStatus = betStatus;
        }

        public String getWinMoney() {
            return winMoney;
        }

        public void setWinMoney(String winMoney) {
            this.winMoney = winMoney;
        }

        public String getWinState() {
            return winState;
        }

        public void setWinState(String winState) {
            this.winState = winState;
        }
    }
}
