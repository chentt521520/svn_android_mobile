package com.jc.lottery.bean.type;

import java.io.Serializable;

public class MyLottery implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private String playtype;
	private String code;
	private String win_money;
	private Group<MatchInfo> matchInfos = new Group<MatchInfo>();
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPlaytype() {
		return playtype;
	}
	public void setPlaytype(String playtype) {
		this.playtype = playtype;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public MatchInfo getMatchInfos(int index) {
		return matchInfos.get(index);
	}
	public void setMatchInfos(Group<MatchInfo> matchInfos) {
		this.matchInfos = matchInfos;
	}
	
	 public int getChildSize(){
         return matchInfos.size();
 }
	public String getWin_money() {
		return win_money;
	}
	public void setWin_money(String win_money) {
		this.win_money = win_money;
	}
	
	
}
