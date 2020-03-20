package com.zero.navigationdemo

import android.content.Intent
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
): LiveData<NavController>{

    val graphToTagMap = SparseArray<String>()
    val selectedNavController = MutableLiveData<NavController>()

    var firstFragmentGraphId = 0;

    navGraphIds.forEachIndexed{index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(
            fragmentManager,fragmentTag,navGraphId,containerId
        )

        val graphId = navHostFragment.navController.graph.id
        if(index == 0){
            firstFragmentGraphId = graphId
        }

        if(selectedItemId == graphId){
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager,navHostFragment, index == 0)
        }else{
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    var selectItemTag = graphToTagMap[selectedItemId]
    val firstFragmentTag = graphToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectItemTag == firstFragmentTag

    setOnNavigationItemSelectedListener { item ->
        if(fragmentManager.isStateSaved){
            false
        }else{
            val newSelectItemTag = graphToTagMap[item.itemId]
            if(selectItemTag != newSelectItemTag){
                fragmentManager
                    .popBackStack(firstFragmentTag,FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val selectFragment = fragmentManager.findFragmentByTag(newSelectItemTag)
                as NavHostFragment

                if(firstFragmentTag != newSelectItemTag){
                    fragmentManager.commit {
                        attach(selectFragment)
                        setPrimaryNavigationFragment(selectFragment)
                        graphToTagMap.forEach{_,fragmentTagIter ->
                            if(fragmentTagIter != newSelectItemTag){
                                detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                            }
                        }
                        addToBackStack(firstFragmentTag)
                        setReorderingAllowed(true)
                    }
                }
                selectItemTag = newSelectItemTag
                isOnFirstFragment = selectItemTag == firstFragmentTag
                selectedNavController.value = selectFragment.navController
                true
            }else{
               false
            }
        }
    }//end setOnNavigationItemSelectedListener

    setupItemReselected(graphToTagMap,fragmentManager)

    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }

    return selectedNavController
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as? NavHostFragment
        val navController = selectedFragment?.navController
        // Pop the back stack to the start destination of the current navController graph
        navController?.popBackStack(
            navController.graph.startDestination, false
        )
    }
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.commitNow {
        detach(navHostFragment)
    }
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.commitNow {
        attach(navHostFragment)
        if(isPrimaryNavFragment){
            setPrimaryNavigationFragment(navHostFragment)
        }
    }
}


//单个设置NavHostFragment
private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
):NavHostFragment{
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?

    existingFragment?.let { return it }

    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.commitNow {
        add(containerId,navHostFragment,fragmentTag)
    }
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean{
    val backStackCount = backStackEntryCount
    for(index in 0 until backStackCount){
        if(getBackStackEntryAt(index).name == backStackName){
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "bottomNavigation$index"