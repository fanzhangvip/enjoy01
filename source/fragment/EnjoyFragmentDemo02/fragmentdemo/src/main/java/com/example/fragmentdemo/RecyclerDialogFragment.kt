package com.example.fragmentdemo

import android.app.Dialog
import android.os.Bundle
import android.util.SparseArray
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.fragmentdemo.databinding.DialogBle1Binding
import com.example.fragmentdemo.databinding.DialogBleBinding
import kotlinx.android.synthetic.main.dialog_ble1.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import org.jetbrains.anko.support.v4.toast

class RecyclerDialogFragment: DialogFragment() {

    companion object{
        const val TAG = "Zero"
    }

    var datas= mutableListOf<String>()
    set(value) {
        field.addAll(value)
        bleAdapter.notifyDataSetChanged()
    }

    private val bleAdapter = BleRecycleAdapter(datas,R.layout.recyclerview_item,
        {view, bluetoothDevice ->
            view.textviewItem.text = bluetoothDevice
        },
        {_, bluetoothDevice ->
           toast(bluetoothDevice)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //方法一
//        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onStart() {
        super.onStart()
        //方法二
//        dialog?.let {
//            val dm = DisplayMetrics()
//            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
//            it.window?.setLayout((dm.widthPixels * 0.9f).toInt(),ViewGroup.LayoutParams.MATCH_PARENT)
//        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogBle1Binding.inflate(layoutInflater)
        val dialog = Dialog(activity!!)
        dialog.setContentView(binding.root)
        dialog.show()

        dialog.window?.apply {
            setGravity(Gravity.CENTER)
            decorView.setPadding(0,0,0,0)

            val lp = attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            attributes = lp
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = DialogBle1Binding.inflate(inflater)
//        val binding = DialogBleBinding.inflate(inflater)
//        binding.root.textviewCancel.setOnClickListener {
//            dismiss()
//        }
//        val root = inflater.inflate(R.layout.dialog_ble1,container,true)
//        val recycleView = root.findViewById<RecyclerView>(R.id.recyclerviewBle)
//        recycleView.apply {
//            layoutManager= LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
//            setHasFixedSize(true)
//            adapter = myadapter
//        }
//        myadapter.notifyDataSetChanged()
//        return binding.root
        return super.onCreateView(inflater, container, savedInstanceState)
    }




}