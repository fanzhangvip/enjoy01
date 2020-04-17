package com.zero.navigationdemo

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.zero.navigationdemo.basic.BasicActivity
import com.zero.navigationdemo.bottomnavigationview.BottomNavigationAdvanceKotlinActivity
import com.zero.navigationdemo.bottomnavigationview.BottomNavigationAdvancedActivity
import com.zero.navigationdemo.deeplinks.DeepLinksActivity
import com.zero.navigationdemo.drawerlayout.DrawerLayoutActivity
import com.zero.navigationdemo.fix.BasicFixActivity
import com.zero.navigationdemo.menu.MenuActivity
import com.zero.navigationdemo.safeargs.SafeArgsActivity
import com.zero.navigationdemo.toolbar.ToolBarActivity
import com.zero.navigationdemo.toolbar.ToolBarAdvancedActivity

class MainFragment : ListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array = arrayOf(
            "Navigation基本使用",//0
            "SafeArgs的使用",//1
            "DeepLink的使用",//2
            "与Toolbar一起使用",//3
            "与menu一起使用",//4
            "与drawerLayout一起使用",//5
            "与Toolbar高级使用",//6
            "与BottomNavigation联合使用",//7
            "与BottomNavigation进阶使用",//8
            "与BottomNavigation进阶使用java版本",//9
            "Navigation缺陷修复使用"//10
        )
        listAdapter  = ArrayAdapter(requireActivity(), R.layout.simple_list_item_1, array)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val item = listAdapter?.getItem(position) as String
        toast(item)
        when (position) {
            0 -> {
                startActivity(Intent(requireActivity(),BasicActivity::class.java))
            }
            1 -> {
                startActivity(Intent(requireActivity(),SafeArgsActivity::class.java))
            }
            2 -> {
                startActivity(Intent(requireActivity(),DeepLinksActivity::class.java))
            }
            3 -> {
                startActivity(Intent(requireActivity(),ToolBarActivity::class.java))
            }
            4 -> {
                startActivity(Intent(requireActivity(),MenuActivity::class.java))
            }
            5 -> {
                startActivity(Intent(requireActivity(),DrawerLayoutActivity::class.java))
            }
            6 -> {
                startActivity(Intent(requireActivity(),
                    ToolBarAdvancedActivity::class.java))
            }
            7 -> {
                startActivity(Intent(requireActivity(),
                    BottomNavigationAdvancedActivity::class.java))
            }
            8 -> {
                startActivity(Intent(requireActivity(),BottomNavigationAdvanceKotlinActivity::class.java))
            }
            9 ->{
                startActivity(Intent(requireActivity(),
                    BottomNavigationAdvanceKotlinActivity::class.java))
            }
            10 ->{
                startActivity(Intent(requireActivity(),
                    BasicFixActivity::class.java))
            }
           // else -> { }
        }
    }
}