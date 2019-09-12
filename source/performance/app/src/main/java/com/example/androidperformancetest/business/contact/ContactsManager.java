package com.example.androidperformancetest.business.contact;

import android.content.Context;


import com.example.androidperformancetest.database.tables.ContactInfoTable;
import com.example.androidperformancetest.monitor.time.TimeMonitorConfig;
import com.example.androidperformancetest.monitor.time.TimeMonitorManager;
import com.example.androidperformancetest.utils.GLog;

import java.io.IOException;
import java.util.ArrayList;


public class ContactsManager {
    private static ContactsManager mContactsManager = null;
    private static Context mContext = null;
    private ArrayList<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
    private ContactInfoTable mContactInfoTable = null;
    private final String TAG = "ContactsManager";

    public static void programStart(Context ctx) {
        mContext = ctx;
    }

    public static synchronized ContactsManager getInstance() {
        if (null == mContactsManager) {
            mContactsManager = new ContactsManager();
        }
        return mContactsManager;
    }

    public ContactsManager() {
        mContactInfoTable = new ContactInfoTable(mContext);
    }

    public ArrayList<ContactInfo> createTestContactInfo() {
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_SQLITE).startMoniter();
        mContactInfoTable.getSqliteDB().beginTransaction();
        try {
            for (int i = 1; i <= 10000; i++) {
                ContactInfo info = new ContactInfo(i, "姓名" + i, "1389832" + (0000 + i));
                contactInfos.add(info);
                boolean insert = mContactInfoTable.insertContactInfoForStat(info);
                GLog.d(TAG, "insert Contact info :" + insert);
            }
            mContactInfoTable.getSqliteDB().setTransactionSuccessful();
        } catch (Exception e) {
            GLog.e(TAG,e.getMessage());
        }finally {
            mContactInfoTable.getSqliteDB().endTransaction();
        }
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_SQLITE).end("wirte_sql", false);
        return contactInfos;
    }
    public ArrayList<ContactInfo> getTestContactInfo(){
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_SQLITE).startMoniter();
        ArrayList<ContactInfo> songList = mContactInfoTable.getAllContacts();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_SQLITE).end("read_sql", false);
        GLog.d(TAG,"getTestContactInfo SIZE:"  + songList.size());
        return songList;
    }
}
