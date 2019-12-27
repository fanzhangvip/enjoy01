package com.zero.kotlinstudy.base

abstract class BasePresenter<T> where T:IBaseView{

    var mView:T? = null
    fun attachView(view:T){
        mView = view
    }

    fun detachView(){
        mView = null
    }

    fun isViewAttached():Boolean{
        return mView !=null
    }


}