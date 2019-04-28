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
import com.zero.rxbus.domain.Fragment2Event;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class Fragment2 extends BaseFragment {

    @BindView(R.id.text2)
    TextView text2;

    @BindView(R.id.button2)
    Button button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2, container, false);

        ButterKnife.bind(this, v);

        initViews();

        return v;
    }

    private void initViews() {

        RxView.clicks(button2)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        //发送一个事件
                        rxBus.post(new Fragment2Event());
                    }
                });
    }

    public TextView getText2() {
        return text2;
    }
}
