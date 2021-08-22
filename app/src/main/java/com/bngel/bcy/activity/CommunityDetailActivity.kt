package com.bngel.bcy.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bngel.bcy.R
import com.bngel.bcy.adapter.CommunityDetailViewPagerFragmentStateAdapter
import com.bngel.bcy.adapter.HomeViewPagerFragmentStateAdapter
import com.bngel.bcy.service.CircleControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_community_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_avator.view.*

class CommunityDetailActivity : BaseActivity() {

    private val circleService = CircleControllerService()
    private var isFollowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_detail)
        initWidget()
    }

    private fun initWidget() {
        vpEvent()
        tabEvent()
        closeEvent()
        infoEvent()
    }

    private fun infoEvent() {
        val circleName = intent.getStringExtra("circleName")
        isFollowed = intent.getBooleanExtra("isFollowed", false)
        if (circleName != null) {
            val acgCircle = circleService.getAcgCircle(circleName)
            if (acgCircle != null) {
                when (acgCircle.msg) {
                    "success" -> {
                        val info = acgCircle.data.circleInfo
                        Glide.with(this)
                            .load(info.photo)
                            .placeholder(R.drawable.rem)
                            .error(R.drawable.rem)
                            .into(image_CommunityDetailActivity)
                        circle_name_CommunityDetailActivity.text = info.circleName
                        description_CommunityDetailActivity.text = info.description
                        counts_CommunityDetailActivity.text = "${info.postCounts} 帖子  ${info.followCounts}个成员"
                        follow_btn_CommunityDetailActivity.text = if (isFollowed) "已关注" else "关注圈子"
                        follow_btn_CommunityDetailActivity.setOnClickListener {
                            if (ConstantRepository.loginStatus) {
                                if (isFollowed) {
                                    val deleteAcgFollowCircle =
                                        circleService.deleteAcgFollowCircle(circleName, InfoRepository.user.id)
                                    if (deleteAcgFollowCircle != null) {
                                        when (deleteAcgFollowCircle.msg) {
                                            "success" -> {
                                                follow_btn_CommunityDetailActivity.text = "关注圈子"
                                                isFollowed = !isFollowed
                                                Toast.makeText(this, "取消关注成功", Toast.LENGTH_SHORT).show()
                                            }
                                            "repeatWrong" -> {
                                                Toast.makeText(this, "不能重复取消关注噢", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                                else {
                                    val postAcgFollowCircle =
                                        circleService.postAcgFollowCircle(circleName, InfoRepository.user.id)
                                    if (postAcgFollowCircle != null) {
                                        when (postAcgFollowCircle.msg) {
                                            "success" -> {
                                                follow_btn_CommunityDetailActivity.text = "已关注"
                                                isFollowed = !isFollowed
                                                Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show()
                                            }
                                            "repeatWrong" -> {
                                                Toast.makeText(this, "不能重复关注噢", Toast.LENGTH_SHORT).show()
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
                }
            }
        }
    }

    private fun closeEvent() {
        close_btn_CommunityDetailActivity.setOnClickListener {
            finish()
        }
    }

    private fun vpEvent() {
        val adapter = CommunityDetailViewPagerFragmentStateAdapter(this, 2)
        viewpager_CommunityDetailActivity.isUserInputEnabled = false
        viewpager_CommunityDetailActivity.adapter = adapter
        viewpager_CommunityDetailActivity.currentItem = 0
    }

    private fun tabEvent() {
        switch_discuss_btn_CommunityDetailActivity.setOnClickListener {
            switch_discuss_btn_CommunityDetailActivity.setTextColor(Color.parseColor("#CCBE97"))
            switch_qa_btn_CommunityDetailActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_CommunityDetailActivity.currentItem = 0
        }
        switch_qa_btn_CommunityDetailActivity.setOnClickListener {
            switch_discuss_btn_CommunityDetailActivity.setTextColor(Color.parseColor("#101010"))
            switch_qa_btn_CommunityDetailActivity.setTextColor(Color.parseColor("#CCBE97"))
            viewpager_CommunityDetailActivity.currentItem = 1
        }
    }
}