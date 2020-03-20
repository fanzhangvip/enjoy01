package com.enjoy02.changeskindemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class ZeroView extends View {


    public ZeroView(Context context) {
        this(context,null);
    }

    public ZeroView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public ZeroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
