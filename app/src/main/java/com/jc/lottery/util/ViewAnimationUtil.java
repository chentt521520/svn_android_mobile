package com.jc.lottery.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Administrator on 2019/8/13.
 */

public class ViewAnimationUtil {

    /**
     * View渐隐动画效果
     */
    public  void setHideAnimation(final View view, int duration,AlphaAnimation mHideAnimation)
    {
        if (null == view || duration < 0)
        {
            return;
        }

        if (null != mHideAnimation)
        {
            mHideAnimation.cancel();
        }
        // 监听动画结束的操作
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        mHideAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation arg0)
            {

            }

            @Override
            public void onAnimationRepeat(Animation arg0)
            {

            }

            @Override
            public void onAnimationEnd(Animation arg0)
            {
                view.setVisibility(View.GONE);
                view.clearAnimation();
            }
        });
        view.startAnimation(mHideAnimation);
    }

    /**
     * View渐现动画效果
     */
    public  void setShowAnimation( final View view, int duration, AlphaAnimation mShowAnimation)
    {
        if (null == view || duration < 0)
        {
            return;
        }
        if (null != mShowAnimation)
        {
            mShowAnimation.cancel();
        }
        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(true);
        mShowAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation arg0)
            {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation arg0)
            {

            }

            @Override
            public void onAnimationEnd(Animation arg0)
            {
                view.clearAnimation();
            }
        });
        view.startAnimation(mShowAnimation);
    }
}
