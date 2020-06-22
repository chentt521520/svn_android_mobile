package com.jc.lottery.bean.resp;

import com.jc.lottery.bean.TicketListBean;

import java.io.Serializable;
import java.util.List;

/**
 * @ Create_time: 2018/9/17 on 20:42.
 * @ description：下单投注成功 返回数据
 * @ author: vchao
 */
public class Resp_Order_Success implements Serializable {

    /**
     * betStatus : 00
     * code : 00000
     * data : {"orderInfo":{"betDouble":"1","betMode":"01","dataSource":"1","drawId":"11001","drawNumber":"910731","frisbeeStatus":"01","gameAlias":"kl8","gameId":"195","gamePlayNum":"kl8001","multiDraw":"1","noteNumber":"1","terminal":"100202","terminalId":"2","ticketList":[{"eachBetMode":"01","eachTotalMoney":"20","ticket":"34"}],"totalMoney":"20"}}
     * message : 无线pos下注成功!
     * orderCode : 201809261528002350970078
     * orderStatus : 00
     * safetyCode : {"ketStore":"","safetyCode":"7C47A961-9553FCF8-10C16838-70342313"}
     * state : 00
     */

    private String betStatus;
    private String code;
    private DataBean data;
    private String message;
    private String orderCode;
    private String orderStatus;
    private String safetyCode;
    private String state;
    private Long buyTime;
    private Long prizeTime;
    private Long endTime;

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSafetyCode() {
        return safetyCode;
    }

    public void setSafetyCode(String safetyCode) {
        this.safetyCode = safetyCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Long getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(Long prizeTime) {
        this.prizeTime = prizeTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public static class DataBean implements Serializable {
        /**
         * orderInfo : {"betDouble":"1","betMode":"01","dataSource":"1","drawId":"11001","drawNumber":"910731","frisbeeStatus":"01","gameAlias":"kl8","gameId":"195","gamePlayNum":"kl8001","multiDraw":"1","noteNumber":"1","terminal":"100202","terminalId":"2","ticketList":[{"eachBetMode":"01","eachTotalMoney":"20","ticket":"34"}],"totalMoney":"20"}
         */

        private OrderInfoBean orderInfo;

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class OrderInfoBean implements Serializable {
            /**
             * betDouble : 1
             * betMode : 01
             * dataSource : 1
             * drawId : 11001
             * drawNumber : 910731
             * frisbeeStatus : 01
             * gameAlias : kl8
             * gameId : 195
             * gamePlayNum : kl8001
             * multiDraw : 1
             * noteNumber : 1
             * terminal : 100202
             * terminalId : 2
             * ticketList : [{"eachBetMode":"01","eachTotalMoney":"20","ticket":"34"}]
             * totalMoney : 20
             */

            private String betDouble;
            private String betMode;
            private String dataSource;
            private String drawNumber;
            private String gameAlias;
            private String gamePlayNum;
            private String multiDraw;
            private String noteNumber;
            private String terminal;
            private String totalMoney;

            private String drawId;
            private String frisbeeStatus;
            private String gameId;
            private String terminalId;
            private List<TicketListBean> ticketList;

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

            public String getDataSource() {
                return dataSource;
            }

            public void setDataSource(String dataSource) {
                this.dataSource = dataSource;
            }

            public String getDrawId() {
                return drawId;
            }

            public void setDrawId(String drawId) {
                this.drawId = drawId;
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

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }

            public String getGameId() {
                return gameId;
            }

            public void setGameId(String gameId) {
                this.gameId = gameId;
            }

            public String getGamePlayNum() {
                return gamePlayNum;
            }

            public void setGamePlayNum(String gamePlayNum) {
                this.gamePlayNum = gamePlayNum;
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

            public String getTerminal() {
                return terminal;
            }

            public void setTerminal(String terminal) {
                this.terminal = terminal;
            }

            public String getTerminalId() {
                return terminalId;
            }

            public void setTerminalId(String terminalId) {
                this.terminalId = terminalId;
            }

            public String getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(String totalMoney) {
                this.totalMoney = totalMoney;
            }

            public List<TicketListBean> getTicketList() {
                return ticketList;
            }

            public void setTicketList(List<TicketListBean> ticketList) {
                this.ticketList = ticketList;
            }
        }
    }
}
