package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.SmsController.postOauthCode.PostOauthCode
import com.bngel.bcy.dao.SmsControllerDao.SmsControllerDao
import com.bngel.bcy.utils.ActivityCollector
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
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!,"网络错误", Toast.LENGTH_SHORT)
            return null
        }
        val data = smsService.postOauthCode(phone, type)
        var msg = ""
        var res: PostOauthCode? = null
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