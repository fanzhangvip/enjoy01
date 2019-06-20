package com.zero.scrollerdemo;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalScroller extends ViewGroup {
    private int mTouchSlop;
    private float mLastXIntercept=0;
    private float mLastYIntercept=0;
    private float mLastX=0;
    private float mLastY=0;
    private int leftBorder;
    private int rightBorder;

    private Scroller mScroller;




    public HorizontalScroller(Context context) {
        super(context);
        init(context);
    }

    public HorizontalScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        //创建实例
        mScroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }


//    public void smoothScrollTo(){
//        int ScrollX = getScrollX();
//        int ScrollY = getScrollY();
//        //初始化，1000ms内缓慢滑动到deltaX
//        mScroller.startScroll(ScrollX, 0, 0, deltaX, 1000);
//        invalidate();
//    }

//    @Override
//    public void computeScroll() {
//        if(mScroller.computeScrollOffset()){
//            int currX = mScroller.getCurrX();
//            int currY = mScroller.getCurrY();
//            scrollTo(currX, currY);
//            postInvalidate();
//        }
//    }

//    @Override
//    public void computeScroll() {
//        // 第三步，重写computeScroll()方法，在其内部调用scrollTo或ScrollBy方法，完成滑动过程
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            postInvalidate();
//        }
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float xIntercept = ev.getX();
        float yIntercept = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xIntercept-mLastXIntercept;
                float deltaY = yIntercept-mLastYIntercept;
                // 当水平方向的滑动距离大于竖直方向的滑动距离，且手指拖动值大于TouchSlop值时，拦截事件
                if (Math.abs(deltaX)>Math.abs(deltaY) && Math.abs(deltaX)>mTouchSlop) {
                    intercept=true;
                }else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        mLastX = xIntercept;
        mLastY = yIntercept;
        mLastXIntercept = xIntercept;
        mLastYIntercept = yIntercept;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xTouch-mLastX;
                float deltaY = yTouch-mLastY;
                float scrollByStart = deltaX;
                if (getScrollX() - deltaX < leftBorder) {
                    scrollByStart = getScrollX()-leftBorder;
                } else if (getScrollX() + getWidth() - deltaX > rightBorder) {
                    scrollByStart = rightBorder-getWidth()-getScrollX();
                }
                scrollBy((int) -scrollByStart, 0);
                break;
            case MotionEvent.ACTION_UP:
//                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                scrollTo(getScrollX()+dx,0);
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
//                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
//                int dx = targetIndex * getWidth() - getScrollX();
//                // 第二步，使用startScroll方法，对其进行初始化
//                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
            default:
                break;
        }
        mLastX = xTouch;
        mLastY = yTouch;
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 测量每一个子控件的大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 在水平方向上对子控件进行布局
                childView.layout(i * getMeasuredWidth(), 0, i * getMeasuredWidth()+childView.getMeasuredWidth()+getPaddingLeft(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值
            leftBorder = 0;
            rightBorder = getChildCount()*getMeasuredWidth();
        }
    }
}
