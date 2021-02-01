package com.zero.leakcanarydemo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import leakcanary.AppWatcher
import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.LeakCanary
import leakcanary.OnHeapAnalyzedListener
import shark.AndroidReferenceMatchers
import shark.HeapAnalysis


const val TAG = "Zero"

object MyActivityLifeCycleCallBack : Application.ActivityLifecycleCallbacks{
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(TAG,"${activity.javaClass.simpleName} onDestroyed()")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG,"${activity.javaClass.simpleName} onCreated()")
    }

    override fun onActivityResumed(activity: Activity) {
    }

}

class LeakUploader : OnHeapAnalyzedListener {
    private val defaultListener = DefaultOnHeapAnalyzedListener.create()
    override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {
        Log.i(TAG,"${heapAnalysis.javaClass.simpleName} onHeapAnalyzed()")
        defaultListener.onHeapAnalyzed(heapAnalysis)
    }
}
class DebugExampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
//        LeakCanary.config = LeakCanary.config.copy(
//            onHeapAnalyzedListener = LeakUploader()
//        )
//
//
        registerActivityLifecycleCallbacks(MyActivityLifeCycleCallBack)

//
//        LeakCanary.config = LeakCanary.config.copy(
//            referenceMatchers = AndroidReferenceMatchers.appDefaults +
//                    AndroidReferenceMatchers.staticFieldLeak(
//                        "com.samsing.SomeSingleton",
//                        "sContext"
//                    )
//        )
    }


}