package com.jc.lottery.bean.req;

import java.util.List;

public class Fast3CommitRequest {

    /**
     * interfaceCode : createOrder
     * requestTime : 1534818960
     * accountId : 1
     * data : {"orderInfo":{"gameId":"168","gameAlias":"k3","ticketList":[{"ticket":"2 2 2","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"3 3 3","eachTotalMoney":"200","eachBetMode":"01"}],"terminalId":"2","terminal":"111","multiDraw":"3","betDouble":"1","noteNumber":"1","totalMoney":"400","betMode":"01","drawId":"3725","drawNumber":"180821013","dataSource":"0","gamePlayId":"12"}}
     * sign : 6D9B00C2F6CD4EB0921527432A632031
     */

    private String interfaceCode;
    private int requestTime;
    private int accountId;
    private DataBean data;
    private String sign;

    public Fast3CommitRequest() {
    }

    public Fast3CommitRequest(String interfaceCode, int requestTime, int accountId, DataBean data, String sign) {
        this.interfaceCode = interfaceCode;
        this.requestTime = requestTime;
        this.accountId = accountId;
        this.data = data;
        this.sign = sign;
    }

    public Fast3CommitRequest(int accountId, String sign,
                              String gameId, String gameAlias, String terminalId, String terminal, String multiDraw,
                              String betDouble, String noteNumber, String totalMoney, String betMode, String drawId,
                              String drawNumber, String dataSource, String gamePlayId, List<DataBean.OrderInfoBean.TicketListBean> ticketList) {
        this.interfaceCode = "createOrder";
        this.requestTime = (int) (System.currentTimeMillis() / 1000);
        this.accountId = accountId;

        DataBean.OrderInfoBean orderInfoBean = new DataBean.OrderInfoBean(gameId, gameAlias, terminalId, terminal, multiDraw, betDouble, noteNumber, totalMoney, betMode, drawId, drawNumber, dataSource, gamePlayId, ticketList);
        DataBean dataBean = new DataBean(orderInfoBean);
        this.data = dataBean;
        this.sign = sign;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class DataBean {
        /**
         * orderInfo : {"gameId":"168","gameAlias":"k3","ticketList":[{"ticket":"2 2 2","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"3 3 3","eachTotalMoney":"200","eachBetMode":"01"}],"terminalId":"2","terminal":"111","multiDraw":"3","betDouble":"1","noteNumber":"1","totalMoney":"400","betMode":"01","drawId":"3725","drawNumber":"180821013","dataSource":"0","gamePlayId":"12"}
         */

        private OrderInfoBean orderInfo;

        public DataBean() {
        }

        public DataBean(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class OrderInfoBean {
            /**
             * gameId : 168
             * gameAlias : k3
             * ticketList : [{"ticket":"2 2 2","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"3 3 3","eachTotalMoney":"200","eachBetMode":"01"}]
             * terminalId : 2
             * terminal : 111
             * multiDraw : 3
             * betDouble : 1
             * noteNumber : 1
             * totalMoney : 400
             * betMode : 01
             * drawId : 3725
             * drawNumber : 180821013
             * dataSource : 0
             * gamePlayId : 12
             */

            private String gameId;
            private String gameAlias;
            private String terminalId;// 设备id
            private String terminal;// 设备编号
            private String multiDraw;
            private String betDouble;
            private String noteNumber;
            private String totalMoney;
            private String betMode;
            private String drawId;
            private String drawNumber;
            private String dataSource;
            private String gamePlayId;
            private List<TicketListBean> ticketList;

            public OrderInfoBean() {
            }

            public OrderInfoBean(String gameId, String gameAlias, String terminalId, String terminal, String multiDraw, String betDouble, String noteNumber, String totalMoney, String betMode, String drawId, String drawNumber, String dataSource, String gamePlayId, List<TicketListBean> ticketList) {
                this.gameId = gameId;
                this.gameAlias = gameAlias;
                this.terminalId = terminalId;
                this.terminal = terminal;
                this.multiDraw = multiDraw;
                this.betDouble = betDouble;
                this.noteNumber = noteNumber;
                this.totalMoney = totalMoney;
                this.betMode = betMode;
                this.drawId = drawId;
                this.drawNumber = drawNumber;
                this.dataSource = dataSource;
                this.gamePlayId = gamePlayId;
                this.ticketList = ticketList;
            }

            public String getGameId() {
                return gameId;
            }

            public void setGameId(String gameId) {
                this.gameId = gameId;
            }

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }

            public String getTerminalId() {
                return terminalId;
            }

            public void setTerminalId(String terminalId) {
                this.terminalId = terminalId;
            }

            public String getTerminal() {
                return terminal;
            }

            public void setTerminal(String terminal) {
                this.terminal = terminal;
            }

            public String getMultiDraw() {
                return multiDraw;
            }

            public void setMultiDraw(String multiDraw) {
                this.multiDraw = multiDraw;
            }

            public String getBetDouble() {
                return betDouble;
            }

            public void setBetDouble(String betDouble) {
                this.betDouble = betDouble;
            }

            public String getNoteNumber() {
                return noteNumber;
            }

            public void setNoteNumber(String noteNumber) {
                this.noteNumber = noteNumber;
            }

            public String getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(String totalMoney) {
                this.totalMoney = totalMoney;
            }

            public String getBetMode() {
                return betMode;
            }

            public void setBetMode(String betMode) {
                this.betMode = betMode;
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

            public String getDataSource() {
                return dataSource;
            }

            public void setDataSource(String dataSource) {
                this.dataSource = dataSource;
            }

            public String getGamePlayId() {
                return gamePlayId;
            }

            public void setGamePlayId(String gamePlayId) {
                this.gamePlayId = gamePlayId;
            }

            public List<TicketListBean> getTicketList() {
                return ticketList;
            }

            public void setTicketList(List<TicketListBean> ticketList) {
                this.ticketList = ticketList;
            }

            public static class TicketListBean {
                /**
                 * ticket : 2 2 2
                 * eachTotalMoney : 200
                 * eachBetMode : 01
                 */

                private String ticket;
                private String eachTotalMoney;
                private String eachBetMode;

                public TicketListBean() {
                }

                public TicketListBean(String ticket, String eachTotalMoney, String eachBetMode) {
                    this.ticket = ticket;
                    this.eachTotalMoney = eachTotalMoney;
                    this.eachBetMode = eachBetMode;
                }

                public String getTicket() {
                    return ticket;
                }

                public void setTicket(String ticket) {
                    this.ticket = ticket;
                }

                public String getEachTotalMoney() {
                    return eachTotalMoney;
                }

                public void setEachTotalMoney(String eachTotalMoney) {
                    this.eachTotalMoney = eachTotalMoney;
                }

                public String getEachBetMode() {
                    return eachBetMode;
                }

                public void setEachBetMode(String eachBetMode) {
                    this.eachBetMode = eachBetMode;
                }
            }
        }
    }
}
