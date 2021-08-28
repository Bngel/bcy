package com.bngel.bcy.dao.TalkControllerDao

import com.bngel.bcy.bean.TalkController.getCommunityTalkCounts.GetCommunityTalkCounts
import com.bngel.bcy.bean.TalkController.getCommunityTalkList.GetCommunityTalkList
import com.bngel.bcy.dao.TalkControllerDao.TalkControllerDao
import com.bngel.bcy.web.SSLSocketClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TalkControllerDao {

    /**
     * 获取未读条数和最后一条消息和更新时间（用于聊天列表）
     */
    @GET("/community/talkCounts")
    fun getCommunityTalkCounts(
        @Query("id") id: String,
        @Query("toId") toId: List<String>,
        @Query("token") token: String
    ): Call<GetCommunityTalkCounts>

    /**
     * 获取用户聊天列表（最后一次聊天信息 未读数 更新时间在另一个接口）
     */
    @GET("/community/talkList")
    fun getCommunityTalkList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetCommunityTalkList>

    companion object {
        fun create(): TalkControllerDao {
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
            return retrofit.create(TalkControllerDao::class.java)
        }

        fun create(gson: Gson): TalkControllerDao {
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
            return retrofit.create(TalkControllerDao::class.java)
        }
    }
}