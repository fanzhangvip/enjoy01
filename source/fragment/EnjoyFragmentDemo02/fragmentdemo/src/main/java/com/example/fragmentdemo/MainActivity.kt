package com.example.fragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment.newIntance()
        supportFragmentManager.commit {
            add(R.id.frameLayout,mainFragment)
            setMaxLifecycle(mainFragment,Lifecycle.State.CREATED)
        }

        val  pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }

    class MyFragmentPagerAdapter(fm: FragmentManager, behavior: Int= 0): FragmentPagerAdapter(fm,behavior){
        override fun getItem(position: Int): Fragment {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
