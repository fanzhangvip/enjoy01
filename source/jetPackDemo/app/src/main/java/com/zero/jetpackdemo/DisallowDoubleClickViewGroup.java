package com.zero.jetpackdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


public abstract class DisallowDoubleClickViewGroup extends ViewGroup {

    /**
     * Defines the duration in milliseconds between the first tap's up event and
     * the second tap's down event for an interaction to be considered a
     * double-tap.
     */
    private static final int DOUBLE_TAP_TIMEOUT = 300;

    /**
     * Defines the minimum duration in milliseconds between the first tap's up event and
     * the second tap's down event for an interaction to be considered a
     * double-tap.
     */
    private static final int DOUBLE_TAP_MIN_TIME = 40;

    /**
     * Distance in dips between the first touch and second touch to still be considered a double tap
     */
    private static final int DOUBLE_TAP_SLOP = 100;

    /**
     * This flag indicates that the event has been generated by a gesture generator. It
     * provides a hint to the GestureDector to not apply any touch slop.
     *
     * @hide
     */
    public static final int FLAG_IS_GENERATED_GESTURE = 0x8;

    private int mTouchSlopSquare;
    private int mDoubleTapTouchSlopSquare;
    private int mDoubleTapSlopSquare;

    private boolean mAlwaysInTapRegion;
    private boolean mAlwaysInBiggerTapRegion;

    private MotionEvent mCurrentDownEvent;
    private MotionEvent mPreviousUpEvent;

    private boolean mIsDoubleTapping;

    private float mLastFocusX;
    private float mLastFocusY;
    private float mDownFocusX;
    private float mDownFocusY;


    public DisallowDoubleClickViewGroup(Context context) {
        super(context);
        init(context);
    }

    public DisallowDoubleClickViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DisallowDoubleClickViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DisallowDoubleClickViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        int touchSlop, doubleTapSlop, doubleTapTouchSlop;
        if (context == null) {
            touchSlop = ViewConfiguration.getTouchSlop();
            doubleTapTouchSlop = touchSlop;
            doubleTapSlop = DOUBLE_TAP_SLOP;
        } else {
            final ViewConfiguration configuration = ViewConfiguration.get(context);
            touchSlop = configuration.getScaledTouchSlop();
            doubleTapTouchSlop = touchSlop;
            doubleTapSlop = configuration.getScaledDoubleTapSlop();
        }
        mTouchSlopSquare = touchSlop * touchSlop;
        mDoubleTapTouchSlopSquare = doubleTapTouchSlop * doubleTapTouchSlop;
        mDoubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
    }

    private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp,
                                          MotionEvent secondDown) {
        if (!mAlwaysInBiggerTapRegion) {
            return false;
        }

        final long deltaTime = secondDown.getEventTime() - firstUp.getEventTime();
        if (deltaTime > DOUBLE_TAP_TIMEOUT || deltaTime < DOUBLE_TAP_MIN_TIME) {
            return false;
        }

        int deltaX = (int) firstDown.getX() - (int) secondDown.getX();
        int deltaY = (int) firstDown.getY() - (int) secondDown.getY();
        final boolean isGeneratedGesture =
                (firstDown.getFlags() & FLAG_IS_GENERATED_GESTURE) != 0;
        int slopSquare = isGeneratedGesture ? 0 : mDoubleTapSlopSquare;
        return (deltaX * deltaX + deltaY * deltaY < slopSquare);
    }

    private void cancelTaps() {
        mIsDoubleTapping = false;
        mAlwaysInTapRegion = false;
        mAlwaysInBiggerTapRegion = false;
    }

    private void cancel() {
        mIsDoubleTapping = false;
        mAlwaysInTapRegion = false;
        mAlwaysInBiggerTapRegion = false;
    }

    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();

        final boolean pointerUp =
                (action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP;
        final int skipIndex = pointerUp ? ev.getActionIndex() : -1;
        final boolean isGeneratedGesture =
                (ev.getFlags() & FLAG_IS_GENERATED_GESTURE) != 0;

        // Determine focal point
        float sumX = 0, sumY = 0;
        final int count = ev.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) continue;
            sumX += ev.getX(i);
            sumY += ev.getY(i);
        }
        final int div = pointerUp ? count - 1 : count;
        final float focusX = sumX / div;
        final float focusY = sumY / div;

        boolean handled = false;

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;
                // Cancel long press and taps
                cancelTaps();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;

                // Check the dot product of current velocities.
                // If the pointer that left was opposing another velocity vector, clear.
                final int upIndex = ev.getActionIndex();
                final int id1 = ev.getPointerId(upIndex);
                break;

            case MotionEvent.ACTION_DOWN:
                if ((mCurrentDownEvent != null) && (mPreviousUpEvent != null)
                        && isConsideredDoubleTap(mCurrentDownEvent, mPreviousUpEvent, ev)) {
                    mIsDoubleTapping = true;
                    handled = true;
                }

                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;
                if (mCurrentDownEvent != null) {
                    mCurrentDownEvent.recycle();
                }
                mCurrentDownEvent = MotionEvent.obtain(ev);
                mAlwaysInTapRegion = true;
                mAlwaysInBiggerTapRegion = true;
                break;

            case MotionEvent.ACTION_MOVE:
                final float scrollX = mLastFocusX - focusX;
                final float scrollY = mLastFocusY - focusY;
                if (mIsDoubleTapping) {
                    // Give the move events of the double-tap
                    handled = true;
                } else if (mAlwaysInTapRegion) {
                    final int deltaX = (int) (focusX - mDownFocusX);
                    final int deltaY = (int) (focusY - mDownFocusY);
                    int distance = (deltaX * deltaX) + (deltaY * deltaY);

                    int doubleTapSlopSquare = isGeneratedGesture ? 0 : mDoubleTapTouchSlopSquare;
                    if (distance > doubleTapSlopSquare) {
                        mAlwaysInBiggerTapRegion = false;
                    }
                } else if ((Math.abs(scrollX) >= 1) || (Math.abs(scrollY) >= 1)) {
                    mLastFocusX = focusX;
                    mLastFocusY = focusY;
                }
                break;

            case MotionEvent.ACTION_UP:
                MotionEvent currentUpEvent = MotionEvent.obtain(ev);
                if (mIsDoubleTapping) {
                    handled = true;
                }
                if (mPreviousUpEvent != null) {
                    mPreviousUpEvent.recycle();
                }
                mPreviousUpEvent = currentUpEvent;
                mIsDoubleTapping = false;
                break;

            case MotionEvent.ACTION_CANCEL:
                cancel();
                break;
        }
        return handled;
    }
}
