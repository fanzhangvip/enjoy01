package com.jinlong.newmaterialdesign.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * 和顶部AppBarLayout联动的behavior
 */

public class TwoBehavior extends CoordinatorLayout.Behavior<View> {

    private String TAG = TwoBehavior.class.getSimpleName();

    //这个千万要写，否则会出异常
    public TwoBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //依赖于AppBarLayout的
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //计算出AppBarLayout移动的距离
        float top = Math.abs(dependency.getTop());
        Log.e(TAG, "AppBarLayout移动的距离" + top);
        child.setTranslationY(top);
        return true;
    }
}
