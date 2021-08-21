package com.bngel.bcy.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.service.FavorControllerService
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bngel.bcy.widget.others.DiscussCommentView
import com.bngel.bcy.widget.others.TagTextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_discuss.*
import kotlinx.android.synthetic.main.view_avator.view.*
import kotlinx.android.synthetic.main.view_discuss_card.view.*
import kotlinx.android.synthetic.main.view_discuss_comment.*

class DiscussActivity : BaseActivity() {

    private val cosService = CosControllerService()
    private var curCommentType = 1
    private val COMMENT_COUNT = 3
    private var pageNow = 1

    private var detailLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discuss)
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
        setLikeStatus()
        setFavorStatus()
        countsEvent()
        hottestEvent()
        newestEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_DiscussActivity.setOnClickListener {
            finish()
        }
    }

    private fun hottestEvent(){
        hottest_btn_DiscussActivity.setOnClickListener {
            if (curCommentType != 1) {
                hottest_btn_DiscussActivity.setTextColor(Color.parseColor("#101010"))
                newest_btn_DiscussActivity.setTextColor(Color.parseColor("#CEC4C4"))
                curCommentType = 1
                commentEvent()
            }
        }
    }

    private fun newestEvent(){
        newest_btn_DiscussActivity.setOnClickListener {
            if (curCommentType != 2) {
                hottest_btn_DiscussActivity.setTextColor(Color.parseColor("#CEC4C4"))
                newest_btn_DiscussActivity.setTextColor(Color.parseColor("#101010"))
                curCommentType = 2
                commentEvent()
            }
        }
    }

    private fun detailEvent() {
        val number = intent.getStringExtra("number")?:""
        if (number != "") {
            val acgCos = cosService.getAcgCos(
                if (ConstantRepository.loginStatus) InfoRepository.user.id else null,
                number
            )
            if (acgCos != null) {
                when (acgCos.msg) {
                    "success" -> {
                        val cos = acgCos.data.cos
                        cos.apply {
                            avt_DiscussActivity.setAvt(photo)
                            avt_DiscussActivity.setOnClickListener {
                                val intent = Intent(this@DiscussActivity, UserDetailActivity::class.java)
                                intent.putExtra("id", id)
                                detailLauncher?.launch(intent)
                            }
                            username_DiscussActivity.text = username
                            date_DiscussActivity.text = MyUtils.fromUtcToCst(createTime)
                            fans_count_DiscussActivity.text = "粉丝数: " + if (fansCounts > 100000) (fansCounts/10000).toString() + " 万" else fansCounts
                            description_DiscussActivity.text = cos.description
                            for (tag in label) {
                                val tagView = TagTextView(this@DiscussActivity, tag)
                                tags_DiscussActivity.addView(tagView)
                            }
                            for (cp in cosPhoto) {
                                val image = ImageView(this@DiscussActivity)
                                val param =
                                    LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        1100)
                                param.topMargin = 20
                                image.layoutParams = param
                                image.scaleType = ImageView.ScaleType.CENTER_INSIDE
                                Glide.with(this@DiscussActivity)
                                    .load(cp)
                                    .placeholder(R.drawable.rem)
                                    .error(R.drawable.rem)
                                    .into(image)
                                cosPhoto_DiscussActivity.addView(image)
                            }
                        }
                    }
                    "existWrong" -> {
                        Toast.makeText(this, "cos不存在", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else -> {
                        Toast.makeText(this, "cos加载异常", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    private fun commentEvent() {
        val number = intent.getStringExtra("number")?:""
        if (number != "") {
            val acgCosComment = cosService.getAcgCosComment(
                if (ConstantRepository.loginStatus) InfoRepository.user.id else null,
                number,
                COMMENT_COUNT,
                pageNow,
                1
            )
            if (acgCosComment != null) {
                when (acgCosComment.msg) {
                    "success" -> {
                        val comments = acgCosComment.data.cosCommentList
                        val commentCount = acgCosComment.data.counts
                        comment_count_DiscussActivity.text = commentCount.toString()
                        comments_DiscussActivity.removeAllViews()
                        for (comment in comments) {
                            val view = DiscussCommentView(this, comment.number, comment.username, comment.photo,
                                comment.description, comment.createTime)
                            val replyCountView = view.findViewById<TextView>(R.id.reply_count_DiscussCommentView)
                            val reply = cosService.getAcgCosCommentComment(
                                if (ConstantRepository.loginStatus) InfoRepository.user.id else null,
                                comment.number,
                                1,
                                pageNow,
                                curCommentType)
                            if (reply != null) {
                                when (reply.msg) {
                                    "success" -> {
                                        val commentCommentCount = reply.data.counts
                                        if (commentCommentCount <= 0)
                                            replyCountView.visibility = View.GONE
                                        else {
                                            val replyContent = reply.data.commentCommentList[0]
                                            replyCountView.text =
                                                "${replyContent.fromUsername} 共 $commentCommentCount 条回复"
                                        }
                                    }
                                    else -> {
                                        replyCountView.visibility = View.GONE
                                    }
                                }
                            }
                            else {
                                replyCountView.visibility = View.GONE
                            }
                            comments_DiscussActivity.addView(view)
                        }
                    }
                    "existWrong" -> {
                        Toast.makeText(this, "cos不存在", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun countsEvent() {
        val number = intent.getStringExtra("number")?:""
        if (number != ""){
            val cosService = CosControllerService()
            val acgCosCountsList = cosService.getAcgCosCountsList(
                if(ConstantRepository.loginStatus) InfoRepository.user.id else null , number)

            if (acgCosCountsList != null && acgCosCountsList.msg == "success") {
                val cosCountsList = acgCosCountsList.data.cosCountsList[0]
                val like = cosCountsList.likeCounts
                val star = cosCountsList.favorCounts
                val comment = cosCountsList.commentCounts
                like_text_DiscussActivity.text = like.toString()
                favor_text_DiscussActivity.text = star.toString()
                comment_text_DiscussActivity.text = comment.toString()
            }
        }
    }

    private fun setLikeStatus() {
        val likeService = LikeControllerService()
        var status = 0
        val number = intent.getStringExtra("number")?:""
        if (number != "") {
            if (ConstantRepository.loginStatus) {
                val acgJudgeLikes = likeService.getAcgJudgeLikes(InfoRepository.user.id, listOf(number))
                if (acgJudgeLikes != null && acgJudgeLikes.msg == "success") {
                    val data = acgJudgeLikes.data.judgeLikeList
                    if (data != null) {
                        status = data[0].status
                        if (status == 0)
                            like_btn_DiscussActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_cecece)
                        else if (status == 1)
                            like_btn_DiscussActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                    }
                }
            }
            like_btn_DiscussActivity.setOnClickListener {
                if (ConstantRepository.loginStatus) {
                    if (status == 0) {
                        val postAcgLikeCos = likeService.postAcgLikeCos(InfoRepository.user.id, number)
                        if (postAcgLikeCos != null) {
                            Toast.makeText(this, when(postAcgLikeCos.msg) {
                                "success" -> "喜欢!"
                                "existWrong"->"cos不存在"
                                "repeatWrong" -> "已经喜欢了噢"
                                else -> "未知错误"
                            }, Toast.LENGTH_SHORT).show()
                            if (postAcgLikeCos.msg == "success") {
                                like_btn_DiscussActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                                like_text_DiscussActivity.text = (like_text_DiscussActivity.text.toString().toInt() + 1).toString()
                                status = 1
                            }
                        }
                        else {
                            Toast.makeText(this,"未知错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else if (status == 1) {
                        val deleteAcgLikeCos = likeService.deleteAcgLikeCos(InfoRepository.user.id, number)
                        if (deleteAcgLikeCos != null) {
                            Toast.makeText(this, when(deleteAcgLikeCos.msg) {
                                "success" -> "不喜欢了噢!"
                                "existWrong" -> "cos不存在"
                                "repeatWrong" -> "已经不喜欢了噢"
                                else -> "未知错误"
                            }, Toast.LENGTH_SHORT).show()
                            if (deleteAcgLikeCos.msg == "success") {
                                like_btn_DiscussActivity.setImageResource(R.drawable.like_discuss_card_home_fragment_cecece)
                                like_text_DiscussActivity.text = (like_text_DiscussActivity.text.toString().toInt() - 1).toString()
                                status = 0
                            }
                        }
                        else {
                            Toast.makeText(this,"未知错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            Toast.makeText(this, "读取Cos信息失败", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun setFavorStatus() {
        val favorService = FavorControllerService()
        var status = 0
        val number = intent.getStringExtra("number") ?: ""
        if (number != "") {
            if (ConstantRepository.loginStatus) {
                val acgJudgeFavor =
                    favorService.postAcgJudgeFavor(InfoRepository.user.id, listOf(number))
                if (acgJudgeFavor != null && acgJudgeFavor.msg == "success") {
                    val data = acgJudgeFavor.data.judgeFavorList
                    if (data != null) {
                        status = data[0].status
                        if (status == 0)
                            favor_btn_DiscussActivity.setImageResource(R.drawable.star_discuss_card_home_fragment_cecece)
                        else if (status == 1)
                            favor_btn_DiscussActivity.setImageResource(R.drawable.star_discuss_card_home_fragment_ed6065)
                    }
                }
            }

            favor_btn_DiscussActivity.setOnClickListener {
                if (ConstantRepository.loginStatus) {
                    if (status == 0) {
                        val postAcgFavorCos =
                            favorService.postAcgFavorCos(InfoRepository.user.id, number)
                        if (postAcgFavorCos != null) {
                            Toast.makeText(
                                this, when (postAcgFavorCos.msg) {
                                    "success" -> "收藏!"
                                    "existWrong" -> "cos不存在"
                                    "repeatWrong" -> "已经收藏了噢"
                                    else -> "未知错误"
                                }, Toast.LENGTH_SHORT
                            ).show()
                            if (postAcgFavorCos.msg == "success") {
                                favor_btn_DiscussActivity.setImageResource(R.drawable.star_discuss_card_home_fragment_ed6065)
                                favor_text_DiscussActivity.text =
                                    (favor_text_DiscussActivity.text.toString()
                                        .toInt() + 1).toString()
                                status = 1
                            }
                        } else {
                            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show()
                        }
                    } else if (status == 1) {
                        val deleteAcgFavorCos =
                            favorService.deleteAcgFavorCos(InfoRepository.user.id, number)
                        if (deleteAcgFavorCos != null) {
                            Toast.makeText(
                                this, when (deleteAcgFavorCos.msg) {
                                    "success" -> "取消收藏了噢!"
                                    "existWrong" -> "cos不存在"
                                    "repeatWrong" -> "已经取消收藏了噢"
                                    else -> "未知错误"
                                }, Toast.LENGTH_SHORT
                            ).show()
                            if (deleteAcgFavorCos.msg == "success") {
                                favor_btn_DiscussActivity.setImageResource(R.drawable.star_discuss_card_home_fragment_cecece)
                                favor_text_DiscussActivity.text =
                                    (favor_text_DiscussActivity.text.toString()
                                        .toInt() - 1).toString()
                                status = 0
                            }
                        } else {
                            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            Toast.makeText(this, "读取Cos信息失败", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}