package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bngel.bcy.R
import kotlinx.android.synthetic.main.view_label_text.view.*

class TagTextView: LinearLayout {

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, tag: String): super(context) {
        tag_text_TagTextView.text = tag
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_label_text, this)
    }
}