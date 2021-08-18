package com.bngel.bcy.fragment;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import com.bngel.bcy.widget.others.DiscussCommentView
import kotlinx.android.synthetic.main.fragment_follow.*

class FollowFragment: Fragment() {

    private val cosService = CosControllerService()
    val FOLLOW_COUNT = 20
    var pageNow = 1
    var parentContext: Context? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        cardListEvent()
    }

    override fun onResume() {
        super.onResume()
        if (!ConstantRepository.followFragmentUpdate) {
            cardListEvent()
        }
    }

    private fun cardListEvent() {
        if (ConstantRepository.loginStatus) {
            val acgFollowCos =
                cosService.getAcgFollowCos(InfoRepository.user.id, FOLLOW_COUNT, pageNow)
            if (acgFollowCos != null && acgFollowCos.msg == "success") {
                val data = acgFollowCos.data
                val cardInfoList = data.cosFollowList
                discuss_cards_FollowFragment.removeAllViews()
                for (follow in cardInfoList) {
                    val card = DiscussCardHomeFragment(
                        parentContext!!,
                        follow.number, follow.id, follow.username ?: "", follow.photo,
                        follow.cosPhoto, follow.label, follow.description, follow.createTime
                    )
                    discuss_cards_FollowFragment.addView(card)
                }
            }
            ConstantRepository.followFragmentUpdate
        }
    }
}
