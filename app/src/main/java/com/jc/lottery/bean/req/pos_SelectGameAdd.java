package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：玩法查询
 * @ author: lr
 */
public class pos_SelectGameAdd {

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public pos_SelectGameAdd(String accountName, DataBean data) {
        this.interfaceCode = "selectGameAdd";
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
        private GameInfo gameInfo;

        public DataBean(GameInfo gameInfo) {
            this.gameInfo = gameInfo;
        }

        public GameInfo getGameInfo() {
            return gameInfo;
        }

        public void setGameInfo(GameInfo gameInfo) {
            this.gameInfo = gameInfo;
        }
    }

    public static class GameInfo{
        private String gameAlias;

        public GameInfo(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }
    }

}
