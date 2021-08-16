package com.bngel.bcy.bean.CosController.getAcgCosComment

data class Data(
    val cosCommentList: List<CosComment>,
    val counts: Int,
    val pages: Int
)