package com.zero.leakcanarydemo

import android.app.Activity
import android.widget.TextView

class TestDataModel{
    var mRetainedTextView: TextView? = null
    var activitys = mutableListOf<Activity>()

    companion object{//static
        @JvmStatic
        private  var  sInstance: TestDataModel? = null //gcroot

        @JvmStatic
        public fun getInstance(): TestDataModel{
            if(sInstance == null){
                sInstance = TestDataModel()
            }
            return sInstance!!
        }
    }

}

class Cat{}
class Box(var hiddenCat: Cat? = null){

}

class Docker{
    companion object{
        @JvmStatic var container:Box? = null
    }
}
fun catTest(){
    val box = Box()
    val schrodingerCat = Cat()
    box.hiddenCat = schrodingerCat
    Docker.container = box
    //在你认为这个对象不需要使用了 null 检测
//    AppWatcher.objectWatcher.watch(schrodingerCat,"schrodingerCat")
}