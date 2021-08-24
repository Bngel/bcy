package com.bngel.bcy.bean.HistoryController.getUserHistoryList

data class Data(
    val counts: Int,
    val historyCosList: List<HistoryCos>,
    val pages: Int
)