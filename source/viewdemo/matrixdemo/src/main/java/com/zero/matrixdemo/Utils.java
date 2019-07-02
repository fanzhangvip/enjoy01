package com.zero.matrixdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

public class Utils {

    public static int dp2px(int dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getBitmapByWidth(Resources resources,int resId,int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inJustDecodeBounds = false;
        options.inTargetDensity = width;
        return  BitmapFactory.decodeResource(resources,resId,options);
    }
}
