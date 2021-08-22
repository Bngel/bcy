package com.bngel.bcy.service

import android.util.Log
import android.widget.Toast
import com.bngel.bcy.bean.CircleController.deleteAcgFollowCircle.DeleteAcgFollowCircle
import com.bngel.bcy.bean.CircleController.getAcgCircle.GetAcgCircle
import com.bngel.bcy.bean.CircleController.getAcgCircleCosList.GetAcgCircleCosList
import com.bngel.bcy.bean.CircleController.getAcgJudgeCircle.GetAcgJudgeCircle
import com.bngel.bcy.bean.CircleController.getEsRecommendCircle.GetEsRecommendCircle
import com.bngel.bcy.bean.CircleController.postAcgCircle.PostAcgCircle
import com.bngel.bcy.bean.CircleController.postAcgCirclePhoto.PostAcgCirclePhoto
import com.bngel.bcy.bean.CircleController.postAcgFollowCircle.PostAcgFollowCircle
import com.bngel.bcy.bean.UserController.postOauthToken.PostOauthToken
import com.bngel.bcy.dao.CircleControllerDao.CircleControllerDao
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

    /**
     * msg:
     * success：成功
     * judgeCircleList
     * （circleName：圈子名 isFollow：1关注 0未关注）
     */
    fun getAcgJudgeCircle(circleName: String, id: String): GetAcgJudgeCircle? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.getAcgJudgeCircle(listOf(circleName), id, InfoRepository.token)
            var msg = ""
            var res: GetAcgJudgeCircle? = null
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
     * success：成功
     * judgeCircleList
     * （circleName：圈子名 isFollow：1关注 0未关注）
     */
    fun getAcgJudgeCircle(circleName: List<String>, id: String): GetAcgJudgeCircle? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.getAcgJudgeCircle(circleName, id, InfoRepository.token)
            var msg = ""
            var res: GetAcgJudgeCircle? = null
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
     * repeatWrong：用户已关注
     * success：成功
     */
    fun postAcgFollowCircle(circleName: String, id: String): PostAcgFollowCircle? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.postAcgFollowCircle(circleName, id, InfoRepository.token)
            var msg = ""
            var res: PostAcgFollowCircle? = null
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
     * repeatWrong：用户已关注
     * success：成功
     */
    fun deleteAcgFollowCircle(circleName: String, id: String): DeleteAcgFollowCircle? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.deleteAcgFollowCircle(circleName, id, InfoRepository.token)
            var msg = ""
            var res: DeleteAcgFollowCircle? = null
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
     * existWrong：圈子不存在
     * success：成功
     * 返回data
     * circleInfo：
     * （circleName：圈子名 description：圈子简介 photo：圈子图片url nickName：成员昵称
     * postCounts：圈子发帖数 followCounts：圈子成员数 createTime：创建时间）
     */
    fun getAcgCircle(circleName: String): GetAcgCircle? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.getAcgCircle(circleName)
            var msg = ""
            var res: GetAcgCircle? = null
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
     * 返回data
     * circleCosList
     * （number：cos编号 id：发布者id username：昵称 photo：头像
     * description：描述内容 cosPhoto：图片列表（list） createTime：发布时间
     */
    fun getAcgCircleCosList(circleName: String, cnt: Int, page: Int, type: Int): GetAcgCircleCosList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = circleService.getAcgCircleCosList(circleName, cnt, page, type)
            var msg = ""
            var res: GetAcgCircleCosList? = null
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