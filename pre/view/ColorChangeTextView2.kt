package com.zero.toutiaodemo.view.kotlin

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.IntDef
import com.zero.toutiaodemo.R
import com.zero.toutiaodemo.view.ColorChangeTextView1

class ColorChangeTextView2(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    companion object {
        const val TAG = "Zero"

        const val DIRECTION_LEFT = 0
        const val DIRECTION_RIGHT = 1
        const val DIRECTION_TOP = 2
        const val DIRECTION_BOTTOM = 3
    }

     var text: String = "享学课堂"
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }
    var textSize: Float = dp2px(30f)
        set(value) {
            field = value
            mPaint.textSize = value
            requestLayout()
            invalidate()
        }

    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            invalidate()
        }
    var textColorChange: Int = Color.RED
        set(value) {
            field = value
            invalidate()
        }

    private var progress: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLinePaint: Paint = Paint()
    private var mPaint: Paint = Paint()
    private var mTextBound: Rect = Rect()

    private var mTextWidth: Int = 0
    private var mTextHeight: Int = 0
    private var mTextStartX: Int = 0
    private var mTextStartY: Int = 0


    @IntDef(flag = true, value = [0, 1, 2, 3])
    annotation class Directions

    @Directions
    var direction: Int = DIRECTION_LEFT

    init {
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = sp2px(3f)
        mLinePaint.color = Color.GREEN
    }

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorChangeTextView1)
        text = ta.getString(R.styleable.ColorChangeTextView1_text) as String
        textSize = ta.getDimensionPixelSize(R.styleable.ColorChangeTextView1_text_size, textSize.toInt()).toFloat()
        textColor = ta.getColor(
                R.styleable.ColorChangeTextView1_text_color, textColor)
        textColorChange = ta.getColor(
                R.styleable.ColorChangeTextView1_text_color_change, textColorChange)
        progress = ta.getFloat(R.styleable.ColorChangeTextView1_progress, 0f)

        direction = ta
                .getInt(R.styleable.ColorChangeTextView1_direction, direction)

        ta.recycle()

        mPaint.textSize = textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //1. 先测量文字
        measureText()
        //2. 测量自身
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        //3. 保持测量尺寸
        setMeasuredDimension(width, height)

        mTextStartX = measuredWidth / 2 - mTextWidth / 2
        mTextStartY = measuredHeight / 2 - mTextHeight / 2
    }

    private fun measureText() {
        mPaint.getTextBounds(text, 0, text.length, mTextBound)
        Log.i(TAG, "mTextBound = $mTextBound")
        mTextWidth = (mPaint.measureText(text) + .5f).toInt()
        Log.i(TAG, "mTextWidth = $mTextWidth")
        val fontMetrics = Paint.FontMetrics()
        mPaint.getFontMetrics(fontMetrics)
        mTextHeight = (fontMetrics.descent - fontMetrics.ascent + .5f).toInt()
        Log.i(TAG, "mTextHeight = $mTextHeight")
    }

    private fun measureWidth(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> {
                Log.i(TAG, "measureWidth: EXACTLY")
                result = size
            }
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> result = (mTextWidth + .5f).toInt() + paddingLeft + paddingRight
        }
        //如果是AT_MOST,不能超过父布局的尺寸
        result = if (mode == MeasureSpec.AT_MOST) Math.min(result, size) else result
        return result
    }

    private fun measureHeight(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> {
                Log.i(TAG, "measureWidth: EXACTLY")
                result = size
            }
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> result = (mTextBound.height() + .5f).toInt() + paddingTop + paddingBottom
        }
        //如果是AT_MOST,不能超过父布局的尺寸
        result = if (mode == MeasureSpec.AT_MOST) Math.min(result, size) else result
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when (direction) {
            ColorChangeTextView1.DIRECTION_LEFT -> {
                Log.i(TAG, "DIRECTION_LEFT = " + ColorChangeTextView1.DIRECTION_LEFT)
                //1 先绘制改变的颜色的文字  ？
                mPaint.color = textColorChange
                //起始
//绘制的终点
                canvas!!.save()
                canvas.clipRect(mTextStartX, 0, (mTextStartX + progress * mTextWidth) as Int, measuredHeight)
                canvas.drawRect(canvas.clipBounds, mLinePaint)
                canvas.drawText(text, mTextStartX
                        .toFloat(), measuredHeight / 2 - (mPaint.descent() / 2 + mPaint.ascent() / 2), mPaint)
                canvas.restore()
                //2.绘制没有改变 底色
                mPaint.color = textColor
                canvas.save()
                canvas.clipRect((mTextStartX + progress * mTextWidth) as Int, 0, mTextStartX + mTextWidth, measuredHeight)
                canvas.drawText(text, mTextStartX.toFloat(), measuredHeight / 2 - (mPaint.descent() / 2 + mPaint.ascent() / 2), mPaint)
                canvas.restore()
            }
            ColorChangeTextView1.DIRECTION_RIGHT -> {
                Log.i(TAG, "DIRECTION_RIGHT = " + ColorChangeTextView1.DIRECTION_RIGHT)
                //先绘制改变的颜色
//                canvas.drawRect((int) (mTextStartX + (1 - mProgress) * mTextWidth), 0, mTextStartX + mTextWidth, getMeasuredHeight(), mLinePaint);
                drawTextHorizontal(canvas!!, textColorChange,
                        (mTextStartX + (1 - progress) * mTextWidth) as Int, mTextStartX + mTextWidth)
                //后绘制没改变的
                drawTextHorizontal(canvas, textColor, mTextStartX,
                        (mTextStartX + (1 - progress) * mTextWidth) as Int)
            }
            ColorChangeTextView1.DIRECTION_TOP -> {
                Log.i(TAG, "DIRECTION_TOP = " + ColorChangeTextView1.DIRECTION_TOP)
                //先绘制改变的颜色
                drawTextVertical(canvas!!, textColorChange, mTextStartY,
                        (mTextStartY + progress * mTextHeight) as Int)
                //后绘制没改变的
                drawTextVertical(canvas, textColor,
                        (mTextStartY + progress * mTextHeight) as Int, mTextStartY + mTextHeight)
            }
            ColorChangeTextView1.DIRECTION_BOTTOM -> {
                Log.i(TAG, "DIRECTION_BOTTOM = " + ColorChangeTextView1.DIRECTION_BOTTOM)
                //先绘制改变的颜色
                drawTextVertical(canvas!!, textColorChange,
                        (mTextStartY + (1 - progress) * mTextHeight) as Int, mTextStartY + mTextHeight)
                //后绘制没改变的
                drawTextVertical(canvas, textColor, mTextStartY,
                        (mTextStartY + (1 - progress) * mTextHeight) as Int)
            }
            else -> {
            }
        }
    }

    private fun drawTextHorizontal(canvas: Canvas, color: Int, startX: Int, endX: Int) {
        mPaint.color = color
        canvas.save()
        canvas.clipRect(startX, 0, endX, measuredHeight)
        canvas.drawText(text, mTextStartX.toFloat(), measuredHeight / 2
                - (mPaint.descent() + mPaint.ascent()) / 2, mPaint)
        canvas.restore()
    }

    private fun drawTextVertical(canvas: Canvas, color: Int, startY: Int, endY: Int) {
        mPaint.color = color
        canvas.save()
        canvas.clipRect(0, startY, measuredWidth, endY)
        canvas.drawText(text, mTextStartX.toFloat(), measuredHeight / 2
                - (mPaint.descent() + mPaint.ascent()) / 2, mPaint)
        canvas.restore()
    }


}