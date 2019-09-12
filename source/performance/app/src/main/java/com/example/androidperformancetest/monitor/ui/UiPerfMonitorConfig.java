package com.example.androidperformancetest.monitor.ui;

import android.os.Environment;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface UiPerfMonitorConfig {

    //time level
    int TIME_WARNING_LEVEL_1 = 100;   //
    int TIME_WARNING_LEVEL_2 = 300;//
    //
    int UI_PERF_LEVEL_0 = 0;
    int UI_PERF_LEVEL_1 = 1;
    int UI_PERF_LEVEL_2 = 2;
    //
    @IntDef({UI_PERF_LEVEL_0, UI_PERF_LEVEL_1,UI_PERF_LEVEL_2})
    @Retention(RetentionPolicy.SOURCE)
    @interface PER_LEVEL {
    }
    int UI_PERF_MONITER_START = 0x01;
    int UI_PERF_MONITER_STOP = 0x01 << 1;

    String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + "/Performance";
    String FILENAME = "UiMonitorLog";
}
