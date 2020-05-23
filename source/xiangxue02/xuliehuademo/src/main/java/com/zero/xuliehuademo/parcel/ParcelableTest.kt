package com.zero.xuliehuademo.parcel

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.zero.xuliehuademo.TAG
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course1(var name: String, var score: Float) : Parcelable


fun parcelTest() {

    var parcel = Parcel.obtain()
    parcel.writeInt(19)
    parcel.writeInt(12)

    val bs = parcel.marshall()
    Log.i(TAG,"bs = $bs, ${bs.size}")

    parcel.setDataPosition(0)
    parcel.recycle()

    parcel = Parcel.obtain()

    parcel.unmarshall(bs,0,bs.size)

    val size = parcel.dataSize()
    for(i in 0 .. size step 4){
        parcel.setDataPosition(i)
        Log.i(TAG,"value = ${parcel.readInt()}")
    }
    parcel.recycle()


}