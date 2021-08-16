package com.bngel.bcy.bean.FansController.getUserFansList

data class Fans(
    val description: String,
    val fansCounts: Int,
    val id: String,
    val photo: String,
    val sex: String,
    val username: String
)