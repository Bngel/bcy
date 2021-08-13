package com.bngel.bcy.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.bngel.bcy.R
import com.bngel.bcy.service.SmsControllerService
import com.bngel.bcy.service.UserControllerService
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.widget.doOnTextChanged as doOnTextChanged

class LoginActivity : BaseActivity() {

    private var codeLauncher: ActivityResultLauncher<Intent>? = null
    private val smsService = SmsControllerService()
    private val userService = UserControllerService()
    private var loginType = 0
    private var passwordType = 0

    init {
        codeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data?.getBooleanExtra("loginStatus", false) == true){
                    intent.putExtra("loginStatus", true)
                    finish()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initWidget()
    }

    private fun initWidget() {
        clearTelEvent()
        okEvent()
        telInputEvent()
        closeEvent()
        switchTypeEvent()
        passwordVisibleEvent()
        passwordInputEvent()
    }

    private fun passwordInputEvent() {
        password_input_edit_LoginActivity.doOnTextChanged { text, start, before, count ->
            if (loginType == 1 && text != null && text.isNotEmpty() && tel_input_LoginActivity.text.toString() != "") {
                next_btn_LoginActivity.setBackgroundResource(R.drawable.bk_login_btn_valid_login_activity)
                next_btn_LoginActivity.isClickable = true
                next_btn_LoginActivity.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else {
                next_btn_LoginActivity.setBackgroundResource(R.drawable.bk_login_btn_login_activity)
                next_btn_LoginActivity.isClickable = false
                next_btn_LoginActivity.setTextColor(Color.parseColor("#CECECE"))
            }
        }
    }

    private fun passwordVisibleEvent() {
        visible_password_LoginActivity.setOnClickListener {
            if (passwordType == 0) {
                password_input_edit_LoginActivity.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordType = 1
            }
            else {
                password_input_edit_LoginActivity.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordType = 0
            }
        }
    }

    private fun switchTypeEvent() {
        switch_type_btn_LoginActivity.setOnClickListener {
            if (loginType == 0) {
                password_type_LoginActivity.visibility = View.VISIBLE
                next_btn_LoginActivity.text = "登录"
                switch_type_btn_LoginActivity.text = "短信登录"
                forget_password_btn_LoginActivity.visibility = View.VISIBLE
                loginType = 1
            }
            else {
                password_type_LoginActivity.visibility = View.GONE
                next_btn_LoginActivity.text = "下一步"
                switch_type_btn_LoginActivity.text = "账号密码登录"
                forget_password_btn_LoginActivity.visibility = View.GONE
                loginType = 0
            }
            password_input_edit_LoginActivity.setText("")
        }
    }

    private fun closeEvent() {
        skip_btn_LoginActivity.setOnClickListener {
            finish()
        }
    }

    private fun okEvent() {
        next_btn_LoginActivity.setOnClickListener {
            if (loginType == 0)
                nextEvent()
            else
                loginEvent()
        }
    }

    private fun loginEvent() {
        if (tel_input_LoginActivity.length() < 11) {
            Toast.makeText(this, "手机号不符合条件, 请重新输入", Toast.LENGTH_SHORT).show()
        }
        else {
            val tel = tel_input_LoginActivity.text.toString()
            val password = password_input_edit_LoginActivity.text.toString()
            val postOauthToken = userService.postOauthToken(tel, password)
            if (postOauthToken != null) {
                intent.putExtra("loginStatus", true)
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun nextEvent() {
        if (tel_input_LoginActivity.length() < 11) {
            Toast.makeText(this, "手机号不符合条件, 请重新输入", Toast.LENGTH_SHORT).show()
        }
        else {
            val tel = tel_input_LoginActivity.text.toString()
            val intent = Intent(this, SendCodeActivity::class.java)
            val postOauthCode = smsService.postOauthCode(tel, 1)
            if (postOauthCode != null) {
                Toast.makeText(this,
                    when (postOauthCode.msg) {
                        "repeatWrong" -> "获取验证码次数过多"
                        "existWrong" -> "手机号不存在"
                        "success" -> "发送验证码成功"
                        else -> "发生未知错误"
                    },
                    Toast.LENGTH_SHORT).show()
                if (postOauthCode.msg == "success") {
                    intent.putExtra("phone", tel)
                    codeLauncher?.launch(intent)
                    finish()
                }
            }
            else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun telInputEvent() {
        tel_input_LoginActivity.doOnTextChanged { text, start, before, count ->
            if (text?.length == 11) {
                if (loginType == 0 || (loginType == 1 && password_input_edit_LoginActivity.text.toString() != "")) {
                    next_btn_LoginActivity.setBackgroundResource(R.drawable.bk_login_btn_valid_login_activity)
                    next_btn_LoginActivity.isClickable = true
                    next_btn_LoginActivity.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
            else {
                next_btn_LoginActivity.setBackgroundResource(R.drawable.bk_login_btn_login_activity)
                next_btn_LoginActivity.isClickable = false
                next_btn_LoginActivity.setTextColor(Color.parseColor("#CECECE"))
            }
        }
    }

    private fun clearTelEvent() {
        tel_clear_btn_LoginActivity.setOnClickListener {
            tel_input_LoginActivity.setText("")
        }
    }

}