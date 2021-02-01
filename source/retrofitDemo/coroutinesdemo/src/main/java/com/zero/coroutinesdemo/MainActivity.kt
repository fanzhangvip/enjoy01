package com.zero.coroutinesdemo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

//import as用法
import com.zero.coroutinesdemo.R.id.tv_show as tvShow

fun <T: View>Activity._view(id: Int): T{
    return findViewById<T>(id)
}



class MainActivity : AppCompatActivity(),CoroutineScope by MainScope() {

    fun Int.onClick(click: ()-> Unit){
        val tmp = _view<View>(this).apply {
            setOnClickListener { click() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//应用一
        val textview = _view<TextView>(R.id.tv_show)

        tvShow.onClick {
            println("Login...")
        }



        val coroutines1 = Coroutines1()
        val mainScope = MainScope()
        mainScope.launch {
        coroutines1.gitHubServiceTest()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
