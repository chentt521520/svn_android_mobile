package com.jc.lottery.bean.type;

import android.location.Location;

import java.io.Serializable;

public class JsonLocation implements LotteryType ,Serializable{
	
	private Location location;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}
	
}
