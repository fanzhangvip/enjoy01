package com.zero.kotlinstudy

import android.os.Bundle
import android.widget.TextView
import com.zero.kotlinstudy.base.BaseActivity
import com.zero.kotlinstudy.base.BasePresenter
import com.zero.kotlinstudy.base.IBaseView
import com.zero.kotlinstudy.extensions.Perference
import org.jetbrains.annotations.NotNull

class MainActivity : BaseActivity<BasePresenter<IBaseView>>(){//extends implements

    //private Textview mTextView =null;
//    private TextView mTextView
    private var test by Perference("key","23")

    lateinit var mTextView: TextView

    class User{

    }

    override fun onCreate(@NotNull savedInstanceState: Bundle?) {//@override
        super.onCreate(savedInstanceState)

        val str = test

        test = "345"
    }

    override fun getLayoutId()= R.layout.activity_main

    override fun initView() {
        mTextView = findViewById(R.id.tv_text)

    }

    override fun initData() {

    }

    override fun createPresenter(): BasePresenter<IBaseView> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
