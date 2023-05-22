package com.phinion.cryptoinfo.models.crptoModels


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.phinion.cryptoinfo.utils.Constants
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
@Entity(tableName = Constants.DATABASE_TABLE)
data class CryptoCurrency(
    @PrimaryKey(autoGenerate = true)
    val databaseId: Int = 0,
    @SerializedName("auditInfoList")
    val auditInfoList: @RawValue List<AuditInfo>,
    @SerializedName("circulatingSupply")
    val circulatingSupply: Double,
    @SerializedName("cmcRank")
    val cmcRank: Double,
    @SerializedName("dateAdded")
    val dateAdded: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isActive")
    val isActive: Double,
    @SerializedName("isAudited")
    val isAudited: Boolean,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("marketPairCount")
    val marketPairCount: Double,
    @SerializedName("maxSupply")
    val maxSupply: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("platform")
    val platform: @RawValue Platform,
    @SerializedName("quotes")
    val quotes: @RawValue List<Quote>,
    @SerializedName("selfReportedCirculatingSupply")
    val selfReportedCirculatingSupply: Double,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("totalSupply")
    val totalSupply: Double
) : Parcelable