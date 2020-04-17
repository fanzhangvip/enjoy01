package com.zero.navigationdemo.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zero.navigationdemo.R

class BasicActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

    }



    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }


}
