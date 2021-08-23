package com.bngel.bcy.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.activity.CreateCommunityActivity
import com.bngel.bcy.service.CircleControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.CommunityCardView
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : Fragment() {

    private var createLauncher: ActivityResultLauncher<Intent>? = null
    private var detailLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
    }
    var parentContext: Context? = null
    val CIRCLE_COUNT = 10
    var pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunch()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun registerLaunch() {
        createLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    override fun onResume() {
        super.onResume()
        if (!ConstantRepository.circleFragmentUpdate) {
            circleEvent()
            ConstantRepository.circleFragmentUpdate = true
        }
    }

    private fun initWidget() {
        circleEvent()
        createEvent()
    }

    private fun createEvent() {
        create_circle_CommunityFragment.setOnClickListener {
            val intent = Intent(parentContext!!, CreateCommunityActivity::class.java)
            createLauncher?.launch(intent)
        }
    }

    private fun circleEvent() {
        val circleService = CircleControllerService()
        val esRecommendCircle = circleService.getEsRecommendCircle(
            CIRCLE_COUNT,
            pageNow,
            if (ConstantRepository.loginStatus) InfoRepository.user.id else "0"
        )
        if (esRecommendCircle != null) {
            val recommendCircleList = esRecommendCircle.data.recommendCircleList
            circles_CommunityFragment.removeAllViews()
            for (circle in recommendCircleList) {
                val card = CommunityCardView(parentContext!!, circle.photo, circle.circleName, circle.description, circle.followCounts, detailLauncher)
                circles_CommunityFragment.addView(card)
            }
        }
    }
}