package com.zero.matrixdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SelfDrawableActivity extends Activity {

    private ImageView mImageView;
    private TaskClearDrawable mTaskClearDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_drawable);
        
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTaskClearDrawable = new TaskClearDrawable(this, 500, 500);
        mImageView.setImageDrawable(mTaskClearDrawable);
        
        mImageView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {

                if(false == mTaskClearDrawable.isRunning()){
                    mTaskClearDrawable.start();
                }
                Log.i("Zero", "mTaskClearDrawable = " + mTaskClearDrawable.isRunning() );
            }
        });
        
    }
}
