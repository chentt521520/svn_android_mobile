package com.jc.lottery.bean.type;

import java.io.Serializable;

public class Payment implements LotteryType , Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mumber;
	private int betting;
	private String type;
	private String zuheType;
	private String isRandomSelection;

	public String getIsRandomSelection() {
		return isRandomSelection;
	}

	public void setIsRandomSelection(String isRandomSelection) {
		this.isRandomSelection = isRandomSelection;
	}

	public String getMumber() {
		return mumber;
	}
	public void setMumber(String mumber) {
		this.mumber = mumber;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBetting() {
		return betting;
	}
	public void setBetting(int betting) {
		this.betting = betting;
	}
	public String getZuheType() {
		return zuheType;
	}
	public void setZuheType(String zuheType) {
		this.zuheType = zuheType;
	}
	
}
