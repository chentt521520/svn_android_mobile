package com.jc.lottery.bean.resp;

import java.io.Serializable;

/**
 * @author lr
 * @description:
 * @date:${DATA} 15:02
 */

public class WinQueryInfo implements Serializable {

    private String activeState;
    private String cashState;
    private String channel;
    private String code;
    private String gameId;
    private String gameName;
    private String message;
    private String number;
    private String orderCode;
    private String parentName;
    private String recipient;
    private String secondOpenState;
    private String state;
    private String winMoney;
    private String winState;
    private Data data;

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getCashState() {
        return cashState;
    }

    public void setCashState(String cashState) {
        this.cashState = cashState;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSecondOpenState() {
        return secondOpenState;
    }

    public void setSecondOpenState(String secondOpenState) {
        this.secondOpenState = secondOpenState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{
        private OrderInfo orderInfo;

        public Data(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }
    }

    public class OrderInfo implements Serializable{
        private String logisticsCode;
        private String manualCode;
        private String securityCode;
        private String terminalNum;

        public OrderInfo(String logisticsCode, String manualCode, String securityCode, String terminalNum) {
            this.logisticsCode = logisticsCode;
            this.manualCode = manualCode;
            this.securityCode = securityCode;
            this.terminalNum = terminalNum;
        }

        public String getLogisticsCode() {
            return logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getManualCode() {
            return manualCode;
        }

        public void setManualCode(String manualCode) {
            this.manualCode = manualCode;
        }

        public String getSecurityCode() {
            return securityCode;
        }

        public void setSecurityCode(String securityCode) {
            this.securityCode = securityCode;
        }

        public String getTerminalNum() {
            return terminalNum;
        }

        public void setTerminalNum(String terminalNum) {
            this.terminalNum = terminalNum;
        }
    }
}
