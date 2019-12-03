package com.taher.footballdata.data.datarepository.source.remote.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("message")
    val message: String
) {
    companion object {
        fun instance(jsonString: String): ErrorBody? {
            return try {
                Gson().fromJson(jsonString, ErrorBody::class.java)

            } catch (exception: IllegalStateException) {
                exception.printStackTrace()
                null
            }

        }
    }
}