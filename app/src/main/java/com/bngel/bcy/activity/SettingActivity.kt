package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.UserControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    private var accountLauncher: ActivityResultLauncher<Intent>? = null
    private var msgLauncher: ActivityResultLauncher<Intent>? = null
    private var shieldLauncher: ActivityResultLauncher<Intent>? = null

    private val settingService = UserControllerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        accountLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
        msgLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
        shieldLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }
    private fun initWidget() {
        accountSecurityEvent()
        msgRemindEvent()
        forbidEvent()
        logoutEvent()
    }

    private fun logoutEvent() {
        logout_SettingActivity.setOnClickListener {
            val logout = settingService.postOauthLogout(InfoRepository.user.id)
            if (logout != null && logout.msg == "success") {
                finish()
                InfoRepository.quitStatus(this)
            }
            else {
                Toast.makeText(this, "登出失败", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun accountSecurityEvent(){
        account_and_security_SettingActivity.setOnClickListener {
            val intent = Intent(this, AccountAndSecurityActivity::class.java)
            accountLauncher?.launch(intent)
        }
    }

    private fun msgRemindEvent() {
        msg_and_remind_SettingActivity.setOnClickListener {
            val intent = Intent(this, MsgAndRemindActivity::class.java)
            msgLauncher?.launch(intent)
        }
    }

    private fun forbidEvent() {
        forbid_SettingActivity.setOnClickListener {
            val intent = Intent(this, ShieldActivity::class.java)
            shieldLauncher?.launch(intent)
        }
    }

}