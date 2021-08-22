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
import com.bngel.bcy.service.CircleControllerService
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import com.bngel.bcy.widget.others.CommunityCardView
import kotlinx.android.synthetic.main.fragment_discuss_of_community.*

class DiscussOfCommunityFragment: Fragment() {

    var parentContext: Context? = null
    private val COS_COUNT = 10
    private var pageNow = 1

    private var detailLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discuss_of_community, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        discussEvent()
    }

    private fun discussEvent() {
        val service = CircleControllerService()
        val intent = activity?.intent
        if (intent != null) {
            val circleName = intent.getStringExtra("circleName")
            if (circleName != null) {
                val acgCircleCosList =
                    service.getAcgCircleCosList(circleName, COS_COUNT, pageNow, 1)
                if (acgCircleCosList != null && acgCircleCosList.msg == "success") {
                    val coses = acgCircleCosList.data.circleCosList
                    cards_DiscussOfCommunityFragment.removeAllViews()
                    for (cos in coses) {
                        val card = DiscussCardHomeFragment(parentContext!!, cos.number, cos.id, cos.username, cos.photo,
                            cos.cosPhoto, null, cos.description, cos.createTime, detailLauncher)
                        cards_DiscussOfCommunityFragment.addView(card)
                    }
                }
            }
        }
    }
}