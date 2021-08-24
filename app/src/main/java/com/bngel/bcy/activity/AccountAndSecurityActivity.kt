package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import kotlinx.android.synthetic.main.activity_account_and_security.*

class AccountAndSecurityActivity : BaseActivity() {

    private var passwordLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_and_security)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        passwordLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun initWidget() {
        changePasswordEvent()
    }

    private fun changePasswordEvent() {
        changePassword_AccountAndSecurityActivity.setOnClickListener {
            val intent = Intent(this, ChangeTelActivity::class.java)
            passwordLauncher?.launch(intent)
        }
    }
}