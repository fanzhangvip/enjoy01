package com.zero.toutiaodemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;


public class RankIdView extends View {

    private Paint bgPaint;
    private Paint txtPaint;
    private Path path;
    private int widthSize;
    private int heightSize;
    private int xCenter;
    private int yCenter;
    private String string = "0";

    private float rankIdTextSize;
    private Context context;
    private Paint.FontMetrics fm;

    public RankIdView(Context context) {
        this(context, null);
    }

    public RankIdView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RankIdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        xCenter = widthSize / 2;
        yCenter = heightSize / 2;
    }

    static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    static int sp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics());
    }
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RankIdView,
//                defStyleAttr, 0);
//        try {
//            rankIdTextSize = array.getDimension(R.styleable.RankIdView_rankIdTextSize, 20);
//        } finally {
//            array.recycle();
//        }

        rankIdTextSize = sp2px(20);

        path = new Path();
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(Color.WHITE);
        txtPaint.setTextSize(rankIdTextSize);

        fm = txtPaint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(0, 0);
        path.lineTo(0, heightSize);
        path.lineTo(widthSize - 40, heightSize);
        path.lineTo(widthSize, 0);
        path.close();
        canvas.drawPath(path, bgPaint);
        float textWidth = txtPaint.measureText(string, 0, string.length());
        float offset = (fm.ascent + fm.descent) / 2;
        Log.i("33333333333", "descent = " + fm.descent + " -------- ascent = " + fm.ascent);
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/F-blackshark-regular.ttf");
//        txtPaint.setTypeface(typeface);
        canvas.drawText(string, xCenter - textWidth / 2, yCenter - offset, txtPaint);
        canvas.drawLine(0, yCenter, widthSize, yCenter, txtPaint);
    }

    public void setText(String num) {
        this.string = num;
        postInvalidate();
    }

    public void setBgColor(int color) {
        bgPaint.setColor(color);
        postInvalidate();
    }
}
