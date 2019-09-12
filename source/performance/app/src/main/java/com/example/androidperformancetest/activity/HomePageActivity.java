package com.example.androidperformancetest.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.androidperformancetest.R;
import com.example.androidperformancetest.activity.base.BaseFragmentActivity;
import com.example.androidperformancetest.activity.base.StackLayout;
import com.example.androidperformancetest.fragment.base.BaseFragment;
import com.example.androidperformancetest.fragment.homepage.HomePageFragment;
import com.example.androidperformancetest.utils.GLog;


public class HomePageActivity extends BaseFragmentActivity {

    private final static String TAG = "MusicMainWithMiniBarActivity";
    private int mFocusedTextColor, mTextColor;
    private StackLayout mMainFragmentContainer;
    private static final String MAIN_FRAGMENT_CONTENT = "main_content";
    public static final int CONTAINER_ID = R.id.homepage_fragment_detail;
    private int mViewIndex = TAB_VIEW_00;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(BaseFragmentActivity activity) {


        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        verifyStoragePermissions(this);
        mMainFragmentContainer = (StackLayout) findViewById(R.id.homepage_fragment_detail);
        makeNewContentFragmentStackManager(CONTAINER_ID, MAIN_FRAGMENT_CONTENT, mMainFragmentContainer);
        Bundle data = new Bundle();
        data.putInt(APP_INDEX_KEY, mViewIndex);
        addSecondFragment(HomePageFragment.class, data, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                BaseFragment top = top();
                if (top != null && top.onKeyDown(keyCode, event)) {
                    return true;
                }
                if (size() > 1) {
                    popBackStack();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private static final int MSG_REFRESH_MUSIC_CIRCLE = 10000;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GLog.d(TAG, "On Resume" + getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        GLog.d(TAG, "onPause " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
