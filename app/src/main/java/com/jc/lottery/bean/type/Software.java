package com.jc.lottery.bean.type;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class Software implements LotteryType, Parcelable{
	private String title;
	private Bitmap logo;
	private String SoftwareUrl;
	public Software(){

	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Bitmap getLogo() {
		return logo;
	}
	public void setLogo(Bitmap logo) {
		this.logo = logo;
	}
	


	public String getSoftwareUrl() {
		return SoftwareUrl;
	}

	public void setSoftwareUrl(String softwareUrl) {
		SoftwareUrl = softwareUrl;
	}



	public static final Creator<Software> CREATOR = new Creator<Software>() {
		@Override
		public Software createFromParcel(Parcel in) {

			return new Software(in);
		}
		@Override
		public Software[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Software[size];
		}
	};



	private Software(Parcel in) {
//		title = ParcelUtils.readStringFromParcel(in);
//		if(0 != in.readInt()){
//			logo = Bitmap.CREATOR.createFromParcel(in);//因为Bitmap实现了Parcelable接口，所以这里可以这样使用
//		}
//		SoftwareUrl = ParcelUtils.readStringFromParcel(in);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
//		ParcelUtils.writeStringToParcel(out, title);
		
		if(this.logo != null){  
			out.writeInt(1);  
			this.logo.writeToParcel(out, flags);  
		}else{  
			out.writeInt(0);  
		} 
		
//		ParcelUtils.writeStringToParcel(out, SoftwareUrl);
	}
}
