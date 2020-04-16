package com.example.xiangxue.enjoyfragmentnavigationdemo.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiangxue.enjoyfragmentnavigationdemo.R;


public class OneFragment1 extends Fragment {


    public static Fragment newIntance(OneFragment oneFragment) {
        OneFragment1 fragment = new OneFragment1();
        fragment.setOneFragment(oneFragment);
        return fragment;
    }

    OneFragment oneFragment;

    public void setOneFragment(OneFragment oneFragment) {
        this.oneFragment = oneFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_11, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick(v);
            }
        });

    }

    public void myClick(View view) {
        Navigation.createNavigateOnClickListener(R.id.action_oneFragment1_to_oneFragment2).onClick(view);
    }


}
