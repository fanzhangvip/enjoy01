package com.zero.navigationdemo.safeargs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentArgsOneBinding
import com.zero.navigationdemo.databinding.FragmentArgsTwoBinding
import com.zero.navigationdemo.databinding.FragmentOneBinding

class SafeArgsTwoFragment : LifeCycleFragment() {

    private val binding by lazy {
        FragmentArgsTwoBinding.inflate(layoutInflater)
    }

    private val args: SafeArgsOneFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.showMsg.text = "安全：名称: ${args.userName},年龄: ${args.age}"
        return binding.root
    }

}