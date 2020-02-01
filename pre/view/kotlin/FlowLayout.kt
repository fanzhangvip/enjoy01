package com.zero.flowlayoutdemo.kotlin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import java.util.*

class FlowLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0, defStyleRes: Int = 0)
    : ViewGroup(context, attrs, defStyle, defStyleRes){

    companion object{
        const val TAG = "Zero"

        private fun getModeString(mode: Int) = when(mode){
            MeasureSpec.UNSPECIFIED ->{"UNSPECIFIED"}
            MeasureSpec.EXACTLY ->{"EXACTLY"}
            MeasureSpec.AT_MOST ->{"AT_MOST"}
            else -> {"Unkown"}
        }
    }

    private val mVerticalSpacing = dp2px(8f)
    private val mHorizontalSpacing = dp2px(16f)

    private lateinit var lineViews: MutableList<View>
    private lateinit var views: MutableList<MutableList<View>>
    private lateinit var heights: MutableList<Int>

    private fun initMeasureParams(){
        lineViews = arrayListOf()
        views = arrayListOf()
        heights = arrayListOf()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //        具体的measure 思路
//        1. viewgroup  开始测量自身的尺寸
//        2. 为每个child计算测量限制信息(MeasureSpec) = 限制Mode（高两位） + size(30) 32位 int 二进制 01
//        3. 把上面的限制信息 传递给每个child child 知道如何测量自己了child.measure()发指令 -》 onMeasure
//        4. child测量完毕后 viewgroup获取每个子View测量后的大小
//        5. viewgroup结合自身的情况 计算自己的大小
//        6. 保存自身的大小
        initMeasureParams()
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        //记录当前行的宽度和高度
        //记录当前行的宽度和高度
        var lineWidthUsed = 0 // 宽度是当前行子view的宽度之和

        var lineHeight = 0 // 高度是当前行所有子View中高度的最大值


        //整个流式布局的宽度和高度
        //整个流式布局的宽度和高度
        var wantedWidth = 0 //所有行中宽度的最大值

        var wantedHeight = 0 // 所以行的高度的累加

        val childCount: Int = childCount
        for (i in 0 until childCount) { //获取子View
            val childView: View = this.getChildAt(i)
            val childLayoutParams = childView.layoutParams
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                            childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                            childLayoutParams.height))
            val childMeasureWidth = childView.measuredWidth
            val childMeasureHeight = childView.measuredHeight
            if (lineWidthUsed + childMeasureWidth + mHorizontalSpacing > selfWidth) { //换行
                views.add(lineViews)
                heights.add(lineHeight)
                wantedWidth = wantedWidth.coerceAtLeast((lineWidthUsed + mHorizontalSpacing).toInt())
                wantedHeight += (lineHeight + mHorizontalSpacing).toInt()
                lineViews = ArrayList() //创建新的一行
                lineWidthUsed = 0
                lineHeight = 0
            }
            lineViews.add(childView)
            lineWidthUsed = (lineWidthUsed + childMeasureWidth + mHorizontalSpacing).toInt()
            lineHeight = Math.max(lineHeight, childMeasureHeight) //取当前行中高度最大的作为当前行高
            //如果当前childView是最后一行的最后一个
            if (i == childCount - 1) { //最后一行
                heights.add(lineHeight)
                views.add(lineViews)
                wantedWidth = Math.max(wantedWidth, lineWidthUsed)
                wantedHeight += lineHeight
            }
        }

        //确定流式布局自身最终的宽高
        //确定流式布局自身最终的宽高
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        Log.i(TAG, "onMeasure: widthMode= " + getModeString(widthMode) + ", heightMode= " + getModeString(heightMode))

        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else wantedWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else wantedHeight
        Log.i(TAG, "onMeasure: selfWidth=$selfWidth , wantedWidth=$wantedWidth , realWidth=$realWidth,realHeight=$realHeight")

        //保存FlowLayout最终宽高
        //保存FlowLayout最终宽高
        setMeasuredDimension(realWidth, realHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val lineCount = views.size
        var currX = left
        Log.i(TAG, "onLayout0: $lineCount")
        Log.i(TAG, "onLayout1: $left")
//        for (i in 0..100) {
//            Log.e(TAG, "onLayout: dp=" + i + " , px=" + dp2px(i.toFloat()))
//        }
        var currY = 0

        for (i in 0 until lineCount) { //大循环，所有的子View 一行一行的布局
            val lineViews: List<View> = views[i] //取出一行
            val lineHeight = heights[i] // 取出这一行的高度值
            //遍历当前行的子View
            val lineViewCount = lineViews.size
            Log.i(TAG, "for j: $lineViewCount")
            for (j in 0 until lineViewCount) { //布局当前行的每一个view
                val child = lineViews[j]
                val left = currX
                val top = currY
                val right = left + child.measuredWidth
                val bottom = top + child.measuredHeight
                child.layout(left, top, right, bottom)
                //确定下一个view的left
                currX = (left + child.measuredWidth + mHorizontalSpacing).toInt()
            }
            currY = (currY + lineHeight + mVerticalSpacing).toInt()
            currX = 0
        }
    }
}