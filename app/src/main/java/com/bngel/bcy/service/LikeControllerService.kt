package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.CosController.getAcgFollowCos.GetAcgFollowCos
import com.bngel.bcy.bean.LikeController.deleteAcgLikeCos.DeleteAcgLikeCos
import com.bngel.bcy.bean.LikeController.getAcgJudgeLikes.GetAcgJudgeLikes
import com.bngel.bcy.bean.LikeController.getAcgLikeList.GetAcgLikeList
import com.bngel.bcy.bean.LikeController.postAcgLikeCos.PostAcgLikeCos
import com.bngel.bcy.dao.LikeControllerDao.LikeControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import retrofit2.Call
import retrofit2.http.Query
import kotlin.concurrent.thread

class LikeControllerService {

    private val likeService = LikeControllerDao.create()

    /**
     * success：成功
     * judgeLikeList
     * （number：编号 status 0：未点赞 1：已点赞）
     */
    fun getAcgJudgeLikes(id: String, numbers: List<String>): GetAcgJudgeLikes? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        val data = likeService.getAcgJudgeLikes(id, numbers, InfoRepository.token)
        var msg = ""
        var res: GetAcgJudgeLikes? = null
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
     * existWrong：cos不存在
     * repeatWrong：重复喜欢
     * success：成功
     */
    fun postAcgLikeCos(id: String, number: String): PostAcgLikeCos? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        val data = likeService.postAcgLikeCos(id, number, InfoRepository.token)
        var msg = ""
        var res: PostAcgLikeCos? = null
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
     * existWrong：cos不存在
     * repeatWrong：未喜欢
     * success：成功
     */
    fun deleteAcgLikeCos(id: String, number: String): DeleteAcgLikeCos? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        val data = likeService.deleteAcgLikeCos(id, number, InfoRepository.token)
        var msg = ""
        var res: DeleteAcgLikeCos? = null
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
     * success：成功
     * 返回data
     * likeCosList
     * （number：cos编号 id：发布用户id username：发布用户昵称
     * photo：发布用户头像 description：内容
     * cosPhoto：照片列表（list） createTime：发布时间）
     */
    fun getAcgLikeList(id: String, cnt: Int, page: Int): GetAcgLikeList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        val data = likeService.getAcgLikeList(id, cnt, page, InfoRepository.token)
        var msg = ""
        var res: GetAcgLikeList? = null
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