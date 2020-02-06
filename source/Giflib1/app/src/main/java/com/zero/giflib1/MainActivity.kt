package com.zero.giflib1

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zero.giflib1.gif.GifDrawable
import com.zero.giflib1.gif.GifFrame
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //动态申请权限
        requestPermission()
        //测试giflib
        gifTest()
    }

    fun gifTest(){
        var gifDrawable: GifDrawable? = null //BitmapDrawable
        try {
//            gifDrawable =  GifDrawable(GifFrame.decodeStream(FileInputStream("aaa.gif")))
            gifDrawable =  GifDrawable(GifFrame.decodeStream(this,"time_1.gif"))
//            gifDrawable = GifDrawable(GifFrame.decodeStream(null, "/sdcard/timg_2.gif"))
            image.setImageDrawable(gifDrawable)
            gifDrawable.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun requestPermission(){
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
