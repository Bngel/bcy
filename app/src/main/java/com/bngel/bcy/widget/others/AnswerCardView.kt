package com.bngel.bcy.widget.others

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.bngel.bcy.R
import com.bngel.bcy.activity.AnswerDetailActivity
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.view_answer_card.view.*
import kotlinx.android.synthetic.main.view_answer_comment.view.*

class AnswerCardView: LinearLayout {

    private val qaService = QAControllerService()

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, number: String, avt: String, username: String, createTime: String,
                description: String, cosPhoto:List<String>, launcher: ActivityResultLauncher<Intent>): super(context) {
        avt_AnswerCardView.setAvt(avt)
        username_AnswerCardView.text = username
        date_AnswerCardView.text = createTime
        description_AnswerCardView.text = description
        val acgQaAnswerCountsList = qaService.getAcgQaAnswerCountsList(
            number,
            if (ConstantRepository.loginStatus) InfoRepository.user.id else null
        )
        if (acgQaAnswerCountsList != null && acgQaAnswerCountsList.msg == "success") {
            val qaAnswerCounts = acgQaAnswerCountsList.data.qaAnswerCountsList[0]
            val likeCount = qaAnswerCounts.likeCounts
            val commentCount = qaAnswerCounts.commentCounts
            like_count_AnswerCardView.text = likeCount.toString()
            comment_count_AnswerCardView.text = commentCount.toString()
        }
        this.setOnClickListener {
            val intent = Intent(context, AnswerDetailActivity::class.java)
            intent.putExtra("description", description)
            intent.putExtra("number", number)
            if (cosPhoto.count() > 0)
                intent.putExtra("cosPhoto", cosPhoto.toString())
            intent.putExtra("username", username)
            intent.putExtra("createTime", createTime)
            intent.putExtra("avt", avt)
            launcher.launch(intent)
        }
        setLikeStatus(context, number)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_answer_card, this)
    }

    private fun setLikeStatus(context: Context, number: String) {
        like_image_AnswerCardView.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val acgJudgeQaAnswer = qaService.getAcgJudgeQaAnswer(InfoRepository.user.id, number)
                if (acgJudgeQaAnswer != null && acgJudgeQaAnswer.msg=="success") {
                    when (acgJudgeQaAnswer.data.qaAnswerLikeList[0].isLike) {
                        0 -> {
                            val deleteAcgLikeAnswer = qaService.deleteAcgLikeAnswer(InfoRepository.user.id, number)
                            if (deleteAcgLikeAnswer != null) {
                                when (deleteAcgLikeAnswer.msg) {
                                    "success" -> {
                                        like_image_AnswerCardView.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                                        Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show()
                                    }
                                    "repeatWrong" -> {
                                        Toast.makeText(context, "不能重复点赞噢", Toast.LENGTH_SHORT).show()
                                    }
                                    "existWrong" -> {
                                        Toast.makeText(context, "回答不存在", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        1 -> {
                            val deleteAcgLikeAnswer = qaService.deleteAcgLikeAnswer(InfoRepository.user.id, number)
                            if (deleteAcgLikeAnswer != null) {
                                when (deleteAcgLikeAnswer.msg) {
                                    "success" -> {
                                        like_image_AnswerCardView.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                                        Toast.makeText(context, "取消点赞成功", Toast.LENGTH_SHORT).show()
                                    }
                                    "repeatWrong" -> {
                                        Toast.makeText(context, "不能重复取消噢", Toast.LENGTH_SHORT).show()
                                    }
                                    "existWrong" -> {
                                        Toast.makeText(context, "回答不存在", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }
    }
}