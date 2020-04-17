package com.zero.navigationdemo.fix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zero.navigationdemo.R

class BasicFixActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_fix)

    }



    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }


}
