/**
 * Copyright 2009 Joe LaPenna
 */

package com.jc.lottery.bean.type;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class Group<T extends LotteryType> extends ArrayList<T> implements LotteryType {

    private static final long serialVersionUID = 1L;

    private String mType;
    
    public Group() {
        super();
    }
    
    public Group(Collection<T> collection) {
        super(collection);
    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }
}
