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
import androidx.appcompat.widget.AppCompatTextView;

public class ColorChangeTextView2 extends AppCompatTextView {
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

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        //绘制文字
        //决定显示什么(形状) canvas 画布
        //决定画的样式风格(大小，颜色) paint 画笔
        Paint paint = new Paint();
        paint.setTextSize(80);
        float baseline = 100;
        float x = getWidth()/2;
        canvas.drawText(mText,x,baseline,paint);
        //绘制辅助线
        drawCenterLineX(canvas);
        drawCenterLineY(canvas);
        //绘制到中间
        paint.setTextAlign(Paint.Align.CENTER);//第一种方法
        canvas.drawText(mText,x,baseline+paint.getFontSpacing(),paint);
        //绘制到右边
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(mText,x,baseline+paint.getFontSpacing()*2,paint);

        //第二种文字绘制到x中心
        drawCenterText(canvas);
        drawCenterText2(canvas);

    }

    //第二种文字绘制到x中心，第一层
    private void drawCenterText(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        //测量文字
        canvas.save();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = fontMetrics.descent - fontMetrics.ascent;
        float baseline = getHeight()/2  - (fontMetrics.descent +fontMetrics.ascent)/2;
        float width = paint.measureText(mText);
        float left = getWidth()/2 - width/2;
        canvas.drawText(mText,left,baseline,paint);
        canvas.restore();
    }

    //第二层
    private void drawCenterText2(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        //测量文字
        canvas.save();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = getHeight()/2  - (fontMetrics.descent +fontMetrics.ascent)/2;
        float width = paint.measureText(mText);
        float left = getWidth()/2 - width/2;

        float right = left + width*mPercent;
        Rect rect = new Rect((int)left,0,(int)right,getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText,left,baseline,paint);
        canvas.restore();
    }


    private void drawCenterLineX(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(getWidth()/2,0,getWidth()/2,getHeight(),paint);
    }

    private void drawCenterLineY(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,paint);
    }


}
