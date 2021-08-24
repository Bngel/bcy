package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.activity_like.*

class LikeActivity : BaseActivity() {

    private var discussLauncher: ActivityResultLauncher<Intent>? = null
    val likeService = LikeControllerService()
    val LIKE_COUNT = 20
    val pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)
        registerLaunch()
        initWidget()
    }

    private fun initWidget() {
        cardEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_LikeActivity.setOnClickListener {
            finish()
        }
    }

    private fun registerLaunch() {
        discussLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun cardEvent() {
        if (ConstantRepository.loginStatus){
            val acgLikeList = likeService.getAcgLikeList(InfoRepository.user.id, LIKE_COUNT, pageNow)
            if (acgLikeList != null && acgLikeList.msg == "success") {
                val likeList = acgLikeList.data.likeCosList
                like_card_LikeActivity.removeAllViews()
                for (like in likeList) {
                    val card = DiscussCardHomeFragment(this, like.number, like.id, like.username, like.photo,
                        like.cosPhoto, null, like.description, like.createTime, discussLauncher)
                    like_card_LikeActivity.addView(card)
                }
            }
        }
    }
}