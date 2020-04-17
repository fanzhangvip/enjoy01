package com.zero.navigationdemo.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentScrollBinding

class ScrollFragment: LifeCycleFragment() {
    private val binding by lazy {
        FragmentScrollBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_scrollFragment_to_mainFragment)
        }
        return binding.root
    }
}