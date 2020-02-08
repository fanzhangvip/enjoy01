package com.zero.toutiaodemo.kotlin

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zero.toutiaodemo.R

class ColorChangeTextView2
@JvmOverloads
constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0, defStyleRes: Int = 0)
    : View(context, attrs, defStyle, defStyleRes) {

    private var mPaint = Paint()//绘制文字
    private var mLinePaint = Paint()//辅助框

    private var mTextBound = Rect()
    private var mTextWidth = 0
    private var mTextHeight = 0

    private var mTextStartX = 0

    companion object{
        const val TAG = "Zero"

        const val DIRECTION_LEFT = 0
        const val DIRECTION_RIGHT = 1
        const val DIRECTION_TOP = 2
        const val DIRECTION_BOTTOM = 3
    }


    var text: String = "享学课堂"//属性  = java成员变量 + get set
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

    var direction: Int = DIRECTION_LEFT

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

    init {
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = sp2px(3f)
        mLinePaint.color = Color.GREEN
    }




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //确定自身的宽高
        //1. 测量出文字的大小
        measureText()
        //2. 确定自身的大小
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        //3. 保存测量的尺寸 setMeasuredDimension()
        setMeasuredDimension(width,height)
        mTextStartX = measuredWidth/2 - mTextWidth/2

    }

    private fun measureWidth(measureSpec: Int): Int{
        var result = 0

        val mode =  MeasureSpec.getMode(measureSpec)//父view的mode
        val size = MeasureSpec.getSize(measureSpec)//父view的size

        when(mode){
            MeasureSpec.EXACTLY ->{
                result = size
            }
            MeasureSpec.AT_MOST,MeasureSpec.UNSPECIFIED ->{
                result = (mTextWidth +.5f).toInt() + paddingLeft + paddingRight
            }
        }

        result = if(mode == MeasureSpec.AT_MOST) result.coerceAtMost(size) else result
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
        result = if (mode == MeasureSpec.AT_MOST) result.coerceAtMost(size) else result
        return result
    }

    private fun measureText(){
        mPaint.getTextBounds(text,0,text.length,mTextBound)
        mTextWidth = (mPaint.measureText(text) + .5f).toInt()

//        val fontMetrics = Paint.FontMetrics()
//        mPaint.getFontMetrics(fontMetrics)
//
//        mTextHeight = (fontMetrics.descent - fontMetrics.ascent + .5f).toInt()

    }


    override fun onDraw(canvas: Canvas?) {
        //可空对象 ？
        //不可空

        when(direction){
            DIRECTION_LEFT ->{
                canvas?.apply {
                    //绘制颜色改变的层
                    save()
                    mPaint.color = textColorChange
                    clipRect(mTextStartX,0,(mTextStartX + progress * mTextWidth).toInt(),measuredHeight)
                    drawText(text,mTextStartX.toFloat(),measuredHeight/2 - (mPaint.descent()/2 + mPaint.ascent()/2)
                            ,mPaint)
                    restore()

                    //绘制底色层
                    save()
                    mPaint.color = textColor
                    clipRect((mTextStartX + progress * mTextWidth).toInt(),0,mTextStartX + mTextWidth,measuredHeight)
                    drawText(text,mTextStartX.toFloat(),measuredHeight/2 - (mPaint.descent()/2 + mPaint.ascent()/2)
                            ,mPaint)
                    restore()

                }


            }
            DIRECTION_RIGHT ->{

            }
            DIRECTION_TOP ->{

            }
            DIRECTION_BOTTOM ->{

            }
        }
    }

}