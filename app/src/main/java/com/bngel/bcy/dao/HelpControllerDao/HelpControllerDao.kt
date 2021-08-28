package com.bngel.bcy.dao.HelpControllerDao

import com.bngel.bcy.bean.HelpController.getUserHelp.GetUserHelp
import com.bngel.bcy.bean.HelpController.getUserHelpList.GetUserHelpList
import com.bngel.bcy.dao.HelpControllerDao.HelpControllerDao
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

interface HelpControllerDao {

    /**
     * 获取用户帮助列表P
     */
    @GET("/user/helpList")
    fun getUserHelpList(
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("type") type: Int
    ): Call<GetUserHelpList>

    @GET("/user/help")
    fun getUserHelp(
        @Query("number") number: String
    ): Call<GetUserHelp>

    companion object {
        fun create(): HelpControllerDao {
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
            return retrofit.create(HelpControllerDao::class.java)
        }

        fun create(gson: Gson): HelpControllerDao {
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
            return retrofit.create(HelpControllerDao::class.java)
        }
    }
}