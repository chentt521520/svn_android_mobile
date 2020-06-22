package com.jc.lottery.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.jc.lottery.R;

/**
 * @author lr
 * @description:
 * @date:${DATA} 12:45
 */

public class MyNestedScrolling extends NestedScrollView implements NestedScrollingParent2 {

    private int recommendY;

    public MyNestedScrolling(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //当子 view （直接或间接）调用startNestedScroll(View, int)时，会回调父控件该方法。
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        //获取textViewStart顶部的Y值，这个Y值是RecyclerView开始滚动的位置以及NestedScrolling停止滚动的位置
        recommendY = child.findViewById(R.id.tv_receiving_item_one).getTop();
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        super.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 在子View消费滑动事件前，优先响应滑动操作，消费部分或全部滑动距离。
     *
     * @param target   触发嵌套滑动的 view
     * @param dx       表示 view 本次 x 方向的滚动的总距离，单位：像素
     * @param dy       表示 view 本次 y 方向的滚动的总距离，单位：像素
     * @param consumed 输出：表示父布局消费的水平和垂直距离。
     * @param type     触发滑动事件的类型：其值有
     *                 ViewCompat. TYPE_TOUCH
     *                 ViewCompat. TYPE_NON_TOUCH
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        //当滑动距离（dy）大于0，并且当前Y轴小于RecyclerView开始滚动的位置就让NestedScrollView滚动，滑动距离消费不完则由RecyclerView消费
        if (dy > 0 && getScrollY() < recommendY) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }
}
