package com.phinion.cryptoinfo.models.crptoModels


import com.google.gson.annotations.SerializedName
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency

data class Data(
    @SerializedName("cryptoCurrencyList")
    val cryptoCurrencyList: List<CryptoCurrency>,
    @SerializedName("totalCount")
    val totalCount: String
)