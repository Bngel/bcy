package com.bngel.bcy.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.bngel.bcy.R
import com.bngel.bcy.adapter.FollowAndFansViewPagerFragmentStateAdapter
import com.bngel.bcy.adapter.MainViewPagerFragmentStateAdapter
import com.bngel.bcy.utils.AnimRepository
import com.bngel.bcy.utils.ConstantRepository
import kotlinx.android.synthetic.main.activity_follow_and_fan.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_tab_main_activity.*

class FollowAndFanActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow_and_fan)
        initWidget()
    }

    private fun initWidget() {
        vpEvent()
        tabEvent()
        closeEvent()
    }

    private fun closeEvent(){
        close_FollowAndFanActivity.setOnClickListener {
            finish()
        }
    }

    private fun vpEvent(){
        val adapter = FollowAndFansViewPagerFragmentStateAdapter(this, 2)
        viewpager_FollowAndFanActivity.adapter = adapter
        viewpager_FollowAndFanActivity.isUserInputEnabled = false
        viewpager_FollowAndFanActivity.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    ConstantRepository.PAGE_FOLLOW -> {
                        follow_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#CCBE97"))
                        fans_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#7B7878"))
                        viewpager_FollowAndFanActivity.currentItem = ConstantRepository.PAGE_FOLLOW
                    }
                    ConstantRepository.PAGE_FANS -> {
                        fans_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#CCBE97"))
                        follow_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#7B7878"))
                        viewpager_FollowAndFanActivity.currentItem = ConstantRepository.PAGE_FANS
                    }
                }
            }
        })
    }

    private fun tabEvent() {
        val tab = intent.getStringExtra("type")?:"follow"
        if (tab == "follow") {
            follow_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#CCBE97"))
            fans_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#7B7878"))
            viewpager_FollowAndFanActivity.currentItem = ConstantRepository.PAGE_FOLLOW
        }
        else if (tab == "fans") {
            fans_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#CCBE97"))
            follow_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#7B7878"))
            viewpager_FollowAndFanActivity.currentItem = ConstantRepository.PAGE_FANS
        }
        follow_switch_FollowAndFanActivity.setOnClickListener {
            follow_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#CCBE97"))
            fans_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#7B7878"))
            viewpager_FollowAndFanActivity.currentItem = ConstantRepository.PAGE_FOLLOW
        }
        fans_switch_FollowAndFanActivity.setOnClickListener {
            fans_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#CCBE97"))
            follow_switch_FollowAndFanActivity.setTextColor(Color.parseColor("#7B7878"))
            viewpager_FollowAndFanActivity.currentItem = ConstantRepository.PAGE_FANS
        }
    }
}