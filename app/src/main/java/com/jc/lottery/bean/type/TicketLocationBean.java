package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： TicketLocationBean
 * @classDescription：
 * @author： 万
 * @createTime： 2017/11/20 11:51
 */
public class TicketLocationBean implements Serializable {
    public String name;
    public String address;
    public String distance;
    public double latitude;
    public double longitude;

    public TicketLocationBean() {
    }

    public TicketLocationBean(String name, String address, String distance, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
