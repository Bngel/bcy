package com.bngel.bcy.dao.MessageControllerDao

import com.bngel.bcy.bean.MessageController.getCommunityAtList.GetCommunityAtList
import com.bngel.bcy.bean.MessageController.getCommunityCommentList.GetCommunityCommentList
import com.bngel.bcy.bean.MessageController.getCommunityLikeList.GetCommunityLikeList
import com.bngel.bcy.dao.MessageControllerDao.MessageControllerDao
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

interface MessageControllerDao {

    /**
     * 获取我的消息盒子中@我的列表
     */
    @GET("/community/atList")
    fun getCommunityAtList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetCommunityAtList>

    /**
     * 获取我的消息盒子中评论列表
     */
    @GET("/community/commentList")
    fun getCommunityCommentList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetCommunityCommentList>

    /**
     * 获取我的消息盒子中赞列表
     */
    @GET("/community/likeList")
    fun getCommunityLikeList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetCommunityLikeList>

    companion object {
        fun create(): MessageControllerDao {
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
            return retrofit.create(MessageControllerDao::class.java)
        }

        fun create(gson: Gson): MessageControllerDao {
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
            return retrofit.create(MessageControllerDao::class.java)
        }
    }

}