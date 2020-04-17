package com.zero.navigationdemo.deeplinks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zero.navigationdemo.R

class DeepLinksActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

    }



    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }


}
