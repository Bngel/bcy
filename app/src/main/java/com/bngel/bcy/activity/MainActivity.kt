package com.bngel.bcy.activity

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.bngel.bcy.R
import com.bngel.bcy.adapter.MainViewPagerFragmentStateAdapter
import com.bngel.bcy.utils.AnimRepository.playTabBtnClickAnim
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.ConstantRepository.PAGE_COMMUNITY
import com.bngel.bcy.utils.ConstantRepository.PAGE_HOME
import com.bngel.bcy.utils.ConstantRepository.PAGE_ME
import com.bngel.bcy.utils.ConstantRepository.PAGE_QANDA
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_tab_main_activity.*

class MainActivity : BaseActivity() {

    private var createLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConstantRepository.DOWNLOAD_PATH = applicationContext.filesDir.absolutePath + "/bcy_Download"
        ConstantRepository.PORTRAIT_PATH = applicationContext.filesDir.absolutePath + "/bcy_Avt"
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        createLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            val data = result.data
        }
    }

    private fun initWidget() {
        loginEvent()
        vpEvent()
        tabEvent()
        createEvent()
    }

    private fun createEvent() {
        create_btn_MainActivity.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val contentView = LayoutInflater.from(this).inflate(R.layout.popup_create, null)
                val popWindow = PopupWindow(
                    contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true
                )
                popWindow.contentView = contentView
                val createDiscuss = contentView.findViewById<LinearLayout>(R.id.discuss_btn_PopupCreate)
                val createQa = contentView.findViewById<LinearLayout>(R.id.qa_btn_PopupCreate)
                createDiscuss.setOnClickListener {
                    val intent = Intent(this, PostDiscussActivity::class.java)
                    createLauncher?.launch(intent)
                    popWindow.dismiss()
                }
                createQa.setOnClickListener {

                }
                val rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
                popWindow.animationStyle = R.style.contextCommentAnim
                popWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
            }
            else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginEvent() {
        InfoRepository.initStatus(this)
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