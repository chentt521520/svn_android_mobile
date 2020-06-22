package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class HistoryBettingQueryBean {
    /**
     * interfaceCode : winQuery
     * requestTime : 1455606858
     * accountName : ceshi
     * password : ceshi
     * channel : ceshi
     * data : {"rechargeCardInfo":{"safetyCode": "13CE59C2-2CDC19A1-EA5EFD4D-22303756"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public HistoryBettingQueryBean(String accountName, DataBean data) {
        this.interfaceCode = "historyBettingQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = data;
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

    public static class DataBean {
        /**
         * rechargeCardInfo : {"gameAlias": "jkc"}
         */
        private BettingInfo bettingInfo;

        public DataBean(BettingInfo bettingInfo) {
            this.bettingInfo = bettingInfo;
        }

        public BettingInfo getBettingInfo() {
            return bettingInfo;
        }

        public void setBettingInfo(BettingInfo bettingInfo) {
            this.bettingInfo = bettingInfo;
        }
    }

    public static class BettingInfo{
        private String gameAlias;
        private String type;
        private String pageNo;

        public BettingInfo(String gameAlias, String type, String pageNo) {
            this.gameAlias = gameAlias;
            this.type = type;
            this.pageNo = pageNo;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }
    }

}
