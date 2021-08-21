package com.bngel.bcy.dao.CircleController

import com.bngel.bcy.bean.CircleController.getAcgCircle.GetAcgCircle
import com.bngel.bcy.bean.CircleController.getEsRecommendCircle.GetEsRecommendCircle
import com.bngel.bcy.bean.CircleController.postAcgCircle.PostAcgCircle
import com.bngel.bcy.bean.CircleController.postAcgCirclePhoto.PostAcgCirclePhoto
import com.bngel.bcy.bean.CircleController.deleteAcgFollowCircle.DeleteAcgFollowCircle
import com.bngel.bcy.bean.CircleController.getAcgPersonalCircle.GetAcgPersonalCircle
import com.bngel.bcy.bean.CircleController.getAcgSearchCircle.GetAcgSearchCircle
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

    @GET("/acg/circle")
    fun getAcgCircle(
        @Query("circleName") circleName: String
    ): Call<GetAcgCircle>

    @POST("/acg/circle")
    fun postAcgCircle(
        @Query("circleName") circleName: String,
        @Query("description") description: String,
        @Query("id") id: String,
        @Query("nickName") nickName: String,
        @Query("photo") photo: String,
        @Query("token") token: String
    ): Call<PostAcgCircle>

    @POST("/acg/circlePhoto")
    @Multipart
    fun postAcgCirclePhoto(
        @Part("id") id: String,
        @Part photo: MultipartBody.Part,
        @Part("token") token: String
    ): Call<PostAcgCirclePhoto>

    @POST("/acg/followCircle")
    fun postAcgFollowCircle(
        @Query("circleName") circleName: String,
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<DeleteAcgFollowCircle>

    @DELETE("/acg/followCircle")
    fun deleteAcgFollowCircle(
        @Query("circleName") circleName: String,
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<DeleteAcgFollowCircle>

    @GET("/acg/personalCircle")
    fun getAcgPersonalCircle(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetAcgPersonalCircle>

    @GET("/acg/searchCircle")
    fun getAcgSearchCircle(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetAcgSearchCircle>



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