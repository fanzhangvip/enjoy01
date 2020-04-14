package com.zero.navigationdemo.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentOneBinding
import com.zero.navigationdemo.databinding.FragmentThreeBinding

class ThreeFragment: LifeCycleFragment() {

    private val binding by lazy{
        FragmentThreeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.button.setOnClickListener {
            //方式三
            findNavController().navigate(R.id.action_threeFragment_to_fourFragment)
        }
        return binding.root
    }

}