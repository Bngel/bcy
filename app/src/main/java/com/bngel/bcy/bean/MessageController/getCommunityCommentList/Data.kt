package com.bngel.bcy.bean.MessageController.getCommunityCommentList

data class Data(
    val commentList: List<Comment>,
    val counts: Int,
    val pages: Int
)