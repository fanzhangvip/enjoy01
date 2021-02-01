package com.zero.toutiaodemo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.toutiaodemo.view.ColorChangeTextView2;
import com.zero.toutiaodemo.view.TextMeasureView;
import com.zero.toutiaodemo.view.TextMeasureView0;

public class TextMeasureActivity extends AppCompatActivity {

    ColorChangeTextView2 mMyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyText = new ColorChangeTextView2((this));
        setContentView(mMyText);

//        setContentView(new TextMeasureView0(this));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onStartLeft();
            }
        },2000);
        onStartLeft();
    }
    public void onStartLeft(){
        ObjectAnimator.ofFloat(mMyText,"percent",0,1).setDuration(2500).start();
    }
}
