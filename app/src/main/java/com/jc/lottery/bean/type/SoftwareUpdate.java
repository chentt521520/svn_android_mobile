package com.jc.lottery.bean.type;

import java.io.Serializable;

public class SoftwareUpdate implements LotteryType, Serializable{
	/**
	 * {
	 "status":"_0000",
	 "message":"服务器处理成功",
	 "version":"1.0.1",
	 "downloadUrl":"http://chu.mvschina.com/upnew/cce_lottery_v1.0.0.apk",
	 "updataDescription":"1、企业、机构等成为主办方\n2、统一使用实名制\n3、浏览活动详情优化",
	 "upload_type":"2"
	 }
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String message;
	private String version;
	private String downloadUrl;
	private String updataDescription;
	private String upload_type;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdataDescription() {
		return updataDescription;
	}

	public void setUpdataDescription(String updataDescription) {
		this.updataDescription = updataDescription;
	}

	public String getUpload_type() {
		return upload_type;
	}

	public void setUpload_type(String upload_type) {
		this.upload_type = upload_type;
	}
}
