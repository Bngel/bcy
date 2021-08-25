package com.bngel.bcy.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlin.math.cos

class DiscoveryFragment: Fragment() {

    private val cosService = CosControllerService()
    val FOLLOW_COUNT = 1
    var pageNow = 1
    var parentContext: Context? = null
    private var discussLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunch()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
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
        if (!ConstantRepository.discoveryFragmentUpdate) {
            cardListEvent()
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
            discuss_cards_DiscoveryFragment.removeAllViews()
            for (follow in cardInfoList) {
                val cosPhoto = MyUtils.fromStringToList(follow.cosPhoto)
                Log.d("TestLog", follow.cosPhoto)
                Log.d("TestLog", cosPhoto.toString())
                val label = MyUtils.fromStringToList(follow.label)
                val card = DiscussCardHomeFragment(
                    parentContext!!,
                    follow.number, follow.id, follow.username, follow.photo,
                    cosPhoto, label, follow.description, follow.createTime, discussLauncher
                )
                discuss_cards_DiscoveryFragment.addView(card)
            }
        }
        ConstantRepository.discoveryFragmentUpdate = true
    }
}
