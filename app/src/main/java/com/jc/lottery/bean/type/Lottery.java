package com.jc.lottery.bean.type;

import java.io.Serializable;

public class Lottery implements LotteryType, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String type;
    private String name;
    private String termNo;
    private String date;
    private String action;
    private String code;
    private String prizepool;


    private String desc;
    private String awary_team;
    private String home_team;
    private String concede;
    private String bout_index;
    private String match_time;
    private String stop_sale_time;
    private long time;

    // 快3开奖历史
    private String term;
    private String result;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAwary_team() {
        return awary_team;
    }

    public void setAwary_team(String awary_team) {
        this.awary_team = awary_team;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getConcede() {
        return concede;
    }

    public void setConcede(String concede) {
        this.concede = concede;
    }

    public String getBout_index() {
        return bout_index;
    }

    public void setBout_index(String bout_index) {
        this.bout_index = bout_index;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getStop_sale_time() {
        return stop_sale_time;
    }

    public void setStop_sale_time(String stop_sale_time) {
        this.stop_sale_time = stop_sale_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTermNo() {
        return termNo;
    }

    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrizepool() {
        return prizepool;
    }

    public void setPrizepool(String prizepool) {
        this.prizepool = prizepool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
