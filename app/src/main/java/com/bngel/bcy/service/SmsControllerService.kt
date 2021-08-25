package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.SmsController.postOauthCode.PostOauthCode
import com.bngel.bcy.bean.SmsController.postUserChangePhone.PostUserChangePhone
import com.bngel.bcy.dao.SmsControllerDao.SmsControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import java.lang.Exception
import kotlin.concurrent.thread

class SmsControllerService {

    private val smsService = SmsControllerDao.create()

    /***
     * msg:
     * repeatWrong：获取验证码次数过多24小时超5次
     * existWrong：手机号不存在（验证码发送错误）
     * success：成功
     */
    fun postOauthCode(
        phone: String,
        type: Int
    ): PostOauthCode? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = smsService.postOauthCode(phone, type)
            var msg = ""
            var res: PostOauthCode? = null
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
     * codeWrong：验证码错误
     * success：成功
     */
    fun postUserChangePhone(
        code: String,
        id: String,
        phone: String
    ): PostUserChangePhone? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = smsService.postUserChangePhone(code,id,phone, InfoRepository.token)
            var msg = ""
            var res: PostUserChangePhone? = null
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
}