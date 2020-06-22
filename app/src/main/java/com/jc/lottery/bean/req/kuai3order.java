package com.jc.lottery.bean.req;

import com.jc.lottery.bean.TicketListBean;
import com.jc.lottery.util.SignUtil;
import com.jc.lottery.util.TimeUtils;

import java.util.List;

/**
 * @ Create_time: 2018/9/17 on 17:20.
 * @ description：快3下单  0917给的新接口文档
 * @ author: vchao
 */
public class kuai3order {

    /**
     * interfaceCode : createOrder
     * requestTime : 1529564400
     * accountName : wg
     * data : {"orderInfo":{"gameAlias":"k3","ticketList":[{"ticket":"10","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"12","eachTotalMoney":"200","eachBetMode":"01"}],"terminal":"qkl","thirdUserId":29,"thirdPartyCode":"11","multiDraw":3,"betDouble":1,"noteNumber":1,"totalMoney":400,"betMode":"01","drawNumber":"180821015","gamePlayNum":"11","dataSource":"0","additional":""}}
     * sign : 6D9B00C2F6CD4EB0921527432A632031
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;
    private String sign;

    public kuai3order(String accountName, DataBean.OrderInfoBean orderInfoBean) {
        this.interfaceCode = "createOrder";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        DataBean dataBean = new DataBean(orderInfoBean);
        this.data = dataBean;
        this.sign = SignUtil.sign(SignUtil.getData(orderInfoBean));
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class DataBean {
        /**
         * orderInfo : {"gameAlias":"k3","ticketList":[{"ticket":"10","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"12","eachTotalMoney":"200","eachBetMode":"01"}],"terminal":"qkl","thirdUserId":29,"thirdPartyCode":"11","multiDraw":3,"betDouble":1,"noteNumber":1,"totalMoney":400,"betMode":"01","drawNumber":"180821015","gamePlayNum":"11","dataSource":"0","additional":""}
         */

        private OrderInfoBean orderInfo;

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
             * gameAlias : k3
             * ticketList : [{"ticket":"10","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"12","eachTotalMoney":"200","eachBetMode":"01"}]
             * terminal : qkl
             * thirdUserId : 29
             * thirdPartyCode : 11
             * multiDraw : 3
             * betDouble : 1
             * noteNumber : 1
             * totalMoney : 400
             * betMode : 01
             * drawNumber : 180821015
             * gamePlayNum : 11
             * dataSource : 0
             * additional :
             */

            private String gameAlias;
            private String terminal;
            private int thirdUserId;
            private String thirdPartyCode;
            private int multiDraw;
            private int betDouble;
            private int noteNumber;
            private int totalMoney;
            private String betMode;
            private String drawNumber;
            private String gamePlayNum;
            private String dataSource;
            private String additional;
            private List<TicketListBean> ticketList;

            public OrderInfoBean() {
            }

            public OrderInfoBean(String gameAlias, String terminal, int thirdUserId, String thirdPartyCode, int multiDraw,
                                 int betDouble, int noteNumber, int totalMoney, String betMode, String drawNumber,
                                 String gamePlayNum, String dataSource, String additional, List<TicketListBean> ticketList) {
                this.gameAlias = gameAlias;
                this.terminal = terminal;
                this.thirdUserId = thirdUserId;
                this.thirdPartyCode = thirdPartyCode;
                this.multiDraw = multiDraw;
                this.betDouble = betDouble;
                this.noteNumber = noteNumber;
                this.totalMoney = totalMoney;
                this.betMode = betMode;
                this.drawNumber = drawNumber;
                this.gamePlayNum = gamePlayNum;
                this.dataSource = dataSource;
                this.additional = additional;
                this.ticketList = ticketList;
            }

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }

            public String getTerminal() {
                return terminal;
            }

            public void setTerminal(String terminal) {
                this.terminal = terminal;
            }

            public int getThirdUserId() {
                return thirdUserId;
            }

            public void setThirdUserId(int thirdUserId) {
                this.thirdUserId = thirdUserId;
            }

            public String getThirdPartyCode() {
                return thirdPartyCode;
            }

            public void setThirdPartyCode(String thirdPartyCode) {
                this.thirdPartyCode = thirdPartyCode;
            }

            public int getMultiDraw() {
                return multiDraw;
            }

            public void setMultiDraw(int multiDraw) {
                this.multiDraw = multiDraw;
            }

            public int getBetDouble() {
                return betDouble;
            }

            public void setBetDouble(int betDouble) {
                this.betDouble = betDouble;
            }

            public int getNoteNumber() {
                return noteNumber;
            }

            public void setNoteNumber(int noteNumber) {
                this.noteNumber = noteNumber;
            }

            public int getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(int totalMoney) {
                this.totalMoney = totalMoney;
            }

            public String getBetMode() {
                return betMode;
            }

            public void setBetMode(String betMode) {
                this.betMode = betMode;
            }

            public String getDrawNumber() {
                return drawNumber;
            }

            public void setDrawNumber(String drawNumber) {
                this.drawNumber = drawNumber;
            }

            public String getGamePlayNum() {
                return gamePlayNum;
            }

            public void setGamePlayNum(String gamePlayNum) {
                this.gamePlayNum = gamePlayNum;
            }

            public String getDataSource() {
                return dataSource;
            }

            public void setDataSource(String dataSource) {
                this.dataSource = dataSource;
            }

            public String getAdditional() {
                return additional;
            }

            public void setAdditional(String additional) {
                this.additional = additional;
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
