package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:57
 */

public class HistoryBetDetailBean {
    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public HistoryBetDetailBean(String accountName, DataBean data) {
        this.interfaceCode = "historyBetDetail";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = data;
    }

    public static class DataBean{
        private BettingInfoBean bettingInfo;

        public DataBean(BettingInfoBean bettingInfo) {
            this.bettingInfo = bettingInfo;
        }

        public BettingInfoBean getBettingInfo() {
            return bettingInfo;
        }

        public void setBettingInfo(BettingInfoBean bettingInfo) {
            this.bettingInfo = bettingInfo;
        }
    }
    public static class BettingInfoBean{
        private String gameAlias;
        private String orderCode;

        public BettingInfoBean(String gameAlias, String orderCode) {
            this.gameAlias = gameAlias;
            this.orderCode = orderCode;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }
    }
}
