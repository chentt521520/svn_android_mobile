package com.jc.lottery.bean.type;

import java.io.Serializable;

public class MumericalSelection implements LotteryType, Serializable{
	/**
	 * "type": "\u7ade\u5f69\u8db3\u7403",
        "desc": "\u4e94\u5927\u8054\u8d5b\u706b\u70ed\u8fdb\u884c\u4e2d",
        "name": "jczq",
        "time": 1352193679365,
        "awary_team": "\u6ce2\u5c14\u56fe",
        "home_team": "\u57fa\u8f85\u8fea\u7eb3\u6469",
        "concede": "",
        "bout_index": "121106001",
        "match_time": "2012-11-07 03:45:00",
        "stop_sale_time": "2012-11-06 22:40:01"

	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String termNo;
	private String stopSaleTime;
	private String monetary;
	private String openPrizeTime;
	private String tage;
	
	private String desc;
	private String awary_team;
	private String home_team;
	private String concede;
	private String bout_index;
	private String match_time;
	private String stop_sale_time;
	
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
	public String getStopSaleTime() {
		return stopSaleTime;
	}
	public void setStopSaleTime(String stopSaleTime) {
		this.stopSaleTime = stopSaleTime;
	}
	public String getOpenPrizeTime() {
		return openPrizeTime;
	}
	public void setOpenPrizeTime(String openPrizeTime) {
		this.openPrizeTime = openPrizeTime;
	}
	public String getMonetary() {
		return monetary;
	}
	public void setMonetary(String monetary) {
		this.monetary = monetary;
	}
	public String getTage() {
		return tage;
	}
	public void setTage(String tage) {
		this.tage = tage;
	}
	
	
}
