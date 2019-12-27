package com.zero.kotlinstudy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<P> : Fragment() ,IBaseView where P:BasePresenter<IBaseView>{

    lateinit var mPresenter:P
    lateinit var mActivity: BaseActivity<BasePresenter<IBaseView>>

    lateinit var mRootView:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(layoutId(),container,false)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity<BasePresenter<IBaseView>>
    }

    abstract fun layoutId():Int
    abstract fun initDate()

}