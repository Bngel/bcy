package com.bngel.bcy.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.adapter.SearchViewPagerFragmentStateAdapter
import com.bngel.bcy.adapter.UserDetailViewPagerFragmentStateAdapter
import com.bngel.bcy.service.FansControllerService
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.utils.MyUtils
import kotlinx.android.synthetic.main.activity_edit_user_info.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.text.SimpleDateFormat
import java.util.*

class UserDetailActivity : BaseActivity() {

    private val personalService = PersonalControllerService()
    private val fansService = FansControllerService()
    private var editLauncher: ActivityResultLauncher<Intent>? = null
    private var curPage = 0

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
        tabEvent()
        vpEvent()
    }

    private fun closeEvent() {
        close_btn_UserDetailActivity.setOnClickListener {
            finish()
        }
    }

    private fun editInfoEvent() {
        val toId = intent.getStringExtra("id") ?: ""
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

        val id = intent.getStringExtra("id") ?: ""
        var followStatus = "0"
        if (id != "") {
            val postUserJudgeFollow =
                fansService.postUserJudgeFollow(InfoRepository.user.id, id)
            if (postUserJudgeFollow != null) {
                followStatus = postUserJudgeFollow.data.status ?: "0"
            }
            follow_btn_UserDetailActivity.text = when (followStatus) {
                "0" -> "关注"
                "1" -> "已关注"
                "2" -> "已互粉"
                else -> "关注"
            }
        }
        follow_btn_UserDetailActivity.setOnClickListener {
            if (ConstantRepository.loginStatus) {
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
                                followStatus = when (followStatus) {
                                    "0" -> "1"
                                    "3" -> "2"
                                    else -> followStatus
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
                                followStatus = when (followStatus) {
                                    "1" -> "0"
                                    "2" -> "3"
                                    else -> followStatus
                                }
                                Toast.makeText(this, "取消关注成功", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "取消关注失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
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
                val birthday = MyUtils.fromUtcToCst(user.birthday)
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

    private fun vpEvent(){
        val adapter = UserDetailViewPagerFragmentStateAdapter(this, 2)
        viewpager_UserDetailActivity.adapter = adapter
        viewpager_UserDetailActivity.isUserInputEnabled = false
    }

    private fun tabEvent(){
        create_switch_UserDetailActivity.setOnClickListener {
            curPage = 0
            create_switch_UserDetailActivity.setTextColor(Color.parseColor("#DFC048"))
            like_switch_UserDetailActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_UserDetailActivity.currentItem = curPage
        }
        like_switch_UserDetailActivity.setOnClickListener {
            curPage = 1
            create_switch_UserDetailActivity.setTextColor(Color.parseColor("#101010"))
            like_switch_UserDetailActivity.setTextColor(Color.parseColor("#DFC048"))
            viewpager_UserDetailActivity.currentItem = curPage
        }
    }
}