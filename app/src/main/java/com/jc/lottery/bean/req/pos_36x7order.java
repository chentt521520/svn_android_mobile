package com.jc.lottery.bean.req;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jc.lottery.bean.TicketListBean;
import com.jc.lottery.util.LogUtils;
import com.jc.lottery.util.SignUtil;
import com.jc.lottery.util.TimeUtils;

import java.util.List;


/**
 * @ Create_time: 2018/9/21 on 10:37.
 * @ description：排列5 
 * @ author: vchao
 */
public class pos_36x7order {

    /**
     * interfaceCode : createOrder
     * requestTime : 1536840060
     * accountName : ceshi
     * data : {"orderInfo":{"gameId":"196","gameAlias":"pl5","ticketList":[{"ticket":"7 1 3 7 4","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"2 9 8 2 7","eachTotalMoney":"200","eachBetMode":"01"}],"terminalId":"2","terminal":"111","multiDraw":"3","betDouble":"1","noteNumber":"1","totalMoney":"400","betMode":"01","dataSource":"3","drawId":"7514","drawNumber":"2018250"}}
     * sign : C749E8643E63B6D53D49F35A98C7DDF7
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;
    private String sign;

    public pos_36x7order(String accountName, pos_36x7order.DataBean.OrderInfoBean orderInfoBean) {
        this.interfaceCode = "createOrder";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        pos_36x7order.DataBean dataBean = new pos_36x7order.DataBean(orderInfoBean);
        this.data = dataBean;

        String s = new Gson().toJson(dataBean);
        LogUtils.e("对象转json  == " + s);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) JSONObject.parseObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.sign = SignUtil.sign(jsonObject);
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
         * orderInfo : {"gameId":"196","gameAlias":"pl5","ticketList":[{"ticket":"7 1 3 7 4","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"2 9 8 2 7","eachTotalMoney":"200","eachBetMode":"01"}],"terminalId":"2","terminal":"111","multiDraw":"3","betDouble":"1","noteNumber":"1","totalMoney":"400","betMode":"01","dataSource":"3","drawId":"7514","drawNumber":"2018250"}
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
             * gameId : 196
             * gameAlias : pl5
             * ticketList : [{"ticket":"7 1 3 7 4","eachTotalMoney":"200","eachBetMode":"01"},{"ticket":"2 9 8 2 7","eachTotalMoney":"200","eachBetMode":"01"}]
             * terminalId : 2
             * terminal : 111
             * multiDraw : 3
             * betDouble : 1
             * noteNumber : 1
             * totalMoney : 400
             * betMode : 01
             * dataSource : 3
             * drawId : 7514
             * drawNumber : 2018250
             */

//            private String gameId;
            private String gameAlias;
//            private String terminalId;
            private String terminal;
            private String multiDraw;
            private String betDouble;
            private String noteNumber;
            private String totalMoney;
            private String betMode;
            private String dataSource;
//            private String drawId;
            private String drawNumber;
            private String gamePlayNum;
            private List<TicketListBean> ticketList;

            public OrderInfoBean() {
            }

            public OrderInfoBean(String gameAlias, String terminal, String multiDraw, String betDouble, String noteNumber, String totalMoney, String betMode, String dataSource, String drawNumber, String gamePlayNum, List<TicketListBean> ticketList) {
                this.gameAlias = gameAlias;
                this.terminal = terminal;
                this.multiDraw = multiDraw;
                this.betDouble = betDouble;
                this.noteNumber = noteNumber;
                this.totalMoney = totalMoney;
                this.betMode = betMode;
                this.dataSource = dataSource;
                this.drawNumber = drawNumber;
                this.gamePlayNum = gamePlayNum;
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

            public String getDataSource() {
                return dataSource;
            }

            public void setDataSource(String dataSource) {
                this.dataSource = dataSource;
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

            public List<TicketListBean> getTicketList() {
                return ticketList;
            }

            public void setTicketList(List<TicketListBean> ticketList) {
                this.ticketList = ticketList;
            }
        }
    }
}
