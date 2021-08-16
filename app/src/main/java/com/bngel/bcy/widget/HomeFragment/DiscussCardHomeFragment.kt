package com.bngel.bcy.widget.HomeFragment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bngel.bcy.R

class DiscussCardHomeFragment : LinearLayout {

    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet):super(context)
    constructor(context: Context, number: String):super(context) {
        
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_discuss_card, this)
    }
}