package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bngel.bcy.R
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.activity_like.*

class LikeActivity : BaseActivity() {

    val likeService = LikeControllerService()
    val LIKE_COUNT = 20
    val pageNew = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)
        initWidget()
    }

    private fun initWidget() {
        cardEvent()
    }

    private fun cardEvent() {
        if (ConstantRepository.loginStatus){
            val acgLikeList = likeService.getAcgLikeList(InfoRepository.user.id, LIKE_COUNT, pageNew)
            if (acgLikeList != null && acgLikeList.msg == "success") {
                val likeList = acgLikeList.data.likeCosList
                like_card_LikeActivity.removeAllViews()
                for (like in likeList) {
                    val card = DiscussCardHomeFragment(this, like.number, like.id, like.username, like.photo,
                        like.cosPhoto, null, like.description, like.createTime)
                    like_card_LikeActivity.addView(card)
                }
            }
        }
    }
}