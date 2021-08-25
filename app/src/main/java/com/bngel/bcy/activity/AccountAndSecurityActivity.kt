package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_account_and_security.*

class AccountAndSecurityActivity : BaseActivity() {

    private var passwordLauncher: ActivityResultLauncher<Intent>? = null
    private var phoneLauncher: ActivityResultLauncher<Intent>? = null
    private val personalService = PersonalControllerService()

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
        phoneLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    private fun initWidget() {
        changePasswordEvent()
        changePhoneEvent()
        phoneEvent()
    }

    private fun phoneEvent() {
        val userCommunityInfo = personalService.getUserCommunityInfo(InfoRepository.user.id)
        if (userCommunityInfo != null && userCommunityInfo.msg == "success") {
            val phone = userCommunityInfo.data.userCommunityInfo.phone
            phone_text_AccountAndSecurityActivity.text = phone.substring(0, 3) + "****" + phone.substring(7)
        }
        else {
            phone_text_AccountAndSecurityActivity.text = ""
        }
    }

    private fun changePasswordEvent() {
        changePassword_AccountAndSecurityActivity.setOnClickListener {
            val intent = Intent(this, SetPasswordActivity::class.java)
            passwordLauncher?.launch(intent)
        }
    }

    private fun changePhoneEvent() {
        changePhone_btn_AccountAndSecurityActivity.setOnClickListener {
            val intent = Intent(this, ChangeTelActivity::class.java)
            phoneLauncher?.launch(intent)
        }
    }
}