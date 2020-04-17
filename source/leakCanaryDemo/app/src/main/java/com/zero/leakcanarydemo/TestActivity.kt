package com.zero.leakcanarydemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zero.leakcanarydemo.databinding.ActivityTestBinding

class TestActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        TestDataModel.getInstance().mRetainedTextView = binding.textview
    }
}