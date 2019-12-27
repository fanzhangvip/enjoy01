package com.zero.kotlinstudy.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<P:BasePresenter<IBaseView>> : AppCompatActivity(),IBaseView{

    lateinit var mPersenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPersenter = createPresenter()
        if(::mPersenter.isInitialized){
            mPersenter.attachView(this)
        }
        initView()
        initData()



    }

    override fun onDestroy() {
        super.onDestroy()
        if(::mPersenter.isInitialized){
            mPersenter.detachView()

        }
    }

    private fun setUpActionBar() {
//        toolbar?.apply {
//            setSupportActionBar(this)
//        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    abstract fun createPresenter():P

    abstract fun getLayoutId():Int

    abstract fun initView()

    abstract fun initData()


}