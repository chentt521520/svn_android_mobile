package com.jc.lottery.bean.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JCZQSelect implements LotteryType ,Serializable{

	/**
	 * 
	 */

	private String awary_team;
	private String home_team;
	private String concede;
	private String bout_index;
	List<String> list = new ArrayList<String>();
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
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}

}
