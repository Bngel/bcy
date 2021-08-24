package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.FavorController.deleteAcgFavorCos.DeleteAcgFavorCos
import com.bngel.bcy.bean.HistoryController.deleteUserAllHistory.DeleteUserAllHistory
import com.bngel.bcy.bean.HistoryController.deleteUserHistory.DeleteUserHistory
import com.bngel.bcy.bean.HistoryController.getUserHistoryList.GetUserHistoryList
import com.bngel.bcy.dao.HistoryControllerDao.HistoryControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import kotlin.concurrent.thread

class HistoryControllerService {

    private val historyService = HistoryControllerDao.create()

    /**
     * msg:
     * success：成功
     */
    fun deleteUserAllHistory(id: String): DeleteUserAllHistory? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = historyService.deleteUserAllHistory(id, InfoRepository.token)
            var msg = ""
            var res: DeleteUserAllHistory? = null
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
     * existWrong：历史浏览不存在
     * success：成功
     */
    fun deleteUserHistory(id: String, numbers: List<String>): DeleteUserHistory? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = historyService.deleteUserHistory(id, numbers, InfoRepository.token)
            var msg = ""
            var res: DeleteUserHistory? = null
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
     * 返回historyCosList
     * （number：cos编号 id：发布者id username：用户昵称 photo：头像 description：内容 cosPhoto：cos图片 create_time：发布时间）
     */
    fun getUserHistoryList(cnt:Int, id:String, page:Int): GetUserHistoryList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = historyService.getUserHistoryList(cnt, id, page, InfoRepository.token)
            var msg = ""
            var res: GetUserHistoryList? = null
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