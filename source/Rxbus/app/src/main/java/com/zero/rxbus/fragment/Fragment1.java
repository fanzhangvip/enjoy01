package com.zero.rxbus.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.zero.rxbus.R;
import com.zero.rxbus.app.BaseFragment;
import com.zero.rxbus.domain.Fragment1Event;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class Fragment1 extends BaseFragment {


    @BindView(R.id.text1)
    TextView text1;

    @BindView(R.id.button1)
    Button button1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);

        ButterKnife.bind(this,v);

        initViews();

        return v;
    }

    private void initViews() {

        RxView.clicks(button1)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        //发送一个event事件
                        rxBus.post(new Fragment1Event());
                    }
                });
    }

    public TextView getText1() {
        return text1;
    }
}
