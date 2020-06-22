package com.jc.lottery.bean.type;

import java.io.Serializable;

public class SelScancodeBean implements LotteryType, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selstatus;
	public String getSelstatus() {
		return selstatus;
	}
	public void setSelstatus(String selstatus) {
		this.selstatus = selstatus;
	}
	
	

}
