package com.zero.jetpackdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public abstract class DoubleViewGroup extends ViewGroup {
    public DoubleViewGroup(Context context) {
        super(context);
        init(context);
    }

    public DoubleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DoubleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DoubleViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){

    }

    private static long lastClickTime;
    public static boolean isDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }



}
