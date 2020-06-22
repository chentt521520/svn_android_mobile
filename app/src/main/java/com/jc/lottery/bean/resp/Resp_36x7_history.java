package com.jc.lottery.bean.resp;

import java.util.List;

/**
 * @ Create_time: 2018/10/8 on 15:58.
 * @ description：36 选7 开奖历史
 * @ author: vchao
 */
public class Resp_36x7_history {

    /**
     * code : 00000
     * data : {"colorPeriodInfo":{"gameId":194}}
     * drawList : [{"draw":"2018112","id":7534,"prizeNum":"04 09 10 11 15 32 35-28"},{"draw":"2018111","id":7533,"prizeNum":"03 10 19 21 25 32 34-09"},{"draw":"2018110","id":7532,"prizeNum":"07 08 22 24 26 29 34-09"},{"draw":"2018109","id":7531,"prizeNum":"18 20 25 27 32 34 35-17"},{"draw":"2018108","id":7530,"prizeNum":"01 02 27 32 33 34 35-36"},{"draw":"2018107","id":7529,"prizeNum":"02 03 10 11 12 25 30-24"},{"draw":"2018106","id":7528,"prizeNum":"04 06 08 11 14 19 25-03"},{"draw":"2018105","id":7527,"prizeNum":"01 04 10 17 24 26 29-21"},{"draw":"2018104","id":7267,"prizeNum":"01 15 20 22 26 32 33-19"},{"draw":"2018103","id":6786,"prizeNum":"11 17 20 24 26 28 34-27"},{"draw":"2018102","id":6140,"prizeNum":"01 10 20 21 23 24 26-25"},{"draw":"2018101","id":5750,"prizeNum":"09 15 20 30 31 33 36-22"},{"draw":"2018100","id":4964,"prizeNum":"01 02 07 09 23 25 31-34"},{"draw":"2018099","id":4274,"prizeNum":"01 13 18 19 23 31 34-04"},{"draw":"2018098","id":3914,"prizeNum":"03 07 10 20 22 27 31-12"},{"draw":"2018097","id":3142,"prizeNum":"01 04 10 17 19 29 31-34"},{"draw":"2018096","id":2616,"prizeNum":"02 15 17 19 20 25 27-05"},{"draw":"2018095","id":1456,"prizeNum":"01 02 03 04 05 06 07-08"},{"draw":"2018094","id":1015,"prizeNum":"02 10 16 23 26 27 36-20"},{"draw":"2018093","id":758,"prizeNum":"03 04 06 12 16 19 25-02"}]
     * message : 历史开奖信息查询 成功!
     * state : 00
     */

    private String code;
    private DataBean data;
    private String message;
    private String state;
    private List<DrawListBean> drawList;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DrawListBean> getDrawList() {
        return drawList;
    }

    public void setDrawList(List<DrawListBean> drawList) {
        this.drawList = drawList;
    }

    public static class DataBean {
        /**
         * colorPeriodInfo : {"gameId":194}
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
             * gameId : 194
             */

            private int gameId;

            public int getGameId() {
                return gameId;
            }

            public void setGameId(int gameId) {
                this.gameId = gameId;
            }
        }
    }

    public static class DrawListBean {
        /**
         * draw : 2018112
         * id : 7534
         * prizeNum : 04 09 10 11 15 32 35-28
         */

        private String draw;
        private int id;
        private String prizeNum = "";
        private String prizeTime;

        public String getDraw() {
            return draw;
        }

        public void setDraw(String draw) {
            this.draw = draw;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrizeNum() {
            return prizeNum;
        }

        public void setPrizeNum(String prizeNum) {
            this.prizeNum = prizeNum;
        }

        public String getPrizeTime() {
            return prizeTime;
        }

        public void setPrizeTime(String prizeTime) {
            this.prizeTime = prizeTime;
        }
    }
}
