package com.zero.navigationdemo.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentScrollBinding
import com.zero.navigationdemo.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.activity_bottomnavigation.*

class SettingsFragment: LifeCycleFragment() {
    private val binding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_scrollFragment)
        }
        return binding.root
    }
}