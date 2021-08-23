package com.bngel.bcy.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.adapter.CommunityViewPagerFragmentStateAdapter
import com.bngel.bcy.adapter.FollowAndFansViewPagerFragmentStateAdapter
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_follow_and_fan.*

class CommunityActivity : BaseActivity() {

    private var curPage = 0

    private var createLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        createLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun initWidget() {
        closeEvent()
        createEvent()
        vpEvent()
        tabEvent()
    }

    private fun closeEvent() {
        close_btn_CommunityActivity.setOnClickListener {
            finish()
        }
    }

    private fun createEvent() {
        add_CommunityActivity.setOnClickListener {
            val intent = Intent(this, CreateCommunityActivity::class.java)
            createLauncher?.launch(intent)
        }
    }

    private fun vpEvent() {
        val adapter = CommunityViewPagerFragmentStateAdapter(this, 2)
        viewpager_CommunityActivity.adapter = adapter
        viewpager_CommunityActivity.isUserInputEnabled = false
    }

    private fun tabEvent() {
        myFollow_CommunityActivity.setOnClickListener {
            curPage = 0
            myFollow_CommunityActivity.setTextColor(Color.parseColor("#DFC048"))
            //myCreate_CommunityActivity.setTextColor(Color.parseColor("#101010"))
            recommend_CommunityActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_CommunityActivity.currentItem = curPage
        }
        /*myCreate_CommunityActivity.setOnClickListener {
            curPage = 1
            myFollow_CommunityActivity.setTextColor(Color.parseColor("#101010"))
            myCreate_CommunityActivity.setTextColor(Color.parseColor("#DFC048"))
            recommend_CommunityActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_CommunityActivity.currentItem = curPage
        }*/
        recommend_CommunityActivity.setOnClickListener {
            curPage = 1
            myFollow_CommunityActivity.setTextColor(Color.parseColor("#101010"))
            //myCreate_CommunityActivity.setTextColor(Color.parseColor("#101010"))
            recommend_CommunityActivity.setTextColor(Color.parseColor("#DFC048"))
            viewpager_CommunityActivity.currentItem = curPage
        }
    }
}