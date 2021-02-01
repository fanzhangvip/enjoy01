package com.zero.materialdesign.coordinatorstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zero.materialdesign.R;
import com.zero.materialdesign.databinding.ActivityCoordinatorMainBinding;

public class CoordinatorMainActivity extends AppCompatActivity {

    private ActivityCoordinatorMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoordinatorMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoordinatorMainActivity.this, Demo01Activity.class));
            }
        });

        binding.btnDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoordinatorMainActivity.this, Demo02Activity.class));
            }
        });
    }
}