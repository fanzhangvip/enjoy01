package com.zero.navigationdemo.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zero.navigationdemo.R
import kotlinx.android.synthetic.main.fragment_shopping.*
import kotlinx.android.synthetic.main.fragment_shopping.view.*
import kotlinx.android.synthetic.main.list_view_item.view.*

class ShoppingFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shopping,container,false)

        val myAdapter = MyAdapter(listData)
        view.recyclerView.run {
            setHasFixedSize(true)
            adapter = myAdapter
        }


        return view
    }


}
class MyAdapter(private val myDataset: Array<String>):RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() =  myDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            item.shopName.text = myDataset[position]
            item.setOnClickListener {
                val bundle = bundleOf(SHOP_KEY to myDataset[position])
            }
        }

    }

    companion object{
        const val SHOP_KEY = "com.zero.navigationdemo.shopping.shop_key"
    }
}
private val listData = arrayOf(
   "oppo R17",
    "帮宝适",
    "爽然",
    "杜蕾斯"
)