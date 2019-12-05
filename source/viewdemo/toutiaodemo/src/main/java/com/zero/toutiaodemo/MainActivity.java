package com.zero.toutiaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zero.toutiaodemo.view.Clip1View;
import com.zero.toutiaodemo.view.ClipXXXView;
import com.zero.toutiaodemo.view.ColorChangeTextView1;
import com.zero.toutiaodemo.view.OverdrawView;
import com.zero.toutiaodemo.view.TextMeasureView;

public class MainActivity extends AppCompatActivity {

    ColorChangeTextView1 mView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new TextMeasureView(this));
//        setContentView(R.layout.activity_clipxxxx);

        mView1 = findViewById(R.id.color_change_textview);

    }

    public void onStartLeft(View view){
        mView1.setDirection(ColorChangeTextView1.DIRECTION_LEFT);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }

    public void onStartRight(View view){
        mView1.setDirection(ColorChangeTextView1.DIRECTION_RIGHT);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }

    public void onStartTop(View view){
        mView1.setDirection(ColorChangeTextView1.DIRECTION_TOP);
        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }

    public void onStartBottom(View view){
        Intent intent = new Intent(this,ViewPagerActivity.class);
        startActivity(intent);
//        mView1.setDirection(ColorChangeTextView1.DIRECTION_BOTTOM);
//        ObjectAnimator.ofFloat(mView1,"progress",0,1).setDuration(2500).start();
    }
}
