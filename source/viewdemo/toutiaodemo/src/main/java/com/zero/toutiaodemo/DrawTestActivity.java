package com.zero.toutiaodemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zero.toutiaodemo.view.Clip1View;
import com.zero.toutiaodemo.view.OverdrawView;
import com.zero.toutiaodemo.view.RankIdView;

public class DrawTestActivity extends AppCompatActivity {

    Handler mHandler = new Handler(){

        @Override
        public void dispatchMessage(@NonNull final Message msg) {
            super.dispatchMessage(msg);
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rankIdView.setText("" + (++num));
                }
            },1000);
        }

        @Override
        public void handleMessage(@NonNull final Message msg) {
            super.handleMessage(msg);

        }
    };

    RankIdView rankIdView = null;

    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rankIdView = new RankIdView(this);
        setContentView(rankIdView);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rankIdView.setText("" + (++num));
            }
        },1000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
