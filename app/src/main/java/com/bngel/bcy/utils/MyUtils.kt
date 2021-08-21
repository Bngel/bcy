package com.bngel.bcy.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object MyUtils {

    fun fromUtcToCst(utcTime: String): String {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss"
        val dfParse = SimpleDateFormat(pattern, Locale.ENGLISH)
        dfParse.timeZone = TimeZone.getTimeZone("UTC")
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
        val dateTime = dfParse.parse(utcTime)
        return df.format(dateTime)
    }

    fun fromPxToDp(context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (px/scale + 0.5f).toInt()
    }

    fun fromStringToList(string: String): List<String> {
        return string.substring(1, string.length-1).split(",")
    }
}