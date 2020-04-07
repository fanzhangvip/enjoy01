package com.example.bledemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.view.View
import android.widget.Toast


fun View.toast(msg: String){
    android.widget.Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}
fun Context.toast(msg: String){
    android.widget.Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun Context.isLocationEnable(): Boolean{
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    val gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    return networkProvider||gpsProvider
}

const val  REQUEST_CODE_LOCATION_SETTING = 101

fun Activity.setLocationService(){
    val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    startActivityForResult(locationIntent, REQUEST_CODE_LOCATION_SETTING)
}