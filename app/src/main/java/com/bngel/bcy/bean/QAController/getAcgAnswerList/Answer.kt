package com.bngel.bcy.bean.QAController.getAcgAnswerList

data class Answer(
    val answerPhoto: List<String>,
    val createTime: String,
    val description: String,
    val id: String,
    val number: String,
    val photo: String,
    val username: String
)