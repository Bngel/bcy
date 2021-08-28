package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.TalkControllerService
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.MsgCardView
import kotlinx.android.synthetic.main.activity_msg_box.*

class MsgBoxActivity : BaseActivity() {

    private val talkService = TalkControllerService()
    private val MSG_COUNT = 10
    private var pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_box)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        atEvent()
        commentEvent()
        likeEvent()
        talkEvent()
    }

    private fun talkEvent() {
        val communityTalkList =
            talkService.getCommunityTalkList(MSG_COUNT, InfoRepository.user.id, pageNow)
        if (communityTalkList != null && communityTalkList.msg == "success") {
            val talks = communityTalkList.data.talkList
            cards_MsgBoxActivity.removeAllViews()
            for (talk in talks) {
                val communityTalkCounts =
                    talkService.getCommunityTalkCounts(InfoRepository.user.id, listOf(talk.id))
                if (communityTalkCounts != null && communityTalkCounts.msg == "success") {
                    val tc = communityTalkCounts.data.talkCountsList[0]
                    val view = MsgCardView(this, tc.id, talk.username, talk.photo, tc.lastInfo, 0 , "", "", 0, tc.updateTime)
                    cards_MsgBoxActivity.addView(view)
                }
            }
        }
    }

    private fun atEvent() {
        at_btn_MsgBoxActivity.setOnClickListener {
            val intent = Intent(this, MsgAtActivity::class.java)
            startActivity(intent)
        }
    }

    private fun commentEvent() {
        comment_btn_MsgBoxActivity.setOnClickListener {
            val intent = Intent(this, MsgCommentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun likeEvent() {
        like_btn_MsgBoxActivity.setOnClickListener {
            val intent = Intent(this, MsgLikeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun closeEvent() {
        close_btn_MsgBoxActivity.setOnClickListener {
            finish()
        }
    }
}