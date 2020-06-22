package com.jc.lottery.bean.type;

import java.io.Serializable;

public class PoiSearch implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title_;
	private String addr_;
	private String phone_;
	private GeoCoord loc;
	public String getTitle_() {
		return title_;
	}
	public void setTitle_(String title_) {
		this.title_ = title_;
	}
	public String getAddr_() {
		return addr_;
	}
	public void setAddr_(String addr_) {
		this.addr_ = addr_;
	}
	public String getPhone_() {
		return phone_;
	}
	public void setPhone_(String phone_) {
		this.phone_ = phone_;
	}
	public GeoCoord getLoc() {
		return loc;
	}
	public void setLoc(GeoCoord loc) {
		this.loc = loc;
	}
	
}
