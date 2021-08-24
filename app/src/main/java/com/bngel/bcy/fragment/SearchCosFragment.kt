package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.fragment_search_cos.*

class SearchCosFragment: Fragment() {

    private val cosService = CosControllerService()
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
        return inflater.inflate(R.layout.fragment_search_cos, container, false)
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
                val postEsSearchCos = cosService.postEsSearchCos(
                    COS_COUNT,
                    keyword,
                    pageNow,
                    if (ConstantRepository.loginStatus) InfoRepository.user.id else "0"
                )
                if (postEsSearchCos != null && postEsSearchCos.msg == "success") {
                    cards_SearchCosFragment.removeAllViews()
                    val coses = postEsSearchCos.data.cosList
                    for (cos in coses) {
                        val view = DiscussCardHomeFragment(parentContext!!, cos.number, cos.id, cos.username, cos.photo, MyUtils.fromStringToList(cos.cosPhoto),
                            MyUtils.fromStringToList(cos.label), cos.description, cos.createTime, detailLauncher)
                        cards_SearchCosFragment.addView(view)
                    }
                }
            }
        })

    }
}