package com.zero.navigationdemo.bottomnavigationview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.zero.navigationdemo.R
import kotlinx.android.synthetic.main.activity_basic.toolbar
import kotlinx.android.synthetic.main.activity_bottomnavigation.*

class BottomNavigationAdvanceKotlinActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottomnavigation)
        if(savedInstanceState == null){
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar(){
        val navGraphIds = listOf(
            R.navigation.shopping,
            R.navigation.history,
            R.navigation.mine,
            R.navigation.about
        )

        val controller = bottomNavigationView.setupWithNavController1(
            navGraphIds,
            supportFragmentManager,
            R.id.frameLayout,
            intent
        )
        controller.observe(this, Observer {navController ->
            toolbar.setupWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp()?:false
    }


}
