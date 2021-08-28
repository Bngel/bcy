package com.bngel.bcy.bean.MessageController.getCommunityLikeList

data class Like(
    val cosOrQaNumber: String,
    val createTime: String,
    val id: String,
    val info: String,
    val isRead: Int,
    val photo: String,
    val type: Int,
    val username: String
)