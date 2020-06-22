package com.jc.lottery.bean;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/1/8 on 16:04.
 * @ description：开奖信息详情页面
 * @ author: vchao
 */
public class pos_open_result_detail {

    /**
     * interfaceCode : drawNoticeQuery
     * requestTime : 1525743539
     * accountName : zhengweichao
     * data : {"colorPeriodInfo":{"gameAlias":"k3","draw":"181227011"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public pos_open_result_detail(String accountName, String gameAlias, String draw) {
        this.interfaceCode = "periodDrawQueryList";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        DataBean.ColorPeriodInfoBean colorPeriodInfoBean = new DataBean.ColorPeriodInfoBean(gameAlias, draw);
        this.data = new DataBean(colorPeriodInfoBean);
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
         * colorPeriodInfo : {"gameAlias":"k3","draw":"181227011"}
         */

        private ColorPeriodInfoBean colorPeriodInfo;

        public DataBean(ColorPeriodInfoBean colorPeriodInfo) {
            this.colorPeriodInfo = colorPeriodInfo;
        }

        public ColorPeriodInfoBean getColorPeriodInfo() {
            return colorPeriodInfo;
        }

        public void setColorPeriodInfo(ColorPeriodInfoBean colorPeriodInfo) {
            this.colorPeriodInfo = colorPeriodInfo;
        }

        public static class ColorPeriodInfoBean {
            /**
             * gameAlias : k3
             * draw : 181227011
             */

            private String gameAlias;
            private String draw;

            public ColorPeriodInfoBean(String gameAlias, String draw) {
                this.gameAlias = gameAlias;
                this.draw = draw;
            }

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }

            public String getDraw() {
                return draw;
            }

            public void setDraw(String draw) {
                this.draw = draw;
            }
        }
    }
}
