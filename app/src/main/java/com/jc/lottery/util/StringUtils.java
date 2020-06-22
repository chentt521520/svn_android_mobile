package com.jc.lottery.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0 || "null".equals(s);
	}
	
	public static String StringFilter(String str)throws PatternSyntaxException{ 
		 String regEx = "[/\\\\:*?<>|\"\n\t]"; //要过滤掉的字符
		 Pattern p = Pattern.compile(regEx); 
		 Matcher m = p.matcher(str); 
		 return m.replaceAll("").trim(); 
		 }

}
