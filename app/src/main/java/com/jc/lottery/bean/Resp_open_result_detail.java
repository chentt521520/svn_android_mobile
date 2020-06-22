package com.jc.lottery.bean;

import java.util.List;

/**
 * @ Create_time: 2019/1/8 on 16:13.
 * @ description：开奖结果详情
 * @ author: vchao
 */
public class Resp_open_result_detail {

    /**
     * code : 00000
     * data : {"colorPeriodInfo":{"draw":"181227011","gameAlias":"k3"}}
     * drawDetails : {"draw":"181227011","gameName":"快3","income":"0","levelList":[{"betNum":0,"winLevel":16,"winMoney":"0"},{"betNum":0,"winLevel":17,"winMoney":"0"},{"betNum":0,"winLevel":18,"winMoney":"0"},{"betNum":0,"winLevel":11,"winMoney":"0"},{"betNum":0,"winLevel":12,"winMoney":"0"},{"betNum":0,"winLevel":13,"winMoney":"0"},{"betNum":0,"winLevel":14,"winMoney":"0"},{"betNum":0,"winLevel":15,"winMoney":"0"}],"poolMoney":"989007666","prizeNum":"1 3 5","prizeTime":1545877200000}
     * message : 奖期详情查询成功!
     * state : 00
     */

    private String code;
    private DataBean data;
    private DrawDetailsBean drawDetails;
    private String message;
    private String state;

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

    public DrawDetailsBean getDrawDetails() {
        return drawDetails;
    }

    public void setDrawDetails(DrawDetailsBean drawDetails) {
        this.drawDetails = drawDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * colorPeriodInfo : {"draw":"181227011","gameAlias":"k3"}
         */

        private ColorPeriodInfoBean colorPeriodInfo;

        public ColorPeriodInfoBean getColorPeriodInfo() {
            return colorPeriodInfo;
        }

        public void setColorPeriodInfo(ColorPeriodInfoBean colorPeriodInfo) {
            this.colorPeriodInfo = colorPeriodInfo;
        }

        public static class ColorPeriodInfoBean {
            /**
             * draw : 181227011
             * gameAlias : k3
             */

            private String draw;
            private String gameAlias;

            public String getDraw() {
                return draw;
            }

            public void setDraw(String draw) {
                this.draw = draw;
            }

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }
        }
    }

    public static class DrawDetailsBean {
        /**
         * draw : 181227011
         * gameName : 快3
         * income : 0
         * levelList : [{"betNum":0,"winLevel":16,"winMoney":"0"},{"betNum":0,"winLevel":17,"winMoney":"0"},{"betNum":0,"winLevel":18,"winMoney":"0"},{"betNum":0,"winLevel":11,"winMoney":"0"},{"betNum":0,"winLevel":12,"winMoney":"0"},{"betNum":0,"winLevel":13,"winMoney":"0"},{"betNum":0,"winLevel":14,"winMoney":"0"},{"betNum":0,"winLevel":15,"winMoney":"0"}]
         * poolMoney : 989007666
         * prizeNum : 1 3 5
         * prizeTime : 1545877200000
         */

        private String draw;
        private String gameName;
        private String income;
        private String poolMoney;
        private String secondPool;
        private String prizeNum;
        private long prizeTime;
        private List<LevelListBean> levelList;

        public String getDraw() {
            return draw;
        }

        public void setDraw(String draw) {
            this.draw = draw;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getPoolMoney() {
            return poolMoney;
        }

        public void setPoolMoney(String poolMoney) {
            this.poolMoney = poolMoney;
        }

        public String getSecondPool() {
            return secondPool;
        }

        public void setSecondPool(String secondPool) {
            this.secondPool = secondPool;
        }

        public String getPrizeNum() {
            return prizeNum;
        }

        public void setPrizeNum(String prizeNum) {
            this.prizeNum = prizeNum;
        }

        public long getPrizeTime() {
            return prizeTime;
        }

        public void setPrizeTime(long prizeTime) {
            this.prizeTime = prizeTime;
        }

        public List<LevelListBean> getLevelList() {
            return levelList;
        }

        public void setLevelList(List<LevelListBean> levelList) {
            this.levelList = levelList;
        }

        public static class LevelListBean {
            /**
             * betNum : 0
             * winLevel : 16
             * winMoney : 0
             */

            private int betNum;
            private String winLevel;
            private String winMoney;

            public int getBetNum() {
                return betNum;
            }

            public void setBetNum(int betNum) {
                this.betNum = betNum;
            }

            public String getWinLevel() {
                return winLevel;
            }

            public void setWinLevel(String winLevel) {
                this.winLevel = winLevel;
            }

            public String getWinMoney() {
                return winMoney;
            }

            public void setWinMoney(String winMoney) {
                this.winMoney = winMoney;
            }
        }
    }
}
