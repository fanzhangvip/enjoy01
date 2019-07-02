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
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class TextDrawView extends View {

    private final int mRadius = Utils.dp2px(100);
    private final int mCy = Utils.dp2px(550);
    private float mDensity;
    private Paint mPaint;

    private String text = "澳大利亚曾质疑过日本科研捕鲸的真实性。2010年，澳大利亚政府曾向海牙国际法院提起诉讼，控告日本在南冰洋的“科研”捕鲸活动实则是商业捕鲸。2014年，国际法院对此作出终审裁决，认定日本“出于科研目的”的捕鲸理由不成立，其捕鲸行为违背了《国际捕鲸管制公约》。日本表示尊重国际法院的裁决，并有所收敛了一段时间，但捕鲸活动仍未终止。2018年9月，在IWC的巴西峰会上，日本重提恢复商业捕鲸的诉求，但又一次遭到委员会的否决。这被视为日本最终退出该组织的直接原因被“科研”捕杀的鲸鱼，是如何被送上餐桌的？以科研名义被捕杀的鲸鱼，最后被输送到日本国内，满足人们的口腹之欲。负责执行这一系列动作的是一个名为日本鲸类研究所的机构，其上属机构是日本水产厅。日本鲸类研究所对鲸鱼肉有一个有趣的称呼：科研调查的副产物。他们称，根据《国际捕鲸规则公约》第8条的规定，调查后的鲸鱼体应被尽可能充分地利用。因而在鲸鱼被捕捞到渔船上并完成了对其体型、皮脂、胃内容物等款项的检测后，鲸体即会被拆解，用于鲸肉消费品的生产。当渔船抵达日本后，一块块的鲸肉会被分送给日本各级消费市场，或是以远低于市场价的价格出售给各地政府、供应于日本小学生的午餐中。";

    float[] curWidth = new float[1];

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


        mPaint.reset();
        canvas.drawLine(0,Utils.dp2px(420),getMeasuredWidth(),Utils.dp2px(420),mPaint);

        mPaint.setStyle(Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(Utils.dp2px(15));
        canvas.drawCircle(getWidth()/2, mCy, mRadius,mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth()/2 - mRadius,mCy - mRadius,getWidth()/2 + mRadius,
                mCy + mRadius,-90,225,false ,mPaint);

        mPaint.setStyle(Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(Utils.dp2px(50));

        Paint paintLine = new Paint();//反面教材
        paintLine.setStyle(Style.STROKE);
        canvas.drawLine(0,mCy,getWidth(),mCy,paintLine);

        Rect rect = new Rect();
        mPaint.getTextBounds("fgab",0,4,rect);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        float offset = (fontMetrics.ascent + fontMetrics.descent)/2;
//        float offset = (rect.top + rect.bottom)/2;
        canvas.drawText("fgaa",getWidth()/2,mCy - offset,mPaint);

        //文字绘制2
        mPaint.setTextSize(Utils.dp2px(150));
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect rect1 = new Rect();
        mPaint.getTextBounds("aaaa",0,4,rect1);
        canvas.drawText("aaaa",0- rect1.left,Utils.dp2px(460),mPaint);

        mPaint.setTextSize(Utils.dp2px(15));
        canvas.drawText("aaaa",0,Utils.dp2px(460) + mPaint.getFontSpacing(),mPaint);

        //int index = mPaint.breakText("",true,getWidth(),curWidth);
//        StaticLayout staticLayout = new StaticLayout(text,new TextPaint(),getWidth(), Layout.Alignment.ALIGN_NORMAL,1,0,false);



    }

}
