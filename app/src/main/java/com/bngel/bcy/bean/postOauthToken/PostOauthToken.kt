package com.bngel.bcy.bean.postOauthToken

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostOauthToken(
    var access_token: String?,
    var token_type: String?,
    var expires_in: String?,
    var scope: String?,
    var error: String?,
    var error_description: String?
) {

    class DataStateDeserializer : JsonDeserializer<PostOauthToken> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostOauthToken {
            val response = Gson().fromJson(json, PostOauthToken::class.java)
            val jsonObject = json!!.asJsonObject

            if (jsonObject.has("error")) {
                response.error = jsonObject.get("error").asString
                response.error_description = jsonObject.get("error_description").asString
            }
            else {
                response.access_token = jsonObject.get("access_token").asString
                response.token_type = jsonObject.get("token_type").asString
                response.expires_in =  jsonObject.get("expires_in").asString
            }
            return response
        }
    }
}
