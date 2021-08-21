package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.FansController.deleteUserFollow.DeleteUserFollow
import com.bngel.bcy.bean.FansController.getUserFansList.GetUserFansList
import com.bngel.bcy.bean.FansController.getUserFollowList.GetUserFollowList
import com.bngel.bcy.bean.FansController.postUserFollow.PostUserFollow
import com.bngel.bcy.bean.FansController.postUserJudgeFollow.PostUserJudgeFollow
import com.bngel.bcy.bean.UserController.postOauthLoginBySms.PostOauthLoginBySms
import com.bngel.bcy.dao.FansControllerDao.FansControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import com.google.gson.GsonBuilder
import kotlin.concurrent.thread

class FansControllerService {

    private val fansService = FansControllerDao.create()

    /**
     * msg:
     * 返回data中
     * status：0未关注 1已关注 2已相互关注 3被关注（用来判断关注后是互粉还是单纯关注）
     */
    fun postUserJudgeFollow(fromId: String, toId: String): PostUserJudgeFollow? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = fansService.postUserJudgeFollow(fromId, toId, InfoRepository.token)
            var msg = ""
            var res: PostUserJudgeFollow? = null
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
     * repeatWrong：已成功关注（重复请求）
     * success：成功
     */
    fun postUserFollow(fromId: String, toId: String): PostUserFollow? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = fansService.postUserFollow(fromId, toId, InfoRepository.token)
            var msg = ""
            var res: PostUserFollow? = null
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
     * repeatWrong：已成功取消关注（重复请求）
     * success：成功
     */
    fun deleteUserFollow(fromId: String, toId: String): DeleteUserFollow? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = fansService.deleteUserFollow(fromId, toId, InfoRepository.token)
            var msg = ""
            var res: DeleteUserFollow? = null
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
     * fansList：粉丝列表
     * （id：用户id sex：性别 username：昵称 photo：头像url fansCounts：粉丝数）
     * counts：数据总量
     * pages：页面总数
     */
    fun getUserFansList(cnt: Int, id: String, page: Int, keyword: String = ""): GetUserFansList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = fansService.getUserFansList(cnt, id, page, InfoRepository.token, keyword)
            var msg = ""
            var res: GetUserFansList? = null
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
     *  followList：关注列表
     * （id：用户id sex：性别 username：昵称 photo：头像url fansCounts：粉丝数）
     * counts：数据总量
     * pages：页面总数
     */
    fun getUserFollowList(cnt: Int, id: String, page: Int, keyword: String = ""): GetUserFollowList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = fansService.getUserFollowList(cnt, id, page, InfoRepository.token, keyword)
            var msg = ""
            var res: GetUserFollowList? = null
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