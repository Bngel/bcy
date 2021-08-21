package com.bngel.bcy.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.fragment_top.*

class TopFragment: Fragment() {

    private val cosService = CosControllerService()
    val FOLLOW_COUNT = 5
    var pageNow = 1
    var parentContext: Context? = null
    private var discussLauncher: ActivityResultLauncher<Intent>? = null

    private val LABEL_PAINT = "绘画"
    private val LABEL_COS = "cos"
    private val LABEL_WRITE = "写作"
    var curLabel = LABEL_PAINT

    private val LABEL_DAY = 0
    private val LABEL_WEEK = 1
    var curTop = LABEL_DAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunch()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        //cardListEvent()
        switchEvent()
    }

    private fun switchEvent() {
        paint_top_TopFragment.setOnClickListener {
            if (curLabel != LABEL_PAINT) {
                curLabel = LABEL_PAINT
                paint_top_TopFragment.setTextColor(Color.parseColor("#CCBE97"))
                cos_top_TopFragment.setTextColor(Color.parseColor("#101010"))
                write_top_TopFragment.setTextColor(Color.parseColor("#101010"))
            }
        }
        cos_top_TopFragment.setOnClickListener {
            if (curLabel != LABEL_COS) {
                curLabel = LABEL_COS
                cos_top_TopFragment.setTextColor(Color.parseColor("#CCBE97"))
                paint_top_TopFragment.setTextColor(Color.parseColor("#101010"))
                write_top_TopFragment.setTextColor(Color.parseColor("#101010"))
            }
        }
        write_top_TopFragment.setOnClickListener {
            if (curLabel != LABEL_WRITE) {
                curLabel = LABEL_WRITE
                write_top_TopFragment.setTextColor(Color.parseColor("#CCBE97"))
                paint_top_TopFragment.setTextColor(Color.parseColor("#101010"))
                cos_top_TopFragment.setTextColor(Color.parseColor("#101010"))
            }
        }
        day_top_TopFragment.setOnClickListener {
            if (curTop != LABEL_DAY) {
                curTop = LABEL_DAY
                day_top_TopFragment.setTextColor(Color.parseColor("#CCBE97"))
                week_top_TopFragment.setTextColor(Color.parseColor("#101010"))
            }
        }
        week_top_TopFragment.setOnClickListener {
            if (curTop != LABEL_WEEK) {
                curTop = LABEL_WEEK
                week_top_TopFragment.setTextColor(Color.parseColor("#CCBE97"))
                day_top_TopFragment.setTextColor(Color.parseColor("#101010"))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!ConstantRepository.topFragmentUpdate) {
            //cardListEvent()
        }
    }

    private fun registerLaunch() {
        discussLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun cardListEvent() {
        val esRecommendCos =
            cosService.getEsRecommendCos(FOLLOW_COUNT, if(ConstantRepository.loginStatus) InfoRepository.user.id else "0")
        if (esRecommendCos != null && esRecommendCos.msg == "success") {
            val data = esRecommendCos.data
            val cardInfoList = data.cosList
            discuss_cards_TopFragment.removeAllViews()
            for (follow in cardInfoList) {
                val cosPhoto = MyUtils.fromStringToList(follow.cosPhoto)
                val label = MyUtils.fromStringToList(follow.label)
                val card = DiscussCardHomeFragment(
                    parentContext!!,
                    follow.number, follow.id, follow.username, follow.photo,
                    cosPhoto, label, follow.description, follow.createTime, discussLauncher
                )
                discuss_cards_TopFragment.addView(card)
            }
        }
        ConstantRepository.topFragmentUpdate = true
    }
}
