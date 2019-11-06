package com.zero.flowlayoutdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(AppCompatActivity activity) {


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

    private Button button;

    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verifyStoragePermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i(TAG, "onCreate: "+ this.getClass().getSimpleName());
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            Log.i(TAG, "onCreate: 1");
            finish();
            return;
        }

        if(!this.isTaskRoot()){
            Intent intent = getIntent();
            if(intent!=null){
                String action = intent.getAction();
                Log.i(TAG, "onCreate: action: " + action);
                if(intent.hasCategory(Intent.CATEGORY_LAUNCHER)&&Intent.ACTION_MAIN.equals(action)){
                    Log.i(TAG, "onCreate: 2");
                    finish();
                    return;
                }
            }
        }

        button = findViewById(R.id.btn_main);
        button1 = findViewById(R.id.btn_main1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(gotoMain);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(HomeActivity.this, FlowLayoutActivity.class);
                startActivity(gotoMain);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + this.getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: "+ this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: "+ this.getClass().getSimpleName());
    }
    private static final String TAG = "Zero";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: "+ this.getClass().getSimpleName());
    }
}
