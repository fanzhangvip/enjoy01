package com.jinlong.newmaterialdesign.fab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jinlong.newmaterialdesign.R;

/**
 * FAB的演示
 */
public class FABActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
    }

    /**
     * FAB与RecyclerView的联动
     */
    public void recyclerView(View view) {
        Intent intent = new Intent(this, FABAndRecyclerViewActivity.class);
        startActivity(intent);
    }
}
