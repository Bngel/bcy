package com.bngel.bcy.bean.FansController.getUserFansList

data class Data(
    val counts: Int,
    val fansList: List<Fans>,
    val pages: Int
)