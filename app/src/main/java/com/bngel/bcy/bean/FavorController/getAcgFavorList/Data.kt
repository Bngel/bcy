package com.bngel.bcy.bean.FavorController.getAcgFavorList

data class Data(
    val counts: Int,
    val favorList: List<Favor>,
    val pages: Int
)