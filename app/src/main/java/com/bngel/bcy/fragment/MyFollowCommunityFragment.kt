package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.service.CircleControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.CommunityCardView
import kotlinx.android.synthetic.main.fragment_my_follow_community.*

class MyFollowCommunityFragment : Fragment() {

    private val circleService = CircleControllerService()

    private var detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        return inflater.inflate(R.layout.fragment_my_follow_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun registerLaunch() {
        detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun initWidget() {
        circleEvent()
    }

    private fun circleEvent() {
        if (ConstantRepository.loginStatus) {
            val acgPersonalCircle =
                circleService.getAcgPersonalCircle(CIRCLE_COUNT, InfoRepository.user.id, pageNow)
            if (acgPersonalCircle != null && acgPersonalCircle.msg == "success") {
                val circles = acgPersonalCircle.data.personalCircleList
                cards_MyFollowCommunityFragment.removeAllViews()
                for (circle in circles) {
                    val view = CommunityCardView(parentContext!!, circle.photo, circle.circleName, circle.description, 0, detailLauncher)
                    cards_MyFollowCommunityFragment.addView(view)
                }
            }
        }
    }
}