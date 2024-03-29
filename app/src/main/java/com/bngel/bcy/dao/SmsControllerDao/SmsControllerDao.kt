package com.bngel.bcy.dao.SmsControllerDao

import com.bngel.bcy.bean.SmsController.postOauthCode.PostOauthCode
import com.bngel.bcy.bean.SmsController.postUserChangePhone.PostUserChangePhone
import com.bngel.bcy.web.SSLSocketClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface SmsControllerDao {

    /**
     * 发送短信验证码（15分钟有效）
     */
    @POST("/oauth/code")
    fun postOauthCode(
        @Query("phone") phone: String,
        @Query("type") type: Int
    ): Call<PostOauthCode>

    /**
     * 用户改绑手机
     */
    @POST("/user/changePhone")
    fun postUserChangePhone(
        @Query("code") code: String,
        @Query("id") id: String,
        @Query("phone") phone: String,
        @Query("token") token: String
    ): Call<PostUserChangePhone>

    companion object {
        fun create(): SmsControllerDao {
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
            return retrofit.create(SmsControllerDao::class.java)
        }

        fun create(gson: Gson): SmsControllerDao {
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
            return retrofit.create(SmsControllerDao::class.java)
        }
    }
}