package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.HelpControllerService
import com.bngel.bcy.widget.others.HelpCardView
import kotlinx.android.synthetic.main.activity_help_and_reply.*

class HelpAndReplyActivity : BaseActivity() {

    private val helpService = HelpControllerService()
    private var pageNow = 1
    private val HELP_COUNT = 10
    private var detailLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_and_reply)
        registerLaunch()
        initWidget()
    }

    private fun initWidget() {
        helpEvent()
    }

    private fun registerLaunch() {
        detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun helpEvent() {
        val userHelpList = helpService.getUserHelpList(HELP_COUNT, pageNow, 0)
        if (userHelpList != null && userHelpList.msg=="success") {
            val helps = userHelpList.data.helpList
            help_cards_HelpAndReplyActivity.removeAllViews()
            for (help in helps) {
                val view = HelpCardView(this, help.number, help.question, detailLauncher!!)
                help_cards_HelpAndReplyActivity.addView(view)
            }
        }
    }
}