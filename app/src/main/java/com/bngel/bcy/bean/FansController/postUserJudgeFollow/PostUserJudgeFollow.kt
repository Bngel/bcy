package com.bngel.bcy.bean.FansController.postUserJudgeFollow


import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostUserJudgeFollow(
    var code: Int,
    var `data`: Data,
    var msg: String
)