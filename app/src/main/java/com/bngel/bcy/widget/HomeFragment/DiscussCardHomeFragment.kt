package com.bngel.bcy.widget.HomeFragment

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.activity.DiscussActivity
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.service.FavorControllerService
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_follow.view.*
import kotlinx.android.synthetic.main.view_avator.view.*
import kotlinx.android.synthetic.main.view_discuss_card.view.*
import kotlin.math.cos

class DiscussCardHomeFragment : LinearLayout {

    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet):super(context)
    constructor(context: Context, number: String, id: String, username: String?, photo: String?, cosPhoto:List<String>?,
                label: List<String>?, description: String?, createTime: String?, launch: ActivityResultLauncher<Intent>?):super(context) {
        username_discuss_card_HomeFragment.text = username?:""
        if (photo != null)
            avt_discuss_card_HomeFragment.setAvt(photo)
        getPhotos(cosPhoto)
        content_discuss_card_HomeFragment.text = description?:""
        if (createTime != null)
            date_discuss_card_HomeFragment.text = MyUtils.fromUtcToCst(createTime)
        getCounts(id, number)
        setLikeStatus(context, number)
        setFavorStatus(context, number)
        setLaunch(context, launch, number)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_discuss_card, this)
    }

    private fun setLaunch(context: Context, launch: ActivityResultLauncher<Intent>?, number: String) {
        this.setOnClickListener {
            val intent = Intent(context, DiscussActivity::class.java)
            intent.putExtra("number", number)
            launch?.launch(intent)
        }
    }

    private fun getCounts(id: String, number: String) {
        val cosService = CosControllerService()
        val acgCosCountsList = cosService.getAcgCosCountsList(id, number)
        if (acgCosCountsList != null && acgCosCountsList.msg == "success") {
            val cosCountsList = acgCosCountsList.data.cosCountsList[0]
            val like = cosCountsList.likeCounts
            val star = cosCountsList.favorCounts
            val comment = cosCountsList.commentCounts
            val share = cosCountsList.shareCounts
            like_text_discuss_card_HomeFragment.text = like.toString()
            star_text_discuss_card_HomeFragment.text = star.toString()
            comment_text_discuss_card_HomeFragment.text = comment.toString()
            share_text_discuss_card_HomeFragment.text = share.toString()
        }
    }

    private fun getPhotos(cosPhoto: List<String>?) {
        images_discuss_card_HomeFragment.removeAllViews()
        if (cosPhoto != null) {
            for (photo in cosPhoto) {
                val image = ImageView(context)
                val params = LayoutParams(300, 300)
                params.setMargins(10, 10, 10, 10)
                Glide.with(context)
                    .load(photo)
                    .placeholder(R.drawable.rem)
                    .error(R.drawable.rem)
                    .into(image)
                image.layoutParams = params
                images_discuss_card_HomeFragment.addView(image)
            }
        }
    }

    private fun setLikeStatus(context: Context, number: String) {
        var status = 0
        val likeService = LikeControllerService()
        if (ConstantRepository.loginStatus) {
            val acgJudgeLikes = likeService.getAcgJudgeLikes(InfoRepository.user.id, listOf(number))
            if (acgJudgeLikes != null && acgJudgeLikes.msg == "success") {
                val data = acgJudgeLikes.data.judgeLikeList
                if (data != null) {
                    status = data[0].status
                    if (status == 0)
                        like_btn_discuss_card_HomeFragment.setImageResource(R.drawable.like_discuss_card_home_fragment_cecece)
                    else if (status == 1)
                        like_btn_discuss_card_HomeFragment.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                }
            }
        }
        like_btn_discuss_card_HomeFragment.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                if (status == 0) {
                    val postAcgLikeCos = likeService.postAcgLikeCos(InfoRepository.user.id, number)
                    if (postAcgLikeCos != null) {
                        Toast.makeText(context, when(postAcgLikeCos.msg) {
                            "success" -> "喜欢!"
                            "existWrong"->"cos不存在"
                            "repeatWrong" -> "已经喜欢了噢"
                            else -> "未知错误"
                        }, Toast.LENGTH_SHORT).show()
                        if (postAcgLikeCos.msg == "success") {
                            like_btn_discuss_card_HomeFragment.setImageResource(R.drawable.like_discuss_card_home_fragment_ed6065)
                            like_text_discuss_card_HomeFragment.text = (like_text_discuss_card_HomeFragment.text.toString().toInt() + 1).toString()
                            status = 1
                        }
                    }
                    else {
                        Toast.makeText(context,"未知错误", Toast.LENGTH_SHORT).show()
                    }
                }
                else if (status == 1) {
                    val deleteAcgLikeCos = likeService.deleteAcgLikeCos(InfoRepository.user.id, number)
                    if (deleteAcgLikeCos != null) {
                        Toast.makeText(context, when(deleteAcgLikeCos.msg) {
                            "success" -> "不喜欢了噢!"
                            "existWrong" -> "cos不存在"
                            "repeatWrong" -> "已经不喜欢了噢"
                            else -> "未知错误"
                        }, Toast.LENGTH_SHORT).show()
                        if (deleteAcgLikeCos.msg == "success") {
                            like_btn_discuss_card_HomeFragment.setImageResource(R.drawable.like_discuss_card_home_fragment_cecece)
                            like_text_discuss_card_HomeFragment.text = (like_text_discuss_card_HomeFragment.text.toString().toInt() - 1).toString()
                            status = 0
                        }
                    }
                    else {
                        Toast.makeText(context,"未知错误", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setFavorStatus(context: Context, number: String) {
        val favorService = FavorControllerService()
        var status = 0
        if (ConstantRepository.loginStatus) {
            val acgJudgeFavor = favorService.postAcgJudgeFavor(InfoRepository.user.id, listOf(number))
            if (acgJudgeFavor != null && acgJudgeFavor.msg == "success") {
                val data = acgJudgeFavor.data.judgeFavorList
                if (data != null) {
                    status = data[0].status
                    if (status == 0)
                        star_btn_discuss_card_HomeFragment.setImageResource(R.drawable.star_discuss_card_home_fragment_cecece)
                    else if (status == 1)
                        star_btn_discuss_card_HomeFragment.setImageResource(R.drawable.star_discuss_card_home_fragment_ed6065)
                }
            }
        }
        star_btn_discuss_card_HomeFragment.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                if (status == 0) {
                    val postAcgFavorCos = favorService.postAcgFavorCos(InfoRepository.user.id, number)
                    if (postAcgFavorCos != null) {
                        Toast.makeText(context, when(postAcgFavorCos.msg) {
                            "success" -> "收藏!"
                            "existWrong"->"cos不存在"
                            "repeatWrong" -> "已经收藏了噢"
                            else -> "未知错误"
                        }, Toast.LENGTH_SHORT).show()
                        if (postAcgFavorCos.msg == "success") {
                            star_btn_discuss_card_HomeFragment.setImageResource(R.drawable.star_discuss_card_home_fragment_ed6065)
                            star_text_discuss_card_HomeFragment.text = (star_text_discuss_card_HomeFragment.text.toString().toInt() + 1).toString()
                            status = 1
                        }
                    }
                    else {
                        Toast.makeText(context,"未知错误", Toast.LENGTH_SHORT).show()
                    }
                }
                else if (status == 1) {
                    val deleteAcgFavorCos = favorService.deleteAcgFavorCos(InfoRepository.user.id, number)
                    if (deleteAcgFavorCos != null) {
                        Toast.makeText(context, when(deleteAcgFavorCos.msg) {
                            "success" -> "取消收藏了噢!"
                            "existWrong" -> "cos不存在"
                            "repeatWrong" -> "已经取消收藏了噢"
                            else -> "未知错误"
                        }, Toast.LENGTH_SHORT).show()
                        if (deleteAcgFavorCos.msg == "success") {
                            star_btn_discuss_card_HomeFragment.setImageResource(R.drawable.star_discuss_card_home_fragment_cecece)
                            star_text_discuss_card_HomeFragment.text = (star_text_discuss_card_HomeFragment.text.toString().toInt() - 1).toString()
                            status = 0
                        }
                    }
                    else {
                        Toast.makeText(context,"未知错误", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }
    }
}