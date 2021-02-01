package com.zero.materialdesign.coordinatorstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.zero.materialdesign.R;
import com.zero.materialdesign.comm.CommonRecyclerAdapter;
import com.zero.materialdesign.comm.CommonRecyclerHolder;
import com.zero.materialdesign.databinding.ActivityDemo02Binding;

import java.util.ArrayList;
import java.util.List;

public class Demo02Activity extends AppCompatActivity {

    private ActivityDemo02Binding binding;
    private String mText = "自定义Behavior";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDemo02Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);


        CommonRecyclerAdapter<String> commonRecyclerAdapter = new CommonRecyclerAdapter<String>(this,createData(), R.layout.item_recycle) {

            @Override
            public void convert(CommonRecyclerHolder holder, String item, int position, boolean isScrolling) {
                holder.setText(R.id.item_tv,item);
            }
        };
        binding.recyclerView.setAdapter(commonRecyclerAdapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private List<String> createData(){
        ArrayList<String> result = new ArrayList<>(100);
        for(int i = 0; i < 100; i++){
            result.add(mText + i);
        }
        return result;
    }
}