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
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bngel.bcy.widget.others.QAndACardView
import kotlinx.android.synthetic.main.fragment_qanda.*

class QAndAFragment: Fragment() {

    var parentContext: Context? = null
    private val QA_COUNT = 10
    private var detailLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
    }
    private val qaService = QAControllerService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qanda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        qaEvent()
    }

    private fun qaEvent() {
        val esRecommendQa = qaService.getEsRecommendQa(
            QA_COUNT,
            if (ConstantRepository.loginStatus) InfoRepository.user.id else "0"
        )
        if (esRecommendQa != null && esRecommendQa.msg == "success") {
            val qaList = esRecommendQa.data.qaList
            qaCards_QAndAFragment.removeAllViews()
            for (qa in qaList) {
                val card = QAndACardView(parentContext!!, qa.number, 0, qa.title, qa.description, qa.createTime, MyUtils.fromStringToList(qa.label), detailLauncher)
                qaCards_QAndAFragment.addView(card)
            }
        }
    }
}