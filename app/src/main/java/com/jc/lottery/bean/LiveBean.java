package com.jc.lottery.bean;

import java.io.Serializable;

/**
 * @author lr
 * @description:
 * @date:${DATA} 15:54
 */

public class LiveBean implements Serializable{

    private String gameAlias;
    private String gameName;
    private String gameId;
    private String drawId;
    private String drawNumber;
    private String electronicStatus;
    private String prizeTime;
    private String electronicPrizeNum;
    private String liveStatus;

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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getDrawId() {
        return drawId;
    }

    public void setDrawId(String drawId) {
        this.drawId = drawId;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getElectronicStatus() {
        return electronicStatus;
    }

    public void setElectronicStatus(String electronicStatus) {
        this.electronicStatus = electronicStatus;
    }

    public String getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(String prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getElectronicPrizeNum() {
        return electronicPrizeNum;
    }

    public void setElectronicPrizeNum(String electronicPrizeNum) {
        this.electronicPrizeNum = electronicPrizeNum;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }
}
