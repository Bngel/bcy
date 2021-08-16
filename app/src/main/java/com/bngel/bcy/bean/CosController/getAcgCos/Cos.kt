package com.bngel.bcy.bean.CosController.getAcgCos

data class Cos(
    val cosPhoto: List<String>,
    val createTime: String,
    val description: String,
    val fansCounts: Int,
    val id: String,
    val label: List<String>,
    val number: String,
    val photo: String,
    val username: String
)