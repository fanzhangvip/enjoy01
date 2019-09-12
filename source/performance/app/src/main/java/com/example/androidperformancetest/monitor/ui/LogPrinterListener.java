package com.example.androidperformancetest.monitor.ui;


public interface LogPrinterListener {
    void onStartLoop();
    void onEndLoop(long starttime, long endtime, String loginfo, int level);
}
