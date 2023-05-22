package com.phinion.cryptoinfo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {


    @Query("SELECT * FROM crypto_database_table")
    fun getAllCryptoData(): Flow<List<CryptoCurrency>>

    @Query("SELECT * FROM crypto_database_table WHERE databaseId = :cryptoId")
    fun getSelectedCryptoData(cryptoId : Int) : Flow<CryptoCurrency>

    @Insert
    suspend fun addCryptoData(cryptoCurrency: CryptoCurrency)

    @Delete
    suspend fun deleteCryptoData(cryptoCurrency: CryptoCurrency)

}