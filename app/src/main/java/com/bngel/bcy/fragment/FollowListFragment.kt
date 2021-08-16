package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.service.FansControllerService
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import com.bngel.bcy.widget.others.FollowAndFanCardView
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.fragment_follow_list.*

class FollowListFragment: Fragment() {

    var parentContext: Context? = null
    val followService = FansControllerService()
    val FOLLOW_COUNT = 50
    var pageNow = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        followListEvent()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        followListEvent()
    }

    private fun followListEvent() {
        val userFollowList =
            followService.getUserFollowList(FOLLOW_COUNT, InfoRepository.user.id, pageNow)
        if (userFollowList != null) {
            if (userFollowList.msg == "success") {
                val followData = userFollowList.data
                val followList = followData.followList
                userCard_FollowListFragment.removeAllViews()
                for (follow in followList) {
                    val card = FollowAndFanCardView(parentContext!!, follow)
                    userCard_FollowListFragment.addView(card)
                }
            }
        }
    }
}
