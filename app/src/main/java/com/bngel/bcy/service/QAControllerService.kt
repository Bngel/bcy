package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.CircleController.getAcgCircle.GetAcgCircle
import com.bngel.bcy.bean.QAController.deleteAcgDislikeAnswer.DeleteAcgDislikeAnswer
import com.bngel.bcy.bean.QAController.deleteAcgFollowQa.DeleteAcgFollowQa
import com.bngel.bcy.bean.QAController.getAcgAnswerCommentCommentList.GetAcgAnswerCommentCommentList
import com.bngel.bcy.bean.QAController.getAcgAnswerCommentList.GetAcgAnswerCommentList
import com.bngel.bcy.bean.QAController.getAcgAnswerList.GetAcgAnswerList
import com.bngel.bcy.bean.QAController.getAcgJudgeQa.GetAcgJudgeQa
import com.bngel.bcy.bean.QAController.getAcgJudgeQaAnswer.GetAcgJudgeQaAnswer
import com.bngel.bcy.bean.QAController.getAcgQaAnswerCountsList.GetAcgQaAnswerCountsList
import com.bngel.bcy.bean.QAController.getAcgQaTopic.GetAcgQaTopic
import com.bngel.bcy.bean.QAController.getEsRecommendQa.GetEsRecommendQa
import com.bngel.bcy.bean.QAController.postAcgFollowQa.PostAcgFollowQa
import com.bngel.bcy.bean.QAController.postAcgLikeAnswer.PostAcgLikeAnswer
import com.bngel.bcy.bean.QAController.postAcgQa.PostAcgQa
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

    /**
     * msg:
     * success：成功
     * 返回qaAnswerCountsList：
     * （number：问答回答编号
     * likeCounts：点赞数
     * commentCounts：评论数）
     */
    fun getAcgQaAnswerCountsList(number: String, id: String?): GetAcgQaAnswerCountsList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgQaAnswerCountsList(id, listOf(number), if (id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgQaAnswerCountsList? = null
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
     * 返回qaAnswerCountsList：
     * （number：问答回答编号
     * likeCounts：点赞数
     * commentCounts：评论数）
     */
    fun getAcgQaAnswerCountsList(numbers: List<String>, id: String?): GetAcgQaAnswerCountsList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgQaAnswerCountsList(id, numbers, if (id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgQaAnswerCountsList? = null
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
     * success：成功
     * 返回qaJudgeList：
     * （number：问答编号 isFollow：1关注 0未关注）
     */
    fun getAcgJudgeQa(id: String, number: String): GetAcgJudgeQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgJudgeQa(id, listOf(number), InfoRepository.token)
            var msg = ""
            var res: GetAcgJudgeQa? = null
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
     * success：成功
     * 返回qaJudgeList：
     * （number：问答编号 isFollow：1关注 0未关注）
     */
    fun getAcgJudgeQa(id: String, numbers: List<String>): GetAcgJudgeQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgJudgeQa(id, numbers, InfoRepository.token)
            var msg = ""
            var res: GetAcgJudgeQa? = null
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
     * repeatWrong：已经关注了
     * existWrong：圈子不存在
     * success：成功
     */
    fun postAcgFollowQa(id: String, number: String): PostAcgFollowQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.postAcgFollowQA(id, number, InfoRepository.token)
            var msg = ""
            var res: PostAcgFollowQa? = null
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
     * repeatWrong：已经取消关注了
     * existWrong：圈子不存在
     * success：成功
     */
    fun deleteAcgFollowQa(id: String, number: String): DeleteAcgFollowQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.deleteAcgFollowQA(id, number, InfoRepository.token)
            var msg = ""
            var res: DeleteAcgFollowQa? = null
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
     * dirtyWrong：问答标题或内容有敏感内容（会推送）
     * repeatWrong：24小时内超过15次
     * success：成功
     */
    fun postAcgQa(description: String, id: String, label: List<String>, photo: List<String>, title: String): PostAcgQa? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.postAcgQA(description, id, label, photo, title, InfoRepository.token)
            var msg = ""
            var res: PostAcgQa? = null
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
     * 返回answerCommentList
     * （number：评论编号 id:：评论者id username：评论者昵称 photo：评论者头像 description：评论内容 createTime：评论时间）
     */
    fun getAcgAnswerCommentList(answerNumber: String, cnt: Int, id: String?, page: Int, type: Int): GetAcgAnswerCommentList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgAnswerCommentList(answerNumber, cnt, id, page, type, if (id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgAnswerCommentList? = null
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
     * 返回answerCommentList
     * （number：评论编号 id:：评论者id username：评论者昵称 photo：评论者头像 description：评论内容 createTime：评论时间）
     */
    fun getAcgAnswerCommentCommentList(answerNumber: String, cnt: Int, id: String?, page: Int, type: Int): GetAcgAnswerCommentCommentList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgAnswerCommentCommentList(cnt, id, answerNumber, page, type, if (id != null) InfoRepository.token else null)
            var msg = ""
            var res: GetAcgAnswerCommentCommentList? = null
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
    * success：成功 返回qaAnswerLikeList：（number：回答编号 isLike：1：已点赞 0未点赞）
    */
    fun getAcgJudgeQaAnswer(id: String, numbers: List<String>): GetAcgJudgeQaAnswer? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgJudgeQaAnswer(id, numbers, InfoRepository.token)
            var msg = ""
            var res: GetAcgJudgeQaAnswer? = null
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
     * success：成功 返回qaAnswerLikeList：（number：回答编号 isLike：1：已点赞 0未点赞）
     */
    fun getAcgJudgeQaAnswer(id: String, number: String): GetAcgJudgeQaAnswer? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.getAcgJudgeQaAnswer(id, listOf(number), InfoRepository.token)
            var msg = ""
            var res: GetAcgJudgeQaAnswer? = null
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
     * repeatWrong：已经点赞了 existWrong：回答不存在 success：成功
     */
    fun postAcgLikeAnswer(id: String, number: String): PostAcgLikeAnswer? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.postAcgLikeAnswer(id, number, InfoRepository.token)
            var msg = ""
            var res: PostAcgLikeAnswer? = null
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
     * repeatWrong：已经取消了 existWrong：回答不存在 success：成功
     */
    fun deleteAcgLikeAnswer(id: String, number: String): DeleteAcgDislikeAnswer? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = qaService.deleteAcgDislikeAnswer(id, number, InfoRepository.token)
            var msg = ""
            var res: DeleteAcgDislikeAnswer? = null
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