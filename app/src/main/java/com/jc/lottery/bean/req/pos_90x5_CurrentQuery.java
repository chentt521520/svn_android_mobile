package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class pos_90x5_CurrentQuery {

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public pos_90x5_CurrentQuery(String accountName, DataBean data) {
        this.interfaceCode = "currentQuery";
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
        private ColorPeriodInfo colorPeriodInfo;

        public DataBean(ColorPeriodInfo colorPeriodInfo) {
            this.colorPeriodInfo = colorPeriodInfo;
        }

        public ColorPeriodInfo getColorPeriodInfo() {
            return colorPeriodInfo;
        }

        public void setColorPeriodInfo(ColorPeriodInfo colorPeriodInfo) {
            this.colorPeriodInfo = colorPeriodInfo;
        }
    }

    public static class ColorPeriodInfo{
        private String gameAlias;

        public ColorPeriodInfo(String gameAlias) {
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
