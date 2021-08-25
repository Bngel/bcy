package com.bngel.bcy.service

import android.util.Log
import android.widget.Toast
import com.bngel.bcy.bean.PersonalController.getUserCommunityInfo.GetUserCommunityInfo
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.getUserPersonalInfoByPhone.GetUserPersonalInfoByPhone
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.getUserPersonalInfoById.GetUserPersonalInfoById
import com.bngel.bcy.bean.PersonalController.getUserPersonalSetting.GetUserPersonalSetting
import com.bngel.bcy.bean.PersonalController.getUserUserCounts.GetUserUserCounts
import com.bngel.bcy.bean.PersonalController.postUserPhotoUpload.PostUserPhotoUpload
import com.bngel.bcy.bean.PersonalController.postUserSetPassword.PostUserSetPassword
import com.bngel.bcy.bean.PersonalController.putUserPersonalInfo.PutUserPersonalInfo
import com.bngel.bcy.bean.PersonalController.putUserPersonalSetting.PutUserPersonalSetting
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
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.getUserPersonalInfoByPhone(phone, InfoRepository.token)
            var msg = ""
            var res: GetUserPersonalInfoByPhone? = null
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
     * existWrong：账号不存在或已被冻结
     * success：成功 返回data personalInfo
     */
    fun getUserPersonalInfoById(id: String): GetUserPersonalInfoById? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.getUserPersonalInfoById(id, InfoRepository.token)
            var msg = ""
            var res: GetUserPersonalInfoById? = null
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
     * fileWrong：文件为空
     * typeWrong：上传格式错误
     * success：成功（就不返回url了，会自动替换头像）
     */
    fun postUserPhotoUpload(id: String, photo: MultipartBody.Part): PostUserPhotoUpload?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.postUserPhotoUpload(id, photo, InfoRepository.token)
            var msg = ""
            var res:PostUserPhotoUpload? = null
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
     * existWrong：账号不存在或已被冻结
     * success：成功 返回data personalInfo
     */
    fun getUserUserCounts(id: String): GetUserUserCounts? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.getUserUserCounts(listOf(id), InfoRepository.token)
            var msg = ""
            var res: GetUserUserCounts? = null
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
     * existWrong：账号不存在或已被冻结
     * success：成功
     */
    fun putUserPersonalInfo(id: String,  username: String?, sex: String?, province: String?, city: String?, birthday: String?, description: String?): PutUserPersonalInfo? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.putUserPersonalInfo(birthday, city, description, id, province, sex, username, InfoRepository.token)
            var msg = ""
            var res: PutUserPersonalInfo? = null
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
     * existWrong：用户不存在或已被冻结
     * success：成功
     * 返回data：personalSetting
     * （id：用户id pushComment：推送评论 pushLike：推送点赞
     * pushFans：推送粉丝 pushSystem：推送系统通知 pushInfo：推送聊天）
     */
    fun getUserPersonalSetting(id: String): GetUserPersonalSetting? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.getUserPersonalSetting(id, InfoRepository.token)
            var msg = ""
            var res: GetUserPersonalSetting? = null
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
     * existWrong：账号不存在或已被冻结
     * success：成功
     */
    fun putUserPersonalSetting(id: String, pushComment: Int? = null, pushFans: Int? = null, pushInfo: Int? = null,
                               pushLike: Int? = null, pushSystem: Int? = null): PutUserPersonalSetting? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.putUserPersonalSetting(id, pushComment, pushFans, pushInfo, pushLike, pushSystem, InfoRepository.token)
            var msg = ""
            var res: PutUserPersonalSetting? = null
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
     * existWrong：用户不存在
     * success：成功
     */
    fun postUserSetPassword(id: String, password: String): PostUserSetPassword? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.postUserSetPassword(id, password, InfoRepository.token)
            var msg = ""
            var res: PostUserSetPassword? = null
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
     * 返回data
     * userCommunityInfo（id：用户id phone：电话 weiboId：微博id）
     */
    fun getUserCommunityInfo(id: String): GetUserCommunityInfo? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = personalService.getUserCommunityInfo(id, InfoRepository.token)
            var msg = ""
            var res: GetUserCommunityInfo? = null
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