package com.zero.toutiaodemo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CustomView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0)
    : View(context,attributeSet,defStyleAttr) {


    init {
        Log.i("Zero","init....")
    }
}