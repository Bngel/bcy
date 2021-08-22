package com.bngel.bcy.bean.QAController.getAcgQaTopic

data class QATopic(
    val answerCounts: Int,
    val description: String,
    val followCounts: Int,
    val label: List<String>,
    val number: String,
    val photo: List<String>,
    val title: String
)