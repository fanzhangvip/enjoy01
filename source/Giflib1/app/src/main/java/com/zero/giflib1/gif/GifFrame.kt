package com.zero.giflib1.gif

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import java.io.InputStream

class GifFrame //JNI 创建java对象
private constructor(
    //C++ 一帧的对象 指针
    var nativeHandle: Long,
    val width: Int,
    val height: Int,
    val frameCount: Int
) {
    constructor() : this(0, 0, 0, 0)


    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        const val TAG = "Zero"

        @JvmStatic
        private external fun nativeDecodeStream(
            stream: InputStream,
            buffer: ByteArray
        ): GifFrame

        @JvmStatic
        private external fun nativeDecodeStreamJNI(
            assetManager: AssetManager?,
            gifPath: String
        ): GifFrame

        @JvmStatic
        private external fun nativeGetFrame(
            nativeHandle: Long,
            bitmap: Bitmap,
            frameIndex: Int
        ): Long

        fun decodeStream(stream: InputStream): GifFrame {
            val buffer = ByteArray(16 * 1024)
            return nativeDecodeStream(stream, buffer)
        }

        fun decodeStream(context: Context?, fileName: String): GifFrame {
            return nativeDecodeStreamJNI(
                context?.assets,
                fileName
            )
        }
    }

    fun getFrame(bitmap: Bitmap, frameIndex: Int): Long {
        Log.i(TAG,"getFrame")
        return nativeGetFrame(nativeHandle, bitmap, frameIndex)
    }

}