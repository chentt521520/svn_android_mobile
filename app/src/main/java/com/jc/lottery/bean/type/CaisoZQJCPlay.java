package com.jc.lottery.bean.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaisoZQJCPlay implements LotteryType, Serializable{
/*
	{
        "home_team": "\u8272\u5f53",
        "awary_team": "\u963f\u5c14\u52d2",
        "bout_index": "121019007",
        "concede": 0,
        "sop": "2.12",
        "fop": "3.45",
        "pop": "2.99",
        "stzb": "43",
        "ftzb": "26",
        "ptzb": "31",
        "match_time": "2012-10-20 00:45:00",
        "match_name": "\u6cd5\u56fd\u4e59\u7ea7\u8054\u8d5b",
        "sale_date": "2012-10-19 11:38:21",
        "stop_sale_time": "2012-10-19 22:40:21",
        "status": "1",
        "current_ratios": "4.36,2.60,8.16;2.00,2.90,3.60"
    }
*/

	private static final long serialVersionUID = 1L;
	private String home_team;
	private String awary_team;
	private String bout_index;
	private String concede;
	private String sop;
	private String fop;
	private String pop;
	private String stzb;
	private String ftzb;
	private String ptzb;
	private String match_time;
	private String match_name;
	private String sale_date;
	private String stop_sale_time;
	private String status;
	private String[] current_ratios;
	private List<String> list = new ArrayList<String>();
	
	private String stop_date;
	private String stop_Time;
	
	
	public String getStop_date() {
		return stop_date;
	}
	public void setStop_date(String stop_date) {
		this.stop_date = stop_date;
	}
	public String getStop_Time() {
		return stop_Time;
	}
	public void setStop_Time(String stop_Time) {
		this.stop_Time = stop_Time;
	}
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
	public String getSop() {
		return sop;
	}
	public void setSop(String sop) {
		this.sop = sop;
	}
	public String getFop() {
		return fop;
	}
	public void setFop(String fop) {
		this.fop = fop;
	}
	public String getPop() {
		return pop;
	}
	public void setPop(String pop) {
		this.pop = pop;
	}
	public String getStzb() {
		return stzb;
	}
	public void setStzb(String stzb) {
		this.stzb = stzb;
	}
	public String getFtzb() {
		return ftzb;
	}
	public void setFtzb(String ftzb) {
		this.ftzb = ftzb;
	}
	public String getPtzb() {
		return ptzb;
	}
	public void setPtzb(String ptzb) {
		this.ptzb = ptzb;
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
	public String getSale_date() {
		return sale_date;
	}
	public void setSale_date(String sale_date) {
		this.sale_date = sale_date;
	}
	public String getStop_sale_time() {
		return stop_sale_time;
	}
	public void setStop_sale_time(String stop_sale_time) {
		this.stop_sale_time = stop_sale_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getCurrent_ratios() {
		return current_ratios;
	}
	public void setCurrent_ratios(String[] current_ratios) {
		this.current_ratios = current_ratios;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	
	
}
