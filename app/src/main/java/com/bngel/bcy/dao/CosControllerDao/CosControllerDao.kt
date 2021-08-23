package com.bngel.bcy.dao.CosControllerDao

import com.bngel.bcy.bean.CosController.deleteAcgCos.DeleteAcgCos
import com.bngel.bcy.bean.CosController.deleteAcgLikeCosComment.DeleteAcgLikeCosComment
import com.bngel.bcy.bean.CosController.deleteEsAllSearchHistory.DeleteEsAllSearchHistory
import com.bngel.bcy.bean.CosController.deleteEsSearchHistory.DeleteEsSearchHistory
import com.bngel.bcy.bean.CosController.getAcgCos.GetAcgCos
import com.bngel.bcy.bean.CosController.getAcgCosComment.GetAcgCosComment
import com.bngel.bcy.bean.CosController.getAcgCosCommentComment.GetAcgCosCommentComment
import com.bngel.bcy.bean.CosController.getAcgCosCommentCountsList.GetAcgCosCommentCountsList
import com.bngel.bcy.bean.CosController.getAcgCosCountsList.GetAcgCosCountsList
import com.bngel.bcy.bean.CosController.getAcgFollowCos.GetAcgFollowCos
import com.bngel.bcy.bean.CosController.getAcgFollowNoRead.GetAcgFollowNoRead
import com.bngel.bcy.bean.CosController.getAcgRecommendList.GetAcgRecommendList
import com.bngel.bcy.bean.CosController.getEsLabelCos.GetEsLabelCos
import com.bngel.bcy.bean.CosController.getEsRecommendCos.GetEsRecommendCos
import com.bngel.bcy.bean.CosController.getEsSearchHistory.GetEsSearchHistory
import com.bngel.bcy.bean.CosController.postAcgCos.PostAcgCos
import com.bngel.bcy.bean.CosController.postAcgCosComment.PostAcgCosComment
import com.bngel.bcy.bean.CosController.postAcgCosPhotoUpload.PostAcgCosPhotoUpload
import com.bngel.bcy.bean.CosController.postAcgLikeCosComment.PostAcgLikeCosComment
import com.bngel.bcy.bean.CosController.postEsSearchCos.PostEsSearchCos
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
        @Query("permission") permission: Int,
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
        @Query("number") number: String,
        @Query("cnt") cnt: Int,
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
    ): Call<GetAcgCosCommentComment>

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
        @Part photo: MultipartBody.Part,
        @Part("token") token: String
    ): Call<PostAcgCosPhotoUpload>

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
        @Query("time") time: String,
        @Query("type") type: Int
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
    ): Call<GetAcgRecommendList>

    /**
     * 清空所有(搜索)历史记录
     */
    @DELETE("/es/allSearchHistory")
    fun deleteEsAllSearchHistory(
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<DeleteEsAllSearchHistory>

    /**
     * 获取标题（绘画 cos 写作）下的cos内容（也是推荐制的，随机）
     */
    @GET("/es/labelCos")
    fun getEsLabelCos(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("token") token: String
    ): Call<GetEsLabelCos>

    /**
     * 发现（推荐cos，在不同的获取是会有重复推荐的）
     */
    @GET("/es/recommendCos")
    fun getEsRecommendCos(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("token") token: String?
    ): Call<GetEsRecommendCos>

    /**
     * cos搜索（会匹配标签内容和描述 拼音分词（搜拼音也能搜到） + ik细粒度分词）
     */
    @POST("/es/searchCos")
    fun postEsSearchCos(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("token") token: String?
    ): Call<PostEsSearchCos>

    /**
     * 获取搜索历史
     */
    @GET("/es/searchHistory")
    fun getEsSearchHistory(
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<GetEsSearchHistory>

    /**
     * 删除历史搜索
     */
    @DELETE("/es/searchHistory")
    fun deleteEsSearchHistory(
        @Query("id") id: String,
        @Query("token") token: String
    ): Call<DeleteEsSearchHistory>



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