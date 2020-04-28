package com.example.bledemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BleRecycleAdapter<T> constructor(
    private val dataList: List<T>,
    private val layoutId: Int,
    private var addBindView: ((View, T) -> Unit)? = null,
    private var itemClick: ((View, T) -> Unit)? = null
) : RecyclerView.Adapter<BleRecycleAdapter<T>.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        addBindView?.invoke(holder.itemView, dataList[position])
        holder.itemView.setOnClickListener {
            itemClick?.invoke(it, dataList[position])
        }
    }

}