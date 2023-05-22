package com.phinion.cryptoinfo.apis

import com.phinion.cryptoinfo.models.crptoModels.MarketModel
import retrofit2.Response
import retrofit2.http.GET


interface ApiInterface {

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun MarketModel() : Response<MarketModel>
}