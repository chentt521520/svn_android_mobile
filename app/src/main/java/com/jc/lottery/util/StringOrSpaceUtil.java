package com.jc.lottery.util;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:33
 */

public class StringOrSpaceUtil {

    private static StringOrSpaceUtil stringOrSpaceUtil = new StringOrSpaceUtil();

    // 私有的构造方法
    private StringOrSpaceUtil(){}

    // 以自己实例为返回值的静态的公有方法，静态工厂方法
    public static StringOrSpaceUtil getStringOrSpaceUtil(){
        return stringOrSpaceUtil;
    }

    public String StringOrSpace(String content){
        if (content.equals("")){
            return "--";
        }
        return content;
    }
}
