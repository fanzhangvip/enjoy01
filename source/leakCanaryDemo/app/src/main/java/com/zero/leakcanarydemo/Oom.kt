package com.zero.leakcanarydemo

import android.content.Context
import android.os.Debug
import java.io.File

public class OomExceptionHandle(@JvmField private val defaultHandler: Thread.UncaughtExceptionHandler,@JvmField private var context: Context) : Thread.UncaughtExceptionHandler{

    init {
        context = context.applicationContext
    }

    companion object{
        const val TAG = "Zero"
        const val FILENAME = "out-of-memory.hprof"

        @JvmStatic fun install(context: Context){
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            if(defaultHandler is OomExceptionHandle) return
            val oomHandler = OomExceptionHandle(defaultHandler,context)
            Thread.setDefaultUncaughtExceptionHandler(oomHandler)
        }
    }

    private fun containsOom(ex: Throwable): Boolean{
        if(ex is OutOfMemoryError) return true
        var extemp:Throwable? = null
        while(true){
            extemp = ex.cause
            if(extemp == null)break
            if(extemp is OutOfMemoryError)return true
        }
        return false
    }


    override fun uncaughtException(t: Thread, e: Throwable) {
        if(containsOom(e)){
            val heapDumpFile = File(context.filesDir, FILENAME)
            try{
                Debug.dumpHprofData(heapDumpFile.absolutePath)
            }catch (ignored: Throwable){}
        }
        defaultHandler.uncaughtException(t,e)
    }

}