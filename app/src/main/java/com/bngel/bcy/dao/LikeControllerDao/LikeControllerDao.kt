package com.bngel.bcy.dao.LikeControllerDao

import com.bngel.bcy.bean.LikeController.deleteAcgLikeCos.DeleteAcgLikeCos
import com.bngel.bcy.bean.LikeController.getAcgJudgeLikes.GetAcgJudgeLikes
import com.bngel.bcy.bean.LikeController.getAcgLikeList.GetAcgLikeList
import com.bngel.bcy.bean.LikeController.postAcgLikeCos.PostAcgLikeCos
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

interface LikeControllerDao {

    /**
     * 获取是否被喜欢
     */
    @GET("/acg/judgeLikes")
    fun getAcgJudgeLikes(
        @Query("id") id: String,
        @Query("numbers") numbers: List<String>,
        @Query("token") token: String
    ): Call<GetAcgJudgeLikes>

    /**
     * 添加喜欢cos
     */
    @POST("/acg/likeCos")
    fun postAcgLikeCos(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<PostAcgLikeCos>

    /**
     * 取消喜欢cos
     */
    @DELETE("/acg/likeCos")
    fun deleteAcgLikeCos(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<DeleteAcgLikeCos>

    /**
     * 获取喜欢列表（点赞收藏内容请调用另外接口）
     */
    @GET("/acg/likeList")
    fun getAcgLikeList(
        @Query("id") id: String,
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetAcgLikeList>

    companion object {
        fun create(): LikeControllerDao {
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
            return retrofit.create(LikeControllerDao::class.java)
        }

        fun create(gson: Gson): LikeControllerDao {
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
            return retrofit.create(LikeControllerDao::class.java)
        }
    }
}