package com.bngel.bcy.bean.TalkController.getCommunityTalkList

data class Data(
    val counts: Int,
    val pages: Int,
    val talkList: List<Talk>
)