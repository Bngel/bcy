package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import com.bngel.bcy.R
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_set_password.*

class SetPasswordActivity : BaseActivity() {

    private val personalService = PersonalControllerService()
    private var newType = 0
    private var confirmType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_password)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        confirmEvent()
        eyeEvent()
    }

    private fun closeEvent() {
        close_btn_SetPasswordActivity.setOnClickListener {
            finish()
        }
    }

    private fun eyeEvent() {
        new_eye_SetPasswordActivity.setOnClickListener {
            if (newType == 0) {
                new_password_SetPasswordActivity.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                newType = 1
            }
            else {
                new_password_SetPasswordActivity.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                newType = 0
            }
        }
        confirm_eye_SetPasswordActivity.setOnClickListener {
            if (newType == 0) {
                confirm_password_SetPasswordActivity.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                newType = 1
            }
            else {
                confirm_password_SetPasswordActivity.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                newType = 0
            }
        }
    }

    private fun confirmEvent() {
        confirm_password_SetPasswordActivity.setOnClickListener {
            val newPassword = new_password_SetPasswordActivity.text.toString()
            val confirmPassword = confirm_password_SetPasswordActivity.text.toString()
            if (newPassword == confirmPassword) {
                if (newPassword.length < 6) {
                    Toast.makeText(this, "密码需至少为6位", Toast.LENGTH_SHORT).show()
                }
                else {
                    val postUserSetPassword =
                        personalService.postUserSetPassword(InfoRepository.user.id, confirmPassword)
                    if (postUserSetPassword != null && postUserSetPassword.msg == "success") {
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
                        InfoRepository.quitStatus(this)
                        finish()
                    }
                    else {
                        Toast.makeText(this, "输入密码不一致", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}