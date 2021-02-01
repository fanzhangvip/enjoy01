package com.jinlong.newmaterialdesign.animation;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Explode;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import com.jinlong.newmaterialdesign.R;

/**
 * 操作View的动画
 */
public class HandleViewActivity extends AppCompatActivity {

    private ConstraintLayout mClRoot;
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_view);

        initView();
    }

    private void initView() {
        mClRoot = findViewById(R.id.cl_root);
        mTvShow = findViewById(R.id.tv_show);
    }


    /**
     * fade动画,淡入淡出
     *
     * @param view
     */
    public void fadeTransition(View view) {
        Fade fade = new Fade();
        TransitionManager.beginDelayedTransition(mClRoot, fade);
        show();
    }


    /**
     * Slide动画，移动
     */
    public void slideTransition(View view) {
        Slide slide = new Slide();
        TransitionManager.beginDelayedTransition(mClRoot, slide);
        show();
    }

    public void explodeTransition(View view) {
        Explode explode = new Explode();
        TransitionManager.beginDelayedTransition(mClRoot, explode);
        show();
    }

    /**
     * 公共的显示和隐藏的方法
     */
    private void show() {
        if (mTvShow.getVisibility() == View.VISIBLE) {
            mTvShow.setVisibility(View.GONE);
        } else {
            mTvShow.setVisibility(View.VISIBLE);
        }
    }
}
