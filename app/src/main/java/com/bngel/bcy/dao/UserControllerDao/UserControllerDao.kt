package com.bngel.bcy.dao.UserControllerDao

import com.bngel.bcy.bean.UserController.getCommunitySearchUser.GetCommunitySearchUser
import com.bngel.bcy.bean.UserController.postOauthChangePassword.PostOauthChangePassword
import com.bngel.bcy.bean.UserController.postOauthLoginBySms.PostOauthLoginBySms
import com.bngel.bcy.bean.UserController.postOauthLogout.PostOauthLogout
import com.bngel.bcy.bean.UserController.postOauthToken.PostOauthToken
import com.bngel.bcy.web.SSLSocketClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface UserControllerDao {

    /**
     * 找回密码
     */
    @POST("/oauth/changePassword")
    fun postChangePassword(
        @Query("code") code: String,
        @Query("newPassword") newPassword: String,
        @Query("phone") phone: String
    ): Call<PostOauthChangePassword>

    /**
     * 用户短信验证码登录
     */
    @POST("/oauth/loginBySms")
    fun postOauthLoginBySms(
        @Query("code") code: String,
        @Query("phone") phone: String,
        @Query("type") type: Int
    ): Call<PostOauthLoginBySms>

    /**
     * 登出账号
     */
    @POST("/oauth/logout")
    fun postOauthLogout(
        @Query("id") id: String,
        @Query("type") type: Int
    ): Call<PostOauthLogout>

    /**
     * 用户账号密码登录
     */
    @POST("/oauth/token")
    fun postOauthToken(
        @Query("grant_type") grant_type: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String
    ): Call<PostOauthToken>

    /**
     * 搜索用户（默认搜索不到自己，用户未登录不能用）
     */
    @GET("/community/searchUser")
    fun getCommunitySearchUser(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("token") token: String?
    ): Call<GetCommunitySearchUser>

    companion object {
        fun create(): UserControllerDao {
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
            return retrofit.create(UserControllerDao::class.java)
        }

        fun create(gson: Gson): UserControllerDao {
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
            return retrofit.create(UserControllerDao::class.java)
        }
    }
}