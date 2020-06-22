package com.jc.lottery.view.rebound;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.jc.lottery.bean.LeBall;

/**
 * @author lr
 * @description:
 * @date:${DATA} 11:25
 */

public class LeCrash extends Activity {

    LeBallSurfaceView mLeBallSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// init trigonometric function
        LeTrigonometricFunction.getInstance();

        mLeBallSurfaceView = new LeBallSurfaceView(this);
        mLeBallSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
// TODO Auto-generated method stub
                int eventaction = event.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        for (LeBall mb : LeBallSurfaceView.ballList) {
                            if (isBallExist(mb, event.getX(), event.getY())) {
                                mLeBallSurfaceView.removeMoveBall(mb);
                                return false;
                            }
                        }
                        mLeBallSurfaceView.addMoveBall(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        setContentView(mLeBallSurfaceView);
    }

    private boolean isBallExist(LeBall mb, float x, float y) {
        if (x >= mb.getxAxis() && x <= (mb.getxAxis() + LeCrashValues.ballSize) && y >= mb.getyAxis() && y <= (mb.getyAxis() + LeCrashValues.ballSize)){
            return true;
        }
        return false;
    }
}
