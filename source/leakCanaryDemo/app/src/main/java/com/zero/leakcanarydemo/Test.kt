package com.zero.leakcanarydemo

import android.widget.TextView
import shark.HeapObject

class TestDataModel{
    @JvmField
    var mRetainedTextView: TextView? = null

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