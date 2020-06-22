package com.jc.lottery.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {

	public static String getAppVersion(Context context) {
		try {  
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
		    // 当前应用的版本名称  
		    String versionName = info.versionName;  
		    return versionName;
		    // 当前版本的版本号  
//		    int versionCode = info.versionCode;  
		    // 当前版本的包名  
//		    String packageNames = info.packageName;  
		} catch (NameNotFoundException e) {  
		    e.printStackTrace();  
		}
		return "0.0";  
	}
}
