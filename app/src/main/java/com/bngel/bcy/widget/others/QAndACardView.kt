package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bngel.bcy.R

class QAndACardView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)

    init{
        LayoutInflater.from(context).inflate(R.layout.view_qanda_card, this)
    }
}