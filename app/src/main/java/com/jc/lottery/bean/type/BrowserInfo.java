package com.jc.lottery.bean.type;

import java.io.Serializable;

public class BrowserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
    public BrowserInfo(String title) {
        this.title = title;
    }

    public String getAppInfoId() {
        return title;
    }

    public void setAppInfoId(String title) {
        this.title = title;
    }
}
