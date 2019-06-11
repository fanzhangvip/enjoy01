package com.enjoy.myviewdemo.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView01 extends View {

    private static final String TAG = "Zero";

    Paint mPaint = new Paint();

    public MyView01(Context context) {
        super(context);
    }

    public MyView01(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView01(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        Log.i(TAG, "onMeasure: widthSize= " + widthSize);
        Log.i(TAG, "onMeasure: widthMode= " + widthMode);

        Log.i(TAG, "onMeasure: heightMeasureSpec= " + MeasureSpec.getMode(heightMeasureSpec));

        if (heightMeasureSpec == 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(800, MeasureSpec.AT_MOST);
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.i(TAG, "onMeasure: heightSize= " + heightSize);
        Log.i(TAG, "onMeasure: heightMode= " + heightMode);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(800, 800);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1000, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, widthSize);
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSize, 4000);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Paint.setStyle(Style style) 设置绘制模式
//        Paint.setColor(int color) 设置颜色
//        Paint.setStrokeWidth(float width) 设置线条宽度
//        Paint.setTextSize(float textSize) 设置文字大小
//        Paint.setAntiAlias(boolean aa) 设置抗锯齿开关

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10f);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(300, 300, 200, mPaint);

//        canvas.drawColor(Color.parseColor("#88880000"));

        mPaint.setStrokeWidth(10);
        canvas.drawRect(new RectF(100, 600, 500, 1000), mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(new RectF(100, 600, 500, 1000), 0f, 360f, false, mPaint);

        mPaint.setColor(Color.GRAY);
        canvas.drawArc(100, 1100, 500, 1500, 0f, 270f, true, mPaint);

        mPaint.setColor(Color.MAGENTA);
        canvas.drawRect(new Rect(600, 100, 1000, 500), mPaint);

        mPaint.setColor(Color.LTGRAY);
        canvas.drawOval(new RectF(600, 600, 1000, 1000), mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRoundRect(new RectF(600, 1100, 1000, 1500), 100f, 100f, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawLine(100, 1600, 500, 2500, mPaint);

        mPaint.setStrokeWidth(50);
        canvas.drawPoint(600, 1600, mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(600, 1700, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(600, 1800, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(600, 1900, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GREEN);
        canvas.drawPoints(new float[]{500, 1900, 700, 2000, 800, 2100}, mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawPoints(new float[]{800, 2200, 900, 2300}, 0, 4, mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(1);
        canvas.drawLine(0, 2600, 1080, 2600, mPaint);
        canvas.drawLine(0, 2605, 1080, 2605, mPaint);


        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);

        float[] points = new float[]{
                100, 2700, 200, 2700
                , 150, 2700, 150, 2800
                , 100, 2800, 200, 2800
                , 210, 2700, 210, 2800
                , 210, 2700, 310, 2700
                , 310, 2700, 310, 2800
                , 210, 2800, 310, 2800};
        canvas.drawLines(points, mPaint);


        Path path = new Path();

//        mPaint.setStyle(Paint.Style.FILL);

        path.addArc(200, 2900, 400, 3100, -225, 225);
        path.arcTo(400, 2900, 600, 3100, -180, 225, false);
        path.lineTo(400, 3242);
        path.close();

        canvas.drawPath(path, mPaint);

//        canvas.drawArc(200, 2900, 400, 3100
//                , -225, 225, true, mPaint);
//
//        mPaint.setColor(Color.RED);
//        canvas.drawArc(400, 2900, 600, 3100,
//                -180, 225, true, mPaint);


//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(300, 2800, 300, 3300, mPaint);
//        canvas.drawLine(400, 2800, 400, 3300, mPaint);
//        canvas.drawLine(500, 2800, 500, 3300, mPaint);
//        canvas.drawLine(0,3000,1080,3000,mPaint);

          path.addCircle(200,3500,100, Path.Direction.CCW);
          canvas.drawPath(path,mPaint);


    }
}
