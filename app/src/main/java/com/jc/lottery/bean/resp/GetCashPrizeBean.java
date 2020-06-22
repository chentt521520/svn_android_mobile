package com.jc.lottery.bean.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 16:01
 */

public class GetCashPrizeBean implements Serializable {

    private String code;
    private String cashStatus;
    private String message;
    private String state;
    private List<CashPrizeBean> cashPrizeList = new ArrayList<CashPrizeBean>();
    private List<WinListBean> winList = new ArrayList<WinListBean>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<CashPrizeBean> getCashPrizeList() {
        return cashPrizeList;
    }

    public void setCashPrizeList(List<CashPrizeBean> cashPrizeList) {
        this.cashPrizeList = cashPrizeList;
    }

    public List<WinListBean> getWinList() {
        return winList;
    }

    public void setWinList(List<WinListBean> winList) {
        this.winList = winList;
    }

    public class CashPrizeBean implements Serializable{
        private String betId;
        private String betNum;
        private String buyTime;
        private String drawNumber;
        private String gameName;
        private String prizeTime;
        private String qrCode;
        private String realpayMoney;
        private String terminalNum;
        private String winAmount;
        private String winMoney;
        private String winLevel;
        private String keyStore;
        private String safetyCode;
        private String multidraw;

        public String getBetId() {
            return betId;
        }

        public void setBetId(String betId) {
            this.betId = betId;
        }

        public String getBetNum() {
            return betNum;
        }

        public void setBetNum(String betNum) {
            this.betNum = betNum;
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

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getPrizeTime() {
            return prizeTime;
        }

        public void setPrizeTime(String prizeTime) {
            this.prizeTime = prizeTime;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getRealpayMoney() {
            return realpayMoney;
        }

        public void setRealpayMoney(String realpayMoney) {
            this.realpayMoney = realpayMoney;
        }

        public String getTerminalNum() {
            return terminalNum;
        }

        public void setTerminalNum(String terminalNum) {
            this.terminalNum = terminalNum;
        }

        public String getWinAmount() {
            return winAmount;
        }

        public void setWinAmount(String winAmount) {
            this.winAmount = winAmount;
        }

        public String getWinMoney() {
            return winMoney;
        }

        public void setWinMoney(String winMoney) {
            this.winMoney = winMoney;
        }

        public String getWinLevel() {
            return winLevel;
        }

        public void setWinLevel(String winLevel) {
            this.winLevel = winLevel;
        }

        public String getKeyStore() {
            return keyStore;
        }

        public void setKeyStore(String keyStore) {
            this.keyStore = keyStore;
        }

        public String getSafetyCode() {
            return safetyCode;
        }

        public void setSafetyCode(String safetyCode) {
            this.safetyCode = safetyCode;
        }

        public String getMultidraw() {
            return multidraw;
        }

        public void setMultidraw(String multidraw) {
            this.multidraw = multidraw;
        }
    }

    public class WinListBean implements Serializable{
        private String drawNumber;
        private String winState;
        private List<WinEachList> winEachList = new ArrayList<WinEachList>();

        public String getDrawNumber() {
            return drawNumber;
        }

        public void setDrawNumber(String drawNumber) {
            this.drawNumber = drawNumber;
        }

        public String getWinState() {
            return winState;
        }

        public void setWinState(String winState) {
            this.winState = winState;
        }

        public List<WinEachList> getWinEachList() {
            return winEachList;
        }

        public void setWinEachList(List<WinEachList> winEachList) {
            this.winEachList = winEachList;
        }
    }

    public class WinEachList implements Serializable{
        private String winMoney;
        private String winNum;
        private String winLevel;

        public String getWinMoney() {
            return winMoney;
        }

        public void setWinMoney(String winMoney) {
            this.winMoney = winMoney;
        }

        public String getWinNum() {
            return winNum;
        }

        public void setWinNum(String winNum) {
            this.winNum = winNum;
        }

        public String getWinLevel() {
            return winLevel;
        }

        public void setWinLevel(String winLevel) {
            this.winLevel = winLevel;
        }
    }
}
