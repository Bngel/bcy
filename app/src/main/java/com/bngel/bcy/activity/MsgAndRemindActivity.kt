package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_msg_and_remind.*

class MsgAndRemindActivity : BaseActivity() {

    private val personalService = PersonalControllerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_and_remind)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        statusEvent()
        checkEvent()
    }

    private fun closeEvent() {
        close_btn_MsgAndRemindActivity.setOnClickListener {
            finish()
        }
    }

    private fun statusEvent() {
        val userPersonalSetting = personalService.getUserPersonalSetting(InfoRepository.user.id)
        if (userPersonalSetting != null && userPersonalSetting.msg == "success") {
            val switch = userPersonalSetting.data.personalSetting
            if (switch.pushComment == 1) comment_switch_MsgAndRemindActivity.isChecked = true
            if (switch.pushLike == 1) like_switch_MsgAndRemindActivity.isChecked = true
            if (switch.pushFans == 1) fan_switch_MsgAndRemindActivity.isChecked = true
            if (switch.pushSystem == 1) system_switch_MsgAndRemindActivity.isChecked = true
            if (switch.pushInfo == 1) msg_switch_MsgAndRemindActivity.isChecked = true
        }
    }

    private fun checkEvent() {
        comment_switch_MsgAndRemindActivity.setOnCheckedChangeListener { view, checked ->
            val p = if (checked) 1 else 0
            personalService.putUserPersonalSetting(InfoRepository.user.id, pushComment = p)
        }
        like_switch_MsgAndRemindActivity.setOnCheckedChangeListener { view, checked ->
            val p = if (checked) 1 else 0
            personalService.putUserPersonalSetting(InfoRepository.user.id, pushLike = p)
        }
        fan_switch_MsgAndRemindActivity.setOnCheckedChangeListener { view, checked ->
            val p = if (checked) 1 else 0
            personalService.putUserPersonalSetting(InfoRepository.user.id, pushFans = p)
        }
        system_switch_MsgAndRemindActivity.setOnCheckedChangeListener { view, checked ->
            val p = if (checked) 1 else 0
            personalService.putUserPersonalSetting(InfoRepository.user.id, pushSystem = p)
        }
        msg_switch_MsgAndRemindActivity.setOnCheckedChangeListener { view, checked ->
            val p = if (checked) 1 else 0
            personalService.putUserPersonalSetting(InfoRepository.user.id, pushInfo = p)
        }
    }
}