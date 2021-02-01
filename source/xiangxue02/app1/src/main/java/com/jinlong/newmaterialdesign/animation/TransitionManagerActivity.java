package com.jinlong.newmaterialdesign.animation;

import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.transition.Scene;

import com.jinlong.newmaterialdesign.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 这个适用于展示TransitionManager的动画效果
 */
public class TransitionManagerActivity extends AppCompatActivity {

    private ConstraintLayout mCl_root;
    private TextView mTvText;
    private Scene mScene1;
    private Scene mScene2;
    private boolean isScene2;//这个标识是显示的是否是Scene2的页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_manager);
    }

    /**
     * 操作单独View的
     */
    public void handleView(View view) {
        Intent intent = new Intent(this, HandleViewActivity.class);
        startActivity(intent);
    }

    /**
     * 操作Scene的
     */
    public void handleScene(View view) {
        Intent intent = new Intent(this, SceneActivity.class);
        startActivity(intent);
    }

    /**
     * 共享动画
     */
    public void sharedAnimation(View view) {
        CircleImageView civHead = findViewById(R.id.civ_heard);
        TextView tvTitle = findViewById(R.id.tv_title);

        Intent intent = new Intent(this, SharedActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<View, String>(civHead, "shared_image"),
                new Pair<View, String>(tvTitle, "shared_textview"));

        startActivity(intent, optionsCompat.toBundle());
    }
}
