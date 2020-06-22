package com.jc.lottery.bean.type;
/**
 * 视频播放javabean
 * @author office
 *
 */
public class VideoDataBean {

	private String createTime;
	private String picUrl;
	private String title;
	private String videoUrl;
	
	public VideoDataBean(String createTime, String picUrl, String title, String videoUrl) {
		super();
		this.createTime = createTime;
		this.picUrl = picUrl;
		this.title = title;
		this.videoUrl = videoUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}
