package com.zero.toutiaodemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zero.toutiaodemo.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ColorChangeTextView1 extends View {
    private static final String TAG = "Zero";

    private String mText = "享学课堂";
    private int mTextSize = sp2px(30);
    private int mTextColor = Color.BLACK;
    private int mTextColorChange = Color.RED;
    private float mProgress;

    @Directions
    private int mDirection = DIRECTION_LEFT;
    private Paint mLinePaint;

    ///////////////////////////////////////////////////////////////////////////
    // Direction 代替枚举
    ///////////////////////////////////////////////////////////////////////////
    @IntDef(flag = true, value = {DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Directions {
    }

    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_TOP = 2;
    public static final int DIRECTION_BOTTOM = 3;

    ///////////////////////////////////////////////////////////////////////////
    // 绘制
    ///////////////////////////////////////////////////////////////////////////
    private Rect mTextBound = new Rect();
    private Paint mPaint;
    private int mTextWidth;
    private int mTextHeight;

    private int mTextStartX;
    private int mTextStartY;

    public void setDirection(@Directions int direction) {
        mDirection = direction;
    }

    public ColorChangeTextView1(Context context) {
        this(context, null);
    }

    public ColorChangeTextView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChangeTextView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(final Context context, @Nullable final AttributeSet attrs) {
        mPaint = new Paint();
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(dp2px(3));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.GREEN);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ColorChangeTextView1);
        mText = ta.getString(R.styleable.ColorChangeTextView1_text);
        mTextSize = ta.getDimensionPixelSize(
                R.styleable.ColorChangeTextView1_text_size, mTextSize);
        mTextColor = ta.getColor(
                R.styleable.ColorChangeTextView1_text_color, mTextColor);
        mTextColorChange = ta.getColor(
                R.styleable.ColorChangeTextView1_text_color_change, mTextColorChange);
        mProgress = ta.getFloat(R.styleable.ColorChangeTextView1_progress, 0);

        mDirection = ta
                .getInt(R.styleable.ColorChangeTextView1_direction, mDirection);

        ta.recycle();

        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //应该如何合理的测量view的尺寸？
        //1.  测量文字大小
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        Log.i(TAG, "mTextBound = " + mTextBound);
        mTextWidth = (int) (mPaint.measureText(mText) + .5f);//24.3 -> 24  25
        Log.i(TAG, "mTextWidth = " + mTextWidth);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);

        //一种获取高度的办法

        mTextHeight = (int) (fontMetrics.descent - fontMetrics.ascent + .5f);
        Log.i(TAG, "mTextHeight = " + mTextHeight);

        //2.  决定view大小
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        //3.  setMeasuredDimension
        setMeasuredDimension(width, height);

        mTextStartX = width / 2 - mTextWidth / 2;
        mTextStartY = height / 2 - mTextHeight / 2;

    }

    private int measureWidth(final int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = mTextWidth + getPaddingLeft() + getPaddingRight();
                break;
        }
        result =(mode == MeasureSpec.AT_MOST) ? Math.min(result,size): result;
        return result;
    }

    private int measureHeight(final int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = mTextBound.height() + getPaddingTop() + getPaddingBottom();
                break;
        }
        result =(mode == MeasureSpec.AT_MOST) ? Math.min(result,size): result;
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        //文字绘制
//        canvas.drawText(mText,mTextStartX,mTextStartY,mPaint);
        switch (mDirection){
            case DIRECTION_LEFT:

                //1 先绘制改变的颜色的文字  ？
                mPaint.setColor(mTextColorChange);
                //起始
                int starx = 0;
                starx =  mTextStartX;
                //绘制的终点
                int finalx = 0;
                finalx = (int)(mTextStartX + mProgress * mTextWidth);
                canvas.save();
                //剪裁的画布
                canvas.clipRect(starx,0,finalx,getMeasuredHeight());
                Log.i(TAG, "finalx = " + finalx + " ,mProcess: " + mProgress);
                canvas.drawRect(canvas.getClipBounds(),mLinePaint);
                canvas.drawText(mText,mTextStartX
                ,getMeasuredHeight()/2 - (mPaint.descent()/2 + mPaint.ascent()/2),mPaint);
                canvas.restore();

                //2.绘制没有改变 底色
                canvas.save();
                mPaint.setColor(mTextColor);
                canvas.drawText(mText,finalx,getMeasuredHeight()/2 - (mPaint.descent()/2 + mPaint.ascent()/2),mPaint);
                canvas.restore();
                break;
            case DIRECTION_RIGHT:
                break;
            case DIRECTION_TOP:
                break;
            case DIRECTION_BOTTOM:
                break;
        }

    }





    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        mPaint.setTextSize(mTextSize);
        requestLayout();
        invalidate();
    }

    public void setText(String text) {
        this.mText = text;
        requestLayout();
        invalidate();
    }

    public int getTextColorChange() {
        return mTextColorChange;
    }

    public void setTextColorChange(int textColorChange) {
        this.mTextColorChange = textColorChange;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        invalidate();
    }

    static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    static int sp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
