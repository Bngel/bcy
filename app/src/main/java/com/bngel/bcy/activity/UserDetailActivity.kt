package com.bngel.bcy.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.FansControllerService
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.activity_edit_user_info.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.text.SimpleDateFormat
import java.util.*

class UserDetailActivity : BaseActivity() {

    private val personalService = PersonalControllerService()
    private val fansService = FansControllerService()
    private var editLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            headCardEvent()
        }
    }

    private fun initWidget() {
        headCardEvent()
        followBtnEvent()
        editInfoEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_UserDetailActivity.setOnClickListener {
            finish()
        }
    }

    private fun editInfoEvent() {
        val toId = intent.getStringExtra("id")?:""
        val fromId = InfoRepository.user.id
        if (toId == fromId) {
            editInfo_UserDetailActivity.visibility = View.VISIBLE
            editInfo_UserDetailActivity.setOnClickListener {
                val intent = Intent(this, EditUserInfoActivity::class.java)
                editLauncher?.launch(intent)
            }
        }
    }

    private fun followBtnEvent() {
        val id = intent.getStringExtra("id")?:""
        val postUserJudgeFollow = fansService.postUserJudgeFollow(InfoRepository.user.id, id)
        var followStatus = "0"
        if (postUserJudgeFollow != null) {
            followStatus = postUserJudgeFollow.data.status?:"0"
        }
        follow_btn_UserDetailActivity.text = when (followStatus) {
            "0" -> "关注"
            "1" -> "已关注"
            "2" -> "已互粉"
            else -> "关注"
        }
        follow_btn_UserDetailActivity.setOnClickListener {
            if (id == InfoRepository.user.id)
                Toast.makeText(this, "无法关注自己", Toast.LENGTH_SHORT).show()
            else {
                val fromId = InfoRepository.user.id
                if (followStatus == "0" || followStatus == "3") {
                    val postUserFollow = fansService.postUserFollow(fromId, id)
                    if (postUserFollow != null) {
                        if (postUserFollow.msg == "success") {
                            follow_btn_UserDetailActivity.text = when (followStatus) {
                                "0" -> "已关注"
                                "3" -> "已互粉"
                                else -> "已关注"
                            }
                            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "关注失败", Toast.LENGTH_SHORT).show()
                    }
                } else if (followStatus == "1" || followStatus == "2") {
                    val deleteUserFollow = fansService.deleteUserFollow(fromId, id)
                    if (deleteUserFollow != null) {
                        if (deleteUserFollow.msg == "success") {
                            follow_btn_UserDetailActivity.text = "关注"
                            Toast.makeText(this, "取消关注成功", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "取消关注失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun headCardEvent() {
        val id = intent.getStringExtra("id")?:""
        if (id != "") {
            val user = personalService.getUserPersonalInfoById(id)?.data!!.personalInfo
            val userCounts = personalService.getUserUserCounts(id)?.data!!.userCountsList[0]
            if (user.photo != null)
                avt_UserDetailActivity.setAvt(user.photo)
            username_UserDetailActivity.text = user.username?:"用户名获取失败"
            description_UserDetailActivity.text = user.description?:""
            if (user.sex == "男")
                sex_UserDetailActivity.setImageResource(R.drawable.nan_user_detail_activity_92caff)
            else
                sex_UserDetailActivity.setImageResource(R.drawable.nv_user_detail_activity_e87b9c)
            if (user.birthday != null) {
                val pattern = "yyyy-MM-dd'T'HH:mm:ss"
                val dfParse = SimpleDateFormat(pattern, Locale.ENGLISH)
                dfParse.timeZone = TimeZone.getTimeZone("UTC")
                val df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                df.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
                val dateTime = dfParse.parse(user.birthday)
                val birthday = df.format(dateTime)
                birthday_UserDetailActivity.text = birthday
            }
            else {
                birthday_UserDetailActivity.text = ""
            }
            address_UserDetailActivity.text = "${(user.province?:"")}${(user.city?:"")}"
            follow_count_text_UserDetailActivity.text = userCounts.followCounts.toString() + " 关注"
            fan_count_text_UserDetailActivity.text = userCounts.fansCounts.toString() + " 粉丝"
        }
    }
}