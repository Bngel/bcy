package com.bngel.bcy.bean.QAController.getAcgAnswerList

data class Data(
    val answerList: List<Answer>,
    val counts: Int,
    val pages: Int
)