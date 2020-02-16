package com.example.fragmentdemo.basic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R

class LeftFragment:LifeCycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_left,container,false)
    }

    fun showMsg(view: View) {
        Log.i(TAG,"show left fragment")
    }
}