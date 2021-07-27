package com.bngel.bcy.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bngel.bcy.R
import com.bngel.bcy.adapter.MainViewPagerFragmentStateAdapter
import com.bngel.bcy.utils.AnimRepository.playTabBtnClickAnim
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.ConstantRepository.PAGE_COMMUNITY
import com.bngel.bcy.utils.ConstantRepository.PAGE_HOME
import com.bngel.bcy.utils.ConstantRepository.PAGE_ME
import com.bngel.bcy.utils.ConstantRepository.PAGE_QANDA
import com.bngel.bcy.widget.MainActivity.ItemTabMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_tab_main_activity.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vpEvent()
        tabEvent()
    }

    private fun vpEvent() {
        val adapter = MainViewPagerFragmentStateAdapter(this, 4)
        viewpager_MainActivity.adapter = adapter
        viewpager_MainActivity.isUserInputEnabled = false
        viewpager_MainActivity.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    PAGE_HOME -> {
                        playTabBtnClickAnim(home_bottom_tab_main_activity, R.drawable.home_bottom_tab_main_activity_ccbe97)
                        ConstantRepository.homeTabStatus = PAGE_HOME
                        community_bottom_tab_main_activity.setImageResource(R.drawable.community_bottom_tab_main_activity_979797)
                        me_bottom_tab_main_activity.setImageResource(R.drawable.me_bottom_tab_main_activity_979797)
                        qanda_bottom_tab_main_activity.setImageResource(R.drawable.qanda_bottom_tab_main_activity_979797)
                        viewpager_MainActivity.currentItem = PAGE_HOME
                    }
                    PAGE_QANDA -> {
                        playTabBtnClickAnim(qanda_bottom_tab_main_activity, R.drawable.qanda_bottom_tab_main_activity_ccbe97)
                        ConstantRepository.homeTabStatus = PAGE_QANDA
                        community_bottom_tab_main_activity.setImageResource(R.drawable.community_bottom_tab_main_activity_979797)
                        home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_979797)
                        me_bottom_tab_main_activity.setImageResource(R.drawable.me_bottom_tab_main_activity_979797)
                        viewpager_MainActivity.currentItem = PAGE_QANDA
                    }
                    PAGE_COMMUNITY -> {
                        playTabBtnClickAnim(community_bottom_tab_main_activity, R.drawable.community_bottom_tab_main_activity_ccbe97)
                        ConstantRepository.homeTabStatus = PAGE_COMMUNITY
                        qanda_bottom_tab_main_activity.setImageResource(R.drawable.qanda_bottom_tab_main_activity_979797)
                        home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_979797)
                        me_bottom_tab_main_activity.setImageResource(R.drawable.me_bottom_tab_main_activity_979797)
                        viewpager_MainActivity.currentItem = PAGE_COMMUNITY
                    }
                    PAGE_ME -> {
                        playTabBtnClickAnim(me_bottom_tab_main_activity, R.drawable.me_bottom_tab_main_activity_ccbe97)
                        ConstantRepository.homeTabStatus = PAGE_ME
                        community_bottom_tab_main_activity.setImageResource(R.drawable.community_bottom_tab_main_activity_979797)
                        home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_979797)
                        qanda_bottom_tab_main_activity.setImageResource(R.drawable.qanda_bottom_tab_main_activity_979797)
                        viewpager_MainActivity.currentItem = PAGE_ME
                    }
                }
            }
        })
    }

    private fun tabEvent() {
        home_bottom_tab_main_activity.setOnClickListener {
            if (ConstantRepository.homeTabStatus != PAGE_HOME) {
                playTabBtnClickAnim(it as ImageView, R.drawable.home_bottom_tab_main_activity_ccbe97)
                ConstantRepository.homeTabStatus = PAGE_HOME
                community_bottom_tab_main_activity.setImageResource(R.drawable.community_bottom_tab_main_activity_979797)
                me_bottom_tab_main_activity.setImageResource(R.drawable.me_bottom_tab_main_activity_979797)
                qanda_bottom_tab_main_activity.setImageResource(R.drawable.qanda_bottom_tab_main_activity_979797)
                viewpager_MainActivity.currentItem = PAGE_HOME
            }
        }
        qanda_bottom_tab_main_activity.setOnClickListener {
            if (ConstantRepository.homeTabStatus != PAGE_QANDA) {
                playTabBtnClickAnim(it as ImageView, R.drawable.qanda_bottom_tab_main_activity_ccbe97)
                ConstantRepository.homeTabStatus = PAGE_QANDA
                community_bottom_tab_main_activity.setImageResource(R.drawable.community_bottom_tab_main_activity_979797)
                me_bottom_tab_main_activity.setImageResource(R.drawable.me_bottom_tab_main_activity_979797)
                home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_979797)
                viewpager_MainActivity.currentItem = PAGE_QANDA
            }
        }
        community_bottom_tab_main_activity.setOnClickListener {
            if (ConstantRepository.homeTabStatus != PAGE_COMMUNITY) {
                playTabBtnClickAnim(it as ImageView, R.drawable.community_bottom_tab_main_activity_ccbe97)
                ConstantRepository.homeTabStatus = PAGE_COMMUNITY
                home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_979797)
                me_bottom_tab_main_activity.setImageResource(R.drawable.me_bottom_tab_main_activity_979797)
                qanda_bottom_tab_main_activity.setImageResource(R.drawable.qanda_bottom_tab_main_activity_979797)
                viewpager_MainActivity.currentItem = PAGE_COMMUNITY
            }
        }
        me_bottom_tab_main_activity.setOnClickListener {
            if (ConstantRepository.homeTabStatus != PAGE_ME) {
                playTabBtnClickAnim(it as ImageView, R.drawable.me_bottom_tab_main_activity_ccbe97)
                ConstantRepository.homeTabStatus = PAGE_ME
                home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_979797)
                community_bottom_tab_main_activity.setImageResource(R.drawable.community_bottom_tab_main_activity_979797)
                qanda_bottom_tab_main_activity.setImageResource(R.drawable.qanda_bottom_tab_main_activity_979797)
                viewpager_MainActivity.currentItem = PAGE_ME
            }
        }
        // 默认启动选择首页
        home_bottom_tab_main_activity.setImageResource(R.drawable.home_bottom_tab_main_activity_ccbe97)
        ConstantRepository.homeTabStatus = PAGE_HOME
    }
}