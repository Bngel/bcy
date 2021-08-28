package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.CosController.getAcgCosCountsList.GetAcgCosCountsList
import com.bngel.bcy.bean.HelpController.getUserHelp.GetUserHelp
import com.bngel.bcy.bean.HelpController.getUserHelpList.GetUserHelpList
import com.bngel.bcy.dao.HelpControllerDao.HelpControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import kotlin.concurrent.thread

class HelpControllerService {

    private val helpService = HelpControllerDao.create()

    /**
     * msg:
     * 返回data
     * helpList：帮助列表
     * （number：帮助编号 question：问题）cnts：数据总量，pages：页面总数
     */
    fun getUserHelpList(cnt: Int, page: Int, type: Int): GetUserHelpList?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = helpService.getUserHelpList(cnt, page, type)
            var msg = ""
            var res: GetUserHelpList? = null
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
     * existWrong：帮助不存在
     * success：成功
     * 返回data： help（number：帮助编号 question：问题 answer：解决方案）
     */
    fun getUserHelp(number: String): GetUserHelp?{
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = helpService.getUserHelp(number)
            var msg = ""
            var res: GetUserHelp? = null
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