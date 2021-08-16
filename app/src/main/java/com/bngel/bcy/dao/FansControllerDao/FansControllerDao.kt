package com.bngel.bcy.dao.FansControllerDao

import com.bngel.bcy.bean.FansController.deleteUserFollow.DeleteUserFollow
import com.bngel.bcy.bean.FansController.getUserFansList.GetUserFansList
import com.bngel.bcy.bean.FansController.getUserFollowList.GetUserFollowList
import com.bngel.bcy.bean.FansController.postUserFollow.PostUserFollow
import com.bngel.bcy.bean.FansController.postUserJudgeFollow.PostUserJudgeFollow
import com.bngel.bcy.dao.FansControllerDao.FansControllerDao
import com.bngel.bcy.web.SSLSocketClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FansControllerDao {

    /**
     * 获取自己的粉丝列表（还要显示关注情况的话，请用judgeFollow那个接口）
     */
    @GET("/user/fansList")
    fun getUserFansList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String,
        @Query("keyword") keyword: String?
    ): Call<GetUserFansList>

    /**
     * 关注别的用户（关注在线的话，会有推送）
     */
    @POST("/user/follow")
    fun postUserFollow(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String,
        @Query("token") token: String
    ): Call<PostUserFollow>

    /**
     * 取消关注别的用户
     */
    @DELETE("/user/follow")
    fun deleteUserFollow(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String,
        @Query("token") token: String
    ): Call<DeleteUserFollow>

    /**
     * 获取自己的关注列表（还要显示关注情况的话，请用judgeFollow那个接口）
     */
    @GET("/user/followList")
    fun getUserFollowList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String,
        @Query("keyword") keyword: String?
    ): Call<GetUserFollowList>

    /**
     * 判断用户的关注情况
     */
    @POST("/user/judgeFollow")
    fun postUserJudgeFollow(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String,
        @Query("token") token: String
    ): Call<PostUserJudgeFollow>

    companion object {
        fun create(): FansControllerDao {
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
            return retrofit.create(FansControllerDao::class.java)
        }

        fun create(gson: Gson): FansControllerDao {
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
            return retrofit.create(FansControllerDao::class.java)
        }
    }
}