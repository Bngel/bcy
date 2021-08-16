package com.bngel.bcy.bean.FansController.getUserFollowList

data class Data(
    val counts: Int,
    val followList: List<Follow>,
    val pages: Int
)