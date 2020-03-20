package com.enjoy02.changeskindemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class NormalChangeActivity extends AppCompatActivity {


    private ZeroView zeroView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_normalchange);

        zeroView = findViewById(R.id.zeroView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeroView.setBackground(getDrawable(R.drawable.girl));
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeroView.setBackground(getDrawable(R.drawable.girl1));
            }
        });


    }
}
