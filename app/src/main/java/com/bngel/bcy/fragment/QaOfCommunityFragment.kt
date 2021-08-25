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
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import com.bngel.bcy.widget.others.QAndACardView
import kotlinx.android.synthetic.main.fragment_discuss_of_community.*
import kotlinx.android.synthetic.main.fragment_discuss_of_community.cards_DiscussOfCommunityFragment
import kotlinx.android.synthetic.main.fragment_qa_of_community.*

class QaOfCommunityFragment: Fragment() {

    var parentContext: Context? = null
    private val QA_COUNT = 10
    private var pageNow = 1

    private var detailLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qa_of_community, container, false)
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
        val service = QAControllerService()
        val intent = activity?.intent
        if (intent != null) {
            val circleName = intent.getStringExtra("circleName")
            if (circleName != null) {
                val postEsSearchQa = service.postEsSearchQa(
                    QA_COUNT,
                    circleName,
                    pageNow,
                    if (ConstantRepository.loginStatus) InfoRepository.user.id else "0"
                )
                if (postEsSearchQa != null && postEsSearchQa.msg == "success") {
                    val qas = postEsSearchQa.data.qaList
                    cards_QaOfCommunityFragment.removeAllViews()
                    for (qa in qas) {
                        val card = QAndACardView(parentContext!!, qa.number, 0, qa.title, qa.description, qa.createTime, MyUtils.fromStringToList(qa.label),detailLauncher)
                        cards_QaOfCommunityFragment.addView(card)
                    }
                }
            }
        }
    }
}