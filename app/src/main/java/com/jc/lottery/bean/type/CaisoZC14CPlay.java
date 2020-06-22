package com.jc.lottery.bean.type;

import java.io.Serializable;

public class CaisoZC14CPlay implements LotteryType, Serializable{

	private static final long serialVersionUID = 1L;
	
	private String term_no;
	private Group<ZC14CMatch> zc14cMatchsGroup;
	public String getTerm_no() {
		return term_no;
	}
	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}
	public Group<ZC14CMatch> getZc14cMatchsGroup() {
		return zc14cMatchsGroup;
	}
	public void setZc14cMatchsGroup(Group<ZC14CMatch> zc14cMatchsGroup) {
		this.zc14cMatchsGroup = zc14cMatchsGroup;
	}
	
	
	
}
