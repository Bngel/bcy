package com.bngel.bcy.utils

import android.app.Activity
import android.content.Context
import com.bngel.bcy.activity.MainActivity

object ActivityCollector {

    val activities = ArrayList<Activity>()
    var curActivity : Context? = null

    fun finishAllActivities() {
        for (activity in activities) {
            if (!activity.isFinishing)
                activity.finish()
        }
    }
}