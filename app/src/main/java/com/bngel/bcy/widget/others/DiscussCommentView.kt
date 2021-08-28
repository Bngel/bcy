package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import kotlinx.android.synthetic.main.view_discuss_comment.view.*

class DiscussCommentView: LinearLayout {

    private val cosService = CosControllerService()

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, commentNumber: String, username: String, photo: String, description: String, createTime: String): super(context) {
        username_DiscussCommentView.text = username
        avt_DiscussCommentView.setAvt(photo)
        description_DiscussCommentView.text = description
        date_DiscussCommentView.text = MyUtils.fromUtcToCst(createTime)
        val reply = cosService.getAcgCosCommentComment(
            if (ConstantRepository.loginStatus) InfoRepository.user.id else null,
            commentNumber, 3, 1, 1)
        if (reply != null) {
            when (reply.msg) {
                "success" -> {
                    val commentCommentCount = reply.data.counts
                    if (commentCommentCount <= 0)
                        reply_count_DiscussCommentView.visibility = View.GONE
                    else {
                        val replyContent = reply.data.commentCommentList[0]
                        reply_count_DiscussCommentView.text =
                            "${replyContent.fromUsername} 共 $commentCommentCount 条回复"
                    }
                }
                else -> {
                    reply_count_DiscussCommentView.visibility = View.GONE
                }
            }
        }
        else {
            reply_count_DiscussCommentView.visibility = View.GONE
        }
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_discuss_comment, this)
    }
}