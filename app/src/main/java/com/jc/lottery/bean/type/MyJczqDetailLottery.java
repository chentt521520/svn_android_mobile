package com.jc.lottery.bean.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyJczqDetailLottery implements LotteryType, Serializable{
	/**
	 * 竞彩详情内容
	 */
	private static final long serialVersionUID = 1L;
	private String awaryTeam; //客队名称
	private String homeTeam; //主队
	private String issue;   //显示星期时间
	private String match_time;  //截止时间
	private String caiguo;  //赛果
	private List<String> list = new ArrayList<>();
	public String getAwaryTeam() {
		return awaryTeam;
	}
	public void setAwaryTeam(String awaryTeam) {
		this.awaryTeam = awaryTeam;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getMatch_time() {
		return match_time;
	}
	public void setMatch_time(String match_time) {
		this.match_time = match_time;
	}
	public String getCaiguo() {
		return caiguo;
	}
	public void setCaiguo(String caiguo) {
		this.caiguo = caiguo;
	}
 
	public List<String> getTouzhuList(){
		return list;
	}
}
