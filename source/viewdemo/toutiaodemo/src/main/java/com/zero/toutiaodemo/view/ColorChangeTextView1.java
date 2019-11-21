package com.zero.toutiaodemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ColorChangeTextView1 extends View {

//    <attr name="text"/>
//        <attr name="text_size"/>
//        <attr name="text_color"/>
//        <attr name="text_color_change"/>
//        <attr name="progress"/>
//        <attr name="direction"/>
    private String mText;
    private float mTextSize;
    private int mTextColor;
    private int mTextColorChange;
    private float mProgress;

    @Directions
    private int mDirection;

    ///////////////////////////////////////////////////////////////////////////
    // Direction 代替枚举
    ///////////////////////////////////////////////////////////////////////////
    @IntDef(flag = true,value = {DIRECTION_LEFT,DIRECTION_RIGHT,DIRECTION_TOP,DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Directions{}
    private static final int DIRECTION_LEFT = 0;
    private static final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_TOP = 2;
    private static final int DIRECTION_BOTTOM = 3;



    public void setDirection(@Directions int direction){
        mDirection = direction;
    }

    public ColorChangeTextView1(final Context context) {
        super(context);
        init();
    }

    public ColorChangeTextView1(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorChangeTextView1(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ColorChangeTextView1(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){

    }

    private void initAttr(final Context context,@Nullable final AttributeSet attrs){

    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
    }

    private void measureText(){

    }

    static int dp2px(float dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,Resources.getSystem().getDisplayMetrics());
    }
    static int sp2px(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,dp, Resources.getSystem().getDisplayMetrics());
    }
}
