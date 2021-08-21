package com.bngel.bcy.bean.CircleController.postAcgCirclePhoto

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostAcgCirclePhoto(
    var code: Int,
    var `data`: Data,
    var msg: String
){
    class DataStateDeserializer : JsonDeserializer<PostAcgCirclePhoto> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostAcgCirclePhoto {
            val response = Gson().fromJson(json, PostAcgCirclePhoto::class.java)
            val jsonObject = json!!.asJsonObject
            response.code = jsonObject.get("code").asInt
            response.msg = jsonObject.get("msg").asString
            if (jsonObject.has("data")) {
                val dt = jsonObject.get("data").asJsonObject
                if (dt.has("url")) {
                    response.data.url = dt.get("url").asString
                }
                else {
                    response.data.url = null
                }
            }
            return response
        }
    }
}