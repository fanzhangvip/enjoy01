package com.zero.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class MainActivity : AppCompatActivity(),CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coroutines1 = Coroutines1()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
