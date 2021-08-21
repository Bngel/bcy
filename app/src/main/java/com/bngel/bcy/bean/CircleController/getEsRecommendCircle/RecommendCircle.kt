package com.bngel.bcy.bean.CircleController.getEsRecommendCircle

data class RecommendCircle(
    val circleName: String,
    val createTime: String,
    val description: String,
    val followCounts: Int,
    val nickName: String,
    val photo: String,
    val postCounts: Int
)