package com.jc.lottery.bean.type;

import java.io.Serializable;

public class ZhuiHaoXiangQingData implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
