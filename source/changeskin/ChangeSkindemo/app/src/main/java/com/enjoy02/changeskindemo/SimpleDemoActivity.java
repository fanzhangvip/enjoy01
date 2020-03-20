package com.enjoy02.changeskindemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;

public class SimpleDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater.from(this).setFactory2(new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                if(TextUtils.equals(name,"TextView")){
                    Button button = new Button(SimpleDemoActivity.this);
                    button.setText("Hello Button");
                    return button;
                }

                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpledemo);

    }
}
