package com.bngel.bcy.widget.MainActivity

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bngel.bcy.R
import com.bngel.bcy.utils.AnimRepository
import java.util.zip.Inflater

class ItemTabMainActivity : RelativeLayout {

    var view: View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.item_tab_main_activity, this)
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, text: String): super(context) {
        setText(text)
    }

    private fun setText(text: String) {
        val textView = view?.findViewById<TextView>(R.id.text_tab_MainActivity)
        textView?.text = text
    }

    private fun setBottomColor(color: Int) {
        val imageView = view?.findViewById<ImageView>(R.id.bottom_tab_MainActivity)
        imageView?.setBackgroundColor(color)
    }

    private fun setBottomTransparent() {
        val imageView = view?.findViewById<ImageView>(R.id.bottom_tab_MainActivity)
        imageView?.background?.alpha = 0
    }

    fun setClicked() {
        val textView = view?.findViewById<TextView>(R.id.text_tab_MainActivity)
        AnimRepository.playTextEnlargedClickAnim(textView!!, 1)
        setBottomColor(Color.parseColor("#CCBE97"))
    }

    fun setUnClicked() {
        val textView = view?.findViewById<TextView>(R.id.text_tab_MainActivity)
        AnimRepository.playTextReducedClickAnim(textView!!, 1)
        setBottomTransparent()
    }
}