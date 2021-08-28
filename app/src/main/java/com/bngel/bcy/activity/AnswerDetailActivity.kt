package com.bngel.bcy.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.AnswerCommentView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_answer_detail.*
import kotlinx.android.synthetic.main.activity_discuss.*

class AnswerDetailActivity : BaseActivity() {

    private val qaService = QAControllerService()
    private var curCommentType = 1
    private val COMMENT_COUNT = 3
    private var pageNow = 1

    private var detailLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_detail)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun initWidget() {
        detailEvent()
        commentEvent()
        hottestEvent()
        newestEvent()
        closeEvent()
        likeEvent()
    }

    private fun likeEvent() {
        val number = intent.getStringExtra("number")?:""
        if (ConstantRepository.loginStatus) {
            val acgJudgeQaAnswer = qaService.getAcgJudgeQaAnswer(InfoRepository.user.id, number)
            if (acgJudgeQaAnswer != null && acgJudgeQaAnswer.msg=="success") {
                when (acgJudgeQaAnswer.data.qaAnswerLikeList[0].isLike) {
                    0 ->
                        like_btn_AnswerDetailActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_cecece)
                    1 ->
                        like_btn_AnswerDetailActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                }
            }
        }
        like_btn_AnswerDetailActivity.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val acgJudgeQaAnswer = qaService.getAcgJudgeQaAnswer(InfoRepository.user.id, number)
                if (acgJudgeQaAnswer != null && acgJudgeQaAnswer.msg=="success") {
                    when (acgJudgeQaAnswer.data.qaAnswerLikeList[0].isLike) {
                        0 -> {
                            val deleteAcgLikeAnswer = qaService.deleteAcgLikeAnswer(InfoRepository.user.id, number)
                            if (deleteAcgLikeAnswer != null) {
                                when (deleteAcgLikeAnswer.msg) {
                                    "success" -> {
                                        like_btn_AnswerDetailActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                                        Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show()
                                    }
                                    "repeatWrong" -> {
                                        Toast.makeText(this, "不能重复点赞噢", Toast.LENGTH_SHORT).show()
                                    }
                                    "existWrong" -> {
                                        Toast.makeText(this, "回答不存在", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        1 -> {
                            val deleteAcgLikeAnswer = qaService.deleteAcgLikeAnswer(InfoRepository.user.id, number)
                            if (deleteAcgLikeAnswer != null) {
                                when (deleteAcgLikeAnswer.msg) {
                                    "success" -> {
                                        like_btn_AnswerDetailActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                                        Toast.makeText(this, "取消点赞成功", Toast.LENGTH_SHORT).show()
                                    }
                                    "repeatWrong" -> {
                                        Toast.makeText(this, "不能重复取消噢", Toast.LENGTH_SHORT).show()
                                    }
                                    "existWrong" -> {
                                        Toast.makeText(this, "回答不存在", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun closeEvent() {
        close_btn_AnswerDetailActivity.setOnClickListener {
            finish()
        }
    }

    private fun hottestEvent(){
        hottest_btn_AnswerDetailActivity.setOnClickListener {
            if (curCommentType != 1) {
                hottest_btn_AnswerDetailActivity.setTextColor(Color.parseColor("#101010"))
                newest_btn_AnswerDetailActivity.setTextColor(Color.parseColor("#CEC4C4"))
                curCommentType = 1
                commentEvent()
            }
        }
    }

    private fun newestEvent(){
        newest_btn_AnswerDetailActivity.setOnClickListener {
            if (curCommentType != 2) {
                hottest_btn_AnswerDetailActivity.setTextColor(Color.parseColor("#CEC4C4"))
                newest_btn_AnswerDetailActivity.setTextColor(Color.parseColor("#101010"))
                curCommentType = 2
                commentEvent()
            }
        }
    }

    private fun detailEvent(){
        avt_AnswerDetailActivity.setAvt(intent.getStringExtra("avt")?:"")
        username_AnswerDetailActivity.text = intent.getStringExtra("username")?:""
        date_AnswerDetailActivity.text = intent.getStringExtra("createTime")
        fans_count_AnswerDetailActivity.text = ""
        val description = intent.getStringExtra("description")?:""
        description_AnswerDetailActivity.text = description
        val cosPhoto = listOf(intent.getStringExtra("cosPhoto"))
        for (photo in cosPhoto) {
            val image = ImageView(this)
            val param =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1100)
            param.topMargin = 20
            image.layoutParams = param
            image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            Glide.with(this)
                .load(photo)
                .placeholder(R.drawable.rem)
                .error(R.drawable.rem)
                .into(image)
            cosPhoto_AnswerDetailActivity.addView(image)
        }
    }

    private fun commentEvent() {
        val number = intent.getStringExtra("number")?:""
        val acgAnswerCommentList = qaService.getAcgAnswerCommentList(
            number,
            COMMENT_COUNT,
            if (ConstantRepository.loginStatus) InfoRepository.user.id else null,
            pageNow,
            curCommentType
        )
        if (acgAnswerCommentList != null && acgAnswerCommentList.msg == "success") {
            val comments = acgAnswerCommentList.data.answerCommentList
            comments_AnswerDetailActivity.removeAllViews()
            for (comment in comments) {
                val view = AnswerCommentView(this, comment.number, comment.username, comment.photo?:"", comment.description, comment.createTime)
                comments_AnswerDetailActivity.addView(view)
            }
        }
    }


}