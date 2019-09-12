package com.example.androidperformancetest;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Trace;

import androidx.core.util.DebugUtils;

import com.example.androidperformancetest.business.contact.ContactsManager;
import com.example.androidperformancetest.database.DBManager;
import com.example.androidperformancetest.monitor.time.TimeMonitorConfig;
import com.example.androidperformancetest.monitor.time.TimeMonitorManager;
import com.example.imageloader.MiniImageLoader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class GmfApplication extends Application {

    private static Context mContext = null;
    private static RefWatcher mRefWatcher = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
        TimeMonitorManager.getInstance().resetTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START);
        //初始化图片引擎
        MiniImageLoader.progrem(mContext);
        MiniImageLoader.getInstance();
        GmfSharedPreferences.progrem(mContext);
        ContactsManager.programStart(mContext);
    }
    public static  Context getmContext(){
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher  = LeakCanary.install(this);

        InitModule();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recodingTimeTag("ApplicationCreate");
    }
    public static RefWatcher getRefWatcher(){
        return mRefWatcher;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public static Context getContext(){
        return mContext;
    }

    private void InitModule(){
        DBManager.InitDB(mContext);
        CrashHandler crashHandler = new CrashHandler();
        crashHandler.init(this);
    }
}
