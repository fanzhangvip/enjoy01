package com.zero.leakcanarydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.zero.leakcanarydemo.databinding.ActivityMainBinding
import com.zero.leakcanarydemo.databinding.ActivityTestBinding
import leakcanary.AppWatcher

object MyFragmentLifeCycleCallBack: FragmentManager.FragmentLifecycleCallbacks(){
    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Log.i(TAG,"${f.javaClass.simpleName} onCreated()")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        Log.i(TAG,"${f.javaClass.simpleName} onViewDestroyed()")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Log.i(TAG,"${f.javaClass.simpleName} onDestroyed()")
    }
}
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifeCycleCallBack,true)
        val objectWatcher = AppWatcher.objectWatcher

        val retainedObjectCount = AppWatcher.objectWatcher.retainedObjectCount

        binding.button.setOnClickListener {
            startActivity(Intent(MainActivity@this,TestActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(MyFragmentLifeCycleCallBack)
    }



    interface MaybeObjectWatcher{
        fun watch(watchedObject: Any, description: String)

        object None: MaybeObjectWatcher{
            override fun watch(watchedObject: Any, description: String) {
                TODO("Not yet implemented")
            }
        }
    }

    class RealObjectWatcher: MaybeObjectWatcher{
        override fun watch(watchedObject: Any, description: String) {
            AppWatcher.objectWatcher.watch(watchedObject,description)
        }
    }
}
