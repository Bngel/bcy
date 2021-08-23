package com.bngel.bcy.bean.CosController.postAcgCosPhotoUpload

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostAcgCosPhotoUpload(
    var code: Int,
    var `data`: Data,
    var msg: String
){
    class DataStateDeserializer : JsonDeserializer<PostAcgCosPhotoUpload> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostAcgCosPhotoUpload {
            val response = Gson().fromJson(json, PostAcgCosPhotoUpload::class.java)
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