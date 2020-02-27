package com.example.fragmentdemo.basic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import com.example.fragmentdemo.LifeCycleFragment
import com.example.fragmentdemo.R
import org.jetbrains.anko.support.v4.find

class LeftFragment:LifeCycleFragment(),View.OnClickListener {
    //这是静态fragment的使用方式

    //嵌套了fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_left,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        find<Button>(R.id.btn_show).setOnClickListener(LeftFragment@this)
    }

    override fun onClick(v: View?) {
        childFragmentManager.commit {
            add(R.id.frameLayout,ParentFragment.newInstance(),"parent")
            addToBackStack(null)
        }
    }


}