package com.jinlong.newmaterialdesign.material;

import android.animation.ObjectAnimator;

import android.os.Bundle;

import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.jinlong.newmaterialdesign.R;

public class AppBarLayoutActivity extends AppCompatActivity {

    private static final String TAG = AppBarLayoutActivity.class.getSimpleName();
    private AppBarLayout mAbl;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);

        mAbl = findViewById(R.id.appBar);
        mTvTitle = findViewById(R.id.tv_title);

        RecyclerView rvTab = findViewById(R.id.rv_tab);
        rvTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TabRVAdapter adapter = new TabRVAdapter();
        rvTab.setAdapter(adapter);


        mAbl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                //这个是计算App bar Layout的总高度的API
                int totalScrollRange = appBarLayout.getTotalScrollRange();

                float ratio = Math.abs((float) verticalOffset / totalScrollRange);

                mTvTitle.setAlpha(1 - ratio);

                Log.e(TAG, "onOffsetChanged: " + ratio + "---" + verticalOffset);
            }
        });
    }
}
