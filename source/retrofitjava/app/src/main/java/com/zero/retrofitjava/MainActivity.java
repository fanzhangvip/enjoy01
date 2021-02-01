package com.zero.retrofitjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RetrofitDemo retrofitDemo = new RetrofitDemo();
        retrofitDemo.listRepos();
        OkHttpDemo okHttpDemo = new OkHttpDemo();
        okHttpDemo.listReposEx();

    }



}
