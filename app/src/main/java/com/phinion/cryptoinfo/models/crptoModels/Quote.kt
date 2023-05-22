package com.phinion.cryptoinfo.models.crptoModels


import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("dominance")
    val dominance: Double,
    @SerializedName("fullyDilluttedMarketCap")
    val fullyDilluttedMarketCap: Double,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("marketCap")
    val marketCap: Double,
    @SerializedName("marketCapByTotalSupply")
    val marketCapByTotalSupply: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("percentChange1h")
    val percentChange1h: Double,
    @SerializedName("percentChange24h")
    val percentChange24h: Double,
    @SerializedName("percentChange30d")
    val percentChange30d: Double,
    @SerializedName("percentChange60d")
    val percentChange60d: Double,
    @SerializedName("percentChange7d")
    val percentChange7d: Double,
    @SerializedName("percentChange90d")
    val percentChange90d: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("turnover")
    val turnover: Double,
    @SerializedName("tvl")
    val tvl: Double,
    @SerializedName("volume24h")
    val volume24h: Double,
    @SerializedName("ytdPriceChangePercentage")
    val ytdPriceChangePercentage: Double
)