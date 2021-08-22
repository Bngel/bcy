package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import kotlinx.android.synthetic.main.activity_post_discuss.*

class PostDiscussActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_discuss)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_PostDiscussActivity.setOnClickListener {
            finish()
        }
    }
}