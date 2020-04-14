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
import com.zero.navigationdemo.databinding.FragmentOneBinding

class SafeArgsOneFragment : LifeCycleFragment() {

    private val binding by lazy {
        FragmentArgsOneBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.button.setOnClickListener {
            //普通方式
            val bundle = Bundle()
            bundle.putString("user_name", "zero老师")
            bundle.putInt("age", 18)
            findNavController().navigate(R.id.action_safeArgsOneFragment_to_normalTwoFragment,bundle)
        }
        binding.button1.setOnClickListener {
            //安全参数
            val args = SafeArgsOneFragmentArgs("Lance老师",20)
//            findNavController().navigate(SafeArgsOneFragmentDirections.actionSafeArgsOneFragmentToSafeArgsTwoFragment().actionId,args.toBundle())
            findNavController().navigate(R.id.action_safeArgsOneFragment_to_safeArgsTwoFragment,args.toBundle())

        }
        return binding.root
    }

}