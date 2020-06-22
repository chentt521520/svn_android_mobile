package com.jc.lottery.bean;

/**
 * Created by qq on 2020/4/24.
 */

public class DrawListInfo {

    private String gameAlias;
    private String gameName;
    private String drawNumber;
    private Long prizeTime;
    private String url;
    private String prizeNum;
    private String videoStatus;

    public String getGameAlias() {
        return gameAlias;
    }

    public void setGameAlias(String gameAlias) {
        this.gameAlias = gameAlias;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public Long getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(Long prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeNum(String prizeNum) {
        this.prizeNum = prizeNum;
    }
}
