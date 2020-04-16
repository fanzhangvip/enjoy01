package com.example.fragmentdemo.basic

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentdemo.ProgressDialog
import com.example.fragmentdemo.R
import com.example.fragmentdemo.RecyclerDialogFragment
import com.example.fragmentdemo.databinding.ActivityProgressdialogBinding
import org.jetbrains.anko.toast

class ProgressDialogDemoActivity: AppCompatActivity() {

   private val progressDialog = RecyclerDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProgressdialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showProgress(view: View) {
        toast("show dialog")
        progressDialog.show(supportFragmentManager,"show")
    }
    fun hideProgress(view: View) {
        progressDialog.dismiss()
    }
}