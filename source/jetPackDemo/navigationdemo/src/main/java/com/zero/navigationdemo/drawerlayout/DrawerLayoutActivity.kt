package com.zero.navigationdemo.drawerlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.ActivityDrawerLayoutBinding
import com.zero.navigationdemo.toast

class DrawerLayoutActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var navController:NavController

    private val binding by lazy {
        ActivityDrawerLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController = findNavController(this,R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)

        NavigationUI.setupWithNavController(binding.navigationView,navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toast("onDestinationChanged:$destination")
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
