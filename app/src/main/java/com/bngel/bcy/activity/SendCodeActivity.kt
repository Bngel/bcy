package com.bngel.bcy.activity

import android.os.Bundle
import android.widget.Toast
import com.bngel.bcy.R
import com.bngel.bcy.service.UserControllerService
import kotlinx.android.synthetic.main.activity_send_code.*

class SendCodeActivity : BaseActivity() {

    private val userService = UserControllerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_code)
        initWidget()
    }

    private fun initWidget(){
        codeInputEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_SendCodeActivity.setOnClickListener {
            finish()
        }
    }

    private fun codeInputEvent() {
        code_inputCode_SendCodeActivity.setOnCompleteListener { code ->
            val postOauthLoginBySms = userService.postOauthLoginBySms(
                code, intent.getStringExtra("phone") ?: "")
            Toast.makeText(this,
                when(postOauthLoginBySms?.msg) {
                    "codeWrong" -> "验证码错误"
                    "success" -> "登录成功"
                    else -> "发生了未知错误"
                },
            Toast.LENGTH_SHORT).show()
            if (postOauthLoginBySms?.msg == "success") {
                intent.putExtra("loginStatus", true)
                finish()
            }
        }
    }

}