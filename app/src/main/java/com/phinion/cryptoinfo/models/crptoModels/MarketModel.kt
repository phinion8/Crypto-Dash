package com.phinion.cryptoinfo.models.crptoModels


import com.google.gson.annotations.SerializedName

data class MarketModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)