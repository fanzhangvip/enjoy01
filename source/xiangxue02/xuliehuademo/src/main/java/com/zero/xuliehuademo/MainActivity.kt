package com.zero.xuliehuademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.util.Log
import com.zero.xuliehuademo.parcel.parcelTest

const val TAG = "Zero"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parcelTest()
    }
}
