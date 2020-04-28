package com.example.bledemo

import android.app.AlertDialog
import android.app.Dialog
import android.bluetooth.BluetoothDevice
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.bledemo.databinding.DialogBle1Binding
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerDialogFragment: DialogFragment() {

    companion object{
        const val TAG = "Zero"
    }

    var datas= mutableListOf<BluetoothDevice>()
    set(value) {
        field.addAll(value)
        myadapter.notifyDataSetChanged()
    }


    private val myadapter = BleRecycleAdapter(datas!!,R.layout.recyclerview_item,
        {view, bluetoothDevice ->
            view.textviewItem.text = "${bluetoothDevice.name}, address: ${bluetoothDevice.address}"
        },
        {view, bluetoothDevice ->
            view.toast("点击了${bluetoothDevice.name}")
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogBle1Binding.inflate(inflater)
        val root = inflater.inflate(R.layout.dialog_ble,container)
//        val recycleView = root.findViewById<RecyclerView>(R.id.recyclerviewBle)
//        recycleView.apply {
//            layoutManager= LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
//            setHasFixedSize(true)
//            adapter = myadapter
//        }
//        myadapter.notifyDataSetChanged()
        return root
    }




}