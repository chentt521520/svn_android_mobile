package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Create_time: 2018/10/11 on 17:35.
 * @ description：规则查询 请求
 * @ author: vchao
 */
public class pos_victoryBetting {

    /**
     * interfaceCode : findRule
     * requestTime : 1534917840
     * accountName : ceshi
     * data : {"ruleInfo":{"gameAlias":"k3"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;
    private DataBean data;

    public pos_victoryBetting(String accountName, String password,OrderInfo orderInfo) {
        this.interfaceCode = "betting";
        this.channel = "3";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.data = new DataBean(orderInfo);
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
         * ruleInfo : {"gameAlias":"k3"}
         */
        private OrderInfo orderInfo;

        public DataBean(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }
    }

    public static class OrderInfo{
        private String issue_no;
        private String amount;
        private String bet_num;
        private String bet_multiple;
        private String device_num;
        private String data_source;
        private List<GameList> gameList = new ArrayList<GameList>();
        public OrderInfo(){

        }
        public OrderInfo(String issue_no, String amount, String bet_num, String bet_multiple, String device_num, String data_source, List<GameList> gameList) {
            this.issue_no = issue_no;
            this.amount = amount;
            this.bet_num = bet_num;
            this.bet_multiple = bet_multiple;
            this.device_num = device_num;
            this.data_source = data_source;
            this.gameList = gameList;
        }

        public String getIssue_no() {
            return issue_no;
        }

        public void setIssue_no(String issue_no) {
            this.issue_no = issue_no;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBet_num() {
            return bet_num;
        }

        public void setBet_num(String bet_num) {
            this.bet_num = bet_num;
        }

        public String getBet_multiple() {
            return bet_multiple;
        }

        public void setBet_multiple(String bet_multiple) {
            this.bet_multiple = bet_multiple;
        }

        public String getDevice_num() {
            return device_num;
        }

        public void setDevice_num(String device_num) {
            this.device_num = device_num;
        }

        public String getData_source() {
            return data_source;
        }

        public void setData_source(String data_source) {
            this.data_source = data_source;
        }

        public List<GameList> getGameList() {
            return gameList;
        }

        public void setGameList(List<GameList> gameList) {
            this.gameList = gameList;
        }
    }

    public static class GameList{
        private String game_key_id;
        private String game_no;
        private String play_type;
        private String bet_content;
        public GameList(){

        }
        public GameList(String game_key_id, String game_no, String play_type, String bet_content) {
            this.game_key_id = game_key_id;
            this.game_no = game_no;
            this.play_type = play_type;
            this.bet_content = bet_content;
        }

        public String getGame_key_id() {
            return game_key_id;
        }

        public void setGame_key_id(String game_key_id) {
            this.game_key_id = game_key_id;
        }

        public String getGame_no() {
            return game_no;
        }

        public void setGame_no(String game_no) {
            this.game_no = game_no;
        }

        public String getPlay_type() {
            return play_type;
        }

        public void setPlay_type(String play_type) {
            this.play_type = play_type;
        }

        public String getBet_content() {
            return bet_content;
        }

        public void setBet_content(String bet_content) {
            this.bet_content = bet_content;
        }
    }
}
