package com.zero.navigationdemo.safeargs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zero.navigationdemo.R

class SafeArgsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe_args)

    }



    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }


}
