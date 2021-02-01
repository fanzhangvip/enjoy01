package com.jinlong.newmaterialdesign.animation;

import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.ChangeBounds;
import androidx.transition.Explode;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import com.jinlong.newmaterialdesign.R;

/**
 * 展示布局的过渡动画
 */
public class SceneActivity extends AppCompatActivity {

    private Scene mScene1;
    private Scene mScene2;
    private boolean isScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);


        initScene();
    }

    private void initScene() {
        FrameLayout layout = findViewById(R.id.rl_root);
        mScene1 = Scene.getSceneForLayout(layout, R.layout.scene1, this);
        mScene2 = Scene.getSceneForLayout(layout, R.layout.scene2, this);
        TransitionManager.go(mScene1);
    }

    /**
     * 操作Scene动画
     */
    public void scene(View view) {
        TransitionManager.go(isScene2 ? mScene1 : mScene2, new ChangeBounds());
        isScene2 = !isScene2;
    }
}
