package com.bngel.bcy.bean.QAController.getAcgAnswerCommentList

data class Data(
    val answerCommentList: List<AnswerComment>,
    val counts: Int,
    val pages: Int
)