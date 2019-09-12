package com.example.uiperflib;

import android.os.Looper;
import android.util.Log;

public class UIPerfMonitor  implements LogPrinter.LogPrinterListener {

    private static final String TAG = "Zero";

    private static UIPerfMonitor M_INSTANCE = null;
    private LogPrinter mLogPrinter;

    private int monitorState = LogPrinter.LogPrinterConfig.UI_PERF_MONITER_STOP;

    public synchronized static UIPerfMonitor getInstance(){
        synchronized (UIPerfMonitor.class){
            if(null == M_INSTANCE){
                M_INSTANCE = new UIPerfMonitor();
            }
            return M_INSTANCE;
        }
    }

    private UIPerfMonitor(){
        mLogPrinter = new LogPrinter(this);
    }

    public void startMonitor() {
        Looper.getMainLooper().setMessageLogging(mLogPrinter);
        monitorState =  LogPrinter.LogPrinterConfig.UI_PERF_MONITER_START;
    }

    public void stopMonitor() {
        Looper.getMainLooper().setMessageLogging(null);
        monitorState =  LogPrinter.LogPrinterConfig.UI_PERF_MONITER_STOP;
    }

    public boolean isMonitoring() {
        return monitorState ==  LogPrinter.LogPrinterConfig.UI_PERF_MONITER_START;
    }

    @Override
    public void onStartLoop() {
        Log.i(TAG, "onStartLoop: ");
    }

    @Override
    public void onEndLoop(String loginfo, int level) {
        Log.i(TAG, "onEndLoop: " + loginfo + ", level= " + level);
    }
}
