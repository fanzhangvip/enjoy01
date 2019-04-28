package com.zero.rxbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.zero.rxbus.R;
import com.zero.rxbus.app.BaseActivity;
import com.zero.rxbus.domain.CrossActivityEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.text1)
    TextView text1;

    @BindView(R.id.text2)
    TextView text2;

    private Disposable disposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        registerEvents();
    }

    private void initViews() {

        RxView.clicks(text1)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {

                Intent i = new Intent(MainActivity.this,TestEventBusActivity.class);
                startActivity(i);
            }
        });

        RxView.clicks(text2)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        Intent i = new Intent(MainActivity.this,TestCrossActivity.class);
                        startActivity(i);
                    }
                });
    }

    private void registerEvents() {
        //rxbus注册
//FlowableProcessor 订阅
        disposable = rxBus.toFlowable(CrossActivityEvent.class)
                .subscribe(new Consumer<CrossActivityEvent>() {
                    @Override
                    public void accept(CrossActivityEvent crossActivityEvent) throws Exception {
                        Log.i(TAG, "我是来自MainAct的event");
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null && !disposable.isDisposed()) {

            disposable.dispose();
        }
    }
}
