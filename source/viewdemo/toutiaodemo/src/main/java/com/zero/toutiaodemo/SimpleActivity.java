package com.zero.toutiaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.zero.toutiaodemo.view.ColorChangeTextView2;


public class SimpleActivity extends AppCompatActivity {

    ColorChangeTextView2 mView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mView1 = findViewById(R.id.color_change_textview);
    }

    public void onStartLeft(View view){
        mView1.setDirection(ColorChangeTextView2.DIRECTION_LEFT);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }

    public void onStartRight(View view){
        mView1.setDirection(ColorChangeTextView2.DIRECTION_RIGHT);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }

    public void onStartTop(View view){
        mView1.setDirection(ColorChangeTextView2.DIRECTION_TOP);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }

    public void onStartBottom(View view){
        mView1.setDirection(ColorChangeTextView2.DIRECTION_BOTTOM);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }
}
