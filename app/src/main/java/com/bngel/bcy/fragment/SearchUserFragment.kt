package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bngel.bcy.R
import com.bngel.bcy.bean.FansController.getUserFollowList.Follow
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.service.UserControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.others.FollowAndFanCardView
import kotlinx.android.synthetic.main.fragment_search_user.*

class SearchUserFragment  : Fragment() {

    private val userService = UserControllerService()
    private val personalService = PersonalControllerService()
    private var keyword = ""
    private var detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
    }
    var parentContext: Context? = null
    val COS_COUNT = 10
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
        return inflater.inflate(R.layout.fragment_search_user, container, false)
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
        cosEvent()
    }

    private fun cosEvent() {
        ConstantRepository.searchConfirm.observe(viewLifecycleOwner, Observer {
            if (it) {
                val keyword = activity?.intent?.getStringExtra("keyword")?:""
                Log.d("TestLog", "keyword: $keyword")
                val postEsSearchUser = userService.getCommunitySearchUser(
                    COS_COUNT,
                    if (ConstantRepository.loginStatus) InfoRepository.user.id else "0",
                    keyword,
                    pageNow,
                )
                Log.d("TestLog", "postEsSearchUser: " + postEsSearchUser.toString())
                if (postEsSearchUser != null && postEsSearchUser.msg == "success") {
                    cards_SearchUserFragment.removeAllViews()
                    val users = postEsSearchUser.data.searchUserList
                    for (user in users) {
                        val userPersonalInfoById = personalService.getUserPersonalInfoById(user.id)
                        val userUserCounts = personalService.getUserUserCounts(user.id)
                        if (userPersonalInfoById != null && userPersonalInfoById.msg == "success" && userUserCounts != null && userUserCounts.msg == "success") {
                            val info = userPersonalInfoById.data.personalInfo
                            val view = FollowAndFanCardView(parentContext!!, Follow(info.description?:"", userUserCounts.data.userCountsList[0].fansCounts, info.id, info.photo?:"", info.sex?:"", info.username?:""))
                            cards_SearchUserFragment.addView(view)
                        }
                    }
                }
            }
        })

    }
}