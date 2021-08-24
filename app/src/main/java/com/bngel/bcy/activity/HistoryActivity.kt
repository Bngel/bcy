package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.dao.HistoryControllerDao.HistoryControllerDao
import com.bngel.bcy.service.HistoryControllerService
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_like.*

class HistoryActivity : BaseActivity() {

    private var discussLauncher: ActivityResultLauncher<Intent>? = null
    val historyService = HistoryControllerService()
    val HISTORY_COUNT = 5
    val pageNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        discussLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun initWidget(){
        closeEvent()
        deleteEvent()
        historyEvent()
    }

    private fun closeEvent() {
        close_btn_HistoryActivity.setOnClickListener {
            finish()
        }
    }

    private fun deleteEvent() {
        delete_btn_HistoryActivity.setOnClickListener {
            val deleteUserAllHistory = historyService.deleteUserAllHistory(InfoRepository.user.id)
            if (deleteUserAllHistory != null && deleteUserAllHistory.msg == "success") {
                history_cards_HistoryActivity.removeAllViews()
                Toast.makeText(this, "已清空", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun historyEvent() {
        val userHistoryList =
            historyService.getUserHistoryList(HISTORY_COUNT, InfoRepository.user.id, pageNow)
        if (userHistoryList != null && userHistoryList.msg == "success") {
            val historyList = userHistoryList.data.historyCosList
            history_cards_HistoryActivity.removeAllViews()
            for (history in historyList) {
                val view = DiscussCardHomeFragment(this, history.number, history.id, history.username, history.photo, history.cosPhoto,
                    null, history.description, history.createTime, discussLauncher)
                history_cards_HistoryActivity.addView(view)
            }
        }
    }

}