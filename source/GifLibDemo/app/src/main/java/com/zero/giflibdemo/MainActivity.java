package com.zero.giflibdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zero.giflibdemo.gif.GifDrawable;
import com.zero.giflibdemo.gif.GifFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 666);
        }
        mImageView = findViewById(R.id.image);
        GifDrawable gifDrawable = null;//BitmapDrawable


        try {

//            File file = new File("a.txt");
//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] buffer = new byte[1024];
//            fileInputStream.read(buffer,0,buffer.length);

//            gifDrawable = new GifDrawable(GifFrame.decodeStream(getAssets().open("fire.gif")));
//            gifDrawable = new GifDrawable(GifFrame.decodeStream(this,"time_1.gif"));
            gifDrawable = new GifDrawable(GifFrame.decodeStream(null,"/sdcard/timg_2.gif"));
            mImageView.setImageDrawable(gifDrawable);
            gifDrawable.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
