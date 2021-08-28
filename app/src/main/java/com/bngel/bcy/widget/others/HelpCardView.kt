package com.bngel.bcy.widget.others

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import com.bngel.bcy.R
import com.bngel.bcy.activity.HelpDetailActivity
import kotlinx.android.synthetic.main.view_help_card.view.*

class HelpCardView: LinearLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, number: String, text: String, launcher: ActivityResultLauncher<Intent>): super(context) {
        text_HelpCardView.text = text
        this.setOnClickListener {
            val intent = Intent(context, HelpDetailActivity::class.java)
            intent.putExtra("number", number)
            launcher.launch(intent)
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_help_card, this)
    }
}