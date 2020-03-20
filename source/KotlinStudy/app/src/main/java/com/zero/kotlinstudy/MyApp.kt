package com.zero.kotlinstudy

import android.app.Application
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zero.kotlinstudy.extensions.DelegatesExt

open class KBaseModel<T>{}
open class KBaseViewMode<V,M:KBaseModel<View>>{}
class K1:KBaseViewMode<View,KBaseModel<View>>(){}
abstract class BaseActivity<K:KBaseViewMode<View,KBaseModel<View>>>{}
class KMvvMActivity:BaseActivity<K1>(){}

//泛型问题
interface IBaseModel{}
interface IBaseView{}
open class BaseModel:IBaseModel{
    constructor(any: Any){}
}
abstract class CommonModel constructor(any: Any) :BaseModel(any){}
open class BasicModel constructor(any: Any): CommonModel(any){}
abstract class BaseView:IBaseView{}
interface ApiResponseCallback {}
interface IBasePresenter:ApiResponseCallback{}
abstract class BasePresenter<out M: IBaseModel,out V: IBaseView> : IBasePresenter {

    var  m: @UnsafeVariance M? = null

}
class TestView: BaseView() {}
class TestModel constructor(any: Any) : BasicModel(any) {}
class TestPresenter: BasePresenter<TestModel, TestView>() {}

abstract class BaseMVPActivity<P: BasePresenter<IBaseModel, IBaseView>> : AppCompatActivity(),IBaseView{}
class TestActivity : BaseMVPActivity<TestPresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
    }

}
class MyApp : Application(){

    companion object{
        var instance: MyApp by DelegatesExt.notNullsingleValue<MyApp>()
    }

    override fun onCreate() {
        super.onCreate()
        //初始化
        instance = this
    }
}