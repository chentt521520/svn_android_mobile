package com.jc.lottery.bean.resp;

import java.util.List;

/**
 * @ Create_time: 2018/9/14 on 16:25.
 * @ description：3.7.1. 奖期查询（终端）
 * @ author: vchao
 */
public class Resp_3_7_1_drawNotOpenQuery {

    /**
     * code : 00000
     * drawList : [{"drawId":9418,"drawNumber":"180914045","endTime":1536913200000,"gameAlias":"k3","gameId":168,"gameName":"快3","status":"00"}]
     * message : 奖期查询成功!
     * state : 00
     */

    private String code;
    private String message;
    private String state;
    private List<DrawListBean> drawList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public static class DrawListBean {
        /**
         * drawId : 9418
         * drawNumber : 180914045
         * endTime : 1536913200000
         * gameAlias : k3
         * gameId : 168
         * gameName : 快3
         * status : 00
         */

        private int drawId;
        private String drawNumber;
        private long endTime;
        private long startTime;
        private String gameAlias;
        private int gameId;
        private String gameName;
        private String status;

        public int getDrawId() {
            return drawId;
        }

        public void setDrawId(int drawId) {
            this.drawId = drawId;
        }

        public String getDrawNumber() {
            return drawNumber;
        }

        public void setDrawNumber(String drawNumber) {
            this.drawNumber = drawNumber;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }
    }
}
