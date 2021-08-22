package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import com.bngel.bcy.widget.others.TagTextView
import kotlinx.android.synthetic.main.activity_q_and_a_detail.*

class QAndADetailActivity : BaseActivity() {

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