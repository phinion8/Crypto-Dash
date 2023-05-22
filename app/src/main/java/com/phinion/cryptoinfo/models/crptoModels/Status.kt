package com.phinion.cryptoinfo.models.crptoModels


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("credit_count")
    val creditCount: Int,
    @SerializedName("elapsed")
    val elapsed: String,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("error_message")
    val errorMessage: String,
    @SerializedName("timestamp")
    val timestamp: String
)