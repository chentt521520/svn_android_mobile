package com.jc.lottery.bean.resp;

import java.util.List;

/**
 * @ Create_time: 2018/10/9 on 14:37.
 * @ description：获取游戏列表
 * @ author: vchao
 */
public class Resp_queryGameList {

    private String code;
    private DataBean data;
    private String message;
    private String state;
    private List<GameListBean> gameList;

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

    public List<GameListBean> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameListBean> gameList) {
        this.gameList = gameList;
    }

    public static class DataBean {
    }

    public static class GameListBean {
        /**
         * gameAlias : pl5
         * gameName : 排列5
         * id : 196
         * poolMoney : 1996801200
         */
        private String alias;
        private String approvalNum;
        private String approvalTime;
        private String gameName;
        private String id;
        private String launch;
        private String onSaleTime;
        private String poolMoney;
        private String ruleSummary;
        private String stopNoticeTime;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getApprovalNum() {
            return approvalNum;
        }

        public void setApprovalNum(String approvalNum) {
            this.approvalNum = approvalNum;
        }

        public String getApprovalTime() {
            return approvalTime;
        }

        public void setApprovalTime(String approvalTime) {
            this.approvalTime = approvalTime;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLaunch() {
            return launch;
        }

        public void setLaunch(String launch) {
            this.launch = launch;
        }

        public String getOnSaleTime() {
            return onSaleTime;
        }

        public void setOnSaleTime(String onSaleTime) {
            this.onSaleTime = onSaleTime;
        }

        public String getPoolMoney() {
            return poolMoney;
        }

        public void setPoolMoney(String poolMoney) {
            this.poolMoney = poolMoney;
        }

        public String getRuleSummary() {
            return ruleSummary;
        }

        public void setRuleSummary(String ruleSummary) {
            this.ruleSummary = ruleSummary;
        }

        public String getStopNoticeTime() {
            return stopNoticeTime;
        }

        public void setStopNoticeTime(String stopNoticeTime) {
            this.stopNoticeTime = stopNoticeTime;
        }
    }
}
