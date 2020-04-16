package com.zero.giflib1.gif

import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.SystemClock


class GifDrawable(private val mGifFrame: GifFrame) : Drawable(),
    Animatable, Runnable {
    private val mPaint: Paint
    //1. 首先Java准备一个Bitmap传给C++
    //2. gif 解压出来之后每一帧(Screen) 填充 成Bitmap
    //3. 成Bitmap 绘制到Canvas
    //4. Bitmap是要Java端初始化出来的
    private val mBitmap: Bitmap //成员属性 Java成员变量 + get + set
    private val srcRect //gif的矩形区域
            : Rect
    //gif在C++解码  width,height
    val width: Int = mGifFrame.width
    val height: Int = mGifFrame.height
    private var runFlag = false
    private var showTime: Long = 0
    private var curTime //当前帧要显示的时间 C++ 获取的
            : Long = 0
    private var frameCount: Int = mGifFrame.frameCount
    private var frameIndex: Int
        get() {
            field++
            return if (field < frameCount) field else 0.also { field = it }
        }


    init {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mPaint = Paint()
        mPaint.isFilterBitmap = true
        frameIndex = 0
        srcRect = Rect(0, 0, width, height)
        mGifFrame.getFrame(mBitmap, frameIndex)
    }


    override fun draw(canvas: Canvas) { //绘制 把gif的每一帧绘制到canvas
        canvas.drawBitmap(mBitmap, srcRect, bounds, mPaint)
        showTime = SystemClock.uptimeMillis()
        if (isRunning) {
            scheduleSelf(this, showTime + curTime)
        }
    }

    override fun setAlpha(i: Int) {
        mPaint.alpha = i
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun start() {

        if (!isRunning) {
            runFlag = true
            scheduleSelf(this, SystemClock.uptimeMillis())
        }
    }

    override fun stop() {
        if (isRunning) {
            runFlag = false
            unscheduleSelf(this)
        }
    }

    override fun isRunning(): Boolean {
        return runFlag
    }

    override fun run() { //获取每一帧
        curTime = mGifFrame.getFrame(mBitmap, frameIndex)
        invalidateSelf()
    }

}