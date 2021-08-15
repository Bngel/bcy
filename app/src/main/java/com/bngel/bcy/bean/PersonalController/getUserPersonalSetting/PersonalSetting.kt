package com.bngel.bcy.bean.PersonalController.getUserPersonalSetting

data class PersonalSetting(
    val id: Long,
    val pushComment: Int,
    val pushFans: Int,
    val pushInfo: Int,
    val pushLike: Int,
    val pushSystem: Int
)