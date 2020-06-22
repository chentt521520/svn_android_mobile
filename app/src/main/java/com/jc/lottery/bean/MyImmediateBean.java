package com.jc.lottery.bean;

/**
 * @author lr
 * @description:
 * @date:${DATA} 19:53
 */

public class MyImmediateBean {

    private int id;
    private String name;
    private int icon;
    private int iconLock;//锁定状态时icon
    private boolean state = false; //加密状态
    private String alias;

    public MyImmediateBean(int id, String name, int icon, boolean state, String alias) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.state = state;
        this.alias = alias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
