package com.jc.lottery.bean.type;

import java.io.Serializable;

public class JCZQHistory implements LotteryType,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bout_index;
	private String match_name;
	private String home_team;
	private String awary_team;
	private String match_time;
	private String sale_date;
	private String whole_score;
	private String half_score;
	private String concede;
	private String sop;
	private String pop;
	private String fop;
	private String sp_sfp;
	private String sp_bf;
	private String sp_jqs;
	private String sp_bcsfp;
	private String match_result;
	
	public String getMatch_result() {
		return match_result;
	}
	public void setMatch_result(String match_result) {
		this.match_result = match_result;
	}
	public String getBout_index() {
		return bout_index;
	}
	public void setBout_index(String bout_index) {
		this.bout_index = bout_index;
	}
	public String getMatch_name() {
		return match_name;
	}
	public void setMatch_name(String match_name) {
		this.match_name = match_name;
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
	public String getMatch_time() {
		return match_time;
	}
	public void setMatch_time(String match_time) {
		this.match_time = match_time;
	}
	public String getSale_date() {
		return sale_date;
	}
	public void setSale_date(String sale_date) {
		this.sale_date = sale_date;
	}
	public String getWhole_score() {
		return whole_score;
	}
	public void setWhole_score(String whole_score) {
		this.whole_score = whole_score;
	}
	public String getHalf_score() {
		return half_score;
	}
	public void setHalf_score(String half_score) {
		this.half_score = half_score;
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
	public String getPop() {
		return pop;
	}
	public void setPop(String pop) {
		this.pop = pop;
	}
	public String getFop() {
		return fop;
	}
	public void setFop(String fop) {
		this.fop = fop;
	}
	public String getSp_sfp() {
		return sp_sfp;
	}
	public void setSp_sfp(String sp_sfp) {
		this.sp_sfp = sp_sfp;
	}
	public String getSp_bf() {
		return sp_bf;
	}
	public void setSp_bf(String sp_bf) {
		this.sp_bf = sp_bf;
	}
	public String getSp_jqs() {
		return sp_jqs;
	}
	public void setSp_jqs(String sp_jqs) {
		this.sp_jqs = sp_jqs;
	}
	public String getSp_bcsfp() {
		return sp_bcsfp;
	}
	public void setSp_bcsfp(String sp_bcsfp) {
		this.sp_bcsfp = sp_bcsfp;
	}
	
	
}
