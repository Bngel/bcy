package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.CosController.getAcgCosCountsList.GetAcgCosCountsList
import com.bngel.bcy.bean.MessageController.getCommunityAtList.GetCommunityAtList
import com.bngel.bcy.bean.MessageController.getCommunityCommentList.GetCommunityCommentList
import com.bngel.bcy.bean.MessageController.getCommunityLikeList.GetCommunityLikeList
import com.bngel.bcy.dao.MessageControllerDao.MessageControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import kotlin.concurrent.thread

class MessageControllerService {

    private val msgService = MessageControllerDao.create()

    /**
     * msg:
     * success：成功
     * 返回data
     * atList：
     * （id：用户id username：昵称 photo：头像 description：内容
     * type：@类型（用来跳转）number：目标的cos或问答编号 info：右边展示的原内容
     * isRead：是否已读（0：未读 1：已读） createTime：@时间）
     */
    fun getCommunityAtList(cnt: Int, id: String, page: Int): GetCommunityAtList?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = msgService.getCommunityAtList(cnt, id, page, InfoRepository.token)
            var msg = ""
            var res: GetCommunityAtList? = null
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
     * atList：
     * （id：用户id username：昵称 photo：头像 description：内容
     * type：@类型（用来跳转）number：目标的cos或问答编号 info：右边展示的原内容
     * isRead：是否已读（0：未读 1：已读） createTime：@时间）
     */
    fun getCommunityCommentList(cnt: Int, id: String, page: Int): GetCommunityCommentList?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = msgService.getCommunityCommentList(cnt, id, page, InfoRepository.token)
            var msg = ""
            var res: GetCommunityCommentList? = null
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
     * atList：
     * （id：用户id username：昵称 photo：头像 description：内容
     * type：@类型（用来跳转）number：目标的cos或问答编号 info：右边展示的原内容
     * isRead：是否已读（0：未读 1：已读） createTime：@时间）
     */
    fun getCommunityLikeList(cnt: Int, id: String, page: Int): GetCommunityLikeList?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = msgService.getCommunityLikeList(cnt, id, page, InfoRepository.token)
            var msg = ""
            var res: GetCommunityLikeList? = null
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