package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.FansController.postUserJudgeFollow.PostUserJudgeFollow
import com.bngel.bcy.bean.TalkController.getCommunityTalkCounts.GetCommunityTalkCounts
import com.bngel.bcy.bean.TalkController.getCommunityTalkList.GetCommunityTalkList
import com.bngel.bcy.dao.TalkControllerDao.TalkControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import kotlin.concurrent.thread

class TalkControllerService {

    private val talkService = TalkControllerDao.create()

    fun getCommunityTalkCounts(id: String, toId: List<String>): GetCommunityTalkCounts? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = talkService.getCommunityTalkCounts(id, toId, InfoRepository.token)
            var msg = ""
            var res: GetCommunityTalkCounts? = null
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

    fun getCommunityTalkList(cnt: Int, id: String, page: Int): GetCommunityTalkList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = talkService.getCommunityTalkList(cnt, id, page, InfoRepository.token)
            var msg = ""
            var res: GetCommunityTalkList? = null
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