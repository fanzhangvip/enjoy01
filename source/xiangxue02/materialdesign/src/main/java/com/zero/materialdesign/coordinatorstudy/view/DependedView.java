package com.zero.materialdesign.coordinatorstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.core.view.ViewCompat;


public class DependedView extends View {

    private float mLastX;
    private float mLastY;
    private final int mDragSlop;


    public DependedView(Context context) {
        this(context, null);
    }

    public DependedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DependedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getX() - mLastX);
                int dy = (int) (event.getY() - mLastY);
                if (Math.abs(dx) > mDragSlop || Math.abs(dy) > mDragSlop) {
                    ViewCompat.offsetTopAndBottom(this, dy);
                    ViewCompat.offsetLeftAndRight(this, dx);
                }
                mLastX = event.getX();
                mLastY = event.getY();
                break;

            default:
                break;

        }

        return true;
    }
}
