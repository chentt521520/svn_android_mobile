package com.jc.lottery.bean.type;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:50
 */

public class EncryptedStateBean {

    private String name;
    private boolean state = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
