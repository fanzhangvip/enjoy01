package com.zero.navigationdemo.deeplinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.databinding.FragmentDeepLinkSettingsBinding

class DeepLinkSettingsFragment: LifeCycleFragment() {
    private val binding by lazy { FragmentDeepLinkSettingsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }
}