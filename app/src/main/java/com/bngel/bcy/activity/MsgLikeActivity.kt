package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.MessageControllerService
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.MsgCardView
import kotlinx.android.synthetic.main.activity_msg_comment.*
import kotlinx.android.synthetic.main.activity_msg_like.*

class MsgLikeActivity : BaseActivity() {

    private val msgService = MessageControllerService()
    private val MSG_COUNT = 10
    private var pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_like)
        initWidget()
    }

    private fun initWidget() {
        msgEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_MsgLikeActivity.setOnClickListener {
            finish()
        }
    }
    private fun msgEvent() {
        val communityLikeList =
            msgService.getCommunityLikeList(MSG_COUNT, InfoRepository.user.id, pageNow)
        if (communityLikeList != null && communityLikeList.msg == "success") {
            val ats = communityLikeList.data.likeList
            cards_MsgLikeActivity.removeAllViews()
            for (at in ats) {
                val view = MsgCardView(this, at.id, at.username, at.photo, "${at.username}点赞了您", at.type, at.cosOrQaNumber, at.info, at.isRead, at.createTime)
                cards_MsgLikeActivity.addView(view)
            }
        }
    }
}