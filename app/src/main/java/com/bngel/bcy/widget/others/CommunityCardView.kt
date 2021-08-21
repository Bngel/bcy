package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bngel.bcy.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_avator.view.*
import kotlinx.android.synthetic.main.view_community_card.view.*

class CommunityCardView: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, image: String, name: String, description: String, fans: Int):super(context) {
        Glide.with(context)
            .load(image)
            .placeholder(R.drawable.rem)
            .error(R.drawable.rem)
            .into(image_CommunityCardView)
        circle_name_CommunityCardView.text = name
        circle_description_CommunityCardView.text = description
        circle_fans_CommunityCardView.text = fans.toString() + "个成员"
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_community_card, this)
    }
}