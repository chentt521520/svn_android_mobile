package com.jc.lottery.bean.type;

import java.io.Serializable;

public class JCZQDanShuangWanFa implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String [] strSPFWanFa;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getStrSPFWanFa() {
		return strSPFWanFa;
	}
	public void setStrSPFWanFa(String[] strSPFWanFa) {
		this.strSPFWanFa = strSPFWanFa;
	}
	
}
