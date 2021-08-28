package com.bngel.bcy.widget.others

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.activity.CommunityDetailActivity
import com.bngel.bcy.service.CircleControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_avator.view.*
import kotlinx.android.synthetic.main.view_community_card.view.*

class CommunityCardView: LinearLayout {

    private var isFollowed = 0

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, image: String, name: String, description: String, fans: Int, circleLauncher: ActivityResultLauncher<Intent>):super(context) {
        Glide.with(context)
            .load(image)
            .placeholder(R.drawable.rem)
            .error(R.drawable.rem)
            .into(image_CommunityCardView)
        circle_name_CommunityCardView.text = name
        circle_description_CommunityCardView.text = description
        if (fans > 0)
            circle_fans_CommunityCardView.text = fans.toString() + "个成员"
        else
            circle_fans_CommunityCardView.text = ""
        if (ConstantRepository.loginStatus) {
            val service = CircleControllerService()
            val acgJudgeCircle = service.getAcgJudgeCircle(name, InfoRepository.user.id)
            if (acgJudgeCircle != null && acgJudgeCircle.msg == "success") {
                isFollowed = acgJudgeCircle.data.judgeCircleList[0].isFollow
                val intent = Intent(context, CommunityDetailActivity::class.java)
                intent.putExtra("isFollowed", isFollowed != 0)
                when (isFollowed) {
                    0 -> {
                        follow_btn_CommunityCardView.apply {
                            text = "+ 关注"
                            setTextColor(Color.parseColor("#F06A0F"))
                            follow_btn_CommunityCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                        }
                    }
                    1 -> {
                        follow_btn_CommunityCardView.apply {
                            text = "已关注"
                            setTextColor(Color.parseColor("#CCBE97"))
                            follow_btn_CommunityCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                        }
                    }
                }
                follow_btn_CommunityCardView.setOnClickListener {
                    when (isFollowed) {
                        0 -> {
                            val postAcgFollowCircle =
                                service.postAcgFollowCircle(name, InfoRepository.user.id)
                            if (postAcgFollowCircle != null) {
                                when (postAcgFollowCircle.msg) {
                                    "success" -> {
                                        follow_btn_CommunityCardView.text = "已关注"
                                        follow_btn_CommunityCardView.setTextColor(Color.parseColor("#CCBE97"))
                                        follow_btn_CommunityCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                                        Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show()
                                        ConstantRepository.circleFragmentUpdate = false
                                        isFollowed = 1
                                    }
                                    "repeatWrong" -> {
                                        Toast.makeText(context, "不能重复关注噢", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        1 -> {
                                val deleteAcgFollowCircle =
                                    service.deleteAcgFollowCircle(name, InfoRepository.user.id)
                                if (deleteAcgFollowCircle != null) {
                                    when (deleteAcgFollowCircle.msg) {
                                        "success" -> {
                                            follow_btn_CommunityCardView.text = "+ 关注"
                                            follow_btn_CommunityCardView.setTextColor(Color.parseColor("#F06A0F"))
                                            follow_btn_CommunityCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                                            Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show()
                                            ConstantRepository.circleFragmentUpdate = false
                                            isFollowed = 0
                                        }
                                        "repeatWrong" -> {
                                            Toast.makeText(context, "不能重复取消关注噢", Toast.LENGTH_SHORT).show()
                                        }
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

        this.setOnClickListener {
            val intent = Intent(context, CommunityDetailActivity::class.java)
            intent.putExtra("circleName", name)
            circleLauncher.launch(intent)
        }
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_community_card, this)
    }
}