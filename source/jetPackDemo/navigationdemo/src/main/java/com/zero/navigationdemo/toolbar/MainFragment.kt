package com.zero.navigationdemo.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentMainBinding
import com.zero.navigationdemo.databinding.FragmentScrollBinding

class MainFragment: LifeCycleFragment() {
    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }
        return binding.root
    }
}