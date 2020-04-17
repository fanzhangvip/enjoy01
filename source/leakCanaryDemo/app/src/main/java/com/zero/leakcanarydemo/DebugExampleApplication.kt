package com.zero.leakcanarydemo

import android.app.Application
import leakcanary.AppWatcher
import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.LeakCanary
import leakcanary.OnHeapAnalyzedListener
import shark.AndroidReferenceMatchers
import shark.HeapAnalysis

class LeakUploader : OnHeapAnalyzedListener {

    private val defaultListener = DefaultOnHeapAnalyzedListener.create()

    override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {
        TODO("Upload heap analysis to server")

        // Delegate to default behavior (notification and saving result)
        defaultListener.onHeapAnalyzed(heapAnalysis)
    }
}

class DebugExampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
//        AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = false)
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