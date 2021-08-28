package com.bngel.bcy.utils

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.PersonalInfo
import com.bngel.bcy.bean.PersonalController.getUserUserCounts.UserCounts
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.web.WebRepository
import org.java_websocket.enums.ReadyState

object InfoRepository {

    val personalService = PersonalControllerService()

    private const val LOCAL_STATUS = "localStatus"
    private const val KEY_STATUS = "userStatus"
    private const val KEY_ID = "userId"
    private const val KEY_TOKEN = "userToken"

    var user = PersonalInfo(null,null,null,"",null,null,null,null)
    var userCounts = UserCounts(0,0,"",0)
    var token = ""

    private fun initUser(context: Context, id: String) {
        val userPersonalInfoById = personalService.getUserPersonalInfoById(id)
        if (userPersonalInfoById != null && userPersonalInfoById.msg == "success") {
            user = userPersonalInfoById.data.personalInfo
            ConstantRepository.loginStatus = true
        }
        else {
            quitStatus(context)
        }
        val userUserCounts = personalService.getUserUserCounts(user.id)
        if (userUserCounts!= null && userUserCounts.msg == "success") {
            if (userUserCounts.data.userCountsList.count() > 0) {
                userCounts = userUserCounts.data.userCountsList[0]
            }
        }
        else {
            quitStatus(context)
        }
    }

    fun initStatus(context: Context){
        val localStatus = context.getSharedPreferences(LOCAL_STATUS, Context.MODE_PRIVATE)
        val status = localStatus.getBoolean(KEY_STATUS, false)
        if (status) {
            // 用户处于登录状态 可以直接 getUser
            val id = localStatus.getString(KEY_ID, "")?:""
            val localToken = localStatus.getString(KEY_TOKEN, "")?:""
            token = localToken
            initUser(context, id)
            WebRepository.createWebClient(user.id)
            while (WebRepository.webClient.readyState != ReadyState.OPEN)
                Log.d("TestLog", "连接中")
        }
    }

    fun quitStatus(context: Context) {
        context.getSharedPreferences(LOCAL_STATUS, 0).edit{
            putBoolean(KEY_STATUS, false)
            putString(KEY_ID, "")
            putString(KEY_TOKEN, "")
        }
        user = PersonalInfo(null,null,null,"",null,null,null,null)
        ConstantRepository.loginStatus = false
    }

    fun initLogin(context: Context, id: String) {
        initUser(context, id)
        context.getSharedPreferences(LOCAL_STATUS, 0).edit {
            putBoolean(KEY_STATUS, true)
            putString(KEY_ID, id)
            putString(KEY_TOKEN, token)
        }
        ConstantRepository.loginStatus = true
    }
}