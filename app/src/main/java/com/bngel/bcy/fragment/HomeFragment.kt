package com.bngel.bcy.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bngel.bcy.R
import com.bngel.bcy.adapter.HomeViewPagerFragmentStateAdapter
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.widget.MainActivity.ItemTabMainActivity
import kotlinx.android.synthetic.main.activity_main.*
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
        vpEvent()
        tabEvent()
    }

    private fun vpEvent() {
        val adapter = HomeViewPagerFragmentStateAdapter(activity as AppCompatActivity, 6)
        viewpager_home_fragment.adapter = adapter
        viewpager_home_fragment.isUserInputEnabled = false
        viewpager_home_fragment.currentItem = 1
    }

    private fun tabEvent() {
        totalPages.addAll(listOf("关注","发现","关注","关注","关注","关注"))
        for (p in totalPages.indices) {
            val view = ItemTabMainActivity(parentContext!!, totalPages[p])
            if (totalPages[p] == "发现") {
                view.setClicked()
                curPage.value = view
            }
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
                if (viewpager_home_fragment != null)
                    viewpager_home_fragment.currentItem = p
            }
            top_tab_home_fragment.addView(view)
        }
    }
}