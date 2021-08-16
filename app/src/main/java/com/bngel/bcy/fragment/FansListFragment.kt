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
import kotlinx.android.synthetic.main.fragment_fans_list.*
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.fragment_follow_list.*
import kotlinx.android.synthetic.main.fragment_follow_list.userCard_FollowListFragment

class FansListFragment: Fragment() {

    var parentContext: Context? = null
    val fansService = FansControllerService()
    val FANS_COUNT = 50
    var pageNow = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fans_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        fansListEvent()
    }

    override fun onResume() {
        super.onResume()
        fansListEvent()
    }

    private fun fansListEvent() {
        val userFansList =
            fansService.getUserFansList(FANS_COUNT, InfoRepository.user.id, pageNow)
        if (userFansList != null) {
            if (userFansList.msg == "success") {
                val fansData = userFansList.data
                val fansList = fansData.fansList
                userCard_FansListFragment.removeAllViews()
                for (fans in fansList) {
                    val card = FollowAndFanCardView(parentContext!!, fans)
                    userCard_FansListFragment.addView(card)
                }
            }
        }
    }
}
