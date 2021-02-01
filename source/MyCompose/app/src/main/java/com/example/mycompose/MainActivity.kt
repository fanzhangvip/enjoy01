package com.example.mycompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.mycompose.ui.MyComposeTheme

const val TAG = "Zero"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsStroy()
        }
    }

    @Composable
    fun Greeting(name: String) {//单一原则
        Text(text = "Hello $name !")
    }

    @Preview
    @Composable
    fun PreviewGreeting() {
        Greeting(name = "Android")
    }

//@Composable
//fun NewsStroy() {
//    val image = imageResource(id = R.drawable.meinv)
//    Column(horizontalAlignment = Alignment.End,
//            modifier = Modifier.padding(16.dp)) {
//
//        Image(image, modifier = Modifier.preferredHeight(300.dp)
//                .fillMaxWidth(),
//                contentScale = ContentScale.Crop)
//
//        Spacer(modifier = Modifier.preferredHeight(16.dp))
//
//        Text(text = "A day in Shark Fin cove")
//        Text("Davenport, California")
//        Text("December 2018")
//    }
//
//}

    @Composable
    fun NewsStroy() {
        val image = imageResource(id = R.drawable.meinv)
        MaterialTheme {
            val typography = MaterialTheme.typography
            Column(horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(16.dp)) {

                Image(
                    image, modifier = Modifier.preferredHeight(400.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = {
                            Log.i(TAG, "Lance老师喜欢小姐姐")
                        }),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.preferredHeight(16.dp))

                Text(text = """A day wandering through the sandhills 
                |in Shark Fin Cove, and a few of the sights
                | I saw """.trimMargin(), style = typography.h6,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                )
                val flag = true
                if(flag){
                    Text("Davenport, California", style = typography.body1)
                    Text("December 2018", style = typography.body2)
                }


                Button({
                    Log.i(TAG, "gotoOther: ...")
                    startActivity(Intent(this@MainActivity,SecondActivity::class.java))
                }){
                    Text(" onclick...")
                }

            }
        }
    }


    @Preview
    @Composable
    fun DefaultPreview() {
        DrawBox()
    }

    @Composable
    fun DrawBox(){

    }

    @Composable
    fun FabAdd(onClick: (() -> Unit)? = null){

    }

    @Composable
    fun Items(){
        ScrollableColumn {

        }
    }
}
















































