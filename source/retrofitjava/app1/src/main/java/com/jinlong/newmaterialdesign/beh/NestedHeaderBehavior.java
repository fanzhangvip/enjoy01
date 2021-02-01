package com.jinlong.newmaterialdesign.beh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import java.lang.ref.WeakReference;

public class NestedHeaderBehavior extends CoordinatorLayout.Behavior<View> {


    private WeakReference<View> mNestedScrollingChildRef;

    public static final String TAG = "Zero";

    private int mOffset;

    public NestedHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        mNestedScrollingChildRef = new WeakReference<>(findScrollingChild(parent));
        return super.onLayoutChild(parent, child, layoutDirection);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        View scrollingChild = mNestedScrollingChildRef.get();
        if (target != scrollingChild) {
            return;
        }
        int currentTop = child.getTop();
        int newTop = currentTop - dy;
        if (dy > 0) {//向上滑动
            //处理在范围内的滚动与fling
            if (newTop >= -child.getHeight()) {
                consumed[1] = dy;
                mOffset = -dy;
                ViewCompat.offsetTopAndBottom(child, -dy);
                coordinatorLayout.dispatchDependentViewsChanged(child);
            } else { //当超过后，单独处理
                consumed[1] = child.getHeight() + currentTop;
                mOffset = -consumed[1];
                ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                coordinatorLayout.dispatchDependentViewsChanged(child);
            }
        }
        if (dy < 0) {//向下滑动
            if (newTop <= 0 && !target.canScrollVertically(-1)) {
                consumed[1] = dy;
                mOffset = -dy;
                ViewCompat.offsetTopAndBottom(child, -dy);
                coordinatorLayout.dispatchDependentViewsChanged(child);
            }
        }

    }

    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        if (dyUnconsumed < 0) {//表示已经向下滑动到头。
            int currentTop = child.getTop();
            int newTop = currentTop - dyUnconsumed;
            if (newTop <= 0) {
                ViewCompat.offsetTopAndBottom(child, -dyUnconsumed);
                mOffset = -dyUnconsumed;
            } else {//如果当前的值大于最大的偏移量，那么就直接滚动到-currentTop就行了
                ViewCompat.offsetTopAndBottom(child, -currentTop);
                mOffset = -currentTop;
            }
            coordinatorLayout.dispatchDependentViewsChanged(child);
        }
    }


    /**
     * 向下查找实现了NestedScrollingChild的view
     */
    private View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    /**
     * 获取当前控件的偏移量
     */
    public int getOffset() {
        return mOffset;
    }

}
