package com.jinlong.newmaterialdesign.navigation;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.jinlong.newmaterialdesign.R;

public class NavigationActivity extends AppCompatActivity {

    private String TAG = NavigationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initNavigation();
    }

    private void initNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView nv_left = findViewById(R.id.nv_left);
        final DrawerLayout dl_content = findViewById(R.id.dl_content);
        nv_left.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_camera:
                        Log.e(TAG, "onNavigationItemSelected: ");
                        dl_content.closeDrawer(Gravity.LEFT);
                        break;
                }
                return true;
            }
        });

        //这是带Home旋转开关按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl_content,
                toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl_content.addDrawerListener(toggle);
        toggle.syncState();
    }
}
