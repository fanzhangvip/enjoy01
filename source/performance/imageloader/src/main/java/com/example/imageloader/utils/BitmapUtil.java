package com.example.imageloader.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.example.imageloader.cache.ImageCache;

import java.io.InputStream;


public class BitmapUtil {
    static public Bitmap decodeSampledBitmapFromStream(
            InputStream is, BitmapFactory.Options options) {
        MLog.d("BitmapUtil", "mSampleSize:" + options.inSampleSize);
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static Bitmap decodeSampledBitmapFromStream(
            InputStream is, BitmapFactory.Options options, ImageCache cache) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            addInBitmapOptions(options, cache);
        }

        return BitmapFactory.decodeStream(is, null, options);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void addInBitmapOptions(BitmapFactory.Options options, ImageCache cache) {

        options.inMutable = true;
        if (cache != null) {
            Bitmap inBitmap = cache.getBitmapFromReusableSet(options);

            if (inBitmap != null) {
                options.inBitmap = inBitmap;
            }
        }
    }
}
