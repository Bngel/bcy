package com.bngel.bcy.service

import android.util.Log
import android.widget.Toast
import com.bngel.bcy.bean.CosController.getAcgCosCountsList.GetAcgCosCountsList
import com.bngel.bcy.bean.CosController.getAcgFollowCos.GetAcgFollowCos
import com.bngel.bcy.bean.FansController.getUserFansList.GetUserFansList
import com.bngel.bcy.dao.CosControllerDao.CosControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
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
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = cosService.getAcgFollowCos(id, cnt, page, InfoRepository.token)
        var msg = ""
        var res: GetAcgFollowCos? = null
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
     * success：成功
     * 返回data
     * cosCountsList
     * （number：cos编号 commentCounts：评论数 likeCounts：点赞数 favorCounts：收藏数 shareCounts：分享数)
     */
    fun getAcgCosCountsList(id: String?, number: String): GetAcgCosCountsList?{
        if (!WebRepository.isNetworkConnected()) {
            Toast.makeText(ActivityCollector.curActivity!!, "网络错误", Toast.LENGTH_SHORT).show()
            return null
        }
        val data = cosService.getAcgCosCountsList(id, number, if(id != null) InfoRepository.token else null)
        var msg = ""
        var res: GetAcgCosCountsList? = null
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