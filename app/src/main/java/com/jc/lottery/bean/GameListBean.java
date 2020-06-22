package com.jc.lottery.bean;

/**
 * Created by Administrator on 2019/7/4.
 */

public class GameListBean {

    private String gameName;
    private String gameAlias;
    private String ticketPrice;
    private String enabled;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameAlias() {
        return gameAlias;
    }

    public void setGameAlias(String gameAlias) {
        this.gameAlias = gameAlias;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
