package com.example.livedatademo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.doAsync

class MyViewModel  : ViewModel{

    private lateinit var myCount: MutableLiveData<Int>
    private lateinit var handle: SavedStateHandle

    constructor(handle: SavedStateHandle):super(){
        if(!handle.contains(MainActivity.My_KEY)){
            handle.set(MainActivity.My_KEY,0)
        }
        this.handle = handle
    }

    fun getNumber(): MutableLiveData<Int>{
        return handle.getLiveData(MainActivity.My_KEY)
//        if(!::myCount.isInitialized){
//            myCount = MutableLiveData<Int>()
//            myCount.value = 0
//        }
//        return myCount
    }

    fun addNumber(){
//        myCount.value = myCount.value?.plus(1)
        handle.run {
            set(MainActivity.My_KEY,get<Int>(MainActivity.My_KEY)?.plus(1))
        }
    }




}