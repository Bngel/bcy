package com.bngel.bcy.dao.PersonalControllerDao

import com.bngel.bcy.bean.PersonalController.getUserCommunityInfo.GetUserCommunityInfo
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.getUserPersonalInfoByPhone.GetUserPersonalInfoByPhone
import com.bngel.bcy.bean.PersonalController.getUserPersonalSetting.GetUserPersonalSetting
import com.bngel.bcy.bean.PersonalController.getUserUserCounts.GetUserUserCounts
import com.bngel.bcy.bean.PersonalController.putUserPersonalInfo.PutUserPersonalInfo
import com.bngel.bcy.bean.PersonalController.putUserPersonalSetting.PutUserPersonalSetting
import com.bngel.bcy.bean.PersonalController.postUserJudgeNew.PostUserJudgeNew
import com.bngel.bcy.bean.PersonalController.postUserPhotoUpload.PostUserPhotoUpload
import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.getUserPersonalInfoById.GetUserPersonalInfoById
import com.bngel.bcy.bean.PersonalController.postUserSetPassword.PostUserSetPassword
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

interface PersonalControllerDao {

    /**
     * 获取用户社交信息（手机 和 微博号）
     */
    @GET("/user/communityInfo")
    fun getUserCommunityInfo(
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<GetUserCommunityInfo>
    /**
     * 判断是否为新用户
     */
    @POST("/user/judgeNew")
    fun postUserJudgeNew(
        @Query("id") id: String,
        @Query("token") token:String
    ): Call<PostUserJudgeNew>

    /**
     * 获取用户基本个人信息（个人主页）
     */
    @GET("/user/personalInfo")
    fun getUserPersonalInfoById(
        @Query("id") id: String,
        @Query("token") token:String
    ): Call<GetUserPersonalInfoById>

    /**
     * 获取用户基本个人信息（个人主页）
     */
    @GET("/user/personalInfo")
    fun getUserPersonalInfoByPhone(
        @Query("phone") phone: String,
        @Query("token") token:String
    ): Call<GetUserPersonalInfoByPhone>

    /**
     * 用户修改基本个人信息（修改头像在上传头像那个接口会自动修改）
     */
    @PUT("/user/personalInfo")
    fun putUserPersonalInfo(
        @Query("birthday") birthday: String?,
        @Query("city") city: String?,
        @Query("description") description: String?,
        @Query("id") id: String,
        @Query("province") province: String?,
        @Query("sex") sex: String?,
        @Query("username") username: String?,
        @Query("token") token:String
    ): Call<PutUserPersonalInfo>

    /**
     * 获取用户个人设置
     */
    @GET("/user/personalSetting")
    fun getUserPersonalSetting(
        @Query("id") id: String,
        @Query("token") token:String
    ): Call<GetUserPersonalSetting>

    /**
     * 修改个人设置
     */
    @PUT("/user/personalSetting")
    fun putUserPersonalSetting(
        @Query("id") id: String,
        @Query("pushComment") pushComment: Int?,
        @Query("pushFans") pushFans: Int?,
        @Query("pushInfo") pushInfo: Int?,
        @Query("pushLike") pushLike: Int?,
        @Query("pushSystem") pushSystem: Int?,
        @Query("token") token:String
    ): Call<PutUserPersonalSetting>

    /**
     * 新用户设置密码
     */
    @POST("/user/setPassword")
    fun postUserSetPassword(
        @Query("id") id: String,
        @Query("password") password: String,
        @Query("token") token:String
    ): Call<PostUserSetPassword>

    /**
     * 获取用户的粉丝数 关注数 动态数（统一接口，所有人的信息都可以在这里获取）
     */
    @GET("/user/userCounts")
    fun getUserUserCounts(
        @Query("userId") userId: List<String>,
        @Query("token") token:String
    ): Call<GetUserUserCounts>

    /**
     * 用户头像上传
     */
    @Multipart
    @POST("/user/photoUpload")
    fun postUserPhotoUpload(
        @Part("id") id: String,
        @Part photo: MultipartBody.Part,
        @Part("token") token:String
    ): Call<PostUserPhotoUpload>

    companion object {
        fun create(): PersonalControllerDao {
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
            return retrofit.create(PersonalControllerDao::class.java)
        }

        fun create(gson: Gson): PersonalControllerDao {
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
            return retrofit.create(PersonalControllerDao::class.java)
        }
    }
}