package com.bngel.bcy.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.bngel.bcy.utils.ActivityCollector

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ActivityCollector.activities.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.activities.remove(this)
    }

    override fun onResume() {
        super.onResume()
        ActivityCollector.curActivity = this
    }

}