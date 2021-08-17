package com.bngel.bcy.dao.CosControllerDao

import com.bngel.bcy.bean.CosController.deleteAcgCos.DeleteAcgCos
import com.bngel.bcy.bean.CosController.deleteAcgLikeCosComment.DeleteAcgLikeCosComment
import com.bngel.bcy.bean.CosController.getAcgCos.Cos
import com.bngel.bcy.bean.CosController.getAcgCos.GetAcgCos
import com.bngel.bcy.bean.CosController.getAcgCosComment.GetAcgCosComment
import com.bngel.bcy.bean.CosController.getAcgCosCommentCountsList.GetAcgCosCommentCountsList
import com.bngel.bcy.bean.CosController.getAcgCosCountsList.GetAcgCosCountsList
import com.bngel.bcy.bean.CosController.getAcgFollowCos.GetAcgFollowCos
import com.bngel.bcy.bean.CosController.getAcgFollowNoRead.GetAcgFollowNoRead
import com.bngel.bcy.bean.CosController.postAcgCos.PostAcgCos
import com.bngel.bcy.bean.CosController.postAcgCosComment.PostAcgCosComment
import com.bngel.bcy.bean.CosController.postAcgLikeCosComment.PostAcgLikeCosComment
import com.bngel.bcy.dao.CosControllerDao.CosControllerDao
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

interface CosControllerDao {

    /**
     * 获取cos页面上半部分内容（获取底下评论在另外一个接口）
     */
    @GET("/acg/cos")
    fun getAcgCos(
        @Query("id") id: String?,
        @Query("number") number: String,
        @Query("token") token: String?
    ): Call<GetAcgCos>

    /**
     * 发布cos
     */
    @POST("/acg/cos")
    fun postAcgCos(
        @Query("id") id: String,
        @Query("description") description: String,
        @Query("label") label: List<String>,
        @Query("permission") permission: String,
        @Query("photo") photo: List<String>,
        @Query("token") token: String
    ): Call<PostAcgCos>

    /**
     * 批量删除Cos（管理员用，如果违反规定就删除）（只有一个也扔这个接口~）
     */
    @DELETE("/acg/cos")
    fun deleteAcgCos(
        @Query("numbers") numbers: List<String>
    ): Call<DeleteAcgCos>

    /**
     * 修改cos内容（没改的参数不要带，或者可以带空，图片要注意下带空就是没图）
     */
    @PUT("/acg/cos")
    fun putAcgCos(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("description") description: String?,
        @Query("permission") permission: String?,
        @Query("cosPhoto") cosPhoto: List<String>?,
        @Query("token") token: String
    )//: Call<PutAcgCos>

    /**
     * 获取cos页面下面的评论内容
     */
    @GET("/acg/cosComment")
    fun getAcgCosComment(
        @Query("id") id: String?,
        @Query("cnt") cnt: Int,
        @Query("number") number: String,
        @Query("page") page: Int,
        @Query("type") type: Int,
        @Query("token") token: String?
    ): Call<GetAcgCosComment>

    /**
     * 发表cos下面的评论
     */
    @POST("/acg/cosComment")
    fun postAcgCosComment(
        @Query("id") id: String,
        @Query("cosNumber") cosNumber: String,
        @Query("description") description: String,
        @Query("fatherNumber") fatherNumber: String?,
        @Query("reply") reply: String?,
        @Query("replyNumber") replyNumber: String?,
        @Query("toId") toId: String?,
        @Query("token") token: String
    ): Call<PostAcgCosComment>

    /**
     * 获取cos下面评论的评论列表（在cos页面也用这个接口获取下面的回复数据，cnt请填3 page请填1 type请填1）
     */
    @GET("/acg/cosCommentComment")
    fun getAcgCosCommentComment(
        @Query("id") id: String?,
        @Query("cnt") cnt: Int,
        @Query("number") number: String,
        @Query("page") page: Int,
        @Query("type") type: Int,
        @Query("token") token: String?
    )//: Call<GetAcgCosCommentComment>

    /**
     * 获取cos下面评论的点赞数和评论数
     */
    @GET("/acg/cosCommentCountsList")
    fun getAcgCosCommentCountsList(
        @Query("id") id: String?,
        @Query("number") number: String,
        @Query("token") token: String?
    ): Call<GetAcgCosCommentCountsList>

    /**
     * 获取cos的 点赞 评论 收藏数
     */
    @GET("/acg/cosCountsList")
    fun getAcgCosCountsList(
        @Query("id") id: String?,
        @Query("number") number: String,
        @Query("token") token: String?
    ): Call<GetAcgCosCountsList>

    /**
     * cos图片上传
     */
    @Multipart
    @POST("/acg/cosPhotoUpload")
    fun postAcgCosPhotoUpload(
        @Part("photo") photo: MultipartBody.Part,
        @Part("token") token: String
    )//: Call<PostAcgCosPhotoUpload>

    /**
     * 获取关注用户的cos列表
     */
    @GET("/acg/followCos")
    fun getAcgFollowCos(
        @Query("id") id: String,
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetAcgFollowCos>

    /**
     * 获取关注的未读数（获取关注的cos的关注列表后会清空）
     */
    @GET("/acg/followNoRead")
    fun getAcgFollowNoRead(
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<GetAcgFollowNoRead>

    /**
     * 获取日榜热门cos列表
     */
    @GET("/acg/hotDayCos")
    fun getAcgHotDayCos(
        @Query("time") time: String
    )//: Call<GetAcgHotDayCos>

    /**
     * 获取周榜热门cos列表
     */
    @GET("/acg/hotWeekCos")
    fun getAcgHotWeekCos(
        @Query("time") time: String
    )//: Call<GetAcgHotWeekCos>

    /**
     * 点赞cos下的评论
     */
    @POST("/acg/likeCosComment")
    fun postAcgLikeCosComment(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<PostAcgLikeCosComment>

    /**
     * 取消点赞cos下的评论
     */
    @DELETE("/acg/likeCosComment")
    fun deleteAcgLikeCosComment(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<DeleteAcgLikeCosComment>

    /**
     * 获取推荐cos标签
     */
    @GET("/acg/recommendList")
    fun getAcgRecommendList(
    )//: Call<GetAcgRecommendList>

    companion object {
        fun create(): CosControllerDao {
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
            return retrofit.create(CosControllerDao::class.java)
        }

        fun create(gson: Gson): CosControllerDao {
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
            return retrofit.create(CosControllerDao::class.java)
        }
    }
}