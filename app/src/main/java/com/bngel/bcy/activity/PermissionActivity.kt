package com.bngel.bcy.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bngel.bcy.R
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : BaseActivity() {

    private var nowPermission = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        confirmEvent()
        switchEvent()
    }

    private fun closeEvent() {
        close_btn_PermissionActivity.setOnClickListener {
            finish()
        }
    }

    private fun confirmEvent() {
        confirm_btn_PermissionActivity.setOnClickListener {
            val intent = Intent(this, PostDiscussActivity::class.java)
            intent.putExtra("permission", nowPermission)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun switchEvent() {
        all_PermissionActivity.setOnClickListener {
            all_image_PermissionActivity.visibility = View.VISIBLE
            fans_image_PermissionActivity.visibility = View.GONE
            self_image_PermissionActivity.visibility = View.GONE
            nowPermission = 1
        }
        fans_PermissionActivity.setOnClickListener {
            all_image_PermissionActivity.visibility = View.GONE
            fans_image_PermissionActivity.visibility = View.VISIBLE
            self_image_PermissionActivity.visibility = View.GONE
            nowPermission = 2
        }
        self_PermissionActivity.setOnClickListener {
            all_image_PermissionActivity.visibility = View.GONE
            fans_image_PermissionActivity.visibility = View.GONE
            self_image_PermissionActivity.visibility = View.VISIBLE
            nowPermission = 3
        }
    }
}