package com.zero.navigationdemo.bottomnavigationview;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransactionKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zero.navigationdemo.R;
import com.zero.navigationdemo.databinding.ActivityBottomnavigationBinding;
import com.zero.navigationdemo.databinding.ActivityMainBinding;

public class BottomNavigationAdvancedActivity extends AppCompatActivity {

    private ActivityBottomnavigationBinding binding = null;

    private NavController currNavController = null;

    private int[] graphIds = new int[]{
            R.navigation.shopping, R.navigation.history, R.navigation.mine, R.navigation.about
    };

    private String selectItemTag;
    private boolean isOnFirstFragment;
    private int firstFragmentGraphId = 0;
    private String firstFragmentTag;

    private void setupBottomNavigation() {

        //创建一个graphId与fragmentTag的映射表
        SparseArray<String> graphIdToTagMap = new SparseArray<>();

        //为每个graph创建出一个NavHostFragment
        for (int i = 0; i < graphIds.length; i++) {

            //为每个Item对应的NavHostFragment创建一个tag
            String fragmentTag = getFragmentTag(i);

            //获取对应graphId的NavHostFragment，并add到FragmentContainerView里面去
            NavHostFragment navHostFragment = obtainNavHostFragment(getSupportFragmentManager(), fragmentTag, R.id.frameLayout, graphIds[i]);

            int graphId = navHostFragment.getNavController().getGraph().getId();
            //在容器中显示第一个，其他的detach
            if (i == 0) {
                firstFragmentGraphId = graphId;
            }
            graphIdToTagMap.put(graphId, fragmentTag);

            //因为实际上是只有一个容器，先全部添加进去，用attach与detach控制fragment的显示
            //这里默认显示当前被选择的
            if (binding.bottomNavigationView.getSelectedItemId() == graphId) {
                //attach
                currNavController = navHostFragment.getNavController();
                attachNavHostFragment(getSupportFragmentManager(), navHostFragment, i == 0);
            } else {
                //detach
                detachNavHostFragment(getSupportFragmentManager(), navHostFragment);
            }

        }

        //选中的fragment的tag
        selectItemTag = graphIdToTagMap.get(binding.bottomNavigationView.getSelectedItemId());
        firstFragmentTag = graphIdToTagMap.get(firstFragmentGraphId);
        isOnFirstFragment = selectItemTag == firstFragmentTag;

        //设置OnNavigationItemSelectedListener
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (getSupportFragmentManager().isStateSaved()) {//然后fragment状态已经保存则什么都不要做，一般是不可交互的状态
                    return false;
                }
                String newlySelectedItemTag = graphIdToTagMap.get(menuItem.getItemId());
                if (TextUtils.equals(newlySelectedItemTag, selectItemTag)) {//fragment的Tag相同说明是点击了同一个item
                    return false;
                }
                //fragment的Tag不同代表进行了页面切换

                //1.进行页面切换，清除一切在first fragment之上的页面
                getSupportFragmentManager().popBackStack(firstFragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                NavHostFragment selectFragment = (NavHostFragment) getSupportFragmentManager().findFragmentByTag(newlySelectedItemTag);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.nav_default_enter_anim,
                                R.anim.nav_default_exit_anim,
                                R.anim.nav_default_pop_enter_anim,
                                R.anim.nav_default_pop_exit_anim
                        )
                        .attach(selectFragment)
                        .setPrimaryNavigationFragment(selectFragment);
                //detach其他NavHostFragment
                for (int i = 0; i < graphIdToTagMap.size(); i++) {
                    if (graphIdToTagMap.valueAt(i) != newlySelectedItemTag) {
                        transaction.detach(getSupportFragmentManager().findFragmentByTag(graphIdToTagMap.valueAt(i)));
                    }
                }

                transaction.addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)//优化操作
                        .commit();

                selectItemTag = newlySelectedItemTag;
                isOnFirstFragment = selectItemTag == firstFragmentTag;
                currNavController = selectFragment.getNavController();

                return true;
            }
        });

        binding.bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                String newlySelectedItemTag = graphIdToTagMap.get(menuItem.getItemId());
                NavHostFragment selectFragment = (NavHostFragment) getSupportFragmentManager().findFragmentByTag(newlySelectedItemTag);
                NavController controller = selectFragment.getNavController();

                controller.popBackStack(controller.getGraph().getStartDestination(), false);

            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (!isOnFirstFragment && !isOnBackStack(getSupportFragmentManager(), firstFragmentTag)) {
                    binding.bottomNavigationView.setSelectedItemId(firstFragmentGraphId);
                }

                if (currNavController != null && currNavController.getCurrentDestination() == null) {
                    currNavController.navigate(currNavController.getGraph().getId());
                }
            }
        });


    }


    private void attachNavHostFragment(FragmentManager fragmentManager, NavHostFragment fragment, boolean isPrivate) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.attach(fragment);
        if (isPrivate) {
            transaction.setPrimaryNavigationFragment(fragment);
        }
        transaction.commitNow();
    }

    private boolean isOnBackStack(FragmentManager fragmentManager, String backStackName) {
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            if (fragmentManager.getBackStackEntryAt(i).getName() == backStackName) {
                return true;
            }
        }
        return false;
    }

    private void detachNavHostFragment(FragmentManager fragmentManager, NavHostFragment fragment) {
        fragmentManager.beginTransaction()
                .detach(fragment)
                .commitNow();
    }

    private NavHostFragment obtainNavHostFragment(FragmentManager fragmentManager, String fragmentTag, int containerId, int graphId) {
        NavHostFragment fragment = (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment != null) return fragment;

        fragment = NavHostFragment.create(graphId);
        fragmentManager.beginTransaction()
                .add(containerId, fragment, fragmentTag)
                .commitNow();

        return fragment;
    }

    private String getFragmentTag(int index) {
        return "navigationbottom#" + index;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomnavigationBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.bottomNavigationView.setItemIconTintList(null);
        if (savedInstanceState == null) {
            setupBottomNavigation();
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setupBottomNavigation();
    }
}
