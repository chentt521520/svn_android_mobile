package com.jc.lottery.error;

public class LotteryException extends Exception {
    private static final long serialVersionUID = 1L;
    
    private String mExtra;

    public LotteryException(String message) {
        super(message);
    }

    public LotteryException(String message, String extra) {
        super(message);
        mExtra = extra;
    }
    
    public String getExtra() {
        return mExtra;
    }
}
