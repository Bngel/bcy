package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.CircleController.getAcgCircle.GetAcgCircle
import com.bngel.bcy.bean.QAController.getAcgAnswerList.GetAcgAnswerList
import com.bngel.bcy.bean.QAController.getAcgQaTopic.GetAcgQaTopic
import com.bngel.bcy.bean.QAController.getEsRecommendQa.GetEsRecommendQa
import com.bngel.bcy.bean.QAController.postEsSearchQa.PostEsSearchQa
import com.bngel.bcy.dao.QAControllerDao.QAControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import kotlin.concurrent.thread

class QAControllerService {

    private val qaService = QAControllerDao.create()

    /**
     * msg:
     * success：成功
     * 返回qaRecommendList
     * （number：问答编号 id：发布用户id username：发布用户昵称 photo：头像
     * description：内容 title：标题 label：标签（字符串list）
     * createTime：创建时间）
     */
    fun getEsRecommendQa(cnt: Int, id: String = "0"): GetEsRecommendQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getEsRecommendQa(cnt, id, if (id == "0") null else InfoRepository.token)
            var msg = ""
            var res: GetEsRecommendQa? = null
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
     * existWrong：问答不存在
     * success：成功
     * 返回data
     * QATopic
     * （number：问答编号 title：题目 description：问题内容 followCounts：关注人数
     * answerCounts：回答数 photo：图片（列表） label：标签（列表））
     */
    fun getAcgQaTopic(number: String, id: String?): GetAcgQaTopic? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgQaTopic(id, number, if (id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgQaTopic? = null
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
     * 返回data answerList
     * （number：评论编号 id：用户id username：昵称 description：主要内容photo：用户头像
     * answerPhoto：回答的图片（list） createTime：回答时间）
     */
    fun getAcgAnswerList(cnt: Int, number: String, id: String?, page: Int, type: Int): GetAcgAnswerList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgAnswerList(cnt, id, number, page, type, if (id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgAnswerList? = null
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
     * 返回qaList
     * （number：问答编号 id：发布用户id username：发布用户昵称 photo：头像
     * description：内容 title：标题 label：标签（字符串list） createTime：创建时间）
     */
    fun postEsSearchQa(cnt: Int, keyword: String, page: Int, id: String?): PostEsSearchQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.postEsSearchQa(cnt, id, keyword, page, if (id == null||id=="0") null else InfoRepository.token)
            var msg = ""
            var res: PostEsSearchQa? = null
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