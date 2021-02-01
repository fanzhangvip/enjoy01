package com.jinlong.newmaterialdesign.beh1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollerBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public ScrollerBehavior() {
    }

    public ScrollerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        // 依赖TextView(也就是上面HellorWorld所在的View)
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        // 如果我们所依赖的View有变化，也是通过offsetTopAndBottom移动我们的RecyclerView
        ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop()));
        return false;
    }
}