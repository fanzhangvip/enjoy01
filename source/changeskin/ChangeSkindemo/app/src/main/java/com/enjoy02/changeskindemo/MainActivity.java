package com.enjoy02.changeskindemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    protected SkinFactory mSkinFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinFactory = new SkinFactory();
        mSkinFactory.setDelegate(getDelegate());
        LayoutInflater.from(this).setFactory2(mSkinFactory);

        super.onCreate(savedInstanceState);
        //理论上都可以
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSkin();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSkinFactory.resetSkin(MainActivity.this);
            }
        });

    }

    public void changeSkin() {
        File skinFile = new File(Environment.getExternalStorageDirectory(), "app-debug.apk");
        SkinEngine.getInstance().load(skinFile.getAbsolutePath());
        mSkinFactory.changeSkin();
    }
}
