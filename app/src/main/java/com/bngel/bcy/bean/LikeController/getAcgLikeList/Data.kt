package com.bngel.bcy.bean.LikeController.getAcgLikeList

data class Data(
    val counts: Int,
    val likeCosList: List<LikeCos>,
    val pages: Int
)