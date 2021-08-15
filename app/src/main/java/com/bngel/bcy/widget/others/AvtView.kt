package com.bngel.bcy.widget.others

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bngel.bcy.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_avator.view.*

class AvtView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_avator, this)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AvtView)
        avt_img.setImageDrawable(ta.getDrawable(0))
        ta.recycle()
    }

    fun setAvt(image: Drawable) {
        avt_img.setImageDrawable(image)
    }

    fun setAvt(url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.rem)
            .error(R.drawable.rem)
            .into(avt_img)
    }

}