package com.jc.lottery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qq on 2020/5/21.
 */

public class VictoryScannerBean implements Serializable {

    private String code;
    private String message;
    private String state;
    private List<CashPrizeList> cashPrizeList = new ArrayList<>();

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

    public List<CashPrizeList> getCashPrizeList() {
        return cashPrizeList;
    }

    public void setCashPrizeList(List<CashPrizeList> cashPrizeList) {
        this.cashPrizeList = cashPrizeList;
    }

    public class CashPrizeList implements Serializable{

        private String issue_no;
        private String lottery_name;
        private String order_code;
        private String amount;
        private String bet_multiple;
        private String bet_num;
        private String bet_status;
        private String data_source;
        private String order_time;
        private String pay_method;
        private String pay_status;
        private String pay_time;
        private String user_name;
        private String cash_status;
        private String cash_user_name;
        private String cash_time;
        private String win_money;
        private String win_status;
        private String game_no;
        private String game_name;
        private String host_name;
        private String away_name;
        private String host_score;
        private String away_score;
        private String play_type;
        private String result;
        private String bet_content;
        private String win_status_bet;

        public String getIssue_no() {
            return issue_no;
        }

        public void setIssue_no(String issue_no) {
            this.issue_no = issue_no;
        }

        public String getLottery_name() {
            return lottery_name;
        }

        public void setLottery_name(String lottery_name) {
            this.lottery_name = lottery_name;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBet_multiple() {
            return bet_multiple;
        }

        public void setBet_multiple(String bet_multiple) {
            this.bet_multiple = bet_multiple;
        }

        public String getBet_num() {
            return bet_num;
        }

        public void setBet_num(String bet_num) {
            this.bet_num = bet_num;
        }

        public String getBet_status() {
            return bet_status;
        }

        public void setBet_status(String bet_status) {
            this.bet_status = bet_status;
        }

        public String getData_source() {
            return data_source;
        }

        public void setData_source(String data_source) {
            this.data_source = data_source;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getPay_method() {
            return pay_method;
        }

        public void setPay_method(String pay_method) {
            this.pay_method = pay_method;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCash_status() {
            return cash_status;
        }

        public void setCash_status(String cash_status) {
            this.cash_status = cash_status;
        }

        public String getCash_user_name() {
            return cash_user_name;
        }

        public void setCash_user_name(String cash_user_name) {
            this.cash_user_name = cash_user_name;
        }

        public String getCash_time() {
            return cash_time;
        }

        public void setCash_time(String cash_time) {
            this.cash_time = cash_time;
        }

        public String getWin_money() {
            return win_money;
        }

        public void setWin_money(String win_money) {
            this.win_money = win_money;
        }

        public String getWin_status() {
            return win_status;
        }

        public void setWin_status(String win_status) {
            this.win_status = win_status;
        }

        public String getGame_no() {
            return game_no;
        }

        public void setGame_no(String game_no) {
            this.game_no = game_no;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getHost_name() {
            return host_name;
        }

        public void setHost_name(String host_name) {
            this.host_name = host_name;
        }

        public String getAway_name() {
            return away_name;
        }

        public void setAway_name(String away_name) {
            this.away_name = away_name;
        }

        public String getHost_score() {
            return host_score;
        }

        public void setHost_score(String host_score) {
            this.host_score = host_score;
        }

        public String getAway_score() {
            return away_score;
        }

        public void setAway_score(String away_score) {
            this.away_score = away_score;
        }

        public String getPlay_type() {
            return play_type;
        }

        public void setPlay_type(String play_type) {
            this.play_type = play_type;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getBet_content() {
            return bet_content;
        }

        public void setBet_content(String bet_content) {
            this.bet_content = bet_content;
        }

        public String getWin_status_bet() {
            return win_status_bet;
        }

        public void setWin_status_bet(String win_status_bet) {
            this.win_status_bet = win_status_bet;
        }
    }
}
