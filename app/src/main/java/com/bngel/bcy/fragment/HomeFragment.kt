package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bngel.bcy.R
import com.bngel.bcy.widget.MainActivity.ItemTabMainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class HomeFragment: Fragment() {
    private val curPage = MutableLiveData<View>(null)

    var parentContext: Context? = null
    private val totalPages = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun initWidget() {
        totalPages.addAll(listOf("关注","关注","关注","关注","关注","关注","关注","关注","关注"))
        for (page in totalPages) {
            top_tab_home_fragment.addView(createTabItem(page))
        }
    }

    private fun createTabItem(text: String): View {
        val view = ItemTabMainActivity(parentContext!!, text)
        view.setOnClickListener {
            val curView = curPage.value
            if (curView == null){
                curPage.value = it
                (it as ItemTabMainActivity).setClicked()
            }
            else if (curView != it) {
                (curView as ItemTabMainActivity).setUnClicked()
                curPage.value = it
                (it as ItemTabMainActivity).setClicked()
            }
        }

        return view
    }
}