package com.zero.rxbus.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zero.rxbus.RxBus;

import butterknife.ButterKnife;



public class BaseActivity extends AppCompatActivity {

    protected RxBus rxBus;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rxBus = RxBus.get();
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
