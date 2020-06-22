package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： TicketLocationListBean
 * @classDescription： 出票位置查找列表实体类
 * @author： 万
 * @createTime： 2017/11/9 16:09
 */
public class TicketLocationListBean implements Serializable {
    private String id; // 门店ID
    private String address; // 门店地址
    private String createtime; // 创建时间
    private String latitude; // 纬度
    private String longitude; // 经度
    private String name; // 门店名称
    private String person; // 门店负责人
    private String distance; // 距离
    private String img; // 图片

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
