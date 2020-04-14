package com.zero.navigationdemo.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.ActivityDrawerLayoutBinding
import com.zero.navigationdemo.databinding.ActivityMenuBinding
import com.zero.navigationdemo.databinding.ActivityToobarBinding
import com.zero.navigationdemo.toast

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var navController:NavController

    private val binding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController = findNavController(this,R.id.navHostFragment)

        navController.addOnDestinationChangedListener { controller, destination, args ->
            toast("${destination.label}")
        }
    }


    /**
     * 加载菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        appBarConfiguration = AppBarConfiguration(menu!!)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        menuInflater.inflate(R.menu.menu_settings,menu)
        return true
    }

    /**
     * 处理actionbar中的按钮被点击时，根据菜单中的id,自动跳转到相应的页面
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,navController)||super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
