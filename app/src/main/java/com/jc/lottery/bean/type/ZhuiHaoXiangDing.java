package com.jc.lottery.bean.type;

import java.io.Serializable;

public class ZhuiHaoXiangDing implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String build_time;
	private String start_term;
	private String end_term;
	private String okterm_num;
	private String is_winprize;
	private String multiple;
	private String money;
	private String term_num;
	private String zhuShu;
	
	private Group<ZhuiHaoXiangQingData> dataGroup;
	public String getBuild_time() {
		return build_time;
	}
	public void setBuild_time(String build_time) {
		this.build_time = build_time;
	}
	public String getStart_term() {
		return start_term;
	}
	public void setStart_term(String start_term) {
		this.start_term = start_term;
	}
	public String getEnd_term() {
		return end_term;
	}
	public void setEnd_term(String end_term) {
		this.end_term = end_term;
	}
	public String getOkterm_num() {
		return okterm_num;
	}
	public void setOkterm_num(String okterm_num) {
		this.okterm_num = okterm_num;
	}
	public String getIs_winprize() {
		return is_winprize;
	}
	public void setIs_winprize(String is_winprize) {
		this.is_winprize = is_winprize;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Group<ZhuiHaoXiangQingData> getDataGroup() {
		return dataGroup;
	}
	public void setDataGroup(Group<ZhuiHaoXiangQingData> dataGroup) {
		this.dataGroup = dataGroup;
	}
	public String getTerm_num() {
		return term_num;
	}
	public void setTerm_num(String term_num) {
		this.term_num = term_num;
	}
	public String getZhuShu() {
		return zhuShu;
	}
	public void setZhuShu(String zhuShu) {
		this.zhuShu = zhuShu;
	}
	
	
}
