package com.example.androidperformancetest.monitor.memory;


import com.example.androidperformancetest.utils.GLog;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;

public class LeakCanaryService extends DisplayLeakService {
    private final String TAG = "LeakCanaryService";
    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        GLog.d(TAG,"afterDefaultHandling:" + leakInfo);
        //super.afterDefaultHandling(heapDump, result, leakInfo);
    }
}
