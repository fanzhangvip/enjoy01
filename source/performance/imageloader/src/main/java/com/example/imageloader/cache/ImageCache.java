package com.example.imageloader.cache;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.example.imageloader.config.BitmapConfig;
import com.example.imageloader.config.MiniImageLoaderConfig;

import java.io.InputStream;



public class ImageCache {
    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;
    public ImageCache(Context ctx){
        mMemoryCache = new MemoryCache(0.4f);
        mDiskCache = new DiskCache(ctx, MiniImageLoaderConfig.DEFAULT_DISK_CACHE_SIZE);
    }
    public Bitmap getBitmap(String url){
        Bitmap bitmap = mMemoryCache.getBitmap(url);

        return bitmap;
    }
    public void addToCache(String url,Bitmap bitmap){
        mMemoryCache.addBitmapToCache(url,bitmap);
    }
    public Bitmap getBitmapFromDisk(String url, BitmapConfig config){
        return mDiskCache.getBitmapFromDiskCache(url,config);
    }
    public void saveToDisk(String url,InputStream is){
        mDiskCache.saveToDisk(url,is);
    }
    public Bitmap getBitmapFromReusableSet(BitmapFactory.Options options){
        return mMemoryCache.getBitmapFromReusableSet(options);
    }
}