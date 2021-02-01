package com.example.compose_demo01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.ui.tooling.preview.Preview
import com.example.compose_demo01.ui.Compose_demo01Theme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}


@Composable fun NewsList(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        
        Image(asset = imageResource(id = R.drawable.meinv))
        
        Text("lance相亲")
        Text("月亮老师 。。。。")
        Text("Jett 肾亏了 ")
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_demo01Theme {
        NewsList()

    }
}