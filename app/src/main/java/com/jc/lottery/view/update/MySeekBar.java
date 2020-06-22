package com.jc.lottery.view.update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by Administrator on 2019/8/12.
 */

@SuppressLint("AppCompatCustomView")
public class MySeekBar extends SeekBar {

    public MySeekBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //return super.onTouchEvent(event);
        return false ;
    }
}
