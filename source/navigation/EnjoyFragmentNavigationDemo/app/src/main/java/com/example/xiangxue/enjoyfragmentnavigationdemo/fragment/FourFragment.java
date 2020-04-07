package com.example.xiangxue.enjoyfragmentnavigationdemo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xiangxue.enjoyfragmentnavigationdemo.R;


public class FourFragment extends Fragment {


    public static Fragment newIntance() {
        FourFragment fragment = new FourFragment();
        return fragment;
    }

    public FourFragment(){
        Log.i("Zero","init: " + this.getClass().getSimpleName());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Zero","onResume: " + this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Zero","onDestroy: " + this.getClass().getSimpleName());
    }

    public void onClick(View view) {

    }


}
