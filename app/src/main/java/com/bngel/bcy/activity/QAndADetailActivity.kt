package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import com.bngel.bcy.widget.others.AnswerCardView
import com.bngel.bcy.widget.others.TagTextView
import kotlinx.android.synthetic.main.activity_q_and_a_detail.*

class QAndADetailActivity : BaseActivity() {

    private var followed = 0
    private val qaService = QAControllerService()
    private val answerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
    }
    private val ANSWER_COUNT = 10
    private var pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_and_a_detail)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        infoEvent()
        answerEvent()
        followEvent()
    }

    private fun followEvent() {
        if (ConstantRepository.loginStatus) {
            val number = intent.getStringExtra("number")
            val acgJudgeQa = qaService.getAcgJudgeQa(InfoRepository.user.id, number ?: "")
            if (acgJudgeQa != null && acgJudgeQa.msg == "success") {
                followed = acgJudgeQa.data.qaJudgeList[0].isFollow
                when (followed) {
                    1 -> {
                        add_follow_btn_QAndADetailActivity.visibility = View.GONE
                        follow_text_QAndADetailActivity.text = "已关注"
                    }
                    0 -> {
                        add_follow_btn_QAndADetailActivity.visibility = View.VISIBLE
                        follow_text_QAndADetailActivity.text = "关注"
                    }
                }
                follow_btn_QAndADetailActivity.setOnClickListener {
                    when (followed) {
                        1 -> {
                            val deleteAcgFollowQa =
                                qaService.deleteAcgFollowQa(InfoRepository.user.id, number ?: "")
                            if (deleteAcgFollowQa != null) {
                                Toast.makeText(this, when(deleteAcgFollowQa.msg) {
                                    "success" -> "取消关注成功"
                                    "repeatWrong" -> "取消关注重复"
                                    "existWrong" -> "问答不存在"
                                    else -> "未知错误"
                                }, Toast.LENGTH_SHORT).show()
                                if (deleteAcgFollowQa.msg == "success"){
                                    add_follow_btn_QAndADetailActivity.visibility = View.VISIBLE
                                    follow_text_QAndADetailActivity.text = "关注"
                                    followed = 0
                                }
                            }
                        }
                        0 -> {
                            val postAcgFollowQa =
                                qaService.postAcgFollowQa(InfoRepository.user.id, number ?: "")
                            if (postAcgFollowQa != null) {
                                Toast.makeText(this, when(postAcgFollowQa.msg) {
                                    "success" -> "关注成功"
                                    "repeatWrong" -> "关注重复"
                                    "existWrong" -> "问答不存在"
                                    else -> "未知错误"
                                }, Toast.LENGTH_SHORT).show()
                                if (postAcgFollowQa.msg == "success"){
                                    add_follow_btn_QAndADetailActivity.visibility = View.GONE
                                    follow_text_QAndADetailActivity.text = "已关注"
                                    followed = 1
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            follow_btn_QAndADetailActivity.setOnClickListener {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun answerEvent() {
        val number = intent.getStringExtra("number")
        if (number != null) {
            val acgAnswerList = qaService.getAcgAnswerList(ANSWER_COUNT, number,
                if (ConstantRepository.loginStatus) InfoRepository.user.id else null, pageNow, 1)
            if (acgAnswerList != null && acgAnswerList.msg == "success") {
                answer_cards_QAndADetailActivity.removeAllViews()
                val answers = acgAnswerList.data.answerList
                for (answer in answers) {
                    val view = AnswerCardView(this, answer.number, answer.photo, answer.username, MyUtils.fromUtcToCst(answer.createTime), answer.description, answer.answerPhoto, answerLauncher)
                    answer_cards_QAndADetailActivity.addView(view)
                }
            }
        }
        else {
            Toast.makeText(this, "读取问答信息错误", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun infoEvent() {
        val number = intent.getStringExtra("number")
        if (number != null) {
            val acgQaTopic = qaService.getAcgQaTopic(
                number,
                if (ConstantRepository.loginStatus) InfoRepository.user.id else null
            )
            if (acgQaTopic != null) {
                when (acgQaTopic.msg) {
                    "success" -> {
                        val info = acgQaTopic.data.QATopic
                        title_QAndADetailActivity.text = info.title
                        description_QAndADetailActivity.text = info.description
                        followCount_QAndADetailActivity.text = "${info.followCounts} 关注"
                        answerCount_QAndADetailActivity.text = "${info.answerCounts} 回答"
                        tags_QAndADetailActivity.removeAllViews()
                        for (tag in info.label) {
                            tags_QAndADetailActivity.addView(TagTextView(this, tag))
                        }
                    }
                    "existWrong" -> {
                        finish()
                        Toast.makeText(this, "问答不存在", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else {
            Toast.makeText(this, "读取问答信息错误", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun closeEvent() {
        close_btn_QAndADetailActivity.setOnClickListener {
            finish()
        }
    }
}