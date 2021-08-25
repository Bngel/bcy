package com.bngel.bcy.dao.CircleControllerDao

import com.bngel.bcy.bean.CircleController.getAcgCircle.GetAcgCircle
import com.bngel.bcy.bean.CircleController.getEsRecommendCircle.GetEsRecommendCircle
import com.bngel.bcy.bean.CircleController.postAcgCircle.PostAcgCircle
import com.bngel.bcy.bean.CircleController.postAcgCirclePhoto.PostAcgCirclePhoto
import com.bngel.bcy.bean.CircleController.deleteAcgFollowCircle.DeleteAcgFollowCircle
import com.bngel.bcy.bean.CircleController.getAcgCircleCosList.GetAcgCircleCosList
import com.bngel.bcy.bean.CircleController.getAcgJudgeCircle.GetAcgJudgeCircle
import com.bngel.bcy.bean.CircleController.getAcgPersonalCircle.GetAcgPersonalCircle
import com.bngel.bcy.bean.CircleController.getAcgSearchCircle.GetAcgSearchCircle
import com.bngel.bcy.bean.CircleController.postAcgFollowCircle.PostAcgFollowCircle
import com.bngel.bcy.web.SSLSocketClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface CircleControllerDao {

    /**
     * 获取推荐圈子列表（未登录给0）
     */
    @GET("/es/recommendCircle")
    fun getEsRecommendCircle(
        @Query("cnt") cnt: Int,
        @Query("id") id: String?,
        @Query("page") page: Int,
        @Query("token") token: String?
    ): Call<GetEsRecommendCircle>

    /**
     * 获取圈子基本信息
     */
    @GET("/acg/circle")
    fun getAcgCircle(
        @Query("circleName") circleName: String
    ): Call<GetAcgCircle>

    /**
     * 创建圈子
     */
    @POST("/acg/circle")
    fun postAcgCircle(
        @Query("circleName") circleName: String,
        @Query("description") description: String,
        @Query("id") id: String,
        @Query("nickName") nickName: String,
        @Query("photo") photo: String,
        @Query("token") token: String
    ): Call<PostAcgCircle>

    /**
     * 上传圈子图片
     */
    @POST("/acg/circlePhoto")
    @Multipart
    fun postAcgCirclePhoto(
        @Part("id") id: String,
        @Part photo: MultipartBody.Part,
        @Part("token") token: String
    ): Call<PostAcgCirclePhoto>

    /**
     * 关注圈子
     */
    @POST("/acg/followCircle")
    fun postAcgFollowCircle(
        @Query("circleName") circleName: String,
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<PostAcgFollowCircle>

    /**
     * 取消关注圈子
     */
    @DELETE("/acg/followCircle")
    fun deleteAcgFollowCircle(
        @Query("circleName") circleName: String,
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<DeleteAcgFollowCircle>

    /**
     * 获取个人圈子列表
     */
    @GET("/acg/personalCircle")
    fun getAcgPersonalCircle(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetAcgPersonalCircle>

    /**
     * 搜索圈子（屏蔽圈子搜不到，所以要带id)
     */
    @GET("/acg/searchCircle")
    fun getAcgSearchCircle(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("token") token: String?
    ): Call<GetAcgSearchCircle>

    /**
     * 批量判断圈子是否关注（一个也用这个接口）
     */
    @GET("/acg/judgeCircle")
    fun getAcgJudgeCircle(
        @Query("circleNames") circleNames: List<String>,
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<GetAcgJudgeCircle>

    /**
     * 获取圈子下cos列表
     */
    @GET("/acg/circleCosList")
    fun getAcgCircleCosList(
        @Query("circleName") circleName: String,
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("type") type: Int
    ): Call<GetAcgCircleCosList>



    companion object {
        fun create(): CircleControllerDao {
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
            return retrofit.create(CircleControllerDao::class.java)
        }

        fun create(gson: Gson): CircleControllerDao {
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
            return retrofit.create(CircleControllerDao::class.java)
        }
    }
}