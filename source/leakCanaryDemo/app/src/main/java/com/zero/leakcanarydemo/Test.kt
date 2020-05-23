package com.zero.leakcanarydemo

import android.app.Activity
import android.widget.TextView
import leakcanary.AppWatcher

class TestDataModel{
    var mRetainedTextView: TextView? = null
    var activitys = mutableListOf<Activity>()

    companion object{
        @JvmStatic
        private  var  sInstance: TestDataModel? = null

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
    AppWatcher.objectWatcher.watch(schrodingerCat,"schrodingerCat")
}