package com.jinlong.newmaterialdesign.beh1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class SampleHeaderBehavior extends CoordinatorLayout.Behavior<TextView> {

    private int mOffsetTopAndBottom;
    private int mLayoutTop;

    public SampleHeaderBehavior() {
    }

    public SampleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 这个方法里，我们并没有自己布局，还是直接通过parent去布局，重写该方法只是为了获取初始top值
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, TextView child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        // 获取到child初始的top值
        mLayoutTop = child.getTop();
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, TextView child, View directTargetChild, View target, int nestedScrollAxes) {
        // 这里我们只关系垂直方向的滚动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, TextView child, View target, int dx, int dy, int[] consumed) {
        if (dy != 0) {
            // 如果本次滑动距离不为0，进行自己的滚动操作
            consumed[1] = scroll(child, dy);
        }
    }

    // 获取childView最大可滑动距离
    private int getChildScrollRang(View childView) {
        if (childView == null) {
            return 0;
        }
        return childView.getHeight();
    }

    // 滚动child
    private int scroll(View child, int dy) {
        int consumed = 0; // 记录我们消费的距离
        int offset = mOffsetTopAndBottom - dy; // 计算出本次需要滚动到的位置
        int minOffset = -getChildScrollRang(child);
        int maxOffset = 0;
        // 调整滚动距离，在0和最大可滑动距离的负数之间(因为是向上滑动，所以是负数哦)
        offset = offset < minOffset ? minOffset : (offset > maxOffset ? maxOffset : offset);
        // 通过offsetTopAndBottom()进行滚动
        ViewCompat.offsetTopAndBottom(child, offset - (child.getTop() - mLayoutTop));
        // 计算消费的距离
        consumed = mOffsetTopAndBottom - offset;
        // 将本次滚动到的位置记录下来
        mOffsetTopAndBottom = offset;
        return consumed;
    }
}

