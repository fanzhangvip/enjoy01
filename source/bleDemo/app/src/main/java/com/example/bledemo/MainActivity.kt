package com.example.bledemo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "Zero"
    }

    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
                toast("自Android6开始需要打开位置权限才可以搜索到Ble设备")
            }
            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION),666)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_LOCATION_SETTING){
            if(isLocationEnable()){
                Log.i(TAG,"定位已经打开了")
            }else{
                Log.i(TAG,"定位没有打开")
            }
        }else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 666){
            if(grantResults.isNotEmpty() && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                toast("授权成功")
            }else{
                toast("授权失败")
                setLocationService()
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    private val  deviceList = mutableListOf<BluetoothDevice>()

    private val bluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        Log.i("Zero","bluetoothManager: $bluetoothManager")
        bluetoothManager.adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
    }

    fun  onScan(view: View): Unit{

        Log.i("Zero","onScan")
        deviceList.clear()

//        bluetoothAdapter.startLeScan(scanCallback)

        showBle.show(supportFragmentManager,"ddd")
    }

    private val showBle = RecyclerDialogFragment()
    private val scanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        Log.i(TAG,"name: ${device.name} , mac: ${device.address}")
        if(!deviceList.contains(device)){
            runOnUiThread {
                deviceList.add(device)
                showBle.datas = deviceList
            }
        }
    }

    fun onStopScan(view: View): Unit{
        bluetoothAdapter.stopLeScan { device, rssi, scanRecord ->

        }
        showBle.dismiss()
    }


}
