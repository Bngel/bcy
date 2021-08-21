package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bngel.bcy.R
import com.bngel.bcy.utils.MyUtils
import kotlinx.android.synthetic.main.view_discuss_comment.view.*

class DiscussCommentView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, commentNumber: String, username: String, photo: String, description: String, createTime: String): super(context) {
        username_DiscussCommentView.text = username
        avt_DiscussCommentView.setAvt(photo)
        description_DiscussCommentView.text = description
        date_DiscussCommentView.text = MyUtils.fromUtcToCst(createTime)
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_discuss_comment, this)
    }
}