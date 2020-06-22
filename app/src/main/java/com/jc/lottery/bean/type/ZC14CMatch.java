package com.jc.lottery.bean.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZC14CMatch implements LotteryType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String home_team;
	private String awary_team;
	private String bout_index;
	private String match_time;
	private String match_name;
	private List<String> list = new ArrayList<String>();
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
	public String getMatch_time() {
		return match_time;
	}
	public void setMatch_time(String match_time) {
		this.match_time = match_time;
	}
	public String getMatch_name() {
		return match_name;
	}
	public void setMatch_name(String match_name) {
		this.match_name = match_name;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	
	
	
}
