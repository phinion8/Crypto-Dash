package com.phinion.cryptoinfo.repositories

import com.phinion.cryptoinfo.CryptoDao
import com.phinion.cryptoinfo.apis.ApiInterface
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.models.crptoModels.MarketModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val cryptoDao: CryptoDao
) {

    fun getCryptoInfoList(): Flow<Response<MarketModel>> = flow {
        emit(apiInterface.MarketModel())
    }.flowOn(Dispatchers.IO)

    fun getTopGainCryptoList(): Flow<Response<MarketModel>> = flow {
        emit(apiInterface.MarketModel())
    }.flowOn(Dispatchers.IO)

    val getAllCryptoDataList: Flow<List<CryptoCurrency>> = cryptoDao.getAllCryptoData()

    suspend fun addCryptoData(cryptoCurrency: CryptoCurrency) {

        cryptoDao.addCryptoData(cryptoCurrency)

    }

}