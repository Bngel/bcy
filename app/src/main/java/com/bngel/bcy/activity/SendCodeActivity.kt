package com.bngel.bcy.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.EditText
import android.widget.Toast
import com.bngel.bcy.R
import com.bngel.bcy.bean.UserController.postOauthLoginBySms.Data
import com.bngel.bcy.service.SmsControllerService
import com.bngel.bcy.service.UserControllerService
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_send_code.*


class SendCodeActivity : BaseActivity() {

    private val userService = UserControllerService()
    private val smsService = SmsControllerService()
    private var resendClock = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_code)
        initWidget()
    }

    private fun initWidget(){
        sendStatusEvent()
        codeInputEvent()
        closeEvent()
        clockEvent()
    }

    private fun sendStatusEvent() {
        send_status_text_SendCodeActivity.text = "已向您的手机 ${intent.getStringExtra("phone")?:""} 发送验证码"
    }

    private fun clockEvent() {
        clock_resend_SendCodeActivity.setOnClickListener {
            if (resendClock == 0) {
                val postOauthCode = smsService.postOauthCode(intent.getStringExtra("phone") ?: "", intent.getIntExtra("type", 1)?:1)
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
                }
                else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show()
                }
                resendClock = 60
                clock_resend_SendCodeActivity.isClickable = false
                val countDownTimer: CountDownTimer =
                    object : CountDownTimer(60000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            resendClock -= 1
                            clock_resend_SendCodeActivity.text = "重新发送验证码 (${resendClock}s)"
                        }

                        override fun onFinish() {
                            clock_resend_SendCodeActivity.isClickable = true
                            clock_resend_SendCodeActivity.text = "重新发送验证码"
                        }
                    }.start()
            }
        }
    }

    private fun closeEvent() {
        close_btn_SendCodeActivity.setOnClickListener {
            finish()
        }
    }

    private fun codeInputEvent() {
        code_inputCode_SendCodeActivity.setOnCompleteListener { code ->
            when (intent.getIntExtra("type", 1)) {
                1 -> {
                    val postOauthLoginBySms = userService.postOauthLoginBySms(
                        code, intent.getStringExtra("phone") ?: ""
                    )
                    Toast.makeText(
                        this,
                        when (postOauthLoginBySms?.msg) {
                            "codeWrong" -> "验证码错误"
                            "success" -> "登录成功"
                            else -> "发生了未知错误"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                    if (postOauthLoginBySms?.msg == "success") {
                        InfoRepository.token = postOauthLoginBySms.data.token ?: ""
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("loginStatus", true)
                        intent.putExtra("phone", intent.getStringExtra("phone"))
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
                5 -> {
                    val postUserChangePhone = smsService.postUserChangePhone(
                        code,
                        InfoRepository.user.id,
                        intent.getStringExtra("phone") ?: ""
                    )
                    Toast.makeText(
                        this,
                        when (postUserChangePhone?.msg) {
                            "codeWrong" -> "验证码错误"
                            "success" -> "修改成功"
                            else -> "发生了未知错误"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                    if (postUserChangePhone?.msg == "success") {
                        val intent = Intent(this, ChangeTelActivity::class.java)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    else {
                        code_inputCode_SendCodeActivity.apply {
                            for (i in 0 until childCount) {
                                if (getChildAt(i) is EditText) {
                                    val et = getChildAt(i) as EditText
                                    et.setText("")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}