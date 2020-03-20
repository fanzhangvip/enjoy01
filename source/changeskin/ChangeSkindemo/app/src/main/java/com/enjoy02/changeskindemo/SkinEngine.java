package com.enjoy02.changeskindemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.lang.reflect.Method;


public class SkinEngine {

    private final static SkinEngine instance = new SkinEngine();

    public static SkinEngine getInstance() {
        return instance;
    }

    private SkinEngine() {
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private Resources mOutResource;// TODO: 外部资源
    private Context mContext;
    private String mOutPkgName;// TODO: 外部资源包的packageName

    /**
     * TODO: 加载外部资源包
     */
    public void load(final String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        mOutPkgName = getOutPkgName(path);
        createResources(path);
    }

    private String getOutPkgName(final String path) {
        PackageManager mPm = mContext.getPackageManager();
        PackageInfo mInfo = mPm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        mOutPkgName = mInfo.packageName;
        return mOutPkgName;
    }

    private AssetManager createAssetManager(final String path) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            // TODO: 加载资源的核心原理
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createResources(final String path) {
        mOutResource = new Resources(createAssetManager(path), mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
    }

    public int getColor(int resId) {
        Log.i("Zero","resId: " + resId);
        try {
            if (mOutResource == null) {
                return resId;
            }
            String resName = mOutResource.getResourceEntryName(resId);//bg
            int outResId = mOutResource.getIdentifier(resName, "color", mOutPkgName);
            Log.i("Zero","outResId: " + outResId);
            if (outResId == 0) {
                return resId;
            }
            return mOutResource.getColor(outResId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }

    public Drawable getDrawable(int resId) {//获取图片
        Log.i("Zero","resId: " + resId);
        try {
            if (mOutResource == null) {
                return ContextCompat.getDrawable(mContext, resId);
            }
            String resName = mOutResource.getResourceEntryName(resId);
            int outResId = mOutResource.getIdentifier(resName, "drawable", mOutPkgName);
            Log.i("Zero","outResId: " + outResId);
            if (outResId == 0) {
                return ContextCompat.getDrawable(mContext, resId);
            }
            return mOutResource.getDrawable(outResId);
        } catch (Exception e) {
            e.printStackTrace();
            return ContextCompat.getDrawable(mContext, resId);
        }
    }

}
