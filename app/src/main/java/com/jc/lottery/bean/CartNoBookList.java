package com.jc.lottery.bean;

import com.jc.lottery.bean.req.pos_GetLogisticsDelivery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:56
 */

public class CartNoBookList {

    private String cartNo;
    private List<pos_GetLogisticsDelivery.BookNoListInfo> list = new ArrayList<pos_GetLogisticsDelivery.BookNoListInfo>();

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String cartNo) {
        this.cartNo = cartNo;
    }

    public List<pos_GetLogisticsDelivery.BookNoListInfo> getList() {
        return list;
    }

    public void setList(List<pos_GetLogisticsDelivery.BookNoListInfo> list) {
        this.list = list;
    }
}
