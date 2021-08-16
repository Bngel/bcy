package com.bngel.bcy.widget.others

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import com.bngel.bcy.R
import com.bngel.bcy.bean.FansController.getUserFansList.Fans
import com.bngel.bcy.bean.FansController.getUserFollowList.Follow
import com.bngel.bcy.service.FansControllerService
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.view_follow_and_fan_card.view.*

class FollowAndFanCardView: LinearLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet)
    constructor(context: Context, data: Follow): super(context) {
        avt_FollowAndFanCardView.setAvt(data.photo)
        username_FollowAndFanCardView.text = data.username
        fans_count_FollowAndFanCardView.text =
            "粉丝数：${if(data.fansCounts < 100000) data.fansCounts else (data.fansCounts/10000).toString() + "万"}"
        val service = FansControllerService()
        val postUserJudgeFollow =
            service.postUserJudgeFollow(InfoRepository.user.id, data.id)
        if (postUserJudgeFollow != null && postUserJudgeFollow.msg == "success") {
            when(postUserJudgeFollow.data.status) {
                "0" -> {
                    follow_btn_FollowAndFanCardView.text = "+ 关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                }
                "1" -> {
                    follow_btn_FollowAndFanCardView.text = "已关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#CCBE97"))
                }
                "2" -> {
                    follow_btn_FollowAndFanCardView.text = "已互粉"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#CCBE97"))
                }
                "3" -> {
                    follow_btn_FollowAndFanCardView.text = "+ 关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                }
                else -> {
                    follow_btn_FollowAndFanCardView.text = "+ 关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                }
            }
        }
        follow_btn_FollowAndFanCardView.setOnClickListener {
            if (data.id == InfoRepository.user.id)
                Toast.makeText(context, "无法关注自己", Toast.LENGTH_SHORT).show()
            else {
                val fromId = InfoRepository.user.id
                if (postUserJudgeFollow != null) {
                    val followStatus = postUserJudgeFollow.data.status
                    if (followStatus == "0" || followStatus == "3") {
                        val postUserFollow = service.postUserFollow(fromId, data.id)
                        if (postUserFollow != null) {
                            if (postUserFollow.msg == "success") {
                                follow_btn_FollowAndFanCardView.text = when (followStatus) {
                                    "0" -> "已关注"
                                    "3" -> "已互粉"
                                    else -> "已关注"
                                }
                                follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                                follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#CCBE97"))
                                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show()
                        }
                    } else if (followStatus == "1" || followStatus == "2") {
                        val deleteUserFollow = service.deleteUserFollow(fromId, data.id)
                        if (deleteUserFollow != null) {
                            if (deleteUserFollow.msg == "success") {
                                follow_btn_FollowAndFanCardView.text = "+ 关注"
                                follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                                follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                                Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    constructor(context: Context, data: Fans): super(context) {
        avt_FollowAndFanCardView.setAvt(data.photo)
        username_FollowAndFanCardView.text = data.username
        fans_count_FollowAndFanCardView.text =
            "粉丝数：${if(data.fansCounts < 100000) data.fansCounts else (data.fansCounts/10000).toString() + "万"}"
        val service = FansControllerService()
        val postUserJudgeFollow =
            service.postUserJudgeFollow(InfoRepository.user.id, data.id)
        if (postUserJudgeFollow != null && postUserJudgeFollow.msg == "success") {
            when(postUserJudgeFollow.data.status) {
                "0" -> {
                    follow_btn_FollowAndFanCardView.text = "+ 关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                }
                "1" -> {
                    follow_btn_FollowAndFanCardView.text = "已关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#CCBE97"))
                }
                "2" -> {
                    follow_btn_FollowAndFanCardView.text = "已互粉"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#CCBE97"))
                }
                "3" -> {
                    follow_btn_FollowAndFanCardView.text = "+ 关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                }
                else -> {
                    follow_btn_FollowAndFanCardView.text = "+ 关注"
                    follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                    follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                }
            }
        }
        follow_btn_FollowAndFanCardView.setOnClickListener {
            if (data.id == InfoRepository.user.id)
                Toast.makeText(context, "无法关注自己", Toast.LENGTH_SHORT).show()
            else {
                val fromId = InfoRepository.user.id
                if (postUserJudgeFollow != null) {
                    val followStatus = postUserJudgeFollow.data.status
                    if (followStatus == "0" || followStatus == "3") {
                        val postUserFollow = service.postUserFollow(fromId, data.id)
                        if (postUserFollow != null) {
                            if (postUserFollow.msg == "success") {
                                follow_btn_FollowAndFanCardView.text = when (followStatus) {
                                    "0" -> "已关注"
                                    "3" -> "已互粉"
                                    else -> "已关注"
                                }
                                follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_followed_btn_community_card_view)
                                follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#CCBE97"))
                                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show()
                        }
                    } else if (followStatus == "1" || followStatus == "2") {
                        val deleteUserFollow = service.deleteUserFollow(fromId, data.id)
                        if (deleteUserFollow != null) {
                            if (deleteUserFollow.msg == "success") {
                                follow_btn_FollowAndFanCardView.text = "+ 关注"
                                follow_btn_FollowAndFanCardView.setBackgroundResource(R.drawable.bk_follow_btn_community_card_view)
                                follow_btn_FollowAndFanCardView.setTextColor(Color.parseColor("#F06A0F"))
                                Toast.makeText(context, "取消关注成功", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    init{
        LayoutInflater.from(context).inflate(R.layout.view_follow_and_fan_card, this)

    }
}