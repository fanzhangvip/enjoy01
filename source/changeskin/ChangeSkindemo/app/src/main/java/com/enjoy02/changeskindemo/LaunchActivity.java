package com.enjoy02.changeskindemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LaunchActivity extends AppCompatActivity {

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //入口
        setContentView(R.layout.activity_launch);
        if(savedInstanceState == null){
            //TODO: 1.获取fragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //TODO: 2.开启一个fragment事务
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //TODO: 3.向FrameLayout容器添加MainFragment,现在并未真正执行
            transaction.add(R.id.frameLayout, MainFragment.newIntance(), MainFragment.class.getName());
            transaction.addToBackStack(null);
            //TODO: 4.提交事务，真正去执行添加动作
            transaction.commit();
        }
        verifyStoragePermissions(this);

    }
}
