package com.zero.toutiaodemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class TextMeasureView0 extends View {
    private static final String TAG = "Zero";
    private String mText;

    public TextMeasureView0(final Context context) {
        super(context);
        init();
    }

    public TextMeasureView0(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextMeasureView0(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        //在0，0位置绘制
        drawText0(canvas);
        drawText1(canvas);
        drawText2(canvas);
    }

    private void drawText0(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setTextSize(sp2px(80));
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        canvas.drawText(mText,0,0,paint);
    }

    private void drawText1(final Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2px(80));
        float baseline = paint.getFontSpacing();
        canvas.drawText(mText,0,baseline,paint);
    }

    private void drawText2(final Canvas canvas){//从基准线开始绘制
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2px(80));
        float height = getMeasuredHeight();
        Log.i("Zero","height: " + height + ", half: " + height/2);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = height/2 - (fontMetrics.descent + fontMetrics.ascent + fontMetrics.leading)/2;
        Log.i("Zero","fontMetrics.top " + fontMetrics.top +", fontMetrics.ascent " + fontMetrics.ascent
                +", fontMetrics.descent " + fontMetrics.descent
                +", fontMetrics.bottom " + fontMetrics.bottom);

        Log.i("Zero","height: " + (fontMetrics.bottom -fontMetrics.top + fontMetrics.leading));
        Log.i("Zero","height: " + (fontMetrics.descent -fontMetrics.ascent + fontMetrics.leading));
        Log.i("Zero","getFontSpacing: " +paint.getFontSpacing());

        Log.i("Zero","height1: " + (fontMetrics.descent +fontMetrics.ascent ));
        Rect rect = new Rect();
        paint.getTextBounds(mText,0, mText.length(),rect);
        Log.i("Zero","rect: " + rect);
        Log.i("Zero","rect.height: " + rect.height());
        Log.i("Zero","rect.b-t: " + (rect.bottom - rect.top));

        canvas.drawText(mText,(rect.width()+10),baseline,paint);
        paint.setColor(Color.BLUE);
        canvas.drawText(mText,(rect.width()+10)*2,height/2,paint);
        paint.setColor(Color.RED);
        float b = height/2 + (fontMetrics.bottom -fontMetrics.top + fontMetrics.leading)/2;
        canvas.drawText(mText,0,b,paint);

        //绘制基准线
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLUE);
        paint1.setStrokeWidth(5);
        canvas.drawLine(0,baseline,getMeasuredWidth(),baseline,paint1);
        paint1.setColor(Color.RED);
        canvas.drawLine(0,height/2,getMeasuredWidth(),height/2,paint1);
    }


    private void init() {
        mText = "ཁྱོད་བདེ་མོ།";//藏文
//        mText = "السلام عليكم";
        mText = "jJ";
    }

    static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    static int sp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
