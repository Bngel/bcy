package com.bngel.bcy.bean.MessageController.getCommunityCommentList

data class Comment(
    val cosOrQaNumber: String,
    val createTime: String,
    val description: String,
    val id: String,
    val info: String,
    val isRead: Int,
    val photo: String,
    val type: Int,
    val username: String
)