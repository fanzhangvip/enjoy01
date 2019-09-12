package com.example.androidperformancetest.utils;

import android.os.Build;


public class Util4Phone {

    /**
     *
     * @Discription:TODO
     * @return
     */
    public static boolean isSupportAnimation() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }
}

