package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.getUserPersonalInfoByPhone.GetUserPersonalInfoByPhone
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.getUserPersonalInfoById.GetUserPersonalInfoById
import com.bngel.bcy.bean.PersonalController.getUserUserCounts.GetUserUserCounts
import com.bngel.bcy.bean.PersonalController.postUserPhotoUpload.PostUserPhotoUpload
import com.bngel.bcy.bean.PersonalController.putUserPersonalInfo.PutUserPersonalInfo
import com.bngel.bcy.dao.PersonalControllerDao.PersonalControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import okhttp3.MultipartBody
import kotlin.concurrent.thread

class PersonalControllerService {

    private val personalService = PersonalControllerDao.create()

    /**
     * msg:
     * existWrong：账号不存在或已被冻结
     * success：成功 返回data personalInfo
     */
    fun getUserPersonalInfoByPhone(phone: String): GetUserPersonalInfoByPhone? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = personalService.getUserPersonalInfoByPhone(phone, InfoRepository.token)
        var msg = ""
        var res: GetUserPersonalInfoByPhone? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                res = body
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }

    /**
     * msg:
     * existWrong：账号不存在或已被冻结
     * success：成功 返回data personalInfo
     */
    fun getUserPersonalInfoById(id: String): GetUserPersonalInfoById? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = personalService.getUserPersonalInfoById(id, InfoRepository.token)
        var msg = ""
        var res: GetUserPersonalInfoById? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                res = body
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }

    /**
     * msg:
     * fileWrong：文件为空
     * typeWrong：上传格式错误
     * success：成功（就不返回url了，会自动替换头像）
     */
    fun postUserPhotoUpload(id: String, photo: MultipartBody.Part): PostUserPhotoUpload?{
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = personalService.postUserPhotoUpload(id, photo, InfoRepository.token)
        var msg = ""
        var res:PostUserPhotoUpload? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                res = body
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }

    /**
     * msg:
     * existWrong：账号不存在或已被冻结
     * success：成功 返回data personalInfo
     */
    fun getUserUserCounts(id: String): GetUserUserCounts? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = personalService.getUserUserCounts(listOf(id), InfoRepository.token)
        var msg = ""
        var res: GetUserUserCounts? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                res = body
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }

    /**
     * msg:
     * existWrong：账号不存在或已被冻结
     * success：成功
     */
    fun putUserPersonalInfo(id: String,  username: String?, sex: String?, province: String?, city: String?, birthday: String?, description: String?): PutUserPersonalInfo? {
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = personalService.putUserPersonalInfo(birthday, city, description, id, province, sex, username, InfoRepository.token)
        var msg = ""
        var res: PutUserPersonalInfo? = null
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                res = body
            }.join(4000)
        } catch (e: Exception) {}
        return res
    }
}