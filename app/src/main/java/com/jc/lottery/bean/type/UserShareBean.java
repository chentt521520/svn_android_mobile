package com.jc.lottery.bean.type;

import java.io.Serializable;

public class UserShareBean implements LotteryType, Serializable{
	/**
	 * {
	 "status":"_0000",
	 "message":{
	 "img_path":"http://chu.mvschina.com/upnew/logo.png",
	 "content":"快来一起抢红包啦，你我都可以得到红包奖励！",
	 "title":"邀请好友",
	 "create_time":"2017-12-08 18:23:49"
	 }
	 }
	 */
	private String status;
	private String img_path;
	private String content;
	private String title;
	private String create_time;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
