package com.phinion.cryptoinfo.utils

import com.phinion.cryptoinfo.models.crptoModels.MarketModel
import retrofit2.Response

sealed class ApiState{
    class Success(val data: Response<MarketModel>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
    object Empty: ApiState()
}
