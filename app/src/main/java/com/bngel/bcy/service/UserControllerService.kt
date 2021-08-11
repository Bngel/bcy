package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.postChangePassword.PostChangePassword
import com.bngel.bcy.bean.postOauthLoginBySms.PostOauthLoginBySms
import com.bngel.bcy.bean.postOauthLogout.PostOauthLogout
import com.bngel.bcy.dao.UserControllerDao.UserControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.web.WebRepository
import java.lang.Exception
import kotlin.concurrent.thread

class UserControllerService {

    private val userService = UserControllerDao.create()

    /***
     * msg:
     * codeWrong：验证码错误（不存在或者错误）
     * existWrong：账号不存在
     * success：成功
     */
    fun postChangePassword(
        code: String,
        newPassword: String,
        phone: String
    ): PostChangePassword? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!,"网络错误",Toast.LENGTH_SHORT)
            return null
        }
        val data = userService.postChangePassword(code, newPassword, phone)
        var msg = ""
        var res: PostChangePassword? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    res = body
                }
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }

    /**
     * msg:
     * codeWrong：验证码错误
     * success：成功（会给token）
     */
    fun postOauthLoginBySms(
        code: String,
        phone: String,
        type: Int = 2
    ): PostOauthLoginBySms? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!,"网络错误",Toast.LENGTH_SHORT)
            return null
        }
        val data = userService.postOauthLoginBySms(code, phone, type)
        var msg = ""
        var res: PostOauthLoginBySms? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    res = body
                }
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }

    /**
     * msg:
     * success：成功
     */
    fun postOauthLogout(id: String, type: Int = 2): PostOauthLogout? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!,"网络错误",Toast.LENGTH_SHORT)
            return null
        }
        val data = userService.postOauthLogout(id, type)
        var msg = ""
        var res: PostOauthLogout? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success") {
                    res = body
                }
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }


}