package com.bngel.bcy.service

import android.util.Log
import android.widget.Toast
import com.bngel.bcy.bean.CircleController.getEsRecommendCircle.GetEsRecommendCircle
import com.bngel.bcy.bean.CircleController.postAcgCircle.PostAcgCircle
import com.bngel.bcy.bean.CircleController.postAcgCirclePhoto.PostAcgCirclePhoto
import com.bngel.bcy.bean.CosController.getAcgFollowCos.GetAcgFollowCos
import com.bngel.bcy.bean.UserController.postOauthToken.PostOauthToken
import com.bngel.bcy.dao.CircleController.CircleControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import kotlin.concurrent.thread

class CircleControllerService {

    private val circleService = CircleControllerDao.create()

    /**
     * msg:
     * success：成功
     * 返回recommendCircleList
     * （circleName：圈子名 description：描述 photo：图片 nickName：昵称
     * postCounts：发布数 followCounts：关注数 createTime：创建时间）
     */
    fun getEsRecommendCircle(cnt: Int, page: Int, id: String = "0"): GetEsRecommendCircle?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.getEsRecommendCircle(cnt, id, page, if(id != "0") InfoRepository.token else null)
            var msg = ""
            var res: GetEsRecommendCircle? = null
            thread {
                val exec = data.execute()
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        }
        catch (e: Exception) {
            return null
        }
    }

    /**
     * msg:
     * fileWrong：文件为空
     * typeWrong：上传格式错误
     * success：成功
     * 成功后返回json：url（图片url）
     */
    fun postAcgCirclePhoto(id: String, photo: MultipartBody.Part): PostAcgCirclePhoto? {
        val gson = GsonBuilder()
            .registerTypeAdapter(PostOauthToken::class.java, PostAcgCirclePhoto.DataStateDeserializer())
            .create()
        val photoService = CircleControllerDao.create(gson)
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = photoService.postAcgCirclePhoto(id, photo, InfoRepository.token)
            var msg = ""
            var res: PostAcgCirclePhoto? = null
            thread {
                val exec = data.execute()
                Log.d("TestLog", exec.toString())
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        }
        catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * msg:
     * repeatWrong：圈子已存在
     * success：成功
     */
    fun postAcgCircle(circleName: String, description: String, id: String, nickName: String, photo: String): PostAcgCircle? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.postAcgCircle(circleName, description, id, nickName, photo, InfoRepository.token)
            var msg = ""
            var res: PostAcgCircle? = null
            thread {
                val exec = data.execute()
                if (exec != null) {
                    val body = exec.body()
                    msg = body?.msg!!
                    res = body
                }
            }.join(4000)
            return res
        }
        catch (e: Exception) {
            return null
        }
    }
}