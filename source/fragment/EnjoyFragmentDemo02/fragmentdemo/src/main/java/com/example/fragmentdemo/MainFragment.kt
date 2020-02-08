package com.example.fragmentdemo

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment

class MainFragment : ListFragment() {
    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array = arrayOf(
                "getActivity==null",
                "Can not perform this action after onSaveInstanceState",
                "Fragment重叠异常",
                "嵌套的fragment不能在onActivityResult()中接收到返回值",
                "未必靠谱的出栈方法remove()",
                "mAvailIndeices的BUG",
                "popBackStack的坑",
                "pop多个Fragment时转场动画 带来的问题",
                "进入新的Fragment并立刻关闭当前Fragment 时的一些问题",
                "Fragment+viewpager"
        )
        arrayAdapter = ArrayAdapter(activity!!, R.layout.simple_list_item_1, array)
        listAdapter = arrayAdapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) { // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id)
        val item = arrayAdapter.getItem(position)
        Toast.makeText(activity, item, Toast.LENGTH_LONG).show()
        when (position) {
            0 -> {

            }
            1 -> {
            }
            2 -> {
            }
            3 -> {
            }
            else -> {
            }
        }
    }

    companion object {
        fun newIntance(): Fragment {
            return MainFragment()
        }
    }
}