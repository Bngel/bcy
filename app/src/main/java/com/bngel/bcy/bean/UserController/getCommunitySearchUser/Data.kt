package com.bngel.bcy.bean.UserController.getCommunitySearchUser

data class Data(
    val counts: Int,
    val pages: Int,
    val searchUserList: List<SearchUser>
)