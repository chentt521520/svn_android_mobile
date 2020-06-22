package com.jc.lottery.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @ Create_time: 2019/1/22 on 17:21.
 * @ description：Glide图片加载
 * @ author: vchao
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(path).into(imageView);
    }
}
