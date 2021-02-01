package com.zero.toutiaodemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class ColorChangeTextView2 extends View {
    private static final String TAG = "Zero";

    private String mText = "享学课堂";//成员变量

    private float mPercent = 0.0f;


    public float getPercent() {
        return mPercent;
    }

    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
        invalidate();
    }

    public ColorChangeTextView2(Context context) {
        this(context, null);
    }

    public ColorChangeTextView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChangeTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        initAttr(context, attrs);
    }

    private void init() {

    }

    private void initAttr(final Context context, @Nullable final AttributeSet attrs) {

    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        //绘制文字
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setTextSize(80);
        float baseline = paint.getFontSpacing();
        canvas.drawText(mText,0,baseline,paint);

        //画一根中心线
        drawCenterLineX(canvas);
        drawCenterLineY(canvas);

        //绘制第0层
        drawCenterText(canvas);
        //绘制第一层
        drawCenterText1(canvas);
    }

    private void drawCenterText(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setTextSize(80);


        canvas.save();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Log.i(TAG,"fontMetrics.ascent: " + fontMetrics.ascent + ", fontMetrics.descent: " + fontMetrics.descent);
        float baseline = getMeasuredHeight()/2 + (fontMetrics.descent - fontMetrics.ascent )/2 - fontMetrics.descent;

        //获取文字的宽度
        float width = paint.measureText(mText);

        float x =  getMeasuredWidth()/2 - width/2;

        canvas.drawText(mText,x,baseline,paint);
        canvas.restore();
    }

    private void drawCenterText1(final Canvas canvas){
        //反面教材
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        //获取文字的宽度
        float width = paint.measureText(mText);
        canvas.save();

        float left = getMeasuredWidth()/2 - width/2;
        //right = left + deltaX[0,width]

        float right =left + width * mPercent;
        Rect rect = new Rect((int)left,0,(int)right,getMeasuredHeight());
        canvas.clipRect(rect);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Log.i(TAG,"fontMetrics.ascent: " + fontMetrics.ascent + ", fontMetrics.descent: " + fontMetrics.descent);
        float baseline = getMeasuredHeight()/2 + (fontMetrics.descent - fontMetrics.ascent )/2 - fontMetrics.descent;



        float x =  getMeasuredWidth()/2 - width/2;

        canvas.drawText(mText,x,baseline,paint);
        canvas.restore();
    }

    private void drawCenterLineY(final Canvas canvas){
        //获取view高度
        float height = getMeasuredHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(0,height/2,getMeasuredWidth(),height/2,paint);
    }

    private void drawCenterLineX(final Canvas canvas){
        //获取view高度
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(getMeasuredWidth()/2,0,getMeasuredWidth()/2,getMeasuredHeight(),paint);
    }

}
