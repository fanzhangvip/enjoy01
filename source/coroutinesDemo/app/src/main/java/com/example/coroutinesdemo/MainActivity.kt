package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//         val rxJavaDemo = RxJavaDemo()
//         rxJavaDemo.getProjectTest()

         val mainScope = MainScope()
         mainScope.launch {
             val coroutinesDemo = CoroutinesDemo()
             coroutinesDemo.getProject()
             show("")
         }
         Log.i("Zero","......")



    }

    fun show(str: String){

    }
}