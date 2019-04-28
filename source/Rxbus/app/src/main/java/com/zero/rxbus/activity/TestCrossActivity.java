package com.zero.rxbus.activity;

import android.os.Bundle;

import com.zero.rxbus.app.BaseActivity;
import com.zero.rxbus.domain.CrossActivityEvent;


public class TestCrossActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rxBus.post(new CrossActivityEvent());
    }
}
