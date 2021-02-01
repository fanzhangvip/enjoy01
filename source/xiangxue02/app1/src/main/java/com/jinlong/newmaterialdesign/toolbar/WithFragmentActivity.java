package com.jinlong.newmaterialdesign.toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jinlong.newmaterialdesign.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity和Fragment同时使用menu
 */
public class WithFragmentActivity extends AppCompatActivity {

    private static final String TAG = WithFragmentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);


        initToolbar();
        setViewPager();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void setViewPager() {
        List<Fragment> list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new Main1Fragment());
        MainVPAdapter adapter = new MainVPAdapter(getSupportFragmentManager(), list);

        ViewPager vp = findViewById(R.id.vp_content);
        vp.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Log.e(TAG, "onOptionsItemSelected: setting");
                return true;
            case R.id.menu_other:
                Log.e(TAG, "onOptionsItemSelected: other" );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
