package com.jc.lottery.bean.type;

import java.io.Serializable;

public class GeoCoord implements LotteryType , Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double latitude_;
	private double longitude_;
	public double getLatitude_() {
		return latitude_;
	}
	public void setLatitude_(double latitude_) {
		this.latitude_ = latitude_;
	}
	public double getLongitude_() {
		return longitude_;
	}
	public void setLongitude_(double longitude_) {
		this.longitude_ = longitude_;
	}
	

}
