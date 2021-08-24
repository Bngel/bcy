package com.bngel.bcy.dao.HistoryControllerDao

import com.bngel.bcy.bean.HistoryController.deleteUserAllHistory.DeleteUserAllHistory
import com.bngel.bcy.bean.HistoryController.deleteUserHistory.DeleteUserHistory
import com.bngel.bcy.bean.HistoryController.getUserHistoryList.GetUserHistoryList
import com.bngel.bcy.dao.HistoryControllerDao.HistoryControllerDao
import com.bngel.bcy.web.SSLSocketClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

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
    ): Call<GetUserHistoryList>

    companion object {
        fun create(): HistoryControllerDao {
            val baseUrl = "https://www.rat403.cn/"
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val mHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.SSLSocketFactory)//配置
                .hostnameVerifier(SSLSocketClient.hostnameVerifier)//配置
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mHttpClient)
                .build()
            return retrofit.create(HistoryControllerDao::class.java)
        }

        fun create(gson: Gson): HistoryControllerDao {
            val baseUrl = "https://www.rat403.cn/"
            val mHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.SSLSocketFactory)//配置
                .hostnameVerifier(SSLSocketClient.hostnameVerifier)//配置
                .build();

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mHttpClient)
                .build()
            return retrofit.create(HistoryControllerDao::class.java)
        }
    }
}