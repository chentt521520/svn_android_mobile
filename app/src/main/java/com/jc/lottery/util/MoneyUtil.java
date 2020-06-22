package com.jc.lottery.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:45
 */

public class MoneyUtil {

    public static MoneyUtil ins = new MoneyUtil();

    private MoneyUtil(){}

    public static MoneyUtil getIns(){
        return ins;
    }

    public String GetMoney(String money){
        money = money.replace(",","");
        if (!money.equals("")) {
            NumberFormat nf = new DecimalFormat("#.##");
            double moneyDouble = Double.parseDouble(money) / 100;
//            return String.format("%.2f", moneyDouble);
            return nf.format(moneyDouble);
        }
        return "--";
    }
}
