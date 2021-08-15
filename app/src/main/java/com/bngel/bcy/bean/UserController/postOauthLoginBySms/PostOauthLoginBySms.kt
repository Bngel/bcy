package com.bngel.bcy.bean.UserController.postOauthLoginBySms


import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostOauthLoginBySms(
    var code: Int,
    var `data`: Any?,
    var msg: String
) {
    class DataStateDeserializer : JsonDeserializer<PostOauthLoginBySms> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostOauthLoginBySms {
            val response = Gson().fromJson(json, PostOauthLoginBySms::class.java)
            val jsonObject = json!!.asJsonObject
            response.code = jsonObject.get("code").asInt
            response.msg = jsonObject.get("msg").asString
            val dt = jsonObject.getAsJsonObject("data")
            if (dt.has("token")) {
                response.data = Data(dt.get("token").asString)
            }
            else {
                response.data = null
            }
            return response
        }
    }
}