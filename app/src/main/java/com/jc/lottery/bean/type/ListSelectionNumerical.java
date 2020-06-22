package com.jc.lottery.bean.type;
import android.os.Parcel;
import android.os.Parcelable;

import com.jc.lottery.util.ParcelUtils;


public class ListSelectionNumerical implements Parcelable{
	/**
	 * 
	 */
	private int zhuShu;
	private String strHasoMa;
	private String type;
	private String ZuheType;
	private String isRandomSelection;
	public ListSelectionNumerical() {
		
	}

	public String getIsRandomSelection() {
		return isRandomSelection;
	}

	public void setIsRandomSelection(String isRandomSelection) {
		this.isRandomSelection = isRandomSelection;
	}

	public int getZhuShu() {
		return zhuShu;
	}

	public void setZhuShu(int zhuShu) {
		this.zhuShu = zhuShu;
	}

	public String getStrHasoMa() {
		return strHasoMa;
	}
	public void setStrHasoMa(String strHasoMa) {
		this.strHasoMa = strHasoMa;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(zhuShu);
		ParcelUtils.writeStringToParcel(dest, strHasoMa);
		ParcelUtils.writeStringToParcel(dest, type);
		ParcelUtils.writeStringToParcel(dest, ZuheType);
		ParcelUtils.writeStringToParcel(dest, isRandomSelection);
	}
	
	public static final Creator<ListSelectionNumerical> CREATOR = new Creator<ListSelectionNumerical>() {
        public ListSelectionNumerical createFromParcel(Parcel in) {
            return new ListSelectionNumerical(in);
        }

        @Override
        public ListSelectionNumerical[] newArray(int size) {
            return new ListSelectionNumerical[size];
        }
    };
    
    public ListSelectionNumerical(Parcel in) {
		// TODO Auto-generated constructor stub
		zhuShu = in.readInt();
		strHasoMa = ParcelUtils.readStringFromParcel(in);
		type = ParcelUtils.readStringFromParcel(in);
		ZuheType = ParcelUtils.readStringFromParcel(in);
		isRandomSelection = ParcelUtils.readStringFromParcel(in);
	}

	public String getZuheType() {
		return ZuheType;
	}

	public void setZuheType(String zuheType) {
		ZuheType = zuheType;
	}
    
}
