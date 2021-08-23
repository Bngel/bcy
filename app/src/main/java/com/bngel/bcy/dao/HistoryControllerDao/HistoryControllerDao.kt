package com.bngel.bcy.dao.HistoryControllerDao

import com.bngel.bcy.bean.HistoryController.deleteUserAllHistory.DeleteUserAllHistory
import com.bngel.bcy.bean.HistoryController.deleteUserHistory.DeleteUserHistory
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryControllerDao {

    /**
     * 清空历史浏览
     */
    @DELETE("/user/allHistory")
    fun deleteUserAllHistory(
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<DeleteUserAllHistory>

    /**
     * 批量删除历史浏览
     */
    @DELETE("/user/history")
    fun deleteUserHistory(
        @Query("id") id: String,
        @Query("numbers") numbers: List<String>,
        @Query("token") token: String
    ): Call<DeleteUserHistory>

    /**
     * 获取历史浏览列表（cos）
     */
    @GET("/user/historyList")
    fun getUserHistoryList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    )//: Call<GetUserHistoryList>
}