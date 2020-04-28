package com.zero.glidegif

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.zero.glidegif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var path =
        "file:///android_asset/time_1.gif"

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermission()

        Glide.with(MainActivity@this)
            .load(path)
            .into(binding.image)
    }

    private fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 666
            )
        }
    }
}
