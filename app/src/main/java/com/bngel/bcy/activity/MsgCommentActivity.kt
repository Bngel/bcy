package com.bngel.bcy.activity

import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.MessageControllerService
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.MsgCardView
import kotlinx.android.synthetic.main.activity_msg_comment.*

class MsgCommentActivity : BaseActivity() {

    private val msgService = MessageControllerService()
    private val MSG_COUNT = 10
    private var pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_comment)
        initWidget()
    }

    private fun initWidget() {
        msgEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_MsgCommentActivity.setOnClickListener {
            finish()
        }
    }

    private fun msgEvent() {
        val communityCommentList =
            msgService.getCommunityCommentList(MSG_COUNT, InfoRepository.user.id, pageNow)
        if (communityCommentList != null && communityCommentList.msg == "success") {
            val ats = communityCommentList.data.commentList
            cards_MsgCommentActivity.removeAllViews()
            for (at in ats) {
                val view = MsgCardView(this, at.id, at.username, at.photo, at.description, at.type, at.cosOrQaNumber, at.info, at.isRead, at.createTime)
                cards_MsgCommentActivity.addView(view)
            }
        }
    }
}