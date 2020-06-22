package com.jc.lottery.bean.type;

import java.io.Serializable;

public class MatchInfo implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String home_team;
	private String awary_team;
	private String bout_index;
	private String concede;
	private String whole_score;
	private String lotterycode;
	private String lotteryresult;
	private String sp;
	public String getHome_team() {
		return home_team;
	}
	public void setHome_team(String home_team) {
		this.home_team = home_team;
	}
	public String getAwary_team() {
		return awary_team;
	}
	public void setAwary_team(String awary_team) {
		this.awary_team = awary_team;
	}
	public String getBout_index() {
		return bout_index;
	}
	public void setBout_index(String bout_index) {
		this.bout_index = bout_index;
	}
	public String getConcede() {
		return concede;
	}
	public void setConcede(String concede) {
		this.concede = concede;
	}
	public String getWhole_score() {
		return whole_score;
	}
	public void setWhole_score(String whole_score) {
		this.whole_score = whole_score;
	}
	public String getLotterycode() {
		return lotterycode;
	}
	public void setLotterycode(String lotterycode) {
		this.lotterycode = lotterycode;
	}
	public String getLotteryresult() {
		return lotteryresult;
	}
	public void setLotteryresult(String lotteryresult) {
		this.lotteryresult = lotteryresult;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	
	
}
