package com.example.fragmentdemo.basic

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentdemo.R

class TaskDemo1Activity : AppCompatActivity() {
    private var mAdapter: ListAdapter? = null
    private var mDatas: List<String>? = null
    private var dataFragment: OtherRetainedFragment? = null
    private var mMyTask: MyAsyncTask? = null
    private var listView: ListView? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        setContentView(R.layout.activity_list)
        listView = findViewById(R.id.listview)
        // find the retained fragment on activity restarts
        val fm = supportFragmentManager
        dataFragment = fm.findFragmentByTag("data") as OtherRetainedFragment?
        // create the fragment and data the first time
        if (dataFragment == null) { // add the fragment
            dataFragment = OtherRetainedFragment()
            fm.beginTransaction().add(dataFragment!!, "data").commit()
        }
        mMyTask = dataFragment!!.data
        if (mMyTask != null) {
            mMyTask!!.setActivity(this)
        } else {
            mMyTask = MyAsyncTask(this)
            dataFragment!!.data = mMyTask
            mMyTask!!.execute()
        }
        // the data is available in dataFragment.getData()
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)
        Log.e(TAG, "onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mMyTask!!.setActivity(null)
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState")
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    /**
     * 回调
     */
    fun onTaskCompleted() {
        mDatas = mMyTask!!.items
        mAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, mDatas!!)
        listView!!.adapter = mAdapter
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}