package com.jc.lottery.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qq on 2020/5/6.
 */

public class VictoryBetsList {

    private String bet_content;
    private String bet_money;
    private String play_type;
    private String win_status;
    private String game_name;
    private String host_name;
    private String away_name;
    private String result;
    private List<InfoBean> infoBeans = new ArrayList<>();

    public String getBet_content() {
        return bet_content;
    }

    public void setBet_content(String bet_content) {
        this.bet_content = bet_content;
    }

    public String getBet_money() {
        return bet_money;
    }

    public void setBet_money(String bet_money) {
        this.bet_money = bet_money;
    }

    public String getPlay_type() {
        return play_type;
    }

    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public String getWin_status() {
        return win_status;
    }

    public void setWin_status(String win_status) {
        this.win_status = win_status;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<InfoBean> getInfoBeans() {
        return infoBeans;
    }

    public void setInfoBeans(List<InfoBean> infoBeans) {
        this.infoBeans = infoBeans;
    }

    public static class InfoBean{
        private boolean type;
        private String name;

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
