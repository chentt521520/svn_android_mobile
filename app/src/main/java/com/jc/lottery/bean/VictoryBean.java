package com.jc.lottery.bean;

import com.jc.lottery.bean.type.MatchInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 15:24
 */

public class VictoryBean implements Serializable{

    private String code;
    private String message;
    private String state;
    private IssueInfo issueInfo;
    private List<MatchList> matchList = new ArrayList<MatchList>();

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

    public IssueInfo getIssueInfo() {
        return issueInfo;
    }

    public void setIssueInfo(IssueInfo issueInfo) {
        this.issueInfo = issueInfo;
    }

    public List<MatchList> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<MatchList> matchList) {
        this.matchList = matchList;
    }

    public class IssueInfo implements Serializable{
        private String id;
        private String issue_end_time;
        private String issue_no;
        private String lottery_name;
        private String pool_amount;
        private String unit_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIssue_end_time() {
            return issue_end_time;
        }

        public void setIssue_end_time(String issue_end_time) {
            this.issue_end_time = issue_end_time;
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

        public String getPool_amount() {
            return pool_amount;
        }

        public void setPool_amount(String pool_amount) {
            this.pool_amount = pool_amount;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }
    }

    public class MatchList implements Serializable{
        private String away_name;
        private String game_name;
        private String game_no;
        private String game_time;
        private String host_name;
        private String issue_no;
        private String play_type;
        private String id;
        private String type = "0";
        private List<Boolean> typeSelect = new ArrayList<>();

        public String getAway_name() {
            return away_name;
        }

        public void setAway_name(String away_name) {
            this.away_name = away_name;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getGame_no() {
            return game_no;
        }

        public void setGame_no(String game_no) {
            this.game_no = game_no;
        }

        public String getGame_time() {
            return game_time;
        }

        public void setGame_time(String game_time) {
            this.game_time = game_time;
        }

        public String getHost_name() {
            return host_name;
        }

        public void setHost_name(String host_name) {
            this.host_name = host_name;
        }

        public String getIssue_no() {
            return issue_no;
        }

        public void setIssue_no(String issue_no) {
            this.issue_no = issue_no;
        }

        public String getPlay_type() {
            return play_type;
        }

        public void setPlay_type(String play_type) {
            this.play_type = play_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Boolean> getTypeSelect() {
            return typeSelect;
        }

        public void setTypeSelect(List<Boolean> typeSelect) {
            this.typeSelect = typeSelect;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
