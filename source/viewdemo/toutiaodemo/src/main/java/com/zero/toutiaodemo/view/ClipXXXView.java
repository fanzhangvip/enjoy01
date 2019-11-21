package com.zero.toutiaodemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ClipXXXView extends View {
    private static final String TAG = "Zero";
    private Paint mPaint;
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private Paint mTextPaint;

    public ClipXXXView(final Context context) {
        super(context);
        init(context);
    }

    public ClipXXXView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClipXXXView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ClipXXXView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(final Context context) {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.rgb(255,215,0));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);

        mScreenWidth = Utils.getScreenWidth(context);
        mScreenHeight = Utils.getScreenHeight(context);
        Log.i(TAG, "init: w= " + mScreenWidth + " h= " + mScreenHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heigthMode == MeasureSpec.UNSPECIFIED) {//ScrollView需要特殊处理
            setMeasuredDimension(widthSize, 2*mScreenHeight);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        //默认不剪切
        canvas.save();
        drawScene(canvas);
        canvas.restore();

        drawTitle(canvas,0,0,"NULL");

        //DIFFERENCE,以第一次剪裁的为基础，在此基础上擦除第二次剪裁的部分(裁出第一次操作，并擦除掉第二次操作)
        canvas.save();
        canvas.translate(600, 0);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.DIFFERENCE);
        drawScene(canvas);
        canvas.restore();
        drawTitle(canvas,600,0,"DIFFERENCE");

        //INTERSECT,交叉,保留第一次剪裁和第二次剪裁重叠的部分
        canvas.save();
        canvas.translate(0, 600);
        canvas.clipRect(0, 0, 300, 300);
        canvas.clipRect(200, 200, 500, 500, Region.Op.INTERSECT);
        drawScene(canvas);
        canvas.restore();
        drawTitle(canvas,0,600,"INTERSECT");

        //REPLACE，替换，用第二次剪裁替换第一次剪裁(相当于只做了第二次，丢弃了第一次)
        canvas.save();
        canvas.translate(600,600);
        //1. 第一次
//        private static void checkValidClipOp(@NonNull Region.Op op) {
//            if (sCompatiblityVersion >= Build.VERSION_CODES.P
//                    && op != Region.Op.INTERSECT && op != Region.Op.DIFFERENCE) {
//                throw new IllegalArgumentException(
//                        "Invalid Region.Op - only INTERSECT and DIFFERENCE are allowed");
//            }
//        }
        canvas.save();
        canvas.clipOutRect(0,0,300,300);
        drawScene(canvas);
        canvas.restore();
        canvas.save();
        canvas.clipRect(200,200,500,500);
        drawScene(canvas);
        canvas.restore();
//        canvas.clipRect(0,0,300,300);
//        canvas.clipRect(200,200,500,500,Region.Op.REPLACE);
//        //2.第二次操作
////      canvas.clipRect(200, 200, 500, 500);
////      canvas.clipRect(0, 0, 300, 300, Region.Op.REPLACE);
//        //3.第三种操作
////      canvas.clipRect(0, 0, 300, 300);
//        drawScene(canvas);
        canvas.restore();
        drawTitle(canvas,600,600,"REPLACE");

//        //REVERSE_DIFFERENCE,以第二次剪裁的为基础，在此基础上擦除第一次剪裁的部分(裁出第二次操作，并擦除掉第一次操作)
//        canvas.save();
//        canvas.translate(0, 1200);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.REVERSE_DIFFERENCE );
//        drawScene(canvas);
//        canvas.restore();
        drawTitle(canvas,0,1200,"REVERSE_DIFFERENCE");

//        //UNION,合并,即保留第一次剪裁和第二次剪裁的并集
//        canvas.save();
//        canvas.translate(600, 1200);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.UNION);
//        drawScene(canvas);
//        canvas.restore();
        drawTitle(canvas,600,1200,"UNION");

//        //XOR,异或操作，做出第一次剪裁，并且做出第二次剪裁，在此基础上擦除掉重叠的部分(留下两次操作不重叠的部分)
//        canvas.save();
//        canvas.translate(0, 1800);
//        canvas.clipRect(0, 0, 300, 300);
//        canvas.clipRect(200, 200, 500, 500, Region.Op.XOR);
//        drawScene(canvas);
//        canvas.restore();
        drawTitle(canvas,0,1800,"XOR");



    }

    private void drawTitle(Canvas canvas, float dx,float dy, final String title){
        //标题需要的高度
        canvas.save();
        canvas.translate(dx,dy);
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(title,0,title.length(),textRect);
        int offsety = (textRect.top + textRect.bottom);
        float offsetx = (textRect.left + textRect.right)/2;
        //绘制标题
        Rect rect = new Rect(0,0,500,500);
        float x1 = (rect.left + rect.right)/2;
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(title,x1 -offsetx,500-offsety,mTextPaint);
        canvas.drawLine(0,500-offsety,500,500-offsety,mTextPaint);
        mTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,mTextPaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas,Rect rect,String text){
        //如何把文字绘制在中心位置
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(150);
        //1.
        Rect bounds1 = new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds1);
        float offsetx1 = (bounds1.left + bounds1.right)/2;
        float offsety1 = (bounds1.top + bounds1.bottom)/2;

        //2.
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        float offsety2 = (fontMetrics.ascent + fontMetrics.descent)/2;

        float x1 = (rect.left + rect.right)/2;
        float y1 = (rect.top + rect.bottom)/2;
        canvas.drawText(text,  x1-offsetx1, y1-offsety1, mPaint);
    }

    private void drawScene(Canvas canvas) {
        //绘制场景背景
//        canvas.clipRect(0, 0, 500, 500);
//        canvas.drawColor(Color.GRAY);

        mPaint.setColor(Color.RED);
        Rect rect1 = new Rect(0,0,300,300);
        canvas.drawRect(rect1, mPaint);
        //绘制 1
        drawText(canvas,rect1,"1");

        mPaint.setColor(Color.GREEN);
        Rect rect2 = new Rect(200,200,500,500);
        canvas.drawRect(rect2, mPaint);
        //绘制 2
        drawText(canvas,rect2,"2");

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(200, 200, 300, 300, mPaint);
    }
}
