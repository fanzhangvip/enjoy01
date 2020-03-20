package com.example.livedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.livedatademo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel

    companion object{
        const val My_KEY = "myViewModel_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)


        myViewModel = ViewModelProvider(this).get(My_KEY,MyViewModel::class.java)

        binding.data = myViewModel
        binding.lifecycleOwner = this



    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Zero","onDestroy")
    }
}
