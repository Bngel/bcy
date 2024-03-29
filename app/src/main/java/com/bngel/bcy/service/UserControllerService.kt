package com.bngel.bcy.service

import android.util.Log
import android.widget.Toast
import com.bngel.bcy.bean.UserController.getCommunitySearchUser.GetCommunitySearchUser
import com.bngel.bcy.bean.UserController.postOauthChangePassword.PostOauthChangePassword
import com.bngel.bcy.bean.UserController.postOauthToken.PostOauthToken
import com.bngel.bcy.bean.UserController.postOauthLoginBySms.PostOauthLoginBySms
import com.bngel.bcy.bean.UserController.postOauthLogout.PostOauthLogout
import com.bngel.bcy.dao.UserControllerDao.UserControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import com.google.gson.GsonBuilder
import kotlin.concurrent.thread


class UserControllerService {

    private val userService = UserControllerDao.create()

    /***
     * msg:
     * codeWrong：验证码错误（不存在或者错误）
     * existWrong：账号不存在
     * success：成功
     */
    fun postOauthChangePassword(
        code: String,
        newPassword: String,
        phone: String
    ): PostOauthChangePassword? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = userService.postChangePassword(code, newPassword, phone)
            var msg = ""
            var res: PostOauthChangePassword? = null
            thread {
                val exec = data.execute()
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        } catch (e: Exception) {
            return null
        }
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
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = userService.postOauthLoginBySms(code, phone, type)
            var msg = ""
            var res: PostOauthLoginBySms? = null
            thread {
                val exec = data.execute()
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * msg:
     * success：成功
     */
    fun postOauthLogout(id: String, type: Int = 2): PostOauthLogout? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = userService.postOauthLogout(id, type)
            var msg = ""
            var res: PostOauthLogout? = null
            thread {
                val exec = data.execute()
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        } catch (e: Exception) {
            return null
        }
    }

    /***
     * msg:
     * 错误返回: error
     * 正常返回: access_token
     */
    fun postOauthToken(
        username: String, password: String,
        grant_type: String = "password",
        client_id: String = "bcy-cloud-gateway",
        client_secret: String = "123456"
    ): PostOauthToken? {
        val gson = GsonBuilder()
            .registerTypeAdapter(PostOauthToken::class.java, PostOauthToken.DataStateDeserializer())
            .create()
        val oauthService = UserControllerDao.create(gson)
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = oauthService.postOauthToken(grant_type, username, password, client_id, client_secret)
            var res: PostOauthToken? = null
            try {
                thread {
                    val exec = data.execute()
                    if (exec.code() == 200) {
                        res = exec.body()
                    }
                }.join(4000)
            } catch (e: Exception) {}
            return res
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * msg:
     * success：成功
     */
    fun getCommunitySearchUser(cnt: Int, id: String, keyword: String ,page: Int): GetCommunitySearchUser? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = userService.getCommunitySearchUser(cnt, id, keyword, page, if (id != "0") InfoRepository.token else null)
            var msg = ""
            var res: GetCommunitySearchUser? = null
            thread {
                val exec = data.execute()
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


}