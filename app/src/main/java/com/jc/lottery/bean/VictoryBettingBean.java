package com.jc.lottery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 15:24
 */

public class VictoryBettingBean implements Serializable{

    private String code;
    private String message;
    private String pageCount;
    private String pageNum;
    private String state;
    private String totalNum;
    private List<BettingList> bettingList = new ArrayList<BettingList>();

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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<BettingList> getBettingList() {
        return bettingList;
    }

    public void setBettingList(List<BettingList> bettingList) {
        this.bettingList = bettingList;
    }

    public class BettingList{
        private String amount;
        private String bet_multiple;
        private String bet_num;
        private String bet_status;
        private String cash_status;
        private Long cash_time;
        private String cash_user_name;
        private String issue_no;
        private String lottery_name;
        private String order_code;
        private Long order_time;
        private String pay_method;
        private String pay_status;
        private String win_status;
        private String win_money;
        private String prize;

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

        public String getCash_status() {
            return cash_status;
        }

        public void setCash_status(String cash_status) {
            this.cash_status = cash_status;
        }

        public Long getCash_time() {
            return cash_time;
        }

        public void setCash_time(Long cash_time) {
            this.cash_time = cash_time;
        }

        public String getCash_user_name() {
            return cash_user_name;
        }

        public void setCash_user_name(String cash_user_name) {
            this.cash_user_name = cash_user_name;
        }

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

        public Long getOrder_time() {
            return order_time;
        }

        public void setOrder_time(Long order_time) {
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

        public String getWin_status() {
            return win_status;
        }

        public void setWin_status(String win_status) {
            this.win_status = win_status;
        }

        public String getWin_money() {
            return win_money;
        }

        public void setWin_money(String win_money) {
            this.win_money = win_money;
        }

        public String getPrize() {
            return prize;
        }

        public void setPrize(String prize) {
            this.prize = prize;
        }
    }
}
