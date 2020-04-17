package com.zero.navigationdemo.toolbar

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zero.navigationdemo.R
import com.zero.navigationdemo.databinding.ActivityToolbarAdvancedBinding
import com.zero.navigationdemo.toast

class ToolBarAdvancedActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var navController:NavController

    private val binding by lazy {
        ActivityToolbarAdvancedBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.mipmap.ic_drawer_home)
        binding.collapsingToolbarLayout.title = "ToolBar高级应用"
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        binding.collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE)
        binding.iv.setImageResource(R.mipmap.ic_bg)

        navController = findNavController(this,R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.collapsingToolbarLayout.setupWithNavController(binding.toolbar,navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toast("${destination.label}")
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
