package com.zero.navigationdemo.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentOneBinding

class OneFragment: LifeCycleFragment() {

    private val binding by lazy {
        FragmentOneBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.button.setOnClickListener {
            //方式一
            Navigation.findNavController(it).navigate(R.id.action_oneFragment_to_twoFragment)
        }
        return binding.root
    }

}