package com.bngel.bcy.dao.QAControllerDao

import com.bngel.bcy.bean.QAController.deleteAcgDislikeAnswer.DeleteAcgDislikeAnswer
import com.bngel.bcy.bean.QAController.deleteAcgFollowQa.DeleteAcgFollowQa
import com.bngel.bcy.bean.QAController.getAcgAnswerCommentCommentList.GetAcgAnswerCommentCommentList
import com.bngel.bcy.bean.QAController.getAcgAnswerCommentList.GetAcgAnswerCommentList
import com.bngel.bcy.bean.QAController.getAcgAnswerList.GetAcgAnswerList
import com.bngel.bcy.bean.QAController.getAcgJudgeQa.GetAcgJudgeQa
import com.bngel.bcy.bean.QAController.getAcgJudgeQaAnswer.GetAcgJudgeQaAnswer
import com.bngel.bcy.bean.QAController.getAcgQaAnswerCountsList.GetAcgQaAnswerCountsList
import com.bngel.bcy.bean.QAController.getAcgQaTopic.GetAcgQaTopic
import com.bngel.bcy.bean.QAController.getEsRecommendQa.GetEsRecommendQa
import com.bngel.bcy.bean.QAController.postAcgFollowQa.PostAcgFollowQa
import com.bngel.bcy.bean.QAController.postAcgLikeAnswer.PostAcgLikeAnswer
import com.bngel.bcy.bean.QAController.postAcgQa.PostAcgQa
import com.bngel.bcy.bean.QAController.postEsSearchQa.PostEsSearchQa
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

interface QAControllerDao {

    /**
     * 问答推荐（随机推荐）
     */
    @GET("/es/recommendQa")
    fun getEsRecommendQa(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("token") token: String?
    ): Call<GetEsRecommendQa>

    /**
     * 问答搜索（会匹配标签内容和描述 拼音分词（搜拼音也能搜到） + ik细粒度分词）
     */
    @POST("/es/searchQa")
    fun postEsSearchQa(
        @Query("cnt") cnt: Int,
        @Query("id") id: String?,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("token") token: String?
    ): Call<PostEsSearchQa>

    /**
     * 回答问题
     */
    @POST("/acg/addAnswer")
    fun postAcgAddAnswer(
        @Query("description") description: String,
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("photo") photo: List<String>,
        @Query("token") token: String
    )

    /**
     * 添加问题评论
     */
    @POST("/acg/answerComment")
    fun postAcgAnswerComment(
        @Query("answerNumber") answerNumber: String,
        @Query("description") description: String,
        @Query("fatherNumber") fatherNumber: String?,
        @Query("id") id: String,
        @Query("replyNumber") replyNumber: String?,
        @Query("toId") toId: String,
        @Query("token") token: String
    )

    /**
     * 获取问答下回答的评论的评论列表（在回答主页面也用这个接口获取下面的数据 cnt请填3 page请填1 type请填1）
     */
    @GET("/acg/answerCommentCommentList")
    fun getAcgAnswerCommentCommentList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String?,
        @Query("number") number: String,
        @Query("page") page: Int,
        @Query("type") type: Int,
        @Query("token") token: String?
    ): Call<GetAcgAnswerCommentCommentList>

    @GET("/acg/answerCommentList")
    fun getAcgAnswerCommentList(
        @Query("answerNumber") answerNumber: String,
        @Query("cnt") cnt: Int,
        @Query("id") id: String?,
        @Query("page") page: Int,
        @Query("type") type: Int,
        @Query("token") token: String?
    ): Call<GetAcgAnswerCommentList>

    @GET("/acg/answerList")
    fun getAcgAnswerList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String?,
        @Query("number") number: String,
        @Query("page") page: Int,
        @Query("type") type: Int,
        @Query("token") token: String?
    ): Call<GetAcgAnswerList>

    @DELETE("/acg/dislikeAnswer")
    fun deleteAcgDislikeAnswer(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<DeleteAcgDislikeAnswer>

    @DELETE("/acg/dislikeComment")
    fun deleteAcgDislikeComment()

    @POST("/acg/followQA")
    fun postAcgFollowQA(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<PostAcgFollowQa>

    @DELETE("/acg/followQA")
    fun deleteAcgFollowQA(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<DeleteAcgFollowQa>

    @GET("/acg/followQAList")
    fun getAcgFollowQAList()

    @POST("/acg/likeAnswer")
    fun postAcgLikeAnswer(
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("token") token: String
    ): Call<PostAcgLikeAnswer>

    @POST("/acg/likeComment")
    fun postAcgLikeComment()

    @POST("/acg/QA")
    fun postAcgQA(
        @Query("description") description: String,
        @Query("id") id: String,
        @Query("label") label: List<String>,
        @Query("photo") photo: List<String>,
        @Query("title") title: String,
        @Query("token") token: String
    ): Call<PostAcgQa>

    @GET("/acg/qaAnswerCountsList")
    fun getAcgQaAnswerCountsList(
        @Query("id") id: String?,
        @Query("numbers") numbers: List<String>,
        @Query("token") token: String?
    ): Call<GetAcgQaAnswerCountsList>

    @GET("/acg/qaCommentCountsList")
    fun getAcgQaCommentCountsList()

    @GET("/acg/qaCountsList")
    fun getAcgQaCountsList()

    @GET("/acg/qaTopic")
    fun getAcgQaTopic(
        @Query("id") id: String?,
        @Query("number") number: String,
        @Query("token") token: String?
    ): Call<GetAcgQaTopic>

    @GET("/acg/judgeQa")
    fun getAcgJudgeQa(
        @Query("id") id: String,
        @Query("numbers") numbers: List<String>,
        @Query("token") token: String
    ): Call<GetAcgJudgeQa>

    fun getAcgJudgeQaAnswer(
        @Query("id") id: String,
        @Query("numbers") numbers: List<String>,
        @Query("token") token: String
    ): Call<GetAcgJudgeQaAnswer>


    companion object {
        fun create(): QAControllerDao {
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
            return retrofit.create(QAControllerDao::class.java)
        }

        fun create(gson: Gson): QAControllerDao {
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
            return retrofit.create(QAControllerDao::class.java)
        }
    }
}