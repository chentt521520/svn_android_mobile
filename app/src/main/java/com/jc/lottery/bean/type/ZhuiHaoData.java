package com.jc.lottery.bean.type;

import java.io.Serializable;

public class ZhuiHaoData implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lottery_type;
	private String build_time;
	private String money;

	private String okterm_num;
	private String term_num;
	private String status;
	private String is_winprize;
	private String id;
	private String chase_id;
	public String getLottery_type() {
		return lottery_type;
	}
	public void setLottery_type(String lottery_type) {
		this.lottery_type = lottery_type;
	}
	public String getBuild_time() {
		return build_time;
	}
	public void setBuild_time(String build_time) {
		this.build_time = build_time;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getOkterm_num() {
		return okterm_num;
	}
	public void setOkterm_num(String okterm_num) {
		this.okterm_num = okterm_num;
	}
	public String getTerm_num() {
		return term_num;
	}
	public void setTerm_num(String term_num) {
		this.term_num = term_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIs_winprize() {
		return is_winprize;
	}
	public void setIs_winprize(String is_winprize) {
		this.is_winprize = is_winprize;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChase_id() {
		return chase_id;
	}
	public void setChase_id(String chase_id) {
		this.chase_id = chase_id;
	}
	
	
}
