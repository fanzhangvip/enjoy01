package com.enjoy.skin.lib.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;


/**
 * @author 享学课堂 alvin  2464061231
 * @date 2018/6/8
 */

public class SkinResources {

    private volatile static SkinResources instance;


    private String mSkinPkgName;
    private boolean isDefaultSkin = true;

    // app原始的resource
    private Resources mAppResources;
    // 皮肤包的resource
    private Resources mSkinResources;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }

    /**
     * 通过原始app中的resId 获取其对应的resource中的 名字，类型，从而
     * 根据名字和类型获取到对应的 皮肤包中的信息
     *
     * @param resId
     * @return
     */
    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        //在皮肤包中不一定就是 当前程序的 id
        //获取对应id 在当前的名称 colorPrimary
        String resName = mAppResources.getResourceEntryName(resId);
        String resType = mAppResources.getResourceTypeName(resId);
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
        return skinId;
    }

    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        //通过 app的resource 获取id 对应的 资源名 与 资源类型
        //找到 皮肤包 匹配 的 资源名资源类型 的 皮肤包的 资源 ID
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if ("color".equals(resourceTypeName)) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

}
