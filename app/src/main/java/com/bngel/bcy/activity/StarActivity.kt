package com.bngel.bcy.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.FavorControllerService
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.activity_like.*
import kotlinx.android.synthetic.main.activity_star.*

class StarActivity : BaseActivity() {

    private val starService = FavorControllerService()
    private val starList = ArrayList<String>()
    private val STAR_COUNT = 5
    private var pageNow = 1
    private var detailLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
            }
        }
    }

    private fun initWidget() {
        closeEvent()
        deleteEvent()
        starEvent()
    }

    private fun starEvent() {
        val acgFavorList = starService.getAcgFavorList(InfoRepository.user.id, STAR_COUNT, pageNow)
        if (acgFavorList != null && acgFavorList.msg == "success") {
            val favorList = acgFavorList.data.favorList
            star_cards_StarActivity.removeAllViews()
            for (favor in favorList) {
                starList.add(favor.cosNumber)
                val view = DiscussCardHomeFragment(this, favor.cosNumber, favor.id, favor.username, favor.photo, favor.cosPhoto, null,
                    favor.description, favor.create_time, detailLauncher)
                star_cards_StarActivity.addView(view)
            }
        }
    }

    private fun closeEvent() {
        close_btn_StarActivity.setOnClickListener {
            finish()
        }
    }

    private fun deleteEvent() {
        delete_btn_StarActivity.setOnClickListener {
            for (star in starList) {
                val deleteAcgFavorCos = starService.deleteAcgFavorCos(InfoRepository.user.id, star)
                if (deleteAcgFavorCos != null && deleteAcgFavorCos.msg == "success")
                    starList.remove(star)
            }
            if (starList.count() == 0)
                Toast.makeText(this, "收藏已清空", Toast.LENGTH_SHORT).show()
        }
    }
}