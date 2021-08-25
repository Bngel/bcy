package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.fragment_detail_create.*


class DetailCreateFragment: Fragment(){

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
        return inflater.inflate(R.layout.fragment_detail_create, container, false)
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
        val acgFollowCos = cosService.getAcgFollowCos(InfoRepository.user.id, COS_COUNT, pageNow)
        if (acgFollowCos != null && acgFollowCos.msg == "success") {
            val coses = acgFollowCos.data.cosFollowList
            cards_DetailCreateFragment.removeAllViews()
            for (cos in coses) {
                val view = DiscussCardHomeFragment(parentContext!!, cos.number, cos.id, cos.username, cos.photo,
                    cos.cosPhoto, cos.label, cos.description, cos.createTime, detailLauncher)
                cards_DetailCreateFragment.addView(view)
            }
        }
    }
}