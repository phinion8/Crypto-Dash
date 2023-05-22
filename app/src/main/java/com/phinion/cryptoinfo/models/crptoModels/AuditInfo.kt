package com.phinion.cryptoinfo.models.crptoModels


import com.google.gson.annotations.SerializedName

data class AuditInfo(
    @SerializedName("auditStatus")
    val auditStatus: Int,
    @SerializedName("auditTime")
    val auditTime: String,
    @SerializedName("auditor")
    val auditor: String,
    @SerializedName("coinId")
    val coinId: String,
    @SerializedName("contractAddress")
    val contractAddress: String,
    @SerializedName("contractPlatform")
    val contractPlatform: String,
    @SerializedName("reportUrl")
    val reportUrl: String,
    @SerializedName("score")
    val score: String
)