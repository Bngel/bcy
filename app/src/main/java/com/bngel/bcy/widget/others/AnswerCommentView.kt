package com.bngel.bcy.widget.others

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bngel.bcy.R
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import kotlinx.android.synthetic.main.activity_answer_detail.*
import kotlinx.android.synthetic.main.view_answer_comment.view.*
import kotlinx.android.synthetic.main.view_discuss_comment.view.*

class AnswerCommentView: LinearLayout {

    private val qaService = QAControllerService()

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, commentNumber: String, username: String, photo: String, description: String, createTime: String): super(context) {
        username_AnswerCommentView.text = username
        avt_AnswerCommentView.setAvt(photo)
        description_AnswerCommentView.text = description
        date_AnswerCommentView.text = MyUtils.fromUtcToCst(createTime)
        val reply = qaService.getAcgAnswerCommentCommentList(
            commentNumber,
            3,
            if (ConstantRepository.loginStatus) InfoRepository.user.id else null,
            1,
            1)
        if (reply != null) {
            when (reply.msg) {
                "success" -> {
                    val commentCommentCount = reply.data.counts
                    if (commentCommentCount <= 0)
                        reply_count_AnswerCommentView.visibility = View.GONE
                    else {
                        val replyContent = reply.data.answerCommentCommentList[0]
                        reply_count_AnswerCommentView.text =
                            "${replyContent.fromUsername} 共 $commentCommentCount 条回复"
                    }
                }
                else -> {
                    reply_count_AnswerCommentView.visibility = View.GONE
                }
            }
        }
        else {
            reply_count_AnswerCommentView.visibility = View.GONE
        }
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_answer_comment, this)
    }


}