package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.HelpControllerService
import kotlinx.android.synthetic.main.activity_help_detail.*

class HelpDetailActivity : BaseActivity() {

    private val helpService = HelpControllerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_detail)
        initWidget()
    }

    private fun initWidget() {
        helpEvent()
    }

    private fun helpEvent() {
        val number = intent.getStringExtra("number")?:""
        val userHelp = helpService.getUserHelp(number)
        if (userHelp != null && userHelp.msg == "success") {
            val helpQuestion = userHelp.data.help.question
            val helpAnswer = userHelp.data.help.answer
            title_HelpDetailActivity.text = helpQuestion
            content_HelpDetailActivity.text = helpAnswer
        }
    }
}