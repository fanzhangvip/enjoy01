package com.zero.giflib1

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zero.giflib1.gif.GifDrawable
import com.zero.giflib1.gif.GifFrame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 666
            )
        }

        var gifDrawable: GifDrawable? = null //BitmapDrawable

        try {
            val gifFrame = GifFrame()
//            gifDrawable =  GifDrawable(gifFrame.decodeStream(getAssets().open("fire.gif")))
//            gifDrawable =  GifDrawable(gifFrame.decodeStream(this,"time_1.gif"))
            gifDrawable = GifDrawable(gifFrame.decodeStream(null, "/sdcard/timg_2.gif"))
            image.setImageDrawable(gifDrawable)
            gifDrawable.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
