package com.zero.navigationdemo.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentOneBinding
import com.zero.navigationdemo.databinding.FragmentTwoBinding

class TwoFragment: LifeCycleFragment() {

    private val binding by lazy {
        FragmentTwoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.button.setOnClickListener {
            //方式二
            Navigation.createNavigateOnClickListener(R.id.action_twoFragment_to_threeFragment).onClick(it)
        }
        return binding.root
    }

}