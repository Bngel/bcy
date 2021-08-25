package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.service.SmsControllerService
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_change_tel.*

class ChangeTelActivity : BaseActivity() {

    private val personalService = PersonalControllerService()
    private val smsService = SmsControllerService()
    private var codeLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_tel)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        codeLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data = result.data
                if (result.resultCode == RESULT_OK) {
                    finish()
                }
            }
    }

    private fun initWidget() {
        confirmEvent()
    }

    private fun confirmEvent() {
        confirm_btn_ChangeTelActivity.setOnClickListener {
            val phone = phone_ChangeTelActivity.text.toString()
            val userCommunityInfo = personalService.getUserCommunityInfo(InfoRepository.user.id)
            if (userCommunityInfo != null && userCommunityInfo.msg == "success") {
                if (userCommunityInfo.data.userCommunityInfo.phone == phone) {
                    val postOauthCode = smsService.postOauthCode(phone, 5)
                    if (postOauthCode != null) {
                        Toast.makeText(
                            this,
                            when (postOauthCode.msg) {
                                "repeatWrong" -> "获取验证码次数过多"
                                "existWrong" -> "手机号不存在"
                                "success" -> "发送验证码成功"
                                else -> "发生未知错误"
                            },
                            Toast.LENGTH_SHORT
                        ).show()
                        if (postOauthCode.msg == "success") {
                            val intent = Intent(this, SendCodeActivity::class.java)
                            intent.putExtra("phone", phone)
                            intent.putExtra("type", 5)
                            codeLauncher?.launch(intent)
                        }
                    } else {
                        Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "手机号错误", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "验证失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}