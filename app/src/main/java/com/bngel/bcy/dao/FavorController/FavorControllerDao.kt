package com.bngel.bcy.dao.FavorController

import com.bngel.bcy.bean.FavorController.deleteAcgFavorCos.DeleteAcgFavorCos
import com.bngel.bcy.bean.FavorController.getAcgFavorList.GetAcgFavorList
import com.bngel.bcy.bean.FavorController.postAcgFavorCos.PostAcgFavorCos
import com.bngel.bcy.bean.FavorController.postAcgJudgeFavor.PostAcgJudgeFavor
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

interface FavorControllerDao {

    /**
     * 判断cos是否已经收藏
     */
    @POST("/acg/judgeFavor")
    fun postAcgJudgeFavor(
        @Query("id") id: String,
        @Query("number") number: List<String>,
        @Query("token") token: String
    ): Call<PostAcgJudgeFavor>

    /**
     * 添加收藏（文章/cos）
     */
    @POST("/acg/favorCos")
    fun postAcgFavorCos(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<PostAcgFavorCos>

    /**
     * 取消收藏（文章/cos）
     */
    @DELETE("/acg/favorCos")
    fun deleteAcgFavorCos(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<DeleteAcgFavorCos>

    /**
     * 获取收藏列表（文章/cos）
     */
    @GET("/acg/favorList")
    fun getAcgFavorList(
        @Query("id") id: String,
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetAcgFavorList>

    companion object {
        fun create(): FavorControllerDao {
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
            return retrofit.create(FavorControllerDao::class.java)
        }

        fun create(gson: Gson): FavorControllerDao {
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
            return retrofit.create(FavorControllerDao::class.java)
        }
    }
}