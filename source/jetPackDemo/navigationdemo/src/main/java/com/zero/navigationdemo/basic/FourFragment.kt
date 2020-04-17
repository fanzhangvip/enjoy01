package com.zero.navigationdemo.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentFourBinding
import com.zero.navigationdemo.databinding.FragmentOneBinding

class FourFragment: LifeCycleFragment() {

    private val binding by lazy{
        FragmentFourBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_fourFragment_to_oneFragment)
        }
        return binding.root
    }

}