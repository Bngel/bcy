package com.bngel.bcy.bean.MessageController.getCommunityLikeList

data class Data(
    val counts: Int,
    val likeList: List<Like>,
    val pages: Int
)