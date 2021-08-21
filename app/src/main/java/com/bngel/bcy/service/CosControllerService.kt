package com.bngel.bcy.service

import android.util.Log
import android.widget.Toast
import com.bngel.bcy.bean.CosController.getAcgCos.GetAcgCos
import com.bngel.bcy.bean.CosController.getAcgCosComment.GetAcgCosComment
import com.bngel.bcy.bean.CosController.getAcgCosCommentComment.GetAcgCosCommentComment
import com.bngel.bcy.bean.CosController.getAcgCosCountsList.GetAcgCosCountsList
import com.bngel.bcy.bean.CosController.getAcgFollowCos.GetAcgFollowCos
import com.bngel.bcy.bean.CosController.getEsRecommendCos.GetEsRecommendCos
import com.bngel.bcy.bean.FansController.getUserFansList.GetUserFansList
import com.bngel.bcy.dao.CosControllerDao.CosControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class CosControllerService {

    private val cosService = CosControllerDao.create()

    /**
     * msg:
     * success：成功
     * 返回data
     * cosFollowList
     * （number：cos编号 id：用户id username：昵称 photo：用户头像 cosPhoto：cos图片（list） label：标签（list） createTime：发布时间）
     */
    fun getAcgFollowCos(id: String, cnt: Int, page: Int): GetAcgFollowCos?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = cosService.getAcgFollowCos(id, cnt, page, InfoRepository.token)
            var msg = ""
            var res: GetAcgFollowCos? = null
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
     * 返回data
     * cosCountsList
     * （number：cos编号 commentCounts：评论数 likeCounts：点赞数 favorCounts：收藏数 shareCounts：分享数)
     */
    fun getAcgCosCountsList(id: String?, number: String): GetAcgCosCountsList?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = cosService.getAcgCosCountsList(id, number, if(id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgCosCountsList? = null
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
     * existWrong：cos不存在
     * success：成功
     * 返回data
     * cos（number：cos编号 id：用户id username：发布者昵称 photo：发布者头像
     * fansCounts：粉丝数description：内容 cosPhoto：图片（list） label：标签（list） createTime：发布时间 ）
     */
    fun getAcgCos(id: String?, number: String): GetAcgCos?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = cosService.getAcgCos(id, number, if(id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgCos? = null
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
     * existWrong：cos不存在
     * success：成功
     * cosCommentList
     * （number：评论编号 id：用户id username：用户昵称 photo：头像 description：评论内容 createTime：评论时间）
     */
    fun getAcgCosComment(id: String?, number: String, cnt: Int, page: Int, type:Int): GetAcgCosComment?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = cosService.getAcgCosComment(id, number, cnt, page, type, if(id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgCosComment? = null
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
     * commentCommentList：
     * （number：评论编号 fromId:：评论者id fromUsername：评论者昵称 fromPhoto：评论者头像
     * description：评论内容 toId：被回复者id（没有回复别人为空） toUsername：被回复者昵称 createTime：评论时间）
     */
    fun getAcgCosCommentComment(id: String?, number: String, cnt: Int, page: Int, type:Int): GetAcgCosCommentComment?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = cosService.getAcgCosCommentComment(id, cnt, number,page, type, if(id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgCosCommentComment? = null
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
     * （这里没有pages和counts参数 直接获取就行）
     * cosList（参数和cos搜索接口那里的一样）
     */
    fun getEsRecommendCos(cnt: Int, id: String = "0"): GetEsRecommendCos?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = cosService.getEsRecommendCos(cnt, id, if(id != "0") InfoRepository.token else null)
            var msg = ""
            var res: GetEsRecommendCos? = null
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