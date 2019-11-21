package com.zero.toutiaodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zero.toutiaodemo.view.Clip1View;
import com.zero.toutiaodemo.view.ClipXXXView;
import com.zero.toutiaodemo.view.OverdrawView;
import com.zero.toutiaodemo.view.TextMeasureView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new TextMeasureView(this));
//        setContentView(R.layout.activity_clipxxxx);
    }
}
