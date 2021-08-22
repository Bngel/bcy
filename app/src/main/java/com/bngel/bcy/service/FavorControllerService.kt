package com.bngel.bcy.service

import android.widget.Toast
import com.bngel.bcy.bean.FavorController.deleteAcgFavorCos.DeleteAcgFavorCos
import com.bngel.bcy.bean.FavorController.getAcgFavorList.GetAcgFavorList
import com.bngel.bcy.bean.FavorController.postAcgFavorCos.PostAcgFavorCos
import com.bngel.bcy.bean.FavorController.postAcgJudgeFavor.PostAcgJudgeFavor
import com.bngel.bcy.dao.FavorControllerDao.FavorControllerDao
import com.bngel.bcy.utils.ActivityCollector
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.web.WebRepository
import kotlin.concurrent.thread

class FavorControllerService {

    private val favorService = FavorControllerDao.create()


    /**
     * success：成功
     * 返回data：
     * judgeFavorList
     * （id：用户id status：0未收藏 1已收藏）
     */
    fun postAcgJudgeFavor(id: String, number: List<String>): PostAcgJudgeFavor? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = favorService.postAcgJudgeFavor(id, number, InfoRepository.token)
            var msg = ""
            var res: PostAcgJudgeFavor? = null
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
     * existWrong：cos不存在
     * repeatWrong：重复收藏
     * success：成功
     */
    fun postAcgFavorCos(id: String, number: String): PostAcgFavorCos? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try {
            val data = favorService.postAcgFavorCos(id, number, InfoRepository.token)
            var msg = ""
            var res: PostAcgFavorCos? = null
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
     * existWrong：cos不存在
     * repeatWrong：未收藏
     * success：成功
     */
    fun deleteAcgFavorCos(id: String, number: String): DeleteAcgFavorCos? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = favorService.deleteAcgFavorCos(id, number, InfoRepository.token)
            var msg = ""
            var res: DeleteAcgFavorCos? = null
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
     * success：成功
     * 返回data
     * favorList：
     * （cos_number：cos编号 id：cos用户id
     * username：用户昵称 photo：用户头像
     * cosPhoto：cos图片（list） create_time：cos发布时间）
     */
    fun getAcgFavorList(id: String, cnt: Int, page: Int): GetAcgFavorList? {
        if (ActivityCollector.curActivity != null) {
            if (!WebRepository.isNetworkConnected()) {
                Toast.makeText(ActivityCollector.curActivity, "网络错误", Toast.LENGTH_SHORT).show()
                return null
            }
        }
        try{
            val data = favorService.getAcgFavorList(id, cnt, page, InfoRepository.token)
            var msg = ""
            var res: GetAcgFavorList? = null
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