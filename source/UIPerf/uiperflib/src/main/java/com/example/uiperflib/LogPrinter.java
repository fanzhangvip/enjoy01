package com.example.uiperflib;

import android.os.Environment;
import android.util.Log;
import android.util.Printer;

import androidx.core.os.EnvironmentCompat;

public class LogPrinter implements Printer {

    private static final String TAG = "Zero";
    private long startTime = 0;

    interface LogPrinterListener{
        void onStartLoop();
        void onEndLoop(String loginfo, int level);
    }

    private LogPrinterListener logPrinterListener = null;

    public LogPrinter(LogPrinterListener  l){
        logPrinterListener = l;
    }

    interface LogPrinterConfig{
        int LEVEL_1 = 100;//定义卡顿报警阈值
        int LEVEL_2 = 300;//需要上报线程信息阈值

        int UI_PERF_LEVEL_0 = 0;
        int UI_PERF_LEVEL_1 = 1;

        int UI_PERF_MONITER_START = 0x01;
        int UI_PERF_MONITER_STOP = 0x01 << 1;

        String LOG_PATH = Environment.getExternalStorageDirectory().getPath() +"uiperf";

        String LOG_FILE_NAME = "UIPerf_log";
    }

    @Override
    public void println(String s) {
        Log.i(TAG, "println: " + s);
        if(startTime <= 0){
            startTime = System.currentTimeMillis();
            logPrinterListener.onStartLoop();
        }else{
            long time = System.currentTimeMillis() - startTime;
            Log.i(TAG, "dispatch handler time: " + time);
            exeuTime(s,time);
            startTime = 0;
        }
    }

    private void exeuTime(String loginfo,long time){
        int level = 0;
        if(time > LogPrinterConfig.LEVEL_2){
            Log.i(TAG, "Waring_Level_2:\r\n"+loginfo);
            level = LogPrinterConfig.UI_PERF_LEVEL_1;
        }else if(time > LogPrinterConfig.LEVEL_1){
            Log.i(TAG, "Waring_Level_1:\r\n" + loginfo);
            level = LogPrinterConfig.UI_PERF_LEVEL_0;
        }
        logPrinterListener.onEndLoop(loginfo,level);
    }

















}
