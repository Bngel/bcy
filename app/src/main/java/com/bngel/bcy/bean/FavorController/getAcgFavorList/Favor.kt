package com.bngel.bcy.bean.FavorController.getAcgFavorList

data class Favor(
    val cosNumber: String,
    val cosPhoto: List<String>?,
    val create_time: String?,
    val description: String?,
    val id: String,
    val photo: String?,
    val username: String?
)