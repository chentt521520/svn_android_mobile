package com.jc.lottery.bean.type;

/**
 * Created by 万 on 2017/3/30.
 */

public class BannerBean {
    private String bannerUrl;
    private String imageUrl;

    public BannerBean(String bannerUrl, String imageUrl) {
        this.bannerUrl = bannerUrl;
        this.imageUrl = imageUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
