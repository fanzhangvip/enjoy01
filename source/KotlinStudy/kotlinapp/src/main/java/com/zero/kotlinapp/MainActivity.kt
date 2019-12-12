package com.zero.kotlinapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val asyncTask = object : AsyncTask<String, String, String>() {
            override fun doInBackground(vararg params: String?): String {
                return ""
            }

        }
        asyncTask.execute("")
    }
}
