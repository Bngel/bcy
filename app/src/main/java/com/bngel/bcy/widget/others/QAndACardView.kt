package com.bngel.bcy.widget.others

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import com.bngel.bcy.R
import com.bngel.bcy.activity.QAndADetailActivity
import com.bngel.bcy.utils.MyUtils
import kotlinx.android.synthetic.main.view_qanda_card.view.*
import java.lang.StringBuilder

class QAndACardView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, number: String, count: Int, title: String, description: String, date: String, tags: List<String>, launcher: ActivityResultLauncher<Intent>):super(context) {
        qa_count_QAndACardView.text = if (count <= 0) "" else count.toString()
        title_QAndACardView.text = title
        description_QAndACardView.text = description
        date_QAndACardView.text = MyUtils.fromUtcToCst(date)
        val tagText = StringBuilder()
        for (tag in tags)
            tagText.append(" $tag")
        tags_QAndACardView.text = tagText
        this.setOnClickListener {
            val intent = Intent(context, QAndADetailActivity::class.java)
            intent.putExtra("number", number)
            launcher.launch(intent)
        }
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_qanda_card, this)
    }
}