package com.bngel.bcy.activity

import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.MessageControllerService
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.MsgCardView
import kotlinx.android.synthetic.main.activity_msg_at.*
import kotlinx.android.synthetic.main.activity_msg_comment.*

class MsgAtActivity : BaseActivity() {

    private val msgService = MessageControllerService()
    private val MSG_COUNT = 10
    private var pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_at)
        initWidget()
    }

    private fun initWidget() {
        msgEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_MsgAtActivity.setOnClickListener {
            finish()
        }
    }

    private fun msgEvent() {
        val communityAtList =
            msgService.getCommunityAtList(MSG_COUNT, InfoRepository.user.id, pageNow)
        if (communityAtList != null && communityAtList.msg == "success") {
            val ats = communityAtList.data.atList
            cards_MsgAtActivity.removeAllViews()
            for (at in ats) {
                val view = MsgCardView(this, at.id, at.username, at.photo, at.description, at.type, at.cosOrQaNumber, at.info, at.isRead, at.createTime)
                cards_MsgAtActivity.addView(view)
            }
        }
    }

}