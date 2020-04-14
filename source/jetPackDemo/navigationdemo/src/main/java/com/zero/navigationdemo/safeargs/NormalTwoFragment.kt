package com.zero.navigationdemo.safeargs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zero.navigationdemo.LifeCycleFragment
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.FragmentArgsOneBinding
import com.zero.navigationdemo.databinding.FragmentArgsTwoBinding
import com.zero.navigationdemo.databinding.FragmentOneBinding

class NormalTwoFragment : LifeCycleFragment() {

    private val binding by lazy {
        FragmentArgsTwoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        bundle?.let {
            binding.showMsg.text = "名称: ${it["user_name"]},年龄: ${it["age"]}"
        }
        return binding.root
    }

}