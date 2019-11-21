package com.zero.gifframe;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView iv = findViewById(R.id.sample_text);

        try {
            GifFrame gifFrame = GifFrame.decodeStream(getAssets().open("fire.gif"));
            GifDrawable gifDrawable = new GifDrawable(gifFrame, new GifDrawable.BitmapProvider() {
                @Override
                public Bitmap acquireBitmap(int minWidth, int minHeight) {
                    return Bitmap.createBitmap(minWidth, minHeight, Bitmap.Config.ARGB_8888);
                }

                @Override
                public void releaseBitmap(Bitmap bitmap) {

                }
            });
            iv.setImageDrawable(gifDrawable);
            gifDrawable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
