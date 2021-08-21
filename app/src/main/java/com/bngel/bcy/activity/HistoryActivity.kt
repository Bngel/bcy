package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.activity_like.*

class HistoryActivity : BaseActivity() {

    private var discussLauncher: ActivityResultLauncher<Intent>? = null
    val likeService = LikeControllerService()
    val LIKE_COUNT = 20
    val pageNew = 1

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

    private fun initWidget(){}
}