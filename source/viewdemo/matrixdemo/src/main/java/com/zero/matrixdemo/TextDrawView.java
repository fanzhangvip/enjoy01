package com.zero.matrixdemo;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class TextDrawView extends View {
    
    private float mDensity;
    private Paint mPaint;
    public TextDrawView(Context context) {
        super(context);
        init(context);

    }

    public TextDrawView(Context context, AttributeSet set) {
        super(context, set);
        init(context);

    }
    private void init(Context context){
        
        DisplayMetrics displayMetrics = new DisplayMetrics();   
        displayMetrics = context.getResources().getDisplayMetrics();   
        mDensity = displayMetrics.density;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        
        mPaint.setColor(Color.RED); // 设置画笔颜色

        mPaint.setStrokeWidth(5);// 设置画笔宽度
        mPaint.setAntiAlias(true);
        // 指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setTextSize(80);// 设置文字大小
        mPaint.setStyle(Style.FILL);// 绘图样式，设置为填充

        float[] pos = new float[] { 80, 100, 80, 200, 80, 300, 80, 400, 
                25 * mDensity, 30 * mDensity,
                25 * mDensity, 60 * mDensity,
                25 * mDensity, 90 * mDensity,
                25 * mDensity, 120 * mDensity,};
        canvas.drawPosText("画图示例", pos, mPaint);// 两个构造函数

        Path lineTextPath = new Path();
        lineTextPath.moveTo(65 * mDensity, 5 * mDensity);
        lineTextPath.lineTo(65 * mDensity, 200 * mDensity);
        canvas.drawPath(lineTextPath, mPaint);
        canvas.drawTextOnPath("画图示例string1", lineTextPath, 0, 11, mPaint);

        canvas.save();
        canvas.translate(100 * mDensity, 5 * mDensity);
        canvas.rotate(90);
        canvas.drawText("画图示例string2", 0, 11, 0, 0, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);// 设置阴影
        canvas.drawText("画图示例string3", 0, 11, 140 * mDensity, 35 *mDensity, mPaint);// 对文字有效
        canvas.drawCircle(200 * mDensity, 150 * mDensity, 40 * mDensity, mPaint);// 阴影对图形无效
        canvas.restore();

        for (int i = 0; i < 6; i++) {
            mPaint.setTextScaleX(0.4f + 0.3f * i);
            canvas.drawText("画", 0, 1, 
                    5* mDensity + 50 * mDensity * i, 250 * mDensity, mPaint);
        }

        //沿着任意路径
        Path bSplinePath = new Path();
        bSplinePath.moveTo(5 * mDensity, 320 * mDensity);
        bSplinePath.cubicTo(80 * mDensity, 260 * mDensity,
                200 * mDensity, 480 * mDensity,
                350 * mDensity,350 * mDensity);
        mPaint.setStyle(Style.STROKE);
        // 先画出这两个路径
        canvas.drawPath(bSplinePath, mPaint);
        // 依据路径写出文字
        String text = "风萧萧兮易水寒，壮士一去兮不复返";
        mPaint.setColor(Color.GRAY);
        mPaint.setTextScaleX(1.0f);
        mPaint.setTextSize(20 * mDensity);
        canvas.drawTextOnPath(text, bSplinePath, 0, 15, mPaint);


    }

}
