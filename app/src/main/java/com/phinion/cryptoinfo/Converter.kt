package com.phinion.cryptoinfo

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phinion.cryptoinfo.models.crptoModels.AuditInfo
import com.phinion.cryptoinfo.models.crptoModels.Platform
import com.phinion.cryptoinfo.models.crptoModels.Quote

class Converter {
    @TypeConverter
    fun auditInfoListToString(value: List<AuditInfo>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToAuditInfoList(value: String) : List<AuditInfo> {

        val listType = object : TypeToken<List<AuditInfo>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun quoteListToString(value: List<Quote>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToQuoteList(value: String) : List<Quote>{
        val listType = object : TypeToken<List<Quote>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun tagListToString(value: List<String>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToTagList(value: String) : List<String>{
        val listType = object : TypeToken<List<Quote>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun platformToString(value: Platform) : String {

        return Gson().toJson(value)

    }

    @TypeConverter
    fun stringToPlatform(value: String) : Platform {

        val type = object : TypeToken<Platform>(){}.type
        return Gson().fromJson(value, type)
    }




}