package com.example.retrofitmvp.view

import android.app.Dialog
import android.content.Context
import com.example.retrofitmvp.R

object LoadingDialog {

    private var dialog: Dialog? = null

    fun show(context: Context){
        cancle()
        dialog = Dialog(context)
        dialog?.run {
            setContentView(R.layout.dialog_loading)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            this
        }?.show()
    }

    fun cancle(){
        dialog?.dismiss()
    }

}