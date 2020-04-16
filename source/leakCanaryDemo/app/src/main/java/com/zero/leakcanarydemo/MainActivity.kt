package com.zero.leakcanarydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zero.leakcanarydemo.databinding.ActivityMainBinding
import com.zero.leakcanarydemo.databinding.ActivityTestBinding
import leakcanary.AppWatcher

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val objectWatcher = AppWatcher.objectWatcher

        val retainedObjectCount = AppWatcher.objectWatcher.retainedObjectCount

        binding.button.setOnClickListener {
            startActivity(Intent(MainActivity@this,TestActivity::class.java))
        }

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
