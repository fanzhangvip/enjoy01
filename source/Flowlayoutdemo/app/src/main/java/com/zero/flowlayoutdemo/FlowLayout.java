package com.zero.flowlayoutdemo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "Zero";

    private int mVerticalSpacing = dp2px(8); //每个item纵向间距
    private int mHorizontalSpacing = dp2px(16); //每个item横向间距

    private List<View> lineViews;//每一行的子View
    private List<List<View>> views;//所有的行 一行一行的存储
    private List<Integer> heights;//每一行的高度


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initMeasureParams();
    }

    private void initMeasureParams() {
        views = new ArrayList<>();
        lineViews = new ArrayList<>();
        heights = new ArrayList<>();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//调用多次
        Log.i(TAG, "onMeasure: width=" + MeasureSpec.getSize(widthMeasureSpec)+", height="+ MeasureSpec.getSize(heightMeasureSpec) + ",getLeft="+ getLeft());
        initMeasureParams();
//        具体的measure 思路
//        1. viewgroup  开始测量自身的尺寸
//        2. 为每个child计算测量限制信息(MeasureSpec) = 限制Mode（高两位） + size(30) 32位 int 二进制 01
//        3. 把上面的限制信息 传递给每个child child 知道如何测量自己了child.measure()发指令 -》 onMeasure
//        4. child测量完毕后 viewgroup获取每个子View测量后的大小
//        5. viewgroup结合自身的情况 计算自己的大小
//        6. 保存自身的大小

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        //记录当前行的宽度和高度
        int lineWidthUsed = 0;// 宽度是当前行子view的宽度之和
        int lineHeight = 0;// 高度是当前行所有子View中高度的最大值

        //整个流式布局的宽度和高度
        int wantedWidth = 0;//所有行中宽度的最大值
        int wantedHeight = 0;// 所以行的高度的累加
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //获取子View
            View childView = this.getChildAt(i);
            LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                            childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                            childLayoutParams.height));

            int childMeasureWidth = childView.getMeasuredWidth();
            int childMeasureHeight = childView.getMeasuredHeight();
            if (lineWidthUsed + childMeasureWidth + mHorizontalSpacing > selfWidth) {
                //换行
                views.add(lineViews);
                heights.add(lineHeight);
                wantedWidth = Math.max(wantedWidth, lineWidthUsed + mHorizontalSpacing);
                wantedHeight += lineHeight + mHorizontalSpacing;
                lineViews = new ArrayList<>();//创建新的一行
                lineWidthUsed = 0;
                lineHeight = 0;
            }
            lineViews.add(childView);
            lineWidthUsed = lineWidthUsed + childMeasureWidth +  mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childMeasureHeight);//取当前行中高度最大的作为当前行高

            //如果当前childView是最后一行的最后一个
            if (i == childCount - 1) {//最后一行
                heights.add(lineHeight);
                views.add(lineViews);
                wantedWidth = Math.max(wantedWidth, lineWidthUsed);
                wantedHeight += lineHeight;
            }

        }

        //确定流式布局自身最终的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: widthMode= " + getModeString(widthMode) + ", heightMode= " + getModeString(heightMode));

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : wantedWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : wantedHeight;
        Log.i(TAG, "onMeasure: selfWidth="+selfWidth+" , wantedWidth="+ wantedWidth+" , realWidth="+realWidth);

        //保存FlowLayout最终宽高
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lineCount = views.size();
        int currX = getLeft();
        Log.i(TAG, "onLayout0: ");
        Log.i(TAG, "onLayout1: "+ getLeft());
        for(int i = 0; i < 101; i++){
            Log.e(TAG, "onLayout: dp="+ i+" , px=" + dp2px(i) );
        }
        int currY = 0;

        for (int i = 0; i < lineCount; i++) {//大循环，所有的子View 一行一行的布局
            List<View> lineViews = views.get(i);//取出一行
            int lineHeight = heights.get(i);// 取出这一行的高度值
            //遍历当前行的子View
            int lineViewCount = lineViews.size();
            for (int j = 0; j < lineViewCount; j++) {//布局当前行的每一个view
                View child = lineViews.get(j);
                int left = currX;
                int top = currY;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                child.layout(left, top, right, bottom);
                //确定下一个view的left
                currX = left + child.getMeasuredWidth() + mHorizontalSpacing;
            }
            currY = currY + lineHeight +  mVerticalSpacing;
            currX = 0;
        }

    }



    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    private String getModeString(int mode) {
        String result = "Unkown";
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = "UNSPECIFIED";
                break;
            case MeasureSpec.EXACTLY:
                result = "EXACTLY";
                break;
            case MeasureSpec.AT_MOST:
                result = "AT_MOST";
                break;
            default:
        }
        return result;
    }
}
