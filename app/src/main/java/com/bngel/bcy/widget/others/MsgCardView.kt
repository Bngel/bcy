package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bngel.bcy.R
import com.bngel.bcy.utils.MyUtils
import kotlinx.android.synthetic.main.view_msg_card.view.*

class MsgCardView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(
        context: Context, id: String, username: String, photo: String?, description: String,
        type: Int, number: String, info: String, isRead: Int, createTime: String): super(context){
        avt_MsgCardView.setAvt(photo)
        username_MsgCardView.text = username
        description_MsgCardView.text = description
        date_MsgCardView.text = MyUtils.fromUtcToCst(createTime)
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_msg_card, this)
    }
}